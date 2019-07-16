package com.example.waz.Api;

import com.example.waz.UI.Bean.KnowArticleBean;
import com.example.waz.UI.Bean.NavigationBean;
import com.example.waz.UI.Bean.ProjectArticleBean;
import com.example.waz.UI.Bean.ProjectBean;
import com.example.waz.UI.Bean.SearchBean;
import com.example.waz.UI.Bean.SecondBean;
import com.example.waz.UI.Bean.UserBean;
import com.example.waz.UI.Bean.WanArticleBean;
import com.example.waz.UI.Bean.WanBannerBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServer {

    String mWanUrl="https://www.wanandroid.com/";

    @GET("article/list/{page}/json")
    Observable<WanArticleBean> getWanArticle(@Path("page")int page);
    @GET("article/list/{page}/json")
    Observable<KnowArticleBean> getKnowArticle(@Path("page")int page, @Query("cid")int id);

    @GET("banner/json")
    Observable<WanBannerBean>getBanner();


    @GET("navi/json")
    Observable<NavigationBean>getNavigation();

    @GET("navi/json")
    Observable<NavigationBean>getNavigationArticle();

    @GET("project/tree/json")
    Observable<ProjectBean>getProject();
    @GET("tree/json")
    Observable<SecondBean>getSecond();

    @GET("project/list/{page}/json?")
    Observable<ProjectArticleBean>getProjectArticle(@Path("page")int page, @Query("cid")int cid);

    @GET("hotkey/json")
    Observable<SearchBean>getSearch();



    //登录
    //https://www.wanandroid.com/user/login
    @FormUrlEncoded
    @POST("user/login")
    Observable<UserBean> requestLogin(@Field("username") String username, @Field("password") String password);

    //注册
    //https://www.wanandroid.com/user/register
    @FormUrlEncoded
    @POST("user/register")
    Observable<UserBean> requestregister(@Field("username") String username, @Field("password") String password,@Field("repassword")String repassword);

    //https://www.wanandroid.com/user/logout/json
    //推出登录
    @GET("user/logout/json")
    Observable<UserBean> unLogin();

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<ProjectArticleBean>postSearch(@Path("page") int page,@Field("k")String k);
}
