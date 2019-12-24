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
 * PostAuthSignupResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class PostAuthSignupResultJsonUnmarshaller implements Unmarshaller<PostAuthSignupResult, JsonUnmarshallerContext> {

    public PostAuthSignupResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        PostAuthSignupResult postAuthSignupResult = new PostAuthSignupResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return postAuthSignupResult;
        }

        while (true) {
            if (token == null)
                break;

            postAuthSignupResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return postAuthSignupResult;
    }

    private static PostAuthSignupResultJsonUnmarshaller instance;

    public static PostAuthSignupResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new PostAuthSignupResultJsonUnmarshaller();
        return instance;
    }
}
