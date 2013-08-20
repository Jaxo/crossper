<#-- 
	Freemarker template for 'Generic Text' mails.
	
-->
<#setting url_escaping_charset="UTF-8">
<HTML>
	<HEAD>
		<TITLE>
			Crossper.com 
		</TITLE>
	</HEAD>
	<BODY>
		<HEADER>
		</HEADER>
			<P>Hi <#if name?has_content>${name}</#if>,</P>
			<P>Welcome to Crossper. </P>
			
		<FOOTER>
			<#include "./includes/Notice.ftl">
			<#import "./includes/CopyRight.ftl" as cr>
			<@cr.copyright date="2012-2013" organization="Crossper"/>
		</FOOTER>
	</BODY>
</HTML>	