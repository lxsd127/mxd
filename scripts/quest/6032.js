var status = -1;

function start(mode, type, selection) {
}

function end(mode, type, selection) {
    if (qm.getMeso() > 10000) {
        qm.gainMeso(-10000);
        qm.forceCompleteQuest();
        //qm.forceStartQuest(6032);
        qm.forceCompleteQuest(6029);
        qm.teachSkill(1007, 1);
        qm.sendNext("��ȡ������˹�ļ����γ̡����Ǽ����γ̱Ƚ�����˼��");
    } else {
        qm.sendNext("��û��10000���,���޷��̵����������γ̡�");
    }
    qm.dispose();
}
