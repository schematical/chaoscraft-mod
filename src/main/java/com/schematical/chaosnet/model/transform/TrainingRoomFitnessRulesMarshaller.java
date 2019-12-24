/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * TrainingRoomFitnessRulesMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class TrainingRoomFitnessRulesMarshaller {

    private static final MarshallingInfo<StructuredPojo> FITNESSRULES_BINDING = MarshallingInfo.builder(MarshallingType.STRUCTURED)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("fitnessRules").build();
    private static final MarshallingInfo<String> FITNESSRULESRAW_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("fitnessRulesRaw").build();

    private static final TrainingRoomFitnessRulesMarshaller instance = new TrainingRoomFitnessRulesMarshaller();

    public static TrainingRoomFitnessRulesMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(TrainingRoomFitnessRules trainingRoomFitnessRules, ProtocolMarshaller protocolMarshaller) {

        if (trainingRoomFitnessRules == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(trainingRoomFitnessRules.getFitnessRules(), FITNESSRULES_BINDING);
            protocolMarshaller.marshall(trainingRoomFitnessRules.getFitnessRulesRaw(), FITNESSRULESRAW_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
