var status = 0;
var cost = 50000;
function start() {
    cm.sendYesNo("�ˣ����Ǻ��ռ�ʻԱ���Ҹ����ʻ�����Ϻ��ķɻ�����������ķ��У��ҵļ�ʻ�����Ѿ����˲��á�������� #b50000 ���#k. �ҾͿ��Դ���ȥ������ #b�Ϻ���̲#k ��ô����Ҫȥ��");
}

function action(mode, type, selection) {
    if (mode != 1) {
        if (mode == 0)
        cm.sendOk("�������Ϻ���̲���ѵ��㲻��ȥ���������ź���");
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
		cm.warp(701000100, 0);
        cm.dispose();
    }
}
}