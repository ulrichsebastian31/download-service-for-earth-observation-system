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
	<ctl:test name="dseo:ATS_11.1.4_GET_File_Product_non_nominal_conditions">
		<ctl:param name="sp.endpoint.HTTP.url">HTTP URL of GetCapabilities</ctl:param>
		<ctl:param name="cc.dseoschema_URL">DSEO Schema URL</ctl:param>
		<ctl:param name="cc.ows_schema_URL">OWS Schema URL</ctl:param>
		<ctl:param name="cc.messagedir">INPUT MESSAGES</ctl:param>
		<ctl:assertion>To verify whether the DSEO Server under test provides ExceptionReport.</ctl:assertion>
		<ctl:link title="11.1.4 Get Sensor Description/non_nominal_conditions">
		http://www.opengis.net/spec/DSEO/1.0.0/conf/Core/GET_Product_File-non-nominal
		</ctl:link>
		<ctl:code>
			<!-- #############################################################################################  -->
			<!-- #
          To send an incorrect DSEO GetProduct request with invalid
				product ID. Verify that the response is a HTTP Error including an
				ows:ExceptionReport according to the clause 8 of [NR7].
                                                                                                       # -->
			<!-- #############################################################################################  -->
			
			<xsl:variable name="endpoint.description" select="concat($sp.endpoint.HTTP.url, 'pdsfadgfdb')"/>
			<xsl:message> <xsl:copy-of select="$endpoint.description"/> </xsl:message>
			<xsl:variable name="response">
				<ctl:request>
					<ctl:url>
						<xsl:copy-of select="$endpoint.description"/>
					</ctl:url>
					<ctl:method>get</ctl:method>
					<parsers:XMLValidatingParser>
						<parsers:schema type="file">
							<xsl:value-of select="$cc.ows_schema_URL"/>
						</parsers:schema>
					</parsers:XMLValidatingParser>
				</ctl:request>
			</xsl:variable>
			<xsl:if test="not ($response/*)">
				<ctl:message>ATS 11.1.4 validation failed</ctl:message>
				<ctl:fail/>
			</xsl:if>
     <!-- ############################################################################################# -->
     <!-- #includes an ows:ExceptionReport elements set as Table 7-9                                  # -->
     <!-- ############################################################################################# -->
      <xsl:if test="not($response//ows:ExceptionReport)">
				<ctl:message>ExceptionReport is not set as table 7-9 </ctl:message>
				<ctl:fail/>
			</xsl:if>
		</ctl:code>
	</ctl:test>
</ctl:package>
