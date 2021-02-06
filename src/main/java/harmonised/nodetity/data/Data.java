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
    public static Map<Integer, Map<ResourceLocation, NodeNetwork>> nodeNetworks = new HashMap<>();

    public static NodeNetwork findNearbyNodeNetwork( World world, BlockPos pos )
    {
        ResourceLocation resLoc = Util.getDimensionResLoc( world );
        for( int id : nodeNetworks.keySet() )
        {
            Map<ResourceLocation, NodeNetwork> worldNodeNetwork = nodeNetworks.get( id );
            if( worldNodeNetwork.containsKey( resLoc ) )
            {
                NodeNetwork nodeNetwork = worldNodeNetwork.get( resLoc );
                for( BlockPos nodePos : nodeNetwork.nodes )
                {
                    if( Util.getDistance( pos, nodePos ) <= nodeNetwork.nodeMaxDistance )
                        return nodeNetwork;
                }
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
        HashSet<BlockPos> nodes = new HashSet<>();
        nodes.add( pos );
        Map<ResourceLocation, NodeNetwork> globalNodeNetwork = nodeNetworks.get( id );
        globalNodeNetwork.put( resLoc, new NodeNetwork( id, nodes ) );
        return globalNodeNetwork.get( resLoc );
    }

    public static NodeNetwork findOrCreateNetwork( World world, BlockPos pos )
    {
        NodeNetwork nodeNetwork = findNearbyNodeNetwork( world, pos );
        return nodeNetwork == null ? createNodeNetwork( world, pos ) : nodeNetwork;
    }

    public static NodeNetwork getNodeNetwork( World world, int id )
    {
        if( nodeNetworks.containsKey( id ) )
            return nodeNetworks.get( id ).get( Util.getDimensionResLoc( world ) );
        else
            return null;
    }

    public static NodeNetwork getOrCreateNodeNetwork( World world, int id, BlockPos pos )
    {
        NodeNetwork nodeNetwork = Data.getNodeNetwork( world, id );
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
            NodeNetwork nodeNetwork = nodeNetworks.get( id ).get( resLoc );
            if( nodeNetwork != null )
            {
                for( BlockPos nodePos : nodeNetwork.nodes )
                {
                    if( !pos.equals( nodePos ) && Util.getDistance( pos, nodePos ) <= nodeNetwork.nodeMaxDistance )
                        nearbyNodes.add( nodePos );
                }
            }
        }

        return nearbyNodes;
    }
}
