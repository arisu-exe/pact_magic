package io.github.fallOut015.pact_magic.common.capabilities;

import io.github.fallOut015.pact_magic.common.angels.Angels;
import io.github.fallOut015.pact_magic.common.demons.Demons;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

import javax.annotation.Nullable;

public class PactMagic implements IPactMagic {
	final ServerPlayerEntity player;
	
	int angelKarma;
	int demonKarma;
	
	AngelInstance[] angels;
	DemonInstance[] demons;

	int ia, id;

	public PactMagic(ServerPlayerEntity player) {
		this.player = player;
		this.angels = new AngelInstance[]{
			new AngelInstance(Angels.ANGELS, player), new AngelInstance(Angels.ARCHANGELS, player), new AngelInstance(Angels.PRINCIPALITIES, player),
			new AngelInstance(Angels.POWERS, player), new AngelInstance(Angels.VIRTUES, player), new AngelInstance(Angels.DOMINIONS, player),
			new AngelInstance(Angels.THRONES, player), new AngelInstance(Angels.CHERUBIM, player), new AngelInstance(Angels.SERAPHIM, player)
		};
		this.demons = new DemonInstance[]{
			new DemonInstance(Demons.MERIHEM, player), new DemonInstance(Demons.BELIAL, player), new DemonInstance(Demons.PYTHO, player),
			new DemonInstance(Demons.ASMODEUS, player), new DemonInstance(Demons.MAMMON, player), new DemonInstance(Demons.ABADDON, player),
			new DemonInstance(Demons.ASTAROTH, player), new DemonInstance(Demons.BEELZEBUB, player), new DemonInstance(Demons.SATAN, player)
		};
		this.ia = 0;
		this.id = 0;
	}

	public void tick() {
		if(this.getSlottedAngel() != null) {
			this.getSlottedAngel().tick();
		}
		if(this.getSlottedDemon() != null) {
			this.getSlottedDemon().tick();
		}
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
	public AngelInstance getSlottedAngel() {
		if(this.ia == -1) {
			return null;
		} else {
			return this.angels[this.ia];
		}
	}
	@Override
	@Nullable
	public DemonInstance getSlottedDemon() {
		if(this.id == -1) {
			return null;
		} else {
			return this.demons[this.id];
		}
	}
	@Override
	public void slotAngel(int ia) {
		// TODO Return if Angel can't be slotted.
		
		if(this.ia == ia) {
			return;
		}
		if(this.getSlottedAngel() != null) {
			this.getSlottedAngel().onUnslot();
		}
		this.ia = ia;
		if(this.getSlottedAngel() != null) {
			this.getSlottedAngel().onSlot();
		}
	}
	@Override
	public void slotDemon(int id) {
		// Return if Demon can't be slotted.
//		for(ItemStack itemStackIn : slottedDemon.getOffering().getMatchingStacks()) {
//			if(!this.getPlayer().inventory.hasItemStack(itemStackIn)) {
//				return;
//			}
//		}
//		for(ItemStack stack : slottedDemon.getOffering().getMatchingStacks()) {
//			// remove itemstacks matching
//			this.getPlayer().inventory.deleteStack(stack);
//		}

		if(this.id == id) {
			return;
		}
		if(this.getSlottedDemon() != null) {
			this.getSlottedDemon().onUnslot();
		}
		this.id = id;
		if(this.getSlottedDemon() != null) {
			this.getSlottedDemon().onSlot();
		}
	}

	@Override
	public int getAngelIndex() {
		return this.ia;
	}
	@Override
	public int getDemonIndex() {
		return this.id;
	}

	public String getAngelsSerialized() {
		String ret = "";
		for (AngelInstance angel : this.angels) {
			ret += angel.getSerialized() + ";";
		}
		return ret;
	}
	public String getDemonsSerialized() {
		String ret = "";
		for (DemonInstance demon : this.demons) {
			ret += demon.getSerialized() + ";";
		}
		return ret;
	}
}