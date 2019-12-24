/**

*/
package com.schematical.chaosnet.model;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * 
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/chaosnet-2019-09-20T20:35:34Z/GetChaospixelTagsTagTrainingdatas"
 *      target="_top">AWS API Documentation</a>
 */
@Generated("com.amazonaws:aws-java-sdk-code-generator")
public class GetChaospixelTagsTagTrainingdatasRequest extends com.amazonaws.opensdk.BaseRequest implements Serializable, Cloneable {

    private String tag;

    /**
     * @param tag
     */

    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return
     */

    public String getTag() {
        return this.tag;
    }

    /**
     * @param tag
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetChaospixelTagsTagTrainingdatasRequest tag(String tag) {
        setTag(tag);
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
        if (getTag() != null)
            sb.append("Tag: ").append(getTag());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof GetChaospixelTagsTagTrainingdatasRequest == false)
            return false;
        GetChaospixelTagsTagTrainingdatasRequest other = (GetChaospixelTagsTagTrainingdatasRequest) obj;
        if (other.getTag() == null ^ this.getTag() == null)
            return false;
        if (other.getTag() != null && other.getTag().equals(this.getTag()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getTag() == null) ? 0 : getTag().hashCode());
        return hashCode;
    }

    @Override
    public GetChaospixelTagsTagTrainingdatasRequest clone() {
        return (GetChaospixelTagsTagTrainingdatasRequest) super.clone();
    }

    /**
     * Set the configuration for this request.
     *
     * @param sdkRequestConfig
     *        Request configuration.
     * @return This object for method chaining.
     */
    public GetChaospixelTagsTagTrainingdatasRequest sdkRequestConfig(com.amazonaws.opensdk.SdkRequestConfig sdkRequestConfig) {
        super.sdkRequestConfig(sdkRequestConfig);
        return this;
    }

}
