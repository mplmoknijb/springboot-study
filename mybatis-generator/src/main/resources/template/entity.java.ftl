package ${rootPackage}.entity;

import java.io.Serializable;

public class ${domainName}Entity implements Serializable{

 private static final long serialVersionUID = 1L;

<#list meta as a>
 private ${a.dataType} ${a.columnName};
</#list>

<#list meta as a>
 public void set${a.columnName}(${a.dataType} ${a.columnName}){
 this.${a.columnName} = ${a.columnName};
 }
 public ${a.dataType} get${a.columnName}(){
 return this.${a.columnName};
 }

</#list>
}