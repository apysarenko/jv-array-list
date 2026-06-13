package core.basesyntax;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elementData;
    private int size;

    public ArrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= elementData.length) {
            return;
        }
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + oldCapacity / 2;
        if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
        }
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new ArrayListIndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayListIndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public void add(T value) {
        ensureCapacity(size + 1);
        elementData[size++] = value;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        ensureCapacity(size + 1);
        if (index < size) {
            System.arraycopy(elementData,index, elementData, index + 1, size - index);
        }
        elementData[index] = value;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        int addCount = list.size();
        ensureCapacity(size + addCount);
        if (list == this) {
            Object[] temp = Arrays.copyOf(elementData, size);
            System.arraycopy(temp, 0, elementData, size, temp.length);
            size += temp.length;
            return;
        }
        for (int i = 0; i < addCount; i++) {
            T item = list.get(i);
            elementData[size++] = item;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) elementData[index];
    }

    @Override
    public void set(T value, int index) {
        checkIndex(index);
        elementData[index] = value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index);
        T removed = (T) elementData[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
        return removed;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(T element) {
        for (int i = 0; i < size; i++) {
            Object current = elementData[i];
            if (Objects.equals(element, current)) {
                T removed = (T) current;
                int numMoved = size - i - 1;
                if (numMoved > 0) {
                    System.arraycopy(elementData, i + 1, elementData, i, numMoved);
                }
                elementData[--size] = null;
                return removed;
            }
        }
        throw new NoSuchElementException("Element not found: " + element);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
