package napodev.framework.bework.restclient.ano;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by opannapo on 5/14/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BodyType {
    int POST_DEFAULT = 1;
    int POST_MULTIPART = 2;
    int POST_RAW = 3;

    int value();
}
