/* guild emblem npc */
var status = 0;
var sel;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 0) {
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;

    if (status == 0)
	cm.sendSimple("��ã��������ţ����������µ����ҵ��\r\n#b#L0#����/�ı�������#l#k");
    else if (status == 1) {
	sel = selection;
	if (selection == 0) {
	    if (cm.getPlayerStat("GRANK") == 1)
		cm.sendYesNo("�������߸ı���������Ҫ#b10,000,000#k, �Ƿ������");
	    else
		cm.sendOk("�㲻�Ǽ����峤������㲻�ܴ����͸ı������£���ת������峤������������..");
	}
				
    } else if (status == 2) {
	if (sel == 0) {
	    cm.genericGuildMessage(17);

	    cm.dispose();
	}
    }
}
