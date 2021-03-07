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
import java.util.Map;

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

//        try
//        {
//            ArrayList<Integer> array1 = new ArrayList<>();
//            array1.add( 1 );
//            array1.add( 2 );
//            array1.add( 3 );
//            array1.add( 4 );
//            array1.add( 5 );
//            Map<Integer, Map<Integer, List<Integer>>> paths;
//
//            int size = array1.size();
//            for( int lIndex = 0; lIndex < size; lIndex++ )
//            {
//                for( int uIndex = 0; uIndex < size; uIndex++ )
//                {
//                    if( lIndex != uIndex )
//                    {
//                        int fItem, lItem;
//                        if( lIndex > uIndex )
//                        {
//                            fItem = array1.get( uIndex );
//                            lItem = array1.get( lIndex );
//                            System.out.println( "a" );
//                            System.out.println( "f: " + lIndex + ", l: " + uIndex );
////                            System.out.println( array1.subList( uIndex, lIndex+1 ) );
//                            System.out.println( Lists.reverse( array1.subList( uIndex, lIndex+1 ) ) );
//                        }
//                        else
//                        {
//                            fItem = array1.get( lIndex );
//                            lItem = array1.get( uIndex );
//                            System.out.println( "b" );
//                            System.out.println( "f: " + lIndex + ", l: " + uIndex );
//                            System.out.println( array1.subList( lIndex, uIndex+1 ) );
////                            System.out.println( Lists.reverse( array1.subList( lIndex, uIndex+1 ) ) );
//                        }
//                    }
//                }
//            }
//        }
//        catch( Exception e )
//        {
//            System.out.println( e );
//        }
    }
}
