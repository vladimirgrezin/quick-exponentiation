/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exponentiationsmethods;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Владимир
 */
public abstract class Group<T> {
    public T identityElement;
    public abstract T multiply(T value1, T value2);
    public abstract T sqr(T value);
    protected abstract T prepare(T value);
    public Map<Integer, T> prepareEv(int k, T x)
    {
        x=prepare(x);
        Map<Integer, T> res = new HashMap<>();
        res.put(0, identityElement);
        res.put(1, x);
        for(int i=3; i<=Math.pow(2,k)-1;i+=2)
            res.put(i, multiply(res.get(i-2),sqr(x)));
        return res;
    }
    public Map<Integer, T> prepareEvForMethodsWithFixBase(int k, T x)
    {
        x=prepare(x);
        Map<Integer, T> res = new HashMap<>();
        //res.put(0, identityElement);
        res.put(1, x);
        for(int i=2; i<Math.pow(2,k);i*=2)
            res.put(i, sqr(res.get(i/2)));
        return res;
    }
    public T binaryLeftToRight(T x, int degree)
    {
        x=prepare(x);
        String binaryForm = new StringBuffer(Integer.toBinaryString(degree)).reverse().toString();
        T y = identityElement;
        for( int i = binaryForm.length()-1;i>=0;i--)
        {
            y=sqr(y);
            int ni = Integer.parseInt(binaryForm.substring(i, i+1));
            if (ni == 1) y=multiply(y,x);
        }
        return y;
    }
    public T binaryRightToLeft(T x, int degree)
    {
        x=prepare(x);
        String binaryForm = new StringBuffer(Integer.toBinaryString(degree)).reverse().toString();
        T y = identityElement;
        T z = x;
        int l = binaryForm.length()-1;
        for( int i =0; i<=l; i++)
        {
            int ni = Integer.parseInt(binaryForm.substring(i, i+1));
            if (ni == 1) y=multiply(y,z);
            z=sqr(z);            
        }
        return y;
    }
    public T montgomeryReduction(T x, int degree)
    {
        x=prepare(x);
        String binaryForm = new StringBuffer(Integer.toBinaryString(degree)).reverse().toString();
        T x1 = x; T x2 = sqr(x);
        for(int i = binaryForm.length()-1;i>=0;i--)
        {
            int ni = Integer.parseInt(binaryForm.substring(i, i+1));
            if(ni==0)
            {
                x2=multiply(x2,x1);
                x1=sqr(x1);
            }
            else
            {
                x1=multiply(x1,x2);
                x2=sqr(x2);
            }
        }
        return x1;
    }
    public T slidingWindow(T x, int degree, int k, Map<Integer,T> evx)
    {
       
        String binaryForm = new StringBuffer(Integer.toBinaryString(degree)).reverse().toString();
        
        T y = identityElement;
        int i = binaryForm.length()-1;
        while(i>=0)
        {
            int ni = Integer.parseInt(binaryForm.substring(i, i+1));
            if(ni==0)
            {
                y=sqr(y);
                i--;
            }
            else
            {
                int s = Math.max(i-k+1,0);
                while(Integer.parseInt(binaryForm.substring(s, s+1))==0) s++;
                for(int h=1; h<=i-s+1; h++) y=sqr(y); 
                int d = Integer.parseInt(new StringBuffer(binaryForm.substring(s, i+1)).reverse().toString(),2);
                y=multiply(y,evx.get(d));
                i=s-1;
            }
        }
        return y;
    }
   
    public T brouwerReduction(T x, int degree, int k, Map<Integer,T> evx)
    {
       
        String binaryForm = new StringBuffer(Integer.toString(degree,(int)Math.pow(2, k))).reverse().toString();
        
        T y = identityElement;
        int i = binaryForm.length()-1;
        while(i>=0)
        {
            int ni = Integer.parseInt(binaryForm.substring(i, i+1));
            int s =0;
            while((ni%2==0)&&(ni!=0)) { s++; ni/=2;}
            int u = ni;
            for(int j=1; j<=k-s; j++) y=sqr(y);
            y=multiply(y,evx.get(u));
            for(int j=1; j<=s; j++) y=sqr(y);
            i--;
        }
        return y;
    }
    
    public T additionChain(T x, int degree, List<Integer> v, List<List<Integer>> w)
    {
        x=prepare(x);
        int s = w.size();
        LinkedList<T> xi = new LinkedList<>();
        xi.add(x);
        for(int i =1; i<=s; i++)
        {
            xi.add(multiply(xi.get(w.get(i-1).get(0)),xi.get(w.get(i-1).get(1))));
        }
        return xi.getLast();
    }
    public T yaoReduction(T x, int degree, Map<Integer,T> evx)
    {
       T y = identityElement;
       T u = identityElement;
       String binaryForm = new StringBuffer(Integer.toBinaryString(degree)).reverse().toString();
       int j=1; // потому что ni всегда <=1, it's work, do not modify)))
       int l = Math.min(evx.size(),binaryForm.length());
       for(; j>=1; j--)
       {
           for(int i=0; i<l; i++)
           {
               int ni = Integer.parseInt(binaryForm.substring(i, i+1));
               if(ni==j) u=multiply(u,evx.get((int)Math.pow(2, i)));
           }
           y=multiply(y,u);
               
       }
        return y;
    }
    public T euclidReduction(T x, int degree, Map<Integer,T> evx)
    {
        String binaryForm = new StringBuffer(Integer.toBinaryString(degree)).reverse().toString();
        T xM;
        T xN;
        int nM;
        int N = 0;
        int M = 0;
        while(true)
        {
            nM = 0;
            M = 0;
            for(int i=0; i<binaryForm.length(); i++) 
                if(nM<Integer.parseInt(binaryForm.substring(i, i+1)))
                {
                    nM = Integer.parseInt(binaryForm.substring(i, i+1));
                    M = i;
                }
            int nN = 0;
            N = 0;
            for(int i=0; i<binaryForm.length(); i++) 
                if(nN<Integer.parseInt(binaryForm.substring(i, i+1))&&i!=M)
                {
                    nN = Integer.parseInt(binaryForm.substring(i, i+1));
                    N = i;
                }
            if(nN!=0)
            {
                int d = nM/nN;
                xN = evx.get((int)Math.pow(2, N));
                xM = evx.get((int)Math.pow(2, M));
                T temp = xM;
                for(int i=0;i<d-1;i++)
                    temp=multiply(temp,xM);
                evx.put((int)Math.pow(2, N), multiply(temp,xN));
                StringBuffer tempBinaryForm = new StringBuffer();
                tempBinaryForm.append(binaryForm.substring(0, M))
                              .append(nM%nN)
                              .append(binaryForm.substring(M+1, binaryForm.length()));
                binaryForm = tempBinaryForm.toString();
            }
            else break;
        }
        xM = evx.get((int)Math.pow(2, M));
        T temp = xM;
        for(int i=0;i<nM-1;i++)
           temp=multiply(temp,xM);
        return temp;
    }
    
    public LinkedList<Integer> jacobiDictionary(int n)
    {
        StringBuffer binaryForm = new StringBuffer(Integer.toBinaryString(n)).reverse();
        StringBuffer temp = new StringBuffer();
        LinkedList<String> stringDictRepresent = new LinkedList<>();
        LinkedList<Integer> dict = new LinkedList<>();
        for(int i=0; i<binaryForm.length(); i++)
        {
          temp.append(binaryForm.charAt(i));
          if(!stringDictRepresent.contains(temp.toString())) 
          {
             stringDictRepresent.add(temp.toString());
             temp.setLength(0);
          } 
        }
        for(String s: stringDictRepresent)
        {
            int t = Integer.parseInt(s,2);
            if(!dict.contains(t)&&t%2==1) dict.add(t);
        }
        return dict;
    }
    
}
