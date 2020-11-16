package io.github.fallOut015.pact_magic.common.demons;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.Ingredient;

public class LuciferDemon extends Demon {
	LuciferDemon() {
		super("lucifer", 3, Attributes.ATTACK_KNOCKBACK, Attributes.MAX_HEALTH, false, () -> Ingredient.fromItems());
	}

	@Override
	protected void effect(ServerPlayerEntity t) {
		// TODO Auto-generated method stub
		// Activate Morning Sun
	}
}