var status = -1;

function start(mode, type, selection) {
    if (qm.getQuestStatus(2186) == 1) {
        status++;
        if (status == 0) {
            qm.sendNext("�ǳ���л������һ����۾�!\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#v2030019# 5�� #t2030019#\r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0#  1000 ����");
        } else {
            qm.gainItem(4031853, -1);
            qm.gainItem(2030019, 5);
            qm.gainExp(1000);
            qm.forceCompleteQuest();
            qm.dispose();
        }
    } else {
        qm.forceStartQuest();
        qm.dispose();
    }
}
function end(mode, type, selection) {
    if (qm.getQuestStatus(2186) == 1) {
        status++;
        if (status == 0) {
            qm.sendNext("�ǳ���л������һ����۾�!\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#v2030019# 5�� #t2030019#\r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0#  1000 ����");
        } else {
            qm.gainItem(4031853, -1);
            qm.gainItem(2030019, 5);
            qm.gainExp(1000);
            qm.forceCompleteQuest();
            qm.dispose();
        }
    } else {
        qm.forceStartQuest();
        qm.dispose();
    }
}
