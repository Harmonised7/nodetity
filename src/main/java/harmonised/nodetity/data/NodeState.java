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
    private final Map<NodeState, List<NodeState>> shortestPaths = new HashMap<>();

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
        shortestPaths.clear();
    }

    @Deprecated
    public Map<NodeState, List<NodeState>> getShortestPaths()
    {
        return shortestPaths;
    }

    public List<NodeState> getShortestPath( NodeState destState )
    {
        List<NodeState> path = shortestPaths.get( destState );
        if( path == null )
        {
            path = destState.getShortestPathDumb( this );
            if( path != null )
                path = Lists.reverse( path );
            else
            {
                makeShortestPaths();
                path = shortestPaths.get( destState );
            }
        }
        return path;
    }

    private List<NodeState> getShortestPathDumb( NodeState destState )
    {
        return shortestPaths.get( destState );
    }

    public void makeShortestPaths()
    {
        long startTime = System.currentTimeMillis();
        Map<NodeState, PathInfo> allPaths = new HashMap<>();
        for( NodeState nodeState : network.getNodes( this.dim ) )
        {
            allPaths.put( nodeState, new PathInfo( new ArrayList<>(), Double.POSITIVE_INFINITY ) );
        }
        Set<NodeState> doneNodes = new HashSet<>();
        doneNodes.add( this );
        recursiveFindShortestPaths( new PathInfo( this, 0 ), doneNodes, allPaths );
        shortestPaths.clear();
        for( Map.Entry<NodeState, PathInfo> bestPath : allPaths.entrySet() )
        {
            cacheFullPath( bestPath.getValue().getPath() );
        }
        System.out.println( "Pathing took: " + ( System.currentTimeMillis() - startTime ) + "ms" );
    }

    private void cacheFullPath( List<NodeState> shortestPath )
    {
        int shortestPathSize = shortestPath.size();
        for( int lIndex = 0; lIndex < shortestPathSize; lIndex++ )
        {
            for( int uIndex = 0; uIndex < shortestPathSize; uIndex++ )
            {
                NodeState fItem, lItem;
                if( lIndex < uIndex )
                {
                    fItem = shortestPath.get( lIndex );
                    lItem = shortestPath.get( uIndex );
                    fItem.shortestPaths.put( lItem, shortestPath.subList( lIndex, uIndex+1 ) );
//                    System.out.println( "b" );
//                    System.out.println( "f: " + lIndex + ", l: " + uIndex );
                }
            }
        }
    }

    private void recursiveFindShortestPaths( PathInfo pathInfo, Set<NodeState> doneNodes, Map<NodeState, PathInfo> allPaths )
    {
        List<NodeState> currPath = pathInfo.getPath();
        NodeState thisNode = currPath.get( currPath.size()-1 );
        if( shortestPaths.containsKey( thisNode ) )
        {
//            System.out.println( "Redundant pathing" );
            return;
        }
        PathInfo oldPathInfo = allPaths.get( thisNode );
        if( pathInfo.getWeight() < oldPathInfo.getWeight() )
        {
            allPaths.put( thisNode, pathInfo );
//            System.out.println( "Path Found" );
        }

        for( Map.Entry<NodeState, Double> neighborNode : thisNode.getNeighbors().entrySet() )
        {
            if( !doneNodes.contains( neighborNode.getKey() ) )
            {
                double nextWeight = pathInfo.getWeight() + neighborNode.getValue();
                List<NodeState> nextPath = new ArrayList<>( currPath );
                nextPath.add( neighborNode.getKey() );
                Set<NodeState> nextDoneNodes = new HashSet<>( doneNodes );
                nextDoneNodes.add( thisNode );
                PathInfo nextPathInfo = new PathInfo( nextPath, nextWeight );
                recursiveFindShortestPaths( nextPathInfo, nextDoneNodes, allPaths );
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

    @Override
    public String toString()
    {
//        return "[" + pos.getX() + ":" + pos.getY() + ":" + pos.getZ() + "]";
        return "" + pos.getX() + pos.getY() + pos.getZ();
    }
}
