package io.github.fallOut015.pact_magic.common.angels;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.entity.EntityTypePactMagic;
import io.github.fallOut015.pact_magic.entity.effect.angels.SeraphEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;

public class SeraphimAngel extends Angel {
	@Nullable SeraphEntity seraph;
	
	SeraphimAngel() {
		super("seraphim", 3, Attributes.MAX_HEALTH, Attributes.ATTACK_DAMAGE, false, 1000);
		
		this.seraph = null;
	}
	
	@Override
	public void onSlot(ServerPlayerEntity player) {
		super.onSlot(player);
		
		if(this.seraph == null) {
			this.seraph = EntityTypePactMagic.SERAPH.get().spawn(player.getServerWorld(), null, null, player, player.getPosition(), SpawnReason.MOB_SUMMONED, false, false);
			this.seraph.setCaster(player);
			player.getEntityWorld().addEntity(this.seraph);			
		}
	}
	@Override
	public void onUnslot() {
		super.onUnslot();
		
		if(this.seraph != null) {
			this.seraph.remove();
			this.seraph = null;			
		}
	}

	@Override
	protected void effect(ServerPlayerEntity t) {
		if(this.seraph != null) {
			this.seraph.setShielding(!this.seraph.isShielding());
		}
		
		// Activate shield of fire. 
	}
}