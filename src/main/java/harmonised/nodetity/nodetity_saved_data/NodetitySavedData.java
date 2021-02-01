package harmonised.nodetity.nodetity_saved_data;

import harmonised.nodetity.util.Reference;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NodetitySavedData extends WorldSavedData
{
    public static final Logger LOGGER = LogManager.getLogger();

    private static NodetitySavedData nodetitySavedData;
    private static MinecraftServer server;
    private static String NAME = Reference.MOD_ID;
    public NodetitySavedData()
    {
        super( NAME );
    }

    @Override
    public void read( CompoundNBT inData )
    {
        if( inData.contains( "nodes" ) )
        {

        }
    }

    @Override
    public CompoundNBT write( CompoundNBT outData )
    {
        return outData;
    }

    public static void init( MinecraftServer server )
    {
        NodetitySavedData.server = server;
        NodetitySavedData.nodetitySavedData = server.getWorld( World.OVERWORLD ).getSavedData().getOrCreate( NodetitySavedData::new, NAME );
    }

    public static NodetitySavedData get()   //Only available on Server Side, after the Server has Started.
    {
        return NodetitySavedData.nodetitySavedData;
    }

    public static MinecraftServer getServer()
    {
        return NodetitySavedData.server;
    }
}