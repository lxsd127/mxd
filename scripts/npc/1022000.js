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
        cm.sendOk("请重试.");
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
                cm.sendNext("你想成为一位伟大的#r战士#k吗?");
            } else {
                cm.sendOk("成为一个战士需要达到 #r10#k 级,你还不能转职成为 #r战士#k。");
                cm.dispose();
            }
        } else {
            if (cm.getPlayer().getLevel() >= 30 && cm.getJob() == 100) { // 剑士
                if (cm.haveItem(4031012, 1)) {
                    if (cm.haveItem(4031012, 1)) {
                        status = 20;
                        cm.sendNext("我知道你已经完成了转职测试!");
                    } else {
                        if (!cm.haveItem(4031008)) {
                            cm.gainItem(4031008, 1);
                        }
                        cm.sendOk("请去找 #r战士转职教官#k.")
                        cm.dispose();
                    }
                } else {
                    status = 10;
                    cm.sendNext("你或许可以做转职的准备.让我看看...");
                }
            } else if (cm.getPlayer().getLevel() >= 70 && cm.getJob() == 110 || cm.getJob() == 120 || cm.getJob() == 130 || cm.getJob() == 2110) {
				if(cm.haveItem(4031057, 1)){
			    cm.sendOk("你完成了一个考验，现在去找 #泰勒斯#k.位于冰封雪域#b长老公馆#k!");
               } if (cm.haveItem(4031059, 1)) {
                    cm.gainItem(4031057, 1);
                    cm.gainItem(4031059, -1);
                  //cm.warp(211000001, 0);//长老公馆
                    cm.sendOk("你完成了一个考验，现在去找冰封雪域长老公馆#b泰勒斯#k.");
                } else {
                    cm.sendOk("嗨, #b#h0##k!,我又见到你了,你比起上一次强大了许多！我需要一个#b黑符#k.快去找异次元空间拿给我!");
                }
                cm.dispose();
            } else {
                cm.sendOk("战士拥有强健的体魄与无尽的力量。");
                cm.dispose();
            }
        }
    } else if (status == 1) {
        cm.sendNextPrev("一旦转职了就不能反悔。");
    } else if (status == 2) {
        cm.sendYesNo("你真的要成为一位 #r战士#k ?");
    } else if (status == 3) {
        if (cm.getJob() == 0) {
            cm.changeJob(100); // 剑士
            cm.resetStats(4, 4, 4, 4);
        }
        cm.gainItem(1402001, 1);
        cm.sendOk("转职成功!");
        cm.dispose();
    } else if (status == 11) {
        cm.sendNextPrev("你可以选择你要转职成为一位 #r剑客#k, #r准骑士#k 或 #r枪战士#k。")
    } else if (status == 12) {
        cm.askAcceptDecline("但是我必须先测试你,你准备好了吗 ?");
    } else if (status == 13) {
        cm.gainItem(4031008, 1);
       //cm.warp(102020300);
        cm.sendOk("请去找 #b二转战士转职教官#k .他在#b西部岩山IV#k,他会帮助你的!");
        cm.dispose();
    } else if (status == 21) {
        cm.sendSimple("你想要成为什么 ? #b\r\n#L0#剑客#l\r\n#L1#准骑士#l\r\n#L2#枪战士#l#k");
    } else if (status == 22) {
        var jobName;
        if (selection == 0) {
            jobName = "剑客";
            job = 110; // FIGHTER
        } else if (selection == 1) {
            jobName = "准骑士";
            job = 120; // PAGE
        } else {
            jobName = "枪战士";
            job = 130; // SPEARMAN
        }
        cm.sendYesNo("你真的要成为一位 #r" + jobName + "#k?");
    } else if (status == 23) {
        cm.changeJob(job);
        cm.gainItem(4031012, -1);
        cm.sendOk("转职成功!");
        cm.dispose();
    }
}