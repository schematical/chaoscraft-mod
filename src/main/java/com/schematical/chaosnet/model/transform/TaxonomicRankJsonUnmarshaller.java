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
 * TaxonomicRank JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TaxonomicRankJsonUnmarshaller implements Unmarshaller<TaxonomicRank, JsonUnmarshallerContext> {

    public TaxonomicRank unmarshall(JsonUnmarshallerContext context) throws Exception {
        TaxonomicRank taxonomicRank = new TaxonomicRank();

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
                    taxonomicRank.setAge(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("childrenReportedThisGen", targetDepth)) {
                    context.nextToken();
                    taxonomicRank.setChildrenReportedThisGen(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("childrenSpawnedThisGen", targetDepth)) {
                    context.nextToken();
                    taxonomicRank.setChildrenSpawnedThisGen(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("currScore", targetDepth)) {
                    context.nextToken();
                    taxonomicRank.setCurrScore(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("generation", targetDepth)) {
                    context.nextToken();
                    taxonomicRank.setGeneration(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("gensSinceLastImprovement", targetDepth)) {
                    context.nextToken();
                    taxonomicRank.setGensSinceLastImprovement(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("highScore", targetDepth)) {
                    context.nextToken();
                    taxonomicRank.setHighScore(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("historicalScores", targetDepth)) {
                    context.nextToken();
                    taxonomicRank.setHistoricalScores(new ListUnmarshaller<HistoricalScoresElement>(HistoricalScoresElementJsonUnmarshaller.getInstance())
                            .unmarshall(context));
                }
                if (context.testExpression("historicalScoresRaw", targetDepth)) {
                    context.nextToken();
                    taxonomicRank.setHistoricalScoresRaw(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("name", targetDepth)) {
                    context.nextToken();
                    taxonomicRank.setName(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("namespace", targetDepth)) {
                    context.nextToken();
                    taxonomicRank.setNamespace(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("observedAttributesRaw", targetDepth)) {
                    context.nextToken();
                    taxonomicRank.setObservedAttributesRaw(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("owner_username", targetDepth)) {
                    context.nextToken();
                    taxonomicRank.setOwnerUsername(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("parentNamespace", targetDepth)) {
                    context.nextToken();
                    taxonomicRank.setParentNamespace(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("trainingRoomNamespace", targetDepth)) {
                    context.nextToken();
                    taxonomicRank.setTrainingRoomNamespace(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("trankClass", targetDepth)) {
                    context.nextToken();
                    taxonomicRank.setTrankClass(context.getUnmarshaller(String.class).unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return taxonomicRank;
    }

    private static TaxonomicRankJsonUnmarshaller instance;

    public static TaxonomicRankJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new TaxonomicRankJsonUnmarshaller();
        return instance;
    }
}
