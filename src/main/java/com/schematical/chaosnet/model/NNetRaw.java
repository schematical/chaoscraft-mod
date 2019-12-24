/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.protocol.ProtocolMarshaller;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/NNetRaw" target="_top">AWS API
 *      Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class NNetRaw implements Serializable, Cloneable, StructuredPojo {

    private NNet nNet;

    private String nNetRaw;

    /**
     * @param nNet
     */

    public void setNNet(NNet nNet) {
        this.nNet = nNet;
    }

    /**
     * @return
     */

    public NNet getNNet() {
        return this.nNet;
    }

    /**
     * @param nNet
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public NNetRaw nNet(NNet nNet) {
        setNNet(nNet);
        return this;
    }

    /**
     * @param nNetRaw
     */

    public void setNNetRaw(String nNetRaw) {
        this.nNetRaw = nNetRaw;
    }

    /**
     * @return
     */

    public String getNNetRaw() {
        return this.nNetRaw;
    }

    /**
     * @param nNetRaw
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public NNetRaw nNetRaw(String nNetRaw) {
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
        if (getNNet() != null)
            sb.append("NNet: ").append(getNNet()).append(",");
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

        if (obj instanceof NNetRaw == false)
            return false;
        NNetRaw other = (NNetRaw) obj;
        if (other.getNNet() == null ^ this.getNNet() == null)
            return false;
        if (other.getNNet() != null && other.getNNet().equals(this.getNNet()) == false)
            return false;
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

        hashCode = prime * hashCode + ((getNNet() == null) ? 0 : getNNet().hashCode());
        hashCode = prime * hashCode + ((getNNetRaw() == null) ? 0 : getNNetRaw().hashCode());
        return hashCode;
    }

    @Override
    public NNetRaw clone() {
        try {
            return (NNetRaw) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @com.amazonaws.annotation.SdkInternalApi
    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        com.schematical.chaosnet.model.transform.NNetRawMarshaller.getInstance().marshall(this, protocolMarshaller);
    }
}
