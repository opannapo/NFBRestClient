package napodev.framework.bework.restclient.base;

import okhttp3.Call;

/**
 * Created by opannapo on 5/22/17.
 */
public abstract class ExecuteCallback {
    public void onUploadProgress(Object o) {
    }

    public void onDownloadProgress(Object o) {
    }

    public abstract void onResponse(String url, Call call, Object o);

    public abstract void onFailure(String url, Call call, Object e);
}
