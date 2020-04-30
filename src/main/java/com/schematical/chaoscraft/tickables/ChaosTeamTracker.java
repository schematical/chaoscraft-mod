package com.schematical.chaoscraft.tickables;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.world.World;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ChaosTeamTracker extends BaseChaosEventListener {
    private static HashMap<String, ScorePlayerTeam> teams = new HashMap();
    private static Scoreboard scoreboard = null;
    public void onServerSpawn(ServerOrgManager serverOrgManager) {
        if(scoreboard == null) {
            World world = serverOrgManager.getEntity().world;
            scoreboard = world.getScoreboard();
        }
        if(!teams.containsKey(serverOrgManager.getOrganism().getTrainingRoomRoleNamespace())){
            ScorePlayerTeam newScorePlayerTeam = scoreboard.createTeam(serverOrgManager.getOrganism().getTrainingRoomRoleNamespace());//TODO: Change to training room role setting
            teams.put(serverOrgManager.getOrganism().getTrainingRoomRoleNamespace(), newScorePlayerTeam);
        }
        ScorePlayerTeam scorePlayerTeam = teams.get(serverOrgManager.getOrganism().getTrainingRoomRoleNamespace());


        scoreboard.addPlayerToTeam(serverOrgManager.getEntity().getCachedUniqueIdString(), scorePlayerTeam);
        //isOnSameTeam
    }

}
