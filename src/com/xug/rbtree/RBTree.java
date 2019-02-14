package com.xug.rbtree;

import com.sun.org.apache.regexp.internal.RE;

public class RBTree<K extends Comparable<K>,V> {

    public static final boolean RED = false;
    public static final boolean BLACK = true;

    private class Node{
        public K k;
        public V val;
        public Node left;
        public Node right;
        public boolean color;

        public Node(K k,V val){
            this.k = k;
            this.val =val;
            left =null;
            right = null;
            color = RED;
        }

    }


    private Node root;
    private int size;

    public RBTree(){
        root = null;
        size =0;
    }

    // 颜色翻转,并没有实现节点的变化，因此不用返回
    private void flipColor(Node node){
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    // 左旋转，旋转后，其根节点的颜色与原来根节点的颜色一致。
    private Node leftRotate(Node node){
        Node x = node.right;
        Node T2 = x.left;

        // 进行旋转
        x.left = node;
        node.right = T2;

        // 颜色变换
        x.color = node.color;
        node.color = RED;

        return node;
    }

    // 右旋转,旋转后的根节点的颜色，与其原来的根节点颜色一致，因为在2-3树中，分裂成了3个2节点，根节点需要向上融合
    private Node rightRotate(Node node){
        Node x = node.left;
        Node T1 = x.right;

        node.left = T1;
        x.right =node;

        x.color = node.color;
        node.color =RED;

        return node;
    }


    public void add(K k,V val){
        root = add(root,k,val);
        // 确保根节点一定时黑色的
        root.color = BLACK;
    }

    private boolean isRed(Node node){
        return node.color == RED;
    }

    private boolean isBlack(Node node){
        return node.color == BLACK;
    }

    // 添加的位置，一定是一个叶子节点
    private Node add(Node node ,K k,V val){

        if(node == null){
            size++;
            // 在定义Node的构造函数的，默认设置了初始化时，节点颜色为红色
            return new Node(k,val);
        }

        if(k.compareTo(node.k)<0){
            node.left = add(node.left,k,val);
        }else if(k.compareTo(node.k)<0){
            node.right = add(node.right,k,val);
        }else{
            node.val = val;
        }

        // 首先判断是否需要左旋转,
        if(isRed(node.right)&&!isRed(node.left)){
            node = leftRotate(node);
        }

        // 判断是否需要右旋转
        if(isRed(node.left)&&isRed(node.left.left)){
            node = rightRotate(node);
        }

        // 颜色变换
        if(isRed(node.left)&&isRed(node.right)){
            flipColor(node);
        }


        return node;
    }

    // 删除一个节点
    public void remove(K k){
        root = remove(root,k);
    }

    private Node remove(Node node,K k){
        if(node == null){
            return null;
        }
        Node retNode = null;

        if(node.k.compareTo(k)>0){
            node.left = remove(node.left,k);
            retNode = node;
        }else if(node.k.compareTo(k)<0){
            node.left = remove(node.right,k);
            retNode = node;
        }else{
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size--;
                retNode= rightNode;
            }else if(node.right==null){
                Node leftNode = node.left;
                node.left = null;
                size--;
                retNode = leftNode;
            }else {
                Node successor = minimum(node.right);
                size++;
                successor.left = node.left;
                // 继续递归的删除右子树中最小的元素，并且已知了最小元素的k
                successor.right = remove(node.right, successor.k);
                size--;
                node.left = node.right=null;
                retNode = successor;
            }
        }


        return retNode;
    }

    // 求整棵树的最小节点
    public K minimum(){
        if(root == null)
            return null;
        return minimum(root).k;
    }

    private Node minimum(Node node){

        if(node.left==null)
            return node;
        return minimum(node.left);
    }

    public boolean contain(K k){
        return contain(root,k);
    }

    private boolean contain(Node node,K k){
        if(node == null)
            return false;
        if(k.compareTo(node.k)==0)
            return true;
        else if(k.compareTo(node.k)<0)
            return contain(node.left,k);
        else
            return contain(node.right,k);
    }


}
