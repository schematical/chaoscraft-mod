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
public class PostAuthLoginResult extends com.amazonaws.opensdk.BaseResult implements Serializable, Cloneable {

    private AuthLoginResponse authLoginResponse;

    /**
     * @param authLoginResponse
     */

    public void setAuthLoginResponse(AuthLoginResponse authLoginResponse) {
        this.authLoginResponse = authLoginResponse;
    }

    /**
     * @return
     */

    public AuthLoginResponse getAuthLoginResponse() {
        return this.authLoginResponse;
    }

    /**
     * @param authLoginResponse
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public PostAuthLoginResult authLoginResponse(AuthLoginResponse authLoginResponse) {
        setAuthLoginResponse(authLoginResponse);
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
        if (getAuthLoginResponse() != null)
            sb.append("AuthLoginResponse: ").append(getAuthLoginResponse());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof PostAuthLoginResult == false)
            return false;
        PostAuthLoginResult other = (PostAuthLoginResult) obj;
        if (other.getAuthLoginResponse() == null ^ this.getAuthLoginResponse() == null)
            return false;
        if (other.getAuthLoginResponse() != null && other.getAuthLoginResponse().equals(this.getAuthLoginResponse()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getAuthLoginResponse() == null) ? 0 : getAuthLoginResponse().hashCode());
        return hashCode;
    }

    @Override
    public PostAuthLoginResult clone() {
        try {
            return (PostAuthLoginResult) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

}
