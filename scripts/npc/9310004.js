function start() {
	if ((cm.getQuestStatus(4103) == 1 && cm.haveItem(4031289 ,1)) || cm.getQuestStatus(8510) == 2) {
		cm.warp(701010321);
		cm.dispose();
		} else {
		cm.sendOk("Ҫô���Ѿ������#r����#k������������񣬻�û���#bũ�񲮲�#k�İ��С������Ҳ��ܴ����ȥ��");
		cm.dispose();
	}
}