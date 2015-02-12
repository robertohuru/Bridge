package settings;

import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Research1
 */
public class Settings {

    private final String dbmsHost;
    private final String dbmsPort;
    private final String dbmsUser;
    private final String dbmsPassword;
    private final String dbmsDatabase;
    private final String comPort;
    private final String smcNumber;
    private final String password;
    private final String mobNumber;
    private final String logsFolder;
    private final String settingsFolder;


    private Settings() {
        HashMap<String, String> settings = LoadSettings.getInstance().getSettings();
        dbmsHost = settings.get("dbmsHost");
        dbmsPort = settings.get("dbmsPort");
        dbmsUser = settings.get("dbmsUser");
        dbmsPassword = settings.get("dbmsPassword");
        dbmsDatabase = settings.get("dbmsDatabase");
        
        comPort = settings.get("comPort");
        settings.put(comPort, "Robert");
        smcNumber = settings.get("smcNumber");
        password = settings.get("password");
        mobNumber = settings.get("mobNumber");
        logsFolder = settings.get("logsFolder");
        settingsFolder = settings.get("settingsFolder");
    }

    public static Settings getInstance() {
        return SettingsHolder.INSTANCE;
    }

    private static class SettingsHolder {

        private static final Settings INSTANCE = new Settings();
    }

    public String getDatabaseHost() {
        return dbmsHost;
    }

    public String getDatabasePort() {
        return dbmsPort;
    }

    public String getDatabaseUser() {
        return dbmsUser;
    }

    public String getDatabasePassword() {
        return dbmsPassword;
    }

    public String getDatabase() {
        return dbmsDatabase;
    }

    public String getComPort() {
        return comPort;
    }

    public String getSMCNumber() {
        return smcNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getMobileNumber() {
        return mobNumber;
    }
    public String getSettingsFolder() {
        return settingsFolder;
    }
    public String getLogsFolder() {
        return logsFolder;
    }
}
