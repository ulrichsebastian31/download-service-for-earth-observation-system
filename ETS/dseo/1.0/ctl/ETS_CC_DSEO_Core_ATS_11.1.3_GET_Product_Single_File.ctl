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
	<ctl:test name="dseo:ATS_11.1.3_GET_Single_File_Product">
		<ctl:param name="sp.endpoint.HTTP.url">HTTP Url of the capabilities resource</ctl:param>
		<ctl:param name="cc.dseoschema_URL">HTTP Url of the DSEO XSD schema</ctl:param>
		<ctl:param name="cc.ows_schema_URL">HTTP Url of the OWS XSD schema</ctl:param>
		<ctl:param name="cc.messagedir">INPUT MESSAGES</ctl:param>
		<ctl:param name="cc.filepath">The path of a file to be tested (download)</ctl:param>
		<ctl:param name="cc.filemime">The mime type of the file to be tested (direct download)</ctl:param>
		<ctl:assertion>To verify whether the DSEO Server under test provides description of a given sensor.</ctl:assertion>
		<ctl:link title="11.1.3 Get Single file Product">http://www.opengis.net/spec/DSEO/1.0/conf/Core/GET_Sensor_Description</ctl:link>
		<ctl:code>
			
			<xsl:variable name="endpoint.description" select="concat($sp.endpoint.HTTP.url, $cc.filepath)"/>
			<xsl:message> <xsl:copy-of select="$endpoint.description"/> </xsl:message>
			<xsl:variable name="response">
				<ctl:request>
					<ctl:url>
						<xsl:copy-of select="$endpoint.description"/>
					</ctl:url>
					<ctl:method>get</ctl:method>
					
					<parsers:HTTPParser>
					  <parsers:parse>
						<parsers:NullParser/>
					  </parsers:parse>
					</parsers:HTTPParser>
				</ctl:request>
			</xsl:variable>
			
			
			<xsl:variable name="mime" select="$response/response/headers/header[@name = 'Content-Type']"/>
			
			
			<xsl:if test="$mime != $cc.filemime">
				<ctl:message>Mimes do not match : expected <xsl:copy-of select="$cc.filemime"/> found <xsl:copy-of select="$mime"/>.</ctl:message>
				<ctl:fail/>
			</xsl:if>
		</ctl:code>
	</ctl:test>
</ctl:package>
