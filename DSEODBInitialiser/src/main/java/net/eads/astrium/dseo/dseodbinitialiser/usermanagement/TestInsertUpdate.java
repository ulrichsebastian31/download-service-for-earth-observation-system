/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eads.astrium.dseo.dseodbinitialiser.usermanagement;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import net.eads.astrium.dseo.dbhandler.usermanagement.MMFASUserLoader;
import net.eads.astrium.dseo.dseodbinitialiser.TestConnexionParameter;

/**
 *
 * @author re-sulrich
 */
public class TestInsertUpdate {
    
    public static final String user1 = "f9565cf6dba885cf5e25150f936fd9c1";
    public static final String user2 = "e8fb3edf039b2b2253ea265b200b15e7";
    public static final String dm1 = "5ae3e04b833ef6a49636095fd227dee9";
    public static final String dm2 = "442143e70eddb7768e30f2bc16f024e7";
    //to be replaced with the random IDs created during the creation of the DAR
    public static String dar1 = "25d0587bf7ed8e0ea26f9e5359391cf1";
    public static String dar2 = "ad6807775ea093bf1ea77c7c6adfbcea";
    
    public static Calendar timeFirstDARInsert;
    private MMFASUserLoader userLoader;
    
    public void test() throws SQLException, ParseException, InterruptedException {
        
        userLoader = new MMFASUserLoader(
                "jdbc:postgresql://" + TestConnexionParameter.getUrl() + "/DSEODatabase",
                TestConnexionParameter.getUser(), 
                TestConnexionParameter.getPass(),
                TestConnexionParameter.getUserDatabase());
        
//        System.out.println("" + getRandom32BitsID());
        
        testAddUsers();
    }
    
    public void testAddUsers() throws SQLException {
        
        userLoader.addDMUser(user1, "First user", "sebastian_ulrich@hotmail.fr", "00447964626464", 5);
        userLoader.addDMUser(user2, "Second user", "sebastian_ulrich@hotmail.fr", "00447964626464", 5);
    }
}
