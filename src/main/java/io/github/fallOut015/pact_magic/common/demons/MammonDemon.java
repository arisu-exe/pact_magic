package io.github.fallOut015.pact_magic.common.demons;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.entity.EntitiesPactMagic;
import io.github.fallOut015.pact_magic.entity.effect.demons.MammonEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class MammonDemon extends Demon {
	@Nullable MammonEntity mammon;

	MammonDemon() {
		super("mammon", 4, 2, Attributes.ARMOR, Attributes.MAX_HEALTH, false, () -> Ingredient.of(Items.GOLD_INGOT, Items.IRON_INGOT));
	
		this.mammon = null;
	}

	/*@Override
	public void onSlot(ServerPlayerEntity player) {
		super.onSlot(player);
		
		if(this.mammon == null) {
			this.mammon = EntitiesPactMagic.MAMMON.get().spawn(player.getLevel(), null, null, player, player.blockPosition(), SpawnReason.MOB_SUMMONED, false, false);
			this.mammon.setCaster(player);
			player.getCommandSenderWorld().addFreshEntity(this.mammon);			
		}
	}
	@Override
	public void onUnslot() {
		super.onUnslot();
		
		if(this.mammon != null) {
			this.mammon.remove();
			this.mammon = null;			
		}
	}*/
	@Override
	public void effect(ServerPlayerEntity t) {
		// Activate Ensnare
		
		if(this.mammon != null) {
			this.mammon.effect();
		}
	}
}