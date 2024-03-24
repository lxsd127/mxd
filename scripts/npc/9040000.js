/* 
 * Shuang, Victoria Road: Excavation Site<Camp> (101030104)
 * Start of Guild Quest
 */

var status;
var GQItems = Array(1032033, 4001024, 4001025, 4001026, 4001027, 4001028, 4001031, 4001032, 4001033, 4001034, 4001035, 4001037);

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
    }

    if (status == 0) {
        if (cm.getPlayer().hasEquipped(1032033)) {
            cm.sendOk("��ж�����ħ��ʯ������");
            cm.dispose();
        } else {
            cm.sendSimple("��Ҫ��ʲô? #b\r\n#L0#��ʼ��������#l\r\n#L1#�����������#l");
        }

    } else if (status == 1) {
        if (selection == 0) { //Start
            if (cm.getPlayerStat("GID") == 0 || cm.getPlayerStat("GRANK") >= 3) { //no guild or not guild master/jr. master
                cm.sendNext("ֻ�м����峤�͸��峤���ܿ�ʼ�������");
                cm.dispose();
            } else {
                var em = cm.getEventManager("GuildQuest");
                if (em == null) {
                    cm.sendOk("����δ֪�������Ժ����ԡ�");
                } else {
                    var prop = em.getProperty("started");

                    if ((prop.equals("false") || prop == null) && em.getInstance("GuildQuest") == null) {
                        for (var i = 0; i < GQItems.length; i++) {
                            cm.removeAll(GQItems[i]);
                        }
                        em.startInstance(cm.getPlayer(), cm.getPlayer().getName());
                        em.setProperty("state", "0");
                        cm.guildMessage("�����ѽ���������������ͨ�����ھ�Ӫ�ر��� " + cm.getClient().getChannel() + "��");
                    } else {
                        cm.sendOk("�Ѿ���������ս��������")
                    }
                }
                cm.dispose();
            }
        } else if (selection == 1) { //entering existing GQ
            if (cm.getPlayerStat("GID") == 0) { //no guild or not guild master/jr. master
                cm.sendNext("��������һ�����塣");
                cm.dispose();
            } else {
                var em = cm.getEventManager("GuildQuest");
                if (em == null) {
                    cm.sendOk("����δ֪�������Ժ����ԡ�");
                } else {
                    var eim = em.getInstance("GuildQuest");

                    if (eim == null) {
                        cm.sendOk("��ļ���Ŀǰû����ս�������.");
                    } else {
                        /*if (em.getProperty("guildid") != null && !em.getProperty("guildid").equalsIgnoreCase("" + cm.getPlayerStat("GID"))) {
                         if (cm.getPlayer().isGM()) {
                         cm.sendOk("This instance is not your guild. Instance Guild: " + em.getProperty("guildid") + ", Your Guild: " + cm.getPlayerStat("GID"));
                         } else {
                         cm.sendOk("This instance is not your guild.");
                         }
                         } else */if (em.getProperty("started").equals("false")) {
                            for (var i = 0; i < GQItems.length; i++) {
                                cm.removeAll(GQItems[i]);
                            }
                            eim.registerPlayer(cm.getPlayer());
                        } else {
                            cm.sendOk("�Բ��𣬼����Ѿ�û�����ˡ����Ժ�����.");
                        }
                    }
                }
                cm.dispose();
            }
        }
    }
}