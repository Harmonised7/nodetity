package harmonised.nodetity.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import harmonised.nodetity.data.Data;
import harmonised.nodetity.data.NodeNetwork;
import harmonised.nodetity.data.NodeState;
import harmonised.nodetity.events.PlayerHandler;
import harmonised.nodetity.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public class NetworkRenderer
{
    @SubscribeEvent
    public void handleRender( RenderWorldLastEvent event )
    {
        Minecraft mc = Minecraft.getInstance();
        World world = mc.world;
        PlayerEntity player = mc.player;
        ResourceLocation resLoc = Util.getDimensionResLoc( world );
        Vector3d cameraCenter = mc.getRenderManager().info.getProjectedView();
        MatrixStack stack = event.getMatrixStack();
        stack.push();
        stack.translate( -cameraCenter.getX() + 0.5, -cameraCenter.getY() + 0.5, -cameraCenter.getZ() + 0.5 );
//        stack.rotate(Vector3f.YP.rotationDegrees(180.0F));
        Matrix4f matrix4f = stack.getLast().getMatrix();
        IRenderTypeBuffer.Impl buffer = mc.getRenderTypeBuffers().getBufferSource();
        IVertexBuilder builder = buffer.getBuffer( RenderType.getLines() );

        for( Map.Entry<Integer, NodeNetwork> entry : Data.nodeNetworks.entrySet() )
        {
            NodeNetwork nodeNetwork = entry.getValue();
            Set<NodeState> nodeStates = nodeNetwork.getNodes( resLoc );
            Map<BlockPos, Set<BlockPos>> lines = getLinesFromNodeStates( nodeStates, true );
            //DEBUG
            Map<BlockPos, Set<BlockPos>> bestPathLines = null;
            if( PlayerHandler.firstState != null && PlayerHandler.lastState != null )
            {
                List<NodeState> shortestPath = PlayerHandler.firstState.getShortestPath( PlayerHandler.lastState );
                if( shortestPath != null )
                    bestPathLines = getLinesFromNodeStates( new HashSet<>( shortestPath ), false );
            }
            //END OF DEBUG

            drawLines( stack, matrix4f, builder, lines, 255, 0, 255, 200 );
            if( bestPathLines != null )
                drawLines( stack, matrix4f, builder, bestPathLines, 255, 255, 255, 255 );
        }

        stack.pop();
        RenderSystem.disableDepthTest();
        buffer.finish();
    }

    public static void drawLines( MatrixStack stack, Matrix4f matrix4f, IVertexBuilder builder, Map<BlockPos, Set<BlockPos>> lines )
    {
        drawLines( stack, matrix4f, builder, lines, 255, 0, 255, 200 );
    }

    public static void drawLines( MatrixStack stack, Matrix4f matrix4f, IVertexBuilder builder, Map<BlockPos, Set<BlockPos>> lines, int red, int green, int blue, int alpha )
    {
        for( Map.Entry<BlockPos, Set<BlockPos>> line : lines.entrySet() )
        {
            for( BlockPos endPos : line.getValue() )
            {
                stack.push();
                builder.pos( matrix4f, line.getKey().getX(), line.getKey().getY(), line.getKey().getZ() ).color(red, green, blue, alpha ).endVertex();
                builder.pos( matrix4f, endPos.getX(), endPos.getY(), endPos.getZ() ).color(red, green, blue, alpha ).endVertex();
                stack.pop();
            }
        }
    }

    public static void drawLinesGradient( MatrixStack stack, Matrix4f matrix4f, IVertexBuilder builder, Map<BlockPos, Set<BlockPos>> lines, float red, float green, float blue, int alpha )
    {
        int totalLines = 0, i = 0;
        for( Map.Entry<BlockPos, Set<BlockPos>> line : lines.entrySet() )
        {
            totalLines += line.getValue().size();
        }
        for( Map.Entry<BlockPos, Set<BlockPos>> line : lines.entrySet() )
        {
            for( BlockPos endPos : line.getValue() )
            {
                stack.push();
                builder.pos( matrix4f, line.getKey().getX(), line.getKey().getY(), line.getKey().getZ() ).color( (int)(red*255*(i/(float)totalLines)), (int)(green*255*(i/(float)totalLines)), (int)(blue*255*(i/(float)totalLines)), alpha ).endVertex();
                builder.pos( matrix4f, endPos.getX(), endPos.getY(), endPos.getZ() ).color( (int)(red*255*(i/(float)totalLines)), (int)(green*255*(i/(float)totalLines)), (int)(blue*255*(i/(float)totalLines)), alpha ).endVertex();
                stack.pop();
                i++;
            }
        }
    }

    public static Map<BlockPos, Set<BlockPos>> getLinesFromNodeStates( Set<NodeState> nodeStates, boolean drawNeighbors )
    {
        Map<BlockPos, Set<BlockPos>> lines = new HashMap<>();
        for( NodeState thisNodeState : nodeStates )
        {
            BlockPos thisNodePos = thisNodeState.getPos();
            Set<NodeState> nearbyNodes = thisNodeState.getNeighbors().keySet();
            try
            {
                for( NodeState nextNodeState : nearbyNodes )
                {
                    if( !drawNeighbors && !nodeStates.contains( nextNodeState ) )
                        continue;
                    BlockPos nextNodePos = nextNodeState.getPos();
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
            catch( Exception e )
            {
                System.out.println( "For some reason, this crashes..?" );
                e.printStackTrace();
            }
        }
        return lines;
    }
}
