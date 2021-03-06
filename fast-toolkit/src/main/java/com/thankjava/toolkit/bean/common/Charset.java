package com.thankjava.toolkit.bean.common;

public enum Charset {

    /**
     * utf-8
     */
    utf8("utf-8"),
    /**
     * gb2312
     */
    gb2312("gb2312"),
    /**
     * gbk
     */
    gbk("gbk"),
    /**
     * iso-8859-1
     */
    iso_8859_1("iso-8859-1"),
    ;

    public String charset;

    Charset(String code) {
        this.charset = code;
    }

}
