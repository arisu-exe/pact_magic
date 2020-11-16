package io.github.fallOut015.pact_magic.server;

import java.util.function.Supplier;

import io.github.fallOut015.pact_magic.common.angels.Angels;
import io.github.fallOut015.pact_magic.common.angels.IToggleable;
import io.github.fallOut015.pact_magic.common.capabilities.CapabilitiesPactMagic;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class JumpPacketHandler extends PacketHandlerPactMagic {
	public static final int ID = PacketHandlerPactMagic.getNewID();
	
	public JumpPacketHandler() {
		super(ID);
	}
	
	public static void encoder(JumpPacketHandler msg, PacketBuffer buffer) {
	}
	public static JumpPacketHandler decoder(PacketBuffer buffer) {
		return new JumpPacketHandler();
	}
	public static void handle(JumpPacketHandler msg, Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        ServerPlayerEntity sender = ctx.get().getSender();
	        
	        sender.getCapability(CapabilitiesPactMagic.PACT_MAGIC).ifPresent(pactMagic -> {
	        	if(!sender.isOnGround() && !sender.isInWater() && pactMagic.getSlottedAngel() == Angels.CHERUBIM && pactMagic.getSlottedAngel().isPrepared() && ((IToggleable) pactMagic.getSlottedAngel()).isOn()) {
	        		sender.setMotion(sender.getMotion().getX(), 0, sender.getMotion().getZ());
		        	sender.jump();
		        	sender.velocityChanged = true;

		        	// TODO particle effects and stuff
	        	}
	        });
	    });
	    ctx.get().setPacketHandled(true);
	}
}