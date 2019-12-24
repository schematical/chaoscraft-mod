/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * ObservedAttributesElementMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class ObservedAttributesElementMarshaller {

    private static final MarshallingInfo<String> ATTRIBUTEID_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("attributeId").build();
    private static final MarshallingInfo<String> ATTRIBUTEVALUE_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("attributeValue").build();
    private static final MarshallingInfo<String> SPECIES_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("species").build();

    private static final ObservedAttributesElementMarshaller instance = new ObservedAttributesElementMarshaller();

    public static ObservedAttributesElementMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(ObservedAttributesElement observedAttributesElement, ProtocolMarshaller protocolMarshaller) {

        if (observedAttributesElement == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(observedAttributesElement.getAttributeId(), ATTRIBUTEID_BINDING);
            protocolMarshaller.marshall(observedAttributesElement.getAttributeValue(), ATTRIBUTEVALUE_BINDING);
            protocolMarshaller.marshall(observedAttributesElement.getSpecies(), SPECIES_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
