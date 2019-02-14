package com.xug.recursionbst;

import java.util.Stack;

public class BST<E extends Comparable<E>> {

    private class Node{
        public E e;
        public Node left;
        public Node right;
        public Node(E val){
            e = val;
        }
    }

    private Node root;
    private int size;

    public BST(){
        root =null;
        size =0;
    }


    public void insert(E e){
        root = insert(root,e);
    }

    private Node insert(Node node, E e){
        if(node == null){
            size++;
            return new Node(e);
        }
        if(e.compareTo(node.e)>0){
            node.right= insert(node.right,e);
        }else if(e.compareTo(node.e)<0){
            node.left = insert(node.left,e);
        }
        return node;

    }

    public void remove(E e){
        root = remove(root,e);

    }

    private Node remove(Node node,E e){
        if(node == null){
            return null;
        }
        if(e.compareTo(node.e)==0){
            if(node.left==null){
                Node retNode = node.right;
                node.right=null;
                size--;
                return retNode;
            }else if(node.right==null){
                Node leftNode = node.left;
                node.left=null;
                size--;
                return leftNode;
            }else{
                Node minNodeOfRight = new Node(minimum(node.right).e);
                minNodeOfRight.left = node.left;
                minNodeOfRight.right = removeMin(node.right);
                node.left = node.right=null;
                return minNodeOfRight;
            }
        }else if(e.compareTo(node.e)>0){
            node.right = remove(node.right,e);
            return node;
        }else
            node.left = remove(node.left,e);
            return node;
    }

    public boolean contain(E e){
        return contain(root,e);
    }

    private boolean contain(Node node,E e){
        if(node ==null){
            return false;
        }

        if(e.compareTo(node.e)==0) {
            return true;
        }else if(e.compareTo(node.e)>0){
            return contain(node.right,e);
        }else
            return contain(node.left,e);
    }

    public E minimum(){
        if(root == null)
            return null;
        return minimum(root).e;
    }

    private Node minimum(Node node){
        if(node.left == null){
            return node;
        }
        return minimum(node.left);
    }

    public Node removeMin(){
        Node del = minimum(root);
        root = removeMin(root);
        return del;
    }

    private Node removeMin(Node node){
        if(node == null){
            return null;
        }
        if(node.left==null){
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        // 删除其左子树中最小值
        node.left = removeMin(node.left);
        return node;
    }



    public void preOrder(){
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            Node node = stack.pop();
            System.out.println(node);
            if(node.right!=null){
                stack.push(node.right);
            }
            if(node.left!=null)
                stack.push(node.left);
        }

    }

    public void levelOrder(){

    }
}
