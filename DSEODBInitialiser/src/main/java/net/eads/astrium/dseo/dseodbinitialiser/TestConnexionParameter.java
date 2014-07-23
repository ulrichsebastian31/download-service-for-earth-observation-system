/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eads.astrium.dseo.dseodbinitialiser;

/**
 *
 * @author re-sulrich
 */
public class TestConnexionParameter {
    private static String url;
    private static String user;
    private static String pass;
    private static String dseoDatabase;
    private static String userDatabase;
    private static String adminDatabase;

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        TestConnexionParameter.url = url;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        TestConnexionParameter.user = user;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String pass) {
        TestConnexionParameter.pass = pass;
    }

    public static String getDSEODatabase() {
        return dseoDatabase;
    }

    public static void setDSEODatabase(String dseoDatabase) {
        TestConnexionParameter.dseoDatabase = dseoDatabase;
    }

    public static String getUserDatabase() {
        return userDatabase;
    }

    public static void setUserDatabase(String userDatabase) {
        TestConnexionParameter.userDatabase = userDatabase;
    }

    public static String getAdminDatabase() {
        return adminDatabase;
    }

    public static void setAdminDatabase(String adminDatabase) {
        TestConnexionParameter.adminDatabase = adminDatabase;
    }
}
