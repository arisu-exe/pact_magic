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
		nbt.putInt("ia", instance.getAngelIndex());
		nbt.putInt("id", instance.getDemonIndex());
		nbt.putString("angels", instance.getAngelsSerialized());
		nbt.putString("demons", instance.getDemonsSerialized());
		return nbt;
	}
	@Override
	public void readNBT(Capability<IPactMagic> capability, IPactMagic instance, Direction side, INBT nbt) {
		if(nbt instanceof CompoundNBT) {
			instance.setAngelKarma(((CompoundNBT) nbt).getInt("angelKarma"));
			instance.setDemonKarma(((CompoundNBT) nbt).getInt("angelDemon"));
			instance.slotAngel(((CompoundNBT) nbt).getInt("ia"));
			instance.slotDemon(((CompoundNBT) nbt).getInt("id"));
			// deserialize
		}
	}
}