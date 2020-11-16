package io.github.fallOut015.pact_magic.common.capabilities;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.common.angels.Angel;
import io.github.fallOut015.pact_magic.common.demons.Demon;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public class PactMagic implements IPactMagic {
	final ServerPlayerEntity player;
	
	int angelKarma;
	int demonKarma;
	
	@Nullable Angel slottedAngel;
	@Nullable Demon slottedDemon;
	
	
	
	public void tick() {
		if(this.slottedAngel != null) {
			this.slottedAngel.tick();
		}
		if(this.slottedDemon != null) {
			this.slottedDemon.tick();
		}
	}
	
	public PactMagic(ServerPlayerEntity player) {
		this.player = player;
	}
	
	public PlayerEntity getPlayer() {
		return this.player;
	}
	
	@Override
	public int getAngelKarma() {
		return this.angelKarma;
	}
	@Override
	public int getDemonKarma() {
		return this.demonKarma;
	}
	@Override
	public void setAngelKarma(int angelKarma) {
		this.angelKarma = angelKarma;
	}
	@Override
	public void setDemonKarma(int demonKarma) {
		this.demonKarma = demonKarma;
	}
	@Override
	public void spendAngelKarma(int rank) {
		this.angelKarma -= rank;
	}
	@Override
	public void spendDemonKarma(int rank) {
		this.demonKarma -= rank;
	}
	
	@Override
	@Nullable
	public Angel getSlottedAngel() {
		return this.slottedAngel;
	}
	@Override
	@Nullable
	public Demon getSlottedDemon() {
		return this.slottedDemon;
	}
	@Override
	public void slotAngel(@Nullable Angel slottedAngel) {
		if(this.slottedAngel == slottedAngel) {
			return;
		}
		if(this.slottedAngel != null) {
			this.slottedAngel.onUnslot();
		}
		this.slottedAngel = slottedAngel;
		if(this.slottedAngel != null) {
			this.slottedAngel.onSlot(this.player);
		}
	}
	@Override
	public void slotDemon(@Nullable Demon slottedDemon) {
		if(this.slottedDemon == slottedDemon) {
			return;
		}
		if(this.slottedDemon != null) {
			this.slottedDemon.onUnslot();
		}
		this.slottedDemon = slottedDemon;
		if(this.slottedDemon != null) {
			this.slottedDemon.onSlot(this.player);
		}
	}
}