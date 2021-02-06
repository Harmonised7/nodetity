package harmonised.nodetity.events;

import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import net.minecraftforge.event.world.BlockEvent;

public class BlockBreakHandler
{
    public static void handleBlocKBreak( BlockEvent.BreakEvent event )
    {
        NodeNetwork nodeNetwork = Data.getNearbyNodeNetwork( event.getPos() );
        if( nodeNetwork != null )
        {
            nodeNetwork.nodes.remove( event.getPos() );
        }
    }
}
