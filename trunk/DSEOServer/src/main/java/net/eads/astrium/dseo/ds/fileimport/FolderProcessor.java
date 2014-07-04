/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.dseo.ds.fileimport;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.eads.astrium.dseo.util.structures.DBFile;

/**
 *
 * @author re-sulrich
 */
public interface FolderProcessor {
    
    /**
     * 
     * @return true if the file structure can be processed by this FolderProcessor, false otherwise
     */
    public abstract Map<String, DBFile> processFolder(File folder) throws Exception;
    public abstract Map<String, DBFile> processFile(File file) throws Exception;
}
