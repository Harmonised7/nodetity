package harmonised.nodetity.events;

import harmonised.nodetity.data.RouteTask;
import harmonised.nodetity.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.TickEvent.WorldTickEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorldTickHandler
{
    public static int tickCounter = 0;
    public static final List<RouteTask> routeTasks = new ArrayList<>();

    public static void handleWorldTick( WorldTickEvent event )
    {
        tickCounter++;
        if( tickCounter % 100 == 0 )
        {
            int i = 0;
            while( routeTasks.size() > i )
            {
                if( routeTasks.get( i ).process( 1 ) )
                    routeTasks.remove( i );
                else
                    i++;
            }
        }
    }

    public static void addRouteTask( RouteTask routeTask )
    {
        routeTasks.add( routeTask );
    }
}
