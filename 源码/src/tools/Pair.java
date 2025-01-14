/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package tools;

import java.io.Serializable;

/**
 * Represents a pair of values.
 * 
 * @author Frz
 * @since Revision 333
 * @version 1.0
 * 
 * @param <E> The type of the left value.
 * @param <F> The type of the right value.
 */
public class Pair<E, F> implements Serializable {
    private static final long serialVersionUID = 9179541993413738569L;
    public E left;
    public F right;

    /**
     * Class constructor - pairs two objects together.
     *
     * @param left The left object.
     * @param right The right object.
     */
    public Pair(E left, F right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Gets the left value.
     *
     * @return The left value.
     */
    public E getLeft() {
        return left;
    }

    /**
     * Gets the right value.
     *
     * @return The right value.
     */
    public F getRight() {
        return right;
    }

    /**
     * Turns the pair into a string.
     *
     * @return Each value of the pair as a string joined by a colon.
     */
    @Override
    public String toString() {
        return left.toString() + ":" + right.toString();
    }

    /**
     * Gets the hash code of this pair.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());
        return result;
    }

    /**
     * Checks to see if two pairs are equal.
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair other = (Pair) obj;
        if (left == null) {
            if (other.left != null) {
                return false;
            }
        } else if (!left.equals(other.left)) {
            return false;
        }
        if (right == null) {
            if (other.right != null) {
                return false;
            }
        } else if (!right.equals(other.right)) {
            return false;
        }
        return true;
    }
}
