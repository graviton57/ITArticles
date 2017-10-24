package com.havryliuk.itarticles.data;

import com.havryliuk.itarticles.data.remote.model.ArticleResponse;
import com.havryliuk.itarticles.data.remote.model.DouArticle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor Havrylyuk on 22.10.2017.
 */

public class TestModels {

    public static ArticleResponse getArticleResponseModel(int listSize) {
        List<DouArticle> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(newDouArticleModel());
        }
        ArticleResponse responseModel = new ArticleResponse();

        responseModel.setArticles(list);

        return responseModel;
    }

    public static List<DouArticle> getArticles() {
        List<DouArticle> list = new ArrayList<>();
        list.add(newDouArticle1());
        list.add(newDouArticle2());
        list.add(newDouArticle3());
        return list;
    }

    public static DouArticle newDouArticleModel() {
        return new DouArticle();
    }

    public static DouArticle newDouArticle1(){
        DouArticle article = new DouArticle();
        article.setId(51203);
        article.setTitle("PHP дайджест #9: PHP 7.2, Async PHP, Hacktoberfest, Hack Joomla");
        article.setUrl("https://dou.ua/lenta/digests/php-digest-9/");
        article.setCategory("Ссылки");
        article.setCategoryUrl("https://dou.ua/lenta/digests/");
        article.setAnnouncement("У випуску: що нового в PHP 7.2, вразливість Joomla! 3.7.5, " +
                "Hacktoberfest - долучайтеся до PHP-проектів на GitHub.");
        article.setTags("PHP, PHP дайджест");
        article.setPageviews(698);
        article.setCommentsCount("1");
        article.setImgBig("https://s.dou.ua/img/announces/php2_PFn2U7n_RUS4KYT_TvvT3c6_48D4Zec_855emOF.png");
        article.setImgSmall("https://s.dou.ua/img/announces/120x_php8_DXRUo0Y_rEENIKJ_xUhyd7e_syOqq1M_y8HFgnJ.png");
        article.setAuthorName("Рома Севастьянов");
        article.setAuthorUrl("https://dou.ua/users/roman-sevastyanov/");
        article.setPublished("2017-10-21T10:00:04");
        return article;
    };

    public static DouArticle newDouArticle2(){
        DouArticle article = new DouArticle();
        article.setId(51204);
        article.setTitle("PHP дайджест #9: PHP 7.2, Async PHP, Hacktoberfest, Hack Joomla");
        article.setUrl("https://dou.ua/lenta/digests/php-digest-9/");
        article.setCategory("Ссылки");
        article.setCategoryUrl("https://dou.ua/lenta/digests/");
        article.setAnnouncement("У випуску: що нового в PHP 7.2, вразливість Joomla! 3.7.5" +
                ", Hacktoberfest - долучайтеся до PHP-проектів на GitHub.");
        article.setTags("PHP, PHP дайджест");
        article.setPageviews(98);
        article.setCommentsCount("152");
        article.setImgBig("https://s.dou.ua/img/announces/php2_PFn2U7n_RUS4KYT_TvvT3c6_48D4Zec_855emOF.png");
        article.setImgSmall("https://s.dou.ua/img/announces/120x_php8_DXRUo0Y_rEENIKJ_xUhyd7e_syOqq1M_y8HFgnJ.png");
        article.setAuthorName("Бабай Бабайович");
        article.setAuthorUrl("https://dou.ua/users/roman-sevastyanov/");
        article.setPublished("2017-10-19T10:30:04");
        return article;
    };

    public static DouArticle newDouArticle3(){
        DouArticle article = new DouArticle();
        article.setId(51205);
        article.setTitle("PHP дайджест #9: PHP 7.2, Async PHP, Hacktoberfest, Hack Joomla");
        article.setUrl("https://dou.ua/lenta/digests/php-digest-9/");
        article.setCategory("Ссылки");
        article.setCategoryUrl("https://dou.ua/lenta/digests/");
        article.setAnnouncement("У випуску: що нового в PHP 7.2, вразливість Joomla! 3.7.5," +
                " Hacktoberfest - долучайтеся до PHP-проектів на GitHub.");
        article.setTags("PHP, PHP дайджест");
        article.setPageviews(4698);
        article.setCommentsCount("0");
        article.setImgBig("https://s.dou.ua/img/announces/php2_PFn2U7n_RUS4KYT_TvvT3c6_48D4Zec_855emOF.png");
        article.setImgSmall("https://s.dou.ua/img/announces/120x_php8_DXRUo0Y_rEENIKJ_xUhyd7e_syOqq1M_y8HFgnJ.png");
        article.setAuthorName("Дима Фирцман");
        article.setAuthorUrl("https://dou.ua/users/roman-sevastyanov/");
        article.setPublished("2017-10-20T10:00:04");
        return article;
    };

    // database
    public static List<DouArticle> getDbArticles() {
        List<DouArticle> locations = new ArrayList<>();
        locations.add(newDbArticleModel(51203,"test2"));
        locations.add(newDbArticleModel(51204,"test2"));
        locations.add(newDbArticleModel(51205,"test3"));
        return locations;
    }

    private static DouArticle newDbArticleModel(int id ,String test) {
        DouArticle article = new DouArticle();
        article.setId(id);
        article.setTitle(test);
        return article;
    }


}
