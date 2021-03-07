package harmonised.nodetity.data;

import harmonised.nodetity.util.Util;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class NodeState
{
    private final Block block;
    private final BlockPos pos;
    private final NodeNetwork network;
    private final World world;
    private final ResourceLocation dim;

    private final Map<NodeState, Double> neighbors = new HashMap<>();
    private Map<NodeState, Map<List<NodeState>, Double>> sPaths = new HashMap<>();
    private static double shortestLength;

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
            double distance = Util.getDistance( pos, nodePos );
            if( world.getBlockState( nodePos ).isAir() )
                falseNodes.add( nodeState );
            else if( !pos.equals( nodePos ) && distance <= nodeMaxDistance )
                neighbors.put( nodeState, distance );
        }

        for( NodeState falseNodeState : falseNodes )
        {
            network.removeNode( dim, falseNodeState );
        }
    }

    public void clearShortestPaths()
    {
        sPaths.clear();
    }

//    public NodeState getNextDestinationNode( NodeState destNode )
//    {
//        if( !sPaths.containsKey( destNode ) )
//            createShortestPath( this, destNode );
//
//        return sPaths.get( destNode );
//    }

    public void createShortestPath( NodeState currNode, NodeState destNode )
    {
        shortestLength = Integer.MAX_VALUE;
        List<NodeState> currPath = new ArrayList<>();
        currPath.add( currNode );
        recursiveFindPath( currPath, destNode, 0, new HashSet<>() );
    }

    private void recursiveFindPath( List<NodeState> currPath, NodeState destNode, double currWeight, Set<NodeState> doneNodes )
    {
        NodeState thisNode = currPath.get( currPath.size()-1 );
        if( thisNode.getPos().equals( destNode.getPos() ) )
        {
            System.out.println( "Found!!!" );
            if( !sPaths.containsKey( thisNode ) )
                sPaths.put( thisNode, new HashMap<>() );
            sPaths.get( thisNode ).put( currPath, currWeight );
            if( currWeight < shortestLength )
                shortestLength = currWeight;
        }
        else
        {
            for( Map.Entry<NodeState, Double> neighborNode : thisNode.getNeighbors().entrySet() )
            {
                if( !doneNodes.contains( neighborNode.getKey() ) )
                {
                    double nextWeight = currWeight + neighborNode.getValue();
                    if( nextWeight > shortestLength )
                        continue;
                    List<NodeState> nextPath = new ArrayList<>( currPath );
                    Set<NodeState> nextDoneNodes = new HashSet<>( doneNodes );
                    nextDoneNodes.add( thisNode );
                    nextPath.add( neighborNode.getKey() );
                    recursiveFindPath( nextPath, destNode, nextWeight, nextDoneNodes );
                }
            }
        }

        System.out.println( "Dead End" );
    }

    public World getWorld()
    {
        return world;
    }

    public BlockPos getPos()
    {
        return pos;
    }

    public Map<NodeState, Double> getNeighbors()
    {
        return neighbors;
    }
}
