package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.ai.NeuronBase;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.Eye;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChaosOrgBiologyButton extends Button {

    public static final int EXPANDED_WIDTH = 300;
    public static final int MIN_WIDTH = 20;
    public enum State{
        Open,
        Closed
    }
    private final BiologyBase biologyBase;


    public State state = State.Open;
    private ChaosOrgBiologyOverlayGui chaosOrgBiologyOverlayGui;
    public ChaosOrgBiologyButton(BiologyBase biologyBase, ChaosOrgBiologyOverlayGui chaosOrgBiologyOverlayGui, int x, int y) {
        super(
                x,
                y,
                EXPANDED_WIDTH,
                10,
                "X",
                (button)->{

                });
        this.chaosOrgBiologyOverlayGui = chaosOrgBiologyOverlayGui;
        this.biologyBase  = biologyBase;

        //this.min();

    }
    public void renderRefresh(){
        if(state.equals(State.Closed)){
            return;
        }
        if(this.biologyBase instanceof Eye){
            Eye eye = ((Eye)biologyBase);
            eye.reset();
            eye.canSeenBlocks();
            eye.canSeenEntities();
        }
        this.setMessage(this.biologyBase.toString());
    }
    public void min() {
        setWidth(MIN_WIDTH);

        setMessage( "B");
        state = State.Closed;

    }

    @Override
    public void onRelease(double p_onRelease_1_, double p_onRelease_3_) {
        //Toggle
        if(state.equals(State.Closed)){
            //Expand it

            this.chaosOrgBiologyOverlayGui.minAll();
            this.setWidth(EXPANDED_WIDTH);
            this.setMessage(this.biologyBase.toString());
            this.state = State.Open;

        }else{
            //Open detail view
        }
    }
}
