package harmonised.nodetity.client;

import harmonised.nodetity.registries.ModTEs;
import harmonised.nodetity.tile_entities.NodeTileEntityRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientHandler
{
    public static void init()
    {
        ClientRegistry.bindTileEntityRenderer( ModTEs.MASTER_NODE.get(), NodeTileEntityRenderer::new );
        MinecraftForge.EVENT_BUS.register( new NetworkRenderer() );
    }
}
