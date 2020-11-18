package io.github.fallOut015.pact_magic.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import io.github.fallOut015.pact_magic.client.gui.widget.button.DemonButton;
import io.github.fallOut015.pact_magic.common.demons.Demons;
import io.github.fallOut015.pact_magic.item.BookOfDemonsItem;
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
public class BookOfDemonsScreen extends Screen {
	private static final ITextComponent BOOK_OF_DEMONS = new TranslationTextComponent("gui.book_of_demons");
	private static final ResourceLocation BOOK_OF_DEMONS_GUI_TEXTURE = new ResourceLocation("pact_magic", "textures/gui/book_of_demons.png");
	
	protected Button buttonSatan;
	protected Button buttonBeelzebub;
	protected Button buttonAstaroth;

	protected Button buttonAbaddon;
	protected Button buttonMammon;
	protected Button buttonAsmodeus;

	protected Button buttonPytho;
	protected Button buttonBelial;
	protected Button buttonMerihem;
	
	private final PlayerEntity player;
	private final ItemStack stack;

	public BookOfDemonsScreen(final PlayerEntity player, final ItemStack stack) {
		super(BOOK_OF_DEMONS);
		
		this.player = player;
		this.stack = stack;
	}
	
	@Override
	protected void init() {
		super.init();
		
		if(BookOfDemonsItem.hasDemon(this.stack, Demons.SATAN)) {
			this.buttonSatan = this.addButton(new DemonButton(this.width / 18 + 2, 40, this.player, Demons.SATAN, this));
		}
		if(BookOfDemonsItem.hasDemon(this.stack, Demons.BEELZEBUB)) {
			this.buttonBeelzebub = this.addButton(new DemonButton(this.width / 18 + 258, 40, this.player, Demons.BEELZEBUB, this));
		}
		if(BookOfDemonsItem.hasDemon(this.stack, Demons.ASTAROTH)) {
			this.buttonAstaroth = this.addButton(new DemonButton(this.width / 18 + 130, 40, this.player, Demons.ASTAROTH, this));
		}
		
		if(BookOfDemonsItem.hasDemon(this.stack, Demons.ABADDON)) {
			this.buttonAbaddon = this.addButton(new DemonButton(this.width / 18 + 2, 106, this.player, Demons.ABADDON, this));
		}
		if(BookOfDemonsItem.hasDemon(this.stack, Demons.MAMMON)) {
			this.buttonMammon = this.addButton(new DemonButton(this.width / 18 + 130, 106, this.player, Demons.MAMMON, this));
		}
		if(BookOfDemonsItem.hasDemon(this.stack, Demons.ASMODEUS)) {
			this.buttonAsmodeus = this.addButton(new DemonButton(this.width / 18 + 258, 106, this.player, Demons.ASMODEUS, this));
		}
		
		if(BookOfDemonsItem.hasDemon(this.stack, Demons.PYTHO)) {
			this.buttonPytho = this.addButton(new DemonButton(this.width / 18 + 2, 172, this.player, Demons.PYTHO, this));
		}
		if(BookOfDemonsItem.hasDemon(this.stack, Demons.BELIAL)) {
			this.buttonBelial = this.addButton(new DemonButton(this.width / 18 + 130, 172, this.player, Demons.BELIAL, this));
		}
		if(BookOfDemonsItem.hasDemon(this.stack, Demons.MERIHEM)) {
			this.buttonMerihem = this.addButton(new DemonButton(this.width / 18 + 258, 172, this.player, Demons.MERIHEM, this));
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(BOOK_OF_DEMONS_GUI_TEXTURE);
		AbstractGui.blit(matrixStack, 2, 2, 0, 0, 576, 592, 592, 592);
		
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	@Override
	public boolean isPauseScreen() {
		return false;
	}
}