
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.createdeleteandrepeat.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.createdeleteandrepeat.block.entity.StrainerBlockEntity;
import net.mcreator.createdeleteandrepeat.block.entity.IndustrialCauldronBlockEntity;
import net.mcreator.createdeleteandrepeat.CreateDeleteAndRepeatMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CreateDeleteAndRepeatModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CreateDeleteAndRepeatMod.MODID);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> INDUSTRIAL_CAULDRON = register("industrial_cauldron", CreateDeleteAndRepeatModBlocks.INDUSTRIAL_CAULDRON, IndustrialCauldronBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> STRAINER = register("strainer", CreateDeleteAndRepeatModBlocks.STRAINER, StrainerBlockEntity::new);

	// Start of user code block custom block entities
	// End of user code block custom block entities
	private static DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, INDUSTRIAL_CAULDRON.get(), (blockEntity, side) -> ((IndustrialCauldronBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, STRAINER.get(), (blockEntity, side) -> ((StrainerBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, STRAINER.get(), (blockEntity, side) -> ((StrainerBlockEntity) blockEntity).getFluidTank());
	}
}
