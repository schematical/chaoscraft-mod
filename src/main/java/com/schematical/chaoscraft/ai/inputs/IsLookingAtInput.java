package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.Eye;
import java.util.List;
import org.json.simple.JSONObject;
import scala.actors.Debug;

/**
 * Created by user1a on 12/8/18.
 */
public class IsLookingAtInput extends InputNeuron {

  public static final double MAX_DISTANCE = 3d;
  public String attributeId;
  public String attributeValue;
  public Eye eye;
  private String eyeId;

  //public PositionRange positionRange;

  @Override
  public float evaluate() {
    //Iterate through all blocks entities etc with in the range
    if (this.nNet.entity.getDebug()) {
      //ChaosCraft.logger.info("Debugging...");
    }

    List<CCObserviableAttributeCollection> attributeCollections = null;
    switch (attributeId) {
      case (CCAttributeId.BLOCK_ID):
        attributeCollections = eye.canSeenBlocks();
        for (CCObserviableAttributeCollection attributeCollection : attributeCollections) {
          if (attributeValue.equals(attributeCollection.resourceId)) {
            _lastValue = 1;//TODO: Add distance?
          }
        }

        break;
      case (CCAttributeId.ENTITY_ID):
        attributeCollections = eye.canSeenEntities();
        for (CCObserviableAttributeCollection attributeCollection : attributeCollections) {
          if (attributeValue.equals(attributeCollection.resourceId)) {
            _lastValue = 1;//TODO: Add distance?
          }
        }

        break;

    }
    return _lastValue;
  }

  @Override
  public void parseData(JSONObject jsonObject) {
    super.parseData(jsonObject);

    attributeId = jsonObject.get("attributeId").toString();
    attributeValue = jsonObject.get("attributeValue").toString();
    eyeId = jsonObject.get("eye").toString();

    if (!nNet.biology.containsKey(eyeId)) {
      Debug.error("Invalid Eye Id: " + eyeId);
    }
    eye = (Eye) nNet.biology.get(eyeId);

  }

  public String toLongString() {
    String response = super.toLongString();
    response += " " + this.attributeId + " " + this.attributeValue + " E" + this.eyeId;
    return response;

  }

}
