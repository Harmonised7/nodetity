package harmonised.nodetity.data;

import java.util.*;

public class ReusePathInfo
{
    private List<NodeState> path = new ArrayList<>();
    private Set<NodeState> nodes = new HashSet<>();
    private Map<NodeState, Double> weights = new HashMap<>();
    private double weight;

    public ReusePathInfo( NodeState firstNode )
    {
        this.path.add( firstNode );
        this.nodes.add( firstNode );
        this.weights.put( firstNode, 0D );
    }

    public List<NodeState> getPath()
    {
        return path;
    }

    public double getWeight()
    {
        return weight;
    }

    public void setWeight( double weight )
    {
        this.weight = weight;
    }

    public void push( NodeState state, double weight )
    {
        path.add( state );
        weights.put( state, weight );
        nodes.add( state );
    }

    public void pop()
    {
        NodeState nodeState = path.get( path.size() - 1 );
        weight -= weights.get( nodeState );
//        weights.remove( nodeState );
        path.remove( nodeState );
        nodes.remove( nodeState );
    }

    public boolean contains( NodeState state )
    {
        return nodes.contains( state );
    }
}
