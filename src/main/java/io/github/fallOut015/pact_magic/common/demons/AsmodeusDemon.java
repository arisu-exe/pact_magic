package io.github.fallOut015.pact_magic.common.demons;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class AsmodeusDemon extends Demon {
	AsmodeusDemon() {
		super("asmodeus", 3, 2, Attributes.MOVEMENT_SPEED, Attributes.MAX_HEALTH, false, () -> Ingredient.of(ItemStack.EMPTY));
	}

	@Override
	public void effect(ServerPlayerEntity t) {
		// TODO Auto-generated method stub
		// Activate Sweet Revenge
	}
}