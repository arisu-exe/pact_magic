package io.github.fallOut015.pact_magic.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import io.github.fallOut015.pact_magic.client.gui.widget.button.AngelButton;
import io.github.fallOut015.pact_magic.common.angels.Angels;
import io.github.fallOut015.pact_magic.item.BookOfAngelsItem;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
	private final ItemStack stack;

	public BookOfAngelsScreen(final PlayerEntity player, final ItemStack stack) {
		super(BOOK_OF_ANGELS);
		
		this.player = player;
		this.stack = stack;
	}
	
	@Override
	protected void init() {
		super.init();
		
		if(BookOfAngelsItem.hasAngel(this.stack, Angels.SERAPHIM)) {
			this.buttonSeraphim = this.addButton(new AngelButton(this.width / 18 + 2, 40, this.player, Angels.SERAPHIM, this));
		}
		if(BookOfAngelsItem.hasAngel(this.stack, Angels.CHERUBIM)) {
			this.buttonCherubim = this.addButton(new AngelButton(this.width / 18 + 130, 40, this.player, Angels.CHERUBIM, this));
		}
		if(BookOfAngelsItem.hasAngel(this.stack, Angels.THRONES)) {
			this.buttonThrones = this.addButton(new AngelButton(this.width / 18 + 258, 40, this.player, Angels.THRONES, this));
		}

		if(BookOfAngelsItem.hasAngel(this.stack, Angels.DOMINIONS)) {
			this.buttonDominions = this.addButton(new AngelButton(this.width / 18 + 2, 106, this.player, Angels.DOMINIONS, this));
		}
		if(BookOfAngelsItem.hasAngel(this.stack, Angels.VIRTUES)) {
			this.buttonVirtues = this.addButton(new AngelButton(this.width / 18 + 130, 106, this.player, Angels.VIRTUES, this));
		}
		if(BookOfAngelsItem.hasAngel(this.stack, Angels.POWERS)) {
			this.buttonPowers = this.addButton(new AngelButton(this.width / 18 + 258, 106, this.player, Angels.POWERS, this));
		}
		
		if(BookOfAngelsItem.hasAngel(this.stack, Angels.PRINCIPALITIES)) {
			this.buttonPrincipalities = this.addButton(new AngelButton(this.width / 18 + 2, 172, this.player, Angels.PRINCIPALITIES, this));
		}
		if(BookOfAngelsItem.hasAngel(this.stack, Angels.ARCHANGELS)) {
			this.buttonArchangels = this.addButton(new AngelButton(this.width / 18 + 130, 172, this.player, Angels.ARCHANGELS, this));
		}
		if(BookOfAngelsItem.hasAngel(this.stack, Angels.ANGELS)) {
			this.buttonAngels = this.addButton(new AngelButton(this.width / 18 + 258, 172, this.player, Angels.ANGELS, this));
		}
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