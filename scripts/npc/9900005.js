var 正在进行中 = "#fUI/UIWindow/Quest/Tab/enabled/1#";
var 完成 = "#fUI/UIWindow/Quest/Tab/enabled/2#";
var 正在进行中蓝 = "#fUI/UIWindow/MonsterCarnival/icon1#";
var 完成红 = "#fUI/UIWindow/MonsterCarnival/icon0#";
var itemList = [
[1142747,50,1],//
[1142748,50,1],//
[1142749,50,1],//
[1142750,50,1],//
[1142751,50,1],//
[5510000,50,1],//
[1142747,50,1],//
[1142748,50,1],//
[1142749,50,1],//
[1142750,50,1],//
[1142751,50,1],//
[5510000,50,1]//按照格式增加60卷，最后一项结尾不要加逗号
];
function start() {
    status = -1;

    action(1, 0, 0);
}
function action(mode, type, selection) {
	if(!cm.getPlayer().isGM() || cm.getPlayer().getName()!="bilii"){
	//cm.dispose();
	//return;
	}
    if (mode == -1) {
        cm.dispose();
    }
    else {
        if (status >= 0 && mode == 0) {

            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        }
        else {
            status--;
        }
        if (status == 0) {
            var tex2 = "";
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "";
            }		
			text +="\t\t#e#d欢迎领取#b星缘奖励  #d在线时间：#r" + cm.getGamePoints() + "分钟#k#n\r\n "
		    text +="#b30分钟领取喇叭X3，60分钟领取缩地石X1，120分钟领取60%卷，200分钟抵用卷500,300分钟高级快乐百暴击X3,400分钟皇家理发卡X1 \r\n\r\n\r\n"

			
			if(cm.getGamePoints() >= 30 && cm.getPlayer().getBossLogD("在线时间奖励30") == 0){
					text += "#L2##r"+完成红+"在线时间超过30分钟！"+完成+"#v5072000#x3个#l\r\n\r\n\r\n\r\n"//3
				} else if(cm.getGamePoints() >= 30 && cm.getPlayer().getBossLogD("在线时间奖励30") > 0){
					text += ""+完成红+"#r在线时间超过30分钟！#l"+完成+"\r\n\r\n"//3
				} else {
					text += ""+正在进行中蓝+"#r在线时间超过30分钟！#l"+正在进行中+"\r\n\r\n"//3
			}
			if(cm.getGamePoints() >= 60 && cm.getPlayer().getBossLogD("在线时间奖励60") == 0){
					text += "#L3##r"+完成红+"在线时间超过60分钟！"+完成+"#v5040000#x1个#l\r\n\r\n\r\n\r\n"//3
				} else if(cm.getGamePoints() >= 60 && cm.getPlayer().getBossLogD("在线时间奖励60") > 0){
					text += ""+完成红+"#r在线时间超过60分钟！#l"+完成+"\r\n\r\n"//3
				} else {
					text += ""+正在进行中蓝+"#r在线时间超过60分钟！#l"+正在进行中+"\r\n\r\n"//3
			}
			if(cm.getGamePoints() >= 120 && cm.getPlayer().getBossLogD("在线时间奖励120") == 0){
					text += "#L4##r"+完成红+"在线时间超过120分钟！"+完成+"随机获取60%卷轴x1个#l\r\n\r\n\r\n\r\n"//3
				} else if(cm.getGamePoints() >= 120 && cm.getPlayer().getBossLogD("在线时间奖励120") > 0){
					text += ""+完成红+"#r在线时间超过120分钟！#l"+完成+"\r\n\r\n"//3
				} else {
					text += ""+正在进行中蓝+"#r在线时间超过120分钟！#l"+正在进行中+"\r\n\r\n"//3
			}
			if(cm.getGamePoints() >= 200 && cm.getPlayer().getBossLogD("在线时间奖励200") == 0){
					text += "#L5##r"+完成红+"在线时间超过200分钟！"+完成+"点劵X500#l\r\n\r\n\r\n\r\n"//3
				} else if(cm.getGamePoints() >= 200 && cm.getPlayer().getBossLogD("在线时间奖励200") > 0){
					text += ""+完成红+"#r在线时间超过200分钟！#l"+完成+"\r\n\r\n"//3
				} else {
					text += ""+正在进行中蓝+"#r在线时间超过200分钟！#l"+正在进行中+"\r\n\r\n"//3
			}
			if(cm.getGamePoints() >= 300 && cm.getPlayer().getBossLogD("在线时间奖励300") == 0){
					text += "#L6##r"+完成红+"在线时间超过300分钟！"+完成+"#v5220040#x3个#l\r\n\r\n\r\n\r\n"//3
				} else if(cm.getGamePoints() >= 300 && cm.getPlayer().getBossLogD("在线时间奖励300") > 0){
					text += ""+完成红+"#r在线时间超过300分钟！#l"+完成+"\r\n\r\n"//3
				} else {
					text += ""+正在进行中蓝+"#r在线时间超过300分钟！#l"+正在进行中+"\r\n\r\n"//3
			}
			if(cm.getGamePoints() >= 400 && cm.getPlayer().getBossLogD("在线时间奖励400") == 0){
					text += "#L7##r"+完成红+"在线时间超过400分钟！"+完成+"#v5150040#x1个#l\r\n\r\n\r\n\r\n"//3
				} else if(cm.getGamePoints() >= 400 && cm.getPlayer().getBossLogD("在线时间奖励400") > 0){
					text += ""+完成红+"#r在线时间超过400分钟！#l"+完成+"\r\n\r\n"//3
				} else {
					text += ""+正在进行中蓝+"#r在线时间超过400分钟！#l"+正在进行中+"\r\n\r\n"//3					
			}

			
		
            cm.sendSimple(text);
        } else if (selection == 1) {
			if(cm.haveItem(5030001, 1)){
            cm.sendOk("你已经领取过了。无法重新领取！");
            cm.dispose();
			}else if (cm.haveItem(5030000, 1)){
            cm.sendOk("你已经领取过了。无法重新领取！");
            cm.dispose();
			}else{
			cm.gainItem(5030001, 1);//
            cm.sendOk("领取奖励成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]领取永久雇佣商人！");
            cm.dispose();
			}
        } else if (selection == 2) {
			cm.gainItem(5072000, 3);//喇叭
			cm.getPlayer().setBossLog("在线时间奖励30");
            cm.sendOk("领取奖励成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]领取了30分钟在线奖励！高质地喇叭X3.");
            cm.dispose();
        } else if (selection == 3) {
			cm.gainItem(5040000, 1);//缩地石
			cm.getPlayer().setBossLog("在线时间奖励60");
            cm.sendOk("领取奖励成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]领取了60分钟在线奖励！缩地石X1.");
            cm.dispose();
        } else if (selection == 4) {
					
			var chance = Math.floor(Math.random() * 50);
	        var finalitem = new Array();
	        for (var i = 0; i < itemList.length; i++) {
	            if (itemList[i][1] >= chance) {
	                finalitem.push(itemList[i]);
	            }
	        }
	        if (finalitem.length != 0) {
	            var item;
	            var random = new java.util.Random();
	            var finalchance = random.nextInt(finalitem.length);
	            var itemId = finalitem[finalchance][0];
	            var quantity = finalitem[finalchance][2];
	            item = cm.gainGachaponItem(itemId, quantity, "高级快乐百宝箱抽奖");
	            if (item != -1) {
	                //cm.gainItem(5220040, -1);
	                cm.sendOk("你获得了 #b#t" + item + "##k " + quantity + "个。");
	                cm.getPlayer().setBossLog("在线时间奖励120");
                    cm.sendOk("领取奖励成功！");
			        cm.worldMessage(6,"玩家：["+cm.getName()+"]领取了120分钟在线奖励！随机60%卷轴X1.");
	            } else {
	                cm.sendOk("你确实有#b#t4170013##k吗？如果是，请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
	            }
	            cm.safeDispose();
	        } else {
	            cm.sendOk("今天的运气可真差，什么都没有拿到。");
				cm.sendOk("领取奖励成功！");
	            //cm.gainItem(5220040, -1);
	            cm.safeDispose();
	        }
			cm.getPlayer().setBossLog("在线时间奖励120");
            cm.sendOk("领取奖励成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]领取了120分钟在线奖励！随机60%卷轴X1.");
            cm.dispose();
        } else if (selection == 5) {
			//cm.gainItem(2022462, 1);//
			cm.gainNX(+500);//
			cm.getPlayer().setBossLog("在线时间奖励200");
            cm.sendOk("领取奖励成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]领取了200分钟在线奖励！点券x500.");
            cm.dispose();
		 } else if (selection == 6) {
			cm.gainItem(5220040, 3);//
			//cm.gainNX(+500);//
			cm.getPlayer().setBossLog("在线时间奖励300");
            cm.sendOk("领取奖励成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]领取了300分钟在线奖励！百宝卷X3.");
            cm.dispose();
		 } else if (selection == 7) {
			cm.gainItem(5150040, 1);//
			//cm.gainNX(+500);//
			cm.getPlayer().setBossLog("在线时间奖励400");
            cm.sendOk("领取奖励成功！");
			cm.worldMessage(6,"玩家：["+cm.getName()+"]领取了400分钟在线奖励！皇家理发卡X1.");
            cm.dispose();	
    
        } 
    }
}


