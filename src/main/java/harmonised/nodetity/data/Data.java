package harmonised.nodetity.data;

import harmonised.nodetity.util.Util;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Data
{
    public static Map<Integer, NodeNetwork> nodeNetworks = new HashMap<>();

    public static NodeNetwork findNearbyNodeNetwork( World world, BlockPos pos )
    {
        ResourceLocation resLoc = Util.getDimensionResLoc( world );
        for( int id : nodeNetworks.keySet() )
        {
            NodeNetwork nodeNetwork = nodeNetworks.get( id );
           for( BlockPos nodePos : nodeNetwork.getNodes( resLoc ) )
           {
               if( Util.getDistance( pos, nodePos ) <= nodeNetwork.nodeMaxDistance )
                   return nodeNetwork;
           }
        }

        return null;
    }

    public static NodeNetwork createNodeNetwork( World world, BlockPos pos )
    {
        ResourceLocation resLoc = Util.getDimensionResLoc( world );
        int id = 0;
        while( nodeNetworks.containsKey( id ) )
        {
            id++;
        }
        Map<ResourceLocation, Set<BlockPos>> nodes = new HashMap<>();
        nodes.put( resLoc, new HashSet<>() );
        nodes.get( resLoc ).add( pos );
        NodeNetwork nodeNetwork = new NodeNetwork( id, nodes );
        nodeNetworks.put( id, nodeNetwork );
        return nodeNetworks.get( id );
    }

    public static NodeNetwork findOrCreateNetwork( World world, BlockPos pos )
    {
        NodeNetwork nodeNetwork = findNearbyNodeNetwork( world, pos );
        return nodeNetwork == null ? createNodeNetwork( world, pos ) : nodeNetwork;
    }

    public static NodeNetwork getNodeNetwork( int id )
    {
        return nodeNetworks.get( id );
    }

    public static NodeNetwork getOrCreateNodeNetwork( World world, int id, BlockPos pos )
    {
        NodeNetwork nodeNetwork = Data.getNodeNetwork( id );
        if( nodeNetwork == null )
            nodeNetwork = Data.createNodeNetwork( world, pos );
        return nodeNetwork;
    }

    public static Set<BlockPos> getNearbyNodePos( World world, int id, BlockPos pos )
    {
        Set<BlockPos> nearbyNodes = new HashSet<>();

        if( nodeNetworks.containsKey( id ) )
        {
            ResourceLocation resLoc = Util.getDimensionResLoc( world );
            NodeNetwork nodeNetwork = nodeNetworks.get( id );
            if( nodeNetwork != null )
            {
                for( BlockPos nodePos : nodeNetwork.getNodes( resLoc ) )
                {
                    if( !pos.equals( nodePos ) && Util.getDistance( pos, nodePos ) <= nodeNetwork.nodeMaxDistance )
                        nearbyNodes.add( nodePos );
                }
            }
        }

        return nearbyNodes;
    }
}
