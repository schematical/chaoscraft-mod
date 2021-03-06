package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.ai.NeuronBase;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class ChaosNeuronButton extends Button {

    public static final int EXPANDED_WIDTH = 250;
    public static final int MIN_WIDTH = 40;
    public enum State{
        Open,
        Closed
    }
    private final NeuronBase neuronBase;
    private final int i;
    private int defaultY;

    public State state = State.Closed;
    private ChaosNNetViewOverlayGui chaosNNetViewOverlayGui;
    public HashMap<String, ChaosNeuronButton> dependancies = new HashMap<String, ChaosNeuronButton>();
    public ChaosNeuronButton(NeuronBase neuronBase, ChaosNNetViewOverlayGui chaosNNetViewOverlayGui, int i, int y) {
        super(1,
                y,
                MIN_WIDTH,
                15,
                "X",
                (button)->{

                });
        defaultY = y;
        String text = null;
        this.chaosNNetViewOverlayGui = chaosNNetViewOverlayGui;
        this.neuronBase  = neuronBase;
        this.i = i;
        this.min();

    }
    public NeuronBase getNeuronBase(){
        return neuronBase;
    }

    public void renderRefresh(){
        y = defaultY + (int)Math.round(chaosNNetViewOverlayGui.getScrollAmount());
        if(state.equals(State.Closed)){
            switch(neuronBase._base_type()){
                case(com.schematical.chaoscraft.Enum.INPUT):
                    x = 10;
                    setMessage( neuronBase.toAbreviation() + " " + neuronBase.getPrettyCurrValue());
                    break;
                case(com.schematical.chaoscraft.Enum.OUTPUT):
                    x = chaosNNetViewOverlayGui.width - 10 - MIN_WIDTH;
                    setMessage( neuronBase.toAbreviation() + " " + neuronBase.getPrettyCurrValue());
                    break;
                case(com.schematical.chaoscraft.Enum.MIDDLE):
                    x = chaosNNetViewOverlayGui.width / 2;
                    setMessage("M-" + i + " " + neuronBase.getPrettyCurrValue());
                    break;

            }
            return;
        }
        //this.setWidth((int)neuronBase._lastValue * 200);
        this.setMessage(this.neuronBase.toLongString());
        switch(neuronBase._base_type()){
            case(com.schematical.chaoscraft.Enum.INPUT):
                x = 10;

                break;
            case(com.schematical.chaoscraft.Enum.OUTPUT):
                x = chaosNNetViewOverlayGui.width - 10 - this.width;

                break;
            case(com.schematical.chaoscraft.Enum.MIDDLE):
                x = chaosNNetViewOverlayGui.width / 2;

                break;

        }
    }
    public void min() {
        setWidth(MIN_WIDTH);
        state = State.Closed;
        switch(neuronBase._base_type()){
            case(com.schematical.chaoscraft.Enum.INPUT):
                x = 10;
                setMessage( "I-" + i);
                break;
            case(com.schematical.chaoscraft.Enum.OUTPUT):
                x = chaosNNetViewOverlayGui.width - 10 - MIN_WIDTH;
                setMessage("O-" + i);
                break;
            case(com.schematical.chaoscraft.Enum.MIDDLE):
                x = chaosNNetViewOverlayGui.width / 2;
                setMessage("M-" + i);
                break;

        }
    }

    @Override
    public void onRelease(double p_onRelease_1_, double p_onRelease_3_) {
        //Toggle
        if(state.equals(State.Closed)){
            //Expand it

            this.chaosNNetViewOverlayGui.minAll();
            this.setWidth(EXPANDED_WIDTH);
            this.setMessage(this.neuronBase.toString());
            switch(neuronBase._base_type()){
                case(com.schematical.chaoscraft.Enum.OUTPUT):
                    x = chaosNNetViewOverlayGui.width - 10 - EXPANDED_WIDTH;
                    break;
            }
            state = State.Open;

        }else{
            //Open detail view

           /* ChaosOrgNeuronDetailOverlayGui screen = new ChaosOrgNeuronDetailOverlayGui(chaosNNetViewOverlayGui.getClientOrgManager(), neuronBase);
            Minecraft.getInstance().displayGuiScreen(screen);*/
           this.min();
        }
    }
}
