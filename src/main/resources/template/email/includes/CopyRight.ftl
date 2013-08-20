<#-- 
	'Copyright' template. Common included template.
	
-->

<#macro copyright date organization>
  <P>Copyright (C) <#if date?has_content>${date}<#else>2011-2012</#if> <#if organization?has_content>${organization}<#else>Crossper</#if>. All rights reserved.</P>
</#macro>