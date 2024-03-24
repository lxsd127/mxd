﻿/*  NPC : 漢摩尼亞
 劍士 4轉 任務腳本
 地圖代碼 (240010501)
 */

var status = -1;
var pass = false;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1)
        status++;
    else
        status--;

    if (status == 0) {
        if (!(cm.getJob() == 111 || cm.getJob() == 121 || cm.getJob() == 131)) {
            cm.sendOk("为什么你要见我??哈有你想要问我关于什么事情??");
            cm.dispose();
            return;
        } else if (cm.getPlayer().getLevel() < 120) {
            cm.sendOk("你等级尚未到达120级.");
            cm.dispose();
            return;
        } else {
            if (cm.getQuestStatus(6904) == 2) {
                pass = true;
            }
            if (cm.getJob() == 111) {
                cm.sendSimple("恭喜你有资格4转. \r\n请问你想4转吗??\r\n#b#L0#我想成为英雄.#l\r\n#b#L1#让我想一下...#l");
            } else if (cm.getJob() == 121) {
                cm.sendSimple("恭喜你有资格4转. \r\n请问你想4转吗??\r\n#b#L0#我想成为圣骑士.#l\r\n#b#L1#让我想一下...#l");
            } else if (cm.getJob() == 131) {
                cm.sendSimple("恭喜你有资格4转. \r\n请问你想4转吗??\r\n#b#L0#我想成为黑骑士.#l\r\n#b#L1#让我想一下...#l");
            } else {
                cm.sendOk("好吧，假如你想要4转麻烦再来找我。");
                cm.dispose();
                return;
            }
        }
    } else if (status == 1) {
        if (selection == 1) {
            cm.sendOk("好吧，假如你想要4转麻烦再来找我。");
            cm.dispose();
            return;
        }
        if (cm.getPlayerStat("RSP") > (cm.getPlayerStat("LVL") - 120) * 3) {
            cm.sendOk("你的技能点数还没用完..");
            cm.dispose();
            return;
        }
        if (pass) {
            cm.sendNext("即将四转。");
        } else {
            if (!cm.haveItem(4031860) || !cm.haveItem(4031861)) {
                cm.sendOk("我需要#t4031860# x1 #t4031861# x1。");
                cm.dispose();
                return;
            } else {
                cm.sendNext("即将四转。");
            }
        }
    } else if (status == 2) {
        if (cm.canHold(2280003)) {
            cm.gainItem(2280003, 1);
            if (cm.getJob() == 111) {
                cm.changeJob(112);
                cm.teachSkill(1121001, 0, 10);
                cm.teachSkill(1120004, 0, 10);
                cm.teachSkill(1121008, 0, 10);
                cm.gainItem(4031860, -1);
                cm.gainItem(4031861, -1);
                cm.sendNext("恭喜你成为 #b英雄#k.我送你一些神秘小礼物^^");
            } else if (cm.getJob() == 121) {
                cm.changeJob(122);
                cm.teachSkill(1221001, 0, 10);
                cm.teachSkill(1220005, 0, 10);
                cm.teachSkill(1221009, 0, 10);
                cm.gainItem(4031860, -1);
                cm.gainItem(4031861, -1);
                cm.sendNext("恭喜你成为 #b圣骑士#k.我送你一些神秘小礼物^^");
            } else if (cm.getJob() == 131) {
                cm.changeJob(132);
                cm.teachSkill(1321001, 0, 10);
                cm.teachSkill(1320005, 0, 10);
                cm.teachSkill(1321007, 0, 10);
                cm.gainItem(4031860, -1);
                cm.gainItem(4031861, -1);
                cm.sendNext("恭喜你成为 #b黑骑士#k.我送你一些神秘小礼物^^");
            }
        } else {
            cm.sendOk("你的背包没有多余的栏位。");
            cm.dispose();
            return;
        }

    } else if (status == 3) {
        cm.sendNext("不要忘记这一切取决于你的努力。");
    } else if (status == 4) {
        cm.sendNextPrev("我以你为荣。");
        cm.dispose();
    }
}