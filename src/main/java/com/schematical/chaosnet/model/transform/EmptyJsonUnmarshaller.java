/**

*/
package com.schematical.chaosnet.model.transform;

import java.math.*;

import javax.annotation.Generated;

import com.schematical.chaosnet.model.*;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers.*;
import com.amazonaws.transform.*;

import static com.fasterxml.jackson.core.JsonToken.*;

/**
 * Empty JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class EmptyJsonUnmarshaller implements Unmarshaller<Empty, JsonUnmarshallerContext> {

    public Empty unmarshall(JsonUnmarshallerContext context) throws Exception {
        Empty empty = new Empty();

        return empty;
    }

    private static EmptyJsonUnmarshaller instance;

    public static EmptyJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new EmptyJsonUnmarshaller();
        return instance;
    }
}
