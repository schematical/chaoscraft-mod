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
 * TrainingRoomSessionMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class TrainingRoomSessionMarshaller {

    private static final MarshallingInfo<String> GENOMENAMESPACE_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("genomeNamespace").build();
    private static final MarshallingInfo<String> NAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("name").build();
    private static final MarshallingInfo<String> NAMESPACE_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("namespace").build();
    private static final MarshallingInfo<List> ORGANISMS_BINDING = MarshallingInfo.builder(MarshallingType.LIST).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("organisms").build();
    private static final MarshallingInfo<String> OWNERUSERNAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("owner_username").build();
    private static final MarshallingInfo<Double> TTL_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("ttl").build();

    private static final TrainingRoomSessionMarshaller instance = new TrainingRoomSessionMarshaller();

    public static TrainingRoomSessionMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(TrainingRoomSession trainingRoomSession, ProtocolMarshaller protocolMarshaller) {

        if (trainingRoomSession == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(trainingRoomSession.getGenomeNamespace(), GENOMENAMESPACE_BINDING);
            protocolMarshaller.marshall(trainingRoomSession.getName(), NAME_BINDING);
            protocolMarshaller.marshall(trainingRoomSession.getNamespace(), NAMESPACE_BINDING);
            protocolMarshaller.marshall(trainingRoomSession.getOrganisms(), ORGANISMS_BINDING);
            protocolMarshaller.marshall(trainingRoomSession.getOwnerUsername(), OWNERUSERNAME_BINDING);
            protocolMarshaller.marshall(trainingRoomSession.getTtl(), TTL_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
