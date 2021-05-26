package io.github.fallOut015.pact_magic.client.gui.widget.button;

import java.util.function.Function;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import io.github.fallOut015.pact_magic.MainPactMagic;
import io.github.fallOut015.pact_magic.common.capabilities.CapabilitiesPactMagic;
import io.github.fallOut015.pact_magic.common.demons.Demon;
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
public class DemonButton extends Button {
	static final Function<Integer, TranslationTextComponent> SLOT = rank -> new TranslationTextComponent("gui.slot_cost.demon", rank);
	static final Function<Integer, TranslationTextComponent> SWAP = rank -> new TranslationTextComponent("gui.swap_cost.demon", rank);
	static final Function<Integer, TranslationTextComponent> UNSLOT = rank -> new TranslationTextComponent("gui.unslot_cost.demon", rank);
	
	final PlayerEntity player;
	final Demon slottedDemon;
	
	@SuppressWarnings("resource")
	public DemonButton(int x, int y, final PlayerEntity player, final Demon slottedDemon, Screen screen) {
		super(x, y, 64, 64, new TranslationTextComponent("gui." + slottedDemon.getID() + ".title"), button -> {
			player.getCapability(CapabilitiesPactMagic.PACT_MAGIC).ifPresent(pactMagic -> {
				if(pactMagic.getSlottedDemon() == slottedDemon) {
					PacketHandlerPactMagic.INSTANCE.sendToServer(new SlotPacketHandler(true, ""));
				} else {
					PacketHandlerPactMagic.INSTANCE.sendToServer(new SlotPacketHandler(true, slottedDemon.getID()));
				}
			});
		}, (button, stack, x1, y1) -> {
			if(button instanceof DemonButton) {
				player.getCapability(CapabilitiesPactMagic.PACT_MAGIC).ifPresent(pactMagic -> {
					if(pactMagic.getSlottedDemon() == slottedDemon) {
						screen.renderTooltip(stack, screen.getMinecraft().font.split(UNSLOT.apply(slottedDemon.getRank()), 200), x1, y1);
					} else if(pactMagic.getSlottedDemon() == null) {
						screen.renderTooltip(stack, screen.getMinecraft().font.split(SLOT.apply(slottedDemon.getRank()), 200), x1, y1);
						// And offering
					} else {
						screen.renderTooltip(stack, screen.getMinecraft().font.split(SWAP.apply(slottedDemon.getRank()), 200), x1, y1);
						// And offering
					}
				});
			}
		});
		
		this.player = player;
		this.slottedDemon = slottedDemon;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		
		// Background / border
		minecraft.getTextureManager().bind(MainPactMagic.BORDER);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		blit(matrixStack, this.x, this.y, 0, 0, this.width, this.height, this.width, this.height);
		
		// Image
		FontRenderer fontrenderer = minecraft.font;
		minecraft.getTextureManager().bind(this.slottedDemon.getTexture());
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		blit(matrixStack, this.x + 8, this.y + 8, 0, 0, this.width - 16, this.height - 16, this.width - 16, this.height - 16);
		blit(matrixStack, this.x + 8, this.y + 8, 0, 0, this.width - 16, this.height - 16, this.width - 16, this.height - 16);
		this.renderBg(matrixStack, minecraft, mouseX, mouseY);
		int j = getFGColor();
		
		// Text
		drawString(matrixStack, fontrenderer, this.getMessage(), this.x + this.width + 2, this.y + 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
		
		// Icons
		minecraft.getTextureManager().bind(this.slottedDemon.getBuffTexture());
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		blit(matrixStack, this.x + this.width + 2, this.y + 18, 0, 0, 16, 16, 16, 16);
		
		minecraft.getTextureManager().bind(MainPactMagic.BUFF);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		blit(matrixStack, this.x + this.width + 18, this.y + 18, 0, 0, 16, 16, 16, 16);
		
		if(this.slottedDemon.getDebuffTexture() != null) {
			minecraft.getTextureManager().bind(this.slottedDemon.getDebuffTexture());
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
			blit(matrixStack, this.x + this.width + 34, this.y + 18, 0, 0, 16, 16, 16, 16);
			
			minecraft.getTextureManager().bind(MainPactMagic.DEBUFF);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
			blit(matrixStack, this.x + this.width + 50, this.y + 18, 0, 0, 16, 16, 16, 16);			
		} else {
			minecraft.getTextureManager().bind(MainPactMagic.NONE);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
			blit(matrixStack, this.x + this.width + 34, this.y + 18, 0, 0, 32, 16, 32, 16);	
		}
		
		// Tooltip
		if(this.isHovered()) {
			this.renderToolTip(matrixStack, mouseX, mouseY);
		}
	}
}