<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE mapper  
    PUBLIC "-//ibatis.apache.org//DTD Mapper 3.0//EN"  
    "http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd">  
<mapper namespace="${name}"> 
  <resultMap id="bean" type="com.db.${type}.bean.${name?cap_first}Bean" >
  <#list fields as field>
	<result column="${field.name}" property="${field.name}" jdbcType="${field.dbType}" />
	</#list>
  </resultMap>
  
  <select id="select" resultMap="bean">
  	select <#list fields as field><#if field_has_next>${field.name},<#else>${field.name}</#if></#list> from ${name}
  </select>
  
  <insert id="insert" parameterType="com.db.${type}.bean.${name?cap_first}Bean">
  	insert into ${name}(<#list fields as field><#if field_has_next>${field.name},<#else>${field.name}</#if></#list>)
  	     values (<#list fields as field><#if field_has_next>${"#"}{${field.name},jdbcType=${field.dbType}},<#else>${"#"}{${field.name},jdbcType=${field.dbType}}</#if></#list>)
  </insert>
</mapper>