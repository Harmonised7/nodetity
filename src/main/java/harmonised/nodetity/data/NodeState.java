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
//            shortestPaths.put( destNode, shortestPath );

            int shortestPathSize = shortestPath.size();
            for( int lIndex = 0; lIndex < shortestPathSize; lIndex++ )
            {
                for( int uIndex = 0; uIndex < shortestPathSize; uIndex++ )
                {
                    if( lIndex != uIndex )
                    {
                        NodeState fItem, lItem;
                        if( lIndex > uIndex )
                        {
                            fItem = shortestPath.get( uIndex );
                            lItem = shortestPath.get( lIndex );
                            fItem.shortestPaths.put( lItem, Lists.reverse( shortestPath.subList( uIndex, lIndex+1 ) ) );
//                            System.out.println( "a" );
//                            System.out.println( "f: " + uIndex + ", l: " + lIndex );
//                            System.out.println( shortestPath.subList( uIndex, lIndex+1 ) );
                        }
                        else
                        {
                            fItem = shortestPath.get( lIndex );
                            lItem = shortestPath.get( uIndex );
                            fItem.shortestPaths.put( lItem, shortestPath.subList( lIndex, uIndex+1 ) );
//                            System.out.println( "b" );
//                            System.out.println( "f: " + lIndex + ", l: " + uIndex );
                        }
//                        System.out.println( "Linko Startu!!" );
                    }
                }
            }

//            for( int lIndex = 0; lIndex < shortestPathSize; lIndex++ )
//            {
//                for( int uIndex = 0; uIndex < shortestPathSize; uIndex++ )
//                {
//                    boolean reverse = false;
//                    NodeState node1 = shortestPath.get(lIndex);
//                    NodeState node2 = shortestPath.get(uIndex);
//                    if( node1.shortestPaths.containsKey( node2 ) )
//                        continue;
//                    List<NodeState> newPath;
////                    System.out.println( lIndex + " " + uIndex );
////                    System.out.println( "size: " + shortestPath.size() );
//                    if( lIndex > uIndex )
//                    {
//                        newPath = Lists.reverse( shortestPath.subList( uIndex, lIndex+1 ) );
//                        reverse = true;
//                    }
//                    else
//                        newPath = shortestPath.subList( lIndex, uIndex+1 );
//                    if( newPath.size() < 2 )
//                        continue;
////                    System.out.println( "new size: " + newPath.size() );
////                    System.out.println( "linking new path" );
//                    if( reverse )
//                        setShortestPath( node2, node1, newPath );
//                    else
//                        setShortestPath( node1, node2, newPath );
//                }
//            }
        }
    }

    @Deprecated
    public Map<NodeState, Map<List<NodeState>, Double>> getAllPaths()
    {
        return allPaths;
    }

    @Deprecated
    public Map<NodeState, List<NodeState>> getShortestPaths()
    {
        return shortestPaths;
    }

    public List<NodeState> getShortestPath( NodeState destState )
    {
        createShortestPathTo( destState );
        return shortestPaths.get( destState );
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

    @Override
    public String toString()
    {
        return "[" + pos.getX() + ":" + pos.getY() + ":" + pos.getZ() + "]";
    }
}
