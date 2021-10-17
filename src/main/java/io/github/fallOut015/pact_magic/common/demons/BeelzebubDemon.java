package io.github.fallOut015.pact_magic.common.demons;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class BeelzebubDemon extends Demon {
	BeelzebubDemon() {
		super("beelzebub", 7, 3, Attributes.ATTACK_SPEED, Attributes.MAX_HEALTH, false, () -> Ingredient.of(Items.MUTTON, Items.CHICKEN, Items.PORKCHOP));
	}

	@Override
	public void effect(ServerPlayerEntity t) {
		// TODO Auto-generated method stub
		// Activate Lord of Flight
	}
}