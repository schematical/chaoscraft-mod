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
 * AuthLoginResponse JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class AuthLoginResponseJsonUnmarshaller implements Unmarshaller<AuthLoginResponse, JsonUnmarshallerContext> {

    public AuthLoginResponse unmarshall(JsonUnmarshallerContext context) throws Exception {
        AuthLoginResponse authLoginResponse = new AuthLoginResponse();

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
                if (context.testExpression("accessToken", targetDepth)) {
                    context.nextToken();
                    authLoginResponse.setAccessToken(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("expiration", targetDepth)) {
                    context.nextToken();
                    authLoginResponse.setExpiration(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("idToken", targetDepth)) {
                    context.nextToken();
                    authLoginResponse.setIdToken(context.getUnmarshaller(String.class).unmarshall(context));
                }
                if (context.testExpression("issuedAt", targetDepth)) {
                    context.nextToken();
                    authLoginResponse.setIssuedAt(context.getUnmarshaller(Double.class).unmarshall(context));
                }
                if (context.testExpression("refreshToken", targetDepth)) {
                    context.nextToken();
                    authLoginResponse.setRefreshToken(context.getUnmarshaller(String.class).unmarshall(context));
                }
            } else if (token == END_ARRAY || token == END_OBJECT) {
                if (context.getLastParsedParentElement() == null || context.getLastParsedParentElement().equals(currentParentElement)) {
                    if (context.getCurrentDepth() <= originalDepth)
                        break;
                }
            }
            token = context.nextToken();
        }

        return authLoginResponse;
    }

    private static AuthLoginResponseJsonUnmarshaller instance;

    public static AuthLoginResponseJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new AuthLoginResponseJsonUnmarshaller();
        return instance;
    }
}
