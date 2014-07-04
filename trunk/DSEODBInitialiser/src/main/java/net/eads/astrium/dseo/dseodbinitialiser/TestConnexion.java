/**
 * --------------------------------------------------------------------------------------------------------
 *   Project                                            :               DREAM
 * --------------------------------------------------------------------------------------------------------
 *   File Name                                          :               TestConnexion.java
 *   File Type                                          :               Source Code
 *   Description                                        :                *
 * --------------------------------------------------------------------------------------------------------
 *
 * =================================================================
 *             (coffee) COPYRIGHT EADS ASTRIUM LIMITED 2013. All Rights Reserved
 *             This software is supplied by EADS Astrium Limited on the express terms
 *             that it is to be treated as confidential and that it may not be copied,
 *             used or disclosed to others for any purpose except as authorised in
 *             writing by this Company.
 * --------------------------------------------------------------------------------------------------------
 */package net.eads.astrium.dseo.dseodbinitialiser;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestConnexion {
    public static Connection conn;

    public static Connection createConnexion() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver O.K.");
            
//            String url = "jdbc:postgresql://10.2.200.247:5432/DSEODatabase";
//            String url = "jdbc:postgresql://192.168.0.20:5432/DSEODatabase";
            
//            String user = "opensourcedbms";
//            String passwd = "opensourcedbms";
            
            System.out.println("jdbc:postgresql://" + TestConnexionParameter.getUrl() + "/DSEODatabase"
//                    + "/" + TestConnexionParameter.getDSEODatabase()
                );
            
            String url = "jdbc:postgresql://" + TestConnexionParameter.getUrl() + "/DSEODatabase" 
//                    + "/" + TestConnexionParameter.getDSEODatabase()
                ;
            String user = TestConnexionParameter.getUser();
            String passwd = TestConnexionParameter.getPass();
            
            connection = DriverManager.getConnection(url, user,passwd);
            System.out.println("Connection started !");
            
            //Setting schema
            Statement stat = connection.createStatement();
            try {
                stat.executeQuery("SET search_path TO " + TestConnexionParameter.getDSEODatabase()+ ";");
            } catch (SQLException e) {
                if (e.getMessage().contains("No results were returned by the query")) {
                    System.out.println("No results returned");
                }
                else {
                    throw e;
                }
            }
            System.out.println("Selecting schema : " + TestConnexionParameter.getDSEODatabase());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return connection;
    }
    
    public static Connection createUserConnexion() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver O.K.");
            
//            String url = "jdbc:postgresql://10.2.200.247:5432/DSEODatabase";
//            String url = "jdbc:postgresql://192.168.0.20:5432/DSEODatabase";
            
//            String user = "opensourcedbms";
//            String passwd = "opensourcedbms";
            
            System.out.println("jdbc:postgresql://" + TestConnexionParameter.getUrl() + "/DSEODatabase"
//                    + "/" + TestConnexionParameter.getDSEODatabase()
                );
            
            String url = "jdbc:postgresql://" + TestConnexionParameter.getUrl() + "/DSEODatabase" 
//                    + "/" + TestConnexionParameter.getDSEODatabase()
                ;
            String user = TestConnexionParameter.getUser();
            String passwd = TestConnexionParameter.getPass();
            
            connection = DriverManager.getConnection(url, user,passwd);
            System.out.println("Connection started !");
            
            //Setting schema
            Statement stat = connection.createStatement();
            try {
                stat.executeQuery("SET search_path TO " + TestConnexionParameter.getUserDatabase()+ ";");
            } catch (SQLException e) {
                if (e.getMessage().contains("No results were returned by the query")) {
                    System.out.println("No results returned");
                }
                else {
                    throw e;
                }
            }
            System.out.println("Selecting schema : " + TestConnexionParameter.getUserDatabase());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return connection;
    }
    
    
    public static void testDropAndCreateSchema(String schema) throws SQLException {
        
        Statement state = null;
        
        try {
            state = conn.createStatement();

            String query = "drop schema " + schema + " cascade;";

            System.out.println("" + query);

            state.executeQuery(query);
            
        } catch (SQLException e) {
            if (e.getMessage().contains("No results were returned by the query"))
            {
                System.out.println("No results returned");
            }
            else
            {
                System.out.println("Mess : " + e.getMessage());
            }
        }
        //Separe drop and create so drop does not fail the create..
        try {
            state = conn.createStatement();

            String query = "create schema "+schema+";";

            System.out.println("" + query);

            state.executeQuery(query);
        }catch (SQLException e) {
            if (e.getMessage().contains("No results were returned by the query"))
            {
                System.out.println("No results returned");
            }
            else
            {
                System.out.println("Mess : " + e.getMessage());
                throw e;
            }
        }
        state.close();
        
        
    }
    
    public static String[] baseTables = {
        "LNK_Task_Sensor",
        "LNK_Task_Station",
        "LNK_Sensor_IM",
        "Unavailibility",
        "SatellitePlatform",
        "FeasibilityAnalysis",
        "Task",
        "Status",
        "Sensor",
        "Segment",
        "GroundStation",
    };
    
    public static String[] reqTables = {
        "LNK_IM_PM",
        "PolarisationMode",
        "OptSensorCharacteristics",
        "SarSensorCharacteristics",
        "OptIMCharacteristics",
        "SarIMCharacteristics",
        "InstrumentMode",
        "Request",
    };
    
    public static void deleteTable(String table) throws SQLException {
        
        Statement state = null;
        try {
            state = conn.createStatement();

            String query = "DROP TABLE " + table + " CASCADE;";

            System.out.println("" + query);

            state.executeQuery(query);
        }catch (SQLException e) {
            if (e.getMessage().contains("No results were returned by the query"))
            {
                System.out.println("No results returned");
            }
            else
            {
                System.out.println("Mess : " + e.getMessage());
                throw e;
            }
        }
        state.close();
    }
    
    public static void dropEnum(String name) throws SQLException {
        
        Statement state = conn.createStatement();

        String query = "DROP TYPE " + name + " CASCADE;";

        System.out.println("" + query);
        
        state.executeQuery(query);
    }
    
    public static void createEnum(String name, List<String> values) throws SQLException {
        try {
            Statement state = conn.createStatement();

            String query = "CREATE TYPE " + name + " AS ENUM (";
            for (Object value : values) {
                query += "" + value + ", ";
            }
            query = query.substring(0, query.lastIndexOf(","));
            query += ");";

            System.out.println("" + query);

            state.executeQuery(query);
         
        }catch (SQLException e) {
            if (e.getMessage().contains("No results were returned by the query"))
            {
                System.out.println("No results returned");
            }
            else
            {
                System.out.println("Mess : " + e.getMessage());
                throw e;
            }
        }
    }
    
    public static void create(String tableName, List<String[]> fields) throws SQLException {
        try {
            //Create the statement object
            Statement state = conn.createStatement();


            String query = "CREATE TABLE " + tableName + " (";

            for (String[] field : fields) {

                query += "" + field[0] + " " + field[1] + ", ";
            }
            query = query.substring(0, query.lastIndexOf(","));
            query += ");";

            System.out.println("" + query);
            state.executeQuery(query);
            
        }catch (SQLException e) {
            if (e.getMessage().contains("No results were returned by the query"))
            {
                System.out.println("No results returned");
            }
            else
            {
                System.out.println("Mess : " + e.getMessage());
                throw e;
            }
        }
    }
    
    public static void addColumn(String table, String column, String columnType) throws SQLException {
        try {
            //Create the statement object
            Statement state = conn.createStatement();

            String query = "ALTER TABLE " + table + " ADD COLUMN " + column + " " + columnType + ";";

            state.executeQuery(query);
        
        }catch (SQLException e) {
            if (e.getMessage().contains("No results were returned by the query"))
            {
                System.out.println("No results returned");
            }
            else
            {
                System.out.println("Mess : " + e.getMessage());
                throw e;
            }
        }
    }
    
    public static void addUniqueConstraint(String table, List<String> fields) throws SQLException {
        try {
            //Create the statement object
            Statement state = conn.createStatement();
            
            String cn = "unique_" + table.toLowerCase();

            String fieldsQuery = "";
            for (String string : fields) {
                fieldsQuery += string + ",";
                cn += "_" + string;
            }
            fieldsQuery = fieldsQuery.substring(0, fieldsQuery.length() - 1);
            
            String query = "ALTER TABLE " + table + " ADD CONSTRAINT " + cn + " UNIQUE (" + fieldsQuery + ");";
            
            System.out.println("" + query);

            state.executeQuery(query);
        
        }catch (SQLException e) {
            if (e.getMessage().contains("No results were returned by the query"))
            {
                System.out.println("No results returned");
            }
            else
            {
                System.out.println("Mess : " + e.getMessage());
                throw e;
            }
        }
    }
    
    public static void addForeignKey(String table, String column, String refTable, String refColumn) throws SQLException {
        
        try {
            select(Arrays.asList(column), table, null);
        }catch (SQLException e) {
            System.out.println("" + e.getMessage());
            if (e.getMessage().contains("does not exist"))
            {
                addColumn(table, column, "integer");
            }
        }
        
        
        try {
            //Create the statement object
            Statement state = conn.createStatement();

            String query = "ALTER TABLE " + table +
                    " ADD  FOREIGN KEY(" + column + ") " +
                    "REFERENCES "+refTable+"("+refColumn+")";

            state.executeQuery(query);
        
        }catch (SQLException e) {
            if (e.getMessage().contains("No results were returned by the query"))
            {
                System.out.println("No results returned");
            }
            else
            {
                System.out.println("Mess : " + e.getMessage());
                throw e;
            }
        }
    }
    
    public static List<List<String>> select(List<String> fields, String table, List<String> conditions) throws SQLException {
        //Create the statement object
        Statement state = conn.createStatement();

        
        String query = "SELECT ";
        if (fields == null)
        {
            query += "*, ";
        }
        else
        {
            for (String field : fields) {

                query += "" + field + ", ";
            }
        }
        
        query = query.substring(0, query.lastIndexOf(","));
        query += " FROM " + table;
        
        if (conditions != null && conditions.size() > 0)
        {
            query += " WHERE " + conditions.get(0);
            for (int i = 1; i < conditions.size(); i++) {
                
                query += " AND " + conditions.get(i);
            }
        }
        
        query += ";";
        
//        System.out.println("" + query);
        
        //ResultSet object contains the SQL request’s result
        ResultSet result = state.executeQuery(query);

        //Get metaData
        ResultSetMetaData resultMeta = result.getMetaData();
//        System.out.println("\n**********************************");

        //Display columns
//        for(int i = 1; i <= resultMeta.getColumnCount(); i++)
//            System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");

//        System.out.println("\n**********************************");

        List<List<String>> results = new ArrayList<>();
        
        while(result.next()){
            
                List<String> elements = new ArrayList<String>();
                for(int i = 1; i <= resultMeta.getColumnCount(); i++)
                {
//                    System.out.print("\t" + result.getObject(i).toString() + "\t |");
                    elements.add(result.getObject(i).toString());
                }
                results.add(elements);
/*			
                System.out.print("\t" + result.getString("name") + "\t |");
                System.out.print("\t" + result.getString("dept") + "\t |");
                System.out.print("\t" + result.getString("jobtitle") + "\t |");*/

//            System.out.println("\n---------------------------------");
        }
        result.close();
        state.close();
        
        return results;
    }
    
    public static void delete(String table, String column, List<String> values) throws SQLException {
        
        Statement state = null;
        try {
            //Create the statement object
            state = conn.createStatement();

            String query = "DELETE FROM " + table;

            if (values != null && values.size() > 0)
            {
                query += " WHERE";
                for (String id : values) {

                    query += " " + column + " = " + id + " OR";
                }
                query = query.substring(0, query.lastIndexOf("OR"));
            }

            query += ";";
            System.out.println("" + query);

            state.executeQuery(query);

        
        }catch (SQLException e) {
            if (e.getMessage().contains("No results were returned by the query"))
            {
                System.out.println("No results returned");
            }
            else
            {
                System.out.println("Mess : " + e.getMessage());
                throw e;
            }
        }
        state.close();
    }
    
    public static void insert(
            String table, 
            List<String> fields, 
            List<List<String>> entries
        ) throws SQLException {
        try {
            //Create the statement object
            Statement state = conn.createStatement();

            String query = "INSERT INTO " + table 
                    + " (";

            for (String field : fields) {
                
                query += "" + field + ",";
            }
            query = query.substring(0, query.lastIndexOf(","));
            query += ") VALUES ";
            
            for (List<String> entry : entries) {
                
                query += "(";
                for (String value : entry)
                {
                    query += "" + value + ",";
                }
                query = query.substring(0, query.lastIndexOf(","));
                query += "),";
            }
                query = query.substring(0, query.lastIndexOf(","));
            
            query += ";";
            
            System.out.println("" + query);
            state.executeQuery(query);
        
        }catch (SQLException e) {
            if (e.getMessage().contains("No results were returned by the query"))
            {
                System.out.println("No results returned");
            }
            else
            {
                System.out.println("Mess : " + e.getMessage());
                throw e;
            }
        }
    }
    
    public static boolean insertFile
			(String table, String idColumn, String fileColumn, 
                        String id, InputStream fileContent, long fileSize) 
			throws IOException, SQLException {
        
        boolean insere = false;
        
        try 
        {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE " + table + " SET " + fileColumn + " = '?' WHERE " + idColumn + " = '?';");

            try 
            {
                ps.setBinaryStream(1, fileContent, (int)fileSize);
                ps.setString(2, id);
                ps.executeUpdate();
		
                insere = true;
            }
            finally 
            {
              ps.close();
            }
        } 
        finally 
        {
                fileContent.close();
        }

        return insere;
    }
    
    
}
