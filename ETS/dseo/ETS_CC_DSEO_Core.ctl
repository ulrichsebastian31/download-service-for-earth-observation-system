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
		xmlns:ows="http://www.opengis.net/ows/2.0" 
		xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
		xmlns:sec="http://www.intecs.it/sec" 
		xmlns:xlink="http://www.w3.org/1999/xlink" 
		xmlns:csw="http://www.opengis.net/cat/csw/2.0.2" 
		xmlns:util="http://www.occamlab.com/te/util" 
		xmlns:dseo="http://www.opengis.net/dseo/1.0"  >
	
	<!-- CTL FILE METADATA
  Author(s): Sebastian ULRICH  , Astrium LTD (sebastian.ulrich@astrium.eads.net)
  Lineage: 2014/05/01 - ASTRIUM
  -->
  
	<xi:include href="ETS_CC_DSEO_Core_Capabilities.ctl"/>
	<xi:include href="ETS_CC_DSEO_Core_Product_Download.ctl"/>
	<!--
	-->
	
	<!-- ************************************************************************************************* 
   This Conformance Class is in charge of verifying the compliance of the DSEO Server under test with 
       respect to the Core Requirement Class, which includes the basic functions that every DSEO Server 
       shall implement i.e. capabilities, direct product download, ...
       ************************************************************************************************* -->
	<ctl:test name="dseo:DSEO_Core_Conformance_Class">
		<ctl:param name="sp.endpoint.HTTP.url">HTTP Url of the capabilities resource</ctl:param>
		<ctl:param name="cc.dseoschema_URL">HTTP Url of the DSEO XSD schema</ctl:param>
		<ctl:param name="cc.metalinkschema_URL">HTTP Url of the Metalink XSD schema</ctl:param>
		<ctl:param name="cc.ows_schema_URL">HTTP Url of the OWS XSD schema</ctl:param>
		<ctl:param name="cc.messagedir">INPUT MESSAGES</ctl:param>
		<ctl:param name="cc.productpath">The path of the product to be tested (metalink)</ctl:param>
		<ctl:param name="cc.filepath">The path of a file to be tested (direct download)</ctl:param>
		<ctl:param name="cc.filemime">The mime type of the file to be tested (direct download)</ctl:param>
		<ctl:assertion>Tests for verifying the compliance with respect to Core Conformance Class</ctl:assertion>
		<ctl:link title="OGC 13-043 section 11">A.A</ctl:link>
		<ctl:code>
	<!--
	-->
			<ctl:message>#######################################################################</ctl:message>
			<ctl:message>Capabilities resource verification (ATS sections: 11.1.1)</ctl:message>
			<ctl:message>#######################################################################</ctl:message>
			<ctl:call-test name="dseo:DSEO_Core_Capabilities_Tests">
				<ctl:with-param name="sp.endpoint.HTTP.url" select="$sp.endpoint.HTTP.url"/>
				<ctl:with-param name="cc.dseoschema_URL" select="$cc.dseoschema_URL"/>
				<ctl:with-param name="cc.ows_schema_URL" select="$cc.ows_schema_URL"/>
				<ctl:with-param name="cc.messagedir" select="$cc.messagedir"/>
			</ctl:call-test>
			
			<ctl:message>#######################################################################</ctl:message>
			<ctl:message>Product download resource verification (ATS sections: 11.1.3, 11.1.4, 11.1.5)</ctl:message>
			<ctl:message>#######################################################################</ctl:message>
			<ctl:call-test name="dseo:DSEO_Core_Product_Download_Tests">
				<ctl:with-param name="sp.endpoint.HTTP.url" select="$sp.endpoint.HTTP.url"/>
				<ctl:with-param name="cc.dseoschema_URL" select="$cc.dseoschema_URL"/>
				<ctl:with-param name="cc.metalinkschema_URL" select="$cc.metalinkschema_URL"/>
				<ctl:with-param name="cc.ows_schema_URL" select="$cc.ows_schema_URL"/>
				<ctl:with-param name="cc.messagedir" select="$cc.messagedir"/>
				<ctl:with-param name="cc.productpath" select="$cc.productpath"/>
				<ctl:with-param name="cc.filepath" select="$cc.filepath"/>
				<ctl:with-param name="cc.filemime" select="$cc.filemime"/>
			</ctl:call-test>
			<!--
			-->
		</ctl:code>
	</ctl:test>
</ctl:package>