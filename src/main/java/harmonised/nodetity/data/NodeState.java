package harmonised.nodetity.data;

import harmonised.nodetity.blocks.NodeBlock;
import harmonised.nodetity.util.Util;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NodeState
{
    private final Block block;
    private final BlockPos pos;
    private final NodeNetwork network;
    private final World world;
    private final ResourceLocation dim;

    private Set<NodeState> neighbors = new HashSet<>();
    private Map<NodeState, List<NodeState>> sPaths;

    public NodeState( NodeNetwork network, World world, Block block, BlockPos pos )
    {
        this.network = network;
        this.world = world;
        this.block = block;
        this.pos = pos;

        this.dim = Util.getDimensionResLoc( world );
    }

    public void initNeighbors()
    {
        neighbors.clear();
        Set<NodeState> falseNodes = new HashSet<>();
        double nodeMaxDistance = network.nodeMaxDistance;

        for( NodeState nodeState : network.getNodes( dim ) )
        {
            BlockPos nodePos = nodeState.getPos();
            if( world.getBlockState( nodePos ).isAir() )
                falseNodes.add( nodeState );
            else if( !pos.equals( nodePos ) && Util.getDistance( pos, nodePos ) <= nodeMaxDistance )
                neighbors.add( nodeState );
        }

        for( NodeState falseNodeState : falseNodes )
        {
            network.removeNode( dim, falseNodeState );
        }
    }

    public World getWorld()
    {
        return world;
    }

    public BlockPos getPos()
    {
        return pos;
    }

    public Set<NodeState> getNeighbors()
    {
        return neighbors;
    }
}
