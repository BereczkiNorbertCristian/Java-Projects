package Model.Expressions;

import Collections.Heap;
import Collections.IHeap;
import Collections.IToyMap;
import Exceptions.DivisionByZeroException;
import Exceptions.VariableNotDefinedException;

import java.io.Serializable;

/**
 * Created by bnorbert on 03.12.2016.
 * bnorbertcristian@gmail.com
 */
public class IntegerComparissonExpression extends Expression implements Serializable {

    String operation;
    Expression left,right;

    public IntegerComparissonExpression(){}
    public IntegerComparissonExpression(Expression left,Expression right,String operation){
        this.left=left;
        this.right=right;
        this.operation=operation;
    }

    @Override
    public int eval(IToyMap<String,Integer> symbolTable, IHeap<Integer> heap) throws VariableNotDefinedException, DivisionByZeroException{

        int answer=0;

        int leftValue=left.eval(symbolTable,heap);
        int rightValue=right.eval(symbolTable,heap);

        switch (operation){
            case "==":
                if(leftValue == rightValue){
                    answer=1;
                }
                break;
            case "!=":
                if(leftValue != rightValue){
                    answer=1;
                }
                break;
            case ">":
                if(leftValue > rightValue){
                    answer=1;
                }
                break;
            case ">=":
                if (leftValue >= rightValue){
                    answer=1;
                }
                break;
            case "<":
                if(leftValue < rightValue){
                    answer=1;
                }
                break;
            case "<=":
                if (leftValue <= rightValue){
                    answer=1;
                }
                break;
        }


        return answer;
    }

    @Override
    public String toString(){
        return left.toString() + operation + right.toString();
    }

}
