package com.shadhinmusiclibrary.fragments.artist;


import com.shadhinmusiclibrary.utils.AppConstantUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class LastFmApiKeyInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url()
                .newBuilder()
                .addQueryParameter("api_key", AppConstantUtils.LAST_FM_API_KEY)
                .addQueryParameter("format", AppConstantUtils.LAST_FM_CONTENT_TYPE_JSON)
                .build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
