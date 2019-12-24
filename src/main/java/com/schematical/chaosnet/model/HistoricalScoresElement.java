/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.protocol.ProtocolMarshaller;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/__HistoricalScoresElement"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class HistoricalScoresElement implements Serializable, Cloneable, StructuredPojo {

    private Double age;

    private Double genAvg;

    private Double parentAge;

    private Double topAvg;

    private Double topMax;

    /**
     * @param age
     */

    public void setAge(Double age) {
        this.age = age;
    }

    /**
     * @return
     */

    public Double getAge() {
        return this.age;
    }

    /**
     * @param age
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public HistoricalScoresElement age(Double age) {
        setAge(age);
        return this;
    }

    /**
     * @param genAvg
     */

    public void setGenAvg(Double genAvg) {
        this.genAvg = genAvg;
    }

    /**
     * @return
     */

    public Double getGenAvg() {
        return this.genAvg;
    }

    /**
     * @param genAvg
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public HistoricalScoresElement genAvg(Double genAvg) {
        setGenAvg(genAvg);
        return this;
    }

    /**
     * @param parentAge
     */

    public void setParentAge(Double parentAge) {
        this.parentAge = parentAge;
    }

    /**
     * @return
     */

    public Double getParentAge() {
        return this.parentAge;
    }

    /**
     * @param parentAge
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public HistoricalScoresElement parentAge(Double parentAge) {
        setParentAge(parentAge);
        return this;
    }

    /**
     * @param topAvg
     */

    public void setTopAvg(Double topAvg) {
        this.topAvg = topAvg;
    }

    /**
     * @return
     */

    public Double getTopAvg() {
        return this.topAvg;
    }

    /**
     * @param topAvg
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public HistoricalScoresElement topAvg(Double topAvg) {
        setTopAvg(topAvg);
        return this;
    }

    /**
     * @param topMax
     */

    public void setTopMax(Double topMax) {
        this.topMax = topMax;
    }

    /**
     * @return
     */

    public Double getTopMax() {
        return this.topMax;
    }

    /**
     * @param topMax
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public HistoricalScoresElement topMax(Double topMax) {
        setTopMax(topMax);
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
        if (getAge() != null)
            sb.append("Age: ").append(getAge()).append(",");
        if (getGenAvg() != null)
            sb.append("GenAvg: ").append(getGenAvg()).append(",");
        if (getParentAge() != null)
            sb.append("ParentAge: ").append(getParentAge()).append(",");
        if (getTopAvg() != null)
            sb.append("TopAvg: ").append(getTopAvg()).append(",");
        if (getTopMax() != null)
            sb.append("TopMax: ").append(getTopMax());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof HistoricalScoresElement == false)
            return false;
        HistoricalScoresElement other = (HistoricalScoresElement) obj;
        if (other.getAge() == null ^ this.getAge() == null)
            return false;
        if (other.getAge() != null && other.getAge().equals(this.getAge()) == false)
            return false;
        if (other.getGenAvg() == null ^ this.getGenAvg() == null)
            return false;
        if (other.getGenAvg() != null && other.getGenAvg().equals(this.getGenAvg()) == false)
            return false;
        if (other.getParentAge() == null ^ this.getParentAge() == null)
            return false;
        if (other.getParentAge() != null && other.getParentAge().equals(this.getParentAge()) == false)
            return false;
        if (other.getTopAvg() == null ^ this.getTopAvg() == null)
            return false;
        if (other.getTopAvg() != null && other.getTopAvg().equals(this.getTopAvg()) == false)
            return false;
        if (other.getTopMax() == null ^ this.getTopMax() == null)
            return false;
        if (other.getTopMax() != null && other.getTopMax().equals(this.getTopMax()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getAge() == null) ? 0 : getAge().hashCode());
        hashCode = prime * hashCode + ((getGenAvg() == null) ? 0 : getGenAvg().hashCode());
        hashCode = prime * hashCode + ((getParentAge() == null) ? 0 : getParentAge().hashCode());
        hashCode = prime * hashCode + ((getTopAvg() == null) ? 0 : getTopAvg().hashCode());
        hashCode = prime * hashCode + ((getTopMax() == null) ? 0 : getTopMax().hashCode());
        return hashCode;
    }

    @Override
    public HistoricalScoresElement clone() {
        try {
            return (HistoricalScoresElement) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @com.amazonaws.annotation.SdkInternalApi
    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        com.schematical.chaosnet.model.transform.HistoricalScoresElementMarshaller.getInstance().marshall(this, protocolMarshaller);
    }
}
