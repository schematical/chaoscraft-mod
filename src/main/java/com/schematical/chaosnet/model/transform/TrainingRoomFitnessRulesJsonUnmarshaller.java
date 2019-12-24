/**

*/
package com.schematical.chaosnet.model.transform;

import java.math.*;

import javax.annotation.Generated;

import com.schematical.chaosnet.model.*;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers.*;
import com.amazonaws.transform.*;

import com.fasterxml.jackson.core.JsonToken;
import static com.fasterxml.jackson.core.JsonToken.*;

/**
 * TrainingRoomFitnessRules JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TrainingRoomFitnessRulesJsonUnmarshaller implements Unmarshaller<TrainingRoomFitnessRules, JsonUnmarshallerContext> {

    public TrainingRoomFitnessRules unmarshall(JsonUnmarshallerContext context) throws Exception {
        TrainingRoomFitnessRules trainingRoomFitnessRules = new TrainingRoomFitnessRules();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return null;
        }

        while (true) {
            if (token == null)
                break;

            if (token == FIELD_NAME || token == START_OBJECT) {
                if (context.testExpression("fitnessRules", targetDepth)) {
                    context.nextToken();
                    trainingRoomFitnessRules.setFitnessRules(FitnessRulesJsonUnmarshaller.getInstance().unmarshall(context));
                }
                if (context.testExpression("fitnessRulesRaw", targetDepth)) {
                    context.nextToken();
                    trainingRoomFitnessRules.setFitnessRulesRaw(context.getUnmarshaller(String.class).unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return trainingRoomFitnessRules;
    }

    private static TrainingRoomFitnessRulesJsonUnmarshaller instance;

    public static TrainingRoomFitnessRulesJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new TrainingRoomFitnessRulesJsonUnmarshaller();
        return instance;
    }
}
