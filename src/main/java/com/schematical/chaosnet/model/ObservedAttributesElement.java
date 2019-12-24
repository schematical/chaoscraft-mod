/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.amazonaws.protocol.StructuredPojo;
import com.amazonaws.protocol.ProtocolMarshaller;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/__ObservedAttributesElement"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class ObservedAttributesElement implements Serializable, Cloneable, StructuredPojo {

    private String attributeId;

    private String attributeValue;

    private String species;

    /**
     * @param attributeId
     */

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    /**
     * @return
     */

    public String getAttributeId() {
        return this.attributeId;
    }

    /**
     * @param attributeId
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public ObservedAttributesElement attributeId(String attributeId) {
        setAttributeId(attributeId);
        return this;
    }

    /**
     * @param attributeValue
     */

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    /**
     * @return
     */

    public String getAttributeValue() {
        return this.attributeValue;
    }

    /**
     * @param attributeValue
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public ObservedAttributesElement attributeValue(String attributeValue) {
        setAttributeValue(attributeValue);
        return this;
    }

    /**
     * @param species
     */

    public void setSpecies(String species) {
        this.species = species;
    }

    /**
     * @return
     */

    public String getSpecies() {
        return this.species;
    }

    /**
     * @param species
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public ObservedAttributesElement species(String species) {
        setSpecies(species);
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
        if (getAttributeId() != null)
            sb.append("AttributeId: ").append(getAttributeId()).append(",");
        if (getAttributeValue() != null)
            sb.append("AttributeValue: ").append(getAttributeValue()).append(",");
        if (getSpecies() != null)
            sb.append("Species: ").append(getSpecies());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof ObservedAttributesElement == false)
            return false;
        ObservedAttributesElement other = (ObservedAttributesElement) obj;
        if (other.getAttributeId() == null ^ this.getAttributeId() == null)
            return false;
        if (other.getAttributeId() != null && other.getAttributeId().equals(this.getAttributeId()) == false)
            return false;
        if (other.getAttributeValue() == null ^ this.getAttributeValue() == null)
            return false;
        if (other.getAttributeValue() != null && other.getAttributeValue().equals(this.getAttributeValue()) == false)
            return false;
        if (other.getSpecies() == null ^ this.getSpecies() == null)
            return false;
        if (other.getSpecies() != null && other.getSpecies().equals(this.getSpecies()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getAttributeId() == null) ? 0 : getAttributeId().hashCode());
        hashCode = prime * hashCode + ((getAttributeValue() == null) ? 0 : getAttributeValue().hashCode());
        hashCode = prime * hashCode + ((getSpecies() == null) ? 0 : getSpecies().hashCode());
        return hashCode;
    }

    @Override
    public ObservedAttributesElement clone() {
        try {
            return (ObservedAttributesElement) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @com.amazonaws.annotation.SdkInternalApi
    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        com.schematical.chaosnet.model.transform.ObservedAttributesElementMarshaller.getInstance().marshall(this, protocolMarshaller);
    }
}
