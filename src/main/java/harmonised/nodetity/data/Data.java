package harmonised.nodetity.data;

import harmonised.nodetity.util.Util;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Data
{
    public static Map<Integer, NodeNetwork> nodeNetworks = new HashMap<>();

    public static NodeNetwork getNearbyNodeNetwork( BlockPos pos )
    {
        for( int id : nodeNetworks.keySet() )
        {
            NodeNetwork nodeNetwork = nodeNetworks.get( id );
            for( BlockPos nodePos : nodeNetwork.nodes )
            {
                if( Util.getDistance( pos, nodePos ) <= nodeNetwork.nodeMaxDistance )
                    return nodeNetworks.get( id );
            }
        }

        return null;
    }

    private static NodeNetwork createNodeNetwork( BlockPos pos )
    {
        int i = 0;

        while( nodeNetworks.containsKey( i ) )
        {
            i++;
        }

        HashSet<BlockPos> nodes = new HashSet<>();
        nodes.add( pos );

        nodeNetworks.put( i, new NodeNetwork( i, nodes ) );

        return nodeNetworks.get( i );
    }

    public static NodeNetwork findOrCreateNetwork( BlockPos pos )
    {
        NodeNetwork nodeNetwork = getNearbyNodeNetwork( pos );
        return nodeNetwork == null ? createNodeNetwork( pos ) : nodeNetwork;
    }

    public static Set<BlockPos> getNearbyNodePos(int id, BlockPos pos )
    {
        Set<BlockPos> nearbyNodes = new HashSet<>();

        NodeNetwork nodeNetwork = nodeNetworks.get( id );
        for( BlockPos nodePos : nodeNetwork.nodes )
        {
            if( Util.getDistance( pos, nodePos ) <= nodeNetwork.nodeMaxDistance )
                nearbyNodes.add( nodePos );
        }

        return nearbyNodes;
    }
}
