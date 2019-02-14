package com.xug.avltree;

public class AVLTree<K extends Comparable<K>,V> {

    private class Node{
        public K key;
        public V val;
        public Node left;
        public Node right;
        public int height;

        public Node(K key,V val){
            this.key = key;
            this.val = val;
            // 因为在添加节点的时候，从根节点出发，然后添加位置一定是一个叶子节点
            // 默认叶子节点高度为 1
            height=1;
            left = right = null;
        }

    }

    private Node root;
    private int size;
    private Node preNode;
    private boolean flag=false;

    public AVLTree(){
        root = null;
        size =0;
    }


    public int getSize(){
        return size;
    }

    public void add(K key,V val){
        root = add(root,key,val);
    }

    public int getBalanceFactor(Node node){
        return node.left.height-node.right.height;
    }

    public boolean isBalance(Node node){
        if(node == null)
            return true;
        if(Math.abs(node.left.height-node.right.height)>1)
            return false;
        return true;
    }


    public boolean isBST(){
        preOrder(root);
        return flag;
    }

    private void preOrder(Node node){
        if(node == null)
            return ;
        preOrder(node.left);
        if(preNode!=null){
            if(node.key.compareTo(preNode.key)<0)
                flag = false;
        }
        preNode =node;
        preOrder(node.right);
    }

    //LL型，进行右旋转
    private Node rightRotate(Node node){
        Node y = node.left;
        Node T2 = y.right;

        // 右旋转过程
        y.left = node;
        node.left = T2;

        // 维护高度值
        node.height = 1+Math.max(node.left.height,node.right.height);
        y.height = 1+Math.max(y.left.height,y.right.height);

        return y;
    }

    // 左旋转
    private Node leftRotate(Node x){
        Node y  = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        // 更新高度
        x.height = 1+Math.max(x.left.height,x.right.height);
        y.height = 1+Math.max(y.left.height,y.right.height);

        return y;
    }

    private Node add(Node node,K key,V val){
        if(node == null){
            // 此节点为叶子节点，直接添加
            size++;
            // 此时添加到了叶子节点，因此高度为1,不用维护
            return new Node(key,val);
        }
        // 此节点不为叶子节点，添加到其子节点上,然后返回该节点
        if(key.compareTo(node.key)==0){
            //更新值
            node.key = key;
            node.val = val;
        }else if(key.compareTo(node.key)<0){
            node.left = add(node.left,key,val);
        }else{
            node.right = add(node.right,key,val);
        }

        node.height = 1+ Math.max(node.left.height,node.right.height);

        int factor = getBalanceFactor(node);

        //LL
        // factor>1 表示不平衡因素出现在左侧，getBalanceFactor(node.left)>=0表示平衡因素出现在左侧的左侧
        // 又因为添加了一个元素，导致的不平衡，因此差值最大为2
        if(factor>1&&getBalanceFactor(node.left)>=0){
            // 进行右旋转,然后返回到上一层，继续对上一层节点进行调整
            return rightRotate(node);
        }

        //RR
        if(factor<-1&&getBalanceFactor(node.right)<=0){
            // 进行左旋转
            return leftRotate(node);
        }

        //LR
        if(factor>1&&getBalanceFactor(node.left)<0){
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        //RL
        if(factor<-1&&getBalanceFactor(node.right)>0){
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // 在其子节点上添加后，将其返回给其父节点。
        return node;


    }

    public boolean isAvl(){
        return isAvl(root);
    }

    private boolean isAvl(Node node){
        if(node == null)
            return true;
        if(Math.abs(getBalanceFactor(node))>2)
            return false;
        return isAvl(node.left)&&isAvl(node.right);
    }

    public void remove(K key){

        root = remove(root,key);


    }

    private Node remove(Node node,K key){
        if(node == null){
            return null;
        }
        Node retNode = null;
        if(key.compareTo(node.key)>0){
            node.right = remove(node.right,key);
            // 节点的左子树发生了变化，因此节点的平衡性发生了变化
            retNode = node;
        }else if(key.compareTo(node.key)<0){
            node.left = remove(node.left,key);
            // 节点的右子树发生了变化，因此节点的平衡性发生了变化
            retNode = node;
        }else{
            if(node.left==null){
                Node rightNode = node.right;
                node.right=null;
                size--;
                retNode= retNode;
            }else if(node.right==null){
                Node leftNode = node.left;
                node.left=null;
                size--;
                retNode=  retNode;
            }else{// 左右子树都不为空
                // 要拿出最小值，new一下，是因为，最小值可能还有右节点，右节点可以被更新
                Node min = minimum(node.right);
                Node successor = new Node(min.key,min.val);
                size++;
                successor.left = node.left;
                // 继续删除右子树中，最小值的key，一定为一个叶子节点，或者只有左子树或者右子树
                // 删去removeMin(),是因为removeMin()没有维护平衡性
                successor.right = remove(node.right,successor.key);
                size--;
                node.left=node.right=null;
                retNode=  successor;
            }
        }

        retNode.height = 1+ Math.max(retNode.left.height,retNode.right.height);

        int factor = getBalanceFactor(retNode);

        //LL
        // factor>1 表示不平衡因素出现在左侧，getBalanceFactor(node.left)>=0表示平衡因素出现在左侧的左侧
        // 又因为添加了一个元素，导致的不平衡，因此差值最大为2
        if(factor>1&&getBalanceFactor(retNode.left)>=0){
            // 进行右旋转,然后返回到上一层，继续对上一层节点进行调整
            return rightRotate(retNode);
        }

        //RR
        if(factor<-1&&getBalanceFactor(retNode.right)<=0){
            // 进行左旋转
            return leftRotate(retNode);
        }

        //LR
        if(factor>1&&getBalanceFactor(retNode.left)<0){
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }

        //RL
        if(factor<-1&&getBalanceFactor(retNode.right)>0){
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }

        return retNode;
    }

    public K minimum(){
        if(root == null){
            return null;
        }
        // root为空时，会出现空指针异常
        return minimum(root).key;
    }

    private Node minimum(Node node){
        if(node.left == null)
            return node;
        return minimum(node.left);
    }

    public Node removeMin(){
        Node delNode = minimum(root);
        root = removeMin(root);
        return delNode;
    }

    private Node removeMin(Node node){
        if(node == null)
            return null;

        if(node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }

        // 这里有节点变化，这里对左子树进行了操作，操作完后，需要返回给欺父节点
        node.left = removeMin(node.left);
        return node;
    }

    public int getHeight(Node node){
        if(node == null)
            return 0;
        return node.height;
    }


}
