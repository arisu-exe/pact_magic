package io.github.fallOut015.pact_magic.item;

import io.github.fallOut015.pact_magic.client.gui.screen.inventory.BookOfDemonsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BookOfDemonsItem extends Item {
	public BookOfDemonsItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemStack = playerIn.getHeldItem(handIn);
		 
		if(worldIn.isRemote) {
			return ActionResult.resultSuccess(itemStack);
		} else {
			Minecraft.getInstance().displayGuiScreen(new BookOfDemonsScreen(playerIn));
			playerIn.addStat(Stats.ITEM_USED.get(this));
			return ActionResult.resultConsume(itemStack);
		}
	}
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}