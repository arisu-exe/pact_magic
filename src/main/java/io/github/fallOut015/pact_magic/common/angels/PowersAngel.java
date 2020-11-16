package io.github.fallOut015.pact_magic.common.angels;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;

public class PowersAngel extends Angel implements IToggleable {
	boolean allowed;
	
	PowersAngel() {
		super("powers", 2, Attributes.ARMOR, null, true, 0);
	}

	@Override
	protected void effect(ServerPlayerEntity t) {
		this.allowed = !this.allowed;
		// Toggle Fighting Spirit
	}
	
	@Override
	public boolean isOn() {
		return this.allowed;
	}
	@Override
	public boolean isOff() {
		return !this.allowed;
	}
}