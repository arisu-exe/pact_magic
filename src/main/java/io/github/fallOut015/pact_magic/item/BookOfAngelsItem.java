package io.github.fallOut015.pact_magic.item;

import java.util.List;

import io.github.fallOut015.pact_magic.client.gui.screen.inventory.BookOfAngelsScreen;
import io.github.fallOut015.pact_magic.common.angels.Angel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class BookOfAngelsItem extends Item {
	public BookOfAngelsItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);
		 
		if(worldIn.isClientSide) {
			return ActionResult.success(stack);
		} else {
			Minecraft.getInstance().setScreen(new BookOfAngelsScreen(playerIn, stack));
			playerIn.awardStat(Stats.ITEM_USED.get(this));
			return ActionResult.consume(stack);
		}
	}
	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}
	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		
		for(Angel angel : Angel.angels()) {
			if(hasAngel(stack, angel)) {
				tooltip.add(new TranslationTextComponent("gui." + angel.getID() + ".title").withStyle(TextFormatting.YELLOW, TextFormatting.ITALIC));
			}
		}
	}
	
	public static boolean hasAngel(ItemStack stack, Angel angel) {
		return stack.getOrCreateTag().contains(angel.getID()) && stack.getOrCreateTag().getBoolean(angel.getID()) == true;
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if(group == ItemGroupPactMagic.PACT_MAGIC || group == ItemGroup.TAB_SEARCH) {
			ItemStack stack = new ItemStack(ItemsPactMagic.BOOK_OF_ANGELS.get());
			for(Angel angel : Angel.angels()) {
				stack.getOrCreateTag().putBoolean(angel.getID(), true);
			}
			items.add(stack);
		}
	}
}