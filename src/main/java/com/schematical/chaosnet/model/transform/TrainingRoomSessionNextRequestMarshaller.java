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
 * TrainingRoomSessionNextRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class TrainingRoomSessionNextRequestMarshaller {

    private static final MarshallingInfo<Boolean> NNETRAW_BINDING = MarshallingInfo.builder(MarshallingType.BOOLEAN).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("nNetRaw").build();
    private static final MarshallingInfo<List> OBSERVEDATTRIBUTES_BINDING = MarshallingInfo.builder(MarshallingType.LIST)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("observedAttributes").build();
    private static final MarshallingInfo<List> REPORT_BINDING = MarshallingInfo.builder(MarshallingType.LIST).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("report").build();

    private static final TrainingRoomSessionNextRequestMarshaller instance = new TrainingRoomSessionNextRequestMarshaller();

    public static TrainingRoomSessionNextRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(TrainingRoomSessionNextRequest trainingRoomSessionNextRequest, ProtocolMarshaller protocolMarshaller) {

        if (trainingRoomSessionNextRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(trainingRoomSessionNextRequest.getNNetRaw(), NNETRAW_BINDING);
            protocolMarshaller.marshall(trainingRoomSessionNextRequest.getObservedAttributes(), OBSERVEDATTRIBUTES_BINDING);
            protocolMarshaller.marshall(trainingRoomSessionNextRequest.getReport(), REPORT_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
