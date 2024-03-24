/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server.life;

public class OverrideMonsterStats {
    public long hp;
    public int exp, mp;

    public OverrideMonsterStats() {
        hp = 1;
        exp = 0;
        mp = 0;
    }

    public OverrideMonsterStats(long hp, int mp, int exp, boolean change) {
        this.hp = /*change ? (hp * 3L / 2L) : */ hp;
        this.mp = mp;
        this.exp = exp;
    }

    public OverrideMonsterStats(long hp, int mp, int exp) {
        this(hp, mp, exp, true);
    }

    public int getExp() {
        return exp;
    }

    public void setOExp(int exp) {
        this.exp = exp;
    }

    public long getHp() {
        return hp;
    }

    public void setOHp(long hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setOMp(int mp) {
        this.mp = mp;
    }
}
