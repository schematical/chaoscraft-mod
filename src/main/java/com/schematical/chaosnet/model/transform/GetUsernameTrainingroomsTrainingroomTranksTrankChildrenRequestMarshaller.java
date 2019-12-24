/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * GetUsernameTrainingroomsTrainingroomTranksTrankChildrenRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class GetUsernameTrainingroomsTrainingroomTranksTrankChildrenRequestMarshaller {

    private static final MarshallingInfo<String> TRAININGROOM_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("trainingroom").build();
    private static final MarshallingInfo<String> TRANK_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("trank").build();
    private static final MarshallingInfo<String> USERNAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("username").build();

    private static final GetUsernameTrainingroomsTrainingroomTranksTrankChildrenRequestMarshaller instance = new GetUsernameTrainingroomsTrainingroomTranksTrankChildrenRequestMarshaller();

    public static GetUsernameTrainingroomsTrainingroomTranksTrankChildrenRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(GetUsernameTrainingroomsTrainingroomTranksTrankChildrenRequest getUsernameTrainingroomsTrainingroomTranksTrankChildrenRequest,
            ProtocolMarshaller protocolMarshaller) {

        if (getUsernameTrainingroomsTrainingroomTranksTrankChildrenRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(getUsernameTrainingroomsTrainingroomTranksTrankChildrenRequest.getTrainingroom(), TRAININGROOM_BINDING);
            protocolMarshaller.marshall(getUsernameTrainingroomsTrainingroomTranksTrankChildrenRequest.getTrank(), TRANK_BINDING);
            protocolMarshaller.marshall(getUsernameTrainingroomsTrainingroomTranksTrankChildrenRequest.getUsername(), USERNAME_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
