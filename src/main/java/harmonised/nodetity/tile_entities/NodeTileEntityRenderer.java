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
    public void render(NodeTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        World world = tileEntityIn.getWorld();
        BlockPos originPos = tileEntityIn.getPos();
        NodeNetwork nodeNetwork = Data.findNearbyNodeNetwork( world, originPos );
        if( nodeNetwork == null )
        {
            System.out.println( "ERROR, NULL NODENETWORK" );
            return;
        }
//        matrixStackIn.scale(0.5F, 0.5F, 0.5F);
        matrixStackIn.push();
        matrixStackIn.translate( 0.5, 0.5, 0.5 );
//        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));
        IVertexBuilder builder = bufferIn.getBuffer( RenderType.getLines() );
        Matrix4f matrix4f1 = matrixStackIn.getLast().getMatrix();
        Map<BlockPos, Set<BlockPos>> lines = new HashMap<>();

        for( BlockPos thisNodePos : nodeNetwork.nodes )
        {
            BlockPos relThisNodePos = Util.getDifference( originPos, thisNodePos );
            Set<BlockPos> nearbyNodes = Data.getNearbyNodePos( world, tileEntityIn.getNetworkId(), thisNodePos );
            for( BlockPos nextNodePos : nearbyNodes )
            {
                BlockPos relNextNodePos = Util.getDifference( originPos, nextNodePos );
                {
                    Set<BlockPos> posSet = lines.get( relThisNodePos );
                    if( posSet == null )
                    {
                        lines.put( relThisNodePos, new HashSet<>() );
                        posSet = lines.get( relThisNodePos );
                    }
                    if( !( lines.containsKey( relThisNodePos ) && lines.get( relThisNodePos ).contains( relNextNodePos ) ||
                           lines.containsKey( relNextNodePos ) && lines.get( relNextNodePos ).contains( relThisNodePos ) ) )
                    posSet.add( relNextNodePos );
                }
            }
        }


        for( Map.Entry<BlockPos, Set<BlockPos>> line : lines.entrySet() )
        {
            for( BlockPos endPos : line.getValue() )
            {
                matrixStackIn.push();
                builder.pos( matrix4f1, line.getKey().getX(), line.getKey().getY(), line.getKey().getZ() ).color(255, 0, 255, 200).endVertex();
                builder.pos( matrix4f1, endPos.getX(), endPos.getY(), endPos.getZ() ).color(255, 0, 255, 200).endVertex();
                matrixStackIn.pop();
            }
        }

        matrixStackIn.pop();
    }
}
