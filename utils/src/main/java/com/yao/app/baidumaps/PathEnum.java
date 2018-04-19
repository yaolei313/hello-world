package com.yao.app.baidumaps;

/**
 * Created by yaolei02 on 2017/5/9.
 */
public enum PathEnum {
    GEO_CODING_API("/geocoder/v2/"),
    PLACE_SUGGEST_API("/place/v2/suggestion/");

    private String relativePath;

    public String getRelativePath() {
        return relativePath;
    }

    PathEnum(String relativePath) {
        this.relativePath = relativePath;
    }
}
