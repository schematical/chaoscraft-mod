package com.schematical.chaoscraft.gui;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.NeuronBase;
import com.schematical.chaoscraft.ai.NeuronDep;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.entities.EntityOrganism;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgNNetView extends CCGuiBase {

  private final EntityOrganism entityOrganism;

  public CCOrgNNetView(EntityOrganism entityOrganism) {
    super(entityOrganism.getName() + " NNet " + entityOrganism.getNNet().neurons.size(),
        new ResourceLocation(ChaosCraft.MODID, "textures/gui/nn_background.png"), 256, 256);

    this.entityOrganism = entityOrganism;
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    GlStateManager.color(1, 1, 1, 1);

    drawDefaultBackground();

    GlStateManager.pushMatrix();
    float x = (float) width / guiWidth;
    float y = (float) height / guiHeight;
    GlStateManager.scale(x, y, 1.0);
    this.mc.renderEngine.bindTexture(texture);
    drawModalRectWithCustomSizedTexture(0, 0, 0, 0, guiWidth, guiHeight, guiWidth, guiHeight);
    GlStateManager.popMatrix();

    fontRenderer
        .drawString(title, (width - this.fontRenderer.getStringWidth(title)) / 2, this.guiTop,
            0x000000);

    for (GuiButton button : buttonList) {
      if (button instanceof CCGUINeuronDisplayButton) {
        ((CCGUINeuronDisplayButton) button).drawDependencies();
      }
    }

    for (GuiButton button : buttonList) {
      button.drawButton(mc, mouseX, mouseY, partialTicks);
    }

    for (GuiButton button : buttonList) {
      if (button instanceof CCGUINeuronDisplayButton) {
        List<String> text = new ArrayList<String>();
        text.add(((CCGUINeuronDisplayButton) button).neuron.toLongString());
        drawTooltip(text, mouseX, mouseY, button.x, button.y, button.width, button.height);
      }
    }

  }

  public void drawTooltip(List<String> lines, int mouseX, int mouseY, int posX, int posY, int width,
      int height) {
    if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height) {
      drawHoveringText(lines, mouseX, mouseY);
    }
  }

  @Override
  public void initGui() {
    this.guiLeft = 50;
    this.guiTop = 10;

    initializeButtons();
  }

  int initializeButtons() {
    buttonList.clear();
    int ID = 0;
    int buttonWidth = 100;
    int buttonHeight = 20;

    buttonList.add(
        new CCGuiButton(
            ID++,
            ((this.width) / 2) - buttonWidth / 2,
            this.guiTop + (this.height - guiTop) - (buttonHeight + 15),
            buttonWidth,
            buttonHeight,
            I18n.format(ChaosCraft.MODID + ".gui.close"),
            ButtonAction.CLOSE
        )
    );

    List<OutputNeuron> outputs = new ArrayList<>();
    List<InputNeuron> inputs = new ArrayList<>();

    HashMap<NeuronBase, CCNeuronInformation> neuronInformation = new HashMap<>();

    for (NeuronBase neuronBase : entityOrganism.getNNet().neurons.values()) {
      if (neuronBase instanceof OutputNeuron) {
        outputs.add((OutputNeuron) neuronBase);
      } else if (neuronBase instanceof InputNeuron) {
        inputs.add((InputNeuron) neuronBase);
      }

      neuronInformation.put(neuronBase, new CCNeuronInformation());
    }

    for (int i = 0; i < outputs.size(); i++) {
      OutputNeuron output = outputs.get(i);
      CCNeuronInformation information = neuronInformation.get(output);
      information.y = (height / outputs.size()) * i + (height / outputs.size()) / 2;

      output.setDistanceFromIO(0, width, height, buttonHeight, neuronInformation);
      information.x = width - (buttonWidth / 2 + guiLeft);
    }

    for (int i = 0; i < inputs.size(); i++) {
      InputNeuron input = inputs.get(i);
      CCNeuronInformation information = neuronInformation.get(input);
      information.x = buttonWidth / 2 + guiLeft;
      information.y = (height / inputs.size()) * i + (height / inputs.size()) / 2;
    }

    HashMap<Float, List<CCNeuronInformation>> layers = new HashMap<>();
    for (NeuronBase neuron : entityOrganism.getNNet().neurons.values()) {
      CCNeuronInformation information = neuronInformation.get(neuron);
      if (!(neuron instanceof InputNeuron || neuron instanceof OutputNeuron)) {
        if (!layers.containsKey(information.layer)) {
          layers.put(information.layer, new ArrayList<>());
        }

        layers.get(information.layer).add(information);
      }
    }

    for (float layer : layers.keySet()) {
      List<CCNeuronInformation> neuronsInLayer = layers.get(layer);

      for (int i = 0; i < neuronsInLayer.size(); i++) {
        neuronsInLayer.get(i).y =
            (height / neuronsInLayer.size()) * i + (height / neuronsInLayer.size()) / 2;
      }

    }

    for (NeuronBase neuron : entityOrganism.getNNet().neurons.values()) {

      CCNeuronInformation information = neuronInformation.get(neuron);
      buttonWidth = fontRenderer.getStringWidth(neuron.toString()) + 10;

      buttonList.add(new CCGUINeuronDisplayButton(
              ID++,
              information.x - buttonWidth / 2,
              information.y - buttonHeight / 2,
              buttonWidth,
              buttonHeight,
              neuron.toString(),
              entityOrganism,
              neuron,
              neuronInformation

          )
      );
    }

    return ID;
  }

  @Override
  public boolean doesGuiPauseGame() {
    return false;
  }

  public void drawLine(int startX, int startY, int endX, int endY, int r, int g, int b, int a) {
    GlStateManager.disableTexture2D();
    GlStateManager.color(r / 255f, g / 255f, b / 255f, a / 255f);

    GlStateManager.glLineWidth(5);
    GlStateManager.glBegin(GL_LINE_STRIP);

    GlStateManager.glVertex3f(startX, startY, 0);
    GlStateManager.glVertex3f(endX, endY, 0);
    GlStateManager.glEnd();
    GlStateManager.enableTexture2D();
  }


  public class CCGUINeuronDisplayButton extends CCGuiButton {

    public final NeuronBase neuron;
    HashMap<NeuronBase, CCNeuronInformation> neuronInformation;

    public CCGUINeuronDisplayButton(int buttonId, int x, int y, int width, int height,
        String buttonText, EntityOrganism entityOrganism, NeuronBase neuron,
        HashMap<NeuronBase, CCNeuronInformation> neuronInformation) {
      super(buttonId, x, y, width, height, buttonText, ButtonAction.VIEW_NEURON_DETAIL_ACTION,
          entityOrganism);
      this.neuron = neuron;
      this.neuronInformation = neuronInformation;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
      super.drawButton(mc, mouseX, mouseY, partialTicks);
    }

    public void drawDependencies() {
      CCNeuronInformation informationOut = neuronInformation.get(neuron);
      for (NeuronDep dep : neuron.dependencies) {
        CCNeuronInformation informationIn = neuronInformation.get(dep.depNeuron);
        drawLine(informationOut.x, informationOut.y, informationIn.x, informationIn.y,
            dep.weight < 0 ? (int) (255 * dep.weight * -1) : 0,
            dep.weight > 0 ? (int) (255 * dep.weight) : 0, 0, 255);
      }
    }
  }

  public class CCNeuronInformation {

    public int x;
    public int y;
    public float layer;
    public int distanceFromOutput;
    public int distanceFromInput;

  }

}