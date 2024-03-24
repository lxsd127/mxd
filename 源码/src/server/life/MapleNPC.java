/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server.life;

import client.MapleClient;
import server.MapleShopFactory;
import server.maps.MapleMapObjectType;
import tools.packet.MaplePacketCreator;

public class MapleNPC extends AbstractLoadedMapleLife {

    private String name = "MISSINGNO";
    private boolean custom = false;

    public MapleNPC(final int id, final String name) {
        super(id);
        this.name = name;
    }

    public final boolean hasShop() {
        return MapleShopFactory.getInstance().getShopForNPC(getId()) != null;
    }

    public final void sendShop(final MapleClient c) {
        MapleShopFactory.getInstance().getShopForNPC(getId()).sendShop(c);
    }

    @Override
    public void sendSpawnData(final MapleClient client) {
        if (getId() >= 9901000 || getId() == 9209001) {
            return;
        } else {
            client.getSession().write(MaplePacketCreator.spawnNPC(this, true));
            client.getSession().write(MaplePacketCreator.spawnNPCRequestController(this, true));
        }
    }

    @Override
    public final void sendDestroyData(final MapleClient client) {
        client.getSession().write(MaplePacketCreator.removeNPCController(getObjectId()));
        client.getSession().write(MaplePacketCreator.removeNPC(getObjectId()));
    }

    @Override
    public final MapleMapObjectType getType() {
        return MapleMapObjectType.NPC;
    }

    public final String getName() {
        return name;
    }

    public void setName(String n) {
        this.name = n;
    }

    public final boolean isCustom() {
        return custom;
    }

    public final void setCustom(final boolean custom) {
        this.custom = custom;
    }
}
