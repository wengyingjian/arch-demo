package com.wengyingjian.demo.soa.serial;

import java.io.Serializable;

/**
 * Created by wengyingjian on 16/3/8.
 */
public class TestBean implements Serializable{

    private String name;
    private Integer type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
