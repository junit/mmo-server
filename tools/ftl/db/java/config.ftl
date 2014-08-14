<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//ibatis.apache.org//DTD Config 3.0//EN" "http://ibatis.apache.org/dtd/ibatis-3-config.dtd">
<configuration>
    <settings>
        <setting name="defaultStatementTimeout" value="60"/>
    </settings>

    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="${url}?autoReconnect=true"/>
                <property name="username" value="${usr}"/>
                <property name="password" value="${pwd}"/>
                <property name="poolPingQuery" value="select 1"/>
                <property name="poolPingEnabled" value="true"/>
                <property name="poolPingConnectionsNotUsedFor" value="3600000"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
    	<#list names as name>
		<mapper resource="${path}/${name}"/>
		</#list>
    </mappers>

</configuration>
