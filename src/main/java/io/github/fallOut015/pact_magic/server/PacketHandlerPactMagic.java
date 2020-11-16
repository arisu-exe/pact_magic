package io.github.fallOut015.pact_magic.server;

import io.github.fallOut015.pact_magic.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public abstract class PacketHandlerPactMagic {
	private static int ids = 0;
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
	    new ResourceLocation(Main.MODID, "main"),
	    () -> PROTOCOL_VERSION,
	    PROTOCOL_VERSION::equals,
	    PROTOCOL_VERSION::equals
	);
	
	final int id;
	
	protected PacketHandlerPactMagic(int id) {
		this.id = id;
	}
	
	public static int getNewID() {
		return ids++;
	}
    public static void setup(final FMLCommonSetupEvent event) {
		INSTANCE.registerMessage(SpellCastPacketHandler.ID, SpellCastPacketHandler.class, SpellCastPacketHandler::encoder, SpellCastPacketHandler::decoder, SpellCastPacketHandler::handle);
		INSTANCE.registerMessage(RenderIconPacketHandler.ID, RenderIconPacketHandler.class, RenderIconPacketHandler::encoder, RenderIconPacketHandler::decoder, RenderIconPacketHandler::handle);
		INSTANCE.registerMessage(RequestPlayerPacketHandler.ID, RequestPlayerPacketHandler.class, RequestPlayerPacketHandler::encoder, RequestPlayerPacketHandler::decoder, RequestPlayerPacketHandler::handle);
		INSTANCE.registerMessage(JumpPacketHandler.ID, JumpPacketHandler.class, JumpPacketHandler::encoder, JumpPacketHandler::decoder, JumpPacketHandler::handle);
		INSTANCE.registerMessage(SlotPacketHandler.ID,SlotPacketHandler.class, SlotPacketHandler::encoder, SlotPacketHandler::decoder, SlotPacketHandler::handle);
    }
}