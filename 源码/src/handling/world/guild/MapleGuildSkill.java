/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package handling.world.guild;

public class MapleGuildSkill implements java.io.Serializable {
    public static final long serialVersionUID = 3565477792055301248L;
    public String purchaser, activator;
    public long timestamp;
    public int skillID, level;

    public MapleGuildSkill(final int skillID, final int level, final long timestamp, final String purchaser, final String activator) {
        this.timestamp = timestamp;
	this.skillID = skillID;
	this.level = level;
	this.purchaser = purchaser;
	this.activator = activator;
    }
}
