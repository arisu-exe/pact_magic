package io.github.fallOut015.pact_magic.item;

import java.util.List;

import io.github.fallOut015.pact_magic.client.gui.screen.inventory.BookOfAngelsScreen;
import io.github.fallOut015.pact_magic.common.angels.Angel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class BookOfAngelsItem extends Item {
	public BookOfAngelsItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		 
		if(worldIn.isRemote) {
			return ActionResult.resultSuccess(stack);
		} else {
			Minecraft.getInstance().displayGuiScreen(new BookOfAngelsScreen(playerIn, stack));
			playerIn.addStat(Stats.ITEM_USED.get(this));
			return ActionResult.resultConsume(stack);
		}
	}
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		for(Angel angel : Angel.angels()) {
			if(hasAngel(stack, angel)) {
				tooltip.add(new TranslationTextComponent("gui." + angel.getID() + ".title").mergeStyle(TextFormatting.YELLOW, TextFormatting.ITALIC));
			}
		}
	}
	
	public static boolean hasAngel(ItemStack stack, Angel angel) {
		return stack.getOrCreateTag().contains(angel.getID()) && stack.getOrCreateTag().getBoolean(angel.getID()) == true;
	}
}