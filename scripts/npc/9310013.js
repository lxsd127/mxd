var status = 0;
var cost = 50000;
function start() {
    cm.sendYesNo("��ô�����Ϻ���̲���ȷʵ����ɣ�������� #b50000 ���#k. �ҾͿ��Դ���� #b��ʿ����#k ��ô����Ҫ��ȥ��");
}

function action(mode, type, selection) {
    if (mode != 1) {
        if (mode == 0)
        cm.sendOk("��������ı���������̲�羰�������ˡ�");
        cm.dispose();
        return;
    }
    status++;
    if (status == 1) {
		if(cm.getMeso() < cost) {
		cm.sendOk("��ȷ������ #b50000 ���#k�� ���û�У��ҿɲ����������ȥ��");
		cm.dispose();
		} else {
		cm.gainMeso(-cost);
		cm.warp(102000000, 0);
        cm.dispose();
    }
}
}