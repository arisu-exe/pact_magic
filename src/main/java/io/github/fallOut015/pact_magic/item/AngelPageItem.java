package io.github.fallOut015.pact_magic.item;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.common.angels.Angel;
import io.github.fallOut015.pact_magic.common.capabilities.CapabilitiesPactMagic;
import io.github.fallOut015.pact_magic.common.capabilities.IPactMagic;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class AngelPageItem extends Item {
	public AngelPageItem(Properties properties) {
		super(properties);
	}
	
	public static void putAngel(ItemStack stack, Angel angel) {
		stack.getOrCreateTag().putString("angel", angel.getID());
	}
	public static @Nullable Angel getAngel(ItemStack stack) {
		return Angel.fromID(stack.getOrCreateTag().getString("angel"));
	}
	
	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if(group == ItemGroupPactMagic.PACT_MAGIC || group == ItemGroup.TAB_SEARCH) {
			for(Angel angel : Angel.angels()) {
				ItemStack stack = new ItemStack(ItemsPactMagic.ANGEL_PAGE.get());
				putAngel(stack, angel);
				items.add(stack);
			}
		}
	}
	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}
	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		
		@Nullable Angel angel = getAngel(stack);
		if(angel != null) {
			tooltip.add(new TranslationTextComponent("gui." + angel.getID() + ".title").withStyle(TextFormatting.YELLOW));
		}
	}
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);
		
		Optional<IPactMagic> pactMagic = playerIn.getCapability(CapabilitiesPactMagic.PACT_MAGIC).resolve();
		
		if(pactMagic.isPresent()) {
			@Nullable Angel angel = getAngel(stack);
			if(angel != null) {
				/*if(pactMagic.get().getSlottedAngel() == angel) {
					pactMagic.get().slotAngel(null);
				} else {
					pactMagic.get().slotAngel(angel);
				}*/
				return ActionResult.success(stack);
			} else {
				return ActionResult.fail(stack);
			}
		} else {
			return ActionResult.pass(stack);
		}
	}
}