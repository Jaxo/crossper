<#-- 
	Freemarker template for 'Generic Text' mails.
	
-->
<#setting url_escaping_charset="UTF-8">
<HTML>
	<HEAD>
		<TITLE>

		</TITLE>
	</HEAD>
	<BODY>
		<HEADER>
		</HEADER>
                        <P>Hi <#if name?has_content>${name}</#if>,</P>
			<P><#if message?has_content>${message}</#if></P>
			
		
	</BODY>
</HTML>	