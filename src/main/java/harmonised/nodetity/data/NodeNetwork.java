package harmonised.nodetity.data;

import harmonised.nodetity.util.Util;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

    public Set<BlockPos> getNearbyNodePos( World world, BlockPos pos )
    {
        Set<BlockPos> nearbyNodes = new HashSet<>();

        ResourceLocation resLoc = Util.getDimensionResLoc( world );

        for( BlockPos nodePos : getNodes( resLoc ) )
        {
            if( !pos.equals( nodePos ) && Util.getDistance( pos, nodePos ) <= nodeMaxDistance )
                nearbyNodes.add( nodePos );
        }

        return nearbyNodes;
    }

    public int getId()
    {
        return id;
    }
}
