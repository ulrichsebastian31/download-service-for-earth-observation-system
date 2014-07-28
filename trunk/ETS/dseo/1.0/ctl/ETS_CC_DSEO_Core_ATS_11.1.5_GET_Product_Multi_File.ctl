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
		xmlns:met="http://www.metalinker.org/"
 xsi:schemaLocation="http://www.occamlab.com/ctl  ../apps/engine/resources/com/occamlab/te/schemas/ctl.xsd">
	<!-- CTL FILE METADATA
  Author(s): Sebastian ULRICH  , Astrium LTD (sebastian.ulrich@astrium.eads.net)
  Lineage: 2014/05/01 - ASTRIUM
	-->
	<ctl:test name="dseo:ATS_11.1.5_GET_Multi_File_Product">
		<ctl:param name="sp.endpoint.HTTP.url">HTTP URL of GetCapabilities</ctl:param>
		<ctl:param name="cc.dseoschema_URL">DSEO Schema URL</ctl:param>
		<ctl:param name="cc.metalinkschema_URL">Metalink Schema URL</ctl:param>
		<ctl:param name="cc.ows_schema_URL">OWS Schema URL</ctl:param>
		<ctl:param name="cc.messagedir">INPUT MESSAGES</ctl:param>
		<ctl:param name="cc.productpath">The path of the product to be tested (metalink)</ctl:param>
		<ctl:assertion>To verify whether the DSEO Server under test provides description of a given sensor.</ctl:assertion>
		<ctl:link title="11.1.5 Get Multi File Product">http://www.opengis.net/spec/DSEO/1.0/conf/Core/GET_Multi_File_Product</ctl:link>
		<ctl:code>
			<!-- #############################################################################################  -->
			<!-- #
        The Client sends an HTTP GET request including:
		- service=DSEO
		- request=GetProduct
		- version=1.0.0
		- ProductURI={on-line multi file product URI}
		- Verify that the DSEO Server replies with:
		- HTTP/1.1 response
		- Status = 200 Ok
		- Content-type: application/metalink+xml
		- The HTTP payload contains a Metalink file valid with
		respect to Metalink.xsd schema definition.
			 -->
			 
			<!-- #############################################################################################  -->
			<!-- # The returned element is valid against dseo.xsd XML Schema;                               #  -->
			<!-- #############################################################################################  -->
			
			<xsl:variable name="endpoint.description" select="concat($sp.endpoint.HTTP.url, $cc.productpath)"/>
			<xsl:message> <xsl:copy-of select="$endpoint.description"/> </xsl:message>
			<xsl:variable name="response">
				<ctl:request>
					<ctl:url>
						<xsl:copy-of select="$endpoint.description"/>
					</ctl:url>
					<ctl:method>get</ctl:method>
					<parsers:XMLValidatingParser>
						<parsers:schema type="file">
							<xsl:value-of select="$cc.metalinkschema_URL"/>
						</parsers:schema>
					</parsers:XMLValidatingParser>
				</ctl:request>
			</xsl:variable>
			<xsl:if test="not ($response/*)">
				<ctl:message>ATS 11.1.5 validation failed</ctl:message>
				<ctl:fail/>
			</xsl:if>
			
			<xsl:variable name="mime" select="$response/response/headers/header[@name = 'Content-Type']"/>
			
			<xsl:if test="$mime != 'application/metalink+xml'">
				<ctl:message>Mimes do not match : expected application/metalink+xml found <xsl:copy-of select="$mime"/>.</ctl:message>
				<ctl:fail/>
			</xsl:if>
			
			<xsl:if test="not($response//met:metalink//met:files//met:file)">
				<ctl:message>The metalink does not contain any files</ctl:message>
				<ctl:fail/>
			</xsl:if>
			
			
		</ctl:code>
	</ctl:test>
</ctl:package>
