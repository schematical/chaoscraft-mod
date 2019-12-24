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
 * GetChaospixelTagsTagTrainingdatasRequest Marshaller
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
@SdkInternalApi
public class GetChaospixelTagsTagTrainingdatasRequestProtocolMarshaller implements
        Marshaller<Request<GetChaospixelTagsTagTrainingdatasRequest>, GetChaospixelTagsTagTrainingdatasRequest> {

    private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.API_GATEWAY)
            .requestUri("/v0/chaospixel/tags/{tag}/trainingdatas").httpMethodName(HttpMethodName.GET).hasExplicitPayloadMember(false).hasPayloadMembers(false)
            .serviceName("ChaosNet").build();

    private final com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory;

    public GetChaospixelTagsTagTrainingdatasRequestProtocolMarshaller(com.amazonaws.opensdk.protect.protocol.ApiGatewayProtocolFactoryImpl protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    public Request<GetChaospixelTagsTagTrainingdatasRequest> marshall(GetChaospixelTagsTagTrainingdatasRequest getChaospixelTagsTagTrainingdatasRequest) {

        if (getChaospixelTagsTagTrainingdatasRequest == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            final ProtocolRequestMarshaller<GetChaospixelTagsTagTrainingdatasRequest> protocolMarshaller = protocolFactory.createProtocolMarshaller(
                    SDK_OPERATION_BINDING, getChaospixelTagsTagTrainingdatasRequest);

            protocolMarshaller.startMarshalling();
            GetChaospixelTagsTagTrainingdatasRequestMarshaller.getInstance().marshall(getChaospixelTagsTagTrainingdatasRequest, protocolMarshaller);
            return protocolMarshaller.finishMarshalling();
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

}
