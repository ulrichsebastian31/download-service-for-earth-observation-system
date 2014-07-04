/**
 * --------------------------------------------------------------------------------------------------------
 *   Project                                            :               DREAM
 * --------------------------------------------------------------------------------------------------------
 *   File Name                                          :               Populate.java
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.eads.astrium.dseo.dseodbinitialiser.TestConnexion.insert;

/**
 *
 * @author re-sulrich
 */
public class Populate {
    
    //Windows local
    private static final String dsDeployment = "1";
    
    
    //Linux deployments
//    private static final String dsDeployment = "8";
//    private static final String ips1Deployment = "3";
//    private static final String ips2Deployment = "4";
//    private static final String mipsDeployment = "2";
    
//    private static final String dsDMFolder = "/tmp/downloadmanager/";
    
    
    //The data about the Sentinel 1 mission has been collected on :
    // https://directory.eoportal.org/web/eoportal/satellite-missions/c-missions/copernicus-sentinel-1
    
    
    public static void populate() {
        
        try {
            addDeployments();
            addDownloadServer();
            
            addFileTypes();
            
            
        } catch (Exception ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void addDeployments() throws SQLException {
        
        String table = "ApplicationServer";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("name");
        fields.add("description");
        fields.add("serverBaseAddress");
        fields.add("mmfasDownloadManagerFolder");
        fields.add("mmfasDownloadManagerFolderRefreshTime");
        fields.add("shareFolderMountPath");
        
        //Windows local
        List<String> depl1 = new ArrayList<String>();
        depl1.add("'Deployment1'");
        depl1.add("''");
        depl1.add("'http://10.2.200.220:8080'");
//        depl1.add("'http://192.168.0.7:8080'");     //home
        depl1.add("'C:/Users/re-sulrich/.ips/downloadmanager/'");
        depl1.add("120");
        depl1.add("'C:/Users/re-sulrich/.ips/'");
        
        List<List<String>> values = new ArrayList<List<String>>();
        values.add(depl1);
        
        insert(
                table, 
                fields, 
                values);
    }
    
    public static void addDownloadServer() throws SQLException {
        
        String table = "DownloadServer";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("dsId");
        fields.add("name");
        fields.add("description");
        fields.add("asId");
        
        List<String> depl1 = new ArrayList<String>();
        depl1.add("'Download_Server_1'");
        depl1.add("'Download Server 1'");
        depl1.add("'A server that permits to upload and download products from-to the multi image processing server..'");
        depl1.add(dsDeployment);
        
        List<String> depl2 = new ArrayList<String>();
        depl2.add("'Download_Server_2'");
        depl2.add("'Download Server 2'");
        depl2.add("'A second server that permits to upload and download products from-to the multi image processing server..'");
        depl2.add(dsDeployment);
        
        List<List<String>> values = new ArrayList<List<String>>();
        values.add(depl1);
        values.add(depl2);
        
        insert(
                table, 
                fields, 
                values);
    }
    
    
    
    public static void addFileTypes() throws SQLException {
        
        String table = "FileType";
        
        List<String> fields = new ArrayList<String>();
        
        fields.add("name");
        fields.add("fileType");
        fields.add("extension");
        fields.add("fileDefaultName");
        
        List<String> depl1 = new ArrayList<String>();
        depl1.add("'PNG Image'");
        depl1.add("'image'");
        depl1.add("'png'");
        depl1.add("'img'");
        
        List<String> depl2 = new ArrayList<String>();
        depl2.add("'TIFF Image'");
        depl2.add("'image'");
        depl2.add("'tiff'");
        depl2.add("'img'");
        
        List<String> depl3 = new ArrayList<String>();
        depl3.add("'XML file'");
        depl3.add("'text'");
        depl3.add("'xml'");
        depl3.add("'new'");
        
        List<String> depl4 = new ArrayList<String>();
        depl4.add("'Text File'");
        depl4.add("'text'");
        depl4.add("'txt'");
        depl4.add("'new'");
        
        List<List<String>> values = new ArrayList<List<String>>();
        values.add(depl1);
        values.add(depl2);
        values.add(depl3);
        values.add(depl4);
        
        insert(
                table, 
                fields, 
                values);
    }
    
}
