/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.eads.astrium.dseo.ds.rs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author re-sulrich
 */
public class FileStreamOutput implements StreamingOutput {

    private File image;

    public FileStreamOutput(File file) {
        this.image = file;
    }

    @Override
    public void write(OutputStream output)
            throws IOException, WebApplicationException {
        FileInputStream input = new FileInputStream(image);
        try {
            int bytes;
            while ((bytes = input.read()) != -1) {
                output.write(bytes);
            }
        } catch (Exception e) {
            throw new WebApplicationException(e);
        } finally {
            if (output != null) output.close();
            if (input != null) input.close();
        }
    }
}
