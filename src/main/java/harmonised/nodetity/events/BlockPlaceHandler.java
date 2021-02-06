package harmonised.nodetity.events;

import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import harmonised.nodetity.util.Util;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class BlockPlaceHandler
{
    public static void handleBlockPlaced( BlockEvent.EntityPlaceEvent event )
    {
        World world = (World) event.getWorld();
        NodeNetwork nodeNetwork = Data.getOrCreateNodeNetwork( world, 1, event.getPos() );
        ResourceLocation resLoc = Util.getDimensionResLoc( world );

        nodeNetwork.getNodes( resLoc ).add( event.getPos() );
        System.out.println( "Added to Network " + nodeNetwork.getId() );
    }
}
