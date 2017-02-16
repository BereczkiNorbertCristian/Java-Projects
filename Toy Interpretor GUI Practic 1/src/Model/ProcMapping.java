package Model;

import Model.Statements.IStatement;

import java.util.List;

/**
 * Created by bnorbert on 23.01.2017.
 * bnorbertcristian@gmail.com
 */
public class ProcMapping {

    IStatement procBody;
    List<String> formalParams;

    public ProcMapping(){}

    public ProcMapping(List<String> formalParams,IStatement procBody){
        this.formalParams=formalParams;
        this.procBody=procBody;
    }

    public IStatement getProcBody(){
        return procBody;
    }
    public List<String> getFormalParams(){
        return formalParams;
    }

    @Override
    public String toString(){
        String res="";
        for(String str : formalParams){
            res+=str + ",";
        }
        return "("+res + ")" + "\n" + procBody.toString();
    }

}
