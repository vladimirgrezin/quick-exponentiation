/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exponentiationsmethods;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Владимир
 */
public class ExponentiationsMethods {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Zn z7 = new Zn(23);
       int x=44;
       int deg = 587257;
       System.out.println(z7.binaryLeftToRight(x, deg));
       
       List<List<Integer>> w = new LinkedList<>();
       w.add(Arrays.asList(0,0));
       w.add(Arrays.asList(1,1));
       w.add(Arrays.asList(1,2));
       w.add(Arrays.asList(2,3));
       w.add(Arrays.asList(2,4));
       w.add(Arrays.asList(4,5));
       
       //System.out.println(z7.additionChain(x, deg, null, w));
       //int test = z7.slidingWindow(x, 6, 2, evx);
       //Map test = z7.prepareEv(4, x);
       //Map test1 = z7.prepareEvForMethodsWithFixBase(10, x);
       
       //z7.euclidReduction(x, deg, test1);
       //System.out.println(utils.additionChainGeneratorLeftToRight(314));
     //  utils.additionChain t = utils.mergeAdditionChains(utils.additionChainGeneratorLeftToRight(100),
      //                                                   utils.additionChainGeneratorLeftToRight(15));
       Map m = new HashMap();
       m.put(3, x*x*x);
       List dict = z7.jacobiDictionary(deg);
       utils.additionChain t = utils.additionChainGeneratorSlidingWindow(deg, dict);
//       z7.slidingWindow(x, deg, 2, m);
       System.out.println(dict);
       System.out.println(t);
       System.out.println(z7.additionChain(x, deg, null, t.w));

    }
    
}
