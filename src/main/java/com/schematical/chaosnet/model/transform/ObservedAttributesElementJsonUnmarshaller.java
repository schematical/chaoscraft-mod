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
 * ObservedAttributesElement JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class ObservedAttributesElementJsonUnmarshaller implements Unmarshaller<ObservedAttributesElement, JsonUnmarshallerContext> {

    public ObservedAttributesElement unmarshall(JsonUnmarshallerContext context) throws Exception {
        ObservedAttributesElement observedAttributesElement = new ObservedAttributesElement();

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
                if (context.testExpression("attributeId", targetDepth)) {
                    context.nextToken();
                    observedAttributesElement.setAttributeId(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("attributeValue", targetDepth)) {
                    context.nextToken();
                    observedAttributesElement.setAttributeValue(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("species", targetDepth)) {
                    context.nextToken();
                    observedAttributesElement.setSpecies(context.getUnmarshaller(String.class).unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return observedAttributesElement;
    }

    private static ObservedAttributesElementJsonUnmarshaller instance;

    public static ObservedAttributesElementJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new ObservedAttributesElementJsonUnmarshaller();
        return instance;
    }
}
