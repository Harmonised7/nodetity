package harmonised.nodetity.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Util
{
    public static ResourceLocation getDimensionResLoc( World world )
    {
        return world.func_241828_r().func_230520_a_().getKey( world.getDimensionType() );
    }

    public static double getDistance( BlockPos pos1, BlockPos pos2 )
    {
        return Math.sqrt( Math.pow( pos1.getX() - pos2.getX(), 2 ) + Math.pow( pos1.getY() - pos2.getY(), 2 ) + Math.pow( pos1.getZ() - pos2.getZ(), 2 ) );
    }
}
