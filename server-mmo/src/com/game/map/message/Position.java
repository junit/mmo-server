package com.game.map.message;


import com.game.message.struct.Bean;

import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 坐标
 */
public class Position extends Bean {

	private static Logger log = Logger.getLogger(Position.class);
	
	//坐标x
	private int x;
	
	//坐标y
	private int y;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(ByteBuf buf){
	    try {
			//坐标x
			writeInt(buf, this.x);
			//坐标y
			writeInt(buf, this.y);
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
			//坐标x
			this.x = readInt(buf);
			//坐标y
			this.y = readInt(buf);
        } catch (Exception e) {
            log.error(e, e);
            return false;
        }
        return true;
	}
	
	/**
	 * get 坐标x
	 * @return 
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * set 坐标x
	 */
	public void setX(int x){
		this.x = x;
	}
	
	/**
	 * get 坐标y
	 * @return 
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * set 坐标y
	 */
	public void setY(int y){
		this.y = y;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//坐标x
		buf.append("x:" + x +",");
		//坐标y
		buf.append("y:" + y +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}