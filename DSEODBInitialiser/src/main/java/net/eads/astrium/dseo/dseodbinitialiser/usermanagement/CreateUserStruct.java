/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eads.astrium.dseo.dseodbinitialiser.usermanagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.eads.astrium.dseo.dseodbinitialiser.TestConnexion;

/**
 *
 * @author re-sulrich
 */
public class CreateUserStruct {
    
    public static final String user1 = "f9565cf6dba885cf5e25150f936fd9c1";
    public static final String user2 = "e8fb3edf039b2b2253ea265b200b15e7";
    
    public static void createUserStruct() throws SQLException {
        
        TestConnexion.testDropAndCreateSchema("UserDatabase");
        
        testCreateUserTable();
    }
    
    public static void testCreateUserTable() throws SQLException {
        List<String[]> fields = new ArrayList<String[]>();
        
        fields.add(new String[]{"userId", "varchar(32) primary key"});
        fields.add(new String[]{"name", "varchar(100)"});
        fields.add(new String[]{"email", "varchar(100)"});
        fields.add(new String[]{"phoneNumber", "varchar(20)"});
        fields.add(new String[]{"maxDownloadManagers", "integer"});
        
        TestConnexion.create("MMFASUser", fields);
    }
}
