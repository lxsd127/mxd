/* guild creation npc */
var status = -1;
var sel;

function start() {
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
	cm.sendSimple("��ӭ�������幫�ݣ�����������ʲô��?\r\n#b#L0#��������#l\r\n#L1#��ɢ����#l\r\n#L2#���Ӽ����Ա��������#l#k");
    else if (status == 1) {
	sel = selection;
	if (selection == 0) {
	    if (cm.getPlayerStat("GID") > 0) {
		cm.sendOk("���Ѿ�ӵ�м����ˣ������ٴ������塣");
		cm.dispose();
	    } else
		cm.sendYesNo("����һ���µļ�����Ҫ#b20,000,000���#k, ��ȷ����������һ���µļ�����");
	} else if (selection == 1) {
	    if (cm.getPlayerStat("GID") <= 0 || cm.getPlayerStat("GRANK") != 1) {
		cm.sendOk("�㻹û�м�����㲻���峤������㲻�ܽ�ɢ�ü��塣");
		cm.dispose();
	    } else
		cm.sendYesNo("��ȷ�����Ҫ��ɢ��ļ��壿����ɢ���㽫���ָܻ����м�����������Լ�GP����ֵ���Ƿ������");
	} else if (selection == 2) {
	    if (cm.getPlayerStat("GID") <= 0 || cm.getPlayerStat("GRANK") != 1) {
		cm.sendOk("�㲻���峤������㽫�������Ӽ����Ա���������ޡ�");
		cm.dispose();
	    } else
		cm.sendYesNo("�����Ա����ÿ����#b5#kλ��Ҫ֧��#b5,000,000���#k, ��ȷ��Ҫ������");
	} 
    } else if (status == 2) {
	if (sel == 0 && cm.getPlayerStat("GID") <= 0) {
	    cm.genericGuildMessage(1);
	    cm.dispose();
	} else if (sel == 1 && cm.getPlayerStat("GID") > 0 && cm.getPlayerStat("GRANK") == 1) {
	    cm.disbandGuild();
	    cm.dispose();
	} else if (sel == 2 && cm.getPlayerStat("GID") > 0 && cm.getPlayerStat("GRANK") == 1) {
	    cm.increaseGuildCapacity(false);
	    cm.dispose();
	} 
    }
}