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
 * PostUsernameTrainingdatasResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class PostUsernameTrainingdatasResultJsonUnmarshaller implements Unmarshaller<PostUsernameTrainingdatasResult, JsonUnmarshallerContext> {

    public PostUsernameTrainingdatasResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        PostUsernameTrainingdatasResult postUsernameTrainingdatasResult = new PostUsernameTrainingdatasResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return postUsernameTrainingdatasResult;
        }

        while (true) {
            if (token == null)
                break;

            postUsernameTrainingdatasResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return postUsernameTrainingdatasResult;
    }

    private static PostUsernameTrainingdatasResultJsonUnmarshaller instance;

    public static PostUsernameTrainingdatasResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new PostUsernameTrainingdatasResultJsonUnmarshaller();
        return instance;
    }
}
