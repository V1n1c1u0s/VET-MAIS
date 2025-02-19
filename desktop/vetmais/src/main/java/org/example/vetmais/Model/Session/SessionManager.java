package org.example.vetmais.Model.Session;

import java.io.*;
import java.util.Properties;

public class SessionManager {

    private static final String SESSION_FILE = "session.properties";

    public static void saveSession(String token) throws IOException {
        Properties props = new Properties();
        props.setProperty("token", token);
        try (FileOutputStream fos = new FileOutputStream(SESSION_FILE)) {
            props.store(fos, "Sessão do Usuário");
        }
    }

    public static String loadSession() throws IOException {
        Properties props = new Properties();
        File sessionFile = new File(SESSION_FILE);
        if (sessionFile.exists()) {
            try (FileInputStream fis = new FileInputStream(sessionFile)) {
                props.load(fis);
                return props.getProperty("token");
            }
        }
        return null;
    }

    public static void clearSession() {
        File sessionFile = new File(SESSION_FILE);
        if (sessionFile.exists()) {
            sessionFile.delete();
        }
    }
}

