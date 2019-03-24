package com.schematical.chaoscraft;

import com.google.common.base.Predicate;
import com.schematical.chaoscraft.ai.inputs.FocusAreaInput;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaoscraft.gui.CCKeyBinding;
import com.schematical.chaoscraft.gui.CCOrgListView;
import com.schematical.chaoscraft.proxies.CCIMessageHandeler;
import com.schematical.chaoscraft.proxies.ClientProxy;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user1a on 1/6/19.
 */
@Mod.EventBusSubscriber
public class CCEventListener {
    public EntityOrganism org;

    @SubscribeEvent
    public static void onWorldTickEvent(TickEvent.WorldTickEvent worldTickEvent){
        ChaosCraft.ticksSinceLastSpawn += 1;

        if(ChaosCraft.rick != null && ChaosCraft.rick.isDead){
            ChaosCraft.rick = null;
            ChaosCraft.spawnRick(worldTickEvent.world, ChaosCraft.rickPos);
        }
        Iterator<EntityOrganism> iterator = ChaosCraft.organisims.iterator();

        while(iterator.hasNext()){
            EntityOrganism organism = iterator.next();
            if(
                organism.getOrganism() == null ||
                organism.getSpawnHash() != ChaosCraft.spawnHash
            ){
                organism.setDead();
                iterator.remove();
                //ChaosCraft.logger.info("Setting Dead: " + organism.getName() + " - Has no `Organism` record");
            }
        }


        if(
            ChaosCraft.orgsToSpawn != null &&
            ChaosCraft.orgsToSpawn.size() > 0
        ){
            ChaosCraft.spawnOrgs(ChaosCraft.orgsToSpawn);
            ChaosCraft.orgsToSpawn.clear();
        }
        if(ChaosCraft.consecutiveErrorCount > 5){
            throw new ChaosNetException("ChaosCraft.consecutiveErrorCount > 5");
        }
        
        if(ChaosCraft.organisims.size() > 0) {
            for (EntityPlayerMP observingPlayer : ChaosCraft.observingPlayers) {
                Entity entity = observingPlayer.getSpectatingEntity();

                if (
                    entity.equals(observingPlayer) ||
                    entity == null ||
                    entity.isDead
                ) {
                    if(
                        entity != null &&
                        entity instanceof EntityOrganism
                    ){
                        ((EntityOrganism) entity).setObserving(null);
                    }
                    int index = (int) Math.floor(ChaosCraft.organisims.size() * Math.random());
                    EntityOrganism orgToObserve = ChaosCraft.organisims.get(index);
                    orgToObserve.setObserving(observingPlayer);
                    observingPlayer.setSpectatingEntity(orgToObserve);
                }
            }
        }


    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void render(RenderGameOverlayEvent.Post event){
        if(ChaosCraft.fontRenderer == null){
            ChaosCraft.fontRenderer = Minecraft.getMinecraft().fontRenderer;
        }
        if(ChaosCraft.topLeftMessage != null) {
            String[] parts = ChaosCraft.topLeftMessage.split("\n");
            int placement = 5;
            for (String part : parts) {
                ChaosCraft.fontRenderer.drawStringWithShadow(part, 5, placement, 0xFFFFFF);
                placement += 10;
            }
        }
        //if(ChaosCraft.observingPlayers != null) {

        //}
    }

    @SubscribeEvent
    public static void debugRenderer(RenderWorldLastEvent event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        EntityOrganism org;
        CCIMessageHandeler handle = new CCIMessageHandeler();
        double xo = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) event.getPartialTicks();
        double yo = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) event.getPartialTicks();
        double zo = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) event.getPartialTicks();
        Vec3d vector = new Vec3d(1, 1, 1);

        for(int i = 0; i<ChaosCraft.organisims.size(); i++){
            org = ChaosCraft.organisims.get(i);
            Vec3d start = new Vec3d(handle.vec3d.x, handle.vec3d.y, handle.vec3d.z);

            Vec3d offset = new Vec3d(xo, yo, zo);


            renderPathLine(start, start.add(vector), offset, new Color(227, 93, 218));
        }

    }

    static void renderPathLine(Vec3d start, Vec3d end, Vec3d offset, Color color) {
        renderPathLine(start.x, start.y, start.z, end.x, end.y, end.z, offset.x, offset.y, offset.z, color.getRed(), color.getGreen(), color.getBlue());
    }

    public static void renderPathLine(double x1, double y1, double z1, double x2, double y2, double z2, double xo, double yo, double zo, int red, int green, int blue) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        GlStateManager.pushMatrix();
        GlStateManager.color(0.0F, 1.0F, 0.0F, 1f);
        GlStateManager.disableLighting();
        GlStateManager.disableTexture2D();
        GlStateManager.glLineWidth(5.0F);

        bufferbuilder.pos((double) x1 - xo,
                (double) y1 - yo,
                (double) z1 - zo)
                .color(red, green, blue, 255).endVertex();

        bufferbuilder.pos((double) x2 - xo,
                (double) y2 - yo,
                (double) z2 - zo)
                .color(red, green, blue, 255).endVertex();

        tessellator.draw();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled=true)
    public  static void onEvent(InputEvent.KeyInputEvent event) {


        for (KeyBinding keyBinding : ClientProxy.keyBindings) {
            // check each enumerated key binding type for pressed and take appropriate action
            if (keyBinding.isPressed()) {
                // DEBUG
                switch(keyBinding.getKeyDescription()){
                    case(CCKeyBinding.SHOW_ORG_LIST):
                        CCOrgListView view = new CCOrgListView();

                        Minecraft.getMinecraft().displayGuiScreen(view);
                    break;
                    case(CCKeyBinding.OBSERVER_MODE):
                        List<EntityPlayerMP> players = Minecraft.getMinecraft().world.<EntityPlayerMP>getPlayers(EntityPlayerMP.class, new Predicate<EntityPlayerMP>() {
                            @Override
                            public boolean apply(@Nullable EntityPlayerMP input) {
                                return true;
                            }
                        });
                        for(EntityPlayerMP player: players){
                            ChaosCraft.toggleObservingPlayer(player);
                        }
                    break;
                }

                // do stuff for this key binding here
                // remember you may need to send packet to server


            }
        }


    }
    @SubscribeEvent
    public static void spawnEvent(EntityJoinWorldEvent event) {
        /*
        if (event.getEntity() instanceof EntityMob) {
            EntityMob mob = (EntityMob) event.getEntity();

            if (!(mob instanceof EntityPigZombie)) {
                if (mob instanceof EntityEnderman) {

                } else {
                    mob.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(mob, EntityOrganism.class, true));
                }
            }
        }
        */
    }



}
