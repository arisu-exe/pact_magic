package io.github.fallOut015.pact_magic.item;

import java.util.List;

import io.github.fallOut015.pact_magic.client.gui.screen.inventory.BookOfDemonsScreen;
import io.github.fallOut015.pact_magic.common.demons.Demon;
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

public class BookOfDemonsItem extends Item {
	public BookOfDemonsItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		 
		if(worldIn.isRemote) {
			return ActionResult.resultSuccess(stack);
		} else {
			Minecraft.getInstance().displayGuiScreen(new BookOfDemonsScreen(playerIn, stack));
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
		
		for(Demon demon : Demon.demons()) {
			if(hasDemon(stack, demon)) {
				tooltip.add(new TranslationTextComponent("gui." + demon.getID() + ".title").mergeStyle(TextFormatting.RED, TextFormatting.ITALIC));
			}
		}
	}
	
	public static boolean hasDemon(ItemStack stack, Demon demon) {
		return stack.getOrCreateTag().contains(demon.getID()) && stack.getOrCreateTag().getBoolean(demon.getID()) == true;
	}
}