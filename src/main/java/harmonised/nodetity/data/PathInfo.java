package harmonised.nodetity.data;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class PathInfo
{
    private List<NodeState> path;
    private double weight;

    public PathInfo( List<NodeState> path, double weight )
    {
        this.path = path;
        this.weight = weight;
    }

    public PathInfo( NodeState firstNode, double weight )
    {
        this.path = new ArrayList<>();
        this.path.add( firstNode );
        this.weight = weight;
    }

    public List<NodeState> getPath()
    {
        return path;
    }

//    public void setPath( List<NodeState> path )
//    {
//        this.path = path;
//    }

    public double getWeight()
    {
        return weight;
    }
}