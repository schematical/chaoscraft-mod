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
 * TraningRoomSessionStartRequest JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TraningRoomSessionStartRequestJsonUnmarshaller implements Unmarshaller<TraningRoomSessionStartRequest, JsonUnmarshallerContext> {

    public TraningRoomSessionStartRequest unmarshall(JsonUnmarshallerContext context) throws Exception {
        TraningRoomSessionStartRequest traningRoomSessionStartRequest = new TraningRoomSessionStartRequest();

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
                if (context.testExpression("reset", targetDepth)) {
                    context.nextToken();
                    traningRoomSessionStartRequest.setReset(context.getUnmarshaller(Boolean.class).unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return traningRoomSessionStartRequest;
    }

    private static TraningRoomSessionStartRequestJsonUnmarshaller instance;

    public static TraningRoomSessionStartRequestJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new TraningRoomSessionStartRequestJsonUnmarshaller();
        return instance;
    }
}
