package com.xug.trie;

import java.util.Set;
import java.util.TreeMap;

public class Trie {
    private class Node{
        public TreeMap<Character,Node> next;
        public boolean isWord;
        public Node(boolean isWord){
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        public Node(){
            this(false);
        }


    }

    private Node root;
    private int size;

    public Trie(){
        root = new Node();
        size=0;
    }

    public int getSize(){
        return size;
    }

    public void insert(String word){
        Node  cur = root;
        for(int i=0;i<word.length();i++){
            char c = word.charAt(i);
            if(cur.next.get(c)==null){
                cur.next.put(c,new Node());
            }
            // 使cur等于下一个节点
            cur = cur.next.get(c);
        }
        // 指向单词的最后一个字母。
        if(!cur.isWord){
            cur.isWord = true;
            size++;
        }
    }

    public void recursionInsert(String word){
        recursionInsert(root,word,0);
    }

    private void recursionInsert(Node node,String word,int index){
        if(index>=word.length()){
            // 指向单词的最后一个字母。
            if(!node.isWord) {
                node.isWord = true;
                size++;
            }
            return;
        }
        char c = word.charAt(index);
        if(node.next.get(c)==null){

            node.next.put(c,new Node());
        }
        recursionInsert(node.next.get(c),word,index+1);
    }

    public boolean search(String word){
        Node cur = root;
        for(int i=0;i<word.length();i++){
            char c = word.charAt(i);
            if(cur.next.get(c)==null){
                return false;
            }
            // 使cur等于下一个节点
            cur = cur.next.get(c);
        }
        if(!cur.isWord){
            return false;
        }
        return true;
    }

    public boolean match(String word){
        return match(root,word,0);
    }

    private boolean match(Node node,String word,int index){
        if(index>=word.length()){
            return node.isWord;
        }
        char c = word.charAt(index);
        // 判断是否为 .
        if(c!='.'){
            // 不为点，则很简单
            if(node.next.get(c)==null){
                return false;
            }else{
                return match(node.next.get(c),word,index+1);
            }
        }else{
            Set<Character> keys = node.next.keySet();
            for(char key : keys){
                // 只要有一个匹配成功了，就返回True
                if(match(node.next.get(key),word,index+1)){
                    return true;
                }
            }
            // 当所有的都不满足的时候，就返回false
            return false;
        }
    }

    public boolean startWith(String prefix){

        Node cur = root;
        for(int i=0;i<prefix.length();i++) {
            char c = prefix.charAt(i);
            if (cur.next.get(c) == null) {
                return false;
            }
            // 使cur等于下一个节点
            cur = cur.next.get(c);
        }
        return true;

    }
}
