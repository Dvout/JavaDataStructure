package com.xug.map;

public class LinkedListMap<K,V> implements Map<K,V>{

    private class Node{
        public K key;
        public V value;
        public Node next;

        public Node(K key, V value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Node(K key, V value) {
            this(key,value,null);
        }

        public Node() {
            this(null,null,null);
        }

        public String toString(){
            return key.toString()+":"+value.toString();
        }

    }

    private Node dummyNode;
    private int size;

    public LinkedListMap() {
        this.dummyNode = new Node();
        this.size = 0;
    }

    private Node getNode(K key){
        Node cur = dummyNode.next;
        while(cur!=null){
            if(cur.key.equals(key)){
                return cur;
            }
            cur=cur.next;
        }
        return null;
    }

    @Override
    public void add(K key, V value) {
        Node node = getNode(key);
        if(node != null){
            node.value = value;
        }else{
            dummyNode.next = new Node(key,value, dummyNode.next);
            size++;
        }
    }

    @Override
    public V remvoe(K key) {
        if(contain(key)){
            Node pre = dummyNode;
            while(pre.next!=null){
                if(pre.next.key.equals(key)){
                    Node delNode = pre.next;
                    pre.next = delNode.next;
                    delNode.next = null;
                    size--;
                    return delNode.value;
                }
                pre = pre.next;
            }
            return null;
        }else{

            return null;
        }
    }

    @Override
    public void set(K key, V value) {
        Node node = getNode(key);
        if(node == null){
           throw new IllegalArgumentException("error");
        }else {
            node.value = value;
        }
    }

    @Override
    public boolean contain(K key) {
        return getNode(key)==null ? false: true;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        if(node!=null){
            return node.value;
        }
        return null;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }
}
