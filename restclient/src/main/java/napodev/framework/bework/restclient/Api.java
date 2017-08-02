package napodev.framework.bework.restclient;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import napodev.framework.bework.restclient.ano.BodyType;
import napodev.framework.bework.restclient.base.ExecuteCallback;
import napodev.framework.bework.restclient.base.GET;
import napodev.framework.bework.restclient.base.POST;
import napodev.framework.bework.restclient.utils.Constant;
import napodev.framework.bework.restclient.utils.TimeoutAttr;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by opannapo on 5/14/17.
 */
public abstract class Api {
    String url = null;
    String authorization;
    OkHttpClient client;
    Request request;
    Request.Builder reqBuilder;

    long timeStart;
    long timeEnd;


    public Api() {
        if (isLogEnable()) Log.d(Constant.TAG, "Construct");
        reqBuilder = new Request.Builder();
        client = new OkHttpClient.Builder()
                .connectTimeout(getConnectTimeout().getValue(), getConnectTimeout().getTimeUnit())
                .writeTimeout(getWriteTimeout().getValue(), getWriteTimeout().getTimeUnit())
                .readTimeout(getReadTimeout().getValue(), getReadTimeout().getTimeUnit())
                .build();
    }

    public Api authorization(String authorization) {
        if (isLogEnable()) Log.d(Constant.TAG, "authorization : " + authorization);
        this.authorization = authorization;
        this.reqBuilder.header("Authorization", authorization);

        return this;
    }

    public Api prepareGet(GET params) {
        url = getBaseUrl() + params.getUrl();
        if (isLogEnable()) Log.d(Constant.TAG, "prepareGet (Param) " + url);

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        for (int i = 0; i < params.size(); i++) {
            if (isLogEnable()) {
                Log.d(Constant.TAG, "prepareGet " + url + " (Param " + i + ")" + params.get(i).getKey() + "::" + params.get(i).getValue());
            }
            urlBuilder.addQueryParameter(params.get(i).getKey(), params.get(i).getValue());
        }

        reqBuilder.url(urlBuilder.build().toString());
        return this;
    }

    public Api prepareGet(String url) {
        this.url = getBaseUrl() + url;
        if (isLogEnable()) Log.d(Constant.TAG, "prepareGet (Url) " + url);
        reqBuilder.url(this.url);
        Log.d(Constant.TAG, "prepareGet " + url);
        return this;
    }

    public Api preparePost(POST params) {
        url = getBaseUrl() + params.getUrl();
        if (isLogEnable()) Log.d(Constant.TAG, "preparePost (Param) " + url);

        if (params.getBodyType() == BodyType.POST_DEFAULT) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            for (int i = 0; i < params.size(); i++) {
                if (params.get(i).getType() == POST.TYPE_TEXT) {
                    formBodyBuilder.add(params.get(i).getKey(), params.get(i).getValue());
                }

                if (isLogEnable()) {
                    Log.d(Constant.TAG, "preparePost " + url + " (Param " + i + ")" + params.get(i).getKey() + "::" + params.get(i).getValue());
                }
            }
            reqBuilder.method("POST", formBodyBuilder.build());

        } else if (params.getBodyType() == BodyType.POST_RAW) {
            RequestBody body = null;
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");

            if (params.get(0).getType() == POST.TYPE_JSON) {
                body = RequestBody.create(JSON, params.get(0).getValue());
            } else if (params.get(0).getType() == POST.TYPE_TEXT) {
                body = RequestBody.create(TEXT, params.get(0).getValue());
            }

            if (isLogEnable()) {
                Log.d(Constant.TAG, "preparePost " + url + " (RAW) " + params.get(0).getKey() + "::" + params.get(0).getValue());
            }

            reqBuilder.method("POST", body);
        } else if (params.getBodyType() == BodyType.POST_MULTIPART) {
            MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (int i = 0; i < params.size(); i++) {
                if (params.get(i).getType() == POST.TYPE_TEXT) {
                    multipartBodyBuilder.addFormDataPart(params.get(i).getKey(), params.get(i).getValue());
                } else {
                    File file = new File(params.get(i).getValue());
                    if (file.exists()) {
                        multipartBodyBuilder.addFormDataPart(params.get(i).getKey(), file.getName(), RequestBody.create(null, file));
                    } else {
                        Log.d(Constant.TAG, "file not exist ");
                    }
                }

                if (isLogEnable()) {
                    Log.d(Constant.TAG, "preparePost " + url + " (Param " + i + ")" + params.get(i).getKey() + "::" + params.get(i).getValue());
                }
            }
            reqBuilder.method("POST", multipartBodyBuilder.build());
        }

        reqBuilder.url(url);

        return this;
    }

    public void execute(@NonNull final ExecuteCallback callback) {
        if (isLogEnable()) Log.d(Constant.TAG, "execute " + url);

        if (callback == null) {
            request = null;
            return;
        }

        request = reqBuilder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d(Constant.TAG, "onFailure " + url + " " + e.toString());
                callback.onFailure(url, call, e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String resString = new String(response.body().bytes());
                if (!response.isSuccessful()) {
                    Log.d(Constant.TAG, "onResponse NOT response.isSuccessful() " + url + " " + resString);
                    callback.onFailure(url, call, resString);
                } else {
                    Log.d(Constant.TAG, "onResponse response.isSuccessful() " + url + " " + resString);
                    callback.onResponse(url, call, resString);
                }
            }
        });
    }

    public abstract String getBaseUrl();

    public abstract TimeoutAttr getConnectTimeout();

    public abstract TimeoutAttr getWriteTimeout();

    public abstract TimeoutAttr getReadTimeout();

    public abstract boolean isLogEnable();

}
