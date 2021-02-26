package nettyNIO.hander.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import nettyNIO.hander.DecoderMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义编码器
 */
public class SocketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        ArrayList<ArrayList<Byte>> bytes = DecoderMap.decoderMap.get(channelHandlerContext.channel().id());
        int low = 0;
        if(bytes == null){
            bytes = new ArrayList<ArrayList<Byte>>();
        }
        for (int i = 0; i < byteBuf.readableBytes();i++){
             Byte by = byteBuf.readByte();
             //以‘/’为截至符号
             if(by == 47){
                low ++;
             }else {
                 if(low >= bytes.size() || bytes.get(low).isEmpty()){
                     ArrayList<Byte> byteElem = new ArrayList<Byte>();
                     bytes.add(byteElem);
                 }
                 bytes.get(low).add(by);
             }
        }
        for (int i = 0 ; i < low; i++){
            Object[] byteStr = bytes.get(i).toArray();
            byte[] bytess = new byte[byteStr.length];
            for (int j = 0;j < byteStr.length;j++){
                Byte byt = (Byte)byteStr[j];
                bytess[j] = byt.byteValue();
            }
            list.add(new String(bytess));
        }
        for (int i = 0;i < low;i++){
            bytes.remove(i);
        }
        DecoderMap.decoderMap.put(channelHandlerContext.channel().id(),bytes);
    }
}
