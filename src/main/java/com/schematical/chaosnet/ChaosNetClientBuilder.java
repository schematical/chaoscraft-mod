/**

*/
package com.schematical.chaosnet;

import com.amazonaws.annotation.NotThreadSafe;
import com.amazonaws.client.AwsSyncClientParams;
import com.amazonaws.opensdk.protect.client.SdkSyncClientBuilder;
import com.amazonaws.opensdk.internal.config.ApiGatewayClientConfigurationFactory;
import com.schematical.chaosnet.auth.ChaosnetCognitoUserPool;
import com.amazonaws.util.RuntimeHttpUtils;
import com.amazonaws.Protocol;

import java.net.URI;
import javax.annotation.Generated;

/**
 * Fluent builder for {@link ChaosNet}.
 * 
 * @see ChaosNet#builder
 **/
@NotThreadSafe
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public final class ChaosNetClientBuilder extends SdkSyncClientBuilder<ChaosNetClientBuilder, ChaosNet> {

    private static final URI DEFAULT_ENDPOINT = RuntimeHttpUtils.toUri("chaosnet.schematical.com", Protocol.HTTPS);
    private static final String DEFAULT_REGION = "us-east-1";

    /**
     * Package private constructor - builder should be created via {@link ChaosNet#builder()}
     */
    ChaosNetClientBuilder() {
        super(new ApiGatewayClientConfigurationFactory());
    }

    /**
     * Specify an implementation of the ChaosnetCognitoUserPool to be used during signing
     * 
     * @param requestSigner
     *        the requestSigner implementation to use
     * @return This object for method chaining.
     */
    public ChaosNetClientBuilder signer(ChaosnetCognitoUserPool requestSigner) {
        return signer(requestSigner, ChaosnetCognitoUserPool.class);
    }

    /**
     * Specify an implementation of the ChaosnetCognitoUserPool to be used during signing
     * 
     * @param requestSigner
     *        the requestSigner implementation to use
     */
    public void setSigner(ChaosnetCognitoUserPool requestSigner) {
        signer(requestSigner);
    }

    /**
     * Construct a synchronous implementation of ChaosNet using the current builder configuration.
     *
     * @param params
     *        Current builder configuration represented as a parameter object.
     * @return Fully configured implementation of ChaosNet.
     */
    @Override
    protected ChaosNet build(AwsSyncClientParams params) {
        return new ChaosNetClient(params);
    }

    @Override
    protected URI defaultEndpoint() {
        return DEFAULT_ENDPOINT;
    }

    @Override
    protected String defaultRegion() {
        return DEFAULT_REGION;
    }

}
