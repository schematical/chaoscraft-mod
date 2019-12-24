/**

*/
package com.schematical.chaosnet.model.transform;

import javax.annotation.Generated;

import com.amazonaws.SdkClientException;
import com.amazonaws.Request;

import com.amazonaws.http.HttpMethodName;
import com.schematical.chaosnet.model.*;
import com.amazonaws.transform.Marshaller;

import com.amazonaws.protocol.*;
import com.amazonaws.annotation.SdkInternalApi;

/**
 * PutUsernameTrainingroomsTrainingroomRequest Marshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class PutUsernameTrainingroomsTrainingroomRequestProtocolMarshaller implements
        Marshaller<Request<PutUsernameTrainingroomsTrainingroomRequest>, PutUsernameTrainingroomsTrainingroomRequest> {

    private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.API_GATEWAY)
            .requestUri("/v0/{username}/trainingrooms/{trainingroom}").httpMethodName(HttpMethodName.PUT).hasExplicitPayloadMember(false)
            .hasPayloadMembers(false).serviceName("ChaosNet").build();

    private final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory;

    public PutUsernameTrainingroomsTrainingroomRequestProtocolMarshaller(com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    public Request<PutUsernameTrainingroomsTrainingroomRequest> marshall(PutUsernameTrainingroomsTrainingroomRequest putUsernameTrainingroomsTrainingroomRequest) {

        if (putUsernameTrainingroomsTrainingroomRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            final ProtocolRequestMarshaller<PutUsernameTrainingroomsTrainingroomRequest> protocolMarshaller = protocolFactory.createProtocolMarshaller(
                    SDK_OPERATION_BINDING, putUsernameTrainingroomsTrainingroomRequest);

            protocolMarshaller.startMarshalling();
            PutUsernameTrainingroomsTrainingroomRequestMarshaller.getInstance().marshall(putUsernameTrainingroomsTrainingroomRequest, protocolMarshaller);
            return protocolMarshaller.finishMarshalling();
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
