package com.schematical.chaoscraft.tickables;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.CCObservableAttributeManager;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.NeuronBase;
import com.schematical.chaoscraft.ai.action.ActionBase;
import com.schematical.chaoscraft.ai.inputs.HasInInventoryInput;
import com.schematical.chaoscraft.ai.inputs.targetcandidate.TargetCandidateTypeInput;
import com.schematical.chaoscraft.ai.memory.BlockStateMemoryBufferSlot;
import com.schematical.chaoscraft.ai.outputs.rawnav.CraftOutput;
import com.schematical.chaoscraft.ai.outputs.rawnav.EquipOutput;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.client.gui.CCGUIHelper;
import com.schematical.chaoscraft.client.iRenderWorldLastEvent;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaosnet.model.ObservedAttributesElement;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DebugDrawTicker extends BaseChaosEventListener implements iRenderWorldLastEvent {
    private final int MAX = 20 * 5;
    private CCObservableAttributeManager observableAttributeManager;
    private  int ticksSinceLastMutation = 0;
    private  int newAttributesLength = 0;
    private  OrgEntity orgEntity;
    private ClientOrgManager clientOrgManager;
    public DebugDrawTicker(/*OrgEntity orgEntity*/) {
        //this.orgEntity = orgEntity;
        //this.observableAttributeManager = orgEntity.observableAttributeManager;
        ticksSinceLastMutation = (int)  (Math.round(Math.random() * MAX /2));
    }
    public void onClientTick(ClientOrgManager baseOrgManager) {
        if (clientOrgManager == null) {
            clientOrgManager = baseOrgManager;
            ChaosCraft.getClient().addRenderListener(this);

        }
    }

    @Override
    public boolean onRenderWorldLastEvent(RenderWorldLastEvent event) {
        if (clientOrgManager == null) {
            return true;
        }
        if (!clientOrgManager.getEntity().isAlive()) {
            return false;
        }

        Color lineColor = Color.PINK;

        ActionBase actionBase = clientOrgManager.getActionBuffer().getCurrAction();
        if (actionBase != null) {
            ChaosTarget chaosTarget = actionBase.getTarget();
            if (chaosTarget.canEntityTouch(clientOrgManager.getEntity())) {
                lineColor = Color.BLUE;
            }
            if(clientOrgManager.getEntity().getSendRawOutput()){
                //return true;
                lineColor = Color.YELLOW;
            }
            CCGUIHelper.drawLine2d(
                    event.getMatrixStack(),
                    clientOrgManager.getEntity().getEyePosition(1f),
                    chaosTarget.getTargetPositionCenter(),
                    Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView(),
                    lineColor,
                    .5f
            );
        }
        return true;
    }


}
