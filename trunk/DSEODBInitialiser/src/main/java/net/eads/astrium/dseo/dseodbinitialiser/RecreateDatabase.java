/**
 * --------------------------------------------------------------------------------------------------------
 *   Project                                            :               DREAM
 * --------------------------------------------------------------------------------------------------------
 *   File Name                                          :               RecreateDatabase.java
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
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eads.astrium.dseo.dseodbinitialiser;

import net.eads.astrium.dseo.dseodbinitialiser.admin.CreateAdminDatabase;
import net.eads.astrium.dseo.dseodbinitialiser.admin.InsertColumnsWithNames;
import net.eads.astrium.dseo.dseodbinitialiser.admin.InsertTablesWithNames;
import net.eads.astrium.dseo.dseodbinitialiser.usermanagement.CreateUserStruct;
import net.eads.astrium.dseo.util.logging.SysoRedirect;

/**
 *
 * @author re-sulrich
 */
public class RecreateDatabase {
    
   public static void main(String[] args) throws Exception {
        
       

        SysoRedirect.redirectSysoToFiles("dbinit", "dbinit");
        
//        System.out.println("dsafseagd");
//        TestConnexionParameter.setUrl("127.0.0.1:5432");
//        TestConnexionParameter.setUser("postgres");
//        TestConnexionParameter.setPass("postgres");
        
        TestConnexionParameter.setUrl(args[0]);
        TestConnexionParameter.setUser(args[1]);
        TestConnexionParameter.setPass(args[2]);
        
        String toBeDone = null;
        if (args.length < 4 || args[3] == null) {
            toBeDone = "All";
        }
        else {
            toBeDone = args[3];
        }

        System.out.println("Loading : " + toBeDone);
        
       switch (toBeDone) {
           case "All":
           case "UserDatabase":
               TestConnexionParameter.setUserDatabase("UserDatabase");
               TestConnexion.conn = TestConnexion.createUserConnexion();
               TestConnexion.testDropAndCreateSchema("UserDatabase");
               CreateUserStruct.createUserStruct();
               new net.eads.astrium.dseo.dseodbinitialiser.usermanagement.TestInsertUpdate().test();
           if (toBeDone.equals("UserDatabase")) break;
               
           case "DSEODatabase":
               TestConnexionParameter.setDSEODatabase("DSEODatabase");
               TestConnexion.conn = TestConnexion.createConnexion();
               TestConnexion.testDropAndCreateSchema("DSEODatabase");
               CreateStruct.createMain();
               Populate.populate();
//               AddFiles.testInsertS1AFiles();
//               AddFiles.testInsertS2AFiles();
//               AddFiles.testInsertS1BFiles();
//               AddFiles.testInsertS2BFiles();
           
           //If only DSEODatabase is required, exit, else do DownloadManagerDatabase
           if (toBeDone.equals("DSEODatabase")) break;
               
           case "AdminDatabase":
               TestConnexionParameter.setAdminDatabase("AdminDatabase");
               TestConnexion.conn = TestConnexion.createAdminDBConnexion();
               CreateAdminDatabase.createAdminTables();

                InsertTablesWithNames.insertTables();
                InsertColumnsWithNames.insertAllColumns();
           break;
           default:
               System.out.println("Parameter 4 (Database name) does not match one of : DSEODatabase,UserDatabase");
            break;
       }
    }
}
