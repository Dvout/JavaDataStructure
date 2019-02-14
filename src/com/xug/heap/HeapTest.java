package com.xug.heap;

import java.util.ArrayList;

public class HeapTest<E extends Comparable<E>> implements Heap<E> {

    private ArrayList<E> data;
    private int size;

    public HeapTest() {
        data = new ArrayList<>();
        size=0;
    }

    @Override
    public void add(E e) {
        data.add(e);
        int curIndex = data.size()-1;

        while(curIndex>0){
            int parent = (curIndex - 1)/2;
            if(data.get(curIndex).compareTo(data.get(parent))>0){
                swap(curIndex,parent);
            }else
                break;
            curIndex = parent;
        }
    }

    private void swap(int i,int j){
        E temp = data.get(i);
        data.set(i,data.get(j));
        data.set(j,temp);
    }

    @Override
    public E remove() {
        E ret = data.remove(data.size()-1);
        E del = data.get(0);
        data.set(0,ret);
        int cur = 0;
        while(cur<data.size()){
            int left = 2*cur+1;
            int right = 2*cur+2;
            if(left>=data.size())
                break;
            int maxIndex = left;
            if(right<data.size()){
                if(data.get(right).compareTo(data.get(maxIndex))>0){
                    maxIndex = right;
                }
            }

            if(data.get(cur).compareTo(data.get(maxIndex))>0){
                break;
            }
            swap(cur,maxIndex);
            cur = maxIndex;
        }


        return del;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public E getFront() {
        return null;
    }
}
