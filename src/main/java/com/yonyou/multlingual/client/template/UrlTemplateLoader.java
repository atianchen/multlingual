package com.yonyou.multlingual.client.template;

import java.net.URL;

/**
 * Freemarker Url TemplateLoader
 * @author yonyou.jensen
 **/
public class UrlTemplateLoader extends freemarker.cache.URLTemplateLoader {

    private String baseUrl;

    public UrlTemplateLoader(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    @Override
    protected URL getURL(String arg){
        URL url = null;
        try {
            URL test = this.getClass().getClassLoader().getResource("");
            url = this.getClass().getClassLoader().getResource(baseUrl+arg);
        } catch (Exception s) {
            s.printStackTrace();
        }
        return url;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}