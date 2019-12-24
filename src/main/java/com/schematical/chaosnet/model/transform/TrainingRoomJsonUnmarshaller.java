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
 * TrainingRoom JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TrainingRoomJsonUnmarshaller implements Unmarshaller<TrainingRoom, JsonUnmarshallerContext> {

    public TrainingRoom unmarshall(JsonUnmarshallerContext context) throws Exception {
        TrainingRoom trainingRoom = new TrainingRoom();

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
                if (context.testExpression("name", targetDepth)) {
                    context.nextToken();
                    trainingRoom.setName(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("namespace", targetDepth)) {
                    context.nextToken();
                    trainingRoom.setNamespace(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("owner_username", targetDepth)) {
                    context.nextToken();
                    trainingRoom.setOwnerUsername(context.getUnmarshaller(String.class).unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return trainingRoom;
    }

    private static TrainingRoomJsonUnmarshaller instance;

    public static TrainingRoomJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new TrainingRoomJsonUnmarshaller();
        return instance;
    }
}
