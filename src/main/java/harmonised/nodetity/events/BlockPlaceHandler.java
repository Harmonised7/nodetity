package harmonised.nodetity.events;

import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import net.minecraftforge.event.world.BlockEvent;

public class BlockPlaceHandler
{
    public static void handleBlockPlaced( BlockEvent.EntityPlaceEvent event )
    {
        NodeNetwork nodeNetwork = Data.getNearbyNodeNetwork( event.getPos() );
        if( nodeNetwork != null )
        {
            nodeNetwork.nodes.add( event.getPos() );
        }
    }
}
