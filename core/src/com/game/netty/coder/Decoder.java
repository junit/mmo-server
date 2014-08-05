package com.game.netty.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;

import java.util.List;

import org.apache.log4j.Logger;

import com.game.message.MessagePool;
import com.game.message.struct.Message;

/**
 * Created by Administrator on 2014/4/17.
 */
public class Decoder extends ByteToMessageDecoder {
	private static Logger logger = Logger.getLogger(Decoder.class);
	private static final String MESSAGE_COUNT = "MESSAGE_COUNT";
	private static final String MESSAGE_TIME = "MESSAGE_TIME";
	private static final int MESSAGE_COUNT_NUM = 30;
	private static final int MESSAGE_COUNT_MSEC = 1000;
    
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < (Integer.SIZE / Byte.SIZE)) {
            return;
        }
        in.markReaderIndex();

        int length = in.readInt();
        if (length < 0) {
            ctx.close();
            return;
        }

        if (length > in.readableBytes()) { // wait until bytes enough
            in.resetReaderIndex();
            return;
        }
        
        if (!rateCheck(ctx, MESSAGE_COUNT_NUM, MESSAGE_COUNT_MSEC)) {
        	ctx.close();
            return;
        }

        int messageId = in.readInt();

        Message message = MessagePool.getInstance().createMessage(messageId);
        if (message == null) {
        	logger.error("找不到消息:" + messageId);
            ctx.close();
            return;
        }

        if (!message.read(in)) {
        	logger.error("读取消息错误:" + messageId);
            ctx.close();
            return;
        }

        out.add(message);
    }

	private boolean rateCheck(ChannelHandlerContext ctx, int maxNum, int perMsec) {
		Object object = ctx.attr(AttributeKey.valueOf(MESSAGE_TIME)).get();
		if (object == null) {
			object = System.currentTimeMillis();
			ctx.attr(AttributeKey.valueOf(MESSAGE_TIME)).set(object);
			ctx.attr(AttributeKey.valueOf(MESSAGE_COUNT)).set(0);
			return true;
		}
		
		long time = (Long)object;
		long nowTime = System.currentTimeMillis();
		if (time - nowTime > perMsec) {
			ctx.attr(AttributeKey.valueOf(MESSAGE_TIME)).set(nowTime);
			ctx.attr(AttributeKey.valueOf(MESSAGE_COUNT)).set(0);
			return true;
		}
		
		int count = (Integer)ctx.attr(AttributeKey.valueOf(MESSAGE_COUNT)).get();
		if (count > maxNum) {
			return false;
		}
		ctx.attr(AttributeKey.valueOf(MESSAGE_COUNT)).set(count + 1);
		
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(Integer.SIZE);
		System.out.println(Byte.SIZE);
		System.out.println(Integer.SIZE / Byte.SIZE);
	}
}
