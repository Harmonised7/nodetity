package harmonised.nodetity.util;
import net.minecraft.nbt.CompoundNBT;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NBTHelper
{
    public static <T> Map<String, T> MapStringKeyToString( Map<String, T> inMap )
    {
        Map<String, T> outMap = new HashMap<>();

        for( Map.Entry<String, T> entry : inMap.entrySet() )
        {
            outMap.put( entry.getKey().toString(), entry.getValue() );
        }

        return outMap;
    }

    public static Map<String, Double> nbtToMapString( CompoundNBT nbt )
    {
        Map<String, Double> map = new HashMap<>();

        for( String key : nbt.keySet() )
        {
            map.put( key, nbt.getDouble( key ) );
        }

        return map;
    }

    public static CompoundNBT mapStringToNbt( Map<String, Double> map )
    {
        if( map == null )
            return new CompoundNBT();

        CompoundNBT nbt = new CompoundNBT();

        for( Map.Entry<String, Double> entry : map.entrySet() )
        {
            nbt.putDouble( entry.getKey(), entry.getValue() );
        }

        return nbt;
    }

    public static CompoundNBT mapStringMapStringToNbt( Map<String, Map<String, Double>> map )
    {
        CompoundNBT nbt = new CompoundNBT();

        for( Map.Entry<String, Map<String, Double>> entry : map.entrySet() )
        {
            nbt.put( entry.getKey(), mapStringToNbt( entry.getValue() ) );
        }

        return nbt;
    }
}