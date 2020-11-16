package io.github.fallOut015.pact_magic.common.demons;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.entity.EntityTypePactMagic;
import io.github.fallOut015.pact_magic.entity.effect.demons.MammonEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class MammonDemon extends Demon {
	@Nullable MammonEntity mammon;

	MammonDemon() {
		super("mammon", 2, Attributes.MOVEMENT_SPEED, Attributes.MAX_HEALTH, false, () -> Ingredient.fromItems(Items.GOLD_INGOT, Items.GOLD_INGOT));
	
		this.mammon = null;
	}

	@Override
	public void onSlot(ServerPlayerEntity player) {
		super.onSlot(player);
		
		if(this.mammon == null) {
			this.mammon = EntityTypePactMagic.MAMMON.get().spawn(player.getServerWorld(), null, null, player, player.getPosition(), SpawnReason.MOB_SUMMONED, false, false);
			this.mammon.setCaster(player);
			player.getEntityWorld().addEntity(this.mammon);			
		}
	}
	@Override
	public void onUnslot() {
		super.onUnslot();
		
		if(this.mammon != null) {
			this.mammon.remove();
			this.mammon = null;			
		}
	}
	@Override
	protected void effect(ServerPlayerEntity t) {
		// Activate Ensnare
		
		if(this.mammon != null) {
			this.mammon.effect();
		}
	}
}