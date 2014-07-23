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
	<xi:include href="ETS_CC_DSEO_Core_ATS_11.1.3_GET_Product_Single_File.ctl"/>
	<xi:include href="ETS_CC_DSEO_Core_ATS_11.1.4_GET_Product_File_non_nominal_conditions.ctl"/>
	<xi:include href="ETS_CC_DSEO_Core_ATS_11.1.5_GET_Product_Multi_File.ctl"/>
	
	<ctl:test name="dseo:DSEO_Core_Product_Download_Tests">
		<ctl:param name="sp.endpoint.HTTP.url">HTTP Url of the capabilities resource</ctl:param>
		<ctl:param name="cc.dseoschema_URL">HTTP Url of the DSEO XSD schema</ctl:param>
		<ctl:param name="cc.metalinkschema_URL">HTTP Url of the Metalink XSD schema</ctl:param>
		<ctl:param name="cc.ows_schema_URL">HTTP Url of the OWS XSD schema</ctl:param>
		<ctl:param name="cc.messagedir">INPUT MESSAGES</ctl:param>
		<ctl:param name="cc.productpath">The path of the product to be tested (metalink)</ctl:param>
		<ctl:param name="cc.filepath">The path of a file to be tested (direct download)</ctl:param>
		<ctl:param name="cc.filemime">The mime type of the file to be tested (direct download)</ctl:param>
		<ctl:assertion>These tests verify the management of GET Sensor Description resources.</ctl:assertion>
		<ctl:link title="11.1.3-4-5 GET Products">http://www.opengis.net/spec/DSEO/1.0/conf/Core/GET_Sensor_Description</ctl:link>
		<ctl:code>
		<!--
			-->
			<ctl:message>#######################################################################</ctl:message>
			<ctl:message>ATS section: 11.1.3	GET Single file Product </ctl:message>
			<ctl:message>#######################################################################</ctl:message>
			<ctl:call-test name="dseo:ATS_11.1.3_GET_Single_File_Product">
				<ctl:with-param name="sp.endpoint.HTTP.url" select="$sp.endpoint.HTTP.url"/>
				<ctl:with-param name="cc.dseoschema_URL" select="$cc.dseoschema_URL"/>
				<ctl:with-param name="cc.ows_schema_URL" select="$cc.ows_schema_URL"/>
				<ctl:with-param name="cc.messagedir" select="$cc.messagedir"/>
				<ctl:with-param name="cc.filepath" select="$cc.filepath"/>
				<ctl:with-param name="cc.filemime" select="$cc.filemime"/>
			</ctl:call-test>
			<ctl:message>#######################################################################</ctl:message>
			<ctl:message>ATS section: 11.1.4	GET File Product non nominal description  </ctl:message>
			<ctl:message>#######################################################################</ctl:message>
			<ctl:call-test name="dseo:ATS_11.1.4_GET_File_Product_non_nominal_conditions">
				<ctl:with-param name="sp.endpoint.HTTP.url" select="$sp.endpoint.HTTP.url"/>
				<ctl:with-param name="cc.dseoschema_URL" select="$cc.dseoschema_URL"/>
				<ctl:with-param name="cc.ows_schema_URL" select="$cc.ows_schema_URL"/>
				<ctl:with-param name="cc.messagedir" select="$cc.messagedir"/>
			</ctl:call-test>
			<!--
			-->
			
			<ctl:message>#######################################################################</ctl:message>
			<ctl:message>ATS section: 11.1.5	GET Multi file Product </ctl:message>
			<ctl:message>#######################################################################</ctl:message>
			<ctl:call-test name="dseo:ATS_11.1.5_GET_Multi_File_Product">
				<ctl:with-param name="sp.endpoint.HTTP.url" select="$sp.endpoint.HTTP.url"/>
				<ctl:with-param name="cc.metalinkschema_URL" select="$cc.metalinkschema_URL"/>
				<ctl:with-param name="cc.dseoschema_URL" select="$cc.dseoschema_URL"/>
				<ctl:with-param name="cc.ows_schema_URL" select="$cc.ows_schema_URL"/>
				<ctl:with-param name="cc.messagedir" select="$cc.messagedir"/>
				<ctl:with-param name="cc.productpath" select="$cc.productpath"/>
			</ctl:call-test>
		</ctl:code>
	</ctl:test>
</ctl:package>
