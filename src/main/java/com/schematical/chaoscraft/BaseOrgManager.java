package com.schematical.chaoscraft;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.Organism;
import net.minecraft.entity.Entity;

public abstract class BaseOrgManager {
    protected Organism organism;
    protected OrgEntity orgEntity;
    public void attachOrganism(Organism organism){
        this.organism = organism;
    }
    public void attachOrgEntity(OrgEntity orgEntity){
        this.orgEntity = orgEntity;

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
}
