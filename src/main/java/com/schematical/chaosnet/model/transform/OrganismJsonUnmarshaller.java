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
 * Organism JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class OrganismJsonUnmarshaller implements Unmarshaller<Organism, JsonUnmarshallerContext> {

    public Organism unmarshall(JsonUnmarshallerContext context) throws Exception {
        Organism organism = new Organism();

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
                if (context.testExpression("generation", targetDepth)) {
                    context.nextToken();
                    organism.setGeneration(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("nNet", targetDepth)) {
                    context.nextToken();
                    organism.setNNet(NNetJsonUnmarshaller.getInstance().unmarshall(context));
                }
                if (context.testExpression("nNetRaw", targetDepth)) {
                    context.nextToken();
                    organism.setNNetRaw(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("name", targetDepth)) {
                    context.nextToken();
                    organism.setName(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("namespace", targetDepth)) {
                    context.nextToken();
                    organism.setNamespace(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("owner_username", targetDepth)) {
                    context.nextToken();
                    organism.setOwnerUsername(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("parentNamespace", targetDepth)) {
                    context.nextToken();
                    organism.setParentNamespace(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("speciesNamespace", targetDepth)) {
                    context.nextToken();
                    organism.setSpeciesNamespace(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("trainingRoomNamespace", targetDepth)) {
                    context.nextToken();
                    organism.setTrainingRoomNamespace(context.getUnmarshaller(String.class).unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return organism;
    }

    private static OrganismJsonUnmarshaller instance;

    public static OrganismJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new OrganismJsonUnmarshaller();
        return instance;
    }
}
