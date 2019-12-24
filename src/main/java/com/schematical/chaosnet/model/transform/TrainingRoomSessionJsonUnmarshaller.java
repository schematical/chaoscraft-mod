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
 * TrainingRoomSession JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TrainingRoomSessionJsonUnmarshaller implements Unmarshaller<TrainingRoomSession, JsonUnmarshallerContext> {

    public TrainingRoomSession unmarshall(JsonUnmarshallerContext context) throws Exception {
        TrainingRoomSession trainingRoomSession = new TrainingRoomSession();

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
                if (context.testExpression("genomeNamespace", targetDepth)) {
                    context.nextToken();
                    trainingRoomSession.setGenomeNamespace(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("name", targetDepth)) {
                    context.nextToken();
                    trainingRoomSession.setName(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("namespace", targetDepth)) {
                    context.nextToken();
                    trainingRoomSession.setNamespace(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("organisms", targetDepth)) {
                    context.nextToken();
                    trainingRoomSession.setOrganisms(new ListUnmarshaller<String>(context.getUnmarshaller(String.class)).unmarshall(context));
                }
                if (context.testExpression("owner_username", targetDepth)) {
                    context.nextToken();
                    trainingRoomSession.setOwnerUsername(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("ttl", targetDepth)) {
                    context.nextToken();
                    trainingRoomSession.setTtl(context.getUnmarshaller(Double.class).unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return trainingRoomSession;
    }

    private static TrainingRoomSessionJsonUnmarshaller instance;

    public static TrainingRoomSessionJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new TrainingRoomSessionJsonUnmarshaller();
        return instance;
    }
}
