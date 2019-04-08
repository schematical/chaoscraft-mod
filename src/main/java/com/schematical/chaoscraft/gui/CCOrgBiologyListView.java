package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.BlockPositionSensor;
import com.schematical.chaoscraft.ai.biology.Eye;
import com.schematical.chaoscraft.entities.EntityOrganism;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgBiologyListView extends CCGuiBase {

  protected final EntityOrganism entityOrganism;

  public CCOrgBiologyListView(EntityOrganism entityOrganism) {
    super(entityOrganism.getName(), new ResourceLocation(ChaosCraft.MODID, "textures/gui/book.png"),
        175, 228);
    this.entityOrganism = entityOrganism;
  }

  @Override
  int initializeButtons() {
    buttonList.clear();

    int ID = super.initializeButtons();

    int buttonWidth = 150;
    int buttonHeight = 20;
    Iterator<BiologyBase> biologysIterator = this.entityOrganism.getNNet().biology.values()
        .iterator();
    while (biologysIterator.hasNext()) {
      BiologyBase biologyBase = biologysIterator.next();
      buttonList.add(
          new CCBiologyDetailButton(
              ID++,
              this.guiLeft + (this.guiWidth / 2) - buttonWidth / 2,
              this.guiTop + (buttonHeight) * (ID - 1),
              buttonWidth,
              buttonHeight,
              biologyBase.toString(), //I18n.format(ChaosCraft.MODID + ".gui.open.score_events"),
              ButtonAction.VIEW_BIOLOGY_DETAIL_ACTION,
              entityOrganism,
              biologyBase
          )
      );
    }

    return ID;
  }

  public class CCBiologyDetailButton extends CCGuiButton {

    public BiologyBase biologyBase;

    public CCBiologyDetailButton(int x, int y, int z, int buttonWidth, int buttonHeight, String s,
        ButtonAction viewBiologyDetailAction, EntityOrganism entityOrganism,
        BiologyBase _biologyBase) {
      super(x, y, z, buttonWidth, buttonHeight, s, viewBiologyDetailAction, entityOrganism);
      biologyBase = _biologyBase;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {

      this.displayString = biologyBase.id + " ";
      if (biologyBase instanceof Eye) {
        Eye eye = (Eye) biologyBase;
        //this.displayString += " - ";
        eye.reset();
        ArrayList<CCObserviableAttributeCollection> attributeCollections = eye.canSeenBlocks();
        attributeCollections.addAll(eye.canSeenEntities());
        for (CCObserviableAttributeCollection attributeCollection : attributeCollections) {
          this.displayString += attributeCollection.resourceId + " ";
        }
               /* this.displayString +=  "\n";
                ArrayList<CCObserviableAttributeCollection> attributeCollections2 =  eye.canSeenEntities();
                for(CCObserviableAttributeCollection attributeCollection: attributeCollections2){
                    this.displayString += attributeCollection.resourceId + " ";
                }*/
      } else if (biologyBase instanceof BlockPositionSensor) {
                /*BlockPositionSensor blockPositionSensor = (BlockPositionSensor) biologyBase;
                //this.displayString += " - ";
                ArrayList<CCObserviableAttributeCollection> attributeCollections =  blockPositionSensor.();
                attributeCollections.addAll(blockPositionSensor.canSeenEntities());
                for(CCObserviableAttributeCollection attributeCollection: attributeCollections){
                    this.displayString += attributeCollection.resourceId + " ";
                }*/
      }
      //ChaosCraft.logger.info("CCGUINeuronDisplayButton.drawButton " + this.displayString);
      super.drawButton(mc, mouseX, mouseY, partialTicks);

    }
  }

  @Override
  public boolean doesGuiPauseGame() {
    return false;
  }

}