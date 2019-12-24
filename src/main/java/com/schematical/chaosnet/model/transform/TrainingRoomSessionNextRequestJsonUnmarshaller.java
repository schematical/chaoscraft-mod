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
 * TrainingRoomSessionNextRequest JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TrainingRoomSessionNextRequestJsonUnmarshaller implements Unmarshaller<TrainingRoomSessionNextRequest, JsonUnmarshallerContext> {

    public TrainingRoomSessionNextRequest unmarshall(JsonUnmarshallerContext context) throws Exception {
        TrainingRoomSessionNextRequest trainingRoomSessionNextRequest = new TrainingRoomSessionNextRequest();

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
                if (context.testExpression("nNetRaw", targetDepth)) {
                    context.nextToken();
                    trainingRoomSessionNextRequest.setNNetRaw(context.getUnmarshaller(Boolean.class).unmarshall(context));
                }
                if (context.testExpression("observedAttributes", targetDepth)) {
                    context.nextToken();
                    trainingRoomSessionNextRequest.setObservedAttributes(new ListUnmarshaller<ObservedAttributesElement>(
                            ObservedAttributesElementJsonUnmarshaller.getInstance()).unmarshall(context));
                }
                if (context.testExpression("report", targetDepth)) {
                    context.nextToken();
                    trainingRoomSessionNextRequest.setReport(new ListUnmarshaller<TrainingRoomSessionNextReport>(TrainingRoomSessionNextReportJsonUnmarshaller
                            .getInstance()).unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return trainingRoomSessionNextRequest;
    }

    private static TrainingRoomSessionNextRequestJsonUnmarshaller instance;

    public static TrainingRoomSessionNextRequestJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new TrainingRoomSessionNextRequestJsonUnmarshaller();
        return instance;
    }
}
