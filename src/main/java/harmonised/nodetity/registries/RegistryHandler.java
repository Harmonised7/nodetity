package harmonised.nodetity.registries;

import harmonised.nodetity.tile_entities.NodeTileEntity;
import harmonised.nodetity.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler
{
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create( ForgeRegistries.BLOCKS, Reference.MOD_ID );
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create( ForgeRegistries.TILE_ENTITIES, Reference.MOD_ID );

    public static final RegistryObject<Block> NODE_BLOCK = BLOCKS.register("node_block", () -> new Block( Block.Properties.create( Material.BUBBLE_COLUMN ) ) );
    public static final RegistryObject<TileEntityType<NodeTileEntity>> NODE = TILE_ENTITIES.register( "node", () -> TileEntityType.Builder.create( NodeTileEntity::new, NODE_BLOCK.get() ).build( null ) );

    public static void init()
    {
        BLOCKS.register( FMLJavaModLoadingContext.get().getModEventBus() );
        TILE_ENTITIES.register( FMLJavaModLoadingContext.get().getModEventBus() );
    }
}