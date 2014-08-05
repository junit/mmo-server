package com.db.${type}.bean;

public class ${name?cap_first}Bean {
	<#list fields as field>
	private ${field.clazz} ${field.name};
	</#list>
	
	<#list fields as field>
	public ${field.clazz} get${field.name?cap_first}(){
		return ${field.name};
	}
	
	public void set${field.name?cap_first}(${field.clazz} ${field.name}){
		this.${field.name} = ${field.name};
	}
	
	</#list>
}