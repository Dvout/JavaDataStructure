package com.xug.segmenttree;

import javax.swing.text.LayoutQueue;
import java.util.ArrayList;

public class SegmentTreeTest<E> {
    private Merge<E> merge;
    private E[] data;
    private E[] tree;

    public SegmentTreeTest(ArrayList<E> arr,Merge<E> merge){
        this.merge = merge;
        data = (E[])new Object[arr.size()];
        for(int i=0;i<arr.size();i++){
            data[i] = arr.get(i);
        }

        buildSegmentTree(0,0,data.length-1);

    }
    private E buildSegmentTree(int treeIndex,int l,int r){
        if(l==r){
            tree[treeIndex] = data[l];
            return tree[treeIndex];
        }

        int leftChild = 2*treeIndex+1;
        int rightChild = 2*treeIndex+2;
        int mid = l+(r-l)/2;//防止整型溢出

        E left = buildSegmentTree(leftChild,l,mid);
        E right  = buildSegmentTree(rightChild,mid+1,rightChild);


        // 将右子树的结果融合
        tree[treeIndex] = merge.merge(left,right);
        return tree[treeIndex];
    }

    public void update(int index,E val){
        if(index<0||index>data.length-1){
            throw new IllegalArgumentException("out of index");
        }
        // 直接更新值
        data[index] = val;
        // 然后更新树中的值
        update(0,0,data.length-1,index,val);
    }

    private void update(int treeIndex,int l,int r,int index,E val){
        if(l==r){
            tree[treeIndex] = val;
        }

        int leftChild = 2*treeIndex+1;
        int rightChild = 2*treeIndex+2;
        int mid = l+(r-l)/2;

        // 只需要更新左右子树中一边的子树即可
        if(index > mid){
            update(rightChild,mid+1,r,index,val);
        }else
            update(leftChild,l,mid,index,val);

        tree[treeIndex] = merge.merge(tree[leftChild],tree[rightChild]);


    }

    public E query(int Lquery,int Rquery){

        return query(0,0,data.length-1,Lquery,Rquery);
    }

    private E query(int treeIndex,int l,int r,int Lquery,int Rquery){
        if(l==Lquery&&r==Rquery)
            return tree[treeIndex];

        int leftChild = 2*treeIndex+1;
        int rightChild = 2*treeIndex+2;
        int mid = l+(r-l)/2;
        if(Lquery>mid){
            return query(rightChild,mid+1,r,Lquery,Rquery);
        }else if(Rquery<=mid){
            return query(leftChild,l,mid,Lquery,Rquery);
        }

        E left = query(leftChild,l,mid,Lquery,mid);
        E right = query(rightChild,mid+1,r,mid+1,Rquery);
        tree[treeIndex] = merge.merge(left,right);
        return tree[treeIndex];
    }
}
