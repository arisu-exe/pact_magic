package io.github.fallOut015.pact_magic.common.angels;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.registries.ForgeRegistries;

public class VirtuesAngel extends Angel {
	VirtuesAngel() {
		super("virtues", 4, 2, Attributes.LUCK, Attributes.ATTACK_DAMAGE, false, 1000);
	}

	@Override
	public void effect(ServerPlayerEntity t) {
		List<Effect> effects = new LinkedList<Effect>();
		for(Effect effect : ForgeRegistries.POTIONS) {
			if(effect.isBeneficial()) {
				effects.add(effect);
			}
		}
		
		int index = new Random().nextInt(effects.size() + 1);
		t.addEffect(new EffectInstance(effects.get(index), 1000, 0, false, true));
		// Wish
		// TODO a similar thing to a potion effect, a blessing. 1-to-1 with potions, but expires under a different condition (bad deeds)
	}
}