var status = -1;

function start(mode, type, selection) {
}

function end(mode, type, selection) {
    if (qm.getMeso() > 10000) {
        qm.gainMeso(-10000);
        qm.forceCompleteQuest();
        qm.forceStartQuest(6032);
        qm.sendNext("����������������Ŀ�ѧ�γ̡����������˴����񣡻��ã����ڽ����ˣ�");
    } else {
        qm.sendNext("��û��10000���,���޷��̵����������γ̡�");
    }
    qm.dispose();
}
