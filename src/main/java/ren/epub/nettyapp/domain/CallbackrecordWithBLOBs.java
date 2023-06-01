package ren.epub.nettyapp.domain;

import java.io.Serializable;

/**
 * @author 
 * 
 */
public class CallbackrecordWithBLOBs extends Callbackrecord implements Serializable {
    private String reqHeader;

    private String reqBody;

    private static final long serialVersionUID = 1L;

    public String getReqHeader() {
        return reqHeader;
    }

    public void setReqHeader(String reqHeader) {
        this.reqHeader = reqHeader;
    }

    public String getReqBody() {
        return reqBody;
    }

    public void setReqBody(String reqBody) {
        this.reqBody = reqBody;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CallbackrecordWithBLOBs other = (CallbackrecordWithBLOBs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getReqHeader() == null ? other.getReqHeader() == null : this.getReqHeader().equals(other.getReqHeader()))
            && (this.getReqBody() == null ? other.getReqBody() == null : this.getReqBody().equals(other.getReqBody()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getReqHeader() == null) ? 0 : getReqHeader().hashCode());
        result = prime * result + ((getReqBody() == null) ? 0 : getReqBody().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", reqHeader=").append(reqHeader);
        sb.append(", reqBody=").append(reqBody);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}