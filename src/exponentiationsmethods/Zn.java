/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exponentiationsmethods;

import java.math.BigInteger;

/**
 *
 * @author Владимир
 */
public class Zn extends Group<Integer> {
    int n;
    public Zn(int _n)
    {
        n=_n;
        super.identityElement = 1;
    }
    @Override
    public Integer multiply(Integer value1, Integer value2) {
        return (value1*value2)%n;
    }

    @Override
    public Integer sqr(Integer value) {
        return multiply(value, value);
    }

    @Override
    protected Integer prepare(Integer value) {
        return value%n;
    }

    
    
}
