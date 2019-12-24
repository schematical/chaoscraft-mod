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
 * FitnessRules JSON Unmarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class FitnessRulesJsonUnmarshaller implements Unmarshaller<FitnessRules, JsonUnmarshallerContext> {

    public FitnessRules unmarshall(JsonUnmarshallerContext context) throws Exception {
        FitnessRules fitnessRules = new FitnessRules();

        return fitnessRules;
    }

    private static FitnessRulesJsonUnmarshaller instance;

    public static FitnessRulesJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new FitnessRulesJsonUnmarshaller();
        return instance;
    }
}
