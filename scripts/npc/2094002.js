var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }
    if (cm.getPlayer().getMapId() == 925100700) {
	cm.removeAll(4001117);
	cm.removeAll(4001120);
	cm.removeAll(4001121);
	cm.removeAll(4001122);
	cm.warp(251010404,0);
	cm.dispose();
	return;
    }
    var em = cm.getEventManager("Pirate");
    if (em == null) {
	cm.sendNext("����δ֪�������Ժ����ԡ�");
	cm.dispose();
	return;
    }
    if (!cm.isLeader()) {
	cm.sendNext("��жӳ����ҶԻ���");
	cm.dispose();
	return;
    }
    switch(cm.getPlayer().getMapId()) {
	case 925100000:
	   cm.sendNext("�������ڽ��뺣���ڲ���������й�������");
	   cm.dispose();
	   break;
	case 925100100:
	   var emp = em.getProperty("stage2");
	   if (emp == null) {
		em.setProperty("stage2", "0");
		emp = "0";
	   }
	   if (emp.equals("0")) {
		if (cm.haveItem(4001120,20)) {
		    cm.sendNext("����! ��������Ҫ#t4001121# #b20��#k");
		    cm.gainItem(4001120,-20);
		    em.setProperty("stage2", "1");
		} else {
	   	    cm.sendNext("�����20��#t4001120#��");
		}
	   } else if (emp.equals("1")) {
		if (cm.haveItem(4001121,20)) {
		    cm.sendNext("���㣡 ��������Ҫ#t4001122# #b20��#k");
		    cm.gainItem(4001121,-20);
		    em.setProperty("stage2", "2");
		} else {
	   	    cm.sendNext("�����20��#t4001121#��");
		}
	   } else if (emp.equals("2")) {
		if (cm.haveItem(4001122,20)) {
		    cm.sendNext("��ɣ���һ�׶��ѿ�����");
		    cm.gainItem(4001122,-20);
		    em.setProperty("stage2", "3");
		} else {
	   	    cm.sendNext("�����20��#t4001122#��");
		}
	   } else {
		cm.sendNext("��ɣ���һ�׶��ѿ�����");
	   }
	   cm.dispose();
	   break;
	case 925100200:
	   cm.sendNext("ͻ�����I�������Ǳ���������������");
	   cm.dispose();
	   break;
	case 925100300:
	   cm.sendNext("ͻ�����I�������Ǳ���������������");
	   cm.dispose();
	   break;
	case 925100201:
	   if (cm.getMap().getAllMonstersThreadsafe().size() == 0) {
		cm.sendNext("Excellent.");
		if (em.getProperty("stage2a").equals("0")) {
		    cm.getMap().setReactorState();
		    em.setProperty("stage2a", "1");
		}
	   } else {
	   	cm.sendNext("�������ڶ�ء�����һ��Ҫ������ǡ�");
	   }
	   cm.dispose();
	   break;
	case 925100301:
	   if (cm.getMap().getAllMonstersThreadsafe().size() == 0) {
		cm.sendNext("���㡣");
		if (em.getProperty("stage3a").equals("0")) {
		    cm.getMap().setReactorState();
		    em.setProperty("stage3a", "1");
		}
	   } else {
	   	cm.sendNext("�������ڶ�ء�����һ��Ҫ������ǡ�");
	   }
	   cm.dispose();
	   break;
	case 925100202:
	case 925100302:
	   cm.sendNext("�������ڶ�ء�����һ��Ҫ������ǡ�");
	   cm.dispose();
	   break;
	case 925100400:
	   cm.sendNext("����Щ������Ȼ������ǵ�Կ�׷ŵ��ſھ����ˡ�");
	   cm.dispose();
	   break;
	case 925100500:
	   if (cm.getMap().getAllMonstersThreadsafe().size() == 0) {
		cm.warpParty(925100600);
	   } else {
	   	cm.sendNext("���ܵ�ͼ�ϵ����й��");
	   }
	   cm.dispose();
	   break;
    }
}