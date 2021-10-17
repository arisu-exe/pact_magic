package io.github.fallOut015.pact_magic.common.capabilities;

import io.github.fallOut015.pact_magic.MainPactMagic;
import io.github.fallOut015.pact_magic.common.demons.Demon;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class DemonInstance {
    Demon demon;

    ServerPlayerEntity player;

    DemonInstance(Demon demon, ServerPlayerEntity player) {
        this.demon = demon;
        this.player = player;
    }

    public void onSlot() {
        MainPactMagic.LOGGER.debug("slotting " + this.demon.getID());

        this.player.getAttribute(this.demon.getBuff()).addTransientModifier(new AttributeModifier(Demon.DEMON_BUFF, "Demon buff", (double) this.demon.getRank(), AttributeModifier.Operation.MULTIPLY_BASE));
        if(this.demon.getDebuff() != null) {
            this.player.getAttribute(this.demon.getDebuff()).addTransientModifier(new AttributeModifier(Demon.DEMON_DEBUFF, "Demon debuff", 1d / ((double) this.demon.getRank() + 1d), AttributeModifier.Operation.MULTIPLY_BASE));
        }
    }
    public void onUnslot() {
        if(this.player.getAttribute(this.demon.getBuff()).getModifier(Demon.DEMON_BUFF) != null) {
            this.player.getAttribute(this.demon.getBuff()).removeModifier(Demon.DEMON_BUFF);
        }
        if(this.demon.getDebuff() != null) {
            if(this.player.getAttribute(this.demon.getDebuff()).getModifier(Demon.DEMON_DEBUFF) != null) {
                this.player.getAttribute(this.demon.getDebuff()).removeModifier(Demon.DEMON_DEBUFF);
            }
        }

        MainPactMagic.LOGGER.debug("unslotting " + this.demon.getID());
    }
    public void tick() {

    }
    public void spell() {
        this.demon.effect(this.player);
    }
    public ResourceLocation getTexture() {
        return this.demon.getTexture();
    }

    String getSerialized() {
        return this.demon.getID() + "&" + this.player.getStringUUID();
    }
}