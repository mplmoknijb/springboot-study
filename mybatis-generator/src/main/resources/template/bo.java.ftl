package ${rootPackage}.bo;
import java.util.Date;
public class ${domainName}BO{
<#list meta as a>
 /**
  * ${a.comment}
  */
 private ${a.dataType} ${a.fieldName};
</#list>

<#list meta as a>
 public void set${a.fieldName}(${a.dataType} ${a.fieldName}){
 this.${a.fieldName} = ${a.fieldName};
 }
 public ${a.dataType} get${a.fieldName}(){
 return this.${a.fieldName};
 }
</#list>
}