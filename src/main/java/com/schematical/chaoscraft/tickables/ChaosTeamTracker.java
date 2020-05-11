package com.schematical.chaoscraft.tickables;

import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientOrgUpdatePacket;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;

public class ChaosTeamTracker extends BaseChaosEventListener {
    private static HashMap<String, ScorePlayerTeam> teams = new HashMap();
    private static Scoreboard scoreboard = null;
    private static final int ticksBetweenScoreCheck = 10;
    private static final int maxDistance = 40;
    private static final int maxLife = 45;
    private static final int lifeReward = 1;
    private int ticksSinceLastScoreCheck = 0;

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
        ticksSinceLastScoreCheck += 1;
        if(ticksSinceLastScoreCheck < ticksBetweenScoreCheck){
            return;
        }
        ticksSinceLastScoreCheck = 0;
        if(baseOrgManager.getOrganism().getTrainingRoomRoleNamespace().equals(Roles.hiders.toString())){
            if(baseOrgManager.getEntity().getTags().contains(VisibleState.SEEN.toString())){
                //Just remove tag
                baseOrgManager.getEntity().removeTag(VisibleState.SEEN.toString());
            }else{
                //REWARD
                int life = 0;
                //         (int) (orgEntity.world.getGameTime() + ((serverOrgManager.getMaxLife() - serverOrgManager.getAgeSeconds()) * 20)),
                //float lifeEndSeconds = (baseOrgManager.getExpectedLifeEndTime() - baseOrgManager.getEntity().world.getGameTime())/20;
                if(baseOrgManager.getLatestScore() < maxLife){
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
                                1,
                                life,
                                "HIDE_SUCCESS",
                                1,
                                baseOrgManager.getExpectedLifeEndTime() + life * 20,
                                0
                        )
                );

            }
            return;
        }
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
                    !orgEntity.getChaosTarget().isVisiblyBlocked(baseOrgManager.getEntity()) &&
                     orgEntity.getChaosTarget().isEntityLookingAt(baseOrgManager.getEntity())
                ) {
                    int life = 0;
                    //float lifeEndSeconds = (baseOrgManager.getExpectedLifeEndTime() - baseOrgManager.getEntity().world.getGameTime())/20;
                    if(baseOrgManager.getLatestScore() < maxLife){
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
                                    1,
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
    public enum VisibleState{
        SEEN
    }
    public enum Roles{
        seekers,
        hiders
    }

}
