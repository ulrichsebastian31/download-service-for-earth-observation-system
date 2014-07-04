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
public class CreateFileStruct {
    
    
    
    public static void testCreateFileTable() throws SQLException {
        
        List<String[]> fields = new ArrayList<String[]>();
        
        fields.add(new String[]{"fileId", "serial primary key"});
        fields.add(new String[]{"name", "varchar(200)"});
        fields.add(new String[]{"creationTime", "timestamp without time zone"});
        fields.add(new String[]{"modificationTime", "timestamp without time zone"});
        
        //Only filled in for main files (main image)
        //The type of product when we want to export that product
        fields.add(new String[]{"productType", "varchar(100)"});
        
        //The folder path of the file within the product it belongs to
        //E.g. in S1 products, the image is in folder measurement
        //     and the metadata file is in folder annotation
        //Used for exporting products
        fields.add(new String[]{"productPath", "varchar(200)"});
        //The parent file. Only filled in for products subfiles, e.g. image metadata files
        fields.add(new String[]{"parent", "integer references File(fileId)"});
        //Type of file with extension, e.g. PNG image, TIFF image, XML file 
        fields.add(new String[]{"fileTypeId", "integer references FileType(fileTypeId)"});
        
        fields.add(new String[]{"use", "varchar(200)"});//product, preview, metadata, ...
        fields.add(new String[]{"availibility", "varchar(11)"});//AVAILABLE, IN_PROGRESS, FAILED
        
        //Geoserver where the image can be downloaded (WMS or direct download)
        fields.add(new String[]{"dsId", "varchar(20) references DownloadServer(dsId)"});
        fields.add(new String[]{"userId", "varchar(32) references UserDatabase.MMFASUser(userId)"});
        
        TestConnexion.create("File", fields);
    }
    
    
    public static void testCreateFileTypeTable() throws SQLException {
        
        List<String[]> fields = new ArrayList<String[]>();
        
        fields.add(new String[]{"fileTypeId", "serial primary key"});
        fields.add(new String[]{"name", "varchar(200)"});//PNG Image, XML description document, ...
        fields.add(new String[]{"fileType", "varchar(200)"});//image, text, ...
        fields.add(new String[]{"extension", "varchar(5)"});//png, xml, txt, ...
        fields.add(new String[]{"fileDefaultName", "varchar(100)"});//img(.png), description(.xml)
        
        TestConnexion.create("FileType", fields);
    }
    
}
