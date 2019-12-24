/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/PostAuthLogin" target="_top">AWS
 *      API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class PostAuthLoginRequest extends com.amazonaws.opensdk.BaseRequest implements Serializable, Cloneable {

    private AuthLogin authLogin;

    /**
     * @param authLogin
     */

    public void setAuthLogin(AuthLogin authLogin) {
        this.authLogin = authLogin;
    }

    /**
     * @return
     */

    public AuthLogin getAuthLogin() {
        return this.authLogin;
    }

    /**
     * @param authLogin
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public PostAuthLoginRequest authLogin(AuthLogin authLogin) {
        setAuthLogin(authLogin);
        return this;
    }

    /**
     * Returns a string representation of this object; useful for testing and debugging.
     *
     * @return A string representation of this object.
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAuthLogin() != null)
            sb.append("AuthLogin: ").append(getAuthLogin());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof PostAuthLoginRequest == false)
            return false;
        PostAuthLoginRequest other = (PostAuthLoginRequest) obj;
        if (other.getAuthLogin() == null ^ this.getAuthLogin() == null)
            return false;
        if (other.getAuthLogin() != null && other.getAuthLogin().equals(this.getAuthLogin()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getAuthLogin() == null) ? 0 : getAuthLogin().hashCode());
        return hashCode;
    }

    @Override
    public PostAuthLoginRequest clone() {
        return (PostAuthLoginRequest) super.clone();
    }

    /**
     * Set the configuration for this request.
     *
     * @param sdkRequestConfig
     *        Request configuration.
     * @return This object for method chaining.
     */
    public PostAuthLoginRequest sdkRequestConfig(com.amazonaws.opensdk.SdkRequestConfig sdkRequestConfig) {
        super.sdkRequestConfig(sdkRequestConfig);
        return this;
    }

}
