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
 * PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest Marshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequestProtocolMarshaller
        implements
        Marshaller<Request<PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest>, PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest> {

    private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.API_GATEWAY)
            .requestUri("/v0/{username}/trainingrooms/{trainingroom}/sessions/{session}/repair").httpMethodName(HttpMethodName.POST)
            .hasExplicitPayloadMember(false).hasPayloadMembers(false).serviceName("ChaosNet").build();

    private final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory;

    public PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequestProtocolMarshaller(
            com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    public Request<PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest> marshall(
            PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest postUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest) {

        if (postUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            final ProtocolRequestMarshaller<PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest> protocolMarshaller = protocolFactory
                    .createProtocolMarshaller(SDK_OPERATION_BINDING, postUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest);

            protocolMarshaller.startMarshalling();
            PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequestMarshaller.getInstance().marshall(
                    postUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest, protocolMarshaller);
            return protocolMarshaller.finishMarshalling();
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
