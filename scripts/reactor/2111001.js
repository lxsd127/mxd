/*
Zakum Altar - Summons Zakum.
*/

function act() {
    rm.changeMusic("Bgm06/FinalFight");
	rm.getMap().spawnZakum(-10, -215);
    rm.mapMessage("���ڻ���֮�۵�����,�������ٻ�.");
	if (!rm.getPlayer().isGM()) {
		rm.getMap().startSpeedRun();
	}
}
