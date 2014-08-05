package com.game.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.apache.log4j.Logger;

/**
 * Created by game on 3/30/14.
 */
public abstract class Client extends Netty {
    private static Logger logger = Logger.getLogger(Client.class);
//    private static 
    
    protected abstract boolean init();
    public boolean init(String host, int port) {
    	if (!init()) {
    		return false;
    	}
    	EventLoopGroup workerGroup = new NioEventLoopGroup();
    	Runtime.getRuntime().addShutdownHook(new Thread(new StopThread()));
    	
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(getChannelInitializer());

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch (Exception e) {
        	logger.error(e, e);
            return false;
        } finally {
            workerGroup.shutdownGracefully();
        }
        
        return true;
    }
    
    private class StopThread implements Runnable {
        public void run() {
            stop();
        }
    }
}
