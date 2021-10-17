package io.github.fallOut015.pact_magic.common.capabilities;

import io.github.fallOut015.pact_magic.MainPactMagic;
import io.github.fallOut015.pact_magic.common.angels.Angel;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

public class AngelInstance {
    Angel angel;

    int timer;
    ServerPlayerEntity player;

    AngelInstance(Angel angel, ServerPlayerEntity player) {
        this.angel = angel;
        this.player = player;
    }

    public void onSlot() {
        this.timer = 0;

        MainPactMagic.LOGGER.debug("slotting " + this.angel.getID());

        this.player.getAttribute(this.angel.getBuff()).addTransientModifier(new AttributeModifier(Angel.ANGEL_BUFF, "Angel buff", ((double) this.angel.getRank()), AttributeModifier.Operation.MULTIPLY_BASE));
        if(this.angel.getDebuff() != null) {
            this.player.getAttribute(this.angel.getDebuff()).addTransientModifier(new AttributeModifier(Angel.ANGEL_DEBUFF, "Angel debuff", 1d / ((double) this.angel.getRank() + 1d), AttributeModifier.Operation.MULTIPLY_BASE));
        }
    }
    public void onUnslot() {
        if(this.player.getAttribute(this.angel.getBuff()).getModifier(Angel.ANGEL_BUFF) != null) {
            this.player.getAttribute(this.angel.getBuff()).removeModifier(Angel.ANGEL_BUFF);
        }
        if(this.angel.getDebuff() != null) {
            if(this.player.getAttribute(this.angel.getDebuff()).getModifier(Angel.ANGEL_DEBUFF) != null) {
                this.player.getAttribute(this.angel.getDebuff()).removeModifier(Angel.ANGEL_DEBUFF);
            }
        }

        MainPactMagic.LOGGER.debug("unslotting " + this.angel.getID());
    }
    public void tick() {
        if(this.timer > 0) {
            -- this.timer;
        }
    }
    public int getTimer() {
        return this.timer;
    }
    public boolean isPrepared() {
        return this.timer == 0;
    }
    public void spell() {
        if(this.timer == 0) {
            this.timer = this.angel.getCooldown();
            this.angel.effect(this.player);
        }
    }
    public ResourceLocation getTexture() {
        return this.angel.getTexture();
    }
    public int getCooldown() {
        return this.angel.getCooldown();
    }
    public boolean is(Angel angel) {
        return this.angel == angel;
    }

    String getSerialized() {
        return this.angel.getID() + "&" + this.timer + "&" + this.player.getStringUUID();
    }
}