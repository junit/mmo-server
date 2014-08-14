package ${pkg}.message;

<#list fields as field>
<#if field.listFlag==1>
import java.util.List;
import java.util.ArrayList;
<#break>
</#if>
</#list>
import com.game.message.struct.Message;
import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * ${note}消息
 */
public class ${name}Message extends Message{

	private static Logger log = Logger.getLogger(${name}Message.class);
	
	<#list fields as field>
	<#if field.listFlag==1>
	//${field.note}
	private List<${field.clazz?cap_first}> ${field.name} = new ArrayList<>();
	<#else>
	//${field.note}
	private ${field.clazz} ${field.name};
	</#if>
	</#list>
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(ByteBuf buf){
	    try {
			<#list fields as field>
			<#if field.listFlag==1>
			//${field.note}
			writeShort(buf, (short)${field.name}.size());
			for (int i = 0; i < ${field.name}.size(); i++) {
				<#if field.clazz=="int">
				writeInt(buf, ${field.name}.get(i));
				<#elseif field.clazz=="short">
				writeShort(buf, ${field.name}.get(i));
				<#elseif field.clazz=="float">
				writeFloat(buf, ${field.name}.get(i));
				<#elseif field.clazz=="long">
				writeLong(buf, ${field.name}.get(i));
				<#elseif field.clazz=="byte">
				writeByte(buf, ${field.name}.get(i));
				<#elseif field.clazz=="String">
				writeString(buf, ${field.name}.get(i));
				<#else>
				writeBean(buf, ${field.name}.get(i));
				</#if>
			}
			<#else>
			//${field.note}
			<#if field.clazz=="int">
			writeInt(buf, this.${field.name});
			<#elseif field.clazz=="short">
			writeShort(buf, this.${field.name});
			<#elseif field.clazz=="float">
			writeFloat(buf, this.${field.name});
			<#elseif field.clazz=="long">
			writeLong(buf, this.${field.name});
			<#elseif field.clazz=="byte">
			writeByte(buf, this.${field.name});
			<#elseif field.clazz=="String">
			writeString(buf, this.${field.name});
			<#else>
			writeBean(buf, this.${field.name});
			</#if>
			</#if>
			</#list>
        } catch (Exception e) {
            log.error(e, e);
            return false;
        }
        return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(ByteBuf buf){
        try {
			<#list fields as field>
			<#if field.listFlag==1>
			//${field.note}
			int ${field.name}_length = readShort(buf);
			for (int i = 0; i < ${field.name}_length; i++) {
				<#if field.clazz=="int">
				${field.name}.add(readInt(buf));
				<#elseif field.clazz=="short">
				${field.name}.add(readShort(buf));
				<#elseif field.clazz=="float">
				${field.name}.add(readFloat(buf));
				<#elseif field.clazz=="long">
				${field.name}.add(readLong(buf));
				<#elseif field.clazz=="byte">
				${field.name}.add(readByte(buf));
				<#elseif field.clazz=="String">
				${field.name}.add(readString(buf));
				<#else>
				${field.name}.add((${field.clazz})readBean(buf, ${field.clazz}.class));
				</#if>
			}
			<#else>
			//${field.note}
			<#if field.clazz=="int">
			this.${field.name} = readInt(buf);
			<#elseif field.clazz=="short">
			this.${field.name} = readShort(buf);
			<#elseif field.clazz=="float">
			this.${field.name} = readFloat(buf);
			<#elseif field.clazz=="long">
			this.${field.name} = readLong(buf);
			<#elseif field.clazz=="byte">
			this.${field.name} = readByte(buf);
			<#elseif field.clazz=="String">
			this.${field.name} = readString(buf);
			<#else>
			this.${field.name} = (${field.clazz})readBean(buf, ${field.clazz}.class);
			</#if>
			</#if>
			</#list>
        } catch (Exception e) {
            log.error(e, e);
            return false;
        }
        return true;
	}
	
	<#list fields as field>
	<#if field.listFlag==1>
	/**
	 * get ${field.note}
	 * @return 
	 */
	public List<${field.clazz?cap_first}> get${field.name?cap_first}(){
		return ${field.name};
	}
	
	/**
	 * set ${field.note}
	 */
	public void set${field.name?cap_first}(List<${field.clazz?cap_first}> ${field.name}){
		this.${field.name} = ${field.name};
	}
	
	<#else>
	/**
	 * get ${field.note}
	 * @return 
	 */
	public ${field.clazz} get${field.name?cap_first}(){
		return ${field.name};
	}
	
	/**
	 * set ${field.note}
	 */
	public void set${field.name?cap_first}(${field.clazz} ${field.name}){
		this.${field.name} = ${field.name};
	}
	
	</#if>
	</#list>
	
	@Override
	public int getId() {
		return ${id?c};
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		<#list fields as field>
		<#if field.listFlag==1>
		//${field.note}
		buf.append("${field.name}:{");
		for (int i = 0; i < ${field.name}.size(); i++) {
			<#if field.clazz=="int">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.clazz=="short">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.clazz=="float">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.clazz=="long">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.clazz=="byte">
			buf.append(${field.name}.get(i) +",");
			<#elseif field.clazz=="String">
			buf.append(${field.name}.get(i).toString() +",");
			<#else>
			buf.append(${field.name}.get(i).toString() +",");
			</#if>
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		<#else>
		//${field.note}
		<#if field.clazz=="int">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.clazz=="short">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.clazz=="float">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.clazz=="long">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.clazz=="byte">
		buf.append("${field.name}:" + ${field.name} +",");
		<#elseif field.clazz=="String">
		if(this.${field.name}!=null) buf.append("${field.name}:" + ${field.name}.toString() +",");
		<#else>
		if(this.${field.name}!=null) buf.append("${field.name}:" + ${field.name}.toString() +",");
		</#if>
		</#if>
		</#list>
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}