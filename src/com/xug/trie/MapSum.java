package com.xug.trie;

import java.util.TreeMap;

public class MapSum {

    private class Node{
        int val;
        TreeMap<Character,Node> next;

        public Node(int val) {
            this.val = val;
            next = new TreeMap<>();
        }

        public Node(){
            this(0);
        }
    }

    private Node root;

    public MapSum(){
        root = new Node();
    }

    public void insert(String word, int val){
        Node cur = root;
        for(int i=0;i<word.length();i++){
            char c = word.charAt(i);
            if(cur.next.get(c)==null){
                cur.next.put(c,new Node());
            }
            cur = cur.next.get(c);
        }
        cur.val = val;
    }

    public int sum(String prefix){
        Node cur = root;
        for(int i=0;i<prefix.length();i++){
            char c = prefix.charAt(i);
            if(cur.next.get(c)==null){
                return 0;
            }
            cur = cur.next.get(c);
        }
        return sum(cur);
    }

    private int sum(Node node){
        int res = node.val;
        for(char key : node.next.keySet()){
            res += sum(node.next.get(key));
        }
        return res;
    }

}
