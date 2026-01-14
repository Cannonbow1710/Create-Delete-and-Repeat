package net.mcreator.createdeleteandrepeat.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

public class StrainerOnTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (new Object() {
			public int getFluidTankLevel(LevelAccessor level, BlockPos pos, int tank) {
				if (level instanceof ILevelExtension _ext) {
					IFluidHandler _fluidHandler = _ext.getCapability(Capabilities.FluidHandler.BLOCK, pos, null);
					if (_fluidHandler != null)
						return _fluidHandler.getFluidInTank(tank).getAmount();
				}
				return 0;
			}
		}.getFluidTankLevel(world, BlockPos.containing(x, y, z), 0) > 100 && new Object() {
			public int getAmount(LevelAccessor world, BlockPos pos, int slotid) {
				if (world instanceof ILevelExtension _ext) {
					IItemHandler _itemHandler = _ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
					if (_itemHandler != null)
						return _itemHandler.getStackInSlot(slotid).getCount();
				}
				return 0;
			}
		}.getAmount(world, BlockPos.containing(x, y, z), 0) == 0) {
			assert Boolean.TRUE; //#dbg:StrainerOnTickUpdate:checkedbroad
			if (FluidStack.isSameFluid((new Object() {
				public FluidStack getFluidInTank(LevelAccessor level, BlockPos pos, int tank) {
					if (level instanceof ILevelExtension extension) {
						IFluidHandler fluidHandler = extension.getCapability(Capabilities.FluidHandler.BLOCK, pos, null);
						if (fluidHandler != null) {
							return fluidHandler.getFluidInTank(tank).copy();
						} else {
							return FluidStack.EMPTY;
						}
					}
					return FluidStack.EMPTY;
				}
			}.getFluidInTank(world, BlockPos.containing(x, y, z), (int) 0)), (new FluidStack(Fluids.LAVA, 1)))) {
				assert Boolean.TRUE; //#dbg:StrainerOnTickUpdate:checkedfluidlava
				if (world instanceof ILevelExtension _ext) {
					IFluidHandler _fluidHandler = _ext.getCapability(Capabilities.FluidHandler.BLOCK, BlockPos.containing(x, y, z), null);
					if (_fluidHandler != null)
						_fluidHandler.drain(100, IFluidHandler.FluidAction.EXECUTE);
				}
				assert Boolean.TRUE; //#dbg:StrainerOnTickUpdate:drainedlava
				if (Math.random() > 0.5) {
					if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
						ItemStack _setstack = new ItemStack((BuiltInRegistries.ITEM.getOrCreateTag(ItemTags.create(ResourceLocation.parse("create_delete_and_repeat:strainer_loot_lava"))).getRandomElement(RandomSource.create())
								.orElseGet(() -> BuiltInRegistries.ITEM.wrapAsHolder(Items.AIR)).value())).copy();
						_setstack.setCount(1);
						_itemHandlerModifiable.setStackInSlot(0, _setstack);
					}
				}
			} else if (FluidStack.isSameFluid((new Object() {
				public FluidStack getFluidInTank(LevelAccessor level, BlockPos pos, int tank) {
					if (level instanceof ILevelExtension extension) {
						IFluidHandler fluidHandler = extension.getCapability(Capabilities.FluidHandler.BLOCK, pos, null);
						if (fluidHandler != null) {
							return fluidHandler.getFluidInTank(tank).copy();
						} else {
							return FluidStack.EMPTY;
						}
					}
					return FluidStack.EMPTY;
				}
			}.getFluidInTank(world, BlockPos.containing(x, y, z), (int) 0)), (new FluidStack(Fluids.WATER, 1)))) {
				assert Boolean.TRUE; //#dbg:StrainerOnTickUpdate:checkedfluidwater
				if (world instanceof ILevelExtension _ext) {
					IFluidHandler _fluidHandler = _ext.getCapability(Capabilities.FluidHandler.BLOCK, BlockPos.containing(x, y, z), null);
					if (_fluidHandler != null)
						_fluidHandler.drain(100, IFluidHandler.FluidAction.EXECUTE);
				}
				assert Boolean.TRUE; //#dbg:StrainerOnTickUpdate:drainedwater
				if (Math.random() > 0.4) {
					if (world instanceof ILevelExtension _ext && _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
						ItemStack _setstack = new ItemStack((BuiltInRegistries.ITEM.getOrCreateTag(ItemTags.create(ResourceLocation.parse("create_delete_and_repeat:strainer_loot_water"))).getRandomElement(RandomSource.create())
								.orElseGet(() -> BuiltInRegistries.ITEM.wrapAsHolder(Items.AIR)).value())).copy();
						_setstack.setCount(Mth.nextInt(RandomSource.create(), 1, 2));
						_itemHandlerModifiable.setStackInSlot(0, _setstack);
					}
				}
			}
		}
	}
}
