/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.protocol.ProtocolMarshaller;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/AuthLoginResponse"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class AuthLoginResponse implements Serializable, Cloneable, StructuredPojo {

    private String accessToken;

    private Double expiration;

    private String idToken;

    private Double issuedAt;

    private String refreshToken;

    /**
     * @param accessToken
     */

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return
     */

    public String getAccessToken() {
        return this.accessToken;
    }

    /**
     * @param accessToken
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public AuthLoginResponse accessToken(String accessToken) {
        setAccessToken(accessToken);
        return this;
    }

    /**
     * @param expiration
     */

    public void setExpiration(Double expiration) {
        this.expiration = expiration;
    }

    /**
     * @return
     */

    public Double getExpiration() {
        return this.expiration;
    }

    /**
     * @param expiration
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public AuthLoginResponse expiration(Double expiration) {
        setExpiration(expiration);
        return this;
    }

    /**
     * @param idToken
     */

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    /**
     * @return
     */

    public String getIdToken() {
        return this.idToken;
    }

    /**
     * @param idToken
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public AuthLoginResponse idToken(String idToken) {
        setIdToken(idToken);
        return this;
    }

    /**
     * @param issuedAt
     */

    public void setIssuedAt(Double issuedAt) {
        this.issuedAt = issuedAt;
    }

    /**
     * @return
     */

    public Double getIssuedAt() {
        return this.issuedAt;
    }

    /**
     * @param issuedAt
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public AuthLoginResponse issuedAt(Double issuedAt) {
        setIssuedAt(issuedAt);
        return this;
    }

    /**
     * @param refreshToken
     */

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * @return
     */

    public String getRefreshToken() {
        return this.refreshToken;
    }

    /**
     * @param refreshToken
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public AuthLoginResponse refreshToken(String refreshToken) {
        setRefreshToken(refreshToken);
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
        if (getAccessToken() != null)
            sb.append("AccessToken: ").append(getAccessToken()).append(",");
        if (getExpiration() != null)
            sb.append("Expiration: ").append(getExpiration()).append(",");
        if (getIdToken() != null)
            sb.append("IdToken: ").append(getIdToken()).append(",");
        if (getIssuedAt() != null)
            sb.append("IssuedAt: ").append(getIssuedAt()).append(",");
        if (getRefreshToken() != null)
            sb.append("RefreshToken: ").append(getRefreshToken());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof AuthLoginResponse == false)
            return false;
        AuthLoginResponse other = (AuthLoginResponse) obj;
        if (other.getAccessToken() == null ^ this.getAccessToken() == null)
            return false;
        if (other.getAccessToken() != null && other.getAccessToken().equals(this.getAccessToken()) == false)
            return false;
        if (other.getExpiration() == null ^ this.getExpiration() == null)
            return false;
        if (other.getExpiration() != null && other.getExpiration().equals(this.getExpiration()) == false)
            return false;
        if (other.getIdToken() == null ^ this.getIdToken() == null)
            return false;
        if (other.getIdToken() != null && other.getIdToken().equals(this.getIdToken()) == false)
            return false;
        if (other.getIssuedAt() == null ^ this.getIssuedAt() == null)
            return false;
        if (other.getIssuedAt() != null && other.getIssuedAt().equals(this.getIssuedAt()) == false)
            return false;
        if (other.getRefreshToken() == null ^ this.getRefreshToken() == null)
            return false;
        if (other.getRefreshToken() != null && other.getRefreshToken().equals(this.getRefreshToken()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getAccessToken() == null) ? 0 : getAccessToken().hashCode());
        hashCode = prime * hashCode + ((getExpiration() == null) ? 0 : getExpiration().hashCode());
        hashCode = prime * hashCode + ((getIdToken() == null) ? 0 : getIdToken().hashCode());
        hashCode = prime * hashCode + ((getIssuedAt() == null) ? 0 : getIssuedAt().hashCode());
        hashCode = prime * hashCode + ((getRefreshToken() == null) ? 0 : getRefreshToken().hashCode());
        return hashCode;
    }

    @Override
    public AuthLoginResponse clone() {
        try {
            return (AuthLoginResponse) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @com.amazonaws.annotation.SdkInternalApi
    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        com.schematical.chaosnet.model.transform.AuthLoginResponseMarshaller.getInstance().marshall(this, protocolMarshaller);
    }
}
