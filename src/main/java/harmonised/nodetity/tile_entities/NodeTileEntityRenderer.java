package harmonised.nodetity.tile_entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import harmonised.nodetity.data.Data;
import harmonised.nodetity.util.Util;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

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
//        matrixStackIn.scale(0.5F, 0.5F, 0.5F);
        matrixStackIn.push();
        matrixStackIn.translate( 0.5, 0.5, 0.5 );
//        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));
        IVertexBuilder builder = bufferIn.getBuffer( RenderType.getLines() );
        BlockPos originPos = tileEntityIn.getPos();
        Set<BlockPos> originNearbyNodes = Data.getNearbyNodePos( tileEntityIn.getNetworkId(), originPos );

        for( BlockPos thisNodePos : originNearbyNodes )
        {
            BlockPos relThisNodePos = Util.getDifference( originPos, thisNodePos );
            Set<BlockPos> nearbyNodes = Data.getNearbyNodePos( tileEntityIn.getNetworkId(), thisNodePos );
            for( BlockPos nextNodePos : nearbyNodes )
            {
                BlockPos relNextNodePos = Util.getDifference( thisNodePos, nextNodePos );
                matrixStackIn.push();
                matrixStackIn.translate( relThisNodePos.getX(), relThisNodePos.getY(), relThisNodePos.getZ() );
                Matrix4f matrix4f1 = matrixStackIn.getLast().getMatrix();
                builder.pos( matrix4f1, 0, 0, 0 ).color(0, 0, 255, 255).endVertex();
                builder.pos( matrix4f1, relNextNodePos.getX(), relNextNodePos.getY(), relNextNodePos.getZ() ).color(0, 0, 255, 255).endVertex();
                matrixStackIn.pop();
            }
        }

        matrixStackIn.pop();
    }
}
