package com.game.netty.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

import com.game.message.MessagePool;
import com.game.message.struct.Message;

public abstract class Handler extends ChannelHandlerAdapter {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) { // (2)
        if (!(obj instanceof  Message)) {
            return ;
        }
        Message msg = (Message)obj;

        com.game.message.struct.Handler handler = null;
        try {
            handler = MessagePool.getInstance().createHandler(msg.getId());
        } catch (Exception e) {
            ctx.close();
            logger.error(e, e);
            return ;
        }

        if (handler == null) {
        	ctx.close();
            return ;
        }

        handler.setMessage(msg);
        handler.setContext(ctx);
        
        onRecvMsg(handler);
    }
    
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        onActive(ctx);
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        onInactive(ctx);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        logger.error(cause, cause);
        ctx.close();
    }
    
    public abstract void onActive(ChannelHandlerContext ctx);
    public abstract void onInactive(ChannelHandlerContext ctx);
    public abstract void onRecvMsg(com.game.message.struct.Handler handler);
}
