package com.db.config;

<#list names as name>
import com.db.config.container.${name?cap_first}Container;
</#list>
import com.manager.Manager;

public class DbConfigManager extends Manager {
<#list names as name>
	public ${name?cap_first}Container ${name} = new ${name?cap_first}Container();
</#list>
	
	@Override
	public boolean init() {
		<#list names as name>
		if (!${name}.init()) return false;
		</#list>
		return true;
	}

	@Override
	public void stop() {
	}

}
