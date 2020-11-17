package io.github.fallOut015.pact_magic.item;

import java.util.List;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.common.demons.Demon;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

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
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if(group == ItemGroup.MISC) {
			for(Demon demon : Demon.demons()) {
				ItemStack stack = new ItemStack(ItemsPactMagic.DEMON_PAGE.get());
				putDemon(stack, demon);
				items.add(stack);
			}
		}
	}
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		@Nullable Demon demon = getDemon(stack);
		if(demon != null) {
			tooltip.add(new TranslationTextComponent("gui." + demon.getID() + ".title").mergeStyle(TextFormatting.RED, TextFormatting.ITALIC));
		}
	}
}