package utils;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashSet<E> extends AbstractSet<E> implements Set<E> {

    private final ConcurrentHashMap<E, Object> map;

    private static final Object dummyValue = new Object();

    public ConcurrentHashSet(){
        map = new ConcurrentHashMap<>();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Iterator iterator() {
        return map.keySet().iterator();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean add(E e) {
        return map.put(e, ConcurrentHashSet.dummyValue)==null;
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object e) {
        return map.remove(e)== ConcurrentHashSet.dummyValue;
    }

    public boolean putIfAbsent(E e) {
        return map.putIfAbsent(e, ConcurrentHashSet.dummyValue)==null;
    }
}
