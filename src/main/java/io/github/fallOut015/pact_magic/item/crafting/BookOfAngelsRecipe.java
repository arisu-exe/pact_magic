package io.github.fallOut015.pact_magic.item.crafting;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.common.angels.Angel;
import io.github.fallOut015.pact_magic.item.AngelPageItem;
import io.github.fallOut015.pact_magic.item.ItemsPactMagic;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BookOfAngelsRecipe extends SpecialRecipe {
	public BookOfAngelsRecipe(ResourceLocation idIn) {
		super(idIn);
	}

	@Override
	public boolean matches(CraftingInventory inv, World worldIn) {
		List<ItemStack> pages = new LinkedList<ItemStack>();
		List<ItemStack> angelBooks = new LinkedList<ItemStack>();
		ItemStack book = ItemStack.EMPTY;
		
		for(int i = 0; i < inv.getContainerSize(); ++ i) {
			ItemStack stack = inv.getItem(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() == ItemsPactMagic.ANGEL_PAGE.get()) {
					pages.add(stack);
				} else if(stack.getItem() == ItemsPactMagic.BOOK_OF_ANGELS.get()) {
					angelBooks.add(stack);
				} else if(stack.getItem() == Items.BOOK) {
					if(book.isEmpty()) {
						book = stack;
					} else {
						return false;
					}
				}
			}
		}
		
		if(angelBooks.size() > 1) {
			return pages.isEmpty() && book.isEmpty();
		} else {
			if(book.isEmpty()) {
				return !pages.isEmpty() && !angelBooks.isEmpty();
			} else {
				return !pages.isEmpty() && angelBooks.isEmpty();
			}
		}
	}
	@Override
	public ItemStack assemble(CraftingInventory inv) {
		// Three types
		/*
		 * PageN + Book (put pages into a book)
		 * PageN + AngelBookN (put pages into an angel book)
		 * AngelBook +N AngelBook (put pages from one angel book into aNother)
		 */
		
		List<ItemStack> pages = new LinkedList<ItemStack>();
		List<ItemStack> angelBooks = new LinkedList<ItemStack>();
		ItemStack book = ItemStack.EMPTY;
		
		for(int i = 0; i < inv.getContainerSize(); ++ i) {
			ItemStack stack = inv.getItem(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() == ItemsPactMagic.ANGEL_PAGE.get()) {
					pages.add(stack);
				} else if(stack.getItem() == ItemsPactMagic.BOOK_OF_ANGELS.get()) {
					angelBooks.add(stack);
				} else if(stack.getItem() == Items.BOOK) {
					if(book.isEmpty()) {
						book = stack;
					} else {
						return ItemStack.EMPTY;
					}
				}
			}
		}
		
		CompoundNBT nbt = new CompoundNBT();
		
		if(angelBooks.size() > 1) {
			if(pages.isEmpty() && book.isEmpty()) {
				for(ItemStack angelBook : angelBooks) {
					for(String key : angelBook.getOrCreateTag().getAllKeys()) {
						nbt.putBoolean(key, angelBook.getTag().getBoolean(key));
					}
				}
			}
		} else {
			if(book.isEmpty()) {
				if(!pages.isEmpty() && !angelBooks.isEmpty()) {
					for(ItemStack page : pages) {
						@Nullable Angel angel = AngelPageItem.getAngel(page);
						if(angel != null) {
							nbt.putBoolean(angel.getID(), true);
						}
					}
					for(ItemStack angelBook : angelBooks) {
						for(String key : angelBook.getOrCreateTag().getAllKeys()) {
							nbt.putBoolean(key, angelBook.getTag().getBoolean(key));
						}
					}
				}
			} else {
				if(!pages.isEmpty() && angelBooks.isEmpty()) {
					for(ItemStack page : pages) {
						@Nullable Angel angel = AngelPageItem.getAngel(page);
						if(angel != null) {
							nbt.putBoolean(angel.getID(), true);
						}
					}
				}
			}
		}
		
		ItemStack stack = new ItemStack(ItemsPactMagic.BOOK_OF_ANGELS.get());
		stack.setTag(nbt);
		return stack;
	}
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}
	@Override
	public IRecipeSerializer<?> getSerializer() {
		return RecipeSerializersPactMagic.BOOK_OF_ANGELS.get();
	}
}