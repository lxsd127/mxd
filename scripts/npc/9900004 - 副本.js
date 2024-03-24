 

var aaa = "#fUI/UIWindow.img/Quest/icon9/0#";
var zzz = "#fUI/UIWindow.img/Quest/icon8/0#";
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#";

//------------------------------------------------------------------------

var chosenMap = -1;
var monsters = 0;
var towns = 0;
var bosses = 0;
var fuben = 0;

//------------------------------------------------------------------------

var acc = "#fEffect/CharacterEff/1112903/0/0#";//红桃心
var aaa = "#fUI/UIWindow.img/Quest/icon9/0#";//红色右箭头
var zzz = "#fUI/UIWindow.img/Quest/icon8/0#";//蓝色右箭头
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#";//选择道具
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 圆形 = "#fUI/UIWindow/Quest/icon3/6#";
//var 红色箭头 = "#fEffect/CharacterEff/1114000/2/0#";
var 红色箭头 = "#fEffect/CharacterEff/1112908/0/1#";  //彩光3
var ttt1 = "#fEffect/CharacterEff/1062114/1/0#";  //爱心
//var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";

//---------------------------------------------------------------------------
function start() {
    status = -1;
    action(1, 0, 0);
}
function action(mode, type, selection) {
    if (mode == -1) {
        cm.sendOk("#b好的,下次再见.");
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {
            cm.sendOk("#b好的,下次再见.");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }

//------------------------------------------------------------------------

        if (status == 0) {

            var add =  " \t\t\t  #d#e#b#v4030001#欢迎来到#r风雪冒险岛#e#b#v4030001##b#k#n\r\r\n";
			 add +="\t#d#e当前点卷：#r" +cm.getPlayer().getCSPoints(1) +  "#k点  #d当前抵用卷：#r" +cm.getPlayer().getCSPoints(2) +  "#k点#b#k#n\r\n";
			 add +="\t#d#e当前金币：#r" +cm.getPlayer().getMeso() +  "#k金币#b#k#n\r\n";
                         add +="\t#d#e您今天已在线了：#r" + cm.getGamePoints() + "#k分钟,请注意休息哦~!#k#n\r\n";
			 add += "  #L1##b杂货商店#l #L2##b在线奖励#l #L12##b澳门赌场#l #L10##b游戏制作#l #k#n\r\n";

						 

            cm.sendSimple(add);

//------------------------------------------------------------------------

        } else if (status == 1) {



	    if (selection == 1) {//杂货商店
		        cm.openShop(2100); //2041006
		cm.dispose();

            }
	    if (selection == 2) {//在线奖励
		cm.dispose();
                cm.openNpc(9900005);

            }

	    if (selection == 3) {//万能传送
		cm.dispose();
                cm.openNpc(9900006);

            }
             
        }
    }
}

