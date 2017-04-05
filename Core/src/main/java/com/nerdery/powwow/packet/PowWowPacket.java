package com.nerdery.powwow.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public abstract class PowWowPacket{
    public static interface Encoder<T extends PowWowPacket>{
        public void encode(T tPacket, ByteBuf buf);
    }

    public static interface Decoder<T extends PowWowPacket>{
        public T decode(ByteBuf buf);
    }

    public static final class ChannelSpecificPacket<T extends PowWowPacket>
    extends PowWowPacket{
        public final T owner;
        public final Channel channel;

        public ChannelSpecificPacket(T owner, Channel channel){
            this.channel = channel;
            this.owner = owner;
        }
    }

    public static abstract class Codec<T extends PowWowPacket>
    implements Encoder<T>, Decoder<T>{
        protected final void writeUtf8(ByteBuf buf, String str){
            buf.writeInt(str.length());
            buf.writeBytes(str.getBytes(StandardCharsets.UTF_8));
        }

        protected final String readUtf8(ByteBuf buf){
            int len = buf.readInt();
            byte[] bytes = new byte[len];
            buf.readBytes(bytes, 0, len);
            return new String(bytes, StandardCharsets.UTF_8);
        }

        protected final void writeBytes(ByteBuf buf, byte[] bytes){
            buf.writeInt(bytes.length);
            buf.writeBytes(bytes);
        }

        protected final byte[] readBytes(ByteBuf buf){
            int len = buf.readInt();
            byte[] bytes = new byte[len];
            buf.readBytes(bytes, 0, len);
            return bytes;
        }

        protected final <E extends Enum> void writeEnumValue(ByteBuf buf, E e){
            buf.writeInt(e.ordinal());
        }

        @SuppressWarnings("unchecked")
        protected final <E extends Enum> E readEnum(ByteBuf buf, Class<E> tClass){
            int ordinal = buf.readInt();

            try{
                Method valuesMethod = tClass.getDeclaredMethod("values");
                valuesMethod.setAccessible(true);
                E[] values = ((E[]) valuesMethod.invoke(null));
                return values[ordinal];
            } catch(Exception e){
                throw new RuntimeException("Unable to read back enum for class: " + tClass.getSimpleName() + "; ordinal: " + ordinal, e);
            }
        }
    }
}