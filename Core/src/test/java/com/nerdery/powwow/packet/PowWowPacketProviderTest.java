package com.nerdery.powwow.packet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nerdery.powwow.packet.packets.UserPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

public class PowWowPacketProviderTest {
    @Test
    public void testRoundRobbin() throws Exception{
        Injector injector = Guice.createInjector(new PowWowPacketModule());
        PowWowPacketProvider packets = injector.getInstance(PowWowPacketProvider.class);
        UserPacket connectEvent = UserPacket.userConnect("Test");
        AudioEventPacket audioEvent = AudioEventPacket.audioSend("Test", new byte[0]);
        ByteBuf buff = Unpooled.buffer();
        packets.encode(injector, buff, connectEvent);
        UserPacket connectEvent2 = packets.decode(injector, buff);
        System.out.println(connectEvent2.type + ": " + connectEvent2.user);
        buff.clear();

        packets.encode(injector, buff, audioEvent);
        AudioEventPacket audioEvent2 = packets.decode(injector, buff);
        System.out.print(audioEvent2.user + ": " + audioEvent2.audio.length + "bytes");
    }
}