package com.nerdery.powwow.event;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.nerdery.powwow.packet.PacketIdentifiers;
import com.nerdery.powwow.packet.PowWowPacket;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

public final class SubscribePacketPolicy{
    private final Injector injector;

    @Inject
    private SubscribePacketPolicy(Injector injector){
        this.injector = injector;
    }

    @SuppressWarnings("unchecked")
    public Object transposePolicies(Object instance) throws Exception{
        ClassNode rootNode = new ClassNode();
        List<MethodNode> hoisted = new LinkedList<>();

        for(MethodNode methodNode : ((List<MethodNode>) rootNode.methods)){
            MethodNode foundNode = null;
            PowWowPacket.TransposingInfo<?> info = null;

            for(AnnotationNode annotationNode : ((List<AnnotationNode>) methodNode.visibleAnnotations)){
                if(annotationNode.desc.equals("L" + SubscribePacket.class.getSimpleName() + ";")){
                    int packetId = ((int) annotationNode.values.get(2));

                    foundNode = methodNode;
                    info = this.injector.getInstance(Key.get(PowWowPacket.TransposingInfo.class, PacketIdentifiers.identifiedBy(packetId)));
                    break;
                }
            }

            if(foundNode != null){
            }
        }
    }
}