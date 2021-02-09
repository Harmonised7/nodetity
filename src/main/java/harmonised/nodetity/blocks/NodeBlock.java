package harmonised.nodetity.blocks;

import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import harmonised.nodetity.registries.ModBlocks;
import harmonised.nodetity.registries.RegistryHandler;
import harmonised.nodetity.tile_entities.NodeTileEntity;
import harmonised.nodetity.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class NodeBlock extends Block
{
    public NodeBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity( BlockState state, IBlockReader world )
    {
        return new NodeTileEntity();
    }

    @Override
    public void onReplaced( BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving )
    {
        if( !newState.equals( getDefaultState() ) )
        {
            NodeNetwork nodeNetwork = Data.findNearbyNodeNetwork( world, pos );
            if( nodeNetwork != null )
            {
                nodeNetwork.getNodes( Util.getDimensionResLoc( world ) ).remove( pos );
                nodeNetwork.setMasterWorldAndPos( null, null );
            }
        }
        super.onReplaced(state, world, pos, newState, isMoving);
    }

    @Override
    public boolean removedByPlayer( BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid )
    {
        return super.removedByPlayer( state, world, pos, player, willHarvest, fluid );
    }

    @Override
    public void onExplosionDestroy( World world, BlockPos pos, Explosion explosionIn )
    {
        super.onExplosionDestroy( world, pos, explosionIn );
    }

    @Override
    public void onPlayerDestroy(IWorld world, BlockPos pos, BlockState state)
    {
        super.onPlayerDestroy(world, pos, state);
    }

    @Override
    public void onBlockPlacedBy( World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack )
    {
        super.onBlockPlacedBy( world, pos, state, placer, stack );
        NodeNetwork nodeNetwork = Data.findNearbyNodeNetwork( world, pos );
        if( nodeNetwork == null )
        {
            nodeNetwork = Data.createNodeNetwork( world, pos, 1 );
            System.out.println( "Master Node placed with id " + nodeNetwork.getId() );
        }
        else
        {
            nodeNetwork.setMasterWorldAndPos( world, pos );
            System.out.println( "Master Node replaced with id " + nodeNetwork.getId() );
        }
    }

    @Override
    public BlockState getStateForPlacement( BlockItemUseContext context )
    {
        NodeNetwork nodeNetwork = Data.getNodeNetwork( 1 );
        if( !context.getWorld().isRemote() )
        {
            System.out.println( "Server network is null: " + ( nodeNetwork == null ) );
        }
        if( nodeNetwork == null )
            return getDefaultState();
        else
        {
            World masterWorld = nodeNetwork.getMasterWorld();
            if( masterWorld == null || !masterWorld.getBlockState( nodeNetwork.getMasterPos() ).getBlock().equals( ModBlocks.MASTER_NODE_BLOCK.get() ) )
                return getDefaultState();
        }
        return null;
    }
}