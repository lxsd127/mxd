var status = -1;

function start(mode, type, selection) {
}

function end(mode, type, selection) {
    if (qm.getMeso() > 10000) {
        qm.gainMeso(-10000);
        qm.forceCompleteQuest();
        qm.forceStartQuest(6031);
        qm.sendNext("��������ǵ������Э��᳤��ɭ����ѧϰ�����ĵ��������γ̡��������ڽ����ˣ�");
    } else {
        qm.sendNext("��û��10000���,���޷��̵����������γ̡�");
    }
    qm.dispose();
}
