var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            qm.sendNext("�кܾo�������顣Ҫ�Ǿܽ^��Ԓ���϶�����ڵ�Ŷ��#b���P���Lì������#k��Ҳ�������P����^ȥ���l֪���أ������f�����@���Lì�܉������������");
            qm.dispose();
            return;
        }
        status--;
    }
    if (status == 0) {
        qm.askAcceptDecline("�ޟ��Mչ����Σ��ѣ��ȼ������@�N���ˣ��y���˂����f���ݍu���B�R�����ã��S�������������������á������ˣ��F��߀�����f�fԒ�ĕr���ܷ��韩��؍u�ρ�һ�ˣ�");
    } else if (status == 1) {
        qm.forceStartQuest(21200, "3");
        qm.sendOk("#b������#m140000000##k�����#b#p1201001##kͻȻ���F����ֵķ��������f�Lì�ں��Լ����˵ĕr��ŕ��l���ǘӵķ�����#bҲ�S��ʲ�N����Ҫ�D�_�o�㣿#kՈ�ٻ؍u��һ�˰ɡ�");
        qm.dispose();
    }
}

function end(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 11) {
            qm.sendNext("���@���ô�ҲҪŬ������һ�°ɣ�");
            qm.dispose();
            return;
        }
        status--;
    }
    if (status == 0) {
        qm.sendNextS("���������ˡ���", 2);
    } else if (status == 1) {
        qm.sendNextPrevS("#b��#p1201001#�ڰl�����Q����֣���߅���������l����#k", 2);
    } else if (status == 2) {
        qm.sendNextPrevS("#b����ǰ�]Ҋ�^���������N������̫�������#k", 2);
    } else if (status == 3) {
        qm.sendNextPrev("ι��������ʿ��߀ ��Ҋ�ҵ���᣿���� �� ��Ҋ�����������ˣ�");
    } else if (status == 4) {
        qm.sendNextPrevS("#b���ף��@���l���������N �������װͰ͵����ꡭ����#k", 2);
    } else if (status == 5) {
        qm.sendNextPrev("�����������@�ӵ����˰����G�_�����ڱ����e˯�ˎװ��꣬�F���BԒ�� �����ˡ���");
    } else if (status == 6) {
        qm.sendNextPrevS("�����l����", 2);
    } else if (status == 7) {
        qm.sendNextPrev("����������ʿ���F�� ���ҵ����ˣ����Ұ�����ӛ�����ˣ��Ҿ�������#b�Lì #p1201002##k����");
    } else if (status == 8) {
        qm.sendNextPrevS("#b������#p1201002#��#p1201001#���fԒ����#k", 2);
    } else if (status == 9) {
        qm.sendNextPrev("����춰ɣ��@�N���@�������Nʧ�����������B�Ҷ����˰ɣ�̫������˼�ˣ�");
    } else if (status == 10) {
        qm.sendNextPrevS("������˼�����һ�c���벻����", 2);
    } else if (status == 11) {
        qm.sendYesNo("�f������˼�������ˣ����װ�������һ���˹¿�����أ��ж��į��֪���᣿�������ӣ�����c�o��������");
    } else if (status == 12) {
        qm.sendNextS("#b��һ��һ���Լ���#p1201001#��#p1201002#�ģ�߀Խ�fԽ�����ˡ����@�N�f��ȥҲ������ɶ�Mչ��߀�����ߵ� #p1201000#��ǰ���ú�������������#k", 2);
        qm.forceCompleteQuest();
    } else if (status == 13) {
        qm.MovieClipIntroUI(true);
        qm.warp(914090200, 0);
        qm.dispose();
    }
}