/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sms;

/**
 *
 * @author DELL
 */
import Utils.Util;
import controllers.ChatController;
import controllers.DraftInfo;
import controllers.InboxController;
import controllers.OutboxInfo;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.smslib.*;
import org.smslib.AGateway.GatewayStatuses;
import org.smslib.AGateway.Protocols;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.Message.MessageTypes;
import org.smslib.helper.SerialPort;
import org.smslib.modem.SerialModemGateway;
import views.Inbox;

public class Main {

    private String comport;
    private static String center;
    private static String pin;
    private static SerialModemGateway gateway;
    private SerialPort serialPort;
    //private static CallNotification callNotification = new CallNotification();
    private static GatewayStatusNotification statusNotification = new GatewayStatusNotification();
    private static OrphanedMessageNotification orphanedMessageNotification = new OrphanedMessageNotification();
    private static FileWriter fw;
    private static BufferedWriter bw;
    private static String newLine;
    private static Inbox inbox;

    public static void setModemSettings(String port, String center, String pin) {
        gateway = new SerialModemGateway("modem.com4",
                port, 460800, "Huawei", "E160");
        gateway.setInbound(true);
        gateway.setOutbound(true);
        gateway.setSmscNumber(center);
        gateway.setSimPin(pin);
        gateway.setProtocol(Protocols.PDU);
        gateway.getATHandler().setStorageLocations("SMME");
        
    }

    public static int getSignalLevel() {
        int level = 0;
        try {
            level = gateway.getSignalLevel();
                    } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return level;
    }

    public Main() {

    }

    public static void write(String text) {
        StringBuffer buf = new StringBuffer();
        //FileWriter fw;
        //BufferedWriter bw;
        File file = new File(Utils.SystemProperties.getErrorLogFolder().getAbsolutePath() + File.separator + "activity.log");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {

            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            newLine = System.getProperty("line.separator");
            bw.write(text + newLine);
            //Closing BufferedWriter Stre
            bw.close();
            fw.close();

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void startService() throws Exception {
        System.out.println("Example: Read messages from a serial gsm modem.");
        System.out.println(Library.getLibraryDescription());
        System.out.println("Version: " + Library.getLibraryVersion());
        Service.getInstance().addGateway(gateway);
        InboundNotification inboundNotification = new InboundNotification();
        OutboundNotification outboundNotification = new OutboundNotification();
        Service.getInstance().setInboundMessageNotification(inboundNotification);
        Service.getInstance().setOutboundMessageNotification(outboundNotification);
        //Service.getInstance().setCallNotification(callNotification);
        Service.getInstance().setGatewayStatusNotification(statusNotification);
        Service.getInstance().setOrphanedMessageNotification(orphanedMessageNotification);
        Service.getInstance().startService();
        

    }

    public static void readMessage() throws Exception {
        try {
            List<InboundMessage> msgList = new ArrayList<InboundMessage>();
            Service.getInstance().readMessages(msgList, MessageClasses.ALL);
            InboxController inbox = new InboxController();
            ChatController chat = new ChatController();
            for (InboundMessage msg : msgList) {
                inbox.setMessage(msg.getText());
                inbox.setSender(msg.getOriginator());
                inbox.setDateSent(msg.getDate().toString());
                chat.setMessage(msg.getText());
                chat.setSender(msg.getOriginator());
                chat.setOwner(settings.Settings.getInstance().getMobileNumber());
                chat.setSent(false);
                inbox.save();
                chat.save();
                gateway.deleteMessage(msg);
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "A new Message from " + msg.getOriginator(), "New Message", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(msg.getText());
            }
        } catch (Exception e) {
            Utils.Util.logError(Main.class.getName(), e.getMessage());
            e.printStackTrace();
        } finally {
            //Service.getInstance().stopService();
        }

    }

    public static class InboundNotification implements IInboundMessageNotification {

        private static String message = "";

        public void process(AGateway gateway, MessageTypes msgType, InboundMessage msg) {
            if (msgType == MessageTypes.INBOUND) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "A new Message from " + msg.getOriginator(), "New Message", JOptionPane.INFORMATION_MESSAGE);
                Utils.Util.logError(Main.class.getName(), ">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
                System.out.println(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
            } else if (msgType == MessageTypes.STATUSREPORT) {
                Utils.Util.logError(Main.class.getName(), ">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
                System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
            }
            InboxController inbox = new InboxController();
            ChatController chat = new ChatController();
            inbox.setMessage(msg.getText());
            inbox.setSender(msg.getOriginator());
            inbox.setDateSent(msg.getDate().toString());
            chat.setMessage(msg.getText());
            chat.setSender(msg.getOriginator());
            chat.setOwner(settings.Settings.getInstance().getMobileNumber());
            chat.setSent(false);
            inbox.save();
            chat.save();

            try {
                gateway.deleteMessage(msg);
            } catch (Exception ex) {
                Utils.Util.logError(Main.class.getName(), ex.getMessage());
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Closing BufferedWriter Stream
            System.out.println(msg.getText());

            //write(msg.getText() + "\t" + msg.getOriginator() + "\t" + msg.getDate());
            //write(msg.getText());
            //updateGui(msg.toString());
        }

    }

    public static class CallNotification implements ICallNotification {

        public void process(AGateway gateway, String callerId) {
            System.out.println(">>> New call detected from Gateway: " + gateway.getGatewayId() + " : " + callerId);
        }
    }

    public static class GatewayStatusNotification implements IGatewayStatusNotification {

        public void process(AGateway gateway, GatewayStatuses oldStatus, GatewayStatuses newStatus) {
            System.out.println(">>> Gateway Status change for " + gateway.getGatewayId() + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
        }
    }

    public static class OrphanedMessageNotification implements IOrphanedMessageNotification {

        public boolean process(AGateway gateway, InboundMessage msg) {
            System.out.println(">>> Orphaned message part detected from " + gateway.getGatewayId());
            System.out.println(msg);
            // Since we are just testing, return FALSE and keep the orphaned message part.
            return false;
        }
    }

    public static void sendMessage(String message, String[] recipients, JLabel label) throws Exception {
        boolean result = false;
//        try {
//            Service.getInstance().addGateway(gateway);
//
//        } catch (GatewayException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Service.getInstance().startService();
        OutboundMessage m = new OutboundMessage();
        final int total = recipients.length;
        int num = 0;
        OutboxInfo outbox = new OutboxInfo();
        ChatController chat = new ChatController();
        for (int i = 0; i < recipients.length; i++) {
            num++;
            outbox.setSender(settings.Settings.getInstance().getMobileNumber());
            outbox.setReciever(recipients[i]);
            outbox.setMessage(message);
            outbox.setDateSent(m.getDate().toString());
            m.setRecipient(recipients[i]);
            m.setText(message);

            chat.setMessage(message);
            chat.setSender(recipients[i]);
            chat.setOwner(settings.Settings.getInstance().getMobileNumber());
            chat.setSent(true);
            label.setText("Sending Message to " + num + " of " + total + " Contacts");
            if (Service.getInstance().sendMessage(m) == true) {
                System.out.println("Sent Status :"+gateway.getDeliveryErrorCode());
                outbox.save();
                chat.save();
            } else {
                DraftInfo draft = new DraftInfo();
                draft.setMessage(message);
                draft.setDateSent(m.getDate().toString());
                draft.setReciever(recipients[i]);
                if (draft.save() > 0) {
                    JOptionPane.showMessageDialog(null, "Message not sent to" + recipients[i] + ". Saved in Draft !", "Error Message !!", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        label.setText("");

        String portname;
        String owner;
//        Service.getInstance().stopService();
//        gateway.stopGateway();

    }

    public static void sendMessageToOne(String message, String recipients) throws Exception {

        OutboundMessage m = new OutboundMessage();
        m.setRecipient(recipients);
        m.setText(message);
        OutboxInfo outbox = new OutboxInfo();
        outbox.setSender(settings.Settings.getInstance().getMobileNumber());
        outbox.setReciever(recipients);
        outbox.setMessage(message);
        outbox.setDateSent(m.getDate().toString());
        ChatController chat = new ChatController();
        chat.setMessage(message);
        chat.setSender(recipients);
        chat.setOwner(settings.Settings.getInstance().getMobileNumber());
        chat.setSent(true);
        if (Service.getInstance().sendMessage(m) == true) {
            outbox.save();
            chat.save();
        } else {
            DraftInfo draft = new DraftInfo();
            draft.setMessage(message);
            draft.setDateSent(m.getDate().toString());
            draft.setReciever(recipients);
            if (draft.save() > 0) {
                JOptionPane.showMessageDialog(null, "Message not sent to " + recipients + ". Saved in Draft !", "Error Message !!", JOptionPane.ERROR_MESSAGE);
            }
        }

        //        Service.getInstance().stopService();
        //        gateway.stopGateway();
    }

    public static void close() {

        try {
            Service.getInstance().stopService();
            
            Service.getInstance().removeGateway(gateway);
        } catch (Exception ex) {
            Util.logError(Main.class.getName(), ex.getMessage());
        } 

    }

    /**
     * Outbound Message informations handler
     *
     * @author chandpriyankara
     *
     */
    public static class OutboundNotification implements IOutboundMessageNotification {

        public void process(AGateway gateway, OutboundMessage msg) {
        }
    }

    public static void main(String args[]) {
        new Main();
        Main.setModemSettings("COM3", "+254722500029", "");
        try {
            Main.startService();
            //String[] contact = new String[]{"0714360799", "0711149849"};
//        try {
//            //Main.sendMessageToOne("Hello there", "0711149849");
//
//            //System.out.println("Sent");
//            Main.readMessage();
//
//            //Main.close("COM9");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
