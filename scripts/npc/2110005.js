var status = -1;

function start() {

    action(1,0,0);
}

function action(mode, type, selection) {
    if (mode != 1) {
		if (status == 0 && mode == 0) {
			cm.sendNext("�����¿���һ�£�Ȼ���ٺ���˵����");
		}
        cm.dispose();
        return;
    }
    status++;
    if (status == 0) {
        if (cm.getMapId() == 260020000 || cm.getMapId() == 260000000)
            cm.sendYesNo("�������#b�����а�#k�����������Ĵ�ׯ#b�������#kȥ�𣿼۸���#b1500���#k��");
        else if (cm.getMapId() == 260020700 || cm.getMapId() == 261000000)
            cm.sendYesNo("�������#b�����а�#k����������Ĵ�ׯ#b���ﰲ��#kȥ�𣿼۸���#b1500���#k��");
    } else if (status == 1) {
		if(cm.getMeso() >= 1500){
			cm.gainMeso(-1500);
			cm.warp((cm.getMapId() == 260020000 || cm.getMapId() == 260000000) ? 261000000 : (cm.getMapId() == 260020700 || cm.getMapId()) == 261000000 ? 260000000 : 260000000);
		} else {
			cm.sendNext("�����û����Ǯ����");
		}
		cm.dispose();
    }
}