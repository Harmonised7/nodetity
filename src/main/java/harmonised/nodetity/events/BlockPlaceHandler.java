package harmonised.nodetity.events;

import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import net.minecraftforge.event.world.BlockEvent;

public class BlockPlaceHandler
{
    public static void handleBlockPlaced( BlockEvent.EntityPlaceEvent event )
    {
        NodeNetwork nodeNetwork = Data.findNearbyNodeNetwork( event.getPos() );
        if( nodeNetwork != null )
        {
            nodeNetwork.nodes.add( event.getPos() );
            System.out.println( "Added to Network " + nodeNetwork.id );
        }
    }
}
