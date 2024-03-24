/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package tools;

import java.util.Random;

public class Randomizer {
    private final static Random rand = new Random();

    public static final int nextInt() {
        return rand.nextInt();
    }

    public static final int nextInt(final int arg0) {  
        return rand.nextInt(arg0);
    }

    public static final void nextBytes(final byte[] bytes) {  
        rand.nextBytes(bytes);
    }

    public static final boolean nextBoolean() {  
        return rand.nextBoolean();
    }

    public static final double nextDouble() {  
        return rand.nextDouble();
    }

    public static final float nextFloat() {  
        return rand.nextFloat();
    }

    public static final long nextLong() { 
        return rand.nextLong();
    }

    public static final int rand(final int lbound, final int ubound) {  
        return nextInt(ubound - lbound + 1) + lbound;
    }
}
