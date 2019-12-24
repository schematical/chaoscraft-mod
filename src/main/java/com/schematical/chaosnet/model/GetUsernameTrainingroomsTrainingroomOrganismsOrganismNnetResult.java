/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * 
 * @see <a
 *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnet"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult extends com.amazonaws.opensdk.BaseResult implements Serializable, Cloneable {

    private NNetRaw nNetRaw;

    /**
     * @param nNetRaw
     */

    public void setNNetRaw(NNetRaw nNetRaw) {
        this.nNetRaw = nNetRaw;
    }

    /**
     * @return
     */

    public NNetRaw getNNetRaw() {
        return this.nNetRaw;
    }

    /**
     * @param nNetRaw
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult nNetRaw(NNetRaw nNetRaw) {
        setNNetRaw(nNetRaw);
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
        if (getNNetRaw() != null)
            sb.append("NNetRaw: ").append(getNNetRaw());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult == false)
            return false;
        GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult other = (GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult) obj;
        if (other.getNNetRaw() == null ^ this.getNNetRaw() == null)
            return false;
        if (other.getNNetRaw() != null && other.getNNetRaw().equals(this.getNNetRaw()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getNNetRaw() == null) ? 0 : getNNetRaw().hashCode());
        return hashCode;
    }

    @Override
    public GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult clone() {
        try {
            return (GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

}
