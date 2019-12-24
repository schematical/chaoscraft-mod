/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.protocol.ProtocolMarshaller;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/AuthLogin" target="_top">AWS API
 *      Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class AuthLogin implements Serializable, Cloneable, StructuredPojo {

    private String password;

    private String username;

    /**
     * @param password
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return
     */

    public String getPassword() {
        return this.password;
    }

    /**
     * @param password
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public AuthLogin password(String password) {
        setPassword(password);
        return this;
    }

    /**
     * @param username
     */

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return
     */

    public String getUsername() {
        return this.username;
    }

    /**
     * @param username
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public AuthLogin username(String username) {
        setUsername(username);
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
        if (getPassword() != null)
            sb.append("Password: ").append(getPassword()).append(",");
        if (getUsername() != null)
            sb.append("Username: ").append(getUsername());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof AuthLogin == false)
            return false;
        AuthLogin other = (AuthLogin) obj;
        if (other.getPassword() == null ^ this.getPassword() == null)
            return false;
        if (other.getPassword() != null && other.getPassword().equals(this.getPassword()) == false)
            return false;
        if (other.getUsername() == null ^ this.getUsername() == null)
            return false;
        if (other.getUsername() != null && other.getUsername().equals(this.getUsername()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getPassword() == null) ? 0 : getPassword().hashCode());
        hashCode = prime * hashCode + ((getUsername() == null) ? 0 : getUsername().hashCode());
        return hashCode;
    }

    @Override
    public AuthLogin clone() {
        try {
            return (AuthLogin) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @com.amazonaws.annotation.SdkInternalApi
    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        com.schematical.chaosnet.model.transform.AuthLoginMarshaller.getInstance().marshall(this, protocolMarshaller);
    }
}
