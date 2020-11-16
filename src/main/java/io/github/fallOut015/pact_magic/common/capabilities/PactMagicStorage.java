package io.github.fallOut015.pact_magic.common.capabilities;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.common.angels.Angel;
import io.github.fallOut015.pact_magic.common.demons.Demon;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PactMagicStorage implements IStorage<IPactMagic> {
	@Override
	public INBT writeNBT(Capability<IPactMagic> capability, IPactMagic instance, Direction side) {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("angelKarma", instance.getAngelKarma());
		nbt.putInt("demonKarma", instance.getDemonKarma());
		@Nullable Angel angel = instance.getSlottedAngel();
		nbt.putString("slottedAngel", angel == null ? "" : angel.getID());
		@Nullable Demon demon = instance.getSlottedDemon();
		nbt.putString("slottedDemon", demon == null ? "" : demon.getID());
		return nbt;
	}
	@Override
	public void readNBT(Capability<IPactMagic> capability, IPactMagic instance, Direction side, INBT nbt) {
		if(nbt instanceof CompoundNBT) {
			instance.setAngelKarma(((CompoundNBT) nbt).getInt("angelKarma"));
			instance.setDemonKarma(((CompoundNBT) nbt).getInt("angelDemon"));
			instance.slotAngel(Angel.fromID(((CompoundNBT) nbt).getString("slottedAngel")));
			instance.slotDemon(Demon.fromID(((CompoundNBT) nbt).getString("slottedDemon")));
		}
	}
}