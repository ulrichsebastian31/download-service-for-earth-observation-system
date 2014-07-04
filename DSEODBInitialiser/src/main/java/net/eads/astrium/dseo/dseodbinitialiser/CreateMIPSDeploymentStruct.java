/**
 * --------------------------------------------------------------------------------------------------------
 *   Project                                            :               DREAM
 * --------------------------------------------------------------------------------------------------------
 *   File Name                                          :               CreateMIPSDeploymentStruct.java
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author re-sulrich
 */
public class CreateMIPSDeploymentStruct {
    
    
    
    
    
    
    public static void testCreateApplicationServerTable() throws SQLException {
        
        List<String[]> fields = new ArrayList<String[]>();
        
        fields.add(new String[]{"asId", "serial primary key"});
        fields.add(new String[]{"name", "varchar(100)"});
        fields.add(new String[]{"description", "varchar(1024)"});
        fields.add(new String[]{"serverBaseAddress", "varchar(1024)"});
        
        fields.add(new String[]{"mmfasDownloadManagerFolder", "varchar(1024)"});
        fields.add(new String[]{"mmfasDownloadManagerFolderRefreshTime", "integer"});
        
        fields.add(new String[]{"shareFolderMountPath", "varchar(1024)"});
        
        TestConnexion.create("ApplicationServer", fields);
    }
    
    public static void testCreateDownloadServerTable() throws SQLException {
        
        List<String[]> fields = new ArrayList<String[]>();
        
        fields.add(new String[]{"dsId", "varchar(20) primary key"});
        fields.add(new String[]{"name", "varchar(100)"});
        fields.add(new String[]{"description", "varchar(1024)"});
        
        fields.add(new String[]{"asId", "integer references ApplicationServer(asId)"});
        
        TestConnexion.create("DownloadServer", fields);
    }
    
}
