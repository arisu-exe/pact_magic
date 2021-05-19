package io.github.fallOut015.pact_magic.client.renderer.entity;

import io.github.fallOut015.pact_magic.client.renderer.entity.layers.HaloLayer;
import io.github.fallOut015.pact_magic.client.renderer.entity.layers.WingsLayer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class EntityRendererManagerPactMagic {
    public static void doClientStuff(final FMLClientSetupEvent event) {
    	Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default").addLayer(new HaloLayer(Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default")));
    	Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("slim").addLayer(new HaloLayer(Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("slim")));

        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default").addLayer(new WingsLayer(Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default")));
        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("slim").addLayer(new WingsLayer(Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("slim")));
    }
}