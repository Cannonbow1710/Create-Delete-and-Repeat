
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createdeleteandrepeat.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mcreator.createdeleteandrepeat.CreateDeleteAndRepeatMod;

public class CreateDeleteAndRepeatModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(CreateDeleteAndRepeatMod.MODID);
	public static final DeferredItem<Item> FLUID_VOIDER = block(CreateDeleteAndRepeatModBlocks.FLUID_VOIDER);

	// Start of user code block custom items
	// End of user code block custom items
	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
