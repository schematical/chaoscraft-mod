/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * OrganismMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class OrganismMarshaller {

    private static final MarshallingInfo<Double> GENERATION_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("generation").build();
    private static final MarshallingInfo<StructuredPojo> NNET_BINDING = MarshallingInfo.builder(MarshallingType.STRUCTURED)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("nNet").build();
    private static final MarshallingInfo<String> NNETRAW_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("nNetRaw").build();
    private static final MarshallingInfo<String> NAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("name").build();
    private static final MarshallingInfo<String> NAMESPACE_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("namespace").build();
    private static final MarshallingInfo<String> OWNERUSERNAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("owner_username").build();
    private static final MarshallingInfo<String> PARENTNAMESPACE_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("parentNamespace").build();
    private static final MarshallingInfo<String> SPECIESNAMESPACE_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("speciesNamespace").build();
    private static final MarshallingInfo<String> TRAININGROOMNAMESPACE_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("trainingRoomNamespace").build();

    private static final OrganismMarshaller instance = new OrganismMarshaller();

    public static OrganismMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(Organism organism, ProtocolMarshaller protocolMarshaller) {

        if (organism == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(organism.getGeneration(), GENERATION_BINDING);
            protocolMarshaller.marshall(organism.getNNet(), NNET_BINDING);
            protocolMarshaller.marshall(organism.getNNetRaw(), NNETRAW_BINDING);
            protocolMarshaller.marshall(organism.getName(), NAME_BINDING);
            protocolMarshaller.marshall(organism.getNamespace(), NAMESPACE_BINDING);
            protocolMarshaller.marshall(organism.getOwnerUsername(), OWNERUSERNAME_BINDING);
            protocolMarshaller.marshall(organism.getParentNamespace(), PARENTNAMESPACE_BINDING);
            protocolMarshaller.marshall(organism.getSpeciesNamespace(), SPECIESNAMESPACE_BINDING);
            protocolMarshaller.marshall(organism.getTrainingRoomNamespace(), TRAININGROOMNAMESPACE_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
