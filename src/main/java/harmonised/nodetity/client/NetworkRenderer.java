package harmonised.nodetity.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import harmonised.nodetity.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NetworkRenderer
{
    @SubscribeEvent
    public void handleRender( RenderWorldLastEvent event )
    {
        Minecraft mc = Minecraft.getInstance();
        World world = mc.world;
        PlayerEntity player = mc.player;
        ResourceLocation resLoc = Util.getDimensionResLoc( world );
        Vector3d cameraCenter = Minecraft.getInstance().getRenderManager().info.getProjectedView();
        MatrixStack stack = event.getMatrixStack();
        stack.push();
        stack.translate( -cameraCenter.getX() + 0.5, -cameraCenter.getY() + 0.5, -cameraCenter.getZ() + 0.5 );
//        stack.rotate(Vector3f.YP.rotationDegrees(180.0F));
        Matrix4f matrix4f = stack.getLast().getMatrix();
        IRenderTypeBuffer buffer = mc.getRenderTypeBuffers().getBufferSource();
        IVertexBuilder builder = buffer.getBuffer( RenderType.getLines() );

        for( Map.Entry<Integer, NodeNetwork> entry : Data.nodeNetworks.entrySet() )
        {
            NodeNetwork nodeNetwork = entry.getValue();
            Map<BlockPos, Set<BlockPos>> lines = new HashMap<>();
            for( BlockPos thisNodePos : nodeNetwork.getNodes( resLoc ) )
            {
                Set<BlockPos> nearbyNodes = nodeNetwork.getNearbyNodePos( world, thisNodePos );
                for( BlockPos nextNodePos : nearbyNodes )
                {
                Set<BlockPos> posSet = lines.get( thisNodePos );
                if( posSet == null )
                {
                    lines.put( thisNodePos, new HashSet<>() );
                    posSet = lines.get( thisNodePos );
                }
                if( !( lines.containsKey( thisNodePos ) && lines.get( thisNodePos ).contains( nextNodePos ) ||
                       lines.containsKey( nextNodePos ) && lines.get( nextNodePos ).contains( thisNodePos ) ) )
                    posSet.add( nextNodePos );
                }
            }

            for( Map.Entry<BlockPos, Set<BlockPos>> line : lines.entrySet() )
            {
                for( BlockPos endPos : line.getValue() )
                {
                    stack.push();
                    builder.pos( matrix4f, line.getKey().getX(), line.getKey().getY(), line.getKey().getZ() ).color(255, 0, 255, 200).endVertex();
                    builder.pos( matrix4f, endPos.getX(), endPos.getY(), endPos.getZ() ).color(255, 0, 255, 200).endVertex();
                    stack.pop();
                }
            }
        }

        stack.pop();
    }
}
