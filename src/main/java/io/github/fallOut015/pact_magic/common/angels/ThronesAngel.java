package io.github.fallOut015.pact_magic.common.angels;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;

public class ThronesAngel extends Angel {
	ThronesAngel() {
		super("thrones", 3, Attributes.MOVEMENT_SPEED, Attributes.ATTACK_DAMAGE, false, 500);
	}

	@Override
	protected void effect(ServerPlayerEntity t) {
		// TODO Auto-generated method stub
	}
}