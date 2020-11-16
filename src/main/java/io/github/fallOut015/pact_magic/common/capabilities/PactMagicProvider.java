package io.github.fallOut015.pact_magic.common.capabilities;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class PactMagicProvider implements ICapabilityProvider/*, ICapabilitySerializable<CompoundNBT>*/ {
	final IPactMagic pactMagic;
	
	public PactMagicProvider(final IPactMagic pactMagic) {
		this.pactMagic = pactMagic;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(cap == CapabilitiesPactMagic.PACT_MAGIC) {
			return (LazyOptional<T>) LazyOptional.of(() -> this.pactMagic);
		}
		return null;
	}
}