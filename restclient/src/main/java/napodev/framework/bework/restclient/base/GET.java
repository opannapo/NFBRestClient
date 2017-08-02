package napodev.framework.bework.restclient.base;


import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;

import napodev.framework.bework.restclient.ano.GetParam;
import napodev.framework.bework.restclient.ano.Url;
import napodev.framework.bework.restclient.utils.Constant;

/**
 * Created by opannapo on 5/14/17.
 */
public abstract class GET extends ArrayList<GetKeyMap<String, String>> {
    private String url;

    public GET() {

    }

    public GET build() {
        if (this.getClass().isAnnotationPresent(Url.class)) {
            String value = this.getClass().getAnnotation(Url.class).value();
            this.url = value;
            Log.d(Constant.TAG, "isAnnotationPresent Url " + value);
        }

        Field[] field = this.getClass().getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            Field f = field[i];
            if (f.isAnnotationPresent(GetParam.class)) {
                Log.d(Constant.TAG, "isAnnotationPresent @GetParam " + f.getName());
                f.setAccessible(true);
                try {
                    String key = f.getName();
                    String value = (String) f.get(this);
                    Log.d(Constant.TAG, "isAnnotationPresent GetParam " + key + " value " + value);

                    GetKeyMap<String, String> gP = new GetKeyMap<>();
                    gP.set(key, value);
                    add(gP);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d(Constant.TAG, "NOT @GetParam " + f.getName());
            }
        }
        return this;
    }

    public String getUrl() {
        return url;
    }
}
