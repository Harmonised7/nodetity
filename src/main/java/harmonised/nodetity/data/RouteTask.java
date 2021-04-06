package harmonised.nodetity.data;

import harmonised.nodetity.client.NetworkRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class RouteTask
{
    private final NodeNetwork network;
    private final Set<NodeState> nodes;
    private final Set<NodeState> doneNodes = new HashSet<>();
    private final Map<NodeState, PathInfo> originAllPaths = new HashMap<>();
    private final Map<NodeState, List<NodeState>> originShortestPaths;
    private final ResourceLocation dim;
    private final long startTime;
    private final NodeState originState;
    private final List<RouteMemory> memoryStack = new ArrayList<>();
    private final ReusePathInfo currPath;
    private int i = 0;

    public RouteTask( NodeState originState )
    {
        this.originState = originState;
        this.network = originState.getNetwork();
        this.originShortestPaths = originState.getShortestPaths();
        this.dim = originState.getDim();
        this.nodes = network.getNodes( dim );
        this.startTime = System.currentTimeMillis();
        memoryStack.add( new RouteMemory( originState ) );
        currPath = new ReusePathInfo( originState );
    }

    public boolean process( int i )
    {
        while( i-- > 0 )
        {
            if( doTick() )
            {
                System.out.println( "Pathing took: " + ( System.currentTimeMillis() - startTime ) + "ms" );
                originShortestPaths.clear();
                for( Map.Entry<NodeState, PathInfo> bestPath : originAllPaths.entrySet() )
                {
                    originState.cacheFullPath( bestPath.getValue().getPath() );
                }
                return true;
            }
        }
        return false;
    }

    private boolean doTick()
    {
//        System.out.println( "i: " + i );
        RouteMemory currMemory = memoryStack.get( i );
        NodeState currNode = currMemory.getCurrNode();
        RouteMemory nextMemory = currMemory.getNextRouteMemory();

        if( nextMemory == null )
        {
//            System.out.println( "Removing Memory " + i );
            memoryStack.remove( i );
            currPath.pop();
            NetworkRenderer.asyncPath = currPath.getPath();
            i--;
            return i < 0;
        }
        else
        {
            NodeState nextNode = nextMemory.getCurrNode();
            if( currPath.contains( nextNode ) )
            {
//                System.out.println( "Skipping Done Node" );
                return false;
            }
//            System.out.println( "Adding Memory " + i );
            boolean pathExists = originAllPaths.containsKey( nextNode );
            double distToNext = nextNode.getDistanceToNeighbor( currNode );
            if( pathExists && currPath.getWeight() + distToNext > originAllPaths.get( nextNode ).getWeight() )
                return false;
            i++;
            memoryStack.add( nextMemory );
            currPath.push( nextNode, distToNext );
            if( !pathExists || originAllPaths.get( nextNode ).getWeight() > currPath.getWeight() )
                originAllPaths.put( nextNode, new PathInfo( new ArrayList<>( currPath.getPath() ), currPath.getWeight() ) );
            NetworkRenderer.asyncPath = currPath.getPath();
            return false;
        }
    }

//    private void recursiveFindShortestPaths( PathInfo pathInfo, Set<NodeState> doneNodes, Map<NodeState, PathInfo> allPaths )
//    {
//        List<NodeState> currPath = pathInfo.getPath();
//        NodeState thisNode = currPath.get( currPath.size()-1 );
//        if( shortestPaths.containsKey( thisNode ) )
//        {
////            System.out.println( "Redundant pathing" );
//            return;
//        }
//        PathInfo oldPathInfo = allPaths.get( thisNode );
//        if( pathInfo.getWeight() < oldPathInfo.getWeight() )
//        {
//            allPaths.put( thisNode, pathInfo );
////            System.out.println( "Path Found" );
//        }
//
//        for( Map.Entry<NodeState, Double> neighborNode : thisNode.getNeighbors().entrySet() )
//        {
//            if( !doneNodes.contains( neighborNode.getKey() ) )
//            {
//                double nextWeight = pathInfo.getWeight() + neighborNode.getValue();
//                List<NodeState> nextPath = new ArrayList<>( currPath );
//                nextPath.add( neighborNode.getKey() );
//                Set<NodeState> nextDoneNodes = new HashSet<>( doneNodes );
//                nextDoneNodes.add( thisNode );
//                PathInfo nextPathInfo = new PathInfo( nextPath, nextWeight );
//                recursiveFindShortestPaths( nextPathInfo, nextDoneNodes, allPaths );
//            }
//        }
////        System.out.println( "Dead End" );
//    }
}