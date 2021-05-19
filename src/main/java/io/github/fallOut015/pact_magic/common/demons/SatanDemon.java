package io.github.fallOut015.pact_magic.common.demons;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class SatanDemon extends Demon {
	SatanDemon() {
		super("satan", 3, Attributes.ATTACK_DAMAGE, Attributes.MAX_HEALTH, false, () -> Ingredient.of(Items.FLINT_AND_STEEL, Items.TORCH, Items.IRON_SWORD));
	}

	@Override
	protected void effect(ServerPlayerEntity t) {
		// TODO Auto-generated method stub
		// Activate Curse
	}
}