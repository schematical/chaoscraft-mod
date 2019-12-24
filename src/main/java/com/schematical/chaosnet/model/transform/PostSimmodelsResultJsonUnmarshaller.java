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
 * PostSimmodelsResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class PostSimmodelsResultJsonUnmarshaller implements Unmarshaller<PostSimmodelsResult, JsonUnmarshallerContext> {

    public PostSimmodelsResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        PostSimmodelsResult postSimmodelsResult = new PostSimmodelsResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return postSimmodelsResult;
        }

        while (true) {
            if (token == null)
                break;

            postSimmodelsResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return postSimmodelsResult;
    }

    private static PostSimmodelsResultJsonUnmarshaller instance;

    public static PostSimmodelsResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new PostSimmodelsResultJsonUnmarshaller();
        return instance;
    }
}
