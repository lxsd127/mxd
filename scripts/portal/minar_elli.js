function enter(pi) {
try {
    if (pi.haveItem(4031346)) {
	if (pi.getMapId() == 240010100) {
	    pi.playPortalSE();
	    pi.warp(101010000, "minar00");
	} else {
	    pi.playPortalSE();
	    pi.warp(240010100, "elli00");
	}
	pi.gainItem(4031346, -1);
	pi.playerMessage("��Ϊħ�����ӵ������������͵�δ֪����");
	return true;
    } else {
	pi.playerMessage("û��ħ������������������");
	return false;
    }
} catch (e) {
    pi.playerMessage("Error: " + e);
}
}
