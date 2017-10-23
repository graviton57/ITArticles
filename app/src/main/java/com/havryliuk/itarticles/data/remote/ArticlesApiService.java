package com.havryliuk.itarticles.data.remote;

import com.havryliuk.itarticles.data.remote.model.ArticleResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

public interface ArticlesApiService {

    /**
     * All Articles
     * Request http://api.dou.ua/articles/
     * @return Return’s a list of all articles
     */
    @GET("articles")
    Observable<ArticleResponse> getArticles();

    /**
     * Get Articles by options
     * Request http://api.dou.ua/articles/?category=News&limit=20&offset=20&tag=android
     * @param  options  Map<String, String>  of query parameters:
     * options.put("limit", 20) limit (int) – maximum number of results
     * options.put("offset", 20) offset (int) – number of results to displace or skip over
     * options.put("category", "Новости") category (String) – category name
     * options.put("author", "Gavrilyuk") author (String) – author name
     * options.put("tag", "android") tag (String) – tag name
     * options.put("date_from", "2017-07-24") date_from (String) – date from >
     * options.put("date_to", "2017-07-24") date_to (String) – date to <
     * @return Return list of articles based on optional filter arguments
     */
    @GET("articles")
    Observable<ArticleResponse> getArticles(@QueryMap Map<String, String> options);
}
