/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.ips.dseo.ds.fileimport;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import net.eads.astrium.ips.dseo.ds.database.DSDBHandler;
import net.eads.astrium.ips.structures.WebFileInputStream;
import net.eads.astrium.ips.util.DateHandler;
import net.eads.astrium.ips.util.structures.DBFile;
import net.eads.astrium.ips.util.structures.FileAvailibility;
import net.eads.astrium.ips.util.structures.usermanagement.MMFASUser;

/**
 *
 * @author re-sulrich
 */
public class FolderImporter extends FileImporter {

    public FolderImporter(DSDBHandler dbHandler) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        super(dbHandler);
    }

    @Override
    boolean canImportFile(WebFileInputStream inputFile) {
        return false;
    }

    @Override
    boolean canImportFile(File inputFile) {
        return ((inputFile != null) && (inputFile.isDirectory()));
    }

    @Override
    public Map<String, DBFile> importFile(WebFileInputStream inputFile, String productFolderPath, String parentFileId, String use, MMFASUser user) 
            throws Exception {
        Map<String, DBFile> files = new HashMap<>();
        
        return files;
    }

    @Override
    public Map<String, DBFile> importFile(File inputFile, String productFolderPath, String parentFileId, String use, MMFASUser user) 
            throws Exception {
        Map<String, DBFile> files = new HashMap<>();
        Calendar creationTime = DateHandler.getCalendar();
        String fileName = inputFile.getName();
        String fileId = dbHandler.getFileLoader().saveFile(
                fileName, 
                null,                       //A directory is, in the database, filetype = 0|null
                creationTime.getTime(), 
                productFolderPath, 
                parentFileId,  
                use, 
//                dbHandler.getServiceId(),
                user.getUserId(), FileAvailibility.AVAILABLE);
        
        String outputFilePath = shareFolderPath + 
                DateHandler.formatDateToSimpleNumbersOnly(creationTime.getTime()) + File.separator + 
                fileId + File.separator + 
                fileName;
        
        DBFile file = new DBFile(
                fileId, 
                fileName, 
                creationTime.getTime(), 
                dbHandler.getFileLoader().getFileType(null), 
                null);
        
        files.put(fileName, file);
        
        new File(outputFilePath).getParentFile().mkdirs();

        Path in = Paths.get(inputFile.getPath());
        Path out = Paths.get(outputFilePath);

        Files.move(in, out, StandardCopyOption.ATOMIC_MOVE);
//        Files.copy(in, out, StandardCopyOption.COPY_ATTRIBUTES);
        
        return files;
    }
}
