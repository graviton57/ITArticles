package com.havryliuk.itarticles.data.remote.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */
/*
{
        "count": 3720,
        "next": "https://api.dou.ua/articles/?limit=20&offset=20",
        "previous": null,
        "results": [
*/

public class ArticleResponse {

    private int count;
    private String next;
    private String previous;
    @SerializedName("results")
    List<DouArticle> articles;

    public ArticleResponse() {
    }

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<DouArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<DouArticle> articles) {
        this.articles = articles;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
