/*
	? - Victoria Road: Pet-Walking Road (100000202)
*/

var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status >= 0 && mode == 0) {
	cm.sendNext("#b(�қ]����̫�࣬�����қ]��ȥ������)");
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 0) {
	cm.sendYesNo("#b(���ܿ����Ķ����ڲݴ��С���Ӧ�ð����ҳ�����)");
    } else if (status == 1) {
	cm.sendNext("#b(��...���ǳ���ı��!)");
	cm.gainItem(4031922, 1);
	cm.dispose();
    }
}