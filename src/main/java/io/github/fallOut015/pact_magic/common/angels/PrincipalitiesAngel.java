package io.github.fallOut015.pact_magic.common.angels;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;

public class PrincipalitiesAngel extends Angel {
	PrincipalitiesAngel() {
		super("principalities", 2, 1, Attributes.ARMOR, Attributes.ATTACK_DAMAGE, false, 500);
	}

	@Override
	public void effect(ServerPlayerEntity t) {
		// TODO Auto-generated method stub
		// Sentry
	}
}