package harmonised.nodetity.data;

import net.minecraft.util.math.BlockPos;

import java.util.HashSet;

public class NodeNetwork
{
    public int id;
    public HashSet<BlockPos> nodes;

    public NodeNetwork( int id, HashSet<BlockPos> nodes )
    {
        this.id = id;
        this.nodes = nodes;
    }
}
