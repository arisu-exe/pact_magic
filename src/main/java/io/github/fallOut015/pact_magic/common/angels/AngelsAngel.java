package io.github.fallOut015.pact_magic.common.angels;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;

public class AngelsAngel extends Angel implements IToggleable {
	boolean allowed;

	AngelsAngel() {
		super("angels", 0, 1, Attributes.MAX_HEALTH, Attributes.ATTACK_DAMAGE, false, 1);
		
		this.allowed = true;
	}

	@Override
	public void effect(ServerPlayerEntity t) {
		this.allowed = !this.allowed;
		
		// Toggle Guardian Angel
	}
	
	@Override
	public boolean isOn() {
		return this.allowed;
	}
}