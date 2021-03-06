package harmonised.nodetity.registries;

import harmonised.nodetity.tile_entities.NodeTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class ModTEs
{
    public static final RegistryObject<TileEntityType<NodeTileEntity>> MASTER_NODE = RegistryHandler.TILE_ENTITIES.register( "master_node", () -> TileEntityType.Builder.create( NodeTileEntity::new, ModBlocks.MASTER_NODE_BLOCK.get() ).build( null ) );

    public static void register() {}
}
