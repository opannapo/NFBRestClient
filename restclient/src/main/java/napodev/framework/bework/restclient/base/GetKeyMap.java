package napodev.framework.bework.restclient.base;

/**
 * Created by opannapo on 5/14/17.
 */
public class GetKeyMap<K, V> {
    private K param;
    private V value;

    public void set(K key, V value) {
        this.param = key;
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public K getKey() {
        return param;
    }
}
