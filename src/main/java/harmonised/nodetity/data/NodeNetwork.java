package harmonised.nodetity.data;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NodeNetwork
{
    private final int id;
    private final Map<ResourceLocation, Set<BlockPos>> nodes;
    public double nodeMaxDistance = 7;

    public NodeNetwork( int id, Map<ResourceLocation, Set<BlockPos>> nodes )
    {
        this.id = id;
        this.nodes = nodes;
    }

    public Set<BlockPos> getNodes( ResourceLocation resLoc )
    {
        if( !nodes.containsKey( resLoc ) )
            nodes.put( resLoc, new HashSet<>() );
        return nodes.get( resLoc );
    }

    public int getId()
    {
        return id;
    }
}
