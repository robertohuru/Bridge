/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import settings.Settings;

/**
 *
 * @author Research1
 */
public class SystemProperties {
    public static File SETTINGS_FOLDER_PATH;
    public static String path;
    public SystemProperties() {
          path = getClass().getClassLoader().getResource("settings/settings.xml").getPath();
    }

    /**
     * Returns the temporary Folder
     *
     * @param No parameter specified
     * @return Returns the Temporary folder
     */
    public static String getTempDirectory() {
        return System.getProperty("java.io.tmpdir");
    }
    

    /**
     * Returns the Settings Folder
     *
     * @param No parameter specified
     * @return Returns the Settings folder
     */
    public static File getSettingsFolder() {

        File settingsFolder = new File(System.getProperty("user.home") + File.separator + "SMSApplication" + File.separator + "settings");
        return settingsFolder;
    }
    
    public static File getTestFolder() {
        File settingsFolder = new File(System.getProperty("user.home") + File.separator + "SMSApplication" + File.separator + "test");
        return settingsFolder;
    }
//    public static void copySettingsFile(){
//        File settingsFile = new File(Utils.SystemProperties.getSettingsFolder()+ File.separator + "settings.xml");
//        if(!settingsFile.exists()){
//            try {
//                FileUtils.copyFileToDirectory(new File(path), Utils.SystemProperties.getSettingsFolder());
//            } catch (IOException ex) {
//                Logger.getLogger(SystemProperties.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//    }
     

    /**
     * Returns the National ID Folder
     *
     * @param No parameter specified
     * @return Returns the National ID folder
     */
    public static File getErrorLogFolder() {
        File nationalIDFolder = new File(System.getProperty("user.home") + Settings.getInstance().getLogsFolder());
        return nationalIDFolder;
    }



    /**
     * Returns the File Separator
     *
     * @param No parameter specified
     * @return Returns the File Separator
     */
    public static String getFileSeparator() {
        return File.separator;
    }



    /**
     * Creates the BioApp folders
     *
     * @param No parameter specified
     * @return Returns void
     */
    public static void createSMSAppFolders() {        
        if (!getSettingsFolder().isDirectory()) {
            getSettingsFolder().mkdirs();
        }
        
        if (!getErrorLogFolder().isDirectory()){
            getErrorLogFolder().mkdirs();
        }
        if (!getTestFolder().isDirectory()){
            getTestFolder().mkdirs();
        }

    }
    
    public static void main(String []args){
        createSMSAppFolders();        
        
    }

    /**
     * Initialize Global Resources
     *
     * @param No parameter specified
     * @return Returns void
     */
    public static void initializeGlobalResources() {

        SETTINGS_FOLDER_PATH = SystemProperties.getSettingsFolder();
    }

    /**
     * Check if Global Resources have been Initialized
     *
     * @param No parameter specified
     * @return Returns true if the Global Resources have been Initialized and
     * false otherwise
     */
    public static boolean globalsInitialized() {

        return SETTINGS_FOLDER_PATH != null;
    }
   

}
