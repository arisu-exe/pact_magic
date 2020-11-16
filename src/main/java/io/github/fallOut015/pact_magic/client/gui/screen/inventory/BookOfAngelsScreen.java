package io.github.fallOut015.pact_magic.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import io.github.fallOut015.pact_magic.client.gui.widget.button.AngelButton;
import io.github.fallOut015.pact_magic.common.angels.Angels;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BookOfAngelsScreen extends Screen {
	private static final ITextComponent BOOK_OF_ANGELS = new TranslationTextComponent("gui.book_of_angels");
	private static final ResourceLocation BOOK_OF_ANGELS_GUI_TEXTURE = new ResourceLocation("pact_magic", "textures/gui/book_of_angels.png");
	
	protected Button buttonSeraphim;
	protected Button buttonCherubim;
	protected Button buttonThrones;

	protected Button buttonDominions;
	protected Button buttonVirtues;
	protected Button buttonPowers;

	protected Button buttonPrincipalities;
	protected Button buttonArchangels;
	protected Button buttonAngels;
	
	private final PlayerEntity player;

	public BookOfAngelsScreen(final PlayerEntity player) {
		super(BOOK_OF_ANGELS);
		this.player = player;
	}
	
	@Override
	protected void init() {
		super.init();
		
		this.buttonSeraphim = this.addButton(new AngelButton(this.width / 18 + 2, 40, this.player, Angels.SERAPHIM, this));
		this.buttonCherubim = this.addButton(new AngelButton(this.width / 18 + 130, 40, this.player, Angels.CHERUBIM, this));
		this.buttonThrones = this.addButton(new AngelButton(this.width / 18 + 258, 40, this.player, Angels.THRONES, this));

		this.buttonDominions = this.addButton(new AngelButton(this.width / 18 + 2, 106, this.player, Angels.DOMINIONS, this));
		this.buttonVirtues = this.addButton(new AngelButton(this.width / 18 + 130, 106, this.player, Angels.VIRTUES, this));
		this.buttonPowers = this.addButton(new AngelButton(this.width / 18 + 258, 106, this.player, Angels.POWERS, this));

		this.buttonPrincipalities = this.addButton(new AngelButton(this.width / 18 + 2, 172, this.player, Angels.PRINCIPALITIES, this));
		this.buttonArchangels = this.addButton(new AngelButton(this.width / 18 + 130, 172, this.player, Angels.ARCHANGELS, this));
		this.buttonAngels = this.addButton(new AngelButton(this.width / 18 + 258, 172, this.player, Angels.ANGELS, this));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(BOOK_OF_ANGELS_GUI_TEXTURE);
		AbstractGui.blit(matrixStack, 2, 2, 0, 0, 576, 592, 592, 592);
		
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	@Override
	public boolean isPauseScreen() {
		return false;
	}
}