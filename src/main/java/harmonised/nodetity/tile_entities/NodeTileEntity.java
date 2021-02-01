package harmonised.nodetity.tile_entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class NodeTileEntity extends TileEntity implements ISidedInventory, ITickableTileEntity
{
    private List<ItemStack> items;

    public NodeTileEntity( TileEntityType<?> tileEntityTypeIn )
    {
        super( tileEntityTypeIn );
        this.items = new ArrayList<>();
    }

    @Override
    public void tick()
    {
        System.out.println( "Ticking a NodeTileEntity!!!" );
    }
    
    @Override
    public void read( BlockState state, CompoundNBT nbt )
    {

    }

    @Override
    public CompoundNBT write( CompoundNBT compound )
    {
        return compound;
    }

    @Override
    public int[] getSlotsForFace( Direction side )
    {
        return new int[0];
    }

    @Override
    public boolean canInsertItem( int index, ItemStack itemStackIn, @Nullable Direction direction )
    {
        return false;
    }

    @Override
    public boolean canExtractItem( int index, ItemStack stack, Direction direction )
    {
        return false;
    }

    @Override
    public int getSizeInventory()
    {
        return 0;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public ItemStack getStackInSlot( int index )
    {
        return null;
    }

    @Override
    public ItemStack decrStackSize( int index, int count )
    {
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot( int index )
    {
        return null;
    }

    @Override
    public void setInventorySlotContents( int index, ItemStack stack )
    {

    }

    @Override
    public boolean isUsableByPlayer( PlayerEntity player )
    {
        return false;
    }

    @Override
    public void clear()
    {

    }
}
