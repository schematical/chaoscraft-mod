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
 * AuthTokenRequest JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class AuthTokenRequestJsonUnmarshaller implements Unmarshaller<AuthTokenRequest, JsonUnmarshallerContext> {

    public AuthTokenRequest unmarshall(JsonUnmarshallerContext context) throws Exception {
        AuthTokenRequest authTokenRequest = new AuthTokenRequest();

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
                if (context.testExpression("refreshToken", targetDepth)) {
                    context.nextToken();
                    authTokenRequest.setRefreshToken(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("username", targetDepth)) {
                    context.nextToken();
                    authTokenRequest.setUsername(context.getUnmarshaller(String.class).unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return authTokenRequest;
    }

    private static AuthTokenRequestJsonUnmarshaller instance;

    public static AuthTokenRequestJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new AuthTokenRequestJsonUnmarshaller();
        return instance;
    }
}
