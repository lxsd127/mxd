var status = -1;
function start() {
    if((cm.getPlayer().getLevel() > 19 || cm.getPlayer().getLevel() < 30) && !cm.getPlayer().isGM()){
        cm.sendNext("�����Ҫ�μӰ��ﰲ�ؾ���������ĵȼ�������20��~29����");
        cm.dispose();
        return;
    }
    action(1,0,0);
}

function action(mode, type, selection){
    status++;
    if (status == 4){
        cm.saveLocation("ARIANT");
        cm.warp(980010000, 3);
        cm.dispose();
    }
    if(mode != 1){
        if(mode == 0 && type == 0)
            status -= 2;
        else{
            cm.dispose();
            return;
        }
    }
    if (status == 0)//
        cm.sendNext("#e<��Ӹ��������ﰲ�ؾ�����>#n\r\n���ﰲ�ؾٰ쾺����ᣡֻҪ�ռ�������ϲ������걦ʯ,��ɵõ�����Ľ������������ڱ�����ʼ,��ɵõ�#r#v2270002##k�;�������#r#v2100067##k,�ٳ�ʯ������׽��������ʣ�޼��Ĺ���,���õ���걦ʯ,������������ը�����ڵ���,��ɶ�ȡ���˵���걦ʯ��");
    else if (status == 1)
        cm.sendNextPrev("���ﰲ�ؾ���������Űɱ����෴,����Ҫʹ��#v2270002#��ȡ�����������#v4031868##k. #b������,������걦ʯ�������㽱����#k");
    else if (status == 2)
        cm.sendSimple("�������һ���¸ҵ���,��ô�Ҳ���϶��������Ȥ��\r\n#b#L0#�ƶ����������Ⱥ���.#l");
    else if (status == 3)
        cm.sendNext("�ðɣ�������Ҫ����ȥս�������뿴�����ʤ��");
}