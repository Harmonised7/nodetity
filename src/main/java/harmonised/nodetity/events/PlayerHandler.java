package harmonised.nodetity.events;

import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import harmonised.nodetity.data.NodeState;
import harmonised.nodetity.util.Util;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.Set;
import java.util.UUID;

public class PlayerHandler
{
    private static final UUID reachModifierID  = UUID.fromString("fdf3ba66-bdd9-4e06-b1b4-c8c581572626");
    public static NodeState firstState, lastState;

    public static void handlePlayerLoggedIn( PlayerEvent.PlayerLoggedInEvent event )
    {
        PlayerEntity player = event.getPlayer();
        World world = player.getEntityWorld();
        if( !world.isRemote() )
        {
            double reach = 50;
            ModifiableAttributeInstance reachAttribute = player.getAttribute( ForgeMod.REACH_DISTANCE.get() );
            if( reachAttribute.getModifier( reachModifierID ) == null || reachAttribute.getModifier( reachModifierID ).getAmount() != reach )
            {
                AttributeModifier reachModifier = new AttributeModifier( reachModifierID, "Reach bonus thanks to Build Level", reach, AttributeModifier.Operation.ADDITION );
                reachAttribute.removeModifier( reachModifierID );
                reachAttribute.applyPersistentModifier( reachModifier );
            }
        }
    }

    public static void handlePlayerInteraction( PlayerInteractEvent event )
    {
        if( event instanceof PlayerInteractEvent.RightClickBlock )
        {
            PlayerEntity player = event.getPlayer();
            World world = player.getEntityWorld();
            ResourceLocation dimResLoc = Util.getDimensionResLoc( world );
            BlockPos pos = event.getPos();
            NodeNetwork network = Data.getNodeNetwork( 1 );
            if( network == null )
                return;
            Item mainItem = player.getHeldItemMainhand().getItem();
            Item offItem = player.getHeldItemOffhand().getItem();
            if( event.getHand().equals( Hand.MAIN_HAND ) )
            {
                if( mainItem.equals( Items.ARROW ) )
                {
                    firstState = network.getNode( dimResLoc, pos );
                    System.out.println( "First NodeState set" );
                }
                else if( mainItem.equals( Items.NETHER_STAR ) && !world.isRemote() )
                {
                    Set<NodeState> nodes = network.getNodes( dimResLoc );
                    for( NodeState node : nodes )
                    {
                        if( node.getShortestPaths().size() > 0 )
                        {
                            System.out.println( "Shortest Paths Found" );
                        }
                    }
                }
            }
            else if( event.getHand().equals( Hand.OFF_HAND ) && offItem.equals( Items.ARROW ) )
            {
                lastState = network.getNode( dimResLoc, pos );
                System.out.println( "Last NodeState set" );
            }
        }
    }
}