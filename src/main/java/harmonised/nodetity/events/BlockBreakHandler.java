package harmonised.nodetity.events;

import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import harmonised.nodetity.util.Util;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class BlockBreakHandler
{
    public static void handleBlockBreak( BlockEvent.BreakEvent event )
    {
        World world = (World) event.getWorld();
        ResourceLocation resLoc = Util.getDimensionResLoc( world );
        NodeNetwork nodeNetwork = Data.findNearbyNodeNetwork( world, event.getPos() );
        if( nodeNetwork != null )
        {
            nodeNetwork.getNodes( resLoc ).remove( event.getPos() );
        }
    }
}
