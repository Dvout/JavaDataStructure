package com.xug.binarytree;

import java.util.ArrayList;
import java.util.TreeSet;

public class BST<E extends Comparable<E>> {

    TreeSet<E> set;

    private static class TreeNode<E>{
        protected E e;
        protected TreeNode<E> left;
        protected TreeNode<E> right;

        public TreeNode(E e) {
            this.e = e;
        }
    }


    protected TreeNode<E> root;
    protected int size = 0;

    public BST(){
        root = null;
        size=0;
    }

    public BST(ArrayList<E> arr){
        for(E a:arr){
            insert(a);
        }
    }

    public boolean delete(E e){
        // 初始时，根节点的父节点为空
        TreeNode<E> parent = null;
        TreeNode<E> cur=root;
        while(cur!=null){
            if(e.compareTo(cur.e)>0){
                parent = cur;
                cur=cur.right;
            }else if(e.compareTo(cur.e)<0){
                parent = cur;
                cur = cur.left;
            }else
                break;
        }
        // 表示不存在节点e
        if(cur == null){
            return false;
        }

        // 不存在左子树时，直接用右子树代替
        if(cur.left == null){
            if(parent == null){
                // 说明要删除的是根节点，此时cur = root
                cur = cur.right;
            }else if(e.compareTo(parent.e)>0){
                parent.right = cur.right;
            }else
                parent.left = cur.right;
        }

        TreeNode<E> parentOfMaxInLeftTree = cur;
        TreeNode<E> maxInLeftTree = cur.left;

        while(maxInLeftTree.right != null){
            parentOfMaxInLeftTree = maxInLeftTree;
            maxInLeftTree = maxInLeftTree.right;
        }
        // 此时maxinleftTree为左子树中最大的元素
        cur.e = maxInLeftTree.e;

        //删除右子树中最大元素
        if(parentOfMaxInLeftTree.right.equals(maxInLeftTree)){
            parentOfMaxInLeftTree.right = maxInLeftTree.left;
        }else{
            //则表示最大的元素时左子树的根节点
            parentOfMaxInLeftTree.left = maxInLeftTree.left;
        }

        size--;
        return true;
    }

    public boolean insert(E e){
        // 先确保了根节点一定不为空
        if(root == null){
            root = new TreeNode<>(e);
            return true;
        }

        TreeNode<E> parent = null;
        TreeNode<E> cur = root;
        while(cur!=null){
            parent = cur;
            if(e.compareTo(cur.e)>0){
                cur = cur.right;
            }else if(e.compareTo(cur.e)<0) {
                cur = cur.left;
            }else
                return false;
        }
        cur = new TreeNode<>(e);

        if(e.compareTo(parent.e)>0){
            parent.left = cur;
        }else{
            parent.right = cur;
        }
        size++;
        return true;
    }

    public boolean contain(E e){
        if(root==null)
            return false;
        TreeNode<E> cur = root;
        while(cur!=null){
            if(e.compareTo(cur.e)>0)
                cur = cur.right;
            else if(e.compareTo(cur.e)<0)
                cur = cur.left;
            else
                return true;
        }
        return false;
    }
}
