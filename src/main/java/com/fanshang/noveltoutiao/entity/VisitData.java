package com.fanshang.noveltoutiao.entity;

import java.io.Serializable;

public class VisitData implements Serializable {

    private String visitIp;
    private String visitUrl;
    private String visitUA;

    public String getVisitIp() {
        return visitIp;
    }

    public void setVisitIp(String visitIp) {
        this.visitIp = visitIp;
    }

    public String getVisitUrl() {
        return visitUrl;
    }

    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }

    public String getVisitUA() {
        return visitUA;
    }

    public void setVisitUA(String visitUA) {
        this.visitUA = visitUA;
    }
}
