package harmonised.nodetity.tile_entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import harmonised.nodetity.util.Util;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NodeTileEntityRenderer extends TileEntityRenderer<NodeTileEntity>
{
    public NodeTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    @Override
    public boolean isGlobalRenderer( NodeTileEntity te )
    {
        return true;
    }

    @Override
    public void render(NodeTileEntity tileEntityIn, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn)
    {
//        World world = tileEntityIn.getWorld();
//        ResourceLocation resLoc = Util.getDimensionResLoc( world );
//        BlockPos originPos = tileEntityIn.getPos();
//        NodeNetwork nodeNetwork = Data.getNodeNetwork( tileEntityIn.getNetworkId() );
//        if( nodeNetwork == null )
//        {
//            System.out.println( "ERROR, NULL NODENETWORK" );
//            return;
//        }
////        stack.scale(0.5F, 0.5F, 0.5F);
//        stack.push();
//        stack.translate( 0.5, 0.5, 0.5 );
////        stack.rotate(Vector3f.YP.rotationDegrees(180.0F));
//        IVertexBuilder builder = buffer.getBuffer( RenderType.getLines() );
//        Matrix4f matrix4f = stack.getLast().getMatrix();
//        Map<BlockPos, Set<BlockPos>> lines = new HashMap<>();
//
//        for( BlockPos thisNodePos : nodeNetwork.getNodes( resLoc ) )
//        {
//            BlockPos relThisNodePos = Util.getDifference( originPos, thisNodePos );
//            Set<BlockPos> nearbyNodes = nodeNetwork.getNearbyNodePos( world, thisNodePos );
//            for( BlockPos nextNodePos : nearbyNodes )
//            {
//                BlockPos relNextNodePos = Util.getDifference( originPos, nextNodePos );
//                {
//                    Set<BlockPos> posSet = lines.get( relThisNodePos );
//                    if( posSet == null )
//                    {
//                        lines.put( relThisNodePos, new HashSet<>() );
//                        posSet = lines.get( relThisNodePos );
//                    }
//                    if( !( lines.containsKey( relThisNodePos ) && lines.get( relThisNodePos ).contains( relNextNodePos ) ||
//                           lines.containsKey( relNextNodePos ) && lines.get( relNextNodePos ).contains( relThisNodePos ) ) )
//                    posSet.add( relNextNodePos );
//                }
//            }
//        }
//
//        for( Map.Entry<BlockPos, Set<BlockPos>> line : lines.entrySet() )
//        {
//            for( BlockPos endPos : line.getValue() )
//            {
//                stack.push();
//                builder.pos( matrix4f, line.getKey().getX(), line.getKey().getY(), line.getKey().getZ() ).color(255, 0, 255, 200).endVertex();
//                builder.pos( matrix4f, endPos.getX(), endPos.getY(), endPos.getZ() ).color(255, 0, 255, 200).endVertex();
//                stack.pop();
//            }
//        }
//
//        stack.pop();
    }
}
