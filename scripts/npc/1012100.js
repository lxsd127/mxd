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
		cm.sendNext("你想成为一位伟大的#r弓箭手#k吗?");
	    } else {
		cm.sendOk("你还没有资格能转职成为#r弓箭手#k");
		cm.dispose();
	    }
	} else {
	    if (cm.getPlayer().getLevel() >= 30 && cm.getJob() == 300) { // 弓箭手
		if (cm.haveItem(4031012, 1)) {
		    if (cm.haveItem(4031012, 1)) {
			status = 20;
			cm.sendNext("我知道你已经完成了转职测试!");
		    } else {
			if (!cm.haveItem(4031010)) {
			    cm.gainItem(4031010, 1);
			}
			cm.sendOk("请去找 #r二转职业教官#k.")
			cm.dispose();
		    }
		} else {
		    status = 10;
		    cm.sendNext("你或许可以做转职的准备.让我看看...");
		}
	    } else if (cm.getPlayer().getLevel() >= 70 && cm.getJob() == 310 || cm.getJob() == 320) {
			if(cm.haveItem(4031057, 1)){
			cm.sendOk("你完成了一个考验，现在去找 #b蕾妮#k.位于冰封雪域#b长老公馆#k!");
			} else if (cm.haveItem(4031059,1)) {
			cm.gainItem(4031057,1);
			cm.gainItem(4031059, -1);
			//cm.warp(211000001, 0);
		    cm.sendOk("你完成了一个考验，现在去找#b蕾妮#k.");
		} else {
		    cm.sendOk("嗨, #b#h0##k! 我需要一个 #b黑符#k. 快去找异次元空间拿给我");
		}
		cm.dispose();
	    } else {
		cm.sendOk("你好,我是弓箭手转职官.");
		cm.dispose();
	    }
	}
    } else if (status == 1) {
	cm.sendNextPrev("一旦转职了就不能反悔。");
    } else if (status == 2) {
	cm.sendYesNo("你真的要成为一位 #r弓箭手#k ?");
    } else if (status == 3) {
	if (cm.getJob() == 0) {
		cm.changeJob(300); // 弓箭手
		cm.resetStats(4, 4, 4, 4);
	}
	cm.forceCompleteQuest(6700);
	cm.gainItem(1452002, 1);
	cm.gainItem(2060000, 1000);
	cm.sendOk("转职成功!");
	cm.dispose();
    } else if (status == 11) {
	cm.sendNextPrev("你可以选择你要转职成为一位 #r猎手#k, #r弩弓手#k.")
    } else if (status == 12) {
	cm.askAcceptDecline("但是我必须先测试你,你准备好了吗 ?");
    } else if (status == 13) {
	cm.gainItem(4031010, 1);
	//cm.warp(106010000);
	cm.sendOk("请去找 #b二转转职教官#k 位于#b迷宫通道#k,他会帮助你的!");
	cm.dispose();
    } else if (status == 21) {
	cm.sendSimple("你想要成为什么 ? #b\r\n#L0#猎手#l\r\n#L1#弩弓手#l#k");
    } else if (status == 22) {
	var jobName;
	if (selection == 0) {
	    jobName = "猎手";
	    job = 310;
	} else if (selection == 1) {
	    jobName = "弩弓手";
	    job = 320;
	}
	cm.sendYesNo("你真的要成为一位 #r" + jobName + "#k?");
    } else if (status == 23) {
	cm.changeJob(job);
	cm.gainItem(4031012, -1);
	cm.sendOk("转职成功!");
	cm.dispose();
    }
}	
