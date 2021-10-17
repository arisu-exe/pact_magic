package io.github.fallOut015.pact_magic.common.angels;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;

public class CherubimAngel extends Angel implements IToggleable {
	boolean allowed;
	
	CherubimAngel() {
		super("cherubim", 7, 3, Attributes.ARMOR, Attributes.ATTACK_DAMAGE, false, 1);
		
		this.allowed = true;
	}

	@Override
	public void effect(ServerPlayerEntity t) {
		this.allowed = !this.allowed;
		
		// Toggle Wings of Flight
	}
	
	@Override
	public boolean isOn() {
		return this.allowed;
	}
}