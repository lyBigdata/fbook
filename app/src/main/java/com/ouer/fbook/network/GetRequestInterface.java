package com.ouer.fbook.network;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GetRequestInterface {

    @GET("/")
    Observable<String> getUrl();
}
