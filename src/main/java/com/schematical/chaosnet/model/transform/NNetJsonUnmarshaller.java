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
 * NNet JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class NNetJsonUnmarshaller implements Unmarshaller<NNet, JsonUnmarshallerContext> {

    public NNet unmarshall(JsonUnmarshallerContext context) throws Exception {
        NNet nNet = new NNet();

        return nNet;
    }

    private static NNetJsonUnmarshaller instance;

    public static NNetJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new NNetJsonUnmarshaller();
        return instance;
    }
}
