/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package handling.world.exped;

public enum ExpeditionType {
    Normal_Balrog(15, 2001, 50, 200),
    Horntail(30, 2003, 80, 200),
    Zakum(30, 2002, 50, 200),
    Chaos_Zakum(30, 2005, 100, 200),
    ChaosHT(30, 2006, 110, 200),
    Pink_Bean(30, 2004, 140, 200),
    CWKPQ(30, 2007, 90, 200),
    Von_Leon(30, 2008, 120, 200),
    Cygnus(18, 2009, 170, 200),
    Arkarium(18, 2010, 120, 200);

    public int maxMembers, maxParty, exped, minLevel, maxLevel;
    
    private ExpeditionType(int maxMembers, int exped, int minLevel, int maxLevel) {
	this.maxMembers = maxMembers;
	this.exped = exped;
	this.maxParty = (maxMembers / 2) + (maxMembers % 2 > 0 ? 1 : 0);
	this.minLevel = minLevel;
	this.maxLevel = maxLevel;
    }

    public static ExpeditionType getById(int id) {
	for (ExpeditionType pst : ExpeditionType.values()) {
	    if (pst.exped == id) {
		return pst;
	    }
	}
	return null;
    }
}
