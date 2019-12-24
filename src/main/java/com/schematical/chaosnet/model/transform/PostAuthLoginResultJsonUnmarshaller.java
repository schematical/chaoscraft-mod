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
 * PostAuthLoginResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class PostAuthLoginResultJsonUnmarshaller implements Unmarshaller<PostAuthLoginResult, JsonUnmarshallerContext> {

    public PostAuthLoginResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        PostAuthLoginResult postAuthLoginResult = new PostAuthLoginResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return postAuthLoginResult;
        }

        while (true) {
            if (token == null)
                break;

            postAuthLoginResult.setAuthLoginResponse(AuthLoginResponseJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return postAuthLoginResult;
    }

    private static PostAuthLoginResultJsonUnmarshaller instance;

    public static PostAuthLoginResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new PostAuthLoginResultJsonUnmarshaller();
        return instance;
    }
}
