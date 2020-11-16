package io.github.fallOut015.pact_magic.common.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CapabilitiesPactMagic {
	@CapabilityInject(IPactMagic.class)
	public static final Capability<IPactMagic> PACT_MAGIC = null;
	
	public static void setup(final FMLCommonSetupEvent event) {
    	CapabilityManager.INSTANCE.register(IPactMagic.class, new PactMagicStorage(), new PactMagicCallable());
	}
}