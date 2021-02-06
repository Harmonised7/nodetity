package harmonised.nodetity.events;

import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventHandler
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void worldTick( WorldTickEvent event )
	{
		WorldTickHandler.handleWorldTick( event );
	}

	@SubscribeEvent
	public static void blockPlaced( BlockEvent.EntityPlaceEvent event )
	{
		BlockPlaceHandler.handleBlockPlaced( event );
	}

	@SubscribeEvent
	public static void blockRemoved( BlockEvent.BreakEvent event )
	{
		BlockBreakHandler.handleBlocKBreak( event );
	}
}