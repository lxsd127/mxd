/*
Lord Jonathan - Nautilus' Port
*/

function start() {
    if (cm.getJob() == 522 && cm.getPlayerStat("LVL") >= 120) {
	if (!cm.hasSkill(5221003)) {
            cm.forceCompleteQuest(6400);
	    cm.teachSkill(5221003, 0, 10);
	}
    }
    cm.sendOk("����˭�����ڸ���˵���������ֻ�����ģ�ȥ�҄e�ˣ���");
}

function action(mode, type, selection) {
    cm.dispose();
}
