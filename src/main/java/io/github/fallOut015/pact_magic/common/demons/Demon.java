package io.github.fallOut015.pact_magic.common.demons;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.Main;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;

public abstract class Demon {
	static final Map<String, Demon> ID_MAP = new HashMap<String, Demon>();
	
	static final UUID DEMON_BUFF = UUID.fromString("4a50989b-8870-4196-8b48-27f1f6b1595e");
	static final UUID DEMON_DEBUFF = UUID.fromString("72b89da5-432c-403a-a5a3-a65c5c18a258");

	final String id;
	final int rank;
	final Attribute buff;
	@Nullable final Attribute debuff;
	final boolean autoActivate; // true to disallow right clicking
	final LazyValue<Ingredient> offering;
	final ResourceLocation texture;
	final ResourceLocation buff_texture;
	@Nullable final ResourceLocation debuff_texture;
	
	@Nullable ServerPlayerEntity player;
	
	Demon(final String id, final int rank, Attribute buff, @Nullable Attribute debuff, final boolean autoActivate, final Supplier<Ingredient> offering) {
		this.id = id;
		this.rank = rank;
		this.buff = buff;
		this.debuff = debuff;
		this.autoActivate = autoActivate;
		this.offering = new LazyValue<>(offering);
		this.texture = new ResourceLocation("pact_magic", "textures/gui/demons/" + id + ".png");
		this.buff_texture = new ResourceLocation("pact_magic", "textures/gui/" + buff.getAttributeName() + ".png");
		if(this.debuff == null) {
			this.debuff_texture = null;
		} else {
			this.debuff_texture = new ResourceLocation("pact_magic", "textures/gui/" + debuff.getAttributeName() + ".png");
		}
		
		ID_MAP.put(this.id, this);
	}
	
	public final String getID() {
		return this.id;
	}
	public final int getRank() {
		return this.rank;
	}
	public final Attribute getBuff() {
		return buff;
	}
	@Nullable public final Attribute getDebuff() {
		return debuff;
	}
	public final boolean autoActivates() {
		return this.autoActivate;
	}
	public final Ingredient getOffering() {
		return this.offering.getValue();
	}
	
	public void onSlot(@Nonnull ServerPlayerEntity player) {
		this.player = player;
		
		Main.LOGGER.debug("slotting " + this.getID());

		this.player.getAttribute(this.buff).applyNonPersistentModifier(new AttributeModifier(DEMON_BUFF, "Demon buff", (double) this.rank, Operation.MULTIPLY_BASE));
		if(this.debuff != null) {
			this.player.getAttribute(this.debuff).applyNonPersistentModifier(new AttributeModifier(DEMON_DEBUFF, "Demon debuff", 1d / ((double) this.rank + 1d), Operation.MULTIPLY_BASE));
		}
	}
	public void onUnslot() {
		if(this.player.getAttribute(this.buff).getModifier(DEMON_BUFF) != null) {
			this.player.getAttribute(this.buff).removeModifier(DEMON_BUFF);
		}
		if(this.debuff != null) {
			if(this.player.getAttribute(this.debuff).getModifier(DEMON_DEBUFF) != null) {
				this.player.getAttribute(this.debuff).removeModifier(DEMON_DEBUFF);
			}
		}
		
		Main.LOGGER.debug("unslotting " + this.getID());
			
		this.player = null;
	}
	@Nullable public ServerPlayerEntity getPlayer() {
		return this.player;
	}
	public final ResourceLocation getTexture() {
		return this.texture;
	}
	public final ResourceLocation getBuffTexture() {
		return this.buff_texture;
	}
	@Nullable public final ResourceLocation getDebuffTexture() {
		return this.debuff_texture;
	}
	
	public void tick() { }
	public void spell(ServerPlayerEntity t) {
		this.effect(t);
	}
	protected abstract void effect(ServerPlayerEntity t); // TODO curse ability? opposite of wish/bless
	
	public static Demon fromID(String key) {
		return ID_MAP.get(key);
	}
	public static Collection<Demon> demons() {
		return ID_MAP.values();
	}
}