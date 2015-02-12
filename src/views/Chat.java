/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.ChatController;
import controllers.ContactInfo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import static java.awt.MouseInfo.getPointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import models.InBoxModel;
import sms.Main;

/**
 *
 * @author DELL
 */
public class Chat extends javax.swing.JInternalFrame {

    /**
     * Creates new form Chat
     */
    private JDesktopPane desktop;
    private static final int width = 300;
    private static final int height = 150;
    private Worker worker;
    private StatusDialog dialog;
    ArrayList<Integer> arr;
    private String text;
    private ContactInfo contact;
    private String mobileNum;
    private String name;
    public Chat(JDesktopPane desktop, ContactInfo contact) {
        initComponents();
        this.desktop = desktop;  
        this.contact = contact;
        this.setTitle("Chat with "+contact.getFirstName()+" "+contact.getLastName()+" : Mobile Number :"+contact.getMobNumber());
       
        if(contact.getMobNumber().startsWith("254")){
             this.mobileNum = contact.getMobNumber();
        }else{
             this.mobileNum = "254"+contact.getMobNumber().substring(1);
        }
        this.name = contact.getFirstName();
        arr = new ArrayList<>();
        btnSend.setName("btnSend");
        text = "Please Wait. Sending Message...........";
        final Color newColor = Color.MAGENTA;
        ScrollBarUI myUi = new BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(newColor);

                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(newColor);
                return button;
            }
        };
        
        jScrollPane2.getVerticalScrollBar().setUI(myUi);
        read();
        clock();
    }
    
    
    class Worker extends SwingWorker<Integer, String> {

        private JButton button;
        private String action = "";

        public Worker(JButton button) {
            this.button = button;

        }

        @Override
        protected Integer doInBackground() throws Exception {
            action = button.getActionCommand();
            if (action.equalsIgnoreCase("Send")) {
                sendMessage(mobileNum);
            }
            failIfInterrupted();
            return 1;
        }

        @Override
        public void done() {

        }

        private void failIfInterrupted() throws InterruptedException {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException("Interrupted while working..");
            }
        }
    }

    private void sendMessage(String contact) {
           
        //sms.Main.setModemSettings(tfPort.toUpperCase(), tfCenter, tfPin);
        try {
            Main.sendMessageToOne(taMessage.getText(), contact);
            
            //Main.close(tfPort);
        } catch (Exception ex) {
            Logger.getLogger(NewMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void send() {
        dialog = new StatusDialog((Frame) this.getTopLevelAncestor(), text);
        worker = new Worker(btnSend);
        worker.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                switch (event.getPropertyName()) {
                    case "progress":
                        break;
                    case "state":
                        switch ((SwingWorker.StateValue) event.getNewValue()) {
                            case DONE:
                                worker = null;
                                dialog.setVisible(false);
                                dialog.dispose();
                                break;
                            case STARTED:
                            case PENDING:
                                dialog.setVisible(true);

                                break;
                        }
                        break;
                }
            }
        });
        worker.execute();
    }
        
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taMessage = new javax.swing.JTextArea();
        btnSend = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelChat = new javax.swing.JPanel();

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setTitle("Chat");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Enter you message here below"));

        taMessage.setColumns(20);
        taMessage.setLineWrap(true);
        taMessage.setRows(5);
        taMessage.setToolTipText("Enter Your Message Here");
        taMessage.setWrapStyleWord(true);
        jScrollPane1.setViewportView(taMessage);

        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
                    .addComponent(btnSend, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btnSend)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelChat.setBackground(new java.awt.Color(255, 255, 255));
        panelChat.setLayout(new java.awt.GridBagLayout());
        jScrollPane2.setViewportView(panelChat);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(257, 257, 257)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                    .addGap(0, 138, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        
        if (worker == null) {
            send();            
        } else {
            worker.cancel(true);
        }
        
        
        //getSizeO(jPanel1);
//        JPanel pan = new Panel(taMessage.getText());
//        pan.setSize(width, height);
//        pan.setBorder(new TextBubbleBorder(Color.orange.darker(), 2, 4, 0));
//        JTextArea tchat = new JTextArea();
//        panelChat.setLayout(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weightx = 1.0;
//        c.weighty = 0.0;
//        c.fill = GridBagConstraints.BOTH;
//        panelChat.add(pan, c);
//        panelChat.add(new JSeparator());

//        panelChat.setLayout(new BoxLayout(panelChat, BoxLayout.Y_AXIS));
//        tchat.setText(taMessage.getText());
//        tchat.setSize(164, 50);
//        tchat.setEditable(false);
//        tchat.setBorder(new LineBorder(Color.yellow, 2));
//        pan.setAlignmentX(Component.LEFT_ALIGNMENT);
        //panelChat.add(tchat, Component.LEFT_ALIGNMENT);
        //panelChat.add(pan, Component.RIGHT_ALIGNMENT);
//        panelChat.revalidate();
//        panelChat.repaint();
    }//GEN-LAST:event_btnSendActionPerformed

    private void getSizeO(JPanel panel) {
        final JButton b = new JButton();
        Component[] c = panel.getComponents();
        for (int i = 0; i < c.length; i++) {
            if (c[i].getClass().getName().toString().equals("javax.swing.JButton")) {
                final String name = ((JButton) c[i]).getName();
                c[i].addMouseListener(new MouseListener() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        arr.add(1);

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }

                });
            }
        }

    }
    
    public void read(){
        panelChat.removeAll();
        panelChat.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        String mesg = "";
        ChatController inbox[] = new InBoxModel().getAllChats(settings.Settings.getInstance().getMobileNumber(), mobileNum);
        for (int i = 0; i < inbox.length; i++) {
            if(inbox[i].getMessage().isEmpty()){
                mesg = "Empty Message";
            }else{
                mesg = inbox[i].getMessage();
            }
            JPanel pan = new MYPanel(name, mesg);
            JPanel p = new Panel(mesg);
            c.gridy = i;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.weightx = 1.0;
            c.weighty = 1.0;
            c.insets = new Insets(10, 0, 0,200);
            //pan.setSize(width, height);
            //p.setSize(width, height);
            if (inbox[i].isSent() == false) {
                c.gridx = 0;
                panelChat.add(pan, c);
            } else {
                c.insets = new Insets(10, 100, 0, 0);
                c.gridx = 0;
                panelChat.add(p, c);

            }
            panelChat.revalidate();
            panelChat.repaint();
        }

    }
    
    
    private void clock() {
        javax.swing.Timer timer = new javax.swing.Timer(3000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                read();
            }
        });
        timer.start();

    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelChat;
    private javax.swing.JTextArea taMessage;
    // End of variables declaration//GEN-END:variables
}
