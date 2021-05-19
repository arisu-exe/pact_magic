package io.github.fallOut015.pact_magic.item;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.common.capabilities.CapabilitiesPactMagic;
import io.github.fallOut015.pact_magic.common.capabilities.IPactMagic;
import io.github.fallOut015.pact_magic.common.demons.Demon;
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

public class DemonPageItem extends Item {
	public DemonPageItem(Properties properties) {
		super(properties);
	}
	
	public static void putDemon(ItemStack stack, Demon demon) {
		stack.getOrCreateTag().putString("demon", demon.getID());
	}
	public static @Nullable Demon getDemon(ItemStack stack) {
		return Demon.fromID(stack.getOrCreateTag().getString("demon"));
	}
	
	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if(group == ItemGroupPactMagic.PACT_MAGIC || group == ItemGroup.TAB_SEARCH) {
			for(Demon demon : Demon.demons()) {
				ItemStack stack = new ItemStack(ItemsPactMagic.DEMON_PAGE.get());
				putDemon(stack, demon);
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
		
		@Nullable Demon demon = getDemon(stack);
		if(demon != null) {
			tooltip.add(new TranslationTextComponent("gui." + demon.getID() + ".title").withStyle(TextFormatting.RED));
		}
	}
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);
		
		Optional<IPactMagic> pactMagic = playerIn.getCapability(CapabilitiesPactMagic.PACT_MAGIC).resolve();
		
		if(pactMagic.isPresent()) {
			@Nullable Demon demon = getDemon(stack);
			if(demon != null) {
				if(pactMagic.get().getSlottedDemon() == demon) {
					pactMagic.get().slotDemon(null);
				} else {
					pactMagic.get().slotDemon(demon);
				}
				return ActionResult.success(stack);
			} else {
				return ActionResult.fail(stack);
			}
		} else {
			return ActionResult.pass(stack);
		}
	}
}