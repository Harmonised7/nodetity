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
    private static Map<Integer, NodeNetwork> nodeNetworks = new HashMap<>();

    public static NodeNetwork findNearbyNodeNetwork( World world, BlockPos pos )
    {
        ResourceLocation resLoc = Util.getDimensionResLoc( world );
        for( int id : nodeNetworks.keySet() )
        {
            NodeNetwork nodeNetwork = nodeNetworks.get( id );
           for( NodeState nodeState : nodeNetwork.getNodes( resLoc ) )
           {
               if( Util.getDistance( pos, nodeState.getPos() ) <= nodeNetwork.nodeMaxDistance )
                   return nodeNetwork;
           }
        }

        return null;
    }

    public static NodeNetwork createNodeNetwork( World world, BlockPos pos )
    {
        int id = 0;
        while( nodeNetworks.containsKey( id ) )
        {
            id++;
        }
        return createNodeNetwork( world, pos, id );
    }

    public static NodeNetwork createNodeNetwork( World world, BlockPos pos, int id )
    {
//        ResourceLocation resLoc = Util.getDimensionResLoc( world );
        Map<ResourceLocation, Set<NodeState>> nodes = new HashMap<>();
        NodeNetwork nodeNetwork = new NodeNetwork( id, nodes, world, pos );
        nodeNetworks.put( id, nodeNetwork );
        return nodeNetworks.get( id );
    }

    public static NodeNetwork findOrCreateNetwork( World world, BlockPos pos )
    {
        NodeNetwork nodeNetwork = findNearbyNodeNetwork( world, pos );
        return nodeNetwork == null ? createNodeNetwork( world, pos ) : nodeNetwork;
    }

    public static Map<Integer, NodeNetwork> getNodeNetworks()
    {
        return nodeNetworks;
    }

    public static NodeNetwork getNodeNetwork( int id )
    {
        return nodeNetworks.get( id );
    }

    public static NodeNetwork getOrCreateNodeNetwork( World world, int id, BlockPos pos )
    {
        NodeNetwork nodeNetwork = Data.getNodeNetwork( id );
        if( nodeNetwork == null )
            nodeNetwork = Data.createNodeNetwork( world, pos, 1 );
        return nodeNetwork;
    }

    public static void removeNodeNetwork( int id )
    {
        nodeNetworks.remove( id );
    }
}
