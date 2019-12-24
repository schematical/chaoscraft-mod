/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * DeleteUsernameTrainingroomsTrainingroomRequestMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class DeleteUsernameTrainingroomsTrainingroomRequestMarshaller {

    private static final MarshallingInfo<String> TRAININGROOM_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("trainingroom").build();
    private static final MarshallingInfo<String> USERNAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PATH)
            .marshallLocationName("username").build();

    private static final DeleteUsernameTrainingroomsTrainingroomRequestMarshaller instance = new DeleteUsernameTrainingroomsTrainingroomRequestMarshaller();

    public static DeleteUsernameTrainingroomsTrainingroomRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(DeleteUsernameTrainingroomsTrainingroomRequest deleteUsernameTrainingroomsTrainingroomRequest, ProtocolMarshaller protocolMarshaller) {

        if (deleteUsernameTrainingroomsTrainingroomRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(deleteUsernameTrainingroomsTrainingroomRequest.getTrainingroom(), TRAININGROOM_BINDING);
            protocolMarshaller.marshall(deleteUsernameTrainingroomsTrainingroomRequest.getUsername(), USERNAME_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
