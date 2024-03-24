/* Author: Xterminator
	NPC Name: 		Trainer Frod
	Map(s): 		Victoria Road : Pet-Walking Road (100000202)
	Description: 		Pet Trainer
*/
var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status >= 0 && mode == 0) {
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 0) {
	if (cm.haveItem(4031035)) {
	    cm.sendNext("����~ �����Ҹ����Űɣ����ֹ��Ҳ�����̰���˰ɣ��ţ���~ �㰴�Ҹ�˵�ģ�һ·�ϴ��ų���һ���������𣿺ã�����ô����������ˣ��Ҹ�������������֮������ܶȡ�");
	} else {
	    cm.sendOk("�Ҹ�����ҹ�����Щѵ�������õ��ϰ��豸���������������ң������ȥ����������...�����Ҹ翴������������һ���~");
	    cm.dispose();
	}
    } else if (status == 1) {
	if (cm.getPlayer().getPet(0) == null) {
	    cm.sendNextPrev("�ţ�����ĳ������Ķ�������Ϊ����׼�����ϰ����û�г���Ϊʲô����������ȥ�ɣ�");
	} else {
	    cm.gainItem(4031035, -1);
	    cm.gainClosenessAll(2);
	    cm.sendNextPrev("��ô�����ǲ��Ǿ��ó������������ˣ��Ժ����пյ�ʱ�򣬾��������������ɡ����������ζ����ԡ�����������ȵõ��Ҹ�����ɡ�");
	}
	cm.dispose();
    }
}