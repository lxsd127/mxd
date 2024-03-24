/* Dances with Balrog
    Warrior Job Advancement
    Victoria Road : Warriors' Sanctuary (102000003)

    Custom Quest 100003, 100005
*/

var status = 0;
var jobId;
var jobName;


function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 2) {
        cm.sendOk("������.");
        cm.dispose();
        return;
    }
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
        if (cm.getJob() == 0) {
            if (cm.getPlayer().getLevel() >= 10) {
                cm.sendNext("�����Ϊһλΰ���#rսʿ#k��?");
            } else {
                cm.sendOk("��Ϊһ��սʿ��Ҫ�ﵽ #r10#k ��,�㻹����תְ��Ϊ #rսʿ#k��");
                cm.dispose();
            }
        } else {
            if (cm.getPlayer().getLevel() >= 30 && cm.getJob() == 100) { // ��ʿ
                if (cm.haveItem(4031012, 1)) {
                    if (cm.haveItem(4031012, 1)) {
                        status = 20;
                        cm.sendNext("��֪�����Ѿ������תְ����!");
                    } else {
                        if (!cm.haveItem(4031008)) {
                            cm.gainItem(4031008, 1);
                        }
                        cm.sendOk("��ȥ�� #rսʿתְ�̹�#k.")
                        cm.dispose();
                    }
                } else {
                    status = 10;
                    cm.sendNext("����������תְ��׼��.���ҿ���...");
                }
            } else if (cm.getPlayer().getLevel() >= 70 && cm.getJob() == 110 || cm.getJob() == 120 || cm.getJob() == 130 || cm.getJob() == 2110) {
				if(cm.haveItem(4031057, 1)){
			    cm.sendOk("�������һ�����飬����ȥ�� #̩��˹#k.λ�ڱ���ѩ��#b���Ϲ���#k!");
               } if (cm.haveItem(4031059, 1)) {
                    cm.gainItem(4031057, 1);
                    cm.gainItem(4031059, -1);
                  //cm.warp(211000001, 0);//���Ϲ���
                    cm.sendOk("�������һ�����飬����ȥ�ұ���ѩ���Ϲ���#b̩��˹#k.");
                } else {
                    cm.sendOk("��, #b#h0##k!,���ּ�������,�������һ��ǿ������࣡����Ҫһ��#b�ڷ�#k.��ȥ�����Ԫ�ռ��ø���!");
                }
                cm.dispose();
            } else {
                cm.sendOk("սʿӵ��ǿ�����������޾���������");
                cm.dispose();
            }
        }
    } else if (status == 1) {
        cm.sendNextPrev("һ��תְ�˾Ͳ��ܷ��ڡ�");
    } else if (status == 2) {
        cm.sendYesNo("�����Ҫ��Ϊһλ #rսʿ#k ?");
    } else if (status == 3) {
        if (cm.getJob() == 0) {
            cm.changeJob(100); // ��ʿ
            cm.resetStats(4, 4, 4, 4);
        }
        cm.gainItem(1402001, 1);
        cm.sendOk("תְ�ɹ�!");
        cm.dispose();
    } else if (status == 11) {
        cm.sendNextPrev("�����ѡ����Ҫתְ��Ϊһλ #r����#k, #r׼��ʿ#k �� #rǹսʿ#k��")
    } else if (status == 12) {
        cm.askAcceptDecline("�����ұ����Ȳ�����,��׼�������� ?");
    } else if (status == 13) {
        cm.gainItem(4031008, 1);
       //cm.warp(102020300);
        cm.sendOk("��ȥ�� #b��תսʿתְ�̹�#k .����#b������ɽIV#k,����������!");
        cm.dispose();
    } else if (status == 21) {
        cm.sendSimple("����Ҫ��Ϊʲô ? #b\r\n#L0#����#l\r\n#L1#׼��ʿ#l\r\n#L2#ǹսʿ#l#k");
    } else if (status == 22) {
        var jobName;
        if (selection == 0) {
            jobName = "����";
            job = 110; // FIGHTER
        } else if (selection == 1) {
            jobName = "׼��ʿ";
            job = 120; // PAGE
        } else {
            jobName = "ǹսʿ";
            job = 130; // SPEARMAN
        }
        cm.sendYesNo("�����Ҫ��Ϊһλ #r" + jobName + "#k?");
    } else if (status == 23) {
        cm.changeJob(job);
        cm.gainItem(4031012, -1);
        cm.sendOk("תְ�ɹ�!");
        cm.dispose();
    }
}