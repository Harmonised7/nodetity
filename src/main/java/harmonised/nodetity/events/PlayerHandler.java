package harmonised.nodetity.events;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.UUID;

public class PlayerHandler
{
    private static final UUID reachModifierID  = UUID.fromString("fdf3ba66-bdd9-4e06-b1b4-c8c581572626");

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
}
