package com.schematical.chaoscraft.tickables;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.client.gui.CCGUIHelper;
import com.schematical.chaoscraft.client.iRenderWorldLastEvent;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientOrgUpdatePacket;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChaosTeamTracker extends BaseChaosEventListener implements iRenderWorldLastEvent {
    private static HashMap<String, ScorePlayerTeam> teams = new HashMap();
    private static Scoreboard scoreboard = null;
    private static final int ticksBetweenScoreCheck = 10;
    private static final int maxDistance = 20;
    private static final int maxDebugRenderDistance = 5;
    private static final int maxLife = 45;
    private static final int lifeReward = 1;
    private int ticksSinceLastScoreCheck = 0;
    private boolean hasAttachedToClient = false;
    private ClientOrgManager clientOrgManager;
    private ArrayList<OrgEntity> seenOrgs = new ArrayList<>();
    private final float maxYawDelta = 45;

    public void onServerSpawn(ServerOrgManager serverOrgManager) {
        if(scoreboard == null) {
            World world = serverOrgManager.getEntity().world;
            scoreboard = world.getScoreboard();
        }
        if(!teams.containsKey(serverOrgManager.getOrganism().getTrainingRoomRoleNamespace())){
            ScorePlayerTeam newScorePlayerTeam = scoreboard.getTeam(serverOrgManager.getOrganism().getTrainingRoomRoleNamespace());
            if(newScorePlayerTeam == null) {
                newScorePlayerTeam = scoreboard.createTeam(serverOrgManager.getOrganism().getTrainingRoomRoleNamespace());//TODO: Change to training room role setting
            }
            teams.put(serverOrgManager.getOrganism().getTrainingRoomRoleNamespace(), newScorePlayerTeam);
        }
        ScorePlayerTeam scorePlayerTeam = teams.get(serverOrgManager.getOrganism().getTrainingRoomRoleNamespace());


        scoreboard.addPlayerToTeam(serverOrgManager.getEntity().getCachedUniqueIdString(), scorePlayerTeam);
        //isOnSameTeam
    }
    //TODO: Move to seperate class and extend team tracker
    public void onClientTick(ClientOrgManager baseOrgManager){
        if(clientOrgManager == null){
            clientOrgManager = baseOrgManager;
            ChaosCraft.getClient().addRenderListener(this);
        }

        ticksSinceLastScoreCheck += 1;
        if(ticksSinceLastScoreCheck < ticksBetweenScoreCheck){
            return;
        }
        ticksSinceLastScoreCheck = 0;
        if(baseOrgManager.getOrganism().getTrainingRoomRoleNamespace().equals(Roles.hiders.toString())){
            int life = 0;
            int score = 0;
            if(baseOrgManager.getEntity().getTags().contains(VisibleState.SEEN.toString())){
                //Just remove tag
                score = -1;
                baseOrgManager.getEntity().removeTag(VisibleState.SEEN.toString());
            }else {
                score = 1;
                if (baseOrgManager.getLatestScore() < maxLife) {
                    life = lifeReward;
                    ChaosNetworkManager.sendToServer(
                            new CCClientOrgUpdatePacket(
                                    baseOrgManager.getCCNamespace(),
                                    CCClientOrgUpdatePacket.Action.UpdateLifeEnd,
                                    life
                            )
                    );
                }
            }

            baseOrgManager.addServerScoreEvent(
                    new CCServerScoreEventPacket(
                            baseOrgManager.getCCNamespace(),
                            score,
                            life,
                            "HIDE_SUCCESS",
                            1,
                            baseOrgManager.getExpectedLifeEndTime() + life * 20,
                            0
                    )
            );


            return;
        }
        seenOrgs.clear();
        AxisAlignedBB grownBox = baseOrgManager.getEntity().getBoundingBox().grow(maxDistance, maxDistance, maxDistance);
        List<OrgEntity> entities =  baseOrgManager.getEntity().world.getEntitiesWithinAABB(OrgEntity.class,  grownBox);

        for (OrgEntity orgEntity : entities) {
            if(
                    Roles.hiders.toString().equals(
                        orgEntity.getTrainingRoomRoleNamespace()
                    )

            ){
                //This org should get points


                if(
                    !orgEntity.getChaosTarget().isVisiblyBlocked(baseOrgManager.getEntity())
                ) {
                    Double yawDelta = orgEntity.getChaosTarget().getYawDelta(clientOrgManager.getEntity());
                    if(Math.abs(yawDelta) < maxYawDelta) {
                        seenOrgs.add(orgEntity);
                        int life = 0;
                        if (baseOrgManager.getLatestScore() < maxLife) {
                            life = lifeReward;
                            ChaosNetworkManager.sendToServer(
                                    new CCClientOrgUpdatePacket(
                                            baseOrgManager.getCCNamespace(),
                                            CCClientOrgUpdatePacket.Action.UpdateLifeEnd,
                                            life
                                    )
                            );
                        }
                        baseOrgManager.addServerScoreEvent(
                                new CCServerScoreEventPacket(
                                        baseOrgManager.getCCNamespace(),
                                        (int)Math.round(maxDistance - orgEntity.getChaosTarget().getDist(clientOrgManager.getEntity())),
                                        life,
                                        "SEEK_SUCCESS",
                                        1,
                                        baseOrgManager.getExpectedLifeEndTime() + life * 20,
                                        0
                                )
                        );
                        ChaosNetworkManager.sendToServer(
                                new CCClientOrgUpdatePacket(
                                        baseOrgManager.getCCNamespace(),
                                        CCClientOrgUpdatePacket.Action.UpdateLifeEnd,
                                        life
                                )
                        );


                        //Penalize the hider
                        orgEntity.addTag(VisibleState.SEEN.toString());
                    }
                }
            }
        }

    }

    @Override
    public boolean onRenderWorldLastEvent(RenderWorldLastEvent event) {
        if(clientOrgManager == null){
            return true;
        }
        if(!clientOrgManager.getEntity().isAlive()){
            return false;
        }

        if(clientOrgManager.getOrganism().getTrainingRoomRoleNamespace().equals("seekers")) {

            Vec3d vec3d = clientOrgManager.getEntity().getEyePosition(1);
            Vec3d vec3d1 = clientOrgManager.getEntity().getLook(1);
            //vec3d1 = vec3d1.rotatePitch(eye.pitch);
            Vec3d maxVec3d = vec3d1.rotateYaw(maxYawDelta);
            Vec3d vec3d2 = vec3d.add(
                    new Vec3d(
                            maxVec3d.x * maxDebugRenderDistance,
                            maxVec3d.y * maxDebugRenderDistance,
                            maxVec3d.z * maxDebugRenderDistance
                    )
            );

            CCGUIHelper.drawLine2d(
                    event.getMatrixStack(),
                    vec3d,
                    vec3d2,
                    Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView(),
                    Color.GREEN,
                    .1f
            );

            Vec3d minVec3d = vec3d1.rotateYaw(-1 * maxYawDelta);
            Vec3d minVec3d2 = vec3d.add(
                    new Vec3d(
                            minVec3d.x * maxDebugRenderDistance,
                            minVec3d.y * maxDebugRenderDistance,
                            minVec3d.z * maxDebugRenderDistance
                    )
            );
            CCGUIHelper.drawLine2d(
                    event.getMatrixStack(),
                    vec3d,
                    minVec3d2,
                    Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView(),
                    Color.GREEN,
                    .1f
            );
            for (OrgEntity orgEntity : seenOrgs) {
                Vec3d toVec3d = orgEntity.getEyePosition(1);
                CCGUIHelper.drawLine2d(
                        event.getMatrixStack(),
                        vec3d,
                        toVec3d,
                        Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView(),
                        Color.RED,
                        .1f
                );

            }
        }
        return true;

    }

    public enum VisibleState{
        SEEN
    }
    public enum Roles{
        seekers,
        hiders
    }

}
