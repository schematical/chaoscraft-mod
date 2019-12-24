/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetSimmodelsSimmodel"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetSimmodelsSimmodelRequest extends com.amazonaws.opensdk.BaseRequest implements Serializable, Cloneable {

    private String simmodel;

    /**
     * @param simmodel
     */

    public void setSimmodel(String simmodel) {
        this.simmodel = simmodel;
    }

    /**
     * @return
     */

    public String getSimmodel() {
        return this.simmodel;
    }

    /**
     * @param simmodel
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetSimmodelsSimmodelRequest simmodel(String simmodel) {
        setSimmodel(simmodel);
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
        if (getSimmodel() != null)
            sb.append("Simmodel: ").append(getSimmodel());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof GetSimmodelsSimmodelRequest == false)
            return false;
        GetSimmodelsSimmodelRequest other = (GetSimmodelsSimmodelRequest) obj;
        if (other.getSimmodel() == null ^ this.getSimmodel() == null)
            return false;
        if (other.getSimmodel() != null && other.getSimmodel().equals(this.getSimmodel()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getSimmodel() == null) ? 0 : getSimmodel().hashCode());
        return hashCode;
    }

    @Override
    public GetSimmodelsSimmodelRequest clone() {
        return (GetSimmodelsSimmodelRequest) super.clone();
    }

    /**
     * Set the configuration for this request.
     *
     * @param sdkRequestConfig
     *        Request configuration.
     * @return This object for method chaining.
     */
    public GetSimmodelsSimmodelRequest sdkRequestConfig(com.amazonaws.opensdk.SdkRequestConfig sdkRequestConfig) {
        super.sdkRequestConfig(sdkRequestConfig);
        return this;
    }

}
