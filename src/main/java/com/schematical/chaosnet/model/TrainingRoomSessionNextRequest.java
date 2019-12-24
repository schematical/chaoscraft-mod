/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.protocol.ProtocolMarshaller;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/TrainingRoomSessionNextRequest"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class TrainingRoomSessionNextRequest implements Serializable, Cloneable, StructuredPojo {

    private Boolean nNetRaw;

    private java.util.List<ObservedAttributesElement> observedAttributes;

    private java.util.List<TrainingRoomSessionNextReport> report;

    /**
     * @param nNetRaw
     */

    public void setNNetRaw(Boolean nNetRaw) {
        this.nNetRaw = nNetRaw;
    }

    /**
     * @return
     */

    public Boolean getNNetRaw() {
        return this.nNetRaw;
    }

    /**
     * @param nNetRaw
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomSessionNextRequest nNetRaw(Boolean nNetRaw) {
        setNNetRaw(nNetRaw);
        return this;
    }

    /**
     * @return
     */

    public Boolean isNNetRaw() {
        return this.nNetRaw;
    }

    /**
     * @return
     */

    public java.util.List<ObservedAttributesElement> getObservedAttributes() {
        return observedAttributes;
    }

    /**
     * @param observedAttributes
     */

    public void setObservedAttributes(java.util.Collection<ObservedAttributesElement> observedAttributes) {
        if (observedAttributes == null) {
            this.observedAttributes = null;
            return;
        }

        this.observedAttributes = new java.util.ArrayList<ObservedAttributesElement>(observedAttributes);
    }

    /**
     * <p>
     * <b>NOTE:</b> This method appends the values to the existing list (if any). Use
     * {@link #setObservedAttributes(java.util.Collection)} or {@link #withObservedAttributes(java.util.Collection)} if
     * you want to override the existing values.
     * </p>
     * 
     * @param observedAttributes
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomSessionNextRequest observedAttributes(ObservedAttributesElement... observedAttributes) {
        if (this.observedAttributes == null) {
            setObservedAttributes(new java.util.ArrayList<ObservedAttributesElement>(observedAttributes.length));
        }
        for (ObservedAttributesElement ele : observedAttributes) {
            this.observedAttributes.add(ele);
        }
        return this;
    }

    /**
     * @param observedAttributes
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomSessionNextRequest observedAttributes(java.util.Collection<ObservedAttributesElement> observedAttributes) {
        setObservedAttributes(observedAttributes);
        return this;
    }

    /**
     * @return
     */

    public java.util.List<TrainingRoomSessionNextReport> getReport() {
        return report;
    }

    /**
     * @param report
     */

    public void setReport(java.util.Collection<TrainingRoomSessionNextReport> report) {
        if (report == null) {
            this.report = null;
            return;
        }

        this.report = new java.util.ArrayList<TrainingRoomSessionNextReport>(report);
    }

    /**
     * <p>
     * <b>NOTE:</b> This method appends the values to the existing list (if any). Use
     * {@link #setReport(java.util.Collection)} or {@link #withReport(java.util.Collection)} if you want to override the
     * existing values.
     * </p>
     * 
     * @param report
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomSessionNextRequest report(TrainingRoomSessionNextReport... report) {
        if (this.report == null) {
            setReport(new java.util.ArrayList<TrainingRoomSessionNextReport>(report.length));
        }
        for (TrainingRoomSessionNextReport ele : report) {
            this.report.add(ele);
        }
        return this;
    }

    /**
     * @param report
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public TrainingRoomSessionNextRequest report(java.util.Collection<TrainingRoomSessionNextReport> report) {
        setReport(report);
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
            sb.append("NNetRaw: ").append(getNNetRaw()).append(",");
        if (getObservedAttributes() != null)
            sb.append("ObservedAttributes: ").append(getObservedAttributes()).append(",");
        if (getReport() != null)
            sb.append("Report: ").append(getReport());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof TrainingRoomSessionNextRequest == false)
            return false;
        TrainingRoomSessionNextRequest other = (TrainingRoomSessionNextRequest) obj;
        if (other.getNNetRaw() == null ^ this.getNNetRaw() == null)
            return false;
        if (other.getNNetRaw() != null && other.getNNetRaw().equals(this.getNNetRaw()) == false)
            return false;
        if (other.getObservedAttributes() == null ^ this.getObservedAttributes() == null)
            return false;
        if (other.getObservedAttributes() != null && other.getObservedAttributes().equals(this.getObservedAttributes()) == false)
            return false;
        if (other.getReport() == null ^ this.getReport() == null)
            return false;
        if (other.getReport() != null && other.getReport().equals(this.getReport()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getNNetRaw() == null) ? 0 : getNNetRaw().hashCode());
        hashCode = prime * hashCode + ((getObservedAttributes() == null) ? 0 : getObservedAttributes().hashCode());
        hashCode = prime * hashCode + ((getReport() == null) ? 0 : getReport().hashCode());
        return hashCode;
    }

    @Override
    public TrainingRoomSessionNextRequest clone() {
        try {
            return (TrainingRoomSessionNextRequest) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @com.amazonaws.annotation.SdkInternalApi
    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        com.schematical.chaosnet.model.transform.TrainingRoomSessionNextRequestMarshaller.getInstance().marshall(this, protocolMarshaller);
    }
}
