package io.github.fallOut015.pact_magic.client.renderer.entity;

import io.github.fallOut015.pact_magic.client.renderer.entity.layers.HaloLayer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class EntityRendererManagerPactMagic {
    public static void doClientStuff(final FMLClientSetupEvent event) {
    	Minecraft.getInstance().getRenderManager().getSkinMap().get("default").addLayer(new HaloLayer(Minecraft.getInstance().getRenderManager().getSkinMap().get("default")));
    	Minecraft.getInstance().getRenderManager().getSkinMap().get("slim").addLayer(new HaloLayer(Minecraft.getInstance().getRenderManager().getSkinMap().get("slim")));
    }
}