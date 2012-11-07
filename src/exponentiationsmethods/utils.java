/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exponentiationsmethods;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Владимир
 */
public class utils {
    public static class additionChain
    {
        public List<List<Integer>> w;
        public List<Integer> v;
        public additionChain(List _w, List _v) { w=_w; v=_v; }        
        @Override
        public String toString()
        {
            return "V = "+v.toString()+"\nW= "+w.toString();
        }
    }
    public static additionChain mergeAdditionChains(additionChain masterChain, additionChain slaveChain)
    {
        //List vMaster = masterChain.v;
        for(Integer val: slaveChain.v)
            if(!masterChain.v.contains(val))
            {
                masterChain.v.add(val);
                masterChain.w.add(Arrays.asList(masterChain.v.indexOf(slaveChain.v.get(slaveChain.w.get(slaveChain.v.indexOf(val)-1).get(0))), 
                                                masterChain.v.indexOf(slaveChain.v.get(slaveChain.w.get(slaveChain.v.indexOf(val)-1).get(1)))));
            }
        return masterChain;
    }
    public static additionChain additionChainGeneratorLeftToRight(int degree)
    {
        String binaryForm = new StringBuffer(Integer.toBinaryString(degree)).reverse().toString();
        List<Integer> v = new LinkedList<>();
        List<List<Integer>> w = new LinkedList<>();
        v.add(1);
        int currentPosition = -1;
        for (int i = binaryForm.length() - 1; i >= 0; i--) {
           
            if (currentPosition != -1) {
                v.add(v.get(currentPosition) * 2);
                w.add(Arrays.asList(currentPosition, currentPosition));
                currentPosition++;
                int ni = Integer.parseInt(binaryForm.substring(i, i + 1));
                if (ni == 1) {                 
                    v.add(v.get(currentPosition) + 1);
                    w.add(Arrays.asList(0, currentPosition));
                    currentPosition++;
                }
            } else {
                currentPosition++;
            }
        }
       
        return new additionChain(w,v);
    }
    /*
    public static List<List<Integer>> additionChainGeneratorSlidingWindow(int degree, List<Integer> dict)
    {
        Collections.sort(dict);
        int maxInDict = dict.get(dict.size()-1);
        int k = (int)Math.ceil(Math.log(maxInDict+1) / Math.log(2));
       
        String binaryForm = new StringBuffer(Integer.toBinaryString(degree)).reverse().toString();
        
        //T y = identityElement;
        int currentPosition = -1;
        List<Integer> v = new LinkedList<>();
        List<List<Integer>> w = new LinkedList<>();
        v.add(1);
        int i = binaryForm.length()-1;
        while(i>=0)
        {
            int ni = Integer.parseInt(binaryForm.substring(i, i+1));
            if(ni==0)
            {
                //y=sqr(y);
                v.add(v.get(currentPosition) * 2);
                w.add(Arrays.asList(currentPosition, currentPosition));
                currentPosition++;
                i--;
            }
            else
            {
                int s = Math.max(i-k+1,0);
                while(Integer.parseInt(binaryForm.substring(s, s+1))==0) s++;
                for(int h=1; h<=i-s+1; h++) 
                {
                    //y=sqr(y);
                    v.add(v.get(currentPosition) * 2);
                    w.add(Arrays.asList(currentPosition, currentPosition));
                    currentPosition++;
                } 
                int d = Integer.parseInt(new StringBuffer(binaryForm.substring(s, i+1)).reverse().toString(),2);
                additionChain tempChain, masterChain;
                if(dict.contains(d)) 
                {
                    if(!v.contains(d))
                    {
                        tempChain = additionChainGeneratorLeftToRight(d);
                        masterChain = new additionChain(w, v);
                        w = mergeAdditionChains(masterChain, tempChain).w;
                        v = mergeAdditionChains(masterChain, tempChain).v;
                    }
                    v.add(v.get(currentPosition) + d);
                    w.add(Arrays.asList(v.indexOf(d), currentPosition));
                    currentPosition = v.size()-1;
                }else{
                    
                }
                y=multiply(y,evx.get(d));
                i=s-1;
            }
        }
        return y;
    }*/
     public static additionChain additionChainGeneratorSlidingWindow(int degree, List<Integer> dict)
    {
        Collections.sort(dict);
        int maxInDict = dict.get(dict.size()-1);
        int k = (int)Math.ceil(Math.log(maxInDict+1) / Math.log(2));
        boolean f = false;
        String binaryForm = new StringBuffer(Integer.toBinaryString(degree)).reverse().toString();
        
        //T y = identityElement;
        int currentPosition = 0;
        List<Integer> v = new LinkedList<>();
        List<List<Integer>> w = new LinkedList<>();
        v.add(1);
        int i = binaryForm.length()-1;
        while(i>=0)
        {
            int ni = Integer.parseInt(binaryForm.substring(i, i+1));
            if(ni==0&&f)
            {
                //y=sqr(y);
                v.add(v.get(currentPosition) * 2);
                w.add(Arrays.asList(currentPosition, currentPosition));
                currentPosition++;
                i--;
            }
            else
            {
                int s = Math.max(i-k+1,0);
                while(!dict.contains(
                    Integer.parseInt(new StringBuffer(binaryForm.substring(s, i+1)).reverse().toString(),2))) s++;
                for(int h=1; h<=i-s+1; h++) 
                {
                    //y=sqr(y);
                    if(!f) break;
                    v.add(v.get(currentPosition) * 2);
                    w.add(Arrays.asList(currentPosition, currentPosition));
                    currentPosition++;
                } 
                int d = Integer.parseInt(new StringBuffer(binaryForm.substring(s, i+1)).reverse().toString(),2);
                additionChain tempChain, masterChain;
              
                if(!v.contains(d))
                {
                    tempChain = additionChainGeneratorLeftToRight(d);
                    masterChain = new additionChain(w, v);
                    w = mergeAdditionChains(masterChain, tempChain).w;
                    v = mergeAdditionChains(masterChain, tempChain).v;
                }
                
                if(f)
                {
                    v.add(v.get(currentPosition) + d);
                    w.add(Arrays.asList(v.indexOf(d), currentPosition));   
                }
                currentPosition = v.size()-1;
                f = true;
                
                //y=multiply(y,evx.get(d));
                i=s-1;
            }
        }
        return new additionChain(w, v);
    }
}
