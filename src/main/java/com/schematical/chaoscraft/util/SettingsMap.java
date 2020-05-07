package com.schematical.chaoscraft.util;

import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.SettingValue;
import it.unimi.dsi.fastutil.Hash;

import java.util.HashMap;
import java.util.List;

public class SettingsMap {
    public HashMap<String, SettingValue> settings = new HashMap<>();
    public SettingsMap(List<SettingValue> settings){
        for (SettingValue setting : settings) {
            this.settings.put(setting.getNamespace(), setting);
        }
    }
    public String getString(ChaosSettings chaosSetting){
        if(!this.settings.containsKey(chaosSetting.name())){
            throw new ChaosNetException("ChaosSetting not found: " + chaosSetting.name() + " - " + this.settings.keySet().toString());
        }
        return this.settings.get(chaosSetting.name()).getValue();
    }
}
