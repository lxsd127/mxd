var ���ڽ����� = "#fUI/UIWindow/Quest/Tab/enabled/1#";
var ��� = "#fUI/UIWindow/Quest/Tab/enabled/2#";
var ���ڽ������� = "#fUI/UIWindow/MonsterCarnival/icon1#";
var ��ɺ� = "#fUI/UIWindow/MonsterCarnival/icon0#";
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
[5510000,50,1]//���ո�ʽ����60�����һ���β��Ҫ�Ӷ���
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

            cm.sendOk("��л��Ĺ��٣�");
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
			text +="\t\t#e#d��ӭ��ȡ#b��Ե����  #d����ʱ�䣺#r" + cm.getGamePoints() + "����#k#n\r\n "
		    text +="#b30������ȡ����X3��60������ȡ����ʯX1��120������ȡ60%��200���ӵ��þ�500,300���Ӹ߼����ְٱ���X3,400���ӻʼ�����X1 \r\n\r\n\r\n"

			
			if(cm.getGamePoints() >= 30 && cm.getPlayer().getBossLogD("����ʱ�佱��30") == 0){
					text += "#L2##r"+��ɺ�+"����ʱ�䳬��30���ӣ�"+���+"#v5072000#x3��#l\r\n\r\n\r\n\r\n"//3
				} else if(cm.getGamePoints() >= 30 && cm.getPlayer().getBossLogD("����ʱ�佱��30") > 0){
					text += ""+��ɺ�+"#r����ʱ�䳬��30���ӣ�#l"+���+"\r\n\r\n"//3
				} else {
					text += ""+���ڽ�������+"#r����ʱ�䳬��30���ӣ�#l"+���ڽ�����+"\r\n\r\n"//3
			}
			if(cm.getGamePoints() >= 60 && cm.getPlayer().getBossLogD("����ʱ�佱��60") == 0){
					text += "#L3##r"+��ɺ�+"����ʱ�䳬��60���ӣ�"+���+"#v5040000#x1��#l\r\n\r\n\r\n\r\n"//3
				} else if(cm.getGamePoints() >= 60 && cm.getPlayer().getBossLogD("����ʱ�佱��60") > 0){
					text += ""+��ɺ�+"#r����ʱ�䳬��60���ӣ�#l"+���+"\r\n\r\n"//3
				} else {
					text += ""+���ڽ�������+"#r����ʱ�䳬��60���ӣ�#l"+���ڽ�����+"\r\n\r\n"//3
			}
			if(cm.getGamePoints() >= 120 && cm.getPlayer().getBossLogD("����ʱ�佱��120") == 0){
					text += "#L4##r"+��ɺ�+"����ʱ�䳬��120���ӣ�"+���+"�����ȡ60%����x1��#l\r\n\r\n\r\n\r\n"//3
				} else if(cm.getGamePoints() >= 120 && cm.getPlayer().getBossLogD("����ʱ�佱��120") > 0){
					text += ""+��ɺ�+"#r����ʱ�䳬��120���ӣ�#l"+���+"\r\n\r\n"//3
				} else {
					text += ""+���ڽ�������+"#r����ʱ�䳬��120���ӣ�#l"+���ڽ�����+"\r\n\r\n"//3
			}
			if(cm.getGamePoints() >= 200 && cm.getPlayer().getBossLogD("����ʱ�佱��200") == 0){
					text += "#L5##r"+��ɺ�+"����ʱ�䳬��200���ӣ�"+���+"�ㄻX500#l\r\n\r\n\r\n\r\n"//3
				} else if(cm.getGamePoints() >= 200 && cm.getPlayer().getBossLogD("����ʱ�佱��200") > 0){
					text += ""+��ɺ�+"#r����ʱ�䳬��200���ӣ�#l"+���+"\r\n\r\n"//3
				} else {
					text += ""+���ڽ�������+"#r����ʱ�䳬��200���ӣ�#l"+���ڽ�����+"\r\n\r\n"//3
			}
			if(cm.getGamePoints() >= 300 && cm.getPlayer().getBossLogD("����ʱ�佱��300") == 0){
					text += "#L6##r"+��ɺ�+"����ʱ�䳬��300���ӣ�"+���+"#v5220040#x3��#l\r\n\r\n\r\n\r\n"//3
				} else if(cm.getGamePoints() >= 300 && cm.getPlayer().getBossLogD("����ʱ�佱��300") > 0){
					text += ""+��ɺ�+"#r����ʱ�䳬��300���ӣ�#l"+���+"\r\n\r\n"//3
				} else {
					text += ""+���ڽ�������+"#r����ʱ�䳬��300���ӣ�#l"+���ڽ�����+"\r\n\r\n"//3
			}
			if(cm.getGamePoints() >= 400 && cm.getPlayer().getBossLogD("����ʱ�佱��400") == 0){
					text += "#L7##r"+��ɺ�+"����ʱ�䳬��400���ӣ�"+���+"#v5150040#x1��#l\r\n\r\n\r\n\r\n"//3
				} else if(cm.getGamePoints() >= 400 && cm.getPlayer().getBossLogD("����ʱ�佱��400") > 0){
					text += ""+��ɺ�+"#r����ʱ�䳬��400���ӣ�#l"+���+"\r\n\r\n"//3
				} else {
					text += ""+���ڽ�������+"#r����ʱ�䳬��400���ӣ�#l"+���ڽ�����+"\r\n\r\n"//3					
			}

			
		
            cm.sendSimple(text);
        } else if (selection == 1) {
			if(cm.haveItem(5030001, 1)){
            cm.sendOk("���Ѿ���ȡ���ˡ��޷�������ȡ��");
            cm.dispose();
			}else if (cm.haveItem(5030000, 1)){
            cm.sendOk("���Ѿ���ȡ���ˡ��޷�������ȡ��");
            cm.dispose();
			}else{
			cm.gainItem(5030001, 1);//
            cm.sendOk("��ȡ�����ɹ���");
			cm.worldMessage(6,"��ң�["+cm.getName()+"]��ȡ���ù�Ӷ���ˣ�");
            cm.dispose();
			}
        } else if (selection == 2) {
			cm.gainItem(5072000, 3);//����
			cm.getPlayer().setBossLog("����ʱ�佱��30");
            cm.sendOk("��ȡ�����ɹ���");
			cm.worldMessage(6,"��ң�["+cm.getName()+"]��ȡ��30�������߽��������ʵ�����X3.");
            cm.dispose();
        } else if (selection == 3) {
			cm.gainItem(5040000, 1);//����ʯ
			cm.getPlayer().setBossLog("����ʱ�佱��60");
            cm.sendOk("��ȡ�����ɹ���");
			cm.worldMessage(6,"��ң�["+cm.getName()+"]��ȡ��60�������߽���������ʯX1.");
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
	            item = cm.gainGachaponItem(itemId, quantity, "�߼����ְٱ���齱");
	            if (item != -1) {
	                //cm.gainItem(5220040, -1);
	                cm.sendOk("������ #b#t" + item + "##k " + quantity + "����");
	                cm.getPlayer().setBossLog("����ʱ�佱��120");
                    cm.sendOk("��ȡ�����ɹ���");
			        cm.worldMessage(6,"��ң�["+cm.getName()+"]��ȡ��120�������߽��������60%����X1.");
	            } else {
	                cm.sendOk("��ȷʵ��#b#t4170013##k������ǣ�����ȷ���ڱ�����װ�������ģ������������Ƿ���һ�����ϵĿռ䡣");
	            }
	            cm.safeDispose();
	        } else {
	            cm.sendOk("�������������ʲô��û���õ���");
				cm.sendOk("��ȡ�����ɹ���");
	            //cm.gainItem(5220040, -1);
	            cm.safeDispose();
	        }
			cm.getPlayer().setBossLog("����ʱ�佱��120");
            cm.sendOk("��ȡ�����ɹ���");
			cm.worldMessage(6,"��ң�["+cm.getName()+"]��ȡ��120�������߽��������60%����X1.");
            cm.dispose();
        } else if (selection == 5) {
			//cm.gainItem(2022462, 1);//
			cm.gainNX(+500);//
			cm.getPlayer().setBossLog("����ʱ�佱��200");
            cm.sendOk("��ȡ�����ɹ���");
			cm.worldMessage(6,"��ң�["+cm.getName()+"]��ȡ��200�������߽�������ȯx500.");
            cm.dispose();
		 } else if (selection == 6) {
			cm.gainItem(5220040, 3);//
			//cm.gainNX(+500);//
			cm.getPlayer().setBossLog("����ʱ�佱��300");
            cm.sendOk("��ȡ�����ɹ���");
			cm.worldMessage(6,"��ң�["+cm.getName()+"]��ȡ��300�������߽������ٱ���X3.");
            cm.dispose();
		 } else if (selection == 7) {
			cm.gainItem(5150040, 1);//
			//cm.gainNX(+500);//
			cm.getPlayer().setBossLog("����ʱ�佱��400");
            cm.sendOk("��ȡ�����ɹ���");
			cm.worldMessage(6,"��ң�["+cm.getName()+"]��ȡ��400�������߽������ʼ�����X1.");
            cm.dispose();	
    
        } 
    }
}


