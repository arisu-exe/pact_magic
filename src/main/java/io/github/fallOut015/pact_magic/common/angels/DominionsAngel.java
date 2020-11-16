package io.github.fallOut015.pact_magic.common.angels;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;

public class DominionsAngel extends Angel {
	// A light follows the player passively and stays behind when Flash of Light is used.
	
	DominionsAngel() {
		super("dominions", 2, Attributes.MOVEMENT_SPEED, Attributes.ATTACK_DAMAGE, false, 500);
	}

	@Override
	protected void effect(ServerPlayerEntity t) {
		// TODO Auto-generated method stub
		// Flash of Light
	}
}