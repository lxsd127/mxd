/* 
	NPC Name: 		Sunny
	Map(s): 		Orbis: Station<To Ludibrium> (200000121)
	Description: 		Orbis Ticketing Usher
*/
var status = 0;

function start() {
    status = -1;
    train = cm.getEventManager("Trains");
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
	if(train == null) {
	    cm.sendNext("����δ֪����");
	    cm.dispose();
	} else if (train.getProperty("entry").equals("true")) {
	    cm.sendYesNo("�ǳ��ã����ϻ����㹻��λ�ã���׼������Ĵ�Ʊ�����ǽ��������������У����ǲ������ϴ���");
	} else if (train.getProperty("entry").equals("false") && train.getProperty("docked").equals("true")) {
	    cm.sendNext("���ɴ��Ѿ���������ȴ���һ�κ��ࡣ");
	    cm.dispose();
	} else {
	    cm.sendNext("�ɴ�����ǰ5������ֹͣ��Ʊ����ע��ʱ�䡣");
	    cm.dispose();
	}
    } else if(status == 1) {
     if(!cm.haveItem(4031074)) {
	    cm.sendNext("��! ��û��#b#t4031074##k�����Ҳ��������ϴ�!");
	} else {
           cm.gainItem(4031074, -1); 
           cm.warp(200000122, 0);
        }
	cm.dispose();
    }
}