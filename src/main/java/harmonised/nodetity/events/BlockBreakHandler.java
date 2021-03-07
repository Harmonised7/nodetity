package harmonised.nodetity.events;

import com.google.common.collect.Lists;
import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import harmonised.nodetity.data.NodeState;
import harmonised.nodetity.util.Util;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

import java.util.ArrayList;
import java.util.List;

public class BlockBreakHandler
{
    public static void handleBlockBreak( BlockEvent.BreakEvent event )
    {
        World world = (World) event.getWorld();
        ResourceLocation dimResLoc = Util.getDimensionResLoc( world );
        NodeNetwork nodeNetwork = Data.findNearbyNodeNetwork( world, event.getPos() );
        if( nodeNetwork != null )
        {
            NodeState removedNode = nodeNetwork.getNode( dimResLoc, event.getPos() );
            if( removedNode != null )
            {
                nodeNetwork.removeNode( dimResLoc, removedNode );
                PlayerHandler.firstState = null;
                PlayerHandler.lastState = null;
            }
        }

        ArrayList<Double> array1 = new ArrayList<>();
        array1.add( 3D );
        array1.add( 6D );
//        array1.add( 9D );
//        array1.add( 12D );
//        array1.add( 15D );
        int lIndex = array1.indexOf( 3D );
        int uIndex = array1.indexOf( 6D )+1;
        System.out.println( lIndex + " " + uIndex );
        System.out.println( array1 );
        System.out.println( Lists.reverse( array1.subList( lIndex, uIndex ) ) );
    }
}
