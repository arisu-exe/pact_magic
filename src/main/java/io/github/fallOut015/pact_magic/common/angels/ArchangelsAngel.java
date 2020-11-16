package io.github.fallOut015.pact_magic.common.angels;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;

public class ArchangelsAngel extends Angel {
	ArchangelsAngel() {
		super("archangels", 1, Attributes.MAX_HEALTH, Attributes.ATTACK_DAMAGE, true, 500);
	}

	@Override
	protected void effect(ServerPlayerEntity t) {
		// TODO Auto-generated method stub
	}
}