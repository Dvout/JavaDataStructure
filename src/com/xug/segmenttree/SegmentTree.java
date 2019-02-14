package com.xug.segmenttree;


public class SegmentTree<E>{
    private Merge<E> merge;

    private E data[];
    private E tree[];

    public SegmentTree(E[] arr, Merge<E> merge){
        this.merge = merge;
        data = (E[])new Object[arr.length];
        for(int i=0;i<arr.length;i++){
            data[i] = (E)arr[i];
        }
        tree = (E[])new Object[4*data.length];
        buildSegmentTree(0,0,data.length-1);
    }

    private void buildSegmentTree(int treeIndex,int l,int r){

        // 递归结束条件
        if(l==r){
            tree[treeIndex] = data[l];
            return ;
        }

        int leftChild = 2*treeIndex +1;
        int rightChild = 2*treeIndex+2;
        int mid = l+(r-l)/2;// 防止l+r 太大，导致整型溢出

        buildSegmentTree(leftChild,l,mid);
        buildSegmentTree(rightChild,mid+1,r);


        tree[treeIndex] = merge.merge(tree[leftChild],tree[rightChild]);

    }

    public void update(int index, E val){
        if(index<0|| index>=data.length){
            throw new IllegalArgumentException("out of bounds");
        }
        data[index] = val;
        set(0,0,data.length-1,index,val);
    }

    private void set(int treeIndex,int l,int r,int index, E val){
        if(l == r){
            tree[treeIndex] = val;
            return;
        }
        int leftChild = 2*treeIndex +1;
        int rightChild = 2*treeIndex+2;
        int mid = l+(r-l)/2;// 防止l+r 太大，导致整型溢出
        if(mid>=index){
            set(leftChild,l,mid,index,val);
        }else
            set(rightChild,mid+1,r,index,val);
        // 尾递归，更新tree
        tree[treeIndex] = merge.merge(tree[leftChild],tree[rightChild]);

    }

    public E query(int queryL,int queryR){
        //判断queryL和queryR是否合理
        if(queryL<0 || queryL>=data.length || queryR<0 || queryR>=data.length || queryR<=queryL )
            throw new IllegalArgumentException("Index is illegal");
        return query(0,0,data.length-1,queryL,queryR);
    }

    private E query(int treeIndex,int l ,int r,int queryL,int queryR){
        if(l==queryL&&r==queryR){
            return tree[treeIndex];
        }
        int leftChild = 2*treeIndex +1;
        int rightChild = 2*treeIndex+2;
        int mid = l+(r-l)/2;// 防止l+r 太大，导致整型溢出

        if(queryL>=mid+1){
            return query(rightChild,mid+1,r,queryL,queryR);
        }else if(queryR<=mid){
            return query(leftChild,l,mid,queryL,queryR);
        }


        E left = query(leftChild,l,mid,queryL,mid);
        E right = query(leftChild,mid+1,r,mid+1,queryR);

        return merge.merge(left,right);

    }

    public String toString(){
        return null;
    }

}
