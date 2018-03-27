package LexicalAnalyser.BaseModels;

//import org.jetbrains.annotations.NotNull;

import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by alyswidan on 19/03/18.
 */
public class MultiMap<K,V> implements Iterable<Entry<K,V>>{
    private Map<K,List<V>> store;


    public MultiMap() {
        this.store = new HashMap<>();
    }

    public int size() {
        return store.size();
    }

    public boolean isEmpty() {
        return store.isEmpty();
    }

    public List<V> get(Object o) {
        return store.get(o);
    }

    public V put(K k, V v) {
        store.putIfAbsent(k,new ArrayList<>());
        store.get(k).add(v);
        return v;
    }

    public Set<Map.Entry<K, List<V>>> entrySet() {
        return store.entrySet();
    }

    public boolean containsKey(Object o) {
        return store.containsKey(o);
    }

    @NotNull
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new MultiMapIterator<>(this);
    }

    @Override
    public String toString() {
        return store.toString();
    }
}
