/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.ips.dseo.ds.fileimport;

import edu.emory.mathcs.backport.java.util.Arrays;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.eads.astrium.ips.dseo.ds.database.DSDBHandler;
import net.eads.astrium.ips.structures.WebFileInputStream;
import net.eads.astrium.ips.util.DateHandler;
import net.eads.astrium.ips.util.FormatHandler;
import net.eads.astrium.ips.util.logging.DreamLogger;
import net.eads.astrium.ips.util.structures.DBFile;
import net.eads.astrium.ips.util.structures.FileAvailibility;
import net.eads.astrium.ips.util.structures.usermanagement.MMFASUser;

/**
 *
 * @author re-sulrich
 */
public class ImageImporter extends FileImporter {

    public static final List<String> imageExtensions = Arrays.asList(new String[]{
        "tif", "tiff", "png", "jpg", "bmp"});
    public static final List<String> imageMimes = Arrays.asList(new String[]{
        "image/tif", "image/tiff", "image/png", "image/jpg", "image/bmp"});
    
    
    public ImageImporter(DSDBHandler dbHandler) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        super(dbHandler);
    }

    @Override
    boolean canImportFile(WebFileInputStream inputFile) {
        return imageMimes.contains(inputFile.getMimeType());
    }

    @Override
    boolean canImportFile(File inputFile) {
        String extension = inputFile.getName().substring(inputFile.getName().lastIndexOf(".") + 1);
        return imageExtensions.contains(extension);
    }

    @Override
    public Map<String, DBFile> importFile(WebFileInputStream inputFile, String productFolderPath, String parentFileId, String use, MMFASUser user) throws SQLException, IOException {
        Map<String, DBFile> files = new HashMap<>();
        
        String fileName = inputFile.getFileName().substring(0, inputFile.getFileName().lastIndexOf("."));
        String extension = FormatHandler.mimeTypeToExtension(inputFile.getMimeType());
        
        Calendar creationTime = DateHandler.getCalendar();
        String fileTypeId = this.dbHandler.getFileLoader().getFileTypeId(extension);
        if (fileTypeId == null) {
            throw new IOException("Image file type " + extension + " is not supported.");
        }
        else {
            String userId = null;
            if (user != null) {
                userId = user.getUserId();
            }
            
            String fileId = dbHandler.getFileLoader().saveFile(
                           fileName, 
                           fileTypeId, 
                           creationTime.getTime(), 
                           productFolderPath, 
                           parentFileId,  
                           use, 
//                           dbHandler.getServiceId(),
                           userId, FileAvailibility.AVAILABLE);
            
            DBFile file = new DBFile(
                           fileId, fileName, creationTime.getTime(), 
                           dbHandler.getFileLoader().getFileType(fileTypeId), //Get the file type from ID, 0 = 
                           extension);
            
            files.put(inputFile.getFileName(), file);
            

            InputStream fileIS = inputFile.getContent();
            
            if (fileIS != null) {
                //Save the image in a sub folder named from the ID returned by the database
                String outputFilePath =  getDSSystemFilePath(file);

                //Create the folders that are not existing
                new File(outputFilePath).getParentFile().mkdirs();

                //Create the image file
                File fic = new File(outputFilePath);
                fic.createNewFile();
                FileOutputStream output = new FileOutputStream(fic);

                byte[] b = new byte[fileIS.available()];
                fileIS.read(b);
                output.write(b);

                fileIS.close();
                output.flush();
                output.close();
            }
        }
        
        return files;
    }

    @Override
    public Map<String, DBFile> importFile(File inputFile, String productFolderPath, String parentFileId, String use, MMFASUser user) throws SQLException, IOException {
        Map<String, DBFile> files = new HashMap<>();
        
        String fileName = inputFile.getName().substring(0, inputFile.getName().lastIndexOf("."));
        String extension = inputFile.getName().substring(inputFile.getName().lastIndexOf(".") + 1);
        
        Calendar creationTime = DateHandler.getCalendar();
        String fileTypeId = this.dbHandler.getFileLoader().getFileTypeId(extension);
        if (fileTypeId == null) {
            throw new IOException("Image file type " + extension + " is not supported.");
        }
        else {
            String userId = null;
            if (user != null) {
                userId = user.getUserId();
            }
            
            String fileId = dbHandler.getFileLoader().saveFile(
                           fileName, 
                           fileTypeId, 
                           creationTime.getTime(), 
                           productFolderPath, 
                           parentFileId, 
                           use, 
//                           dbHandler.getServiceId(),
                           userId, FileAvailibility.IN_PROGRESS);
            
            DBFile file = new DBFile(
                           fileId, fileName, creationTime.getTime(), 
                           dbHandler.getFileLoader().getFileType(fileTypeId), //Get the file type from ID, 0 = 
                           extension);
            
            files.put(inputFile.getName(), file);

            String outputFilePath = getDSSystemFilePath(file);
            
            new File(outputFilePath).getParentFile().mkdirs();
            
            
            Path in = Paths.get(inputFile.getPath());
            Path out = Paths.get(outputFilePath);

//            Files.move(in, out, StandardCopyOption.ATOMIC_MOVE);
//            Files.copy(in, out, StandardCopyOption.COPY_ATTRIBUTES);
            
            Files.copy(
                    in, out,
                    StandardCopyOption.COPY_ATTRIBUTES
                );

            Files.delete(in);

            if (use.equalsIgnoreCase("product"))
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException ex) {
                    DreamLogger.getThreadLogger(Thread.currentThread().getId()).log(Level.SEVERE, null, ex);
                }
            
            dbHandler.getFileLoader().setFileAvailibility(fileId, FileAvailibility.AVAILABLE);
        }
        
        return files;
    }
}
