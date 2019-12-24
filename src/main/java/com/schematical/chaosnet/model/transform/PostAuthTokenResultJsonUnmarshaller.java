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
 * PostAuthTokenResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class PostAuthTokenResultJsonUnmarshaller implements Unmarshaller<PostAuthTokenResult, JsonUnmarshallerContext> {

    public PostAuthTokenResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        PostAuthTokenResult postAuthTokenResult = new PostAuthTokenResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return postAuthTokenResult;
        }

        while (true) {
            if (token == null)
                break;

            postAuthTokenResult.setAuthLoginResponse(AuthLoginResponseJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return postAuthTokenResult;
    }

    private static PostAuthTokenResultJsonUnmarshaller instance;

    public static PostAuthTokenResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new PostAuthTokenResultJsonUnmarshaller();
        return instance;
    }
}
