package com.schematical.chaoscraft.server;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.AuthWhoamiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ChaosCraftServerPlayerInfo {
    public AuthWhoamiResponse authWhoamiResponse;
    public ArrayList<String> organisims = new ArrayList<String>();
    public UUID playerUUID;
}
