package harmonised.nodetity.events;

import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import harmonised.nodetity.util.Util;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class BlockPlaceHandler
{
    public static void handleBlockPlaced( BlockEvent.EntityPlaceEvent event )
    {
        World world = (World) event.getWorld();
        NodeNetwork nodeNetwork = Data.getNodeNetwork( 1 );
        ResourceLocation resLoc = Util.getDimensionResLoc( world );

        if( nodeNetwork == null )
            return;

        nodeNetwork.getNodes( resLoc ).add( event.getPos() );
        System.out.println( "Added to Network " + nodeNetwork.getId() );

        if( event.getPlacedBlock().getBlock().equals(Blocks.BEDROCK ) )
            Data.removeNodeNetwork( 1 );
    }
}
