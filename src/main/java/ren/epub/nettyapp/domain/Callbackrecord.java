package ren.epub.nettyapp.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 
 */
public class Callbackrecord implements Serializable {
    private Long id;

    private Date createdAt;

    private String reqHeader;

    private String reqBody;

    public Callbackrecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

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
}