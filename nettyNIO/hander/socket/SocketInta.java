package nettyNIO.hander.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 初始化通道
 */
public class SocketInta extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        //channel.pipeline().addLast(new StringDecoder());
        channel.pipeline().addLast(new SocketDecoder());
        channel.pipeline().addLast(new StringEncoder());
        //channel.pipeline().addLast(new SocketEncoder());
        channel.pipeline().addLast(new SocketHander());
    }
}
