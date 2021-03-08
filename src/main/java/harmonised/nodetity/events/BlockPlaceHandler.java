package harmonised.nodetity.events;

import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import harmonised.nodetity.data.NodeState;
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
        ResourceLocation dimResLoc = Util.getDimensionResLoc( world );

        if( nodeNetwork == null )
            return;

        nodeNetwork.addNode( dimResLoc, new NodeState( nodeNetwork, world, event.getPlacedBlock().getBlock(), event.getPos() ) );
        nodeNetwork.reconstructNetwork( dimResLoc );
        System.out.println( "Added to Network " + nodeNetwork.getId() );

        if( event.getPlacedBlock().getBlock().equals(Blocks.BEDROCK ) )
        {
            PlayerHandler.firstState = null;
            PlayerHandler.lastState = null;
            Data.removeNodeNetwork( 1 );
        }
    }
}
