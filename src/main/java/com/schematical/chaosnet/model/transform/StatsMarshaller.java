/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * StatsMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class StatsMarshaller {

    private static final MarshallingInfo<Double> GENPROGRESS_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("genProgress").build();
    private static final MarshallingInfo<Double> ORGSREPORTEDSOFAR_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("orgsReportedSoFar").build();
    private static final MarshallingInfo<Double> ORGSSPAWNEDSOFAR_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("orgsSpawnedSoFar").build();
    private static final MarshallingInfo<Double> TOTALORGSPERGEN_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("totalOrgsPerGen").build();

    private static final StatsMarshaller instance = new StatsMarshaller();

    public static StatsMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(Stats stats, ProtocolMarshaller protocolMarshaller) {

        if (stats == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(stats.getGenProgress(), GENPROGRESS_BINDING);
            protocolMarshaller.marshall(stats.getOrgsReportedSoFar(), ORGSREPORTEDSOFAR_BINDING);
            protocolMarshaller.marshall(stats.getOrgsSpawnedSoFar(), ORGSSPAWNEDSOFAR_BINDING);
            protocolMarshaller.marshall(stats.getTotalOrgsPerGen(), TOTALORGSPERGEN_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
