package net.mcreator.createdeleteandrepeat.block.entity;

import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.FluidStack;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.createdeleteandrepeat.init.CreateDeleteAndRepeatModBlockEntities;

import javax.annotation.Nullable;

import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.Arrays;

public class StrainerBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
	private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
	private final SidedInvWrapper handler = new SidedInvWrapper(this, null);

	public StrainerBlockEntity(BlockPos position, BlockState state) {
		super(CreateDeleteAndRepeatModBlockEntities.STRAINER.get(), position, state);
	}

	@Override
	public void loadAdditional(CompoundTag compound, HolderLookup.Provider lookupProvider) {
		super.loadAdditional(compound, lookupProvider);
		if (!this.tryLoadLootTable(compound))
			this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compound, this.stacks, lookupProvider);
		fluidTank0.readFromNBT(lookupProvider, compound.getCompound("fluidTank0"));
	}

	@Override
	public void saveAdditional(CompoundTag compound, HolderLookup.Provider lookupProvider) {
		super.saveAdditional(compound, lookupProvider);
		if (!this.trySaveLootTable(compound)) {
			ContainerHelper.saveAllItems(compound, this.stacks, lookupProvider);
		}
		compound.put("fluidTank0", fluidTank0.writeToNBT(lookupProvider, new CompoundTag()));
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
		return this.saveWithFullMetadata(lookupProvider);
	}

	@Override
	public int getContainerSize() {
		return stacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.stacks)
			if (!itemstack.isEmpty())
				return false;
		return true;
	}

	@Override
	public Component getDefaultName() {
		return Component.literal("strainer");
	}

	@Override
	public int getMaxStackSize() {
		return 8;
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return ChestMenu.threeRows(id, inventory);
	}

	@Override
	public Component getDisplayName() {
		return Component.literal("Strainer");
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.stacks;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> stacks) {
		this.stacks = stacks;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		if (index == 0)
			return false;
		return true;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return IntStream.range(0, this.getContainerSize()).toArray();
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
		return this.canPlaceItem(index, stack);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		return true;
	}

	public SidedInvWrapper getItemHandler() {
		return handler;
	}

	// FTaO: modifies the IFluidHandler to work with all tanks instead of only the first one
	public final IFluidHandler fluidHandler = new IFluidHandler() {
		@Override
		public int getTanks() {
			return fluidTanks.length;
		}

		@Override
		public FluidStack getFluidInTank(int tank) {
			return fluidTanks[tank].getFluid();
		}

		@Override
		public int getTankCapacity(int tank) {
			return fluidTanks[tank].getCapacity();
		}

		@Override
		public boolean isFluidValid(int tank, FluidStack stack) {
			return fluidTanks[tank].isFluidValid(stack);
		}

		@Override
		public int fill(FluidStack stack, FluidAction action) {
			FluidTank[] tanks = Stream.concat(Arrays.stream(inputTanks), Arrays.stream(ioTanks)).toArray(FluidTank[]::new);
			for (FluidTank tank : tanks) {
				int tankSpace = tank.getCapacity() - tank.getFluidAmount();
				if (stack.isEmpty()) {
					return 0;
				}
				if (!tank.getFluid().isEmpty() && tank.getFluid().getFluid().isSame(stack.getFluid())) {
					int fillAmount = Math.min(stack.getAmount(), tankSpace);
					if (fillAmount > 0) {
						return tank.fill(stack.copyWithAmount(fillAmount), action);
					} else {
						return 0;
					}
				}
				if (tank.isEmpty() && tank.isFluidValid(stack)) {
					return tank.fill(stack.copyWithAmount(stack.getAmount()), action);
				}
			}
			return 0;
		}

		@Override
		public FluidStack drain(FluidStack stack, FluidAction action) {
			FluidTank[] tanks = Stream.concat(Arrays.stream(outputTanks), Arrays.stream(ioTanks)).toArray(FluidTank[]::new);
			for (FluidTank tank : tanks) {
				if (stack.getFluid() == tank.getFluid().getFluid()) {
					return tank.drain(stack.getAmount(), action);
				}
			}
			return FluidStack.EMPTY;
		}

		@Override
		public FluidStack drain(int maxDrain, FluidAction action) {
			FluidTank[] tanks = Stream.concat(Arrays.stream(outputTanks), Arrays.stream(ioTanks)).toArray(FluidTank[]::new);
			for (FluidTank tank : tanks) {
				if (tank.getFluidAmount() > 0) {
					return tank.drain(maxDrain, action);
				}
			}
			return FluidStack.EMPTY;
		}

		// FTaO
		public FluidTank getTank(int index) {
			return fluidTanks[index];
		}
	};

	// FTaO: Used in BlockEntity Init and returns the entire IFluidHandler instead of the IFluidHandler of the first set fluid tank
	public IFluidHandler getFluidTank() {
		return fluidHandler;
	}

	private final FluidTank fluidTank0 = new FluidTank(1000) {
		@Override
		protected void onContentsChanged() {
			super.onContentsChanged();
			setChanged();
			level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), 2);
		}

		@Override
		public void setFluid(FluidStack stack) {
			super.setFluid(stack);
			setChanged();
			level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), 2);
		}
	};

	public FluidTank getFluidTank0() {
		return fluidTank0;
	}

	// FtaO: Holds all fluid tanks + extra with individual type setting
	private final FluidTank[] fluidTanks = {fluidTank0};
	private final FluidTank[] ioTanks = {};
	private final FluidTank[] inputTanks = {};
	private final FluidTank[] outputTanks = {};
}
