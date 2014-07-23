/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.dseo.dseodbinitialiser.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.eads.astrium.dseo.dseodbinitialiser.TestConnexion;
import net.eads.astrium.dseo.dseodbinitialiser.TestConnexionParameter;

/**
 *
 * @author re-sulrich
 */
public class CreateAdminDatabase {

    
    public static void main(String[] args) throws Exception {
        
//        TestConnexionParameter.setUrl("127.0.0.1:5432");
        TestConnexionParameter.setUrl("10.2.200.115:5432");
        TestConnexionParameter.setUser("postgres");
        TestConnexionParameter.setPass("password");
        
        TestConnexionParameter.setAdminDatabase("AdminDatabase");
        TestConnexion.conn = TestConnexion.createAdminDBConnexion();
               
        createAdminTables();
        
        InsertTablesWithNames.insertTables();
        InsertColumnsWithNames.insertAllColumns();
    }
    
    public static void createAdminTables() throws Exception {

        TestConnexion.testDropAndCreateSchema("admindatabase");
        testCreateMMFASTableTable();
        testCreateMMFASColumnTable();
    }
    
    public static void testCreateMMFASTableTable() throws SQLException {
        List<String[]> fields = new ArrayList<String[]>();
        
        fields.add(new String[]{"tableId", "varchar(100) primary key"});
        fields.add(new String[]{"name", "varchar(100)"});
        fields.add(new String[]{"lastUpdateTime", "timestamp"});
        
        TestConnexion.create("MMFASTable", fields);
    }
    
    public static void testCreateMMFASColumnTable() throws SQLException {
        List<String[]> fields = new ArrayList<String[]>();
        
        fields.add(new String[]{"columnId", "varchar(100) primary key"});
        fields.add(new String[]{"name", "varchar(100)"});
        
        fields.add(new String[]{"tableId", "varchar(100) references MMFASTable(tableId)"});
        
        TestConnexion.create("MMFASColumn", fields);
    }
}
