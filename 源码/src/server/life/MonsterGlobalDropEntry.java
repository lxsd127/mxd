/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server.life;

public class MonsterGlobalDropEntry {
    public byte dropType;
    public int itemId, chance, Minimum, Maximum, continent, questid;
    public boolean onlySelf = false;

    public MonsterGlobalDropEntry(int itemId, int chance, int continent, byte dropType, int Minimum, int Maximum, int questid) {
        this.itemId = itemId;
        this.chance = chance;
        this.dropType = dropType;
        this.continent = continent;
        this.questid = questid;
        this.Minimum = Minimum;
        this.Maximum = Maximum;
    }

    public MonsterGlobalDropEntry(int itemId, int chance, int continent, byte dropType, int Minimum, int Maximum, int questid, boolean onlySelf) {
        this.itemId = itemId;
        this.chance = chance;
        this.dropType = dropType;
        this.continent = continent;
        this.questid = questid;
        this.Minimum = Minimum;
        this.Maximum = Maximum;
        this.onlySelf = onlySelf;
    }
}
