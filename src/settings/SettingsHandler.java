
package settings;

import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Research1
 */
public class SettingsHandler {


    private static NodeList dbmsNodeList, smsNodeList, rmiServerNodeList, mysqldumpNodeList, ftpServerNodeList, abisNodeList, serverFoldersNodeList;


    public static void handleSettingsTag(Document document, HashMap settings) {
        dbmsNodeList = document.getElementsByTagName("dbms");
        handleDBMSTag(dbmsNodeList, settings);
        smsNodeList = document.getElementsByTagName("sms");
        handleSMSSettingsTag(smsNodeList, settings);
        serverFoldersNodeList = document.getElementsByTagName("serverFolders");
        handleServerFolderSettingsTag(serverFoldersNodeList, settings);

    }

    public static void handleDBMSTag(NodeList dbmsNodeList, HashMap settings) {
        Node node = dbmsNodeList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            settings.put("dbmsHost", getTagText("host", element));
            settings.put("dbmsPort", getTagText("port", element));
            settings.put("dbmsUser", getTagText("user", element));
            settings.put("dbmsPassword", getTagText("password", element));
            settings.put("dbmsDatabase", getTagText("database", element));
            
            
        }

    }

    public static void handleSMSSettingsTag(NodeList dbmsNodeList, HashMap settings) {
        Node node = dbmsNodeList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            settings.put("comPort", getTagText("port", element));
            settings.put("smcNumber", getTagText("smcNumber", element));
            settings.put("password", getTagText("password", element));
            settings.put("mobNumber", getTagText("mobNumber", element));
        }

    }
    public static void handleServerFolderSettingsTag(NodeList dbmsNodeList, HashMap settings) {
        Node node = dbmsNodeList.item(0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            settings.put("logsFolder", getTagText("logsFolder", element));
            settings.put("settingsFolder", getTagText("settingsFolder", element));
        }
    }
    private static String getTagText(String tagName, Element element) {
        Node nodeValue = element.getElementsByTagName(tagName).item(0).getFirstChild();
        return nodeValue == null ? "" : nodeValue.getNodeValue();
        
    }
}
