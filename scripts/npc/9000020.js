/* Dawnveil
 World Tour Guide
 Spinel
 Made by Daenerys
 */
var status = -1;
var sel;
var map;
var back = true;
var togos = [500000000, 702000000, 800000000];
var maps = [104020000, 200000000, 211000000, 220000000, 221000000, 222000000, 230000000, 240000000, 250000000, 251000000, 260000000, 261000000, 270000000];

function start() {
    switch (cm.getMapId()) {
        case 500000000:
        case 702000000:
        case 800000000:    
            map = cm.getSavedLocation("WORLDTOUR");
            cm.sendSimple("������ð���γ���? \n\r #b#L0# �Ѿ������ˣ���ص�#m" + map + "#��#l");
            break;
        default:
            back = false;
            cm.sendNext("Ϊ�˴ӷ�æ���ճ��н��ѣ�ȥ����һ��������ô�����������������ӱ������Ļ�������ѧ�����ٶ����Ļ��ᣡ����ð�յ����ι�˾Ϊ��׼���ˣ��ḻ��Ȥ��#b��������#k�ײ͡�˭˵��������ܹ����һ����ġ�����#bð�յ����������ײ�#kֻ��Ҫ#b3000���#k�Ϳ�������ȫ�̡�");
            break;
    }
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if ((status <= 2 && mode == 0) || (status == 4 && mode == 1)) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;

        if (!back) {
            if (status == 0) {
                cm.sendSimple("���ھͿ���ȥ�� #b������س���ð�յ���ɫ�ľ���#k����һ������ÿ�����ε��Ҷ���Ϊ����ṩ�����ȳϵķ�����ô��׼���ã����ǳ���ȥ\r\n#L0# ˮ���г���̩����#l\r\n#L1# ��ɽ�����֣�#l\r\n#L2# �Ŵ����磨�ձ���#l");
            } else if (status == 1) {
                sel = selection;
                cm.sendNext("���Ǹ�����ĵط�����������϶��ܿ��ĵġ�");
            } else if (status == 2) {
                if (cm.getMeso() < 3000) {
                    cm.sendNext("��ȷ�����Ƿ�����㹻���̲���");
                    cm.dispose();
                } else {
                    cm.gainMeso(-3000);
                    cm.saveLocation("WORLDTOUR");
                    cm.warp(togos[sel]);
                    cm.dispose();
                }
            }
        } else {
            if (sel == 123456) {
                cm.warp(maps[selection]);
                cm.dispose();
            } else {
                cm.warp(map == -1 ? 100000000 : map);
                cm.clearSavedLocation("WORLDTOUR");
                cm.dispose();
            }
        }
    }
}