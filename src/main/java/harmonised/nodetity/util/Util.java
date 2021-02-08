package harmonised.nodetity.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class Util
{
    public static ResourceLocation getDimensionResLoc( World world )
    {
        return world.func_241828_r().func_230520_a_().getKey( world.getDimensionType() );
    }

    public static double getDistance( BlockPos pos1, BlockPos pos2 )
    {
        return Math.sqrt( Math.pow( pos2.getX() - pos1.getX(), 2 ) + Math.pow( pos2.getY() - pos1.getY(), 2 ) + Math.pow( pos2.getZ() - pos1.getZ(), 2 ) );
    }

    public static BlockPos getDifference( BlockPos pos1, BlockPos pos2 )
    {
        return new BlockPos( pos2.getX() - pos1.getX(), pos2.getY() - pos1.getY(), pos2.getZ() - pos1.getZ() );
    }

    public static BlockPos getDifference( Vector3d pos1, BlockPos pos2 )
    {
        return new BlockPos( pos2.getX() - pos1.getX(), pos2.getY() - pos1.getY(), pos2.getZ() - pos1.getZ() );
    }

    public static BlockPos getDifference( BlockPos pos1, Vector3d pos2 )
    {
        return new BlockPos( pos2.getX() - pos1.getX(), pos2.getY() - pos1.getY(), pos2.getZ() - pos1.getZ() );
    }
}
