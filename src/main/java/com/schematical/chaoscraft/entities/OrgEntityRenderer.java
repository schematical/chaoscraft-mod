package com.schematical.chaoscraft.entities;

import com.schematical.chaoscraft.ChaosCraft;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BeeModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OrgEntityRenderer extends LivingRenderer<OrgEntity, BipedModel<OrgEntity>> {

    public OrgEntityRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new PlayerModel<>(0, false), 0.5f);
        this.addLayer(new HeldItemLayer<>(this));
    }


    @Nullable
    @Override
    public ResourceLocation getEntityTexture(@Nonnull OrgEntity entity) {
        return new ResourceLocation(ChaosCraft.MODID, "batman.png");
    }
}