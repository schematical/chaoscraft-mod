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
 * PostTrainingroomsResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class PostTrainingroomsResultJsonUnmarshaller implements Unmarshaller<PostTrainingroomsResult, JsonUnmarshallerContext> {

    public PostTrainingroomsResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        PostTrainingroomsResult postTrainingroomsResult = new PostTrainingroomsResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return postTrainingroomsResult;
        }

        while (true) {
            if (token == null)
                break;

            postTrainingroomsResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return postTrainingroomsResult;
    }

    private static PostTrainingroomsResultJsonUnmarshaller instance;

    public static PostTrainingroomsResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new PostTrainingroomsResultJsonUnmarshaller();
        return instance;
    }
}
