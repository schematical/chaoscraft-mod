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
 * GetSimmodelsSimmodelResult JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetSimmodelsSimmodelResultJsonUnmarshaller implements Unmarshaller<GetSimmodelsSimmodelResult, JsonUnmarshallerContext> {

    public GetSimmodelsSimmodelResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        GetSimmodelsSimmodelResult getSimmodelsSimmodelResult = new GetSimmodelsSimmodelResult();

        int originalDepth = context.getCurrentDepth();
        String currentParentElement = context.getCurrentParentElement();
        int targetDepth = originalDepth + 1;

        JsonToken token = context.getCurrentToken();
        if (token == null)
            token = context.nextToken();
        if (token == VALUE_NULL) {
            return getSimmodelsSimmodelResult;
        }

        while (true) {
            if (token == null)
                break;

            getSimmodelsSimmodelResult.setEmpty(EmptyJsonUnmarshaller.getInstance().unmarshall(context));
            token = context.nextToken();
        }

        return getSimmodelsSimmodelResult;
    }

    private static GetSimmodelsSimmodelResultJsonUnmarshaller instance;

    public static GetSimmodelsSimmodelResultJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new GetSimmodelsSimmodelResultJsonUnmarshaller();
        return instance;
    }
}
