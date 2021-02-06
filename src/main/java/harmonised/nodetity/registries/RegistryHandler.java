package harmonised.nodetity.registries;

import harmonised.nodetity.tile_entities.NodeTileEntity;
import harmonised.nodetity.tile_entities.NodeTileEntityRenderer;
import harmonised.nodetity.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create( ForgeRegistries.BLOCKS, Reference.MOD_ID );
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create( ForgeRegistries.TILE_ENTITIES, Reference.MOD_ID );
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, Reference.MOD_ID );

    public static void init()
    {
        BLOCKS.register( FMLJavaModLoadingContext.get().getModEventBus() );
        TILE_ENTITIES.register( FMLJavaModLoadingContext.get().getModEventBus() );
        ITEMS.register( FMLJavaModLoadingContext.get().getModEventBus() );

        ModBlocks.register();
        ModItems.register();
        ModTEs.register();
    }
}