package com.example.addon.events;

import com.example.addon.WayPointRenderManager;
import com.example.addon.render.RenderWayPoint;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.client.renderer.RenderWorldLastEvent;
import net.labymod.main.LabyMod;
import net.labymod.utils.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;

import java.util.List;

public class WorldRender {

    WayPointRenderManager wayPointRenderManager = new WayPointRenderManager();

    @Subscribe
    public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        if (Keyboard.isNewKeyDown(77)) {
            wayPointRenderManager.addWayPoint(Minecraft.getInstance().player.getPosX(), Minecraft.getInstance().player.getPosY(), Minecraft.getInstance().player.getPosZ());
        }

        List<List<Double>> waypoints = wayPointRenderManager.getWaypoints();;
        for (List<Double> waypoint : waypoints) {
            double x = waypoint.get(0);
            double y = waypoint.get(1);
            double z = waypoint.get(2);

            MatrixStack matrixStack = event.getMatrixStack();

            IRenderTypeBuffer.Impl bufferSource = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();

            RenderWayPoint.renderNameWayPoint(matrixStack, bufferSource, (int) x, (int)y, (int)z);
            RenderWayPoint.renderBeam(matrixStack, bufferSource, event.getPartialTicks(), (int)x, (int)z);
        }

    }

}
