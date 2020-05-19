package com.schematical.chaoscraft;

import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.action.ActionBuffer;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.tickables.BaseChaosEventListener;
import com.schematical.chaoscraft.util.ChaosSettings;
import com.schematical.chaoscraft.util.SettingsMap;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.Organism;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public abstract class BaseOrgManager {
    protected Organism organism;
    protected OrgEntity orgEntity;
    private ArrayList<BaseChaosEventListener> eventListeners = new ArrayList<BaseChaosEventListener>();
    private ActionBuffer actionBuffer;
    protected SettingsMap roleSettings;
    public void attachOrganism(Organism organism){
        this.organism = organism;

    }
    public void attachOrgEntity(OrgEntity orgEntity){
        this.orgEntity = orgEntity;
        this.actionBuffer = new ActionBuffer(this);

    }
    public void initInventory(){
        if(this.roleSettings == null){
            throw new ChaosNetException("`this.roleSettings` has not been initialized");
        }
        for(int i = 0; i < 4; i++) {
            String invValue = this.roleSettings.getString(ChaosSettings.valueOf("INV_" + i));
            if (invValue != null) {
                String[] parts = invValue.split("@");
                int count = 1;
                String id = parts[0];
                if (parts.length > 1) {
                    count = Integer.parseInt(parts[1]);
                }

                GameRegistry.findRegistry(Item.class);
                Item item = (Item) ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
                ItemStack itemStack = new ItemStack(item, count);
                this.orgEntity.getItemHandler().setStackInSlot(i, itemStack);
                if(ChaosCraft.getServer() != null) {
                    this.orgEntity.syncSlot(i);
                }
            }
        }
    }
    public  OrgEntity getEntity(){
        return orgEntity;
    };
    public String getCCNamespace() {
        if(organism == null){
            return null;
        }
        return organism.getNamespace();
    }


    public Organism getOrganism() {
        return organism;
    }

    public void attatchEventListener(BaseChaosEventListener tickable){
        eventListeners.add(tickable);
    }

    public ArrayList<BaseChaosEventListener> getEventListeners(){
        return eventListeners;
    }
    public BaseChaosEventListener getTickable(Class<?> t){
        for (BaseChaosEventListener tickable : eventListeners) {
            if(t.isInstance(tickable)){
                return tickable;
            }
        }
        return null;
    }
    public NeuralNet getNNet(){
        if(getEntity() == null){
            return null;
        }
        return getEntity().getNNet();
    }
    public BiologyBase getBiology(String biologyId){

        return getEntity().getNNet().getBiology(biologyId);
    }

    public ActionBuffer getActionBuffer(){
        return actionBuffer;
    }
}
