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
 * TrainingRoomSessionNextResponse JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TrainingRoomSessionNextResponseJsonUnmarshaller implements Unmarshaller<TrainingRoomSessionNextResponse, JsonUnmarshallerContext> {

    public TrainingRoomSessionNextResponse unmarshall(JsonUnmarshallerContext context) throws Exception {
        TrainingRoomSessionNextResponse trainingRoomSessionNextResponse = new TrainingRoomSessionNextResponse();

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
                if (context.testExpression("organisms", targetDepth)) {
                    context.nextToken();
                    trainingRoomSessionNextResponse.setOrganisms(new ListUnmarshaller<Organism>(OrganismJsonUnmarshaller.getInstance()).unmarshall(context));
                }
                if (context.testExpression("species", targetDepth)) {
                    context.nextToken();
                    trainingRoomSessionNextResponse.setSpecies(new ListUnmarshaller<TaxonomicRank>(TaxonomicRankJsonUnmarshaller.getInstance())
                            .unmarshall(context));
                }
                if (context.testExpression("stats", targetDepth)) {
                    context.nextToken();
                    trainingRoomSessionNextResponse.setStats(StatsJsonUnmarshaller.getInstance().unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return trainingRoomSessionNextResponse;
    }

    private static TrainingRoomSessionNextResponseJsonUnmarshaller instance;

    public static TrainingRoomSessionNextResponseJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new TrainingRoomSessionNextResponseJsonUnmarshaller();
        return instance;
    }
}
