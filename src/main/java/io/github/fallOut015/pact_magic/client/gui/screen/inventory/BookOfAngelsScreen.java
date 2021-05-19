package io.github.fallOut015.pact_magic.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import io.github.fallOut015.pact_magic.Main;
import io.github.fallOut015.pact_magic.common.angels.Angel;
import io.github.fallOut015.pact_magic.common.angels.Angels;
import io.github.fallOut015.pact_magic.item.BookOfAngelsItem;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ReadBookScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

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
	
	private @Nullable AngelPage pageLeft;
	private @Nullable AngelPage pageRight;
	private final AngelPage[] pages;

	public BookOfAngelsScreen(final PlayerEntity player, final ItemStack stack) {
		super(BOOK_OF_ANGELS);
		
		this.player = player;
		this.stack = stack;

		this.pages = new AngelPage[] {
			new AngelPage(Angels.SERAPHIM),
			new AngelPage(Angels.CHERUBIM),
			new AngelPage(Angels.THRONES),
			new AngelPage(Angels.DOMINIONS),
			new AngelPage(Angels.VIRTUES),
			new AngelPage(Angels.POWERS),
			new AngelPage(Angels.PRINCIPALITIES),
			new AngelPage(Angels.ARCHANGELS),
			new AngelPage(Angels.ANGELS)
		};

		this.pageLeft = this.pages[0];
		this.pageRight = this.pages[1];
	}
	
	@Override
	protected void init() {
		super.init();
		
//		if(BookOfAngelsItem.hasAngel(this.stack, Angels.SERAPHIM)) {
//			this.buttonSeraphim = this.addButton(new AngelButton(this.width / 18 + 2, 40, this.player, Angels.SERAPHIM, this));
//		}
//		if(BookOfAngelsItem.hasAngel(this.stack, Angels.CHERUBIM)) {
//			this.buttonCherubim = this.addButton(new AngelButton(this.width / 18 + 130, 40, this.player, Angels.CHERUBIM, this));
//		}
//		if(BookOfAngelsItem.hasAngel(this.stack, Angels.THRONES)) {
//			this.buttonThrones = this.addButton(new AngelButton(this.width / 18 + 258, 40, this.player, Angels.THRONES, this));
//		}
//
//		if(BookOfAngelsItem.hasAngel(this.stack, Angels.DOMINIONS)) {
//			this.buttonDominions = this.addButton(new AngelButton(this.width / 18 + 2, 106, this.player, Angels.DOMINIONS, this));
//		}
//		if(BookOfAngelsItem.hasAngel(this.stack, Angels.VIRTUES)) {
//			this.buttonVirtues = this.addButton(new AngelButton(this.width / 18 + 130, 106, this.player, Angels.VIRTUES, this));
//		}
//		if(BookOfAngelsItem.hasAngel(this.stack, Angels.POWERS)) {
//			this.buttonPowers = this.addButton(new AngelButton(this.width / 18 + 258, 106, this.player, Angels.POWERS, this));
//		}
//		
//		if(BookOfAngelsItem.hasAngel(this.stack, Angels.PRINCIPALITIES)) {
//			this.buttonPrincipalities = this.addButton(new AngelButton(this.width / 18 + 2, 172, this.player, Angels.PRINCIPALITIES, this));
//		}
//		if(BookOfAngelsItem.hasAngel(this.stack, Angels.ARCHANGELS)) {
//			this.buttonArchangels = this.addButton(new AngelButton(this.width / 18 + 130, 172, this.player, Angels.ARCHANGELS, this));
//		}
//		if(BookOfAngelsItem.hasAngel(this.stack, Angels.ANGELS)) {
//			this.buttonAngels = this.addButton(new AngelButton(this.width / 18 + 258, 172, this.player, Angels.ANGELS, this));
//		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		if(this.pageLeft != null) {
			this.pageLeft.render(this, matrixStack, (this.width / 2) - 169, 2, true);
		}
		if(this.pageRight != null) {
			this.pageRight.render(this, matrixStack, (this.width / 2) - 23, 2, false);
		}

		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	class AngelPage {
		final Angel slottedAngel;

		public AngelPage(final Angel slottedAngel) {
			this.slottedAngel = slottedAngel;
		}
		
		public void render(BookOfAngelsScreen context, MatrixStack matrixStack, int x, int y, boolean left) {
			if(BookOfAngelsItem.hasAngel(context.stack, this.slottedAngel)) {
				// Background
				matrixStack.pushPose();
				//if(left) {
					//matrixStack.rotate(new Quaternion(Vector3f.YN, 180, true));
				//}
				context.minecraft.getTextureManager().bind(ReadBookScreen.BOOK_LOCATION);
				blit(matrixStack, x, y, 0, 0, 192, 192); // 256, 256
				matrixStack.popPose();

				// Text
				context.minecraft.font.width(context.minecraft.font.split(this.slottedAngel.getTranslationKey(), 50).get(0));
				drawString(matrixStack, context.minecraft.font, this.slottedAngel.getTranslationKey(), x + 69, y + 12, 16777215);

				//drawString(matrixStack, context.minecraft.fontRenderer, this.slottedAngel.getDescKey(), x + 44, y + 112, 16777215);

				// Images
				context.minecraft.getTextureManager().bind(this.slottedAngel.getTexture());
				blit(matrixStack, x + 68, y + 28, 0, 0, 48, 48, 48, 48);

				context.minecraft.getTextureManager().bind(Main.BORDER);
				blit(matrixStack, x + 60, y + 20, 0, 0, 64, 64, 64, 64);

				context.minecraft.getTextureManager().bind(this.slottedAngel.getBuffTexture());
				blit(matrixStack, x + 64, y + 90, 0, 0, 16, 16, 16, 16);

				context.minecraft.getTextureManager().bind(Main.BUFF);
				blit(matrixStack, x + 78, y + 90, 0, 0, 16, 16, 16, 16);

				if(this.slottedAngel.getDebuffTexture() != null) {
					context.minecraft.getTextureManager().bind(this.slottedAngel.getDebuffTexture());
					blit(matrixStack, x + 92, y + 90, 0, 0, 16, 16, 16, 16);

					context.minecraft.getTextureManager().bind(Main.DEBUFF);
					blit(matrixStack, x + 106, y + 90, 0, 0, 16, 16, 16, 16);
				} else {
					context.minecraft.getTextureManager().bind(Main.NONE);
					blit(matrixStack, x + 78, y + 90, 0, 0, 32, 16, 32, 16);
				}
			}
		}
	}
}