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
 * Stats JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class StatsJsonUnmarshaller implements Unmarshaller<Stats, JsonUnmarshallerContext> {

    public Stats unmarshall(JsonUnmarshallerContext context) throws Exception {
        Stats stats = new Stats();

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
                if (context.testExpression("genProgress", targetDepth)) {
                    context.nextToken();
                    stats.setGenProgress(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("orgsReportedSoFar", targetDepth)) {
                    context.nextToken();
                    stats.setOrgsReportedSoFar(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("orgsSpawnedSoFar", targetDepth)) {
                    context.nextToken();
                    stats.setOrgsSpawnedSoFar(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("totalOrgsPerGen", targetDepth)) {
                    context.nextToken();
                    stats.setTotalOrgsPerGen(context.getUnmarshaller(Double.class).unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return stats;
    }

    private static StatsJsonUnmarshaller instance;

    public static StatsJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new StatsJsonUnmarshaller();
        return instance;
    }
}
