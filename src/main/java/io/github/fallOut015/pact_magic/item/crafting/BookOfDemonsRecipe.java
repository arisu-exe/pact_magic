package io.github.fallOut015.pact_magic.item.crafting;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.common.demons.Demon;
import io.github.fallOut015.pact_magic.item.DemonPageItem;
import io.github.fallOut015.pact_magic.item.ItemsPactMagic;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BookOfDemonsRecipe extends SpecialRecipe {
	public BookOfDemonsRecipe(ResourceLocation idIn) {
		super(idIn);
	}

	@Override
	public boolean matches(CraftingInventory inv, World worldIn) {
		List<ItemStack> pages = new LinkedList<ItemStack>();
		List<ItemStack> demonBooks = new LinkedList<ItemStack>();
		ItemStack book = ItemStack.EMPTY;
		
		for(int i = 0; i < inv.getContainerSize(); ++ i) {
			ItemStack stack = inv.getItem(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() == ItemsPactMagic.DEMON_PAGE.get()) {
					pages.add(stack);
				} else if(stack.getItem() == ItemsPactMagic.BOOK_OF_DEMONS.get()) {
					demonBooks.add(stack);
				} else if(stack.getItem() == Items.BOOK) {
					if(book.isEmpty()) {
						book = stack;
					} else {
						return false;
					}
				}
			}
		}
		
		if(demonBooks.size() > 1) {
			return pages.isEmpty() && book.isEmpty();
		} else {
			if(book.isEmpty()) {
				return !pages.isEmpty() && !demonBooks.isEmpty();
			} else {
				return !pages.isEmpty() && demonBooks.isEmpty();
			}
		}
	}
	@Override
	public ItemStack assemble(CraftingInventory inv) {
		// Three types
		/*
		 * PageN + Book (put pages into a book)
		 * PageN + AngelBookN (put pages into an demon book)
		 * AngelBook +N AngelBook (put pages from one demon book into aNother)
		 */
		
		List<ItemStack> pages = new LinkedList<ItemStack>();
		List<ItemStack> demonBooks = new LinkedList<ItemStack>();
		ItemStack book = ItemStack.EMPTY;
		
		for(int i = 0; i < inv.getContainerSize(); ++ i) {
			ItemStack stack = inv.getItem(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() == ItemsPactMagic.DEMON_PAGE.get()) {
					pages.add(stack);
				} else if(stack.getItem() == ItemsPactMagic.BOOK_OF_DEMONS.get()) {
					demonBooks.add(stack);
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
		
		if(demonBooks.size() > 1) {
			if(pages.isEmpty() && book.isEmpty()) {
				for(ItemStack demonBook : demonBooks) {
					for(String key : demonBook.getOrCreateTag().getAllKeys()) {
						nbt.putBoolean(key, demonBook.getTag().getBoolean(key));
					}
				}
			}
		} else {
			if(book.isEmpty()) {
				if(!pages.isEmpty() && !demonBooks.isEmpty()) {
					for(ItemStack page : pages) {
						@Nullable Demon demon = DemonPageItem.getDemon(page);
						if(demon != null) {
							nbt.putBoolean(demon.getID(), true);
						}
					}
					for(ItemStack demonBook : demonBooks) {
						for(String key : demonBook.getOrCreateTag().getAllKeys()) {
							nbt.putBoolean(key, demonBook.getTag().getBoolean(key));
						}
					}
				}
			} else {
				if(!pages.isEmpty() && demonBooks.isEmpty()) {
					for(ItemStack page : pages) {
						@Nullable Demon demon = DemonPageItem.getDemon(page);
						if(demon != null) {
							nbt.putBoolean(demon.getID(), true);
						}
					}
				}
			}
		}
		
		ItemStack stack = new ItemStack(ItemsPactMagic.BOOK_OF_DEMONS.get());
		stack.setTag(nbt);
		return stack;
	}
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}
	@Override
	public IRecipeSerializer<?> getSerializer() {
		return RecipeSerializersPactMagic.BOOK_OF_DEMONS.get();
	}
}