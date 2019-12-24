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
 * NNetRaw JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class NNetRawJsonUnmarshaller implements Unmarshaller<NNetRaw, JsonUnmarshallerContext> {

    public NNetRaw unmarshall(JsonUnmarshallerContext context) throws Exception {
        NNetRaw nNetRaw = new NNetRaw();

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
                if (context.testExpression("nNet", targetDepth)) {
                    context.nextToken();
                    nNetRaw.setNNet(NNetJsonUnmarshaller.getInstance().unmarshall(context));
                }
                if (context.testExpression("nNetRaw", targetDepth)) {
                    context.nextToken();
                    nNetRaw.setNNetRaw(context.getUnmarshaller(String.class).unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return nNetRaw;
    }

    private static NNetRawJsonUnmarshaller instance;

    public static NNetRawJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new NNetRawJsonUnmarshaller();
        return instance;
    }
}
