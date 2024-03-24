/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server.shops;

import client.inventory.Item;

public class MaplePlayerShopItem {
    public Item item;
    public short bundles;
    public int price;

    public MaplePlayerShopItem(Item item, short bundles, int price) {
        this.item = item;
        this.bundles = bundles;
        this.price = price;
    }
}
