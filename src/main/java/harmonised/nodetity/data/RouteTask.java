package harmonised.nodetity.data;

import harmonised.nodetity.client.NetworkRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class RouteTask
{
    //Finals
    private final NodeNetwork network;
    private final Set<NodeState> nodes;
//    private final Set<NodeState> doneNodes = new HashSet<>();
    private final Map<NodeState, PathInfo> originAllPaths = new HashMap<>();
    private final Map<NodeState, List<NodeState>> originShortestPaths;
    private final ResourceLocation dim;
    private final long startTime;
    private final NodeState originState;
    private final List<RouteMemory> memoryStack = new ArrayList<>();
    private final ReusePathInfo currPath;

    //Cache
    private RouteMemory currMemory, nextMemory;
    private NodeState currNode, nextNode;
    private Double bestWeight;
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
        //Init Vars

        //Do Tick

        //Tick Wrap Up

        return false;
    }

    private void startTick()
    {
        currMemory = memoryStack.get( i );
        currNode = currMemory.getCurrNode();
    }

//    private boolean doTick()
//    {
////        System.out.println( "i: " + i );
//        RouteMemory currMemory = memoryStack.get( i );
//        NodeState currNode = currMemory.getCurrNode();
//        RouteMemory nextMemory = currMemory.getNextRouteMemory();
//
//        if( nextMemory == null )
//        {
////            System.out.println( "Removing Memory " + i );
//            memoryStack.remove( i );
//            currPath.pop();
//            NetworkRenderer.asyncPath = currPath.getPath();
//            i--;
//            return i < 0;
//        }
//        else
//        {
//            NodeState nextNode = nextMemory.getCurrNode();
//            if( currPath.contains( nextNode ) )
//            {
////                System.out.println( "Skipping Done Node" );
//                return false;
//            }
////            System.out.println( "Adding Memory " + i );
//            boolean pathExists = originAllPaths.containsKey( nextNode );
//            double distToNext = nextNode.getDistanceToNeighbor( currNode );
//            if( pathExists && currPath.getWeight() + distToNext > originAllPaths.get( nextNode ).getWeight() )
//                return false;
//            i++;
//            memoryStack.add( nextMemory );
//            currPath.push( nextNode, distToNext );
//            if( !pathExists || originAllPaths.get( nextNode ).getWeight() > currPath.getWeight() )
//                originAllPaths.put( nextNode, new PathInfo( new ArrayList<>( currPath.getPath() ), currPath.getWeight() ) );
//            NetworkRenderer.asyncPath = currPath.getPath();
//            return false;
//        }
//    }
}