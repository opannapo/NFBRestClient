package napodev.framework.bework.restclient.base;

/**
 * Created by opannapo on 5/14/17.
 */
public class HeaderKeyMap<K, V> {
    private K name;
    private V value;

    public void set(K key, V value) {
        this.name = key;
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public K getName() {
        return name;
    }
}
