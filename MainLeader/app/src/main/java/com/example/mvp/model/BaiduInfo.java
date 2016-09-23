package com.example.mvp.model;

import java.util.List;

/**
 * Created by ${hcc} on 2016/08/11.
 */
public class BaiduInfo {
    private String pubDate;
    private String link;
    private String title;
    private String source;
    private List<String> imageurls;
    private String url;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<String> imageurls) {
        this.imageurls = imageurls;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
