package io.github.fallOut015.pact_magic.server;

import java.util.function.Supplier;

import io.github.fallOut015.pact_magic.MainPactMagic;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class RequestPlayerPacketHandler extends PacketHandlerPactMagic {
	public static final int ID = PacketHandlerPactMagic.getNewID();
	static ServerPlayerEntity player;
	
	public RequestPlayerPacketHandler() {
		super(ID);
	}
	
	public static void encoder(RequestPlayerPacketHandler msg, PacketBuffer buffer) {
		MainPactMagic.LOGGER.debug("Encoding {} to {}", msg, buffer);
	}
	public static RequestPlayerPacketHandler decoder(PacketBuffer buffer) {
		MainPactMagic.LOGGER.debug("Decoding {}", buffer);
		return new RequestPlayerPacketHandler();
	}
	public static void handle(RequestPlayerPacketHandler msg, Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
			MainPactMagic.LOGGER.debug("received request for player from client");
			
			RequestPlayerPacketHandler.player = ctx.get().getSender();
	    });
	    
	    ctx.get().setPacketHandled(true);
	}
	
	public static ServerPlayerEntity getPlayer() {
		return player;
	}
}