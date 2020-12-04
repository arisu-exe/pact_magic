package io.github.fallOut015.pact_magic.client.gui.widget.button;

import java.util.function.Function;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import io.github.fallOut015.pact_magic.Main;
import io.github.fallOut015.pact_magic.common.angels.Angel;
import io.github.fallOut015.pact_magic.common.capabilities.CapabilitiesPactMagic;
import io.github.fallOut015.pact_magic.server.PacketHandlerPactMagic;
import io.github.fallOut015.pact_magic.server.SlotPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AngelButton extends Button {
	static final Function<Integer, TranslationTextComponent> SLOT = rank -> new TranslationTextComponent("gui.slot_cost.angel", rank);
	static final Function<Integer, TranslationTextComponent> SWAP = rank -> new TranslationTextComponent("gui.swap_cost.angel", rank);
	static final Function<Integer, TranslationTextComponent> UNSLOT = rank -> new TranslationTextComponent("gui.unslot_cost.angel", rank);
	
	final PlayerEntity player;
	final Angel slottedAngel;
	
	@SuppressWarnings("resource")
	public AngelButton(int x, int y, final PlayerEntity player, final Angel slottedAngel, Screen screen) {
		super(x, y, 64, 64, new TranslationTextComponent("gui." + slottedAngel.getID() + ".title"), button -> {
			player.getCapability(CapabilitiesPactMagic.PACT_MAGIC).ifPresent(pactMagic -> {
				if(pactMagic.getSlottedAngel() == slottedAngel) {
					PacketHandlerPactMagic.INSTANCE.sendToServer(new SlotPacketHandler(false, ""));
				} else {
					PacketHandlerPactMagic.INSTANCE.sendToServer(new SlotPacketHandler(false, slottedAngel.getID()));
				}
			});
		}, (button, stack, x1, y1) -> {
			if(button instanceof AngelButton) {
				player.getCapability(CapabilitiesPactMagic.PACT_MAGIC).ifPresent(pactMagic -> {
					if(pactMagic.getSlottedAngel() == slottedAngel) {
						screen.renderTooltip(stack, screen.getMinecraft().fontRenderer.trimStringToWidth(UNSLOT.apply(slottedAngel.getRank()), 200), x1, y1);
					} else if(pactMagic.getSlottedAngel() == null) {
						screen.renderTooltip(stack, screen.getMinecraft().fontRenderer.trimStringToWidth(SLOT.apply(slottedAngel.getRank()), 200), x1, y1);
					} else {
						screen.renderTooltip(stack, screen.getMinecraft().fontRenderer.trimStringToWidth(SWAP.apply(slottedAngel.getRank()), 200), x1, y1);
					}
				});
			}
		});
		
		this.player = player;
		this.slottedAngel = slottedAngel;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		if(this.isHovered()) {
			this.renderToolTip(matrixStack, mouseX, mouseY);
		}
	}
}