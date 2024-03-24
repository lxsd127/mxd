/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package client.inventory;

public class InventoryException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InventoryException() {
        super();
    }

    public InventoryException(String msg) {
        super(msg);
    }
}
