package harmonised.nodetity.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class RouteMemory
{
    private final List<NodeState> nextNodes;
    private int i = 1;
//    private final Iterator<NodeState> nextNodesIterator;
    private NodeState currNode;

    public RouteMemory( NodeState currNode )
    {
        this.nextNodes = new ArrayList<>( currNode.getNeighbors().keySet() );
        this.currNode = currNode;
//        this.nextNodesIterator = new HashSet<>( currNode.getNeighbors().keySet() ).iterator();
//        this.currNode = nextNodesIterator.next();
    }

    public RouteMemory getNextRouteMemory()
    {
        if( nextNodes.size() > i )
            return new RouteMemory( nextNodes.get( i++ ) );
        else
            return null;
//        if( nextNodesIterator.hasNext() )
//        {
//            currNode = nextNodesIterator.next();
//            return new RouteMemory( currNode );
//        }
//        return null;
    }

    public NodeState getCurrNode()
    {
        return currNode;
    }
}