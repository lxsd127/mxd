/*
	NPC Name: 		Rini
	Map(s): 		Orbis: Station<To Ellinia> (200000111)
	Description: 		Orbis Ticketing Usher
*/
var status = 0;

function start() {
    status = -1;
    boat = cm.getEventManager("Boats");
    action(1, 0, 0);
}

function action(mode, type, selection) {
    status++;
    if(mode == 0) {
	cm.sendNext("�㻹��ʲô����������û�������");
	cm.dispose();
	return;
    }
    if (status == 0) {
	if(boat == null) {
	    cm.sendNext("����δ֪����");
	    cm.dispose();
	} else if(boat.getProperty("entry").equals("true")) {
	    cm.sendYesNo("�ǳ��ã����ϻ����㹻��λ�ã���׼������Ĵ�Ʊ�����ǽ��������������У����ǲ�����Ǵ���");
	} else if(boat.getProperty("entry").equals("false") && boat.getProperty("docked").equals("true")) {
	    cm.sendNext("�ܱ�Ǹ������Ĵ��Ѿ�����,����ʱ������ͨ����Ʊչ̨�鿴");
	    cm.dispose();
	} else {
	    cm.sendNext("�ɴ����ǰ5������ֹͣ��Ʊ����ע��ʱ�䡣");
	    cm.dispose();
	}
    } else if(status == 1) {
        if(!cm.haveItem(4031047)) {
	    cm.sendNext("��!��û��#b#t4031045##k�����Ҳ��������ϴ�!.");
	} else {
        cm.gainItem(4031047, -1);
	cm.warp(200000112, 0);
        }
	cm.dispose();
    }
}
