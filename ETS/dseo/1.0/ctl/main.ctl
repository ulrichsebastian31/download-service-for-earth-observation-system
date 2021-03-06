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
		xmlns:parsers="http://www.occamlab.com/te/parsers" 
		xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
		xmlns:sec="http://www.intecs.it/sec" 
		xmlns:xlink="http://www.w3.org/1999/xlink" 
		xmlns:csw="http://www.opengis.net/cat/csw/2.0.2" 
		xmlns:util="http://www.occamlab.com/te/util" 
		xmlns:dseo="http://www.opengis.net/dseo/1.0" 
		xsi:schemaLocation="http://www.occamlab.com/ctl  ../apps/engine/resources/com/occamlab/te/schemas/ctl.xsd">
	
	<!-- CTL FILE METADATA
  Author(s): Sebastian ULRICH  , Astrium LTD (sebastian.ulrich@astrium.eads.net)
  Lineage: 2014/05/01 - ASTRIUM
 -->
	<!-- the list of Conformance classes 
      each Conformance Class is composed of a set of tests, each verifying one or more requirements of the corresponding Requirements Class
  -->
	<xi:include href="common.xml"/>
	<xi:include href="ETS_CC_DSEO_Core.ctl"/>
	
	<!-- ############################################################################################################ -->
	<!-- DSEO Compliance CTL Test suite-->
	<!-- ############################################################################################################ -->
	<ctl:suite name="dseo:compliance-suite">
		<ctl:title>OGC 13-043 (V 1.0) compliance test suite</ctl:title>
		<ctl:description>Validates an implementation claiming compliance with respect to the "Download Service for Earch Observation Products" (13-043) Best Practice</ctl:description>
		<ctl:starting-test>dseo:compliance-tests</ctl:starting-test>
	</ctl:suite>
	<!-- ############################################################################################################ -->
	<!-- DSEO Compliance Tests implementation-->
	<!-- ############################################################################################################ -->
	<ctl:test name="dseo:compliance-tests">
		<ctl:assertion>Run the tests specified in the  annex A</ctl:assertion>
		<ctl:comment>Complete set of tests for verifying the compliance of the Server Under Test with respect to the Download Service for Earch Observation Products Best Practice</ctl:comment>
		<ctl:link title="OGC 13-043 section 11">Annex A</ctl:link>
		<ctl:code>
			<xsl:variable name="form-values">
				<ctl:form height="500" width="800">
					<body>
						<h2 align="center">Compliance Test Suite of the OGC Download Service for Earch Observation Products (13-043) Best Practice</h2>
						<h3 align="center">Input Parameters for the Executable Test Suite</h3>
						<table border="0" cellpadding="2" cellspacing="2" bgcolor="#E9CFEC">
							<tr>
								<td align="left">Endpoint Reference of the service provider</td>
								<td align="left">
									<input name="stringvalue.sp.endpoint.HTTP.url" size="70" type="text" value="http://hma-demo-opensearch.spacebel.be/DSEOServer/mips/ds/Download_Server_1/dseo/1.0.0/"/>
								</td>
							</tr>
							<tr>
								<td align="left">Reference of the product path</td>
								<td align="left">
									<input name="stringvalue.cc.productpath" size="70" type="text" value="product/1"/>
								</td>
							</tr>
							<tr>
								<td align="left">Reference of the file path</td>
								<td align="left">
									<input name="stringvalue.cc.filepath" size="70" type="text" value="file/5"/>
								</td>
							</tr>
							<tr>
								<td align="left">Reference of the file mime</td>
								<td align="left">
									<input name="stringvalue.cc.filemime" size="70" type="text" value="image/png"/>
								</td>
							</tr>							
						</table>
						<input style="text-align:center" type="submit" value="Start Test"/>
					</body>
				</ctl:form>
			</xsl:variable>		
			<!-- ############################################################################################################ -->
			<!-- DEFINITION OF COMMON INPUT PARAMETERS -->
			<!-- ############################################################################################################ -->
			<xsl:variable name="sp.endpoint.HTTP.url" select="$form-values/values/value[@key='stringvalue.sp.endpoint.HTTP.url' ]"/>
			<xsl:variable name="cc.productpath" select="$form-values/values/value[@key='stringvalue.cc.productpath' ]"/>
			<xsl:variable name="cc.filepath" select="$form-values/values/value[@key='stringvalue.cc.filepath' ]"/>
			<xsl:variable name="cc.filemime" select="$form-values/values/value[@key='stringvalue.cc.filemime' ]"/>				
			<!-- ************************************************************************************************* -->
					
			<!-- ############################################################################################################ -->
			<!-- DEFINITION OF COMMON INPUT PARAMETERS -->
			<!-- ############################################################################################################ -->						
			<xsl:variable name="cc.dseoschema_URL">/var/local/teamengine/scripts/dseo/1.0/resources/schema/dseo/1.0/dseo.xsd</xsl:variable>
			<xsl:variable name="cc.metalinkschema_URL">/var/local/teamengine/scripts/dseo/1.0/resources/schema/dseo/1.0/metalink.xsd</xsl:variable>
			<xsl:variable name="cc.ows_schema_URL">/var/local/teamengine/scripts/dseo/1.0/resources/schema/ows/1.1.0/owsAll.xsd</xsl:variable>
			<xsl:variable name="cc.messagedir">/var/local/teamengine/scripts/dseo/1.0/resources/netcatdemos</xsl:variable>
			
			<!--<xsl:variable name="sp.endpoint.HTTP.url">http://127.0.0.1:8080/IPSServices/mips/ds/Download_Server_1/dseo/1.0.0/</xsl:variable>-->
			<!--<xsl:variable name="sp.endpoint.HTTP.url">http://127.0.0.1:8080/DSEOServer/mips/ds/Download_Server_1/dseo/1.0.0/</xsl:variable>-->			
			<!--<xsl:variable name="cc.dseoschema_URL">C:\Java\team_engine\schema\dseo\1.0\dseo.xsd</xsl:variable>-->
			<!--<xsl:variable name="cc.metalinkschema_URL">C:\Java\team_engine\schema\dseo\1.0\metalink.xsd</xsl:variable>-->
			<!--<xsl:variable name="cc.ows_schema_URL">C:\Java\team_engine\schema\ows\2.0\owsAll.xsd</xsl:variable>-->
			<!--<xsl:variable name="cc.messagedir">C:/Java/team_engine/netcatdemos</xsl:variable>-->
			<!--<xsl:variable name="cc.productpath">product/1</xsl:variable>-->
			<!--<xsl:variable name="cc.filepath">file/5</xsl:variable>-->
			<!--<xsl:variable name="cc.filemime">image/png</xsl:variable>-->		
			<!--
			<ctl:message>The Input parameters are:                             </ctl:message>
			<ctl:message> the address of the service provider is: <xsl:value-of select="$sp.endpoint.HTTP.url"/>
			</ctl:message>
			<ctl:message> the URL to DSEO schema is : <xsl:value-of select="$cc.dseoschema_URL"/>
			</ctl:message>
			<ctl:message> the URL to OWS schema is : <xsl:value-of select="$cc.ows_schema_URL"/>
			</ctl:message>
			<ctl:message> the URL to input message directory is : <xsl:value-of select="$cc.messagedir"/>
			</ctl:message>
			<ctl:message> the product to try is : <xsl:value-of select="$cc.productpath"/>
			</ctl:message>
			<ctl:message> the file to try is : <xsl:value-of select="$cc.filepath"/>
			</ctl:message>   
		-->	
			<!-- ############################################################################################################ -->
		<!--
			<ctl:message>Testing started at : <xsl:value-of select="current-dateTime()"/>
			</ctl:message>
		-->	
			<!-- ############################################################################################################ -->
			<!-- Conformance Classes -->
			<!-- ############################################################################################################ -->
			<!--
			-->
			<ctl:message>##############################################</ctl:message>
			<ctl:message>Section 14.1: Conformance Class Core </ctl:message>
			<ctl:message>##############################################</ctl:message>
			<ctl:call-test name="dseo:DSEO_Core_Conformance_Class">
				<ctl:with-param name="sp.endpoint.HTTP.url" select="$sp.endpoint.HTTP.url"/>
				<ctl:with-param name="cc.dseoschema_URL" select="$cc.dseoschema_URL"/>
				<ctl:with-param name="cc.metalinkschema_URL" select="$cc.metalinkschema_URL"/>
				<ctl:with-param name="cc.ows_schema_URL" select="$cc.ows_schema_URL"/>
				<ctl:with-param name="cc.messagedir" select="$cc.messagedir"/>
				<ctl:with-param name="cc.productpath" select="$cc.productpath"/>
				<ctl:with-param name="cc.filepath" select="$cc.filepath"/>
				<ctl:with-param name="cc.filemime" select="$cc.filemime"/>
			</ctl:call-test>
		</ctl:code>
	</ctl:test>
</ctl:package>
