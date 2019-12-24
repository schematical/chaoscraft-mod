/**

*/
package com.schematical.chaosnet.model.transform;

import java.util.List;
import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * TrainingRoomSessionNextResponseMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class TrainingRoomSessionNextResponseMarshaller {

    private static final MarshallingInfo<List> ORGANISMS_BINDING = MarshallingInfo.builder(MarshallingType.LIST).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("organisms").build();
    private static final MarshallingInfo<List> SPECIES_BINDING = MarshallingInfo.builder(MarshallingType.LIST).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("species").build();
    private static final MarshallingInfo<StructuredPojo> STATS_BINDING = MarshallingInfo.builder(MarshallingType.STRUCTURED)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("stats").build();

    private static final TrainingRoomSessionNextResponseMarshaller instance = new TrainingRoomSessionNextResponseMarshaller();

    public static TrainingRoomSessionNextResponseMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(TrainingRoomSessionNextResponse trainingRoomSessionNextResponse, ProtocolMarshaller protocolMarshaller) {

        if (trainingRoomSessionNextResponse == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(trainingRoomSessionNextResponse.getOrganisms(), ORGANISMS_BINDING);
            protocolMarshaller.marshall(trainingRoomSessionNextResponse.getSpecies(), SPECIES_BINDING);
            protocolMarshaller.marshall(trainingRoomSessionNextResponse.getStats(), STATS_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
