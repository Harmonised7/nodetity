package harmonised.nodetity.tile_entities;

import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import harmonised.nodetity.registries.ModTEs;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class NodeTileEntity extends TileEntity implements ISidedInventory, ITickableTileEntity
{
//    final private static int renderBoundingBoxRange = 32;
    final private List<ItemStack> items;
    private NodeNetwork nodeNetwork;

//    private AxisAlignedBB renderBoundingBox;

    public NodeTileEntity()
    {
        super( ModTEs.MASTER_NODE.get() );
        this.items = new ArrayList<>();
    }

    public int getNetworkId()
    {
        return nodeNetwork.getId();
    }

//    @Override
//    public AxisAlignedBB getRenderBoundingBox()
//    {
//        return renderBoundingBox;
//    }

    @Override
    public void setWorldAndPos( World world, BlockPos pos )
    {
        super.setWorldAndPos( world, pos );
//        renderBoundingBox = new AxisAlignedBB( new BlockPos( pos.getX()-32, pos.getY()-32, pos.getZ()-32 ), new BlockPos( pos.getX()+32, pos.getY()+32, pos.getZ()+32 ) );
    }

    @Override
    public void tick()
    {
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
