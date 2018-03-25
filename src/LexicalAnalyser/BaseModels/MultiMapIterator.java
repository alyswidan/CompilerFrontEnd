package LexicalAnalyser.BaseModels;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by alyswidan on 19/03/18.
 */
public class MultiMapIterator<K,V> implements Iterator<Entry<K,V>> {

    private Iterator<Map.Entry<K,Set<V>>> Entryiterator;
    private Iterator<V> listIterator;
    private Map.Entry<K,Set<V>> currentEntry;
    private MultiMap<K,V> map;

    public MultiMapIterator(MultiMap<K,V> multiMap) {
        Entryiterator = multiMap.entrySet().iterator();
        this.map = multiMap;
    }

    @Override
    public boolean hasNext() {
        return (listIterator!= null && listIterator.hasNext())
                || (Entryiterator != null && Entryiterator.hasNext());
    }

    @Override
    public Entry<K,V> next() {
        if(listIterator == null || !listIterator.hasNext()){
            currentEntry = Entryiterator.next();
            listIterator = currentEntry.getValue().iterator();
        }
        return new Entry<>(currentEntry.getKey(),listIterator.next());
    }


}
