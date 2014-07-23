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
public class CreateInsertAllColumnsCode {

    public static void main(String[] args) throws SQLException {
        
        TestConnexionParameter.setUrl("10.2.200.115:5432");
        TestConnexionParameter.setUser("postgres");
        TestConnexionParameter.setPass("password");
        
        TestConnexionParameter.setAdminDatabase("AdminDatabase");
        TestConnexion.conn = TestConnexion.createAdminDBConnexion();
        
//        getTablesInsertCode();
        getColumnsInsertCode();
    }
    
    
    
    public static List<String> getTablesNames(String schema) throws SQLException {
        
        List<String> list = new ArrayList<>();
        
        List<String> fields = new ArrayList<>();

        fields.add("table_name");
        
        //Filtering the DB results by app server
        List<String> conditions = new ArrayList<>();
        
        conditions.add("table_schema='"+schema+"'");
        
        List<List<String>> result = TestConnexion.select(fields, "information_schema.tables", conditions);
        
        if (result != null && !result.isEmpty()) {
            for (List<String> list1 : result) {
                list.add(list1.get(0));
            }
        }
        
        return list;
    }
    
    
    public static void getTablesInsertCode() throws SQLException {
        
        List<String> tables = getTablesNames("mipsdatabase");
        for (String tableName : tables) {
            
            String gen = "\n" +
    "        table1 = new ArrayList<String>();\n" +
    "        table1.add(\"'mipsdatabase."+tableName+"'\");\n" +
    "        table1.add(\"''\");\n" +
    "        table1.add(\"'\"+ DateHandler.formatDate(Calendar.getInstance().getTime())+\"'\");\n" +
    "        values.add(table1);\n" +
    "        ";
            
            System.out.println("" + gen);
        }
        
        
        
    }
    
    
    public static void getColumnsInsertCode() throws SQLException {
        List<String> tables = getTablesNamesFromAdminDB();
        
        
        String insertForAllTablesCode = "    public static void insertAllColumns() throws SQLException {\r\n"
                + "        \r\n"
                + "\n" +
"        TestConnexionParameter.setUrl(\"10.2.200.115:5432\");\n" +
"        TestConnexionParameter.setUser(\"postgres\");\n" +
"        TestConnexionParameter.setPass(\"password\");\n" +
"        \n" +
"        TestConnexionParameter.setAdminDatabase(\"AdminDatabase\");\n" +
"        TestConnexion.conn = TestConnexion.createAdminDBConnexion();\n" +
"        ";
        
        for (String ta : tables) {
            
            //Discard schema
            String table = ta;
            String schema = "";
            String[] t = ta.split("\\.");
            if (t.length > 0) {
                table = t[1];
                schema = t[0];
            }
            
//            System.out.println("" + table);

            String functionTitle = "insert"+table.substring(0, 1).toUpperCase() + table.substring(1) + "Columns";
            
            insertForAllTablesCode += "        " + functionTitle + "();\r\n";
            
            String columnInsertCode = "    public static void "+functionTitle+"() throws SQLException {\r\n" + 
                    "        String table = \"MMFASColumn\";\n" +
                    "        \n" +
                    "        List<String> fields = new ArrayList<String>();\n" +
                    "        \n" +
                    "        fields.add(\"columnId\");\n" +
                    "        fields.add(\"name\");\n" +
                    "        fields.add(\"tableId\");\n" +
                    "        \n" +
                    "        List<List<String>> values = new ArrayList<>();\n" +
                    "        List<String> column1;\r\n\r\n";
            
            List<String> columns = getColumnsNames(table);
            
            for (String column : columns) {
                
                columnInsertCode += "" +
        "        column1 = new ArrayList<>();\r\n" +
        "        column1.add(\"'"+ schema + "." + table + "." + column + "'\");\r\n" +
        "        column1.add(\"''\");\r\n" +
        "        column1.add(\"'"+schema + "." + table+"'\");\r\n" +
        "        values.add(column1);\r\n\r\n";

            }
            
            columnInsertCode += "" + 
                    "        TestConnexion.insert(\r\n" +
                    "                table, \r\n" +
                    "                fields, \r\n" +
                    "                values);\r\n" + 
                    "    }";
            
            System.out.println("" + columnInsertCode + "\r\n\r\n\r\n");
            
        }
    
        insertForAllTablesCode += "    }\r\n";
        System.out.println("" + insertForAllTablesCode);
    }
    
    public static List<String> getColumnsNames(String table) throws SQLException {
        
        List<String> list = new ArrayList<>();
        
        List<String> fields = new ArrayList<>();

        fields.add("column_name");
        
        //Filtering the DB results by app server
        List<String> conditions = new ArrayList<>();
        
        conditions.add("table_name = '"+table+"'");
        
        List<List<String>> result = TestConnexion.select(fields, "information_schema.columns", conditions);
        
        if (result != null && !result.isEmpty()) {
            for (List<String> list1 : result) {
                list.add(list1.get(0));
            }
        }
        
//        System.out.println("" + list.size());
        
        return list;
    }
    
    public static List<String> getTablesNamesFromAdminDB() throws SQLException {
        
        List<String> list = new ArrayList<>();
        
        List<String> fields = new ArrayList<>();

        fields.add("tableId");
        
        //Filtering the DB results by app server
        List<String> conditions = new ArrayList<>();
        
        List<List<String>> result = TestConnexion.select(fields, "MMFASTable", conditions);
        
        if (result != null && !result.isEmpty()) {
            for (List<String> list1 : result) {
                list.add(list1.get(0));
            }
        }
        
        return list;
    }
}
