package com.dtusystem.protocol;

import com.dtusystem.message.Message;
import com.dtusystem.message.NetworkMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, NetworkMsg> {

    private final Serializer serializer = Serializer.Algorithm.Json;

    @Override
    protected void encode(ChannelHandlerContext ctx, NetworkMsg networkMsg, List<Object> outList) throws Exception {
        log.debug("msg is {}", networkMsg);
        ByteBuf out = ctx.alloc().buffer();
        // 1. 4 字节的魔数
        out.writeBytes(new byte[]{'b', 'a', 'b', 'y'});
        // 2. 1 字节的版本,
        out.writeByte(1);
        // 3. 1 字节的序列化方式 jdk 0 , json 1
        out.writeByte(1);
        // 4. 1 字节的指令类型
        out.writeByte(networkMsg.getMessage().getMessageCode());
        // 5. 4 个字节
        out.writeInt(networkMsg.getId());
        // 无意义，对齐填充d
        out.writeByte(0xff);
        // 6. 获取内容的字节数组
        byte[] bytes = serializer.serialize(networkMsg.getMessage());
        // 7. 长度
        out.writeInt(bytes.length);
        // 8. 写入内容
        out.writeBytes(bytes);
        outList.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magicNum = in.readInt();
        byte version = in.readByte();
        byte serializerType = in.readByte();
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);

        Class<? extends Message> msgType = Message.getMessageClass(messageType);
        Message msg = serializer.deserialize(msgType, bytes);
        log.debug("{}, {}, {}, {}, {}, {}", magicNum, version, serializerType, messageType, sequenceId, length);
        log.debug("{}", msg);
        out.add(new NetworkMsg(sequenceId, msg));
    }
}