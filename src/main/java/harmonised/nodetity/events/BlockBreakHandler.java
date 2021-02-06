package harmonised.nodetity.events;

import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class BlockBreakHandler
{
    public static void handleBlockBreak( BlockEvent.BreakEvent event )
    {
        NodeNetwork nodeNetwork = Data.findNearbyNodeNetwork( (World) event.getWorld(), event.getPos() );
        if( nodeNetwork != null )
        {
            nodeNetwork.nodes.remove( event.getPos() );
        }
    }
}
