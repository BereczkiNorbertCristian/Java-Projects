package Collections;

import java.io.Serializable;

/**
 * Created by bnorbert on 06.11.2016.
 * bnorbertcristian@gmail.com
 * Class for maintaining unique positive numbers with fast get and insert numbers in O(log(levels))
 */
public class UniqueTrie implements Serializable {

    public class TrieNode implements Serializable{

        public TrieNode left,right;
        public int bit;
        public int numbersDown;


        public TrieNode getLeft() {
            return left;
        }

        public void setLeft(TrieNode left) {
            this.left = left;
        }

        public TrieNode getRight() {
            return right;
        }

        public void setRight(TrieNode right) {
            this.right = right;
        }

        public int getBit() {
            return bit;
        }

        public void setBit(int bit) {
            this.bit = bit;
        }

        public int getNumbersDown() {
            return numbersDown;
        }

        public void setNumbersDown(int numbersDown) {
            this.numbersDown = numbersDown;
        }



        public TrieNode(){}


    }


    private final int levels;
    private TrieNode root;

    public UniqueTrie(){this.levels=17;
        root=createUniqueTrie(0,2);
    }

    public UniqueTrie(int levels){

        if(levels <3){
            this.levels=3;
        }
        else {
            this.levels = levels;
        }
        root=createUniqueTrie(0,2);

    }

    private TrieNode createUniqueTrie(int level,int val){

        if(level == levels+1){
            return null;
        }
        else{
            TrieNode currNode=new TrieNode();
            currNode.bit =val;
            currNode.numbersDown=0;
            currNode.left=createUniqueTrie(level+1,0);
            currNode.right=createUniqueTrie(level+1,1);

            return currNode;
        }
    }


    public boolean insertValue(int value){

        boolean cool;
        if(value%2 == 0){
            cool=propagateDown(root.left,value/2,1);
        }
        else {
            cool=propagateDown(root.right,value/2,1);
        }
        if(cool){
            root.numbersDown++;
        }
        return cool;
    }

    public boolean removeValue(int value){

        boolean cool;
        if(value%2 == 0){
            cool=removePropagateDown(root.left,value/2,1);
        }
        else {
            cool=removePropagateDown(root.right,value/2,1);
        }
        if(cool){
            root.numbersDown--;
        }
        return cool;
    }

    private boolean removePropagateDown(TrieNode currNode, int value, int level){

        if(level == levels){
            if(currNode.numbersDown == 0){
                return false;
            }
            else{
                currNode.numbersDown=currNode.numbersDown-1;
                return true;
            }
        }
        else{
            boolean cool;
            if(value%2 == 0){
                cool=removePropagateDown(currNode.left,value/2,level+1);
            }
            else{
                cool=removePropagateDown(currNode.right,value/2,level+1);
            }
            if(cool){
                currNode.numbersDown=currNode.numbersDown-1;
            }
            return cool;
        }
    }
    public int uniqueValue(){

        int ret;
        if(root.left.numbersDown <= root.right.numbersDown){
            ret=searchDown(root.left,0);
        }
        else{
            ret=searchDown(root.right,0);
        }
        insertValue(ret);
        return ret;
    }

    private int searchDown(TrieNode currNode,int level){

        if(level == levels-1){
            return currNode.bit*(1 << level);
        }
        else{
            int retValue=currNode.bit*(1 << level);
            if(currNode.left.numbersDown <= currNode.right.numbersDown){
                return retValue + searchDown(currNode.left,level+1);
            }
            else{
                return retValue + searchDown(currNode.right,level+1);
            }
        }
    }

    private boolean propagateDown(TrieNode currNode, int value, int level){

        if(level == levels){
            if(currNode.numbersDown == 0){
                currNode.numbersDown++;
                return true;
            }
            else{
                return false;
            }
        }
        else{
            boolean cool;
            if(value%2 == 0){
                cool=propagateDown(currNode.left,value/2,level+1);
            }
            else{
                cool=propagateDown(currNode.right,value/2,level+1);
            }
            if(cool){
                currNode.numbersDown++;
            }
            return cool;
        }
    }



}
