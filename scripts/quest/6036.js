var status = -1;

function start(mode, type, selection) {
}

function end(mode, type, selection) {
        if (qm.haveItem(4031980)) {
            qm.gainItem(4031980, -1);
            qm.forceCompleteQuest(6036);
            qm.teachSkill(1007, 3);
            qm.dispose();
            return;
        } else {
            cm.sendOk("#b��û�лƽ����ӡ�~");
            qm.dispose();
            return;
        }
    
}
