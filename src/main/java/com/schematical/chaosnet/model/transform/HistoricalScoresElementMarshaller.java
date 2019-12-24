/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * HistoricalScoresElementMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class HistoricalScoresElementMarshaller {

    private static final MarshallingInfo<Double> AGE_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("age").build();
    private static final MarshallingInfo<Double> GENAVG_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("genAvg").build();
    private static final MarshallingInfo<Double> PARENTAGE_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("parentAge").build();
    private static final MarshallingInfo<Double> TOPAVG_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("topAvg").build();
    private static final MarshallingInfo<Double> TOPMAX_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("topMax").build();

    private static final HistoricalScoresElementMarshaller instance = new HistoricalScoresElementMarshaller();

    public static HistoricalScoresElementMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(HistoricalScoresElement historicalScoresElement, ProtocolMarshaller protocolMarshaller) {

        if (historicalScoresElement == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(historicalScoresElement.getAge(), AGE_BINDING);
            protocolMarshaller.marshall(historicalScoresElement.getGenAvg(), GENAVG_BINDING);
            protocolMarshaller.marshall(historicalScoresElement.getParentAge(), PARENTAGE_BINDING);
            protocolMarshaller.marshall(historicalScoresElement.getTopAvg(), TOPAVG_BINDING);
            protocolMarshaller.marshall(historicalScoresElement.getTopMax(), TOPMAX_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
