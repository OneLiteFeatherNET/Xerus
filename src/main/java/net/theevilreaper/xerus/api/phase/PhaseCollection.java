package net.theevilreaper.xerus.api.phase;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A {@code PhaseCollection} represents a composite {@link Phase} that consists of a sequence or collection of other {@link Phase} instances.
 * <p>
 * This class provides no logic for advancing or managing the flow of phases.
 * Instead, it offers structural support for storing and managing a list of phases, delegating all {@link List} operations to an internal list.
 * </p>
 * <p>
 * Subclasses such as {@link LinearPhaseSeries} or {@link CyclicPhaseSeries} implement control logic on top of this collection.
 * </p>
 *
 * @param <T> the type of {@link Phase} contained in this collection
 *
 * @version 1.0.1
 * @since 03/01/2020 21:04
 * @author Patrick Zdarsky / Rxcki
 */
public abstract class PhaseCollection<T extends Phase> extends Phase implements Iterable<T>, List<T> {

    /** The list that stores the phases in this collection. */
    protected final List<T> phaseList;

    /**
     * Constructs a new {@code PhaseCollection} with the given name.
     *
     * @param name the name of this phase collection
     */
    protected PhaseCollection(@NotNull String name) {
        super(name);
        this.phaseList = new LinkedList<>();
    }

    /**
     * Constructs a new {@code PhaseCollection} with the given name and pre-defined list of phases.
     *
     * @param name      the name of this phase collection
     * @param phaseList the initial phases to include in the collection
     */
    protected PhaseCollection(@NotNull String name, @NotNull List<T> phaseList) {
        super(name);
        this.phaseList = phaseList;
    }

    // Delegate all List interface methods to the internal phaseList

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return phaseList.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return phaseList.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object o) {
        return phaseList.contains(o);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public Object[] toArray() {
        return phaseList.toArray();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] array) {
        return phaseList.toArray(array);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(T phase) {
        return phaseList.add(phase);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        return phaseList.remove(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T remove(int index) {
        return phaseList.remove(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return phaseList.containsAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        return phaseList.addAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c) {
        return phaseList.addAll(index, c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return phaseList.removeAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return phaseList.retainAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        phaseList.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get(int index) {
        return phaseList.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T set(int index, T element) {
        return phaseList.set(index, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int index, T element) {
        phaseList.add(index, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int indexOf(Object o) {
        return phaseList.indexOf(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int lastIndexOf(Object o) {
        return phaseList.lastIndexOf(o);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        return phaseList.listIterator();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return phaseList.listIterator(index);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return phaseList.subList(fromIndex, toIndex);
    }

    /**
     * Returns an iterator over the phases in this collection.
     *
     * @return an iterator for the contained phases
     */
    @Override
    @NotNull
    public Iterator<T> iterator() {
        return phaseList.iterator();
    }
}
