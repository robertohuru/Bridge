package settings;

import Utils.SystemProperties;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Research1
 */
public class LoadSettings {

    private HashMap<String, String> settings;
    private static File settingsFile;
    private static DocumentBuilderFactory documentBuilderFactory;
    DocumentBuilder documentBuilder;
    private static Document document;

    private LoadSettings() {
        //new SystemProperties().copySettingsFile();
        settingsFile = new File(Utils.SystemProperties.getSettingsFolder() + File.separator + "settings.xml");
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(settingsFile);
            document.getDocumentElement().normalize();
        } catch (Exception ex) {
            Logger.getLogger(LoadSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        init();
        setSettings();
    }

    public static LoadSettings getInstance() {
        return LoadSettingsHolder.INSTANCE;
    }

    private static class LoadSettingsHolder {

        private static final LoadSettings INSTANCE = new LoadSettings();
    }

    public HashMap getSettings() {
        return settings;
    }

    private void init() {
        settings = new HashMap<>();
        settings.put("dbmsHost", "");
        settings.put("dbmsPort", "");
        settings.put("dbmsUser", "");
        settings.put("dbmsPassword", "");
        settings.put("dbmsDatabase", "");
        settings.put("comPort", "");
        settings.put("smcNumber", "");
        settings.put("password", "");
        settings.put("mobNumber", "");
        settings.put("nationalIdFolder", "");
        settings.put("logsFolder", "");
    }

    public static void setComPort(String newValue) {
        NodeList nodeLst = document.getElementsByTagName("sms");
        for (int s = 0; s < nodeLst.getLength(); s++) {
            Node fstNode = nodeLst.item(s);
            if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                Element fstElmnt = (Element) fstNode;
                Node m = fstElmnt.getElementsByTagName("port").item(0).getFirstChild();
                m.setNodeValue(newValue);

            }
        }
    }

    public static void setSMCNumber(String newValue) {
        NodeList nodeLst = document.getElementsByTagName("sms");
        for (int s = 0; s < nodeLst.getLength(); s++) {
            Node fstNode = nodeLst.item(s);
            if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                Element fstElmnt = (Element) fstNode;
                Node m = fstElmnt.getElementsByTagName("smcNumber").item(0).getFirstChild();
                m.setNodeValue(newValue);

            }
        }
    }

    public static void setMobileNumber(String newValue) {
        NodeList nodeLst = document.getElementsByTagName("sms");
        for (int s = 0; s < nodeLst.getLength(); s++) {
            Node fstNode = nodeLst.item(s);
            if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                Element fstElmnt = (Element) fstNode;
                Node m = fstElmnt.getElementsByTagName("mobNumber").item(0).getFirstChild();
                m.setNodeValue(newValue);

            }
        }
    }

    public static void setPassword(String newValue) {
        NodeList nodeLst = document.getElementsByTagName("sms");
        for (int s = 0; s < nodeLst.getLength(); s++) {
            Node fstNode = nodeLst.item(s);
            if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                Element fstElmnt = (Element) fstNode;
                if (fstElmnt.getElementsByTagName("password").item(0).hasChildNodes()) {
                    Node m = fstElmnt.getElementsByTagName("password").item(0).getFirstChild();
                    m.setNodeValue(newValue);
                }
            }
        }
    }

    public static void save() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(settingsFile.getAbsolutePath()));
            try {
                transformer.transform(source, result);
            } catch (TransformerException ex) {
                Logger.getLogger(LoadSettings.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(LoadSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setSettings() {
        SettingsHandler.handleSettingsTag(document, getSettings());
    }

}
