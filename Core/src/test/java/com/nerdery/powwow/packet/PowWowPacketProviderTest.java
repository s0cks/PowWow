package com.nerdery.powwow.packet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

public class PowWowPacketProviderTest {
    @Test
    public void testRoundRobbin() throws Exception{
        Injector injector = Guice.createInjector(new PowWowPacketModule());
        PowWowPacketProvider packets = injector.getInstance(PowWowPacketProvider.class);
        UserEventPacket connectEvent = UserEventPacket.userConnect("Test");
        ByteBuf buff = Unpooled.buffer();
        packets.encode(injector, buff, connectEvent);
        UserEventPacket connectEvent2 = packets.decode(injector, buff);
        System.out.println(connectEvent2.type + ": " + connectEvent2.user);
    }
}