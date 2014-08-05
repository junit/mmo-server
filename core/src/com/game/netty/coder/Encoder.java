package com.game.netty.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.game.message.struct.Message;

/**
 * Created by game on 4/3/14.
 */
public class Encoder extends MessageToByteEncoder<Message> {
    private ByteBuf buf;
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        if (buf == null) {
            buf = ctx.alloc().ioBuffer();
        }

        buf.writeInt(msg.getId());
        msg.write(buf);

        out.writeInt(buf.readableBytes());
        out.writeBytes(buf, 0, buf.readableBytes());

        buf.clear();
    }
}
