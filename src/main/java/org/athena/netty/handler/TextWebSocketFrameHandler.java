package org.athena.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static Logger logger = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        logger.debug("收到消息: {}", text);
        //读取收到的信息写回到客户端
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间: " + LocalDateTime.now()));
    }

    /**
     * 连接建立时
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        String longText =  ctx.channel().id().asLongText();
        logger.debug("handlerAddred {}", longText);
    }

    /**
     * 连接关闭时
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String longText =  ctx.channel().id().asLongText();
        logger.debug("handlerRemoved {}", longText);
    }

    /**
     * 异常发生时
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("异常发生");
        ctx.close();
    }

}
