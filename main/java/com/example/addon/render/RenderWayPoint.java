package com.example.addon.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.BeaconTileEntityRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class RenderWayPoint {

    public static void renderBeam(MatrixStack matrix, IRenderTypeBuffer.Impl buffer, float partialTicks, int posX, int posZ) {
        matrix.push();
        // matrixStack.rotate(Vector3f.YP.rotationDegrees(-Minecraft.getInstance().player.getYaw(event.getPartialTicks()) + 90F));
        // matrixStack.rotate(Vector3f.ZP.rotationDegrees(Minecraft.getInstance().player.getPitch(event.getPartialTicks())));

        matrix.translate(-Minecraft.getInstance().player.getPosX() + posX, -Minecraft.getInstance().player.getPosY(), -Minecraft.getInstance().player.getPosZ() + posZ);
        // matrixStack.rotate(Vector3f.ZP.rotationDegrees(90F));
        // matrixStack.rotate(Vector3f.ZP.rotationDegrees(-1.3F));
        RenderHelper.disableStandardItemLighting();

        BeaconTileEntityRenderer.renderBeamSegment(matrix, buffer, BeaconTileEntityRenderer.TEXTURE_BEACON_BEAM, partialTicks, 1.0F, Minecraft.getInstance().world.getGameTime(), 0, 256, new float[]{1, 240, 1}, 0.2F, 0.25F);

        buffer.finish();
        matrix.pop();
    }

    public static void renderNameWayPoint(MatrixStack matrix, IRenderTypeBuffer.Impl buffer, int posX, int posZ, int posY) {
        FontRenderer font = Minecraft.getInstance().fontRenderer;

        double distance = calculateDistance(posX, posZ, Minecraft.getInstance().player.getPosX(), Minecraft.getInstance().player.getPosZ());
        ITextComponent text = new TranslationTextComponent("" + distance);

        float scale = 0.02f; // размер текста
        int opacity = (int) (.4f * 255.0f) << 24;
        float offset = (float) (-font.getStringPropertyWidth(text) / 2);
        scale = (float) (0.02f + (distance / 200)); // Примерный расчет нового значения scale

        matrix.push();
        Matrix4f matrix4 = matrix.getLast().getMatrix();

        // IRenderTypeBuffer.Impl bufferSource = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();

        matrix.translate(-Minecraft.getInstance().player.getPosX() + posX + 0.500, -Minecraft.getInstance().player.getPosY() + posY , -Minecraft.getInstance().player.getPosZ()  + posZ + 0.500);
        matrix.scale(scale, scale, scale);
        matrix.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
        matrix.rotate(Vector3f.ZP.rotationDegrees(180f));
        font.func_243247_a(text, offset, 0, 0xffffff, false, matrix4, buffer, false, opacity,  getLightLevel(Minecraft.getInstance().world, Minecraft.getInstance().player.getPosition()));

        matrix.pop();
    }
    public static int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightFor(LightType.BLOCK, pos);
        int sLight = world.getLightFor(LightType.SKY, pos);
        return LightTexture.packLight(bLight, sLight);
    }

    public static double calculateDistance(double x1, double z1, double x2, double z2) {
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2));
        return ((int) distance);
    }
}
