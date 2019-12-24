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
 * HistoricalScoresElement JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class HistoricalScoresElementJsonUnmarshaller implements Unmarshaller<HistoricalScoresElement, JsonUnmarshallerContext> {

    public HistoricalScoresElement unmarshall(JsonUnmarshallerContext context) throws Exception {
        HistoricalScoresElement historicalScoresElement = new HistoricalScoresElement();

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
                if (context.testExpression("age", targetDepth)) {
                    context.nextToken();
                    historicalScoresElement.setAge(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("genAvg", targetDepth)) {
                    context.nextToken();
                    historicalScoresElement.setGenAvg(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("parentAge", targetDepth)) {
                    context.nextToken();
                    historicalScoresElement.setParentAge(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("topAvg", targetDepth)) {
                    context.nextToken();
                    historicalScoresElement.setTopAvg(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("topMax", targetDepth)) {
                    context.nextToken();
                    historicalScoresElement.setTopMax(context.getUnmarshaller(Double.class).unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return historicalScoresElement;
    }

    private static HistoricalScoresElementJsonUnmarshaller instance;

    public static HistoricalScoresElementJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new HistoricalScoresElementJsonUnmarshaller();
        return instance;
    }
}
