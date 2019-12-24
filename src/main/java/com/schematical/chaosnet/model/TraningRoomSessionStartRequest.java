/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.protocol.ProtocolMarshaller;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/TraningRoomSessionStartRequest"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TraningRoomSessionStartRequest implements Serializable, Cloneable, StructuredPojo {

    private Boolean reset;

    /**
     * @param reset
     */

    public void setReset(Boolean reset) {
        this.reset = reset;
    }

    /**
     * @return
     */

    public Boolean getReset() {
        return this.reset;
    }

    /**
     * @param reset
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TraningRoomSessionStartRequest reset(Boolean reset) {
        setReset(reset);
        return this;
    }

    /**
     * @return
     */

    public Boolean isReset() {
        return this.reset;
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
        if (getReset() != null)
            sb.append("Reset: ").append(getReset());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof TraningRoomSessionStartRequest == false)
            return false;
        TraningRoomSessionStartRequest other = (TraningRoomSessionStartRequest) obj;
        if (other.getReset() == null ^ this.getReset() == null)
            return false;
        if (other.getReset() != null && other.getReset().equals(this.getReset()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getReset() == null) ? 0 : getReset().hashCode());
        return hashCode;
    }

    @Override
    public TraningRoomSessionStartRequest clone() {
        try {
            return (TraningRoomSessionStartRequest) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @com.amazonaws.annotation.SdkInternalApi
    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        com.schematical.chaosnet.model.transform.TraningRoomSessionStartRequestMarshaller.getInstance().marshall(this, protocolMarshaller);
    }
}
