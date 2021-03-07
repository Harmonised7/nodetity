package harmonised.nodetity.data;

import com.google.common.collect.Lists;
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
    private Map<NodeState, Map<List<NodeState>, Double>> allPaths = new HashMap<>();
    private Map<NodeState, List<NodeState>> shortestPaths = new HashMap<>();
    private double shortestLength;

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

    public void clearAllPaths()
    {
        allPaths.clear();
        shortestPaths.clear();
    }

//    public NodeState getNextDestinationNode( NodeState destNode )
//    {
//        if( !sPaths.containsKey( destNode ) )
//            createShortestPath( this, destNode );
//
//        return sPaths.get( destNode );
//    }

    public void createShortestPathTo( NodeState destNode )
    {
        if( shortestPaths.containsKey( destNode ) )
            return;
        shortestLength = Integer.MAX_VALUE;
        List<NodeState> currPath = new ArrayList<>();
        currPath.add( this );
        recursiveFindPath( currPath, destNode, 0, new HashSet<>() );
        if( allPaths.containsKey( destNode ) )
        {
            Map<List<NodeState>, Double> allDestPaths = allPaths.get( destNode );
            List<List<NodeState>> allDestPathsKeys = new ArrayList<>( allDestPaths.keySet() );
            allDestPathsKeys.sort( Comparator.comparingDouble( allDestPaths::get ) );
            List<NodeState> shortestPath = allDestPathsKeys.get(0);
            shortestPaths.put( destNode, shortestPath );
            int shortestPathSize = shortestPath.size();
        }
    }

    //Debug Only
    public Map<NodeState, Map<List<NodeState>, Double>> getAllPaths()
    {
        return allPaths;
    }

    public Map<NodeState, List<NodeState>> getShortestPaths()
    {
        return shortestPaths;
    }

    public static void setShortestPath( NodeState originNode, NodeState destNode, List<NodeState> path )
    {
        originNode.shortestPaths.put( destNode, path );
    }

    private void recursiveFindPath( List<NodeState> currPath, NodeState destNode, double currWeight, Set<NodeState> doneNodes )
    {
        NodeState thisNode = currPath.get( currPath.size()-1 );
        if( thisNode.getPos().equals( destNode.getPos() ) )
        {
            if( currPath.size() == 1 )
                return;
            if( currWeight < shortestLength )
            {
                shortestLength = currWeight;
                if( !allPaths.containsKey( thisNode ) )
                    allPaths.put( thisNode, new HashMap<>() );
                allPaths.get( thisNode ).put( currPath, currWeight );
            }
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
//        System.out.println( "Dead End" );
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
