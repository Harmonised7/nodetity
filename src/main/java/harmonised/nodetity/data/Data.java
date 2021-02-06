package harmonised.nodetity.data;

import harmonised.nodetity.util.Util;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Data
{
    public static Map<Integer, NodeNetwork> nodeNetworks = new HashMap<>();

    public static NodeNetwork findNodeNetwork( BlockPos pos )
    {
        for( int id : nodeNetworks.keySet() )
        {
            for( BlockPos nodePos : nodeNetworks.get( id ).nodes )
            {
                if( Util.getDistance( pos, nodePos ) <= 5 )
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
        NodeNetwork nodeNetwork = findNodeNetwork( pos );
        return nodeNetwork == null ? createNodeNetwork( pos ) : nodeNetwork;
    }
}
