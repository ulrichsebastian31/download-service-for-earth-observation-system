/**
 * --------------------------------------------------------------------------------------------------------
 *   Project                                            :               DREAM
 * --------------------------------------------------------------------------------------------------------
 *   File Name                                          :               DreamOWSException.java
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
package net.eads.astrium.ips.dseo.ds.dseo;

import javax.xml.ws.WebFault;
import net.eads.astrium.ips.exceptions.DreamOWSException;
import net.opengis.ows.x20.ExceptionReportDocument;
import net.opengis.ows.x20.ExceptionType;

/**
 *
 * @author re-sulrich
 */
@WebFault(name = "Exception", targetNamespace = "http://www.opengis.net/ows/2.0")
public class DSEOOWSException extends Exception {
    
    protected static final long serialVersionUID = -6279041538977056569L;
    protected ExceptionReportDocument exception;
    
    public DSEOOWSException(DreamOWSException e) {
        super(e.getMessage(), e.getCause());
        
        this.exception = ExceptionReportDocument.Factory.newInstance();
        ExceptionType exc = this.exception.addNewExceptionReport().addNewException();
        this.exception.getExceptionReport().setVersion("2.0.0");
        
        net.opengis.ows.x11.ExceptionType eExc = e.getFaultInfo().getExceptionReport().getExceptionArray(0);
        if (eExc != null) {
            exc.setExceptionCode(eExc.getExceptionCode());
            for (int i = 0; i < eExc.getExceptionTextArray().length; i++) {
                String string = eExc.getExceptionTextArray(i);
                exc.addExceptionText(string);
            }
        }
    }
    
    public ExceptionReportDocument getFaultInfo() {
        return this.exception;
    }
}
