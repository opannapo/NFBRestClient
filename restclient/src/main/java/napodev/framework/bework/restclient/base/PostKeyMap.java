package napodev.framework.bework.restclient.base;

/**
 * Created by opannapo on 5/14/17.
 */
public class PostKeyMap<K, V, T> {
    private K param;
    private V value;
    private T type;

    public void set(K key, V value, T type) {
        this.param = key;
        this.value = value;
        this.type = type;
    }

    public V getValue() {
        return value;
    }

    public K getKey() {
        return param;
    }

    public T getType() {
        return type;
    }
}
