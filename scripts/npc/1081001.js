/**
	Pison - Florina Beach(110000000)
**/
var status = -1;
var returnmap = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	cm.sendNext("��������������Щ�»�û�а��������ƣ����ʱ����ƽ�̲��Ϣ����һ��Ҳ����");
	cm.safeDispose();
	return;
    }
    if (status == 0) {
	returnmap = cm.getSavedLocation("FLORINA");
	cm.sendNext("����#b#m110000000##k��û�����������?�������Ҫ�Ļ���������ȥ#b#m"+returnmap+"##k�ɣ�");
    } else if (status == 1) {
	cm.sendYesNo("���#b#m"+returnmap+"##k�𣿺�~������׼�������ɡ��ǡ���������ȥ#m"+returnmap+"#��")
    } else if (status == 2) {
	if (returnmap < 0) {
		returnmap = 104000000;
	}
	cm.warp(returnmap);
	cm.clearSavedLocation("FLORINA");
	cm.dispose();
    }
}
