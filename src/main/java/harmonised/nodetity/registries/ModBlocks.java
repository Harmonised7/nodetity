package harmonised.nodetity.registries;

import harmonised.nodetity.blocks.NodeBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks
{
    public static final RegistryObject<Block> NODE_BLOCK = register("node_block", () -> new NodeBlock( AbstractBlock.Properties.create( Material.BUBBLE_COLUMN ) ) );

    public static void register() {}

    private static <T extends Block> RegistryObject<T> registerNoItem( String name, Supplier<T> block )
    {
        return RegistryHandler.BLOCKS.register( name, block );
    }

    private static <T extends Block> RegistryObject<T> register( String name, Supplier<T> block )
    {
        RegistryObject<T> ret = registerNoItem(name, block);
        RegistryHandler.ITEMS.register(name, () -> new BlockItem( ret.get(), new Item.Properties().group( ItemGroup.REDSTONE ) ) );
        return ret;
    }
}
