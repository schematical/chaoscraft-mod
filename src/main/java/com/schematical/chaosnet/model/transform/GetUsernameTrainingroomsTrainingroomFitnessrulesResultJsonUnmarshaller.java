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
 * GetUsernameTrainingroomsTrainingroomFitnessrulesResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomFitnessrulesResultJsonUnmarshaller implements
        Unmarshaller<GetUsernameTrainingroomsTrainingroomFitnessrulesResult, JsonUnmarshallerContext> {

    public GetUsernameTrainingroomsTrainingroomFitnessrulesResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetUsernameTrainingroomsTrainingroomFitnessrulesResult getUsernameTrainingroomsTrainingroomFitnessrulesResult = new GetUsernameTrainingroomsTrainingroomFitnessrulesResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getUsernameTrainingroomsTrainingroomFitnessrulesResult;
        }

        while (true) {
            if (token == null)
                break;

            getUsernameTrainingroomsTrainingroomFitnessrulesResult.setTrainingRoomFitnessRules(TrainingRoomFitnessRulesJsonUnmarshaller.getInstance()
                    .unmarshall(context));
            token = context.nextToken();
        }

        return getUsernameTrainingroomsTrainingroomFitnessrulesResult;
    }

    private static GetUsernameTrainingroomsTrainingroomFitnessrulesResultJsonUnmarshaller instance;

    public static GetUsernameTrainingroomsTrainingroomFitnessrulesResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetUsernameTrainingroomsTrainingroomFitnessrulesResultJsonUnmarshaller();
        return instance;
    }
}
