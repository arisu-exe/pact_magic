package io.github.fallOut015.pact_magic.common.angels;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.MainPactMagic;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class Angel {
	static final Map<String, Angel> ID_MAP = new HashMap<String, Angel>();
	
	public static final UUID ANGEL_BUFF = UUID.fromString("1557100e-1de1-41ac-9ab4-ba6f116d751d");
	public static final UUID ANGEL_DEBUFF = UUID.fromString("06ca0283-cdfd-466e-9c57-71cb4160cd44");
	
	final String id;
	final int index;
	final int rank;
	final Attribute buff;
	@Nullable final Attribute debuff;
	final boolean autoActivate; // true to disallow right clicking
	final int cooldown;
	final ResourceLocation texture;
	final ResourceLocation buff_texture;
	@Nullable final ResourceLocation debuff_texture;
	final ITextComponent translation_key;
	final ITextComponent desc_key;
	
	Angel(final String id, final int index, final int rank, Attribute buff, @Nullable Attribute debuff, final boolean autoActivate, final int cooldown) {
		this.id = id;
		this.index = index;
		this.rank = rank;
		this.buff = buff;
		this.debuff = debuff;
		this.autoActivate = autoActivate;
		this.cooldown = cooldown;
		this.texture = new ResourceLocation("pact_magic", "textures/gui/angels/" + id + ".png");
		this.buff_texture = new ResourceLocation("pact_magic", "textures/gui/" + buff.getDescriptionId() + ".png");
		if(this.debuff == null) {
			this.debuff_texture = null;
		} else {
			this.debuff_texture = new ResourceLocation("pact_magic", "textures/gui/" + debuff.getDescriptionId() + ".png");
		}
		this.translation_key = new TranslationTextComponent("gui." + id + ".title");
		this.desc_key = new TranslationTextComponent("gui." + id + ".desc");

		ID_MAP.put(this.id, this);
	}
	
	public final String getID() {
		return this.id;
	}
	public final int getIndex() {
		return this.index;
	}
	public final int getRank() {
		return this.rank;
	}
	public final Attribute getBuff() {
		return buff;
	}
	@Nullable
	public final Attribute getDebuff() {
		return debuff;
	}
	public final boolean autoActivates() {
		return this.autoActivate;
	}
	public final int getCooldown() {
		return this.cooldown;
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
	public final ITextComponent getTranslationKey() {
		return this.translation_key;
	}
	public final ITextComponent getDescKey() {
		return this.desc_key;
	}

	public abstract void effect(ServerPlayerEntity t);
	
	public static Angel fromID(String key) {
		return ID_MAP.get(key);
	}
	public static Collection<Angel> angels() {
		return ID_MAP.values();
	}
}