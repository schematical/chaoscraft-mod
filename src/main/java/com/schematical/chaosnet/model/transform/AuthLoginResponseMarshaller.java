/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.schematical.chaosnet.model.*;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * AuthLoginResponseMarshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class AuthLoginResponseMarshaller {

    private static final MarshallingInfo<String> ACCESSTOKEN_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("accessToken").build();
    private static final MarshallingInfo<Double> EXPIRATION_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("expiration").build();
    private static final MarshallingInfo<String> IDTOKEN_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("idToken").build();
    private static final MarshallingInfo<Double> ISSUEDAT_BINDING = MarshallingInfo.builder(MarshallingType.DOUBLE).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("issuedAt").build();
    private static final MarshallingInfo<String> REFRESHTOKEN_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
            .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("refreshToken").build();

    private static final AuthLoginResponseMarshaller instance = new AuthLoginResponseMarshaller();

    public static AuthLoginResponseMarshaller getInstance() {
        return instance;
    }

    /**
     * Marshall the given parameter object.
     */
    public void marshall(AuthLoginResponse authLoginResponse, ProtocolMarshaller protocolMarshaller) {

        if (authLoginResponse == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            protocolMarshaller.marshall(authLoginResponse.getAccessToken(), ACCESSTOKEN_BINDING);
            protocolMarshaller.marshall(authLoginResponse.getExpiration(), EXPIRATION_BINDING);
            protocolMarshaller.marshall(authLoginResponse.getIdToken(), IDTOKEN_BINDING);
            protocolMarshaller.marshall(authLoginResponse.getIssuedAt(), ISSUEDAT_BINDING);
            protocolMarshaller.marshall(authLoginResponse.getRefreshToken(), REFRESHTOKEN_BINDING);
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
