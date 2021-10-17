package io.github.fallOut015.pact_magic.common.demons;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class BelialDemon extends Demon {
	BelialDemon() {
		super("belial", 1, 1, Attributes.MOVEMENT_SPEED, Attributes.MAX_HEALTH, false, () -> Ingredient.of(Items.CRAFTING_TABLE));
	}

	@Override
	public void effect(ServerPlayerEntity t) {
		// TODO Auto-generated method stub
		// Activate 
	}
}