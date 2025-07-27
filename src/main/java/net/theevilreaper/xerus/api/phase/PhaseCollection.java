package net.theevilreaper.xerus.api.phase;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 03/01/2020 21:04
 *
 * General Phase which is constructed of other Phases
 * This Class has no Phase logic built-in
 */
public abstract class PhaseCollection<T extends Phase> extends Phase implements Iterable<T>, List<T> {

    protected final List<T> phaseList;

    protected PhaseCollection(@NotNull String name) {
        super(name);
        phaseList = new LinkedList<>();
    }

    protected PhaseCollection(@NotNull String name, @NotNull List<T> phaseList) {
        super(name);
        this.phaseList = phaseList;
    }

    @Override
    public int size() {
        return phaseList.size();
    }

    @Override
    public boolean isEmpty() {
        return phaseList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return phaseList.contains(o);
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return phaseList.toArray();
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] array) {
        return phaseList.toArray(array);
    }

    @Override
    public boolean add(T phase) {
        return phaseList.add(phase);
    }

    @Override
    public boolean remove(Object o) {
        return phaseList.remove(o);
    }

    @Override
    public T remove(int index) {
        return phaseList.remove(index);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return phaseList.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        return phaseList.addAll(c);
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c) {
        return phaseList.addAll(c);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return phaseList.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return phaseList.retainAll(c);
    }

    @Override
    public void clear() {
        phaseList.clear();
    }

    @Override
    public T get(int index) {
        return phaseList.get(index);
    }

    @Override
    public T set(int index, T element) {
        return phaseList.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        phaseList.add(index, element);
    }

    @Override
    public int indexOf(Object o) {
        return phaseList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return phaseList.lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        return phaseList.listIterator();
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return phaseList.listIterator(index);
    }

    @NotNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return phaseList.subList(fromIndex, toIndex);
    }

    /**
     * Retrieve an Iterator.
     * @return - Iterator containing this StateHolder's states.
     */
    @Override
    @NotNull
    public Iterator<T> iterator() {
        return phaseList.iterator();
    }
}
