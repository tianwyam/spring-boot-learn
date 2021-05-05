package ${packageName} ;

<#if importClassList?? && (importClassList?size > 0) >
<#list importClassList as importClass>
import ${importClass} ;
</#list>
</#if>

import lombok.Data ;


@Data
public class ${className} {

<#if attributes?? && (attributes?size > 0) >
	<#list attributes as attr>
	
		// ${attr.attrComment}
		private ${attr.attrType} ${attr.attrName} ;
		
	</#list>
</#if>


}