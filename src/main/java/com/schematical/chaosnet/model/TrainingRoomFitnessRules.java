/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.protocol.ProtocolMarshaller;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/TrainingRoomFitnessRules"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TrainingRoomFitnessRules implements Serializable, Cloneable, StructuredPojo {

    private FitnessRules fitnessRules;

    private String fitnessRulesRaw;

    /**
     * @param fitnessRules
     */

    public void setFitnessRules(FitnessRules fitnessRules) {
        this.fitnessRules = fitnessRules;
    }

    /**
     * @return
     */

    public FitnessRules getFitnessRules() {
        return this.fitnessRules;
    }

    /**
     * @param fitnessRules
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomFitnessRules fitnessRules(FitnessRules fitnessRules) {
        setFitnessRules(fitnessRules);
        return this;
    }

    /**
     * @param fitnessRulesRaw
     */

    public void setFitnessRulesRaw(String fitnessRulesRaw) {
        this.fitnessRulesRaw = fitnessRulesRaw;
    }

    /**
     * @return
     */

    public String getFitnessRulesRaw() {
        return this.fitnessRulesRaw;
    }

    /**
     * @param fitnessRulesRaw
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomFitnessRules fitnessRulesRaw(String fitnessRulesRaw) {
        setFitnessRulesRaw(fitnessRulesRaw);
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
        if (getFitnessRules() != null)
            sb.append("FitnessRules: ").append(getFitnessRules()).append(",");
        if (getFitnessRulesRaw() != null)
            sb.append("FitnessRulesRaw: ").append(getFitnessRulesRaw());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof TrainingRoomFitnessRules == false)
            return false;
        TrainingRoomFitnessRules other = (TrainingRoomFitnessRules) obj;
        if (other.getFitnessRules() == null ^ this.getFitnessRules() == null)
            return false;
        if (other.getFitnessRules() != null && other.getFitnessRules().equals(this.getFitnessRules()) == false)
            return false;
        if (other.getFitnessRulesRaw() == null ^ this.getFitnessRulesRaw() == null)
            return false;
        if (other.getFitnessRulesRaw() != null && other.getFitnessRulesRaw().equals(this.getFitnessRulesRaw()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getFitnessRules() == null) ? 0 : getFitnessRules().hashCode());
        hashCode = prime * hashCode + ((getFitnessRulesRaw() == null) ? 0 : getFitnessRulesRaw().hashCode());
        return hashCode;
    }

    @Override
    public TrainingRoomFitnessRules clone() {
        try {
            return (TrainingRoomFitnessRules) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @com.amazonaws.annotation.SdkInternalApi
    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        com.schematical.chaosnet.model.transform.TrainingRoomFitnessRulesMarshaller.getInstance().marshall(this, protocolMarshaller);
    }
}
