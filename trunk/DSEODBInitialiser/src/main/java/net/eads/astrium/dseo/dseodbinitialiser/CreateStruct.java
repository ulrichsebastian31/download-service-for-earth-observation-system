/**
 * --------------------------------------------------------------------------------------------------------
 *   Project                                            :               DREAM
 * --------------------------------------------------------------------------------------------------------
 *   File Name                                          :               CreateStruct.java
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
import java.util.Arrays;

/**
 *
 * @author re-sulrich
 */
public class CreateStruct {
    
    public static void createMain() throws SQLException {
        
        
        
        System.out.println("Begin");
        
        CreateMIPSDeploymentStruct.testCreateApplicationServerTable();
        CreateMIPSDeploymentStruct.testCreateDownloadServerTable();
        
        CreateFileStruct.testCreateFileTypeTable();
        
        CreateFileStruct.testCreateFileTable();
    }
}
