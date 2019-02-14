package com.xug.heap;

import com.xug.heap.Heap;
import java.util.ArrayList;
import java.util.Iterator;

public class HeapImpl<E extends Comparable<E>> implements Heap<E>{
    ArrayList<E>  list = new ArrayList<>();

    public HeapImpl(ArrayList<E> list) {
        Iterator<E> iter = list.iterator();
        while(iter.hasNext()){
            add(iter.next());
        }
    }

    @Override
    public void add(E e) {
        list.add(e);
        int curIndex = list.size()-1;
        while(curIndex > 0){
            int parentIndex = (curIndex-1)/2;
            if(list.get(curIndex).compareTo(list.get(parentIndex))>0){
                swap(curIndex,parentIndex);
            }else
                break;
            curIndex = parentIndex;
        }

    }

    @Override
    public E remove() {
        E del = list.get(0);
        list.set(0,list.get(list.size()-1));
        list.remove(list.size()-1);
        int curIndex = 0;
        while(curIndex < list.size()){
            int left = 2*curIndex+1;
            int right = 2*curIndex +2;
            if(left>=list.size())
                break;
            int maxIndex = left;
            if(right<list.size()){
                if(list.get(maxIndex).compareTo(list.get(right))<0) {
                    maxIndex = right;
                }
            }

            if(list.get(curIndex).compareTo(list.get(maxIndex))<0 ){
                swap(maxIndex, curIndex);
                curIndex = maxIndex;
            }else
                break;

        }
        return del;
    }

    @Override
    public int getSize() {
        return  list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public E getFront() {
        return list.get(0);
    }

    public void swap(int i,int j){
        if(i<0||i>getSize()||j<0||j>getSize()){
            throw new IllegalArgumentException("out of bounds");
        }
        E temp = list.get(i);
        list.set(i,list.get(j));
        list.set(j,temp);
    }

    public int getParent(int k){
        if(k<=0){
            throw new IllegalArgumentException("out of bounds");
        }
        return (k-1)/2;
    }
}
