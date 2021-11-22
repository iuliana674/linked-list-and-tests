import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class LinkedListImpl<E> extends LinkedList<E> {
    private int size = 0;
    private Node<E> head;
    private Node<E> tail;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        Node<E> el = head;
        while(el != null){
            if(el.item.equals(o))
                return true;
            el = el.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                return get(index++);
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] o = new Object[size];
        int index = 0;
        Node<E> el = head;
        while(el != null){
            o[index++] = el.item;
            el = el.next;
        }
        return o;
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        if(ts.length < size)
            ts = (T[]) Array.newInstance(ts.getClass().getComponentType(), size);
        Object[] o = ts;
        int index = 0;
        Node<E> el = head;
        while(el != null){
            o[index++] = el.item;
            el = el.next;
        }
        if(ts.length > size)
            o[size] = null;
        return (T[]) o;
    }

    @Override
    public boolean add(E item) {
        Node<E> newNode = new Node<>(item, tail, null);
        if(tail == null){
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node<E> el = head;
        while (el != null){
            if(el.item.equals(o)){
                Node<E> pred = el.prev;
                Node<E> succ = el.next;
                if(pred == null){
                    head = succ;
                } else{
                    pred.next = succ;
                    el.next = null;
                }
                if(succ == null){
                    tail = pred;
                } else{
                    succ.prev = pred;
                    el.prev = null;
                }
                el.item = null;
                size--;
                return true;
            }
            el = el.next;
        }
        return false;
    }

    @Override
    public void clear() {
        Node<E> el = head;
        while (el != null){
            Node<E> next = el.next;
            el.item = null;
            el.prev = null; el.next = null;
            el = next;
        }
        head = null; tail = null;
        size = 0;
    }

    @Override
    public E get(int i) {
        checkIndex(i);
        return getNode(i).item;
    }

    @Override
    public E set(int i, E item) {
        Node<E> el = getNode(i);
        return el.item = item;
    }

    @Override
    public void add(int i, E item) {
        checkIndex(i);
        if(i == size){
            add(item);
        } else{
            Node<E> succ = getNode(i);
            Node<E> pred = succ.prev;
            Node<E> newNode = new Node<>(item, pred, succ);
            succ.prev = newNode;
            if(pred == null)
                head = newNode;
            else
                pred.next = newNode;
            size++;
        }
    }

    @Override
    public E remove(int i) {
        E item = get(i);
        return remove(item) ? item : null;
    }

    @Override
    public int indexOf(Object o) {
        Node<E> el = head;
        int index = 0;
        while(el != null){
            if(el.item.equals(o))
                return index;
            index++;
            el = el.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node<E> el = tail;
        int index = size - 1;
        while(el != null){
            if(el.item.equals(o))
                return index;
            index--;
            el = el.prev;
        }
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListIterator<E>() {
            private int fIndex = 0;
            private int bIndex = size - 1;
            @Override
            public boolean hasNext() {
                return fIndex < size;
            }

            @Override
            public E next() {
                return get(fIndex++);
            }

            @Override
            public boolean hasPrevious() {
                return bIndex > -1;
            }

            @Override
            public E previous() {
                return get(bIndex--);
            }

            @Override
            public int nextIndex() {
                return 0;
            }

            @Override
            public int previousIndex() {
                return 0;
            }

            @Override
            public void remove() {

            }

            @Override
            public void set(E e) {

            }

            @Override
            public void add(E e) {

            }
        };
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        return new ListIterator<E>() {
            private int fIndex = i;
            private int bIndex = size - 1;

            @Override
            public boolean hasNext() {
                return fIndex < size;
            }

            @Override
            public E next() {
                return get(fIndex++);
            }

            @Override
            public boolean hasPrevious() {
                return bIndex > i;
            }

            @Override
            public E previous() {
                return get(bIndex--);
            }

            @Override
            public int nextIndex() {
                return 0;
            }

            @Override
            public int previousIndex() {
                return 0;
            }

            @Override
            public void remove() {

            }

            @Override
            public void set(E e) {

            }

            @Override
            public void add(E e) {

            }
        };
    }

    @Override
    public List<E> subList(int i, int i1) {
        checkIndex(i); checkIndex(i1 - 1);
        List<E> list = new ArrayList<>();
        Node<E> el = getNode(i);
        while(i < i1){
            list.add(el.item);
            el = el.next; i++;
        }
        return list;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        collection.forEach(this::add);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    private void checkIndex(int i) {
        if(i < -1 || i > size)
            throw new IndexOutOfBoundsException("Please check the index!");
    }

    private Node<E> getNode(int index){
        Node<E> node = head;
        for(int i = 0 ; i < index ; i++){
            node = node.next;
        }
        return node;
    }

    private static class Node<E>{
        E item;
        Node<E> prev;
        Node<E> next;

        public Node(E item, Node<E> prev, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
