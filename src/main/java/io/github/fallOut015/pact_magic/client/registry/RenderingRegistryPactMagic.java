package io.github.fallOut015.pact_magic.client.registry;

import io.github.fallOut015.pact_magic.client.renderer.entity.MammonRenderer;
import io.github.fallOut015.pact_magic.client.renderer.entity.SeraphRenderer;
import io.github.fallOut015.pact_magic.client.renderer.entity.SigilRenderer;
import io.github.fallOut015.pact_magic.entity.EntityTypePactMagic;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class RenderingRegistryPactMagic {
	public static void doClientStuff(final FMLClientSetupEvent event) {
    	RenderingRegistry.registerEntityRenderingHandler(EntityTypePactMagic.SERAPH.get(), SeraphRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(EntityTypePactMagic.MAMMON.get(), MammonRenderer::new);
    	RenderingRegistry.registerEntityRenderingHandler(EntityTypePactMagic.SIGIL.get(), SigilRenderer::new);
    }
}