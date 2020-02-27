package com.yao.app.proxy.objenesis;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2020-02-25
 */
public class AlonePerson {

    private String name;

    private String desc;

    private AlonePerson() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AlonePerson{");
        sb.append("name='").append(name).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
