
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createdeleteandrepeat.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.createdeleteandrepeat.CreateDeleteAndRepeatMod;

public class CreateDeleteAndRepeatModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateDeleteAndRepeatMod.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATE_DELETE_AND_REPEAT = REGISTRY.register("create_delete_and_repeat", () -> CreativeModeTab.builder()
			.title(Component.translatable("item_group.create_delete_and_repeat.create_delete_and_repeat")).icon(() -> new ItemStack(CreateDeleteAndRepeatModBlocks.FLUID_VOIDER.get())).displayItems((parameters, tabData) -> {
				tabData.accept(CreateDeleteAndRepeatModBlocks.FLUID_VOIDER.get().asItem());
			}).build());
}
