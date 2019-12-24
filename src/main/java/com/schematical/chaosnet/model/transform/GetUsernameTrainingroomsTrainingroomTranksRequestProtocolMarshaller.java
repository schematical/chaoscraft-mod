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
 * GetUsernameTrainingroomsTrainingroomTranksRequest Marshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class GetUsernameTrainingroomsTrainingroomTranksRequestProtocolMarshaller implements
        Marshaller<Request<GetUsernameTrainingroomsTrainingroomTranksRequest>, GetUsernameTrainingroomsTrainingroomTranksRequest> {

    private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.API_GATEWAY)
            .requestUri("/v0/{username}/trainingrooms/{trainingroom}/tranks").httpMethodName(HttpMethodName.GET).hasExplicitPayloadMember(false)
            .hasPayloadMembers(false).serviceName("ChaosNet").build();

    private final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory;

    public GetUsernameTrainingroomsTrainingroomTranksRequestProtocolMarshaller(
            com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    public Request<GetUsernameTrainingroomsTrainingroomTranksRequest> marshall(
            GetUsernameTrainingroomsTrainingroomTranksRequest getUsernameTrainingroomsTrainingroomTranksRequest) {

        if (getUsernameTrainingroomsTrainingroomTranksRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            final ProtocolRequestMarshaller<GetUsernameTrainingroomsTrainingroomTranksRequest> protocolMarshaller = protocolFactory.createProtocolMarshaller(
                    SDK_OPERATION_BINDING, getUsernameTrainingroomsTrainingroomTranksRequest);

            protocolMarshaller.startMarshalling();
            GetUsernameTrainingroomsTrainingroomTranksRequestMarshaller.getInstance().marshall(getUsernameTrainingroomsTrainingroomTranksRequest,
                    protocolMarshaller);
            return protocolMarshaller.finishMarshalling();
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
