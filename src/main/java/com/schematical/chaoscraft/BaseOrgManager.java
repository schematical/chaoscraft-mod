package com.schematical.chaoscraft;

import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.action.ActionBuffer;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.tickables.BaseChaosEventListener;
import com.schematical.chaosnet.model.Organism;

import java.util.ArrayList;

public abstract class BaseOrgManager {
    protected Organism organism;
    protected OrgEntity orgEntity;
    private ArrayList<BaseChaosEventListener> eventListeners = new ArrayList<BaseChaosEventListener>();
    private ActionBuffer actionBuffer;

    public void attachOrganism(Organism organism){
        this.organism = organism;
    }
    public void attachOrgEntity(OrgEntity orgEntity){
        this.orgEntity = orgEntity;
        this.actionBuffer = new ActionBuffer(this);

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
