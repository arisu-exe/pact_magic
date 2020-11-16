package io.github.fallOut015.pact_magic.server;

import java.util.function.Supplier;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

public class RenderIconPacketHandler extends PacketHandlerPactMagic {
	public static final int ID = PacketHandlerPactMagic.getNewID();
	final ResourceLocation path;
	
	public RenderIconPacketHandler(final ResourceLocation path) {
		super(ID);
		this.path = path;
	}
	
	public static void encoder(RenderIconPacketHandler msg, PacketBuffer buffer) {
		buffer.writeResourceLocation(msg.getPath());
	}
	public static RenderIconPacketHandler decoder(PacketBuffer buffer) {
		return new RenderIconPacketHandler(buffer.readResourceLocation());
	}
	@SuppressWarnings("deprecation")
	public static void handle(RenderIconPacketHandler msg, Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	    	Minecraft minecraft = Minecraft.getInstance();
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0f);
			minecraft.getTextureManager().bindTexture(new ResourceLocation("pact_magic", "textures/gui/border.png"));
			AbstractGui.blit(new MatrixStack(), 8, 8, 0, 0, 48, 48, 48, 48);
	    });
	    
	    ctx.get().setPacketHandled(true);
	}
	
	public final ResourceLocation getPath() {
		return this.path;
	}
}