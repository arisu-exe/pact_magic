package io.github.fallOut015.pact_magic.common.demons;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class MerihemDemon extends Demon {
	MerihemDemon() {
		super("merihem", 1, Attributes.MOVEMENT_SPEED, Attributes.MAX_HEALTH, false, () -> Ingredient.of(Items.POISONOUS_POTATO));
	}

	@Override
	protected void effect(ServerPlayerEntity t) {
		// TODO Auto-generated method stub
		// Activate 
	}
}