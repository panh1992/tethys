package org.athena.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static Logger logger = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);

    // 用于记录和管理所有客户端的 channel
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        String text = msg.text();
        logger.debug("收到消息: {}", text);
        //读取收到的信息写回到客户端
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间: " + LocalDateTime.now()));
    }

    /**
     * 连接建立时, 获取客户端的 channel 放入 ChannelGroup 中
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        System.out.println("客户端建立链接, channel对应的长id为：" + channel.id().asLongText());
        System.out.println("客户端建立链接, channel对应的短id为：" + channel.id().asShortText());
        clients.add(channel);
    }

    /**
     * 连接关闭时
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        clients.remove(channel);
        System.out.println("客户端断开, channel对应的长id为：" + channel.id().asLongText());
        System.out.println("客户端断开, channel对应的短id为：" + channel.id().asShortText());
    }

    /**
     * 异常发生时
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("异常发生");
        ctx.close();
    }

}
