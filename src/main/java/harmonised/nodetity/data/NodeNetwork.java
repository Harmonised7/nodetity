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
    private World masterWorld;
    private BlockPos masterPos;
    public double nodeMaxDistance = 7;

    public NodeNetwork( int id, Map<ResourceLocation, Set<BlockPos>> nodes, World masterWorld, BlockPos masterPos )
    {
        this.id = id;
        this.nodes = nodes;
        this.masterWorld = masterWorld;
        this.masterPos = masterPos;
    }

    public Set<BlockPos> getNodes( ResourceLocation resLoc )
    {
        if( !nodes.containsKey( resLoc ) )
            nodes.put( resLoc, new HashSet<>() );
        return nodes.get( resLoc );
    }

    public Set<BlockPos> getNearbyNodePos( World world, BlockPos pos )
    {
        ResourceLocation resLoc = Util.getDimensionResLoc( world );

        Set<BlockPos> nearbyNodes = new HashSet<>();
        Set<BlockPos> nodes = getNodes( resLoc );
        Set<BlockPos> falseNodes = new HashSet<>();
        for( BlockPos nodePos : nodes )
        {
            if( world.getBlockState( nodePos ).isAir() )
                falseNodes.add( nodePos );
            else if( !pos.equals( nodePos ) && Util.getDistance( pos, nodePos ) <= nodeMaxDistance )
                nearbyNodes.add( nodePos );
        }
        for( BlockPos falseNodePos : falseNodes )
        {
            nodes.remove( falseNodePos );
        }

        return nearbyNodes;
    }

    public int getId()
    {
        return id;
    }

    public World getMasterWorld()
    {
        return masterWorld;
    }

    public BlockPos getMasterPos()
    {
        return masterPos;
    }

    public void setMasterWorldAndPos( World world, BlockPos pos )
    {
        this.masterWorld = world;
        this.masterPos = pos;
    }
}
