package io.github.fallOut015.pact_magic.server;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.common.angels.Angel;
import io.github.fallOut015.pact_magic.common.capabilities.AngelInstance;
import io.github.fallOut015.pact_magic.common.capabilities.CapabilitiesPactMagic;
import io.github.fallOut015.pact_magic.common.capabilities.DemonInstance;
import io.github.fallOut015.pact_magic.common.capabilities.IPactMagic;
import io.github.fallOut015.pact_magic.common.demons.Demon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

public class SpellCastPacketHandler extends PacketHandlerPactMagic {
	public static final int ID = PacketHandlerPactMagic.getNewID();
	final boolean demon;
	
	public SpellCastPacketHandler(final boolean demon) {
		super(ID);
		this.demon = demon;
	}
	
	public static void encoder(SpellCastPacketHandler msg, PacketBuffer buffer) {
		buffer.writeBoolean(msg.isDemon());
	}
	public static SpellCastPacketHandler decoder(PacketBuffer buffer) {
		return new SpellCastPacketHandler(buffer.readBoolean());
	}
	public static void handle(SpellCastPacketHandler msg, Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	    	ServerPlayerEntity t = ctx.get().getSender();
	    	
	    	LazyOptional<IPactMagic> capability = t.getCapability(CapabilitiesPactMagic.PACT_MAGIC);
	    	capability.ifPresent(pactMagic -> {
		    	if(msg.isDemon()) {
		    		DemonInstance demon = pactMagic.getSlottedDemon();
					if(demon != null) {
						demon.spell();
					}
		    	} else {
					AngelInstance angel = pactMagic.getSlottedAngel();
					if(angel != null) {
						angel.spell();
					}
				}
	    	});
	    });
	    
	    ctx.get().setPacketHandled(true);
	}
	
	public final boolean isDemon() {
		return this.demon;
	}
}