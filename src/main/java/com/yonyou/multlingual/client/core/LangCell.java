package com.yonyou.multlingual.client.core;

public class LangCell {

    private String code;

    private int seq;

    public LangCell(String code,int seq)
    {
        this.code = code;
        this.seq = seq;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
