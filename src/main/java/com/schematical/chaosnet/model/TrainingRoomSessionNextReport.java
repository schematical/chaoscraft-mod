/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.protocol.ProtocolMarshaller;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/TrainingRoomSessionNextReport"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TrainingRoomSessionNextReport implements Serializable, Cloneable, StructuredPojo {

    private String namespace;

    private Double score;

    /**
     * @param namespace
     */

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * @return
     */

    public String getNamespace() {
        return this.namespace;
    }

    /**
     * @param namespace
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomSessionNextReport namespace(String namespace) {
        setNamespace(namespace);
        return this;
    }

    /**
     * @param score
     */

    public void setScore(Double score) {
        this.score = score;
    }

    /**
     * @return
     */

    public Double getScore() {
        return this.score;
    }

    /**
     * @param score
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomSessionNextReport score(Double score) {
        setScore(score);
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
        if (getNamespace() != null)
            sb.append("Namespace: ").append(getNamespace()).append(",");
        if (getScore() != null)
            sb.append("Score: ").append(getScore());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof TrainingRoomSessionNextReport == false)
            return false;
        TrainingRoomSessionNextReport other = (TrainingRoomSessionNextReport) obj;
        if (other.getNamespace() == null ^ this.getNamespace() == null)
            return false;
        if (other.getNamespace() != null && other.getNamespace().equals(this.getNamespace()) == false)
            return false;
        if (other.getScore() == null ^ this.getScore() == null)
            return false;
        if (other.getScore() != null && other.getScore().equals(this.getScore()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getNamespace() == null) ? 0 : getNamespace().hashCode());
        hashCode = prime * hashCode + ((getScore() == null) ? 0 : getScore().hashCode());
        return hashCode;
    }

    @Override
    public TrainingRoomSessionNextReport clone() {
        try {
            return (TrainingRoomSessionNextReport) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @com.amazonaws.annotation.SdkInternalApi
    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        com.schematical.chaosnet.model.transform.TrainingRoomSessionNextReportMarshaller.getInstance().marshall(this, protocolMarshaller);
    }
}
