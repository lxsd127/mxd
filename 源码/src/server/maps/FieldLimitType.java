/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server.maps;

public enum FieldLimitType {
    Jump(0x1),
    MovementSkills(0x2),
    SummoningBag(0x04),
    MysticDoor(0x08),
    ChannelSwitch(0x10),
    RegularExpLoss(0x20),
    VipRock(0x40),
    Minigames(0x80),
    Mount(0x200),
    PotionUse(0x400), //or 0x40000
    Event(0x2000),
    Pet(0x8000), //needs confirmation
    Event2(0x10000),
    DropDown(0x20000);
    
    private final int i;

    private FieldLimitType(int i) {
        this.i = i;
    }

    public final int getValue() {
        return i;
    }

    public final boolean check(int fieldlimit) {
        return (fieldlimit & i) == i;
    }
}
