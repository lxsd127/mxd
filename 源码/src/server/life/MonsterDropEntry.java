/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server.life;

public class MonsterDropEntry {
    public int itemId, chance, Minimum, Maximum, questid;
    
    public MonsterDropEntry(int itemId, int chance, int Minimum, int Maximum, int questid) {
        this.itemId = itemId;
        this.chance = chance;
        this.questid = questid;
        this.Minimum = Minimum;
        this.Maximum = Maximum;
    }
}
