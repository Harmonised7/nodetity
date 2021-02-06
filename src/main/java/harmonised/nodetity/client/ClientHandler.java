package harmonised.nodetity.client;

import harmonised.nodetity.registries.ModTEs;
import harmonised.nodetity.tile_entities.NodeTileEntityRenderer;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientHandler
{
    public static void init()
    {
        ClientRegistry.bindTileEntityRenderer( ModTEs.MASTER_NODE.get(), NodeTileEntityRenderer::new );
    }
}
