package nettyNIO.hander.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import nettyNIO.hander.socket.SocketDecoder;
import nettyNIO.hander.socket.SocketHander;

/**
 * 初始化通道
 */
public class WebSocketInta extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        //添加解析http请求
        channel.pipeline().addLast(new HttpServerCodec());
        channel.pipeline().addLast(new ChunkedWriteHandler());
        //聚合器，使用websocket会用到
        channel.pipeline().addLast(new HttpObjectAggregator(1024*64));
        channel.pipeline().addLast(new WebSocketHander());


    }
}
