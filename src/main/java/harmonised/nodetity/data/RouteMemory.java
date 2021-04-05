package harmonised.nodetity.data;

import java.util.HashSet;
import java.util.Iterator;

public class RouteMemory
{
    private final Iterator<NodeState> nextNodesIterator;
    private NodeState currNode;

    public RouteMemory( NodeState currNode )
    {
        this.nextNodesIterator = new HashSet<>( currNode.getNeighbors().keySet() ).iterator();
        this.currNode = nextNodesIterator.next();
    }

    public RouteMemory getNextRouteMemory()
    {
        if( nextNodesIterator.hasNext() )
        {
            currNode = nextNodesIterator.next();
            return new RouteMemory( currNode );
        }
        return null;
    }

    public NodeState getCurrNode()
    {
        return currNode;
    }
}