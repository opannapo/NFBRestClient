package napodev.framework.bework.restclient.base;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;

import napodev.framework.bework.restclient.ano.BodyType;
import napodev.framework.bework.restclient.ano.PostParam;
import napodev.framework.bework.restclient.ano.PostRawData;
import napodev.framework.bework.restclient.ano.Url;
import napodev.framework.bework.restclient.utils.Constant;

/**
 * Created by opannapo on 5/14/17.
 */
public abstract class POST extends ArrayList<PostKeyMap<String, String, Integer>> {
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_FILE = 2;
    public static final int TYPE_JSON = 3;
    private String url;
    private int bodyType;

    public POST() {

    }

    public String getUrl() {
        return url;
    }

    public int getBodyType() {
        return bodyType;
    }

    public POST build() {
        if (this.getClass().isAnnotationPresent(BodyType.class)) {
            int value = this.getClass().getAnnotation(BodyType.class).value();
            this.bodyType = value;
            Log.d(Constant.TAG, "isAnnotationPresent BodyType " + value);
        }

        if (this.getClass().isAnnotationPresent(Url.class)) {
            String value = this.getClass().getAnnotation(Url.class).value();
            this.url = value;
            Log.d(Constant.TAG, "isAnnotationPresent Url " + value);
        }

        Field[] field = this.getClass().getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            Field f = field[i];
            if (f.isAnnotationPresent(PostParam.class)) {
                Log.d(Constant.TAG, "isAnnotationPresent @PostParam " + f.getName());
                f.setAccessible(true);
                try {
                    String key = f.getName();
                    String value = String.valueOf(f.get(this));
                    int type = f.getAnnotation(PostParam.class).value();
                    Log.d(Constant.TAG, "isAnnotationPresent PostParam " + key + " value " + value);

                    PostKeyMap<String, String, Integer> gP = new PostKeyMap<>();
                    gP.set(key, value, type);
                    add(gP);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if (f.isAnnotationPresent(PostRawData.class)) {
                Log.d(Constant.TAG, "isAnnotationPresent @PostRawData " + f.getName());
                f.setAccessible(true);
                try {
                    String key = f.getName();
                    String value = String.valueOf(f.get(this));
                    int type = f.getAnnotation(PostRawData.class).value();
                    Log.d(Constant.TAG, "isAnnotationPresent PostRawData " + key + " value " + value);

                    PostKeyMap<String, String, Integer> gP = new PostKeyMap<>();
                    gP.set(key, value, type);
                    add(gP);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d(Constant.TAG, "NOT @PostParam || @PostRawData " + f.getName());
            }
        }
        return this;
    }


}
