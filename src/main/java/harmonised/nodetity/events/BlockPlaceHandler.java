package harmonised.nodetity.events;

import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class BlockPlaceHandler
{
    public static void handleBlockPlaced( BlockEvent.EntityPlaceEvent event )
    {
        NodeNetwork nodeNetwork = Data.getOrCreateNodeNetwork( (World) event.getWorld(), 1, event.getPos() );

        nodeNetwork.nodes.add( event.getPos() );
        System.out.println( "Added to Network " + nodeNetwork.id );
    }
}
