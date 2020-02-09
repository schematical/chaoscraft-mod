package com.schematical.chaoscraft.server;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.AuthWhoamiResponse;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ChaosCraftServerPlayerInfo {
    public AuthWhoamiResponse authWhoamiResponse;
    public ArrayList<String> organisims = new ArrayList<String>();
    public UUID playerUUID;
    public State state = State.None;
    public ServerOrgManager observingEntity;

    public ServerPlayerEntity getServerPlayerEntity() {
        return ChaosCraft.getServer().server.getPlayerList().getPlayerByUUID(playerUUID);
    }

    public enum State{
        None,
        ObservingPassive,
        ObservingActive
    }
}
