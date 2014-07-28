<?xml version="1.0" encoding="UTF-8"?>
<ctl:package 
		xmlns:ctl="http://www.occamlab.com/ctl" 
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
		xmlns:dc="http://purl.org/dc/elements/1.1/" 
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
		xmlns:xi="http://www.w3.org/2001/XInclude" 
		xmlns:saxon="http://saxon.sf.net/" 
		xmlns:ctlp="http://www.occamlab.com/te/parsers" 
		xmlns:dct="http://purl.org/dc/terms/" 
		xmlns:xs="http://www.w3.org/2001/XMLSchema"
		xmlns:parsers="http://www.occamlab.com/te/parsers" 
		xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
		xmlns:sec="http://www.intecs.it/sec" 
		xmlns:xlink="http://www.w3.org/1999/xlink" 
		xmlns:csw="http://www.opengis.net/cat/csw/2.0.2" 
		xmlns:util="http://www.occamlab.com/te/util" 
		xmlns:ows="http://www.opengis.net/ows/2.0" 
		xmlns:dseo="http://www.opengis.net/dseo/1.0"
		xsi:schemaLocation="http://www.occamlab.com/ctl  ../apps/engine/resources/com/occamlab/te/schemas/ctl.xsd">

  <!-- CTL FILE METADATA
  Author(s): Sebastian ULRICH  , Astrium LTD (sebastian.ulrich@astrium.eads.net)
  Lineage: 2014/05/01 - ASTRIUM
  -->

	<ctl:test name="dseo:ATS_11.1.1_GET_Capabilities">
    <ctl:param name="sp.endpoint.HTTP.url">HTTP URL of GetCapabilities</ctl:param>
    <ctl:param name="cc.dseoschema_URL">DSEO Schema URL</ctl:param>
    <ctl:param name="cc.ows_schema_URL">OWS Schema URL</ctl:param>
    <ctl:param name="cc.messagedir">INPUT MESSAGES</ctl:param>    
		<ctl:assertion>This test to verify that he DSEO Server under test correctly supports the HTTP GET method on Capabilities resource.</ctl:assertion>
		<ctl:link title="11.1.1 GET Capabilities">http://www.opengis.net/spec/DSEO/1.0/conf/Core/GET_Capabilities</ctl:link>
    <ctl:code>
	
	<xsl:variable name="endpoint.withservice" select="concat($sp.endpoint.HTTP.url, '?service=dseo')"/>
    <ctl:message>Request : <xsl:copy-of select="$endpoint.withservice"/></ctl:message>
    
	<xsl:variable name="response">
        <ctl:request>
          <ctl:url>
            <xsl:copy-of select="$endpoint.withservice"/>
          </ctl:url>
          <ctl:method>get</ctl:method>
            <parsers:XMLValidatingParser>
                  <parsers:schema type="file">
                      <xsl:value-of select="$cc.dseoschema_URL"/>
                	</parsers:schema>
            </parsers:XMLValidatingParser>         
        </ctl:request>         
     </xsl:variable>       
     <xsl:if test="not ($response/*)">
          <ctl:message>ATS 11.1.1 validation failed</ctl:message>
          <ctl:fail/>
     </xsl:if>   	
	 
     <!-- #############################################################################################  -->
     <!-- #Create the variables that will be used to test the sections of the Capabilities resource   #  -->
 <!-- #############################################################################################  -->
		<xsl:variable name="profile" select="$response//dseo:Capabilities//ows:ServiceIdentification//ows:Profile"/>
	 
     <!-- #############################################################################################  -->
     <!-- #check the ows:OperationsMetadata element is filled-in with the list of supported operations# -->
     <!-- #############################################################################################  -->
	 
  		<xsl:if test="not($response//ows:OperationsMetadata/ows:Operation[@name='GetCapabilities']/ows:DCP/ows:HTTP/ows:Get)">
				<ctl:message>GetCapabilities operation not supported</ctl:message>
				<ctl:fail/>
			</xsl:if>
      <xsl:if test="not($response//ows:OperationsMetadata/ows:Operation[@name='GetProduct']/ows:DCP/ows:HTTP/ows:Get)">
				<ctl:message>GetProduct operation not supported</ctl:message>
				<ctl:fail/>
			</xsl:if>
     <!-- ################################################################################################################ -->
     <!-- #o	the Capabilities/ServiceIdentification/Profile element is set with: http://www.opengis.net/def/bp/dseo/1.0# -->
     <!-- ################################################################################################################ -->
      
			<xsl:message> <xsl:value-of select="$profile"/> </xsl:message>
	  
	  <xsl:if test="$profile != 'http://www.opengis.net/dseo/1.0'">
				<ctl:message>the Profile element is not set with the correct url</ctl:message>
				<ctl:fail/>
			</xsl:if>       
			
			
    </ctl:code>
	</ctl:test>
</ctl:package>
