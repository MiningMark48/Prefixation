package com.miningmark48.prefixation.container;

import com.miningmark48.prefixation.tile.TileEntityReforge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerReforge extends Container {

    private TileEntityReforge te;
    private static int INV_START = TileEntityReforge.INV_SIZE, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

    public ContainerReforge(final IInventory playerInv, TileEntityReforge te){
        this.te = te;

        //Tile Entity
        this.addSlotToContainer(new Slot(te, 0, 80, 22));

        //Player Inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 78 + i * 18));
            }
        }

        //Hotbar
        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 136));
        }

    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index){
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()){
            ItemStack itemstack1 = slot.getStack();
            stack = itemstack1.copy();

            if (index < INV_START){
                if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true)){
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, stack);
            }else{
                if(!this.mergeItemStack(itemstack1, 0, INV_START, false)){
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.getCount() == 0){
                slot.putStack(ItemStack.EMPTY);
            }else{
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == stack.getCount()){
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);

        }

        return stack;

    }

}
