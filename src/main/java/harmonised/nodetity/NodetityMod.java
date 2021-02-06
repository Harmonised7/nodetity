package harmonised.nodetity;

import harmonised.nodetity.client.ClientHandler;
import harmonised.nodetity.nodetity_saved_data.NodetitySavedData;
import harmonised.nodetity.registries.RegistryHandler;
import harmonised.nodetity.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod( Reference.MOD_ID )
public class NodetityMod
{
    private static String PROTOCOL_VERSION = "1";
    public static SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
            .named( new ResourceLocation( Reference.MOD_ID, "main_channel" ) )
            .clientAcceptedVersions( PROTOCOL_VERSION::equals )
            .serverAcceptedVersions( PROTOCOL_VERSION::equals )
            .networkProtocolVersion( () -> PROTOCOL_VERSION )
            .simpleChannel();

    public NodetityMod()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener( this::modsLoading );
        FMLJavaModLoadingContext.get().getModEventBus().addListener( this::clientLoading );
        MinecraftForge.EVENT_BUS.addListener( this::serverAboutToStart );
        MinecraftForge.EVENT_BUS.addListener( this::registerCommands );
        MinecraftForge.EVENT_BUS.addListener( this::serverStart );

        RegistryHandler.init();
//        DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> Requirements::init );

    }

    private void modsLoading( FMLCommonSetupEvent event )
    {
    }

    private void clientLoading( FMLClientSetupEvent event )
    {
        ClientHandler.init();
    }

    private void serverAboutToStart( FMLServerAboutToStartEvent event )
    {
    }

    private void registerCommands( RegisterCommandsEvent event )
    {
    }

    private void serverStart( FMLServerStartingEvent event )
    {
        NodetitySavedData.init( event.getServer() );
    }
}