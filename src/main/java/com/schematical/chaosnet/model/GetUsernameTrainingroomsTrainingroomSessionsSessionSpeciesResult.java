/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * 
 * @see <a
 *      href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetUsernameTrainingroomsTrainingroomSessionsSessionSpecies"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult extends com.amazonaws.opensdk.BaseResult implements Serializable, Cloneable {

    private java.util.List<TaxonomicRank> taxonomicRankCollection;

    /**
     * @return
     */

    public java.util.List<TaxonomicRank> getTaxonomicRankCollection() {
        return taxonomicRankCollection;
    }

    /**
     * @param taxonomicRankCollection
     */

    public void setTaxonomicRankCollection(java.util.Collection<TaxonomicRank> taxonomicRankCollection) {
        if (taxonomicRankCollection == null) {
            this.taxonomicRankCollection = null;
            return;
        }

        this.taxonomicRankCollection = new java.util.ArrayList<TaxonomicRank>(taxonomicRankCollection);
    }

    /**
     * <p>
     * <b>NOTE:</b> This method appends the values to the existing list (if any). Use
     * {@link #setTaxonomicRankCollection(java.util.Collection)} or
     * {@link #withTaxonomicRankCollection(java.util.Collection)} if you want to override the existing values.
     * </p>
     * 
     * @param taxonomicRankCollection
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult taxonomicRankCollection(TaxonomicRank... taxonomicRankCollection) {
        if (this.taxonomicRankCollection == null) {
            setTaxonomicRankCollection(new java.util.ArrayList<TaxonomicRank>(taxonomicRankCollection.length));
        }
        for (TaxonomicRank ele : taxonomicRankCollection) {
            this.taxonomicRankCollection.add(ele);
        }
        return this;
    }

    /**
     * @param taxonomicRankCollection
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult taxonomicRankCollection(java.util.Collection<TaxonomicRank> taxonomicRankCollection) {
        setTaxonomicRankCollection(taxonomicRankCollection);
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
        if (getTaxonomicRankCollection() != null)
            sb.append("TaxonomicRankCollection: ").append(getTaxonomicRankCollection());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult == false)
            return false;
        GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult other = (GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult) obj;
        if (other.getTaxonomicRankCollection() == null ^ this.getTaxonomicRankCollection() == null)
            return false;
        if (other.getTaxonomicRankCollection() != null && other.getTaxonomicRankCollection().equals(this.getTaxonomicRankCollection()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getTaxonomicRankCollection() == null) ? 0 : getTaxonomicRankCollection().hashCode());
        return hashCode;
    }

    @Override
    public GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult clone() {
        try {
            return (GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

}
