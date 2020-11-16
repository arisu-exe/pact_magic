package io.github.fallOut015.pact_magic.server;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.common.angels.Angel;
import io.github.fallOut015.pact_magic.common.capabilities.CapabilitiesPactMagic;
import io.github.fallOut015.pact_magic.common.capabilities.IPactMagic;
import io.github.fallOut015.pact_magic.common.demons.Demon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

public class SlotPacketHandler extends PacketHandlerPactMagic {
	public static final int ID = PacketHandlerPactMagic.getNewID();
	final boolean demon;
	final String id;
	
	public SlotPacketHandler(final boolean demon, final String id) {
		super(ID);
		this.demon = demon;
		this.id = id;
	}
	
	public static void encoder(SlotPacketHandler msg, PacketBuffer buffer) {
		buffer.writeBoolean(msg.isDemon());
		buffer.writeString(msg.getID());
	}
	public static SlotPacketHandler decoder(PacketBuffer buffer) {
		return new SlotPacketHandler(buffer.readBoolean(), buffer.readString());
	}
	public static void handle(SlotPacketHandler msg, Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	    	ServerPlayerEntity t = ctx.get().getSender();
	    	
	    	LazyOptional<IPactMagic> capability = t.getCapability(CapabilitiesPactMagic.PACT_MAGIC);
	    	capability.ifPresent(pactMagic -> {
		    	if(msg.isDemon()) {
		    		@Nullable Demon demon = Demon.fromID(msg.getID());
		    		pactMagic.slotDemon(demon);
		    	} else {
		    		@Nullable Angel angel = Angel.fromID(msg.getID());
		    		pactMagic.slotAngel(angel);
		    	}
	    	});
	    });
	    
	    ctx.get().setPacketHandled(true);
	}
	
	public final boolean isDemon() {
		return this.demon;
	}
	public final String getID() {
		return this.id;
	}
}