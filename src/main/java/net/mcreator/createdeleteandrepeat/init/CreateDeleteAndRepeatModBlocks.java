
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createdeleteandrepeat.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.Block;

import net.mcreator.createdeleteandrepeat.block.StrainerBlock;
import net.mcreator.createdeleteandrepeat.block.IndustrialCauldronBlock;
import net.mcreator.createdeleteandrepeat.block.FluidVoiderBlock;
import net.mcreator.createdeleteandrepeat.CreateDeleteAndRepeatMod;

public class CreateDeleteAndRepeatModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(CreateDeleteAndRepeatMod.MODID);
	public static final DeferredBlock<Block> FLUID_VOIDER = REGISTRY.register("fluid_voider", FluidVoiderBlock::new);
	public static final DeferredBlock<Block> INDUSTRIAL_CAULDRON = REGISTRY.register("industrial_cauldron", IndustrialCauldronBlock::new);
	public static final DeferredBlock<Block> STRAINER = REGISTRY.register("strainer", StrainerBlock::new);
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
