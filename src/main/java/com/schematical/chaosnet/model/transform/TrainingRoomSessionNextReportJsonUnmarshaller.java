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
 * TrainingRoomSessionNextReport JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TrainingRoomSessionNextReportJsonUnmarshaller implements Unmarshaller<TrainingRoomSessionNextReport, JsonUnmarshallerContext> {

    public TrainingRoomSessionNextReport unmarshall(JsonUnmarshallerContext context) throws Exception {
        TrainingRoomSessionNextReport trainingRoomSessionNextReport = new TrainingRoomSessionNextReport();

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
                if (context.testExpression("namespace", targetDepth)) {
                    context.nextToken();
                    trainingRoomSessionNextReport.setNamespace(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("score", targetDepth)) {
                    context.nextToken();
                    trainingRoomSessionNextReport.setScore(context.getUnmarshaller(Double.class).unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return trainingRoomSessionNextReport;
    }

    private static TrainingRoomSessionNextReportJsonUnmarshaller instance;

    public static TrainingRoomSessionNextReportJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new TrainingRoomSessionNextReportJsonUnmarshaller();
        return instance;
    }
}
