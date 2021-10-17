package io.github.fallOut015.pact_magic.server;

import java.util.function.Supplier;

import io.github.fallOut015.pact_magic.common.capabilities.CapabilitiesPactMagic;
import io.github.fallOut015.pact_magic.common.capabilities.IPactMagic;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

public class SlotPacketHandler extends PacketHandlerPactMagic {
	public static final int ID = PacketHandlerPactMagic.getNewID();
	final boolean demon;
	final boolean disableIfSlotted;
	final int index;
	
	public SlotPacketHandler(final boolean demon, final boolean disableIfSlotted, final int index) {
		super(ID);
		this.demon = demon;
		this.disableIfSlotted = disableIfSlotted;
		this.index = index;
	}
	
	public static void encoder(SlotPacketHandler msg, PacketBuffer buffer) {
		buffer.writeBoolean(msg.demon);
		buffer.writeBoolean(msg.disableIfSlotted);
		buffer.writeInt(msg.index);
	}
	public static SlotPacketHandler decoder(PacketBuffer buffer) {
		return new SlotPacketHandler(buffer.readBoolean(), buffer.readBoolean(), buffer.readInt());
	}
	public static void handle(SlotPacketHandler msg, Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	    	ServerPlayerEntity t = ctx.get().getSender();
	    	
	    	LazyOptional<IPactMagic> capability = t.getCapability(CapabilitiesPactMagic.PACT_MAGIC);
	    	capability.ifPresent(pactMagic -> {
		    	if(msg.demon) {
		    		pactMagic.slotDemon(msg.disableIfSlotted && msg.index == pactMagic.getDemonIndex() ? -1 : msg.index);
		    	} else {
					pactMagic.slotAngel(msg.disableIfSlotted && msg.index == pactMagic.getAngelIndex() ? -1 : msg.index);
		    	}
	    	});
	    });
	    
	    ctx.get().setPacketHandled(true);
	}
}