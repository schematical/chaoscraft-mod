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
 * TaxonomicRankMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class TaxonomicRankMarshaller {

    private static final MarshallingInfo<Double> AGE_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("age").build();
    private static final MarshallingInfo<Double> CHILDRENREPORTEDTHISGEN_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("childrenReportedThisGen").build();
    private static final MarshallingInfo<Double> CHILDRENSPAWNEDTHISGEN_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("childrenSpawnedThisGen").build();
    private static final MarshallingInfo<Double> CURRSCORE_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("currScore").build();
    private static final MarshallingInfo<Double> GENERATION_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("generation").build();
    private static final MarshallingInfo<Double> GENSSINCELASTIMPROVEMENT_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("gensSinceLastImprovement").build();
    private static final MarshallingInfo<Double> HIGHSCORE_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("highScore").build();
    private static final MarshallingInfo<List> HISTORICALSCORES_BINDING = MarshallingInfo.builder(MarshallingType.LIST)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("historicalScores").build();
    private static final MarshallingInfo<String> HISTORICALSCORESRAW_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("historicalScoresRaw").build();
    private static final MarshallingInfo<String> NAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("name").build();
    private static final MarshallingInfo<String> NAMESPACE_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("namespace").build();
    private static final MarshallingInfo<String> OBSERVEDATTRIBUTESRAW_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("observedAttributesRaw").build();
    private static final MarshallingInfo<String> OWNERUSERNAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("owner_username").build();
    private static final MarshallingInfo<String> PARENTNAMESPACE_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("parentNamespace").build();
    private static final MarshallingInfo<String> TRAININGROOMNAMESPACE_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("trainingRoomNamespace").build();
    private static final MarshallingInfo<String> TRANKCLASS_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("trankClass").build();

    private static final TaxonomicRankMarshaller instance = new TaxonomicRankMarshaller();

    public static TaxonomicRankMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(TaxonomicRank taxonomicRank, ProtocolMarshaller protocolMarshaller) {

        if (taxonomicRank == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(taxonomicRank.getAge(), AGE_BINDING);
            protocolMarshaller.marshall(taxonomicRank.getChildrenReportedThisGen(), CHILDRENREPORTEDTHISGEN_BINDING);
            protocolMarshaller.marshall(taxonomicRank.getChildrenSpawnedThisGen(), CHILDRENSPAWNEDTHISGEN_BINDING);
            protocolMarshaller.marshall(taxonomicRank.getCurrScore(), CURRSCORE_BINDING);
            protocolMarshaller.marshall(taxonomicRank.getGeneration(), GENERATION_BINDING);
            protocolMarshaller.marshall(taxonomicRank.getGensSinceLastImprovement(), GENSSINCELASTIMPROVEMENT_BINDING);
            protocolMarshaller.marshall(taxonomicRank.getHighScore(), HIGHSCORE_BINDING);
            protocolMarshaller.marshall(taxonomicRank.getHistoricalScores(), HISTORICALSCORES_BINDING);
            protocolMarshaller.marshall(taxonomicRank.getHistoricalScoresRaw(), HISTORICALSCORESRAW_BINDING);
            protocolMarshaller.marshall(taxonomicRank.getName(), NAME_BINDING);
            protocolMarshaller.marshall(taxonomicRank.getNamespace(), NAMESPACE_BINDING);
            protocolMarshaller.marshall(taxonomicRank.getObservedAttributesRaw(), OBSERVEDATTRIBUTESRAW_BINDING);
            protocolMarshaller.marshall(taxonomicRank.getOwnerUsername(), OWNERUSERNAME_BINDING);
            protocolMarshaller.marshall(taxonomicRank.getParentNamespace(), PARENTNAMESPACE_BINDING);
            protocolMarshaller.marshall(taxonomicRank.getTrainingRoomNamespace(), TRAININGROOMNAMESPACE_BINDING);
            protocolMarshaller.marshall(taxonomicRank.getTrankClass(), TRANKCLASS_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
