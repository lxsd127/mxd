/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server;

import java.util.HashMap;
import java.util.Map;

public class MapleShopFactory {

    private Map<Integer, MapleShop> shops = new HashMap<Integer, MapleShop>();
    private Map<Integer, MapleShop> npcShops = new HashMap<Integer, MapleShop>();
    private static MapleShopFactory instance = new MapleShopFactory();

    public static MapleShopFactory getInstance() {
        return instance;
    }

    public void clear() {
        shops.clear();
        npcShops.clear();
    }

    public MapleShop getShop(int shopId) {
        if (shops.containsKey(shopId)) {
            return shops.get(shopId);
        }
        return loadShop(shopId, true);
    }

    public MapleShop getShopForNPC(int npcId) {
        if (npcShops.containsKey(npcId)) {
            return npcShops.get(npcId);
        }
        return loadShop(npcId, false);
    }

    private MapleShop loadShop(int id, boolean isShopId) {
        MapleShop ret = MapleShop.createFromDB(id, isShopId);
        if (ret != null) {
            shops.put(ret.getId(), ret);
            npcShops.put(ret.getNpcId(), ret);
        } else if (isShopId) {
            shops.put(id, null);
        } else {
            npcShops.put(id, null);
        }
        return ret;
    }
}
