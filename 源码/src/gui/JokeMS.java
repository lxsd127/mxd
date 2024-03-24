package gui;

import client.LoginCrypto;
import constants.GameConstants;
import handling.channel.ChannelServer;
import handling.login.handler.AutoRegister;
import client.MapleCharacter;
import client.inventory.Equip;
import client.inventory.ItemFlag;
import client.inventory.MapleInventoryType;
import gui.tools.*;
import server.CashItemFactory;
import server.quest.MapleQuest;
import database.DatabaseConnection;
import server.*;
import server.life.MapleMonsterInformationProvider;
import constants.ServerConfig;
import constants.ServerConstants;
import constants.WorldConstants;
import handling.login.LoginServer;
import handling.world.World;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import scripting.ReactorScriptManager;
import scripting.PortalScriptManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import tools.packet.MaplePacketCreator;
import scripting.NPCConversationManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import static server.MapleCarnivalChallenge.getJobNameById;
import tools.wztosql.*;
import server.Timer.GuiTimer;
//jokeMS 继承于 javax.swing.JFrame
public class JokeMS extends javax.swing.JFrame { 
    // 定义了一个JokeMS类的叫做instance（实例）
    public static JokeMS instance = null;
    //私有最终变量 PrintStream 是 Java 中的一个类用于将格式化的输出写入到各种输出流中，例如文件、控制台等
    private final PrintStream printStream;
    //使用了java的map接口（存储键值对的数据结构）；并初始化为一个
    //新的、空的HashMap<String, Integer>实例；<>用于类型推断
    public static Map<String, Integer> ConfigValuesMap = new HashMap<>();
    private Map<Windows, javax.swing.JFrame> windows = new HashMap<>();
    //ArrayList 是Java的一个内置类，代表一个可以动态调整大小的数组
    private ArrayList<Tools> tools = new ArrayList();
    private boolean searchServer = false;
    private static String 服务器状态 = "开启";
    //ReentrantReadWriteLock是一个可重入的读写锁，允许多个线程同时读取，
    //但在写操作时只允许一个线程进行。这提供了比简单的互斥锁更高的并发性
    private final ReentrantReadWriteLock mutex = new ReentrantReadWriteLock();
    //ScheduledFuture<?>是一个接口，它是Future接口的子接口，
    //用于表示一个计划执行的任务的结果
    private ScheduledFuture<?> shutdownServer, startRunTime;
    //java.util.concurrent.locks中的一个接口，通常用于实现同步和锁定机制
    //writeLock(): 这是ReentrantReadWriteLock的一个方法，用于获取写锁（write lock）
    //当其他线程已经获取了读锁或写锁时，调用该方法的线程将会等待，直到能够获取到写锁。
    private final Lock writeLock = mutex.writeLock();
    boolean 开启服务端 = false;
    //放入其中2
    boolean inidrop2 = false;
    //将数据放入其中已完成
    private boolean dropaDataInitFinished = false;
    private boolean dropaDataGlobalInitFinished = false;
    private boolean shopsInitFinished = false;
    private static boolean MYSQL = false;
    int si = 0;
    //缓冲线，buff线
    private int buffline = 0;
    //获取实例
    public static final JokeMS getInstance() {
        return instance;
    }
    
    public JokeMS() {
        //ImageIcon是javax.swing包中的一个类,加载各种类型的图像并将它们作为图标
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("Image/Icon.png"));
        //setIconImage是JFrame类的一个方法,用于设置窗口的图标。这个方法接受一个Image对象作为参数，该对象表示要设置的图标。
        setIconImage(icon.getImage());
        //constants.ServerConfig
        setTitle("[Joke服务端]后台管理工具 当前版本:" + ServerConfig.version + " 区名:" + ServerConfig.servername);
        //动态数据库连接
        GetConfigValues();
        //加载组件
        initComponents();
        刷新商城扩充价格();
        刷新物品掉落持续时间();
        刷新地图物品上限();
        刷新地图刷新频率();
        刷新蓝蜗牛开关();
        //打印错误记录
        printStream = new PrintStream(new newOutputStream());
        System.setOut(printStream);
        System.setErr(printStream);
        //加载角色面板
        initCharacterPannel();
        System.out.println("【已成功开启受苦受难岛Ver072】");
        刷新账号信息();
        刷新角色信息();
        GuiTimer.getInstance().start();//计时器
        InputStream is = null;
        Properties p = new Properties();
        BufferedReader bf = null;
        进度条.setMinimum(0);
        进度条.setMaximum(100);
        // 设置当前进度值
        进度条.setValue(0);
        // 绘制百分比文本（进度条中间显示的百分数）
        进度条.setStringPainted(true);

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel68 = new javax.swing.JPanel();
        jTabbedPane4 =  new JTabbedPane(JTabbedPane.LEFT);
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel33 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        output_jTextPane1 = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleInfo = new javax.swing.JTextArea();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        数据包文本大框 = new javax.swing.JTextPane();
        jPanel35 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        提示文本大窗口 = new javax.swing.JTextPane();
        jPanel36 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        报错文本大框 = new javax.swing.JTextPane();
        jPanel37 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        其他文本大框 = new javax.swing.JTextPane();
        jPanel15 = new javax.swing.JPanel();
        保存雇佣按钮 = new javax.swing.JButton();
        保存数据按钮 = new javax.swing.JButton();
        查询在线玩家人数按钮 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        进度条 = new javax.swing.JProgressBar();
        状态信息 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel74 = new javax.swing.JPanel();
        蓝蜗牛开关 = new javax.swing.JButton();
        jPanel75 = new javax.swing.JPanel();
        jPanel76 = new javax.swing.JPanel();
        jPanel77 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        重载副本按钮 = new javax.swing.JButton();
        重载爆率按钮 = new javax.swing.JButton();
        重载反应堆按钮 = new javax.swing.JButton();
        重载传送门按钮 = new javax.swing.JButton();
        重载商城按钮 = new javax.swing.JButton();
        重载商店按钮 = new javax.swing.JButton();
        重载包头按钮 = new javax.swing.JButton();
        重载任务 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        sendNotice = new javax.swing.JButton();
        sendWinNotice = new javax.swing.JButton();
        sendMsgNotice = new javax.swing.JButton();
        sendNpcTalkNotice = new javax.swing.JButton();
        noticeText = new javax.swing.JTextField();
        公告发布喇叭代码 = new javax.swing.JTextField();
        jButton45 = new javax.swing.JButton();
        jLabel259 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane25 = new javax.swing.JScrollPane();
        游戏商店2 = new javax.swing.JTable();
        jPanel55 = new javax.swing.JPanel();
        查询商店2 = new javax.swing.JButton();
        查询商店 = new javax.swing.JTextField();
        jLabel270 = new javax.swing.JLabel();
        jPanel56 = new javax.swing.JPanel();
        删除商品 = new javax.swing.JButton();
        新增商品 = new javax.swing.JButton();
        商品序号 = new javax.swing.JTextField();
        商店代码 = new javax.swing.JTextField();
        商品物品代码 = new javax.swing.JTextField();
        商品售价金币 = new javax.swing.JTextField();
        jLabel268 = new javax.swing.JLabel();
        jLabel269 = new javax.swing.JLabel();
        jLabel271 = new javax.swing.JLabel();
        jLabel272 = new javax.swing.JLabel();
        修改商品 = new javax.swing.JButton();
        商品名称 = new javax.swing.JTextField();
        jLabel273 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel18 = new javax.swing.JPanel();
        主题馆 = new javax.swing.JButton();
        读取热销产品 = new javax.swing.JButton();
        活动 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        帽子 = new javax.swing.JButton();
        脸饰 = new javax.swing.JButton();
        眼饰 = new javax.swing.JButton();
        长袍 = new javax.swing.JButton();
        上衣 = new javax.swing.JButton();
        裙裤 = new javax.swing.JButton();
        鞋子 = new javax.swing.JButton();
        手套 = new javax.swing.JButton();
        武器 = new javax.swing.JButton();
        戒指 = new javax.swing.JButton();
        飞镖 = new javax.swing.JButton();
        披风 = new javax.swing.JButton();
        骑宠 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        喜庆物品 = new javax.swing.JButton();
        通讯物品 = new javax.swing.JButton();
        卷轴 = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        会员卡 = new javax.swing.JButton();
        表情 = new javax.swing.JButton();
        个人商店 = new javax.swing.JButton();
        纪念日 = new javax.swing.JButton();
        游戏 = new javax.swing.JButton();
        效果 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        宠物 = new javax.swing.JButton();
        宠物服饰 = new javax.swing.JButton();
        其他 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        charTable = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        商品数量 = new javax.swing.JTextField();
        商品编码 = new javax.swing.JTextField();
        商品代码 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        商品价格 = new javax.swing.JTextField();
        商品时间 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        商品库存 = new javax.swing.JTextField();
        商品折扣 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        每日限购 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        货币类型 = new javax.swing.JTextField();
        jScrollPane132 = new javax.swing.JScrollPane();
        商城扩充价格 = new javax.swing.JTable();
        商城扩充价格修改 = new javax.swing.JTextField();
        商品出售状态 = new javax.swing.JTextField();
        显示类型 = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        刷新模式 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        添加 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        修改 = new javax.swing.JButton();
        修改背包扩充价格 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel57 = new javax.swing.JPanel();
        全服发送物品数量 = new javax.swing.JTextField();
        全服发送物品代码 = new javax.swing.JTextField();
        给予物品1 = new javax.swing.JButton();
        jLabel217 = new javax.swing.JLabel();
        jLabel234 = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        全服发送装备装备加卷 = new javax.swing.JTextField();
        全服发送装备装备制作人 = new javax.swing.JTextField();
        全服发送装备装备力量 = new javax.swing.JTextField();
        全服发送装备装备MP = new javax.swing.JTextField();
        全服发送装备装备智力 = new javax.swing.JTextField();
        全服发送装备装备运气 = new javax.swing.JTextField();
        全服发送装备装备HP = new javax.swing.JTextField();
        全服发送装备装备攻击力 = new javax.swing.JTextField();
        全服发送装备装备给予时间 = new javax.swing.JTextField();
        全服发送装备装备可否交易 = new javax.swing.JTextField();
        全服发送装备装备敏捷 = new javax.swing.JTextField();
        全服发送装备物品ID = new javax.swing.JTextField();
        全服发送装备装备魔法力 = new javax.swing.JTextField();
        全服发送装备装备魔法防御 = new javax.swing.JTextField();
        全服发送装备装备物理防御 = new javax.swing.JTextField();
        给予装备1 = new javax.swing.JButton();
        jLabel219 = new javax.swing.JLabel();
        jLabel220 = new javax.swing.JLabel();
        jLabel221 = new javax.swing.JLabel();
        jLabel222 = new javax.swing.JLabel();
        jLabel223 = new javax.swing.JLabel();
        jLabel224 = new javax.swing.JLabel();
        jLabel225 = new javax.swing.JLabel();
        jLabel226 = new javax.swing.JLabel();
        jLabel227 = new javax.swing.JLabel();
        jLabel228 = new javax.swing.JLabel();
        jLabel229 = new javax.swing.JLabel();
        jLabel230 = new javax.swing.JLabel();
        jLabel231 = new javax.swing.JLabel();
        jLabel232 = new javax.swing.JLabel();
        jLabel233 = new javax.swing.JLabel();
        发送装备玩家姓名 = new javax.swing.JTextField();
        给予装备2 = new javax.swing.JButton();
        jLabel246 = new javax.swing.JLabel();
        jLabel244 = new javax.swing.JLabel();
        jPanel59 = new javax.swing.JPanel();
        z2 = new javax.swing.JButton();
        z3 = new javax.swing.JButton();
        z1 = new javax.swing.JButton();
        z4 = new javax.swing.JButton();
        z5 = new javax.swing.JButton();
        z6 = new javax.swing.JButton();
        a1 = new javax.swing.JTextField();
        jLabel235 = new javax.swing.JLabel();
        jPanel60 = new javax.swing.JPanel();
        个人发送物品数量 = new javax.swing.JTextField();
        个人发送物品玩家名字 = new javax.swing.JTextField();
        个人发送物品代码 = new javax.swing.JTextField();
        给予物品 = new javax.swing.JButton();
        jLabel240 = new javax.swing.JLabel();
        jLabel241 = new javax.swing.JLabel();
        jLabel242 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        账号信息 = new javax.swing.JTable();
        jPanel30 = new javax.swing.JPanel();
        抵用 = new javax.swing.JTextField();
        账号 = new javax.swing.JTextField();
        点券 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        修改账号点券抵用 = new javax.swing.JButton();
        账号ID = new javax.swing.JTextField();
        jLabel206 = new javax.swing.JLabel();
        jLabel312 = new javax.swing.JLabel();
        管理1 = new javax.swing.JTextField();
        jLabel353 = new javax.swing.JLabel();
        QQ = new javax.swing.JTextField();
        jLabel357 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        注册的账号 = new javax.swing.JTextField();
        注册的密码 = new javax.swing.JTextField();
        jButton35 = new javax.swing.JButton();
        jLabel111 = new javax.swing.JLabel();
        jLabel201 = new javax.swing.JLabel();
        jButton30 = new javax.swing.JButton();
        刷新账号信息 = new javax.swing.JButton();
        离线账号 = new javax.swing.JButton();
        解封 = new javax.swing.JButton();
        已封账号 = new javax.swing.JButton();
        在线账号 = new javax.swing.JButton();
        删除账号 = new javax.swing.JButton();
        封锁账号 = new javax.swing.JButton();
        解卡 = new javax.swing.JButton();
        显示在线账号 = new javax.swing.JLabel();
        账号提示语言 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        账号操作 = new javax.swing.JTextField();
        jPanel38 = new javax.swing.JPanel();
        jTabbedPane8 = new javax.swing.JTabbedPane();
        角色信息1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        角色信息 = new javax.swing.JTable();
        刷新角色信息 = new javax.swing.JButton();
        显示管理角色 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        删除角色 = new javax.swing.JButton();
        角色昵称 = new javax.swing.JTextField();
        等级 = new javax.swing.JTextField();
        力量 = new javax.swing.JTextField();
        敏捷 = new javax.swing.JTextField();
        智力 = new javax.swing.JTextField();
        运气 = new javax.swing.JTextField();
        HP = new javax.swing.JTextField();
        MP = new javax.swing.JTextField();
        金币1 = new javax.swing.JTextField();
        地图 = new javax.swing.JTextField();
        GM = new javax.swing.JTextField();
        jLabel182 = new javax.swing.JLabel();
        jLabel183 = new javax.swing.JLabel();
        jLabel184 = new javax.swing.JLabel();
        jLabel185 = new javax.swing.JLabel();
        jLabel186 = new javax.swing.JLabel();
        jLabel187 = new javax.swing.JLabel();
        jLabel189 = new javax.swing.JLabel();
        jLabel190 = new javax.swing.JLabel();
        jLabel191 = new javax.swing.JLabel();
        jLabel192 = new javax.swing.JLabel();
        jLabel193 = new javax.swing.JLabel();
        角色ID = new javax.swing.JTextField();
        卡号自救1 = new javax.swing.JButton();
        卡号自救2 = new javax.swing.JButton();
        jLabel203 = new javax.swing.JLabel();
        查看技能 = new javax.swing.JButton();
        查看背包 = new javax.swing.JButton();
        卡家族解救 = new javax.swing.JButton();
        脸型 = new javax.swing.JTextField();
        发型 = new javax.swing.JTextField();
        jLabel214 = new javax.swing.JLabel();
        离线角色 = new javax.swing.JButton();
        在线角色 = new javax.swing.JButton();
        显示在线玩家 = new javax.swing.JLabel();
        角色背包 = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel39 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        角色背包穿戴 = new javax.swing.JTable();
        背包物品名字1 = new javax.swing.JTextField();
        身上穿戴序号1 = new javax.swing.JTextField();
        背包物品代码1 = new javax.swing.JTextField();
        jLabel276 = new javax.swing.JLabel();
        jLabel283 = new javax.swing.JLabel();
        jLabel287 = new javax.swing.JLabel();
        删除穿戴装备 = new javax.swing.JButton();
        jPanel40 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        角色装备背包 = new javax.swing.JTable();
        装备背包物品名字 = new javax.swing.JTextField();
        装备背包物品序号 = new javax.swing.JTextField();
        装备背包物品代码 = new javax.swing.JTextField();
        jLabel288 = new javax.swing.JLabel();
        jLabel289 = new javax.swing.JLabel();
        jLabel290 = new javax.swing.JLabel();
        删除装备背包 = new javax.swing.JButton();
        jPanel41 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        角色消耗背包 = new javax.swing.JTable();
        消耗背包物品名字 = new javax.swing.JTextField();
        消耗背包物品序号 = new javax.swing.JTextField();
        消耗背包物品代码 = new javax.swing.JTextField();
        jLabel291 = new javax.swing.JLabel();
        jLabel292 = new javax.swing.JLabel();
        jLabel293 = new javax.swing.JLabel();
        删除消耗背包 = new javax.swing.JButton();
        jPanel42 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        角色设置背包 = new javax.swing.JTable();
        设置背包物品名字 = new javax.swing.JTextField();
        设置背包物品序号 = new javax.swing.JTextField();
        设置背包物品代码 = new javax.swing.JTextField();
        jLabel294 = new javax.swing.JLabel();
        jLabel295 = new javax.swing.JLabel();
        jLabel296 = new javax.swing.JLabel();
        删除设置背包 = new javax.swing.JButton();
        jPanel43 = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        角色其他背包 = new javax.swing.JTable();
        其他背包物品名字 = new javax.swing.JTextField();
        其他背包物品序号 = new javax.swing.JTextField();
        其他背包物品代码 = new javax.swing.JTextField();
        jLabel297 = new javax.swing.JLabel();
        jLabel298 = new javax.swing.JLabel();
        jLabel299 = new javax.swing.JLabel();
        删除其他背包 = new javax.swing.JButton();
        jPanel44 = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        角色特殊背包 = new javax.swing.JTable();
        特殊背包物品名字 = new javax.swing.JTextField();
        特殊背包物品序号 = new javax.swing.JTextField();
        特殊背包物品代码 = new javax.swing.JTextField();
        jLabel300 = new javax.swing.JLabel();
        jLabel301 = new javax.swing.JLabel();
        jLabel302 = new javax.swing.JLabel();
        删除特殊背包 = new javax.swing.JButton();
        jPanel45 = new javax.swing.JPanel();
        jScrollPane21 = new javax.swing.JScrollPane();
        角色游戏仓库 = new javax.swing.JTable();
        游戏仓库物品名字 = new javax.swing.JTextField();
        游戏仓库物品序号 = new javax.swing.JTextField();
        游戏仓库物品代码 = new javax.swing.JTextField();
        jLabel303 = new javax.swing.JLabel();
        jLabel304 = new javax.swing.JLabel();
        jLabel305 = new javax.swing.JLabel();
        删除游戏仓库 = new javax.swing.JButton();
        jPanel46 = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        角色商城仓库 = new javax.swing.JTable();
        商城仓库物品名字 = new javax.swing.JTextField();
        商城仓库物品序号 = new javax.swing.JTextField();
        商城仓库物品代码 = new javax.swing.JTextField();
        jLabel306 = new javax.swing.JLabel();
        jLabel307 = new javax.swing.JLabel();
        jLabel308 = new javax.swing.JLabel();
        删除商城仓库 = new javax.swing.JButton();
        jPanel48 = new javax.swing.JPanel();
        jScrollPane30 = new javax.swing.JScrollPane();
        角色点券拍卖行 = new javax.swing.JTable();
        拍卖行物品名字1 = new javax.swing.JTextField();
        角色点券拍卖行序号 = new javax.swing.JTextField();
        拍卖行物品代码1 = new javax.swing.JTextField();
        jLabel354 = new javax.swing.JLabel();
        jLabel355 = new javax.swing.JLabel();
        jLabel356 = new javax.swing.JLabel();
        删除拍卖行1 = new javax.swing.JButton();
        jPanel47 = new javax.swing.JPanel();
        jScrollPane23 = new javax.swing.JScrollPane();
        角色金币拍卖行 = new javax.swing.JTable();
        拍卖行物品名字 = new javax.swing.JTextField();
        角色金币拍卖行序号 = new javax.swing.JTextField();
        拍卖行物品代码 = new javax.swing.JTextField();
        jLabel309 = new javax.swing.JLabel();
        jLabel310 = new javax.swing.JLabel();
        jLabel311 = new javax.swing.JLabel();
        删除拍卖行 = new javax.swing.JButton();
        技能 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        技能信息 = new javax.swing.JTable();
        技能代码 = new javax.swing.JTextField();
        技能目前等级 = new javax.swing.JTextField();
        技能最高等级 = new javax.swing.JTextField();
        技能名字 = new javax.swing.JTextField();
        jLabel86 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        修改技能 = new javax.swing.JButton();
        技能序号 = new javax.swing.JTextField();
        jLabel188 = new javax.swing.JLabel();
        jLabel204 = new javax.swing.JLabel();
        jLabel205 = new javax.swing.JLabel();
        删除技能 = new javax.swing.JButton();
        修改技能1 = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        怪物爆物 = new javax.swing.JTable();
        怪物爆物序列号 = new javax.swing.JTextField();
        怪物爆物怪物代码 = new javax.swing.JTextField();
        怪物爆物物品代码 = new javax.swing.JTextField();
        怪物爆物爆率 = new javax.swing.JTextField();
        怪物爆物物品名称 = new javax.swing.JTextField();
        删除怪物爆物 = new javax.swing.JButton();
        添加怪物爆物 = new javax.swing.JButton();
        jLabel120 = new javax.swing.JLabel();
        jLabel211 = new javax.swing.JLabel();
        jLabel212 = new javax.swing.JLabel();
        jLabel213 = new javax.swing.JLabel();
        修改怪物爆物 = new javax.swing.JButton();
        刷新怪物爆物 = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        世界爆物 = new javax.swing.JTable();
        世界爆物序列号 = new javax.swing.JTextField();
        世界爆物物品代码 = new javax.swing.JTextField();
        世界爆物爆率 = new javax.swing.JTextField();
        添加世界爆物 = new javax.swing.JButton();
        删除世界爆物 = new javax.swing.JButton();
        jLabel210 = new javax.swing.JLabel();
        jLabel202 = new javax.swing.JLabel();
        jLabel209 = new javax.swing.JLabel();
        世界爆物名称 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        修改世界爆物 = new javax.swing.JButton();
        刷新世界爆物 = new javax.swing.JButton();
        查询物品掉落 = new javax.swing.JButton();
        查询物品掉落代码 = new javax.swing.JTextField();
        查询怪物掉落代码 = new javax.swing.JTextField();
        删除指定的掉落按键 = new javax.swing.JButton();
        删除指定的掉落 = new javax.swing.JTextField();
        修改物品掉落持续时间 = new javax.swing.JButton();
        物品掉落持续时间 = new javax.swing.JTextField();
        jLabel320 = new javax.swing.JLabel();
        刷新怪物卡片 = new javax.swing.JButton();
        地图物品上限 = new javax.swing.JTextField();
        修改物品掉落持续时间1 = new javax.swing.JButton();
        jLabel319 = new javax.swing.JLabel();
        jLabel324 = new javax.swing.JLabel();
        jLabel325 = new javax.swing.JLabel();
        地图刷新频率 = new javax.swing.JTextField();
        修改物品掉落持续时间2 = new javax.swing.JButton();
        jLabel323 = new javax.swing.JLabel();
        jLabel316 = new javax.swing.JLabel();
        查询怪物掉落 = new javax.swing.JButton();
        jLabel321 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane26 = new javax.swing.JScrollPane();
        反应堆 = new javax.swing.JTable();
        删除反应堆物品代码 = new javax.swing.JTextField();
        删除反应堆物品 = new javax.swing.JButton();
        jLabel281 = new javax.swing.JLabel();
        jPanel61 = new javax.swing.JPanel();
        jButton26 = new javax.swing.JButton();
        反应堆序列号 = new javax.swing.JTextField();
        反应堆代码 = new javax.swing.JTextField();
        反应堆物品 = new javax.swing.JTextField();
        反应堆概率 = new javax.swing.JTextField();
        新增反应堆物品 = new javax.swing.JButton();
        删除反应堆物品1 = new javax.swing.JButton();
        查找反应堆掉落 = new javax.swing.JTextField();
        jButton36 = new javax.swing.JButton();
        查找物品 = new javax.swing.JTextField();
        jButton37 = new javax.swing.JButton();
        jLabel274 = new javax.swing.JLabel();
        jLabel275 = new javax.swing.JLabel();
        jLabel277 = new javax.swing.JLabel();
        jLabel278 = new javax.swing.JLabel();
        jLabel279 = new javax.swing.JLabel();
        jLabel280 = new javax.swing.JLabel();
        修改反应堆物品 = new javax.swing.JButton();
        jLabel282 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane27 = new javax.swing.JScrollPane();
        游戏道具 = new javax.swing.JTable();
        游戏道具代码 = new javax.swing.JTextField();
        jLabel338 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        查找道具 = new javax.swing.JButton();
        删除道具 = new javax.swing.JButton();
        jLabel337 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        查找道具1 = new javax.swing.JButton();
        删除道具1 = new javax.swing.JButton();
        jLabel339 = new javax.swing.JLabel();
        jPanel62 = new javax.swing.JPanel();
        查找道具2 = new javax.swing.JButton();
        删除道具2 = new javax.swing.JButton();
        jLabel340 = new javax.swing.JLabel();
        jPanel63 = new javax.swing.JPanel();
        查找道具3 = new javax.swing.JButton();
        删除道具3 = new javax.swing.JButton();
        jLabel341 = new javax.swing.JLabel();
        jPanel64 = new javax.swing.JPanel();
        查找道具4 = new javax.swing.JButton();
        删除道具4 = new javax.swing.JButton();
        jLabel342 = new javax.swing.JLabel();
        jLabel343 = new javax.swing.JLabel();
        jLabel344 = new javax.swing.JLabel();
        jLabel345 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jScrollPane24 = new javax.swing.JScrollPane();
        家族信息 = new javax.swing.JTable();
        刷新家族信息 = new javax.swing.JButton();
        jLabel194 = new javax.swing.JLabel();
        家族ID = new javax.swing.JTextField();
        家族名称 = new javax.swing.JTextField();
        jLabel195 = new javax.swing.JLabel();
        家族族长 = new javax.swing.JTextField();
        jLabel196 = new javax.swing.JLabel();
        jLabel198 = new javax.swing.JLabel();
        家族成员2 = new javax.swing.JTextField();
        jLabel199 = new javax.swing.JLabel();
        家族成员3 = new javax.swing.JTextField();
        jLabel200 = new javax.swing.JLabel();
        家族成员4 = new javax.swing.JTextField();
        jLabel313 = new javax.swing.JLabel();
        家族成员5 = new javax.swing.JTextField();
        jLabel314 = new javax.swing.JLabel();
        家族GP = new javax.swing.JTextField();
        jButton20 = new javax.swing.JButton();
        jPanel65 = new javax.swing.JPanel();
        jScrollPane107 = new javax.swing.JScrollPane();
        封IP = new javax.swing.JTable();
        jScrollPane108 = new javax.swing.JScrollPane();
        封MAC = new javax.swing.JTable();
        刷新封IP = new javax.swing.JButton();
        刷新封MAC = new javax.swing.JButton();
        删除IP代码 = new javax.swing.JTextField();
        删除MAC = new javax.swing.JButton();
        删除IP = new javax.swing.JButton();
        删MAC代码 = new javax.swing.JTextField();
        jLabel346 = new javax.swing.JLabel();
        jLabel347 = new javax.swing.JLabel();
        jPanel66 = new javax.swing.JPanel();
        jPanel69 = new javax.swing.JPanel();
        开启双倍经验 = new javax.swing.JButton();
        双倍经验持续时间 = new javax.swing.JTextField();
        jLabel359 = new javax.swing.JLabel();
        开启双倍爆率 = new javax.swing.JButton();
        双倍爆率持续时间 = new javax.swing.JTextField();
        jLabel360 = new javax.swing.JLabel();
        开启双倍金币 = new javax.swing.JButton();
        双倍金币持续时间 = new javax.swing.JTextField();
        jLabel361 = new javax.swing.JLabel();
        jPanel70 = new javax.swing.JPanel();
        开启三倍经验 = new javax.swing.JButton();
        三倍经验持续时间 = new javax.swing.JTextField();
        jLabel362 = new javax.swing.JLabel();
        开启三倍爆率 = new javax.swing.JButton();
        三倍爆率持续时间 = new javax.swing.JTextField();
        jLabel348 = new javax.swing.JLabel();
        开启三倍金币 = new javax.swing.JButton();
        三倍金币持续时间 = new javax.swing.JTextField();
        jLabel349 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jButton69 = new javax.swing.JButton();
        jButton70 = new javax.swing.JButton();
        jButton72 = new javax.swing.JButton();
        jButton73 = new javax.swing.JButton();
        jButton74 = new javax.swing.JButton();
        jButton75 = new javax.swing.JButton();
        jPanel54 = new javax.swing.JPanel();
        jButton31 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jPanel67 = new javax.swing.JPanel();
        jButton25 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        测试显示图片 = new javax.swing.JTable();
        测试刷新图片按钮 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        RunTime = new javax.swing.JLabel();
        ActiveThread = new javax.swing.JLabel();
        RunStats = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTabbedPane4.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane4.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N

        output_jTextPane1.setEditable(false);
        jScrollPane13.setViewportView(output_jTextPane1);

        consoleInfo.setColumns(20);
        consoleInfo.setFont(new java.awt.Font("宋体", 0, 13)); // NOI18N
        consoleInfo.setForeground(new java.awt.Color(51, 51, 51));
        consoleInfo.setLineWrap(true);
        consoleInfo.setToolTipText("");
        consoleInfo.setDoubleBuffered(true);
        consoleInfo.setInheritsPopupMenu(true);
        consoleInfo.setSelectedTextColor(new java.awt.Color(51, 0, 51));
        jScrollPane1.setViewportView(consoleInfo);

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 1369, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(416, 416, 416)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("全部", jPanel33);

        数据包文本大框.setEditable(false);
        jScrollPane9.setViewportView(数据包文本大框);

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1369, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("数据包", jPanel34);

        提示文本大窗口.setEditable(false);
        jScrollPane10.setViewportView(提示文本大窗口);

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 1369, Short.MAX_VALUE)
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("提示", jPanel35);

        报错文本大框.setEditable(false);
        jScrollPane11.setViewportView(报错文本大框);

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 1369, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("报错", jPanel36);

        其他文本大框.setEditable(false);
        jScrollPane12.setViewportView(其他文本大框);

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 1369, Short.MAX_VALUE)
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("其他", jPanel37);

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        保存雇佣按钮.setText("保存雇佣");
        保存雇佣按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                保存雇佣按钮ActionPerformed(evt);
            }
        });

        保存数据按钮.setText("保存数据");
        保存数据按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                保存数据按钮ActionPerformed(evt);
            }
        });

        查询在线玩家人数按钮.setText("查询在线玩家人数");
        查询在线玩家人数按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查询在线玩家人数按钮ActionPerformed(evt);
            }
        });

        jButton1.setForeground(new java.awt.Color(255, 0, 0));
        jButton1.setText("开启服务端");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton21.setText("关闭服务端");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        状态信息.setText("状态");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(保存雇佣按钮)
                .addGap(4, 4, 4)
                .addComponent(保存数据按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(查询在线玩家人数按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(状态信息)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(进度条, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton21)
                .addGap(4, 4, 4)
                .addComponent(jButton1)
                .addGap(1, 1, 1))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(保存雇佣按钮, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(保存数据按钮, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(查询在线玩家人数按钮, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                        .addComponent(状态信息)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(进度条, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane2)
                .addContainerGap())
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 756, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jTabbedPane4.addTab("启动服务", jPanel1);

        jPanel74.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "风之大陆", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 1, 12))); // NOI18N
        jPanel74.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        蓝蜗牛开关.setText("蓝蜗牛:X");
        蓝蜗牛开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                蓝蜗牛开关ActionPerformed(evt);
            }
        });
        jPanel74.add(蓝蜗牛开关, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 250, -1));

        jPanel75.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "云之大陆", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 1, 12))); // NOI18N

        javax.swing.GroupLayout jPanel75Layout = new javax.swing.GroupLayout(jPanel75);
        jPanel75.setLayout(jPanel75Layout);
        jPanel75Layout.setHorizontalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel75Layout.setVerticalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel76.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "暗之大陆", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 1, 12))); // NOI18N

        javax.swing.GroupLayout jPanel76Layout = new javax.swing.GroupLayout(jPanel76);
        jPanel76.setLayout(jPanel76Layout);
        jPanel76Layout.setHorizontalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1377, Short.MAX_VALUE)
        );
        jPanel76Layout.setVerticalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        jPanel77.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "水之大陆", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 1, 12))); // NOI18N

        javax.swing.GroupLayout jPanel77Layout = new javax.swing.GroupLayout(jPanel77);
        jPanel77.setLayout(jPanel77Layout);
        jPanel77Layout.setHorizontalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel77Layout.setVerticalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel11.setForeground(new java.awt.Color(255, 51, 51));
        jLabel11.setText("注意:请勿全部都开启,会炸客户端的.");

        jLabel56.setForeground(new java.awt.Color(51, 204, 255));
        jLabel56.setText("注意:每个区所建立的角色是不一样的,进入游戏后其他没有变化");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel74, javax.swing.GroupLayout.DEFAULT_SIZE, 1389, Short.MAX_VALUE)
            .addComponent(jPanel75, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel77, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(294, 294, 294))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(165, 165, 165))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel74, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142)
                .addComponent(jPanel75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel77, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 158, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("游戏大区", jPanel10);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("重载系列"));

        重载副本按钮.setText("重载副本");
        重载副本按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载副本按钮ActionPerformed(evt);
            }
        });

        重载爆率按钮.setText("重载爆率");
        重载爆率按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载爆率按钮ActionPerformed(evt);
            }
        });

        重载反应堆按钮.setText("重载反应堆");
        重载反应堆按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载反应堆按钮ActionPerformed(evt);
            }
        });

        重载传送门按钮.setText("重载传送门");
        重载传送门按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载传送门按钮ActionPerformed(evt);
            }
        });

        重载商城按钮.setText("重载商城");
        重载商城按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载商城按钮ActionPerformed(evt);
            }
        });

        重载商店按钮.setText("重载商店");
        重载商店按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载商店按钮ActionPerformed(evt);
            }
        });

        重载包头按钮.setText("重载包头");
        重载包头按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载包头按钮ActionPerformed(evt);
            }
        });

        重载任务.setText("重载任务");
        重载任务.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载任务ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(重载任务, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(重载包头按钮)
                        .addGap(5, 5, 5)
                        .addComponent(重载商店按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(重载副本按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(重载爆率按钮)
                        .addGap(5, 5, 5)
                        .addComponent(重载反应堆按钮)))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(重载商城按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(重载传送门按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(814, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(重载副本按钮, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(重载爆率按钮, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(重载反应堆按钮, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(重载传送门按钮, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(重载任务, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(重载包头按钮, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(重载商店按钮, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(重载商城按钮, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(641, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("重载系列", jPanel8);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("游戏公告"));

        sendNotice.setText("蓝色提示公告");
        sendNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendNoticeActionPerformed(evt);
            }
        });

        sendWinNotice.setText("顶部滚动公告");
        sendWinNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendWinNoticeActionPerformed(evt);
            }
        });

        sendMsgNotice.setText("弹窗公告");
        sendMsgNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMsgNoticeActionPerformed(evt);
            }
        });

        sendNpcTalkNotice.setText("蓝色公告事项");
        sendNpcTalkNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendNpcTalkNoticeActionPerformed(evt);
            }
        });

        noticeText.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        noticeText.setText("游戏即将维护,请安全下线！造成不便请谅解！");

        公告发布喇叭代码.setText("5120027");
        公告发布喇叭代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                公告发布喇叭代码ActionPerformed(evt);
            }
        });

        jButton45.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jButton45.setText("屏幕正中公告");
        jButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton45ActionPerformed(evt);
            }
        });

        jLabel259.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel259.setText("喇叭代码");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(noticeText)
                .addContainerGap())
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(sendNotice)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendWinNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendMsgNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sendNpcTalkNotice)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel259)
                    .addComponent(公告发布喇叭代码, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(541, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(noticeText, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel259)
                        .addGap(0, 0, 0)
                        .addComponent(公告发布喇叭代码, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton45, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sendNpcTalkNotice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sendWinNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sendMsgNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(sendNotice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(291, 291, 291))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("游戏公告", jPanel2);

        游戏商店2.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        游戏商店2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "商店ID", "物品代码", "销售金币", "物品名称"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        游戏商店2.getTableHeader().setReorderingAllowed(false);
        jScrollPane25.setViewportView(游戏商店2);

        jPanel55.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "查询商品出售物品", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel55.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        查询商店2.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        查询商店2.setText("查询商店");
        查询商店2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查询商店2ActionPerformed(evt);
            }
        });
        jPanel55.add(查询商店2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, -1, 30));

        查询商店.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel55.add(查询商店, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 110, 30));

        jLabel270.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel270.setText("商店ID；");
        jPanel55.add(jLabel270, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jPanel56.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "查询商品出售物品", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel56.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        删除商品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除商品.setText("删除");
        删除商品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除商品ActionPerformed(evt);
            }
        });
        jPanel56.add(删除商品, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, -1, 30));

        新增商品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        新增商品.setText("新增");
        新增商品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                新增商品ActionPerformed(evt);
            }
        });
        jPanel56.add(新增商品, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, -1, 30));

        商品序号.setEditable(false);
        商品序号.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel56.add(商品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 80, 30));

        商店代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        商店代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                商店代码ActionPerformed(evt);
            }
        });
        jPanel56.add(商店代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 80, 30));

        商品物品代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel56.add(商品物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 90, 30));

        商品售价金币.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel56.add(商品售价金币, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 100, 30));

        jLabel268.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel268.setText("出售金币；");
        jPanel56.add(jLabel268, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, -1, -1));

        jLabel269.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel269.setText("序号；");
        jPanel56.add(jLabel269, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel271.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel271.setText("物品名称；");
        jPanel56.add(jLabel271, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, -1, -1));

        jLabel272.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel272.setText("商店ID；");
        jPanel56.add(jLabel272, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, -1));

        修改商品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        修改商品.setText("修改");
        修改商品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改商品ActionPerformed(evt);
            }
        });
        jPanel56.add(修改商品, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 120, -1, 30));

        商品名称.setEditable(false);
        商品名称.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        商品名称.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                商品名称ActionPerformed(evt);
            }
        });
        jPanel56.add(商品名称, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 70, 100, 30));

        jLabel273.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel273.setText("物品代码；");
        jPanel56.add(jLabel273, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, -1, -1));

        jButton19.setText("查看全部");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane25)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(283, 283, 283)
                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel55, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel56, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        jTabbedPane4.addTab("商店管理", jPanel3);

        主题馆.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        主题馆.setText("主题馆");
        主题馆.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">主题馆</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">主体馆</font></strong>分类下的所有商品<br> ");
        主题馆.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                主题馆ActionPerformed(evt);
            }
        });

        读取热销产品.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        读取热销产品.setText("热销产品");
        读取热销产品.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">热销产品</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">热销产品</font></strong>分类下的所有商品<br> ");
        读取热销产品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                读取热销产品ActionPerformed(evt);
            }
        });

        活动.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        活动.setText("活动");
        活动.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">活动</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">活动</font></strong>分类下的所有商品<br> ");
        活动.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                活动ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jButton8.setText("每日特卖");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(读取热销产品, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(主题馆, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(活动, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(读取热销产品, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(主题馆, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(活动, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane3.addTab("热销产品", jPanel18);

        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        帽子.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        帽子.setText("帽子");
        帽子.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">帽子</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">帽子</font></strong>分类下的所有商品<br> ");
        帽子.setPreferredSize(new java.awt.Dimension(30, 27));
        帽子.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                帽子ActionPerformed(evt);
            }
        });
        jPanel19.add(帽子, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 20));

        脸饰.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        脸饰.setText("脸饰");
        脸饰.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">脸饰</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">脸饰</font></strong>分类下的所有商品<br> ");
        脸饰.setPreferredSize(new java.awt.Dimension(30, 27));
        脸饰.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                脸饰ActionPerformed(evt);
            }
        });
        jPanel19.add(脸饰, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 60, 20));

        眼饰.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        眼饰.setText("眼饰");
        眼饰.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">眼饰</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">眼饰</font></strong>分类下的所有商品<br> ");
        眼饰.setPreferredSize(new java.awt.Dimension(30, 27));
        眼饰.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                眼饰ActionPerformed(evt);
            }
        });
        jPanel19.add(眼饰, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 60, 20));

        长袍.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        长袍.setText("长袍");
        长袍.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">长袍</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">长袍</font></strong>分类下的所有商品<br> ");
        长袍.setPreferredSize(new java.awt.Dimension(30, 27));
        长袍.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                长袍ActionPerformed(evt);
            }
        });
        jPanel19.add(长袍, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 60, 20));

        上衣.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        上衣.setText("上衣");
        上衣.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">上衣</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">上衣</font></strong>分类下的所有商品<br> ");
        上衣.setPreferredSize(new java.awt.Dimension(30, 27));
        上衣.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                上衣ActionPerformed(evt);
            }
        });
        jPanel19.add(上衣, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 0, 60, 20));

        裙裤.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        裙裤.setText("裙裤");
        裙裤.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">裙裤</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">裙裤</font></strong>分类下的所有商品<br> ");
        裙裤.setPreferredSize(new java.awt.Dimension(30, 27));
        裙裤.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                裙裤ActionPerformed(evt);
            }
        });
        jPanel19.add(裙裤, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 70, 20));

        鞋子.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        鞋子.setText("鞋子");
        鞋子.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">鞋子</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">鞋子</font></strong>分类下的所有商品<br> ");
        鞋子.setPreferredSize(new java.awt.Dimension(30, 27));
        鞋子.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                鞋子ActionPerformed(evt);
            }
        });
        jPanel19.add(鞋子, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 0, 60, 20));

        手套.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        手套.setText("手套");
        手套.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">手套</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">手套</font></strong>分类下的所有商品<br> ");
        手套.setPreferredSize(new java.awt.Dimension(30, 27));
        手套.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                手套ActionPerformed(evt);
            }
        });
        jPanel19.add(手套, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 0, 60, 20));

        武器.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        武器.setText("武器");
        武器.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">武器</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">武器</font></strong>分类下的所有商品<br> ");
        武器.setPreferredSize(new java.awt.Dimension(30, 27));
        武器.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                武器ActionPerformed(evt);
            }
        });
        jPanel19.add(武器, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 0, 60, 20));

        戒指.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        戒指.setText("戒指");
        戒指.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">戒指</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">戒指</font></strong>分类下的所有商品<br> ");
        戒指.setPreferredSize(new java.awt.Dimension(30, 27));
        戒指.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                戒指ActionPerformed(evt);
            }
        });
        jPanel19.add(戒指, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 0, 60, 20));

        飞镖.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        飞镖.setText("飞镖");
        飞镖.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">飞镖</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">飞镖</font></strong>分类下的所有商品<br> ");
        飞镖.setPreferredSize(new java.awt.Dimension(30, 27));
        飞镖.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                飞镖ActionPerformed(evt);
            }
        });
        jPanel19.add(飞镖, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 60, 20));

        披风.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        披风.setText("披风");
        披风.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">披风</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">披风</font></strong>分类下的所有商品<br> ");
        披风.setPreferredSize(new java.awt.Dimension(30, 27));
        披风.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                披风ActionPerformed(evt);
            }
        });
        jPanel19.add(披风, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 70, 20));

        骑宠.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        骑宠.setText("骑宠");
        骑宠.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">骑宠</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">骑宠</font></strong>分类下的所有商品<br> ");
        骑宠.setPreferredSize(new java.awt.Dimension(30, 27));
        骑宠.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                骑宠ActionPerformed(evt);
            }
        });
        jPanel19.add(骑宠, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 60, 20));

        jTabbedPane3.addTab("装备", jPanel19);

        喜庆物品.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        喜庆物品.setText("喜庆物品");
        喜庆物品.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">喜庆物品</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">喜庆物品</font></strong>分类下的所有商品<br> ");
        喜庆物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                喜庆物品ActionPerformed(evt);
            }
        });

        通讯物品.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        通讯物品.setText("通讯物品");
        通讯物品.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">通讯物品</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">通讯物品</font></strong>分类下的所有商品<br> ");
        通讯物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                通讯物品ActionPerformed(evt);
            }
        });

        卷轴.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        卷轴.setText("卷轴");
        卷轴.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">卷轴</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">卷轴</font></strong>分类下的所有商品<br> ");
        卷轴.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                卷轴ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(喜庆物品, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(通讯物品, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(卷轴, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(通讯物品, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(喜庆物品, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(卷轴, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        jTabbedPane3.addTab("消耗", jPanel20);

        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        会员卡.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        会员卡.setText("会员卡");
        会员卡.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">会员卡</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">会员卡</font></strong>分类下的所有商品<br> ");
        会员卡.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                会员卡ActionPerformed(evt);
            }
        });
        jPanel21.add(会员卡, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 20));

        表情.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        表情.setText("表情");
        表情.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">表情</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">表情</font></strong>分类下的所有商品<br> ");
        表情.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                表情ActionPerformed(evt);
            }
        });
        jPanel21.add(表情, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 100, 20));

        个人商店.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        个人商店.setText("个人商店");
        个人商店.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">个人商店</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">个人商店</font></strong>分类下的所有商品<br> ");
        个人商店.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                个人商店ActionPerformed(evt);
            }
        });
        jPanel21.add(个人商店, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 100, 20));

        纪念日.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        纪念日.setText("纪念日");
        纪念日.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                纪念日ActionPerformed(evt);
            }
        });
        jPanel21.add(纪念日, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 100, 20));

        游戏.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        游戏.setText("游戏");
        游戏.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">游戏</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">游戏</font></strong>分类下的所有商品<br> ");
        游戏.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                游戏ActionPerformed(evt);
            }
        });
        jPanel21.add(游戏, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 100, 20));

        效果.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        效果.setText("效果");
        效果.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">效果</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">效果</font></strong>分类下的所有商品<br> ");
        效果.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                效果ActionPerformed(evt);
            }
        });
        jPanel21.add(效果, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 100, 20));

        jTabbedPane3.addTab("其他", jPanel21);

        宠物.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        宠物.setText("宠物");
        宠物.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">宠物</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">宠物</font></strong>分类下的所有商品<br> ");
        宠物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                宠物ActionPerformed(evt);
            }
        });

        宠物服饰.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        宠物服饰.setText("宠物服饰");
        宠物服饰.setToolTipText("<html>\n<strong><font color=\"#FF0000\">点击后；</font></strong><br> \n可在<strong><font color=\"#0000E3\">宠物服饰</font></strong>分类下添加商品<br> \n显示<strong><font color=\"#0000E3\">宠物服饰</font></strong>分类下的所有商品<br> ");
        宠物服饰.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                宠物服饰ActionPerformed(evt);
            }
        });

        其他.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        其他.setText("其他");
        其他.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                其他ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(宠物, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(宠物服饰, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(其他, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(宠物服饰, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(宠物, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(其他, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        jTabbedPane3.addTab("宠物", jPanel22);

        charTable.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        charTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "商品编码", "物品代码", "道具名称", "数量", "价格", "限时/天", "出售状态", "上/下架", "已售出", "库存", "反馈/%", "每日限购"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        charTable.setToolTipText("");
        charTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(charTable);
        if (charTable.getColumnModel().getColumnCount() > 0) {
            charTable.getColumnModel().getColumn(2).setPreferredWidth(200);
            charTable.getColumnModel().getColumn(7).setPreferredWidth(100);
        }

        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "添加值", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        商品数量.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel24.add(商品数量, new org.netbeans.lib.awtextra.AbsoluteConstraints(235, 55, 65, 20));

        商品编码.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        商品编码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                商品编码ActionPerformed(evt);
            }
        });
        jPanel24.add(商品编码, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 25, 65, 20));

        商品代码.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel24.add(商品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 55, 65, -1));

        jLabel30.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel30.setText("商品数量；");
        jPanel24.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, -1, 30));

        jLabel29.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel29.setText("商品代码；");
        jPanel24.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, 30));

        商品价格.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel24.add(商品价格, new org.netbeans.lib.awtextra.AbsoluteConstraints(234, 25, 65, -1));

        商品时间.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel24.add(商品时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 55, 65, 20));

        jLabel32.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel32.setText("商品库存；");
        jPanel24.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 90, 30));

        jLabel33.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel33.setText("限时时间；");
        jPanel24.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, -1, 30));

        jLabel34.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel34.setText("商品编码；");
        jPanel24.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 30));

        jLabel35.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel35.setText("商品价格；");
        jPanel24.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 90, 30));

        商品库存.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel24.add(商品库存, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 25, 65, -1));

        商品折扣.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel24.add(商品折扣, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 25, 65, -1));

        jLabel37.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel37.setText("商品反馈；");
        jPanel24.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 90, 30));

        jLabel36.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel36.setText("每日限购；");
        jPanel24.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 50, 90, 30));

        每日限购.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel24.add(每日限购, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 55, 65, -1));

        jLabel38.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel38.setText("货币类型；");
        jPanel24.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 90, 30));

        货币类型.setEditable(false);
        货币类型.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel24.add(货币类型, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 25, 65, -1));

        商城扩充价格.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        商城扩充价格.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "背包扩充价格"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        商城扩充价格.getTableHeader().setReorderingAllowed(false);
        jScrollPane132.setViewportView(商城扩充价格);

        商城扩充价格修改.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N

        商品出售状态.setEditable(false);
        商品出售状态.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        商品出售状态.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                商品出售状态ActionPerformed(evt);
            }
        });

        显示类型.setEditable(false);
        显示类型.setFont(new java.awt.Font("幼圆", 1, 14)); // NOI18N
        显示类型.setForeground(new java.awt.Color(255, 0, 51));
        显示类型.setText("测试字体");
        显示类型.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                显示类型ActionPerformed(evt);
            }
        });

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton3.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jButton3.setText("重载商城");
        jButton3.setToolTipText("<html>\n<strong><font color=\"#FF0000\">重载商城；</font></strong><br>\n在商城控制台中的修改需要重载才能在游戏中生效");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        刷新模式.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        刷新模式.setText("重载商城");
        刷新模式.setToolTipText("<html>\n<strong><font color=\"#FF0000\">重载商城；</font></strong><br>\n在商城控制台中的修改需要重载才能在游戏中生效");
        刷新模式.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新模式ActionPerformed(evt);
            }
        });

        jButton13.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jButton13.setText("上架");
        jButton13.setToolTipText("<html>\n<strong><font color=\"#FF0000\">上架；</font></strong><br>\n1.选择物品<br>\n2.上架/下架<br>");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton24.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jButton24.setText("删除");
        jButton24.setToolTipText("<html>\n<strong><font color=\"#FF0000\">删除；</font></strong><br>\n1.选择物品<br>\n2.删除<br>");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jButton27.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jButton27.setText("下架");
        jButton27.setToolTipText("<html>\n<strong><font color=\"#FF0000\">下架；</font></strong><br>\n1.选择物品<br>\n2.上架/下架<br>");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        添加.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        添加.setText("添加");
        添加.setToolTipText("<html>\n<strong><font color=\"#FF0000\">添加；</font></strong><br> \n1.选择物品分类<br>\n2.输入商品代码<br>\n3.输入商品数量<br>\n4.输入商品价格<br>\n5.输入限时时间(0代表永久)<br>\n6.选择出售状态<br>");
        添加.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                添加ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 0, 255));
        jButton2.setText("刷新");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        修改.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        修改.setText("修改");
        修改.setToolTipText("<html>\n<strong><font color=\"#FF0000\">修改；</font></strong><br> \n1.在列表中选择需要修改的物品<br>\n2.在文本框中输入修改值<br>\n");
        修改.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改ActionPerformed(evt);
            }
        });

        修改背包扩充价格.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        修改背包扩充价格.setText("修改");
        修改背包扩充价格.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改背包扩充价格ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 902, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(显示类型, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(商品出售状态, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(906, 906, 906)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel17Layout.createSequentialGroup()
                            .addComponent(刷新模式, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel17Layout.createSequentialGroup()
                            .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel17Layout.createSequentialGroup()
                                    .addComponent(添加, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(修改, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel17Layout.createSequentialGroup()
                                    .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel17Layout.createSequentialGroup()
                                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane132, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(修改背包扩充价格, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(商城扩充价格修改, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(260, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane132, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel17Layout.createSequentialGroup()
                                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(商城扩充价格修改, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel17Layout.createSequentialGroup()
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(添加, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(修改, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(修改背包扩充价格, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(刷新模式, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(显示类型, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(商品出售状态, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 318, Short.MAX_VALUE)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6))
        );

        jTabbedPane4.addTab("游戏商城", jPanel17);

        jPanel57.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "全服发送福利", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 12))); // NOI18N
        jPanel57.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        全服发送物品数量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送物品数量ActionPerformed(evt);
            }
        });
        jPanel57.add(全服发送物品数量, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 100, 30));

        全服发送物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送物品代码ActionPerformed(evt);
            }
        });
        jPanel57.add(全服发送物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 110, 30));

        给予物品1.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        给予物品1.setText("给予物品");
        给予物品1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                给予物品1ActionPerformed(evt);
            }
        });
        jPanel57.add(给予物品1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, 100, 30));

        jLabel217.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel217.setText("物品数量；");
        jPanel57.add(jLabel217, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, -1, -1));

        jLabel234.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel234.setText("物品代码；");
        jPanel57.add(jLabel234, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jPanel58.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "全服发送福利", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 12))); // NOI18N
        jPanel58.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        全服发送装备装备加卷.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备加卷ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备加卷, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, 100, 30));

        全服发送装备装备制作人.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备制作人ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备制作人, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 100, 30));

        全服发送装备装备力量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备力量ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备力量, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 100, 30));

        全服发送装备装备MP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备MPActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备MP, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 100, 30));

        全服发送装备装备智力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备智力ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备智力, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 180, 100, 30));

        全服发送装备装备运气.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备运气ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备运气, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, 100, 30));

        全服发送装备装备HP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备HPActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备HP, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 100, 30));

        全服发送装备装备攻击力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备攻击力ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备攻击力, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 100, 30));

        全服发送装备装备给予时间.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备给予时间ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备给予时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 240, 100, 30));

        全服发送装备装备可否交易.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备可否交易ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备可否交易, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 180, 100, 30));

        全服发送装备装备敏捷.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备敏捷ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备敏捷, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 100, 30));

        全服发送装备物品ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备物品IDActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备物品ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 100, 30));

        全服发送装备装备魔法力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备魔法力ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备魔法力, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 100, 30));

        全服发送装备装备魔法防御.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备魔法防御ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备魔法防御, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 100, 30));

        全服发送装备装备物理防御.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备物理防御ActionPerformed(evt);
            }
        });
        jPanel58.add(全服发送装备装备物理防御, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 100, 30));

        给予装备1.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        给予装备1.setText("个人发送");
        给予装备1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                给予装备1ActionPerformed(evt);
            }
        });
        jPanel58.add(给予装备1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 160, 100, 30));

        jLabel219.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel219.setText("能否交易；填0");
        jPanel58.add(jLabel219, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, -1, -1));

        jLabel220.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel220.setText("HP加成；");
        jPanel58.add(jLabel220, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, -1));

        jLabel221.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel221.setText("魔法攻击力；");
        jPanel58.add(jLabel221, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, -1, -1));

        jLabel222.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel222.setText("装备代码；");
        jPanel58.add(jLabel222, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, -1, -1));

        jLabel223.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel223.setText("MP加成；");
        jPanel58.add(jLabel223, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, -1, -1));

        jLabel224.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel224.setText("物理攻击力；");
        jPanel58.add(jLabel224, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, -1, -1));

        jLabel225.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel225.setText("可砸卷次数；");
        jPanel58.add(jLabel225, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, -1, -1));

        jLabel226.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel226.setText("装备署名；");
        jPanel58.add(jLabel226, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, -1, -1));

        jLabel227.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel227.setText("装备力量；");
        jPanel58.add(jLabel227, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, -1, -1));

        jLabel228.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel228.setText("装备敏捷；");
        jPanel58.add(jLabel228, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, -1, -1));

        jLabel229.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel229.setText("装备智力；");
        jPanel58.add(jLabel229, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, -1, -1));

        jLabel230.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel230.setText("装备运气；");
        jPanel58.add(jLabel230, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, -1, -1));

        jLabel231.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel231.setText("魔法防御；");
        jPanel58.add(jLabel231, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, -1, -1));

        jLabel232.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel232.setText("物理防御；");
        jPanel58.add(jLabel232, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, -1, -1));

        jLabel233.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel233.setText("限时时间；");
        jPanel58.add(jLabel233, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 220, -1, -1));

        发送装备玩家姓名.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                发送装备玩家姓名ActionPerformed(evt);
            }
        });
        jPanel58.add(发送装备玩家姓名, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 60, 100, 30));

        给予装备2.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        给予装备2.setText("全服发送");
        给予装备2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                给予装备2ActionPerformed(evt);
            }
        });
        jPanel58.add(给予装备2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 120, 100, 30));

        jLabel246.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel246.setText("玩家名字；");
        jPanel58.add(jLabel246, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 40, -1, -1));

        jLabel244.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel244.setText("个人发送需要填写名字");
        jPanel58.add(jLabel244, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 250, -1, -1));

        jPanel59.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "全服发送福利", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 12))); // NOI18N
        jPanel59.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        z2.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        z2.setText("发送抵用");
        z2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z2ActionPerformed(evt);
            }
        });
        jPanel59.add(z2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 100, 30));

        z3.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        z3.setText("发送金币");
        z3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z3ActionPerformed(evt);
            }
        });
        jPanel59.add(z3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 100, 30));

        z1.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        z1.setText("发送点券");
        z1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z1ActionPerformed(evt);
            }
        });
        jPanel59.add(z1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 100, 30));

        z4.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        z4.setText("发送经验");
        z4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z4ActionPerformed(evt);
            }
        });
        jPanel59.add(z4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 100, 30));

        z5.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        z5.setText("发送人气");
        z5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z5ActionPerformed(evt);
            }
        });
        jPanel59.add(z5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 100, 30));

        z6.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        z6.setText("发送豆豆");
        z6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z6ActionPerformed(evt);
            }
        });
        jPanel59.add(z6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, 100, 30));

        a1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a1ActionPerformed(evt);
            }
        });
        jPanel59.add(a1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 100, 30));

        jLabel235.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel235.setText("数量；");
        jPanel59.add(jLabel235, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, -1, -1));

        jPanel60.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "个人发送物品", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 12))); // NOI18N
        jPanel60.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        个人发送物品数量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                个人发送物品数量ActionPerformed(evt);
            }
        });
        jPanel60.add(个人发送物品数量, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 80, 30));

        个人发送物品玩家名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                个人发送物品玩家名字ActionPerformed(evt);
            }
        });
        jPanel60.add(个人发送物品玩家名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 130, 30));

        个人发送物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                个人发送物品代码ActionPerformed(evt);
            }
        });
        jPanel60.add(个人发送物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 130, 30));

        给予物品.setBackground(new java.awt.Color(255, 255, 255));
        给予物品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        给予物品.setText("给予物品");
        给予物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                给予物品ActionPerformed(evt);
            }
        });
        jPanel60.add(给予物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, 100, 30));

        jLabel240.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel240.setText("物品数量；");
        jPanel60.add(jLabel240, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, -1, -1));

        jLabel241.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel241.setText("玩家名字；");
        jPanel60.add(jLabel241, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jLabel242.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel242.setText("物品代码；");
        jPanel60.add(jLabel242, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, -1, -1));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel60, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel60, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel59, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jPanel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("发送装备", jPanel4);

        账号信息.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        账号信息.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "账号ID", "账号", "IP地址", "MAC地址", "绑定QQ", "点券", "抵用", "最近上线", "在线", "封号", "GM"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        账号信息.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(账号信息);

        jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "账号修改", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        抵用.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel30.add(抵用, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 120, 30));

        账号.setEditable(false);
        账号.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel30.add(账号, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 100, 30));

        点券.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel30.add(点券, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, 120, 30));

        jLabel55.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel55.setText("抵用；");
        jPanel30.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, 60, -1));

        jLabel131.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel131.setText("点券；");
        jPanel30.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, -1, -1));

        修改账号点券抵用.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        修改账号点券抵用.setText("修改");
        修改账号点券抵用.setToolTipText("<html>\n点击账号后可修改账号的<strong><font color=\"#FF0000\">抵用券</font></strong><strong>和<font color=\"#FF0000\">点券</font></strong>");
        修改账号点券抵用.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改账号点券抵用ActionPerformed(evt);
            }
        });
        jPanel30.add(修改账号点券抵用, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 40, 70, 30));

        账号ID.setEditable(false);
        账号ID.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel30.add(账号ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 70, 30));

        jLabel206.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel206.setText("ID；");
        jPanel30.add(jLabel206, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel312.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel312.setText("管理；");
        jPanel30.add(jLabel312, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, -1, -1));

        管理1.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel30.add(管理1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 40, 70, 30));

        jLabel353.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel353.setText("账号；");
        jPanel30.add(jLabel353, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

        QQ.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jPanel30.add(QQ, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 120, 30));

        jLabel357.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel357.setText("绑定QQ；");
        jPanel30.add(jLabel357, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 80, -1));

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "注册/修改", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        注册的账号.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        注册的账号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                注册的账号ActionPerformed(evt);
            }
        });
        jPanel32.add(注册的账号, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 100, 30));

        注册的密码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        注册的密码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                注册的密码ActionPerformed(evt);
            }
        });
        jPanel32.add(注册的密码, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, 100, 30));

        jButton35.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jButton35.setText("注册");
        jButton35.setToolTipText("<html>\n输入<strong><font color=\"#FF0000\">账号</font></strong><strong>和<strong><font color=\"#FF0000\">密码</font></strong><strong>即可注册账号");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });
        jPanel32.add(jButton35, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 70, 30));

        jLabel111.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel111.setText("账号；");
        jPanel32.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 30));

        jLabel201.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel201.setText("密码；");
        jPanel32.add(jLabel201, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, 30));

        jButton30.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jButton30.setText("改密");
        jButton30.setToolTipText("<html>\n输入账号修改<strong><font color=\"#FF0000\">密码</font></strong><strong>");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });
        jPanel32.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 70, 30));

        刷新账号信息.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        刷新账号信息.setText("全部账号");
        刷新账号信息.setToolTipText("显示所有玩家账号");
        刷新账号信息.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新账号信息ActionPerformed(evt);
            }
        });

        离线账号.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        离线账号.setText("离线账号");
        离线账号.setToolTipText("显示离线账号");
        离线账号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                离线账号ActionPerformed(evt);
            }
        });

        解封.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        解封.setText("解封账号");
        解封.setToolTipText("<html>\n在文本框<strong><font color=\"#FF0000\">操作的账号</font></strong>中输入账号即可解封已经被封禁的账号<br>\n");
        解封.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                解封ActionPerformed(evt);
            }
        });

        已封账号.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        已封账号.setText("已封账号");
        已封账号.setToolTipText("显示已经被封禁的账号");
        已封账号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                已封账号ActionPerformed(evt);
            }
        });

        在线账号.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        在线账号.setText("在线账号");
        在线账号.setToolTipText("显示在线账号");
        在线账号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                在线账号ActionPerformed(evt);
            }
        });

        删除账号.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        删除账号.setText("删除账号");
        删除账号.setToolTipText("<html>\n在文本框<strong><font color=\"#FF0000\">操作的账号</font></strong>中输入账号即可删除账号<br>");
        删除账号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除账号ActionPerformed(evt);
            }
        });

        封锁账号.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        封锁账号.setText("封锁账号");
        封锁账号.setToolTipText("<html>\n在文本框<strong><font color=\"#FF0000\">操作的账号</font></strong>中输入账号即可封禁账号<br>");
        封锁账号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                封锁账号ActionPerformed(evt);
            }
        });

        解卡.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        解卡.setText("解卡账号");
        解卡.setToolTipText("<html>\n在文本框<strong><font color=\"#FF0000\">操作的账号</font></strong>中输入账号即可解卡账号<br>");
        解卡.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                解卡ActionPerformed(evt);
            }
        });

        显示在线账号.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N

        账号提示语言.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        账号提示语言.setText("[信息]：");

        jButton12.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jButton12.setText("查账号");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        账号操作.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        账号操作.setText("40074907");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(刷新账号信息)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(在线账号)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(离线账号)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(已封账号)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(删除账号)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(解卡)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(封锁账号)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(解封)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(账号操作, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(账号提示语言, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(显示在线账号, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, 903, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 883, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(刷新账号信息, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(在线账号, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(离线账号, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(已封账号, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(删除账号, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(解卡, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(封锁账号, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(解封, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(账号操作, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(显示在线账号, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(账号提示语言, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("账户中心", jPanel5);

        jTabbedPane8.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N

        角色信息1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色信息.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        角色信息.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        角色信息.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "角色ID", "账号ID", "角色昵称", "职业", "等级", "力量", "敏捷", "智力", "运气", "MaxHP", "MaxMP", "金币", "所在地图", "状态", "GM", "发型", "脸型"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色信息.setName(""); // NOI18N
        角色信息.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(角色信息);

        角色信息1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1390, 490));

        刷新角色信息.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        刷新角色信息.setText("刷新");
        刷新角色信息.setToolTipText("显示所有角色");
        刷新角色信息.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新角色信息ActionPerformed(evt);
            }
        });
        角色信息1.add(刷新角色信息, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 110, 30));

        显示管理角色.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        显示管理角色.setText("管理角色");
        显示管理角色.setToolTipText("显示所有GM管理员");
        显示管理角色.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                显示管理角色ActionPerformed(evt);
            }
        });
        角色信息1.add(显示管理角色, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, 110, 30));

        jButton38.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jButton38.setText("修改");
        jButton38.setToolTipText("<html>\n修改角色信息<strong><font color=\"#FF0000\">文本框不可留空</font></strong><strong>");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });
        角色信息1.add(jButton38, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 580, 110, 40));

        删除角色.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        删除角色.setText("删除角色");
        删除角色.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除角色ActionPerformed(evt);
            }
        });
        角色信息1.add(删除角色, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 500, 130, 30));

        角色昵称.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色昵称.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                角色昵称ActionPerformed(evt);
            }
        });
        角色信息1.add(角色昵称, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 590, 70, 30));

        等级.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        等级.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                等级ActionPerformed(evt);
            }
        });
        角色信息1.add(等级, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 590, 40, 30));

        力量.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        力量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                力量ActionPerformed(evt);
            }
        });
        角色信息1.add(力量, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 590, 40, 30));

        敏捷.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        敏捷.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                敏捷ActionPerformed(evt);
            }
        });
        角色信息1.add(敏捷, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 590, 40, 30));

        智力.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        智力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                智力ActionPerformed(evt);
            }
        });
        角色信息1.add(智力, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 590, 40, 30));

        运气.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        运气.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                运气ActionPerformed(evt);
            }
        });
        角色信息1.add(运气, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 590, 40, 30));

        HP.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        HP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HPActionPerformed(evt);
            }
        });
        角色信息1.add(HP, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 590, 50, 30));

        MP.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        MP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MPActionPerformed(evt);
            }
        });
        角色信息1.add(MP, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 590, 50, 30));

        金币1.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        金币1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                金币1ActionPerformed(evt);
            }
        });
        角色信息1.add(金币1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 590, 80, 30));

        地图.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        地图.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                地图ActionPerformed(evt);
            }
        });
        角色信息1.add(地图, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 590, 80, 30));

        GM.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        GM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GMActionPerformed(evt);
            }
        });
        角色信息1.add(GM, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 590, 40, 30));

        jLabel182.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel182.setText("GM等级；");
        角色信息1.add(jLabel182, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 570, -1, -1));

        jLabel183.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel183.setText("角色ID；");
        角色信息1.add(jLabel183, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 570, -1, -1));

        jLabel184.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel184.setText("等级；");
        角色信息1.add(jLabel184, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 570, -1, -1));

        jLabel185.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel185.setText("力量；");
        角色信息1.add(jLabel185, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 570, -1, -1));

        jLabel186.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel186.setText("敏捷；");
        角色信息1.add(jLabel186, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 570, -1, -1));

        jLabel187.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel187.setText("智力；");
        角色信息1.add(jLabel187, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 570, -1, -1));

        jLabel189.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel189.setText("MaxHP；");
        角色信息1.add(jLabel189, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 570, -1, -1));

        jLabel190.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel190.setText("MaxMP；");
        角色信息1.add(jLabel190, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 570, -1, -1));

        jLabel191.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel191.setText("金币；");
        角色信息1.add(jLabel191, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 570, -1, -1));

        jLabel192.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel192.setText("发型/脸型");
        角色信息1.add(jLabel192, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 500, -1, 30));

        jLabel193.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel193.setText("角色昵称；");
        角色信息1.add(jLabel193, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 570, -1, -1));

        角色ID.setEditable(false);
        角色ID.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                角色IDActionPerformed(evt);
            }
        });
        角色信息1.add(角色ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 590, 40, 30));

        卡号自救1.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        卡号自救1.setText("卡发/脸型解救");
        卡号自救1.setToolTipText("<html>\n角色卡<strong><font color=\"#FF0000\">发型</font></strong><strong>或者<strong><font color=\"#FF0000\">脸型</font></strong><strong>时候可用此功能\n");
        卡号自救1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                卡号自救1ActionPerformed(evt);
            }
        });
        角色信息1.add(卡号自救1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 500, 130, 30));

        卡号自救2.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        卡号自救2.setText("卡物品解救");
        卡号自救2.setToolTipText("<html>\n次卡号解救会对角色进行<strong><font color=\"#FF0000\">清空物品</font></strong><strong>处理");
        卡号自救2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                卡号自救2ActionPerformed(evt);
            }
        });
        角色信息1.add(卡号自救2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 530, 130, 30));

        jLabel203.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel203.setText("运气；");
        角色信息1.add(jLabel203, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 570, -1, -1));

        查看技能.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        查看技能.setText("查看角色技能");
        查看技能.setToolTipText("<html>\n选择角色后，点击此功能，可查看角色所有<strong><font color=\"#FF0000\">技能信息</font></strong><strong>");
        查看技能.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查看技能ActionPerformed(evt);
            }
        });
        角色信息1.add(查看技能, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 530, 130, 30));

        查看背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        查看背包.setText("查看角色背包");
        查看背包.setToolTipText("<html>\n选择角色后，点击此功能，可查看角色所有<strong><font color=\"#FF0000\">物品信息</font></strong><strong>");
        查看背包.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查看背包ActionPerformed(evt);
            }
        });
        角色信息1.add(查看背包, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 530, 130, 30));

        卡家族解救.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        卡家族解救.setText("卡家族解救");
        卡家族解救.setToolTipText("<html>\n角色卡<strong><font color=\"#FF0000\">发型</font></strong><strong>或者<strong><font color=\"#FF0000\">脸型</font></strong><strong>时候可用此功能\n");
        卡家族解救.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                卡家族解救ActionPerformed(evt);
            }
        });
        角色信息1.add(卡家族解救, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 500, 130, 30));

        脸型.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        脸型.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                脸型ActionPerformed(evt);
            }
        });
        角色信息1.add(脸型, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 500, 60, 30));

        发型.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        发型.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                发型ActionPerformed(evt);
            }
        });
        角色信息1.add(发型, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 500, 60, 30));

        jLabel214.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel214.setText("所在地图；");
        角色信息1.add(jLabel214, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 570, -1, -1));

        离线角色.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        离线角色.setText("离线角色");
        离线角色.setToolTipText("显示所有GM管理员");
        离线角色.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                离线角色ActionPerformed(evt);
            }
        });
        角色信息1.add(离线角色, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 530, 110, 30));

        在线角色.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        在线角色.setText("在线角色");
        在线角色.setToolTipText("显示所有GM管理员");
        在线角色.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                在线角色ActionPerformed(evt);
            }
        });
        角色信息1.add(在线角色, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 500, 110, 30));

        显示在线玩家.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        角色信息1.add(显示在线玩家, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 495, 130, 30));

        jTabbedPane8.addTab("角色信息", 角色信息1);

        角色背包.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane5.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N

        jPanel39.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "角色穿戴装备信息", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel39.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色背包穿戴.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色背包穿戴.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色背包穿戴.getTableHeader().setReorderingAllowed(false);
        jScrollPane15.setViewportView(角色背包穿戴);

        jPanel39.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 860, 480));

        背包物品名字1.setEditable(false);
        背包物品名字1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                背包物品名字1ActionPerformed(evt);
            }
        });
        jPanel39.add(背包物品名字1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 550, 150, 30));

        身上穿戴序号1.setEditable(false);
        身上穿戴序号1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                身上穿戴序号1ActionPerformed(evt);
            }
        });
        jPanel39.add(身上穿戴序号1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 550, 110, 30));

        背包物品代码1.setEditable(false);
        背包物品代码1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                背包物品代码1ActionPerformed(evt);
            }
        });
        jPanel39.add(背包物品代码1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 550, 110, 30));

        jLabel276.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel276.setText("序号；");
        jPanel39.add(jLabel276, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, -1, 20));

        jLabel283.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel283.setText("物品名字；");
        jPanel39.add(jLabel283, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 530, -1, 20));

        jLabel287.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel287.setText("物品代码；");
        jPanel39.add(jLabel287, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 530, -1, 20));

        删除穿戴装备.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除穿戴装备.setText("删除");
        删除穿戴装备.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除穿戴装备ActionPerformed(evt);
            }
        });
        jPanel39.add(删除穿戴装备, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 550, -1, 30));

        jTabbedPane5.addTab("身上穿戴", jPanel39);

        jPanel40.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "装备背包", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel40.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色装备背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色装备背包.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色装备背包.getTableHeader().setReorderingAllowed(false);
        jScrollPane16.setViewportView(角色装备背包);

        jPanel40.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 850, 480));

        装备背包物品名字.setEditable(false);
        装备背包物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                装备背包物品名字ActionPerformed(evt);
            }
        });
        jPanel40.add(装备背包物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 550, 150, 30));

        装备背包物品序号.setEditable(false);
        装备背包物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                装备背包物品序号ActionPerformed(evt);
            }
        });
        jPanel40.add(装备背包物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 550, 110, 30));

        装备背包物品代码.setEditable(false);
        装备背包物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                装备背包物品代码ActionPerformed(evt);
            }
        });
        jPanel40.add(装备背包物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 550, 110, 30));

        jLabel288.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel288.setText("序号；");
        jPanel40.add(jLabel288, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, -1, 20));

        jLabel289.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel289.setText("物品名字；");
        jPanel40.add(jLabel289, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 530, -1, 20));

        jLabel290.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel290.setText("物品代码；");
        jPanel40.add(jLabel290, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 530, -1, 20));

        删除装备背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除装备背包.setText("删除");
        删除装备背包.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除装备背包ActionPerformed(evt);
            }
        });
        jPanel40.add(删除装备背包, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 550, -1, 30));

        jTabbedPane5.addTab("装备背包", jPanel40);

        jPanel41.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "消耗背包", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel41.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色消耗背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色消耗背包.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字", "物品数量"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色消耗背包.getTableHeader().setReorderingAllowed(false);
        jScrollPane17.setViewportView(角色消耗背包);

        jPanel41.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        消耗背包物品名字.setEditable(false);
        消耗背包物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                消耗背包物品名字ActionPerformed(evt);
            }
        });
        jPanel41.add(消耗背包物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 550, 150, 30));

        消耗背包物品序号.setEditable(false);
        消耗背包物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                消耗背包物品序号ActionPerformed(evt);
            }
        });
        jPanel41.add(消耗背包物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 550, 110, 30));

        消耗背包物品代码.setEditable(false);
        消耗背包物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                消耗背包物品代码ActionPerformed(evt);
            }
        });
        jPanel41.add(消耗背包物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 550, 110, 30));

        jLabel291.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel291.setText("序号；");
        jPanel41.add(jLabel291, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 530, -1, 20));

        jLabel292.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel292.setText("物品名字；");
        jPanel41.add(jLabel292, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 530, -1, 20));

        jLabel293.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel293.setText("物品代码；");
        jPanel41.add(jLabel293, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 530, -1, 20));

        删除消耗背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除消耗背包.setText("删除");
        删除消耗背包.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除消耗背包ActionPerformed(evt);
            }
        });
        jPanel41.add(删除消耗背包, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 550, -1, 30));

        jTabbedPane5.addTab("消耗背包", jPanel41);

        jPanel42.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "设置背包", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色设置背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色设置背包.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字", "物品数量"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色设置背包.getTableHeader().setReorderingAllowed(false);
        jScrollPane18.setViewportView(角色设置背包);

        jPanel42.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        设置背包物品名字.setEditable(false);
        设置背包物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                设置背包物品名字ActionPerformed(evt);
            }
        });
        jPanel42.add(设置背包物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 550, 150, 30));

        设置背包物品序号.setEditable(false);
        设置背包物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                设置背包物品序号ActionPerformed(evt);
            }
        });
        jPanel42.add(设置背包物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 550, 110, 30));

        设置背包物品代码.setEditable(false);
        设置背包物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                设置背包物品代码ActionPerformed(evt);
            }
        });
        jPanel42.add(设置背包物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 550, 110, 30));

        jLabel294.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel294.setText("序号；");
        jPanel42.add(jLabel294, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 530, -1, 20));

        jLabel295.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel295.setText("物品名字；");
        jPanel42.add(jLabel295, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 530, -1, 20));

        jLabel296.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel296.setText("物品代码；");
        jPanel42.add(jLabel296, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 530, -1, 20));

        删除设置背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除设置背包.setText("删除");
        删除设置背包.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除设置背包ActionPerformed(evt);
            }
        });
        jPanel42.add(删除设置背包, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 550, -1, 30));

        jTabbedPane5.addTab("设置背包", jPanel42);

        jPanel43.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "其他背包", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel43.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色其他背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色其他背包.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字", "物品数量"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色其他背包.getTableHeader().setReorderingAllowed(false);
        jScrollPane19.setViewportView(角色其他背包);

        jPanel43.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        其他背包物品名字.setEditable(false);
        其他背包物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                其他背包物品名字ActionPerformed(evt);
            }
        });
        jPanel43.add(其他背包物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 550, 150, 30));

        其他背包物品序号.setEditable(false);
        其他背包物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                其他背包物品序号ActionPerformed(evt);
            }
        });
        jPanel43.add(其他背包物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 550, 110, 30));

        其他背包物品代码.setEditable(false);
        其他背包物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                其他背包物品代码ActionPerformed(evt);
            }
        });
        jPanel43.add(其他背包物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 550, 110, 30));

        jLabel297.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel297.setText("序号；");
        jPanel43.add(jLabel297, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 530, -1, 20));

        jLabel298.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel298.setText("物品名字；");
        jPanel43.add(jLabel298, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 530, -1, 20));

        jLabel299.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel299.setText("物品代码；");
        jPanel43.add(jLabel299, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 530, -1, 20));

        删除其他背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除其他背包.setText("删除");
        删除其他背包.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除其他背包ActionPerformed(evt);
            }
        });
        jPanel43.add(删除其他背包, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 550, -1, 30));

        jTabbedPane5.addTab("其他背包", jPanel43);

        jPanel44.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "特殊背包", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel44.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色特殊背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色特殊背包.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字", "物品数量"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色特殊背包.getTableHeader().setReorderingAllowed(false);
        jScrollPane20.setViewportView(角色特殊背包);

        jPanel44.add(jScrollPane20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        特殊背包物品名字.setEditable(false);
        特殊背包物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                特殊背包物品名字ActionPerformed(evt);
            }
        });
        jPanel44.add(特殊背包物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 550, 150, 30));

        特殊背包物品序号.setEditable(false);
        特殊背包物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                特殊背包物品序号ActionPerformed(evt);
            }
        });
        jPanel44.add(特殊背包物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 550, 110, 30));

        特殊背包物品代码.setEditable(false);
        特殊背包物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                特殊背包物品代码ActionPerformed(evt);
            }
        });
        jPanel44.add(特殊背包物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 550, 110, 30));

        jLabel300.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel300.setText("序号；");
        jPanel44.add(jLabel300, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, -1, 20));

        jLabel301.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel301.setText("物品名字；");
        jPanel44.add(jLabel301, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 530, -1, 20));

        jLabel302.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel302.setText("物品代码；");
        jPanel44.add(jLabel302, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 530, -1, 20));

        删除特殊背包.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除特殊背包.setText("删除");
        删除特殊背包.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除特殊背包ActionPerformed(evt);
            }
        });
        jPanel44.add(删除特殊背包, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 550, -1, 30));

        jTabbedPane5.addTab("特殊背包", jPanel44);

        jPanel45.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "游戏仓库", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel45.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色游戏仓库.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色游戏仓库.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字", "物品数量"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色游戏仓库.getTableHeader().setReorderingAllowed(false);
        jScrollPane21.setViewportView(角色游戏仓库);

        jPanel45.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        游戏仓库物品名字.setEditable(false);
        游戏仓库物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                游戏仓库物品名字ActionPerformed(evt);
            }
        });
        jPanel45.add(游戏仓库物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 550, 150, 30));

        游戏仓库物品序号.setEditable(false);
        游戏仓库物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                游戏仓库物品序号ActionPerformed(evt);
            }
        });
        jPanel45.add(游戏仓库物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 550, 110, 30));

        游戏仓库物品代码.setEditable(false);
        游戏仓库物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                游戏仓库物品代码ActionPerformed(evt);
            }
        });
        jPanel45.add(游戏仓库物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 550, 110, 30));

        jLabel303.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel303.setText("序号；");
        jPanel45.add(jLabel303, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 530, -1, 20));

        jLabel304.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel304.setText("物品名字；");
        jPanel45.add(jLabel304, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 530, -1, 20));

        jLabel305.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel305.setText("物品代码；");
        jPanel45.add(jLabel305, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 530, -1, 20));

        删除游戏仓库.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除游戏仓库.setText("删除");
        删除游戏仓库.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除游戏仓库ActionPerformed(evt);
            }
        });
        jPanel45.add(删除游戏仓库, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 550, -1, 30));

        jTabbedPane5.addTab("游戏仓库", jPanel45);

        jPanel46.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "商城仓库", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel46.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色商城仓库.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色商城仓库.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字", "物品数量"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色商城仓库.getTableHeader().setReorderingAllowed(false);
        jScrollPane22.setViewportView(角色商城仓库);

        jPanel46.add(jScrollPane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        商城仓库物品名字.setEditable(false);
        商城仓库物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                商城仓库物品名字ActionPerformed(evt);
            }
        });
        jPanel46.add(商城仓库物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 550, 150, 30));

        商城仓库物品序号.setEditable(false);
        商城仓库物品序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                商城仓库物品序号ActionPerformed(evt);
            }
        });
        jPanel46.add(商城仓库物品序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 550, 110, 30));

        商城仓库物品代码.setEditable(false);
        商城仓库物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                商城仓库物品代码ActionPerformed(evt);
            }
        });
        jPanel46.add(商城仓库物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 550, 110, 30));

        jLabel306.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel306.setText("序号；");
        jPanel46.add(jLabel306, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 530, -1, 20));

        jLabel307.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel307.setText("物品名字；");
        jPanel46.add(jLabel307, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 530, -1, 20));

        jLabel308.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel308.setText("物品代码；");
        jPanel46.add(jLabel308, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 530, -1, 20));

        删除商城仓库.setText("删除");
        删除商城仓库.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除商城仓库ActionPerformed(evt);
            }
        });
        jPanel46.add(删除商城仓库, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 550, -1, 30));

        jTabbedPane5.addTab("商城仓库", jPanel46);

        jPanel48.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "点券拍卖行", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel48.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色点券拍卖行.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色点券拍卖行.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字", "物品数量"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色点券拍卖行.getTableHeader().setReorderingAllowed(false);
        jScrollPane30.setViewportView(角色点券拍卖行);

        jPanel48.add(jScrollPane30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        拍卖行物品名字1.setEditable(false);
        拍卖行物品名字1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                拍卖行物品名字1ActionPerformed(evt);
            }
        });
        jPanel48.add(拍卖行物品名字1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 550, 150, 30));

        角色点券拍卖行序号.setEditable(false);
        角色点券拍卖行序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                角色点券拍卖行序号ActionPerformed(evt);
            }
        });
        jPanel48.add(角色点券拍卖行序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 550, 110, 30));

        拍卖行物品代码1.setEditable(false);
        拍卖行物品代码1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                拍卖行物品代码1ActionPerformed(evt);
            }
        });
        jPanel48.add(拍卖行物品代码1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 550, 110, 30));

        jLabel354.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel354.setText("序号；");
        jPanel48.add(jLabel354, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 530, -1, 20));

        jLabel355.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel355.setText("物品名字；");
        jPanel48.add(jLabel355, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 530, -1, 20));

        jLabel356.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel356.setText("物品代码；");
        jPanel48.add(jLabel356, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 530, -1, 20));

        删除拍卖行1.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除拍卖行1.setText("删除");
        删除拍卖行1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除拍卖行1ActionPerformed(evt);
            }
        });
        jPanel48.add(删除拍卖行1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 550, -1, 30));

        jTabbedPane5.addTab("点券拍卖行", jPanel48);

        jPanel47.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "金币拍卖行", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel47.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        角色金币拍卖行.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        角色金币拍卖行.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品代码", "物品名字"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        角色金币拍卖行.getTableHeader().setReorderingAllowed(false);
        jScrollPane23.setViewportView(角色金币拍卖行);

        jPanel47.add(jScrollPane23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 860, 490));

        拍卖行物品名字.setEditable(false);
        拍卖行物品名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                拍卖行物品名字ActionPerformed(evt);
            }
        });
        jPanel47.add(拍卖行物品名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 550, 150, 30));

        角色金币拍卖行序号.setEditable(false);
        角色金币拍卖行序号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                角色金币拍卖行序号ActionPerformed(evt);
            }
        });
        jPanel47.add(角色金币拍卖行序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 550, 110, 30));

        拍卖行物品代码.setEditable(false);
        拍卖行物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                拍卖行物品代码ActionPerformed(evt);
            }
        });
        jPanel47.add(拍卖行物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 550, 110, 30));

        jLabel309.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel309.setText("序号；");
        jPanel47.add(jLabel309, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, -1, 20));

        jLabel310.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel310.setText("物品名字；");
        jPanel47.add(jLabel310, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 530, -1, 20));

        jLabel311.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel311.setText("物品代码；");
        jPanel47.add(jLabel311, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 530, -1, 20));

        删除拍卖行.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除拍卖行.setText("删除");
        删除拍卖行.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除拍卖行ActionPerformed(evt);
            }
        });
        jPanel47.add(删除拍卖行, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 550, -1, 30));

        jTabbedPane5.addTab("金币拍卖行", jPanel47);

        角色背包.add(jTabbedPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 620));

        jTabbedPane8.addTab("角色道具信息", 角色背包);

        技能.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "角色技能", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        技能.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        技能信息.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        技能信息.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "技能名字", "技能代码", "目前等级", "最高等级"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        技能信息.getTableHeader().setReorderingAllowed(false);
        jScrollPane14.setViewportView(技能信息);

        技能.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 870, 430));

        技能代码.setEditable(false);
        技能代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        技能.add(技能代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 490, 120, 30));

        技能目前等级.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        技能.add(技能目前等级, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 490, 120, 30));

        技能最高等级.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        技能.add(技能最高等级, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 490, 120, 30));

        技能名字.setEditable(false);
        技能名字.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        技能名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                技能名字ActionPerformed(evt);
            }
        });
        技能.add(技能名字, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 490, 120, 30));

        jLabel86.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel86.setText("技能代码；");
        技能.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 470, -1, -1));

        jLabel89.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel89.setText("目前等级；");
        技能.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 470, -1, -1));

        jLabel107.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel107.setText("最高等级；");
        技能.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 470, -1, -1));

        修改技能.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        修改技能.setText("修改");
        修改技能.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改技能ActionPerformed(evt);
            }
        });
        技能.add(修改技能, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 530, 120, 40));

        技能序号.setEditable(false);
        技能序号.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        技能.add(技能序号, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 490, 80, 30));

        jLabel188.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel188.setText("技能名字；");
        技能.add(jLabel188, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 470, -1, -1));

        jLabel204.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel204.setText("序号；");
        技能.add(jLabel204, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 470, -1, -1));

        jLabel205.setFont(new java.awt.Font("幼圆", 0, 24)); // NOI18N
        jLabel205.setText("提示;技能无法超出正常范围值。");
        技能.add(jLabel205, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 580, 360, 30));

        删除技能.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除技能.setText("删除");
        删除技能.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除技能ActionPerformed(evt);
            }
        });
        技能.add(删除技能, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 530, 120, 40));

        修改技能1.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        修改技能1.setText("刷新");
        修改技能1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改技能1ActionPerformed(evt);
            }
        });
        技能.add(修改技能1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 530, 120, 40));

        jTabbedPane8.addTab("角色技能信息", 技能);

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addComponent(jTabbedPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addComponent(jTabbedPane8)
                .addContainerGap())
        );

        jTabbedPane4.addTab("玩家信息", jPanel38);

        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "怪物爆物/(10000=1%)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        怪物爆物.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        怪物爆物.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序列号", "怪物代码", "物品代码", "爆率", "物品名字", "物品图片"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(怪物爆物);

        jPanel27.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 610, 380));

        怪物爆物序列号.setEditable(false);
        怪物爆物序列号.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel27.add(怪物爆物序列号, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 40, 30));

        怪物爆物怪物代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel27.add(怪物爆物怪物代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, 60, 30));

        怪物爆物物品代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel27.add(怪物爆物物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 440, 60, 30));

        怪物爆物爆率.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel27.add(怪物爆物爆率, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 440, 70, 30));

        怪物爆物物品名称.setEditable(false);
        怪物爆物物品名称.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel27.add(怪物爆物物品名称, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 440, 90, 30));

        删除怪物爆物.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        删除怪物爆物.setText("删除");
        删除怪物爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除怪物爆物ActionPerformed(evt);
            }
        });
        jPanel27.add(删除怪物爆物, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 480, 70, 30));

        添加怪物爆物.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        添加怪物爆物.setText("添加");
        添加怪物爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                添加怪物爆物ActionPerformed(evt);
            }
        });
        jPanel27.add(添加怪物爆物, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 480, 70, 30));

        jLabel120.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel120.setText("怪物代码；");
        jPanel27.add(jLabel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, -1, -1));

        jLabel211.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel211.setText("物品代码；");
        jPanel27.add(jLabel211, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, -1, 20));

        jLabel212.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel212.setText("爆率；");
        jPanel27.add(jLabel212, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, -1, -1));

        jLabel213.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel213.setText("序列号；");
        jPanel27.add(jLabel213, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, -1, -1));

        修改怪物爆物.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        修改怪物爆物.setText("修改");
        修改怪物爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改怪物爆物ActionPerformed(evt);
            }
        });
        jPanel27.add(修改怪物爆物, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 480, 70, 30));

        刷新怪物爆物.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        刷新怪物爆物.setText("刷新怪物爆物");
        刷新怪物爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新怪物爆物ActionPerformed(evt);
            }
        });
        jPanel27.add(刷新怪物爆物, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 140, 30));

        jLabel39.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel39.setText("物品名；");
        jPanel27.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 420, -1, -1));

        jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "全局爆物/(10000=1%)", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        世界爆物.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        世界爆物.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序列号", "物品代码", "爆率", "物品名"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        世界爆物.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(世界爆物);

        jPanel28.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 390, 380));

        世界爆物序列号.setEditable(false);
        世界爆物序列号.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        世界爆物序列号.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                世界爆物序列号ActionPerformed(evt);
            }
        });
        jPanel28.add(世界爆物序列号, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 50, 30));

        世界爆物物品代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        世界爆物物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                世界爆物物品代码ActionPerformed(evt);
            }
        });
        jPanel28.add(世界爆物物品代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 440, 70, 30));

        世界爆物爆率.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        世界爆物爆率.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                世界爆物爆率ActionPerformed(evt);
            }
        });
        jPanel28.add(世界爆物爆率, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 440, 50, 30));

        添加世界爆物.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        添加世界爆物.setText("添加");
        添加世界爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                添加世界爆物ActionPerformed(evt);
            }
        });
        jPanel28.add(添加世界爆物, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 480, 70, 30));

        删除世界爆物.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        删除世界爆物.setText("删除");
        删除世界爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除世界爆物ActionPerformed(evt);
            }
        });
        jPanel28.add(删除世界爆物, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 480, 70, 30));

        jLabel210.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel210.setText("序列号；");
        jPanel28.add(jLabel210, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, -1, -1));

        jLabel202.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel202.setText("物品代码；");
        jPanel28.add(jLabel202, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, -1, 20));

        jLabel209.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel209.setText("爆率；");
        jPanel28.add(jLabel209, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 420, -1, -1));

        世界爆物名称.setEditable(false);
        世界爆物名称.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        世界爆物名称.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                世界爆物名称ActionPerformed(evt);
            }
        });
        jPanel28.add(世界爆物名称, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 440, 100, 30));

        jLabel40.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel40.setText("物品名；");
        jPanel28.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 420, -1, -1));

        修改世界爆物.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        修改世界爆物.setText("修改");
        修改世界爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改世界爆物ActionPerformed(evt);
            }
        });
        jPanel28.add(修改世界爆物, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 480, 70, 30));

        刷新世界爆物.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        刷新世界爆物.setText("刷新世界爆物");
        刷新世界爆物.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新世界爆物ActionPerformed(evt);
            }
        });
        jPanel28.add(刷新世界爆物, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 140, 30));

        查询物品掉落.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        查询物品掉落.setText("查询物品掉落");
        查询物品掉落.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查询物品掉落ActionPerformed(evt);
            }
        });

        查询物品掉落代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        查询物品掉落代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查询物品掉落代码ActionPerformed(evt);
            }
        });

        查询怪物掉落代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        删除指定的掉落按键.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除指定的掉落按键.setText("删除指定掉落");
        删除指定的掉落按键.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除指定的掉落按键ActionPerformed(evt);
            }
        });

        删除指定的掉落.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        修改物品掉落持续时间.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        修改物品掉落持续时间.setText("修改确认");
        修改物品掉落持续时间.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改物品掉落持续时间ActionPerformed(evt);
            }
        });

        物品掉落持续时间.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        jLabel320.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel320.setForeground(new java.awt.Color(255, 0, 0));
        jLabel320.setText("需要重启生效，建议 10000 ");

        刷新怪物卡片.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        刷新怪物卡片.setText("刷新卡片");
        刷新怪物卡片.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新怪物卡片ActionPerformed(evt);
            }
        });

        地图物品上限.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        修改物品掉落持续时间1.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        修改物品掉落持续时间1.setText("修改确认");
        修改物品掉落持续时间1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改物品掉落持续时间1ActionPerformed(evt);
            }
        });

        jLabel319.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel319.setText("地图物品最多数量");

        jLabel324.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel324.setText("物品掉落持续时间");

        jLabel325.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel325.setText("地图刷新频率");

        地图刷新频率.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N

        修改物品掉落持续时间2.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        修改物品掉落持续时间2.setText("修改确认");
        修改物品掉落持续时间2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改物品掉落持续时间2ActionPerformed(evt);
            }
        });

        jLabel323.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel323.setText("指定物品查询掉落");

        jLabel316.setText("指定怪物查物品掉落");

        查询怪物掉落.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        查询怪物掉落.setText("查询怪物掉落");
        查询怪物掉落.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查询怪物掉落ActionPerformed(evt);
            }
        });

        jLabel321.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel321.setText("删除指定物品掉落");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel26Layout.createSequentialGroup()
                                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel26Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(查询物品掉落代码, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel323))
                                        .addGap(27, 27, 27)
                                        .addComponent(jLabel316))
                                    .addGroup(jPanel26Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(查询物品掉落)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(查询怪物掉落, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(查询怪物掉落代码, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(删除指定的掉落按键)
                                        .addGap(22, 22, 22)
                                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel26Layout.createSequentialGroup()
                                                .addComponent(修改物品掉落持续时间)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(修改物品掉落持续时间1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(14, 14, 14)
                                                .addComponent(修改物品掉落持续时间2))
                                            .addGroup(jPanel26Layout.createSequentialGroup()
                                                .addComponent(物品掉落持续时间, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(地图物品上限, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(地图刷新频率, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel26Layout.createSequentialGroup()
                                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel26Layout.createSequentialGroup()
                                                .addGap(157, 157, 157)
                                                .addComponent(jLabel324)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel319))
                                            .addGroup(jPanel26Layout.createSequentialGroup()
                                                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(jPanel26Layout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(删除指定的掉落, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel26Layout.createSequentialGroup()
                                                        .addGap(12, 12, 12)
                                                        .addComponent(jLabel321)))
                                                .addGap(284, 284, 284)
                                                .addComponent(jLabel325)))
                                        .addGap(5, 5, 5)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(刷新怪物卡片, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel26Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel320)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel316)
                            .addComponent(jLabel323)
                            .addComponent(jLabel321)
                            .addComponent(jLabel324)
                            .addComponent(jLabel319)
                            .addComponent(jLabel325)))
                    .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(查询物品掉落代码, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(查询怪物掉落代码, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(物品掉落持续时间, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(地图物品上限, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(删除指定的掉落, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(地图刷新频率, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(刷新怪物卡片, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(查询物品掉落, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(查询怪物掉落)
                    .addComponent(删除指定的掉落按键)
                    .addComponent(修改物品掉落持续时间, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(修改物品掉落持续时间1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(修改物品掉落持续时间2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(90, 90, 90)
                .addComponent(jLabel320)
                .addGap(69, 69, 69))
        );

        jTabbedPane4.addTab("怪物爆率", jPanel26);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("反应堆/箱子爆率管理"));

        反应堆.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        反应堆.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序列号", "反应堆", "物品代码", "概率", "物品名字"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        反应堆.getTableHeader().setReorderingAllowed(false);
        jScrollPane26.setViewportView(反应堆);

        删除反应堆物品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除反应堆物品.setText("删除");
        删除反应堆物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除反应堆物品ActionPerformed(evt);
            }
        });

        jLabel281.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel281.setText("提示；修改完成后请右侧应用重载后即可生效。");

        jPanel61.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "反应堆编辑", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 18))); // NOI18N
        jPanel61.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton26.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jButton26.setText("刷新列表信息");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        jPanel61.add(jButton26, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, 320, 60));

        反应堆序列号.setEditable(false);
        反应堆序列号.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel61.add(反应堆序列号, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 70, 30));

        反应堆代码.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel61.add(反应堆代码, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 90, 30));

        反应堆物品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel61.add(反应堆物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 90, 30));

        反应堆概率.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        反应堆概率.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                反应堆概率ActionPerformed(evt);
            }
        });
        jPanel61.add(反应堆概率, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, 90, 30));

        新增反应堆物品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        新增反应堆物品.setText("新增");
        新增反应堆物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                新增反应堆物品ActionPerformed(evt);
            }
        });
        jPanel61.add(新增反应堆物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, 70, 30));

        删除反应堆物品1.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除反应堆物品1.setText("删除");
        删除反应堆物品1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除反应堆物品1ActionPerformed(evt);
            }
        });
        jPanel61.add(删除反应堆物品1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 70, 70, 30));

        查找反应堆掉落.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel61.add(查找反应堆掉落, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 90, 30));

        jButton36.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jButton36.setText("查找");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });
        jPanel61.add(jButton36, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 150, -1, 30));

        查找物品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jPanel61.add(查找物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 90, 30));

        jButton37.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jButton37.setText("查找");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });
        jPanel61.add(jButton37, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, -1, 30));

        jLabel274.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel274.setText("掉落概率；");
        jPanel61.add(jLabel274, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, -1, 20));

        jLabel275.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel275.setText("序号；");
        jPanel61.add(jLabel275, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, 20));

        jLabel277.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel277.setText("物品代码；");
        jPanel61.add(jLabel277, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, 20));

        jLabel278.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel278.setText("反应堆；");
        jPanel61.add(jLabel278, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, -1, 20));

        jLabel279.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel279.setText("反应堆；");
        jPanel61.add(jLabel279, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, -1, 20));

        jLabel280.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel280.setText("物品代码；");
        jPanel61.add(jLabel280, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, 20));

        修改反应堆物品.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        修改反应堆物品.setText("修改");
        修改反应堆物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                修改反应堆物品ActionPerformed(evt);
            }
        });
        jPanel61.add(修改反应堆物品, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 70, 70, 30));

        jLabel282.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel282.setText("删除指定反应堆的所有物品；");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane26)
                        .addComponent(jPanel61, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addGap(275, 275, 275)
                            .addComponent(jLabel281, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addGap(336, 336, 336)
                            .addComponent(删除反应堆物品代码, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(删除反应堆物品, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(308, 308, 308)
                        .addComponent(jLabel282, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel61, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel281, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel282, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(删除反应堆物品代码, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(删除反应堆物品, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        jTabbedPane4.addTab("箱子爆率", jPanel13);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("批量删除游戏物品"));

        游戏道具.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        游戏道具.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "角色ID", "角色名字", "道具ID", "道具名字"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        游戏道具.setColumnSelectionAllowed(true);
        jScrollPane27.setViewportView(游戏道具);

        jLabel338.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel338.setForeground(new java.awt.Color(255, 51, 51));
        jLabel338.setText("需离线操作。");

        jPanel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        查找道具.setText("查找道具");
        查找道具.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查找道具ActionPerformed(evt);
            }
        });
        jPanel23.add(查找道具, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 140, 30));

        删除道具.setText("删除道具");
        删除道具.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除道具ActionPerformed(evt);
            }
        });
        jPanel23.add(删除道具, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 140, 30));

        jLabel337.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel337.setText("角色背包，角色穿戴");
        jPanel23.add(jLabel337, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jPanel31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        查找道具1.setText("查找道具");
        查找道具1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查找道具1ActionPerformed(evt);
            }
        });
        jPanel31.add(查找道具1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 140, 30));

        删除道具1.setText("删除道具");
        删除道具1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除道具1ActionPerformed(evt);
            }
        });
        jPanel31.add(删除道具1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 140, 30));

        jLabel339.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel339.setText("点券拍卖行");
        jPanel31.add(jLabel339, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jPanel62.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel62.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        查找道具2.setText("查找道具");
        查找道具2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查找道具2ActionPerformed(evt);
            }
        });
        jPanel62.add(查找道具2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 140, 30));

        删除道具2.setText("删除道具");
        删除道具2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除道具2ActionPerformed(evt);
            }
        });
        jPanel62.add(删除道具2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 140, 30));

        jLabel340.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel340.setText("金币拍卖行");
        jPanel62.add(jLabel340, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jPanel63.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel63.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        查找道具3.setText("查找道具");
        查找道具3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查找道具3ActionPerformed(evt);
            }
        });
        jPanel63.add(查找道具3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 140, 30));

        删除道具3.setText("删除道具");
        删除道具3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除道具3ActionPerformed(evt);
            }
        });
        jPanel63.add(删除道具3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 140, 30));

        jLabel341.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel341.setText("家族游戏仓库");
        jPanel63.add(jLabel341, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jPanel64.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel64.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        查找道具4.setText("查找道具");
        查找道具4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查找道具4ActionPerformed(evt);
            }
        });
        jPanel64.add(查找道具4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 140, 30));

        删除道具4.setText("删除道具");
        删除道具4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除道具4ActionPerformed(evt);
            }
        });
        jPanel64.add(删除道具4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 140, 30));

        jLabel342.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel342.setText("个人游戏仓库");
        jPanel64.add(jLabel342, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jLabel343.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel343.setText("游戏道具代码；");

        jLabel344.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel344.setText("该功能可查询游戏内指定区域的游戏道具，可以查看");

        jLabel345.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel345.setText("该道具有多少玩家拥有。可以一键删除所有该道具。");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(jLabel345, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane27)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(160, 160, 160)
                                .addComponent(jLabel344, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel343)
                    .addComponent(游戏道具代码, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel62, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel64, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel63, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(278, 278, 278)
                .addComponent(jLabel338)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel344, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel345, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel338, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel343, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(游戏道具代码, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel62, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel64, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel63, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("批量删除", jPanel14);

        家族信息.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族信息.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "家族ID", "家族名称", "族长/角色ID", "成员2", "成员3", "成员4", "成员5", "家族GP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        家族信息.getTableHeader().setReorderingAllowed(false);
        jScrollPane24.setViewportView(家族信息);

        刷新家族信息.setText("刷新家族信息");
        刷新家族信息.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新家族信息ActionPerformed(evt);
            }
        });

        jLabel194.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel194.setText("家族ID；");

        家族ID.setEditable(false);
        家族ID.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族IDActionPerformed(evt);
            }
        });

        家族名称.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族名称.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族名称ActionPerformed(evt);
            }
        });

        jLabel195.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel195.setText("家族名称；");

        家族族长.setEditable(false);
        家族族长.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族族长.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族族长ActionPerformed(evt);
            }
        });

        jLabel196.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel196.setText("家族族长；");

        jLabel198.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel198.setText("家族成员2；");

        家族成员2.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族成员2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族成员2ActionPerformed(evt);
            }
        });

        jLabel199.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel199.setText("家族成员3；");

        家族成员3.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族成员3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族成员3ActionPerformed(evt);
            }
        });

        jLabel200.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel200.setText("家族成员4；");

        家族成员4.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族成员4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族成员4ActionPerformed(evt);
            }
        });

        jLabel313.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel313.setText("家族成员5；");

        家族成员5.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族成员5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族成员5ActionPerformed(evt);
            }
        });

        jLabel314.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel314.setText("家族GP；");

        家族GP.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        家族GP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                家族GPActionPerformed(evt);
            }
        });

        jButton20.setText("更改家族GP点数");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane24))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(刷新家族信息, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(280, 280, 280))
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel194, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(家族ID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(家族名称, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(家族族长, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(家族成员2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(家族成员3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(家族成员4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel195)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel196)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel198)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel199)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel200)))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(家族成员5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(家族GP, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel313)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel314)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane24, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel194)
                        .addComponent(jLabel195)
                        .addComponent(jLabel196)
                        .addComponent(jLabel198)
                        .addComponent(jLabel199)
                        .addComponent(jLabel200)
                        .addComponent(jLabel313)
                        .addComponent(jLabel314))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(家族ID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(家族名称, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(家族族长, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(家族成员2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(家族成员3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(家族成员4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(家族成员5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(家族GP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(22, 22, 22)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(刷新家族信息, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jTabbedPane4.addTab("游戏家族", jPanel29);

        jPanel65.setBorder(javax.swing.BorderFactory.createTitledBorder("MAC/IP封禁"));

        封IP.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        封IP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序列号", "IP地址"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane107.setViewportView(封IP);

        封MAC.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        封MAC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序列号", "MAC地址"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane108.setViewportView(封MAC);

        刷新封IP.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        刷新封IP.setText("刷新");
        刷新封IP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新封IPActionPerformed(evt);
            }
        });

        刷新封MAC.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        刷新封MAC.setText("刷新");
        刷新封MAC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                刷新封MACActionPerformed(evt);
            }
        });

        删除MAC.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除MAC.setText("删除");
        删除MAC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除MACActionPerformed(evt);
            }
        });

        删除IP.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        删除IP.setText("删除");
        删除IP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                删除IPActionPerformed(evt);
            }
        });

        jLabel346.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel346.setText("序号；");

        jLabel347.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel347.setText("序号；");

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel347)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(删MAC代码, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(删除MAC, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(刷新封MAC, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(jLabel346)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(删除IP代码, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(删除IP, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(刷新封IP, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160))
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addComponent(jScrollPane108, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane107, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane108, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                    .addComponent(jScrollPane107))
                .addGap(20, 20, 20)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(删MAC代码, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel347, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(删除MAC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(刷新封MAC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(删除IP代码, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel346, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(删除IP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(刷新封IP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("MAC封禁", jPanel65);

        jPanel66.setBorder(javax.swing.BorderFactory.createTitledBorder("活动经验"));

        jPanel69.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "2倍率活动", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel69.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        开启双倍经验.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        开启双倍经验.setText("开启双倍经验");
        开启双倍经验.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启双倍经验ActionPerformed(evt);
            }
        });
        jPanel69.add(开启双倍经验, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 71, 140, 40));
        jPanel69.add(双倍经验持续时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 120, -1));

        jLabel359.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel359.setText("持续时间/h；");
        jPanel69.add(jLabel359, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, 20));

        开启双倍爆率.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        开启双倍爆率.setText("开启双倍爆率");
        开启双倍爆率.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启双倍爆率ActionPerformed(evt);
            }
        });
        jPanel69.add(开启双倍爆率, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 151, 140, 40));
        jPanel69.add(双倍爆率持续时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 120, -1));

        jLabel360.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel360.setText("持续时间/h；");
        jPanel69.add(jLabel360, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, 20));

        开启双倍金币.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        开启双倍金币.setText("开启双倍金币");
        开启双倍金币.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启双倍金币ActionPerformed(evt);
            }
        });
        jPanel69.add(开启双倍金币, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 231, 140, 40));
        jPanel69.add(双倍金币持续时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 120, -1));

        jLabel361.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel361.setText("持续时间/h；");
        jPanel69.add(jLabel361, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, 20));

        jPanel70.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "3倍率活动", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("幼圆", 0, 24))); // NOI18N
        jPanel70.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        开启三倍经验.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        开启三倍经验.setText("开启三倍经验");
        开启三倍经验.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启三倍经验ActionPerformed(evt);
            }
        });
        jPanel70.add(开启三倍经验, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 71, 140, 40));
        jPanel70.add(三倍经验持续时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 120, -1));

        jLabel362.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel362.setText("持续时间/h；");
        jPanel70.add(jLabel362, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, 20));

        开启三倍爆率.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        开启三倍爆率.setText("开启三倍爆率");
        开启三倍爆率.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启三倍爆率ActionPerformed(evt);
            }
        });
        jPanel70.add(开启三倍爆率, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 151, 140, 40));
        jPanel70.add(三倍爆率持续时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 120, -1));

        jLabel348.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel348.setText("持续时间/h；");
        jPanel70.add(jLabel348, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, 20));

        开启三倍金币.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        开启三倍金币.setText("开启三倍金币");
        开启三倍金币.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启三倍金币ActionPerformed(evt);
            }
        });
        jPanel70.add(开启三倍金币, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 231, 140, 40));
        jPanel70.add(三倍金币持续时间, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 120, -1));

        jLabel349.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel349.setText("持续时间/h；");
        jPanel70.add(jLabel349, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, 20));

        jLabel1.setFont(new java.awt.Font("宋体", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("功能说明：本功能无需重启服务端立即生效");

        jLabel3.setFont(new java.awt.Font("宋体", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 51));
        jLabel3.setText("单位换算 h=小时 时间到期自动解除倍率");

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addComponent(jPanel69, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel66Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel3))
                    .addGroup(jPanel66Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel70, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                    .addComponent(jPanel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("活动经验", jPanel66);

        jPanel53.setBorder(javax.swing.BorderFactory.createTitledBorder("转存数据"));

        jButton69.setText("更新物品道具");
        jButton69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton69ActionPerformed(evt);
            }
        });

        jButton70.setText("导出爆物数据");
        jButton70.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton70ActionPerformed(evt);
            }
        });

        jButton72.setText("更新NPC名称");
        jButton72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton72ActionPerformed(evt);
            }
        });

        jButton73.setText("更新怪物技能");
        jButton73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton73ActionPerformed(evt);
            }
        });

        jButton74.setText("更新问答数据");
        jButton74.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton74ActionPerformed(evt);
            }
        });

        jButton75.setText("更新任务数据");
        jButton75.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton75ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addComponent(jButton73, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addComponent(jButton70, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel53Layout.createSequentialGroup()
                        .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton74, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton72, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton69, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton74, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton75, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton70, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton73, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel54.setBorder(javax.swing.BorderFactory.createTitledBorder("其他工具"));

        jButton31.setText("代码查询器");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jButton22.setText("基址计算工具");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setText("包头转换工具");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton28.setText("角色转移工具");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jButton39.setText("自添加NPC删除工具");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        jButton44.setText("游戏抽奖管理工具");
        jButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton44ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel54Layout.createSequentialGroup()
                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(152, 152, 152)
                        .addComponent(jButton23, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
                    .addGroup(jPanel54Layout.createSequentialGroup()
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel54Layout.createSequentialGroup()
                                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton39))
                            .addGroup(jPanel54Layout.createSequentialGroup()
                                .addComponent(jButton28)
                                .addGap(18, 18, 18)
                                .addComponent(jButton44)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel54Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        jPanel67.setBorder(javax.swing.BorderFactory.createTitledBorder("其他控制台"));

        jButton25.setText("商城CDK卡密控制台");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton32.setText("任务管理控制台");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jButton33.setText("锻造控制台");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        jButton34.setText("游戏副本控制台");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        jButton40.setText("野外BOSS刷新时间");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        jButton41.setText("药水冷却时间控制台");
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        jButton42.setText("金锤子成功率控制台");
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });

        jButton43.setText("永恒重生装备控制台");
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });

        jButton29.setForeground(new java.awt.Color(255, 51, 51));
        jButton29.setText("一键清空数据库");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton25, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton41)
                    .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton42)
                    .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton43, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel67Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton43, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addComponent(jPanel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("更多功能", jPanel52);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("关于我们"));

        测试显示图片.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "序列号", "怪物ID", "物品名称", "物品图片"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(测试显示图片);

        测试刷新图片按钮.setText("刷新图片");
        测试刷新图片按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                测试刷新图片按钮ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 789, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 573, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(测试刷新图片按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                .addGap(52, 52, 52)
                .addComponent(测试刷新图片按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(270, 270, 270))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("关于我们", jPanel6);

        jLabel7.setText("3-14今天也要努力哦！");

        RunTime.setText("运行时长：0:0:0:0");
        RunTime.setMaximumSize(new java.awt.Dimension(150, 15));
        RunTime.setMinimumSize(new java.awt.Dimension(150, 15));
        RunTime.setName(""); // NOI18N
        RunTime.setPreferredSize(new java.awt.Dimension(200, 15));

        ActiveThread.setForeground(new java.awt.Color(255, 51, 51));
        ActiveThread.setText("游戏线程:0");

        RunStats.setText("运行状态:已关闭");
        RunStats.setMaximumSize(new java.awt.Dimension(110, 15));
        RunStats.setMinimumSize(new java.awt.Dimension(110, 15));

        javax.swing.GroupLayout jPanel68Layout = new javax.swing.GroupLayout(jPanel68);
        jPanel68.setLayout(jPanel68Layout);
        jPanel68Layout.setHorizontalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ActiveThread)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(RunStats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(RunTime, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 731, Short.MAX_VALUE))
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        jPanel68Layout.setVerticalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RunTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(ActiveThread)
                        .addComponent(RunStats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        private void setRunStats(boolean on) {
        jButton1.setEnabled(!on);
     //   ShutdownGameServer.setEnabled(on);
        RunStats.setText("运行状态：" + (on ? "正在运行" : "已关闭"));
    }
    
    private void startRunTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                startRunTime = server.Timer.GuiTimer.getInstance().register(new Runnable() {
                    @Override
                    public void run() {
                        RunTime.setText(formatDuring(System.currentTimeMillis() - starttime));
                    }
                }, 1000);
            }
        }).start();
    }
    
    public static final String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return "运行时长:" + days + "天" + (hours / 10 == 0 ? "0" : "") + hours + "时" + (minutes / 10 == 0 ? "0" : "") + minutes + "分"
                + (seconds / 10 == 0 ? "0" : "") + seconds + "秒";
    }

    private void 重载商城按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载商城按钮ActionPerformed
        // TODO add your handling code here:
        CashItemFactory.getInstance().clearCashShop();
        System.out.println("[重载系统] 商城重载成功。");
        JOptionPane.showMessageDialog(null, "商城重载成功。");
    }//GEN-LAST:event_重载商城按钮ActionPerformed

    private void 重载包头按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载包头按钮ActionPerformed
        // TODO add your handling code here:
        //SendPacketOpcode.reloadValues();
        //RecvPacketOpcode.reloadValues();
        System.out.println("[重载系统] 包头重载成功。");
        JOptionPane.showMessageDialog(null, "包头重载成功。");
    }//GEN-LAST:event_重载包头按钮ActionPerformed

    private void 重载商店按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载商店按钮ActionPerformed
        // TODO add your handling code here:
        MapleShopFactory.getInstance().clear();
        System.out.println("[重载系统] 商店重载成功。");
        JOptionPane.showMessageDialog(null, "商店重载成功。");
    }//GEN-LAST:event_重载商店按钮ActionPerformed

    private void 重载任务ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载任务ActionPerformed
        // TODO add your handling code here:
        MapleQuest.clearQuests();
        System.out.println("[重载系统] 任务重载成功。");
        JOptionPane.showMessageDialog(null, "任务重载成功。");
    }//GEN-LAST:event_重载任务ActionPerformed

    private void 重载传送门按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载传送门按钮ActionPerformed
        // TODO add your handling code here:
        PortalScriptManager.getInstance().clearScripts();
        System.out.println("[重载系统] 传送门重载成功。");
        JOptionPane.showMessageDialog(null, "传送门重载成功。");
    }//GEN-LAST:event_重载传送门按钮ActionPerformed

    private void 重载反应堆按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载反应堆按钮ActionPerformed
        // TODO add your handling code here:
        ReactorScriptManager.getInstance().clearDrops();
        System.out.println("[重载系统] 反应堆重载成功。");
        JOptionPane.showMessageDialog(null, "反应堆重载成功。");
    }//GEN-LAST:event_重载反应堆按钮ActionPerformed

    private void 重载爆率按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载爆率按钮ActionPerformed
        // TODO add your handling code here:
        MapleMonsterInformationProvider.getInstance().clearDrops();
        System.out.println("[重载系统] 爆率重载成功。");
        JOptionPane.showMessageDialog(null, "爆率重载成功。");
    }//GEN-LAST:event_重载爆率按钮ActionPerformed

    private void 重载副本按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载副本按钮ActionPerformed
        // TODO add your handling code here:
        for (ChannelServer instance1 : ChannelServer.getAllInstances()) {
            if (instance1 != null) {
                instance1.reloadEvents();
            }
        }
        System.out.println("[重载系统] 副本重载成功。");
        JOptionPane.showMessageDialog(null, "副本重载成功。");
    }//GEN-LAST:event_重载副本按钮ActionPerformed

    private void 保存雇佣按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_保存雇佣按钮ActionPerformed
        // TODO add your handling code here:
        int p = 0;
        for (handling.channel.ChannelServer cserv : handling.channel.ChannelServer.getAllInstances()) {
            p++;
            cserv.closeAllMerchants();
        }
        System.out.println("[保存雇佣商人系统] 雇佣商人保存" + p + "个频道成功。");
        JOptionPane.showMessageDialog(null, "雇佣商人保存" + p + "个频道成功。");
    }//GEN-LAST:event_保存雇佣按钮ActionPerformed

    private void 保存数据按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_保存数据按钮ActionPerformed
        // TODO add your handling code here:
        int p = 0;
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                p++;
                chr.saveToDB(true, true);
            }
        }
        System.out.println("[保存数据系统] 保存" + p + "个成功。");
        JOptionPane.showMessageDialog(null, "保存数据成功。");
    }//GEN-LAST:event_保存数据按钮ActionPerformed

    private void 查询在线玩家人数按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查询在线玩家人数按钮ActionPerformed
        int p = 0;
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr != null) {
                    ++p;
                }
            }
        }
        JOptionPane.showMessageDialog(this, "当前在线人数：" + p + "人");
    }//GEN-LAST:event_查询在线玩家人数按钮ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        setRunStats(true);
        if (开启服务端 == false) {
            开启服务端 = true;

        }
        new Thread(new Runnable() {
            public void run() {
                Start.是否控制台启动 = true;
                Start.start(null);
                JOptionPane.showMessageDialog(null, "服务器启动完成。");
                jButton1.setText("服务端运行中");
            }
        }).start();
        // 开始服务端启动计时
        startRunTime();
        // 开始统计线程
        updateThreadNum();
    }//GEN-LAST:event_jButton1ActionPerformed
    public void 删除SN库存() {
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        try {
            ps2 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM character7 WHERE Name = ?");
            ps2.setInt(1, Integer.parseInt(this.商品编码.getText()));
            rs2 = ps2.executeQuery();
            if (rs2.next()) {
                String sqlstr = " delete from character7 where Name =" + Integer.parseInt(this.商品编码.getText()) + ";";
                ps2.executeUpdate(sqlstr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void 删除SN库存2() {
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        try {
            ps2 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM character7 WHERE Name = ?  &&  channel = 2");
            ps2.setInt(1, Integer.parseInt(this.商品编码.getText()));
            rs2 = ps2.executeQuery();
            if (rs2.next()) {
                String sqlstr = " delete from character7 where Name =" + Integer.parseInt(this.商品编码.getText()) + ";";
                ps2.executeUpdate(sqlstr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void 删除SN库存3() {
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        try {
            ps2 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM character7 WHERE Name = ?  &&  channel = 3");
            ps2.setInt(1, Integer.parseInt(this.商品编码.getText()));
            rs2 = ps2.executeQuery();
            if (rs2.next()) {
                String sqlstr = " delete from character7 where Name =" + Integer.parseInt(this.商品编码.getText()) + ";";
                ps2.executeUpdate(sqlstr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void 删除SN库存4() {
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        try {
            ps2 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM character7 WHERE Name = ?  &&  channel = 4");
            ps2.setInt(1, Integer.parseInt(this.商品编码.getText()));
            rs2 = ps2.executeQuery();
            if (rs2.next()) {
                String sqlstr = " delete from character7 where Name =" + Integer.parseInt(this.商品编码.getText()) + ";";
                ps2.executeUpdate(sqlstr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Gaincharacter7(String Name, int Channale, int Piot) {
        try {
            int ret = Getcharacter7(Name, Channale);
            if (ret == -1) {
                ret = 0;
                PreparedStatement ps = null;
                try {
                    ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO character7 (channel, Name,Point) VALUES (?, ?, ?)");
                    ps.setInt(1, Channale);
                    ps.setString(2, Name);
                    ps.setInt(3, ret);
                    ps.execute();
                } catch (SQLException e) {
                    System.out.println("xxxxxxxx:" + e);
                } finally {
                    try {
                        if (ps != null) {
                            ps.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("xxxxxxxxzzzzzzz:" + e);
                    }
                }
            }
            ret += Piot;
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE character7 SET `Point` = ? WHERE Name = ? and channel = ?");
            ps.setInt(1, ret);
            ps.setString(2, Name);
            ps.setInt(3, Channale);
            ps.execute();
            ps.close();
        } catch (SQLException sql) {
            System.err.println("Getcharacter7!!55" + sql);
        }
    }

    public static int Getcharacter7(String Name, int Channale) {
        int ret = -1;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM character7 WHERE channel = ? and Name = ?");
            ps.setInt(1, Channale);
            ps.setString(2, Name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            ret = rs.getInt("Point");
            rs.close();
            ps.close();
        } catch (SQLException ex) {
        }
        return ret;
    }

    public static int Get商城物品() {
        int ret = -1;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM cashshop_modified_items WHERE serial = ?");
            int serial = 0;
            ps.setInt(1, serial);
            ResultSet rs = ps.executeQuery();
            rs.next();
            ret = rs.getInt("meso");
            rs.close();
            ps.close();
        } catch (SQLException ex) {
        }
        return ret;
    }

    public static void Gain商城物品(int Piot, int Piot1) {
        try {
            int ret = Get商城物品();
            if (ret == -1) {
                ret = 0;
                PreparedStatement ps = null;
                try {
                    ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO cashshop_modified_items (serial,meso) VALUES (?, ?)");
                    int serial = 0;
                    ps.setInt(1, serial);
                    ps.setInt(2, ret);
                    ps.execute();
                } catch (SQLException e) {
                    System.out.println("xxxxxxxx:" + e);
                } finally {
                    try {
                        if (ps != null) {
                            ps.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("xxxxxxxxzzzzzzz:" + e);
                    }
                }
            }
            ret += Piot;
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE cashshop_modified_items SET `meso` = ? WHERE serial = ?");
            ps.setInt(1, ret);
            int serial = 0;
            ps.setInt(2, serial);
            ps.execute();
            ps.close();
        } catch (SQLException sql) {
            System.err.println("獲取錯誤!!55" + sql);
        }
    }
    private void 删除怪物爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除怪物爆物ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;

        boolean result = this.怪物爆物序列号.getText().matches("[0-9]+");
        if (result == true) {
            int 商城SN编码 = Integer.parseInt(this.怪物爆物序列号.getText());

            try {
                //清楚table数据
                for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
                    ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
                }
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM drop_data WHERE id = ?");
                ps1.setInt(1, 商城SN编码);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from drop_data where id =" + 商城SN编码 + "";
                    ps1.executeUpdate(sqlstr);
                    JOptionPane.showMessageDialog(null, "[信息]:删除爆物成功。");
                    刷新指定怪物爆物();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_删除怪物爆物ActionPerformed

    private void 添加怪物爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_添加怪物爆物ActionPerformed
        boolean result1 = this.怪物爆物怪物代码.getText().matches("[0-9]+");
        boolean result2 = this.怪物爆物物品代码.getText().matches("[0-9]+");
        boolean result3 = this.怪物爆物爆率.getText().matches("[0-9]+");
        if (result1 && result2 && result3) {
            if (Integer.parseInt(this.怪物爆物怪物代码.getText()) < 0 && Integer.parseInt(this.怪物爆物物品代码.getText()) < 0 && Integer.parseInt(this.怪物爆物爆率.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "[信息]:请填写正确的值。");
                return;
            }
            try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO drop_data ( dropperid,itemid,minimum_quantity,maximum_quantity,chance) VALUES ( ?, ?, ?, ?, ?)")) {
                ps.setInt(1, Integer.parseInt(this.怪物爆物怪物代码.getText()));
                ps.setInt(2, Integer.parseInt(this.怪物爆物物品代码.getText()));
                ps.setInt(3, 1);
                ps.setInt(4, 1);
                ps.setInt(5, Integer.parseInt(this.怪物爆物爆率.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "[信息]:添加成功。");
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入<怪物代码><物品代码><物品爆率>的格式来添加。");
        }
    }//GEN-LAST:event_添加怪物爆物ActionPerformed

    private void 修改怪物爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改怪物爆物ActionPerformed
        boolean result1 = this.怪物爆物怪物代码.getText().matches("[0-9]+");
        boolean result2 = this.怪物爆物物品代码.getText().matches("[0-9]+");
        boolean result3 = this.怪物爆物爆率.getText().matches("[0-9]+");
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        if (result1 && result2 && result3) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE drop_data SET dropperid = ?, itemid = ?, chance = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM drop_data WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.怪物爆物序列号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    String sqlString3 = null;
                    String sqlString4 = null;
                    sqlString2 = "update drop_data set dropperid='" + this.怪物爆物怪物代码.getText() + "' where id=" + this.怪物爆物序列号.getText() + ";";
                    PreparedStatement dropperid = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    dropperid.executeUpdate(sqlString2);
                    sqlString3 = "update drop_data set itemid='" + this.怪物爆物物品代码.getText() + "' where id=" + this.怪物爆物序列号.getText() + ";";
                    PreparedStatement itemid = DatabaseConnection.getConnection().prepareStatement(sqlString3);
                    itemid.executeUpdate(sqlString3);
                    sqlString4 = "update drop_data set chance='" + this.怪物爆物爆率.getText() + "' where id=" + this.怪物爆物序列号.getText() + ";";
                    PreparedStatement chance = DatabaseConnection.getConnection().prepareStatement(sqlString4);
                    chance.executeUpdate(sqlString4);
                    JOptionPane.showMessageDialog(null, "[信息]:修改成功。");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择你要修改的数据。");
        }
    }//GEN-LAST:event_修改怪物爆物ActionPerformed

    private void 刷新怪物爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新怪物爆物ActionPerformed
        JOptionPane.showMessageDialog(null, "[信息]:刷新怪物物品掉落数据。");
        刷新怪物爆物();
    }//GEN-LAST:event_刷新怪物爆物ActionPerformed

    private void 世界爆物序列号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_世界爆物序列号ActionPerformed

    }//GEN-LAST:event_世界爆物序列号ActionPerformed

    private void 世界爆物物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_世界爆物物品代码ActionPerformed

    }//GEN-LAST:event_世界爆物物品代码ActionPerformed

    private void 世界爆物爆率ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_世界爆物爆率ActionPerformed

    }//GEN-LAST:event_世界爆物爆率ActionPerformed

    private void 添加世界爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_添加世界爆物ActionPerformed
        boolean result1 = this.世界爆物物品代码.getText().matches("[0-9]+");
        boolean result2 = this.世界爆物爆率.getText().matches("[0-9]+");
        if (result1 && result2) {
            if (Integer.parseInt(this.世界爆物物品代码.getText()) < 0 && Integer.parseInt(this.世界爆物爆率.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "[信息]:请填写正确的值。");
                return;
            }
            PreparedStatement ps1 = null;
            ResultSet rs = null;
            try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO drop_data_global (continent,dropType,itemid,minimum_quantity,maximum_quantity,questid,chance) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                ps.setInt(1, 1);
                ps.setInt(2, 1);
                ps.setInt(3, Integer.parseInt(this.世界爆物物品代码.getText()));
                ps.setInt(4, 1);
                ps.setInt(5, 1);
                ps.setInt(6, 0);
                ps.setInt(7, Integer.parseInt(this.世界爆物爆率.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "[信息]:世界爆物添加成功。");
                刷新世界爆物();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入<物品代码>，<物品爆率> 。");
        }

    }//GEN-LAST:event_添加世界爆物ActionPerformed

    private void 删除世界爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除世界爆物ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.世界爆物序列号.getText().matches("[0-9]+");
        if (result == true) {
            int 商城SN编码 = Integer.parseInt(this.世界爆物序列号.getText());
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM drop_data_global WHERE id = ?");
                ps1.setInt(1, 商城SN编码);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from drop_data_global where id =" + 商城SN编码 + "";
                    ps1.executeUpdate(sqlstr);
                    JOptionPane.showMessageDialog(null, "[信息]:删除成功。");
                    刷新世界爆物();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择你要删除的物品。");
        }
    }//GEN-LAST:event_删除世界爆物ActionPerformed

    private void 世界爆物名称ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_世界爆物名称ActionPerformed

    }//GEN-LAST:event_世界爆物名称ActionPerformed

    private void 修改世界爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改世界爆物ActionPerformed

        boolean result2 = this.世界爆物物品代码.getText().matches("[0-9]+");
        boolean result3 = this.世界爆物爆率.getText().matches("[0-9]+");
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        if (result2 && result3) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE drop_data_global SET dropperid = ?, itemid = ?, chance = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM drop_data_global WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.世界爆物序列号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    String sqlString3 = null;
                    sqlString2 = "update drop_data_global set itemid='" + this.世界爆物物品代码.getText() + "' where id=" + this.世界爆物序列号.getText() + ";";
                    PreparedStatement dropperid = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    dropperid.executeUpdate(sqlString2);
                    sqlString3 = "update drop_data_global set chance='" + this.世界爆物爆率.getText() + "' where id=" + this.世界爆物序列号.getText() + ";";
                    PreparedStatement itemid = DatabaseConnection.getConnection().prepareStatement(sqlString3);
                    itemid.executeUpdate(sqlString3);
                    JOptionPane.showMessageDialog(null, "[信息]:修改成功。");
                    刷新世界爆物();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择你要修改的数据。");
        }
    }//GEN-LAST:event_修改世界爆物ActionPerformed

    private void 刷新世界爆物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新世界爆物ActionPerformed
        JOptionPane.showMessageDialog(null, "[信息]:刷新世界物品掉落数据。");
        刷新世界爆物();
    }//GEN-LAST:event_刷新世界爆物ActionPerformed

    private void 查询物品掉落ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查询物品掉落ActionPerformed
        boolean result = this.查询物品掉落代码.getText().matches("[0-9]+");
        if (result) {
            if (Integer.parseInt(this.查询物品掉落代码.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "[信息]:请填写正确的值。");
                return;
            }
            for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;

                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM drop_data WHERE itemid =  " + Integer.parseInt(this.查询物品掉落代码.getText()) + "");
                rs = ps.executeQuery();
                while (rs.next()) {

                    ((DefaultTableModel) 怪物爆物.getModel()).insertRow(怪物爆物.getRowCount(), new Object[]{
                        rs.getInt("id"),
                        rs.getInt("dropperid"),
                        rs.getInt("itemid"),
                        rs.getInt("chance"),
                        MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                    });
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            怪物爆物.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int i = 怪物爆物.getSelectedRow();
                    String a = 怪物爆物.getValueAt(i, 0).toString();
                    String a1 = 怪物爆物.getValueAt(i, 1).toString();
                    String a2 = 怪物爆物.getValueAt(i, 2).toString();
                    String a3 = 怪物爆物.getValueAt(i, 3).toString();
                    //String a4 = 怪物爆物.getValueAt(i, 4).toString();
                    怪物爆物序列号.setText(a);
                    怪物爆物怪物代码.setText(a1);
                    怪物爆物物品代码.setText(a2);
                    怪物爆物爆率.setText(a3);
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入你要查找的物品代码。");
        }
    }//GEN-LAST:event_查询物品掉落ActionPerformed

    private void 查询物品掉落代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查询物品掉落代码ActionPerformed

    }//GEN-LAST:event_查询物品掉落代码ActionPerformed

    private void 查询怪物掉落ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查询怪物掉落ActionPerformed
        boolean result = this.查询怪物掉落代码.getText().matches("[0-9]+");
        if (result) {
            if (Integer.parseInt(this.查询怪物掉落代码.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "[信息]:请填写正确的值。");
                return;
            }
            for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;

                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM drop_data WHERE dropperid =  " + Integer.parseInt(this.查询怪物掉落代码.getText()) + " && itemid !=0");//&& itemid !=0
                rs = ps.executeQuery();
                while (rs.next()) {

                    ((DefaultTableModel) 怪物爆物.getModel()).insertRow(怪物爆物.getRowCount(), new Object[]{
                        rs.getInt("id"),
                        rs.getInt("dropperid"),
                        rs.getInt("itemid"),
                        rs.getInt("chance"),
                        MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                    });
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            怪物爆物.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int i = 怪物爆物.getSelectedRow();
                    String a = 怪物爆物.getValueAt(i, 0).toString();
                    String a1 = 怪物爆物.getValueAt(i, 1).toString();
                    String a2 = 怪物爆物.getValueAt(i, 2).toString();
                    String a3 = 怪物爆物.getValueAt(i, 3).toString();
                    //String a4 = 怪物爆物.getValueAt(i, 4).toString();
                    怪物爆物序列号.setText(a);
                    怪物爆物怪物代码.setText(a1);
                    怪物爆物物品代码.setText(a2);
                    怪物爆物爆率.setText(a3);
                    //怪物爆物物品名称.setText(a4);

                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入你要查找的怪物代码。");
        }
    }//GEN-LAST:event_查询怪物掉落ActionPerformed

    private void 删除指定的掉落按键ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除指定的掉落按键ActionPerformed
        String 输出 = "";
        PreparedStatement ps1 = null;
        ResultSet rs = null;

        boolean result = this.删除指定的掉落.getText().matches("[0-9]+");
        if (result == true) {
            int 商城SN编码 = Integer.parseInt(this.删除指定的掉落.getText());
            try {
                // for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
                //   ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
                //}
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM drop_data WHERE itemid = ?");
                ps1.setInt(1, 商城SN编码);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from drop_data where itemid =" + 商城SN编码 + "";
                    ps1.executeUpdate(sqlstr);
                    JOptionPane.showMessageDialog(null, "[信息]:成功删除 " + 商城SN编码 + " 物品。");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            刷新怪物爆物();
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入你要查找的物品代码。");
        }
    }//GEN-LAST:event_删除指定的掉落按键ActionPerformed
    public static void GetConfigValues() {
        //动态数据库连接
        Connection con = DatabaseConnection.getConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT name, val FROM ConfigValues")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    int val = rs.getInt("val");
                    ConfigValuesMap.put(name, val);
                }
            }
            ps.close();
        } catch (SQLException ex) {
            System.err.println("读取动态数据库出错：" + ex.getMessage());
        }
    }
    
   private void 蓝蜗牛开关(String str) {
        蓝蜗牛开关.setText(str);
    }

    private void 刷新蓝蜗牛开关() {
        String 蓝蜗牛显示 = "";
        int 蓝蜗牛 = JokeMS.ConfigValuesMap.get("蓝蜗牛开关");
        if (蓝蜗牛 <= 0) {
            蓝蜗牛显示 = "蓝蜗牛:开";
        } else {
            蓝蜗牛显示 = "蓝蜗牛:关";
        }
        蓝蜗牛开关(蓝蜗牛显示);
    }


    
    private void 修改物品掉落持续时间ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改物品掉落持续时间ActionPerformed
        boolean result2 = this.物品掉落持续时间.getText().matches("[0-9]+");
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        if (result2) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");
                ps1.setInt(1, 998);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    sqlString2 = "update configvalues set Val='" + this.物品掉落持续时间.getText() + "' where id = 998;";
                    PreparedStatement dropperid = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    dropperid.executeUpdate(sqlString2);
                    JokeMS.GetConfigValues();
                    刷新物品掉落持续时间();
                    JOptionPane.showMessageDialog(null, "[信息]:修改成功。");
                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入你要修改的数据。");
        }
    }//GEN-LAST:event_修改物品掉落持续时间ActionPerformed

    private void 刷新怪物卡片ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新怪物卡片ActionPerformed
        刷新怪物卡片();
    }//GEN-LAST:event_刷新怪物卡片ActionPerformed
    private void 刷新物品掉落持续时间() {
        int 显示 = gui.JokeMS.ConfigValuesMap.get("物品掉落持续时间");
        物品掉落持续时间.setText("" + 显示 + "");
    }
    private void 修改物品掉落持续时间1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改物品掉落持续时间1ActionPerformed
        boolean result2 = this.地图物品上限.getText().matches("[0-9]+");
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        if (result2) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");
                ps1.setInt(1, 997);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    sqlString2 = "update configvalues set Val='" + this.地图物品上限.getText() + "' where id = 997;";
                    PreparedStatement dropperid = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    dropperid.executeUpdate(sqlString2);
                    JokeMS.GetConfigValues();
                    刷新地图物品上限();
                    JOptionPane.showMessageDialog(null, "[信息]:修改成功。");
                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入你要修改的数据。");
        }
    }//GEN-LAST:event_修改物品掉落持续时间1ActionPerformed
    private void 刷新地图物品上限() {
        int 显示 = gui.JokeMS.ConfigValuesMap.get("地图物品上限");
        地图物品上限.setText("" + 显示 + "");
    }
    private void 修改物品掉落持续时间2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改物品掉落持续时间2ActionPerformed
        boolean result2 = this.地图刷新频率.getText().matches("[0-9]+");
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        if (result2) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");
                ps1.setInt(1, 996);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    sqlString2 = "update configvalues set Val='" + this.地图刷新频率.getText() + "' where id = 996;";
                    PreparedStatement dropperid = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    dropperid.executeUpdate(sqlString2);
                    gui.JokeMS.GetConfigValues();
                    刷新地图刷新频率();
                    JOptionPane.showMessageDialog(null, "[信息]:修改成功。");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入你要修改的数据。");
        }
    }//GEN-LAST:event_修改物品掉落持续时间2ActionPerformed

    private void 刷新模式ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新模式ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_刷新模式ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JOptionPane.showMessageDialog(null, "[信息]:商城重载开始。");
        //        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, ";商城开始重新载入商品，正在维护，请勿进入。"));        // TODO add your handling code here:
        CashItemFactory.getInstance().clearCashShop();
        //        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, ";商城重新载入商品成功，维护完毕，开放进入。"));
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            cserv.broadcastPacket((MaplePacketCreator.serverNotice(0, ";商城重新载入商品成功，维护完毕，开放进入。")));
        }
        JOptionPane.showMessageDialog(null, "[信息]:商城重载成功。");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void 添加ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_添加ActionPerformed
        boolean result1 = this.商品编码.getText().matches("[0-9]+");
        boolean result2 = this.商品数量.getText().matches("[0-9]+");
        boolean result3 = this.商品价格.getText().matches("[0-9]+");
        boolean result4 = this.商品时间.getText().matches("[0-9]+");
        boolean result5 = this.商品库存.getText().matches("[0-9]+");
        boolean result6 = this.每日限购.getText().matches("[0-9]+");
        boolean result7 = this.商品折扣.getText().matches("[0-9]+");
        boolean result8 = this.商品代码.getText().matches("[0-9]+");

        if (!result1 && !result2 && !result3 && !result4 && !result5 && !result6 && !result7 && !result8) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入正确的数据。");
            return;
        }
        if (商品编码.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请点击商品分类选择添加类型。");
            return;
        }
        if (商品代码.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入添加的商品代码。");
            return;
        }
        if (商品价格.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入商品价格。");
            return;
        }
        if (Integer.parseInt(this.商品价格.getText()) > 999999999) {
            JOptionPane.showMessageDialog(null, "[信息]:商品数量不能大于999999999。");
            return;
        }
        if (商品时间.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入商品的给予时间，0 代表无限制。");
            return;
        }
        if (商品数量.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入商品的商品数量。");
            return;
        }
        if (Integer.parseInt(this.商品数量.getText()) > 100) {
            JOptionPane.showMessageDialog(null, "[信息]:商品数量不能大于100。");
            return;
        }
        int 商品出售状态2;
        if (商品出售状态.getText().equals("")) {
            商品出售状态2 = -1;
        } else {
            商品出售状态2 = Integer.parseInt(this.商品出售状态.getText());
        }
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        try {
            ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM cashshop_modified_items WHERE serial = ?");
            ps1.setInt(1, Integer.parseInt(this.商品编码.getText()));
            rs = ps1.executeQuery();
            if (!rs.next()) {
                try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO cashshop_modified_items (serial, showup,itemid,priority,period,gender,count,meso,discount_price,mark, unk_1, unk_2, unk_3) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                    ps.setInt(1, Integer.parseInt(this.商品编码.getText()));
                    ps.setInt(2, 1);
                    ps.setInt(3, Integer.parseInt(this.商品代码.getText()));
                    ps.setInt(4, 0);
                    ps.setInt(5, Integer.parseInt(this.商品时间.getText()));
                    ps.setInt(6, 2);
                    ps.setInt(7, Integer.parseInt(this.商品数量.getText()));
                    ps.setInt(8, 0);
                    ps.setInt(9, Integer.parseInt(this.商品价格.getText()));
                    ps.setInt(10, 商品出售状态2);
                    ps.setInt(11, 0);
                    ps.setInt(12, 0);
                    ps.setInt(13, 0);
                    ps.executeUpdate();

                } catch (SQLException ex) {
                    Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!商品库存.getText().equals("")) {
                    int SN库存 = Getcharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2);
                    if (SN库存 == -1) {
                        Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2, -2);
                    }
                    Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2, -SN库存);
                    Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2, Integer.parseInt(this.商品库存.getText()));
                }
                if (!商品折扣.getText().equals("")) {
                    int SN库存 = Getcharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3);
                    if (SN库存 == -1) {
                        Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3, -2);
                    }
                    Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3, -SN库存);
                    Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3, Integer.parseInt(this.商品折扣.getText()));
                }

                JOptionPane.showMessageDialog(null, "[信息]:新物品载入成功。");
                int n = JOptionPane.showConfirmDialog(this, "是否刷新？\r\n刷新所耗时间会根据物品数量，服务器配置决定。", "信息", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    刷新();
                }
            } else {
                JOptionPane.showMessageDialog(null, "[信息]:已存在的SN编码无法成功载入。");
            }
        } catch (SQLException ex) {
            Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*int 数字 = Integer.parseInt(this.商品代码.getText()) - 1;
        商品代码.setText("" + 数字 + "");
        /*int 数字 = Integer.parseInt(this.商品代码.getText()) - 1;
        商品代码.setText("" + 数字 + "");
        /*int 数字 = Integer.parseInt(this.商品代码.getText()) - 1;
        商品代码.setText("" + 数字 + "");
        /*int 数字 = Integer.parseInt(this.商品代码.getText()) - 1;
        商品代码.setText("" + 数字 + "");
        /*int 数字 = Integer.parseInt(this.商品代码.getText()) - 1;
        商品代码.setText("" + 数字 + "");
        /*int 数字 = Integer.parseInt(this.商品代码.getText()) - 1;
        商品代码.setText("" + 数字 + "");
        /*int 数字 = Integer.parseInt(this.商品代码.getText()) - 1;
        商品代码.setText("" + 数字 + "");
        /*int 数字 = Integer.parseInt(this.商品代码.getText()) - 1;
        商品代码.setText("" + 数字 + "");
        读取商品(60100000, 60200000, 5, 2);*/
    }//GEN-LAST:event_添加ActionPerformed

    private void 修改ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改ActionPerformed
        boolean result1 = this.商品编码.getText().matches("[0-9]+");
        boolean result2 = this.商品数量.getText().matches("[0-9]+");
        boolean result3 = this.商品价格.getText().matches("[0-9]+");
        boolean result4 = this.商品时间.getText().matches("[0-9]+");
        boolean result5 = this.商品库存.getText().matches("[0-9]+");
        boolean result6 = this.每日限购.getText().matches("[0-9]+");
        boolean result7 = this.商品折扣.getText().matches("[0-9]+");
        boolean result8 = this.商品代码.getText().matches("[0-9]+");
        if (!result1 && !result2 && !result3 && !result4 && !result5 && !result6 && !result7 && !result8) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入正确的数据。");
            return;
        }
        if (商品编码.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请点击商品分类选择添加类型。");
            return;
        }
        if (商品代码.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入添加的商品代码。");
            return;
        }
        if (商品价格.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入商品价格。");
            return;
        }
        if (Integer.parseInt(this.商品价格.getText()) > 999999999) {
            JOptionPane.showMessageDialog(null, "[信息]:商品数量不能大于999999999。");
            return;
        }
        if (商品时间.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入商品的给予时间，0 代表无限制。");
            return;
        }
        if (商品数量.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请输入商品的商品数量。");
            return;
        }
        if (Integer.parseInt(this.商品数量.getText()) > 100) {
            JOptionPane.showMessageDialog(null, "[信息]:商品数量不能大于100。");
            return;
        }
        int 商品出售状态2;
        if (商品出售状态.getText().equals("")) {
            商品出售状态2 = -1;
        } else {
            商品出售状态2 = Integer.parseInt(this.商品出售状态.getText());
        }
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        try {
            //清楚table数据
            for (int i = ((DefaultTableModel) (this.charTable.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.charTable.getModel())).removeRow(i);
            }
            ps = DatabaseConnection.getConnection().prepareStatement("UPDATE cashshop_modified_items SET showup = ?, itemid = ?, priority = ?, period = ?, gender = ?, count = ?, meso = ?, discount_price = ?, mark = ?, unk_1 = ?, unk_2 = ?, unk_3 = ? WHERE serial = ?");
            ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM cashshop_modified_items WHERE serial = ?");
            ps1.setInt(1, Integer.parseInt(this.商品编码.getText()));
            rs = ps1.executeQuery();
            if (rs.next()) {

                String sqlString1 = null;
                String sqlString3 = null;
                String sqlString5 = null;
                String sqlString6 = null;
                String sqlString7 = null;

                sqlString1 = "update cashshop_modified_items set itemid='" + Integer.parseInt(this.商品代码.getText()) + "' where serial=" + Integer.parseInt(this.商品编码.getText()) + ";";
                PreparedStatement itemid = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                itemid.executeUpdate(sqlString1);

                sqlString3 = "update cashshop_modified_items set period='" + Integer.parseInt(this.商品时间.getText()) + "' where serial=" + Integer.parseInt(this.商品编码.getText()) + ";";
                PreparedStatement period = DatabaseConnection.getConnection().prepareStatement(sqlString3);
                period.executeUpdate(sqlString3);

                sqlString5 = "update cashshop_modified_items set count='" + Integer.parseInt(this.商品数量.getText()) + "' where serial=" + Integer.parseInt(this.商品编码.getText()) + ";";
                PreparedStatement count = DatabaseConnection.getConnection().prepareStatement(sqlString5);
                count.executeUpdate(sqlString5);

                sqlString6 = "update cashshop_modified_items set discount_price='" + Integer.parseInt(this.商品价格.getText()) + "' where serial=" + Integer.parseInt(this.商品编码.getText()) + ";";
                PreparedStatement discount_price = DatabaseConnection.getConnection().prepareStatement(sqlString6);
                discount_price.executeUpdate(sqlString6);

                sqlString7 = "update cashshop_modified_items set mark='" + Integer.parseInt(this.商品出售状态.getText()) + "' where serial=" + Integer.parseInt(this.商品编码.getText()) + ";";
                PreparedStatement mark = DatabaseConnection.getConnection().prepareStatement(sqlString7);
                mark.executeUpdate(sqlString7);
                if (!商品库存.getText().equals("")) {
                    int SN库存 = Getcharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2);
                    if (SN库存 == -1) {
                        Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2, -2);
                    }
                    Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2, -SN库存);
                    Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 2, Integer.parseInt(this.商品库存.getText()));
                } else {
                    删除SN库存2();
                }
                if (!商品折扣.getText().equals("")) {
                    int SN库存 = Getcharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3);
                    if (SN库存 == -1) {
                        Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3, -2);
                    }
                    Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3, -SN库存);
                    Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 3, Integer.parseInt(this.商品折扣.getText()));
                } else {
                    删除SN库存3();
                }

                if (!每日限购.getText().equals("")) {
                    int SN库存 = Getcharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 4);
                    if (SN库存 == -1) {
                        Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 4, -2);
                    }
                    Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 4, -SN库存);
                    Gaincharacter7("" + Integer.parseInt(this.商品编码.getText()) + "", 4, Integer.parseInt(this.每日限购.getText()));
                } else {
                    删除SN库存4();
                }
                JOptionPane.showMessageDialog(null, "[信息]:修改物品载入成功。");
                int n = JOptionPane.showConfirmDialog(this, "是否刷新？\r\n刷新所耗时间会根据物品数量，服务器配置决定。", "信息", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    刷新();
                }
            } else {
                JOptionPane.showMessageDialog(null, "[信息]:只是修改！如果需要添加新的SN编码！请点击添加。");

            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_修改ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        int n = JOptionPane.showConfirmDialog(this, "确定为[ " + 商品编码.getText() + " 商品]    上架?", "上架商品提示消息", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            上架();
            //刷新();
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
        int n = JOptionPane.showConfirmDialog(this, "确定为[ " + 商品编码.getText() + " 商品]    下架?", "上架商品提示消息", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            下架();
            //刷新();
        }
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        String 输出 = "";
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        int 商城SN编码 = Integer.parseInt(this.商品编码.getText());
        try {
            ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM cashshop_modified_items WHERE serial = ?");
            ps1.setInt(1, 商城SN编码);
            rs1 = ps1.executeQuery();
            if (rs1.next()) {
                String sqlstr = " delete from cashshop_modified_items where serial =" + 商城SN编码 + ";";
                ps1.executeUpdate(sqlstr);
                JOptionPane.showMessageDialog(null, "[信息]:成功删除商品。");
            } else {
                JOptionPane.showMessageDialog(null, "[信息]:删除商品失败具。");

            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        删除SN库存();
        int n = JOptionPane.showConfirmDialog(this, "是否刷新？\r\n刷新所耗时间会根据物品数量，服务器配置决定。", "信息", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            刷新();
        }
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int n = JOptionPane.showConfirmDialog(this, "是否刷新？\r\n刷新所耗时间会根据物品数量，服务器配置决定。", "信息", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            initCharacterPannel();
        }
        JOptionPane.showMessageDialog(null, "[信息]:刷新商城物品列表。");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void 显示类型ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_显示类型ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_显示类型ActionPerformed

    private void 商品出售状态ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_商品出售状态ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_商品出售状态ActionPerformed

    private void 商品编码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_商品编码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_商品编码ActionPerformed

    private void 其他ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_其他ActionPerformed
        读取商品(60200000, 60300000, 5, 3);
    }//GEN-LAST:event_其他ActionPerformed

    private void 宠物服饰ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_宠物服饰ActionPerformed
        读取商品(60100000, 60200000, 5, 2);
    }//GEN-LAST:event_宠物服饰ActionPerformed

    private void 宠物ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_宠物ActionPerformed
        读取商品(60000000, 60100000, 5, 1);
    }//GEN-LAST:event_宠物ActionPerformed

    private void 效果ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_效果ActionPerformed
        读取商品(50500000, 50600000, 4, 4);
    }//GEN-LAST:event_效果ActionPerformed

    private void 游戏ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_游戏ActionPerformed
        读取商品(50400000, 50500000, 4, 5);
    }//GEN-LAST:event_游戏ActionPerformed

    private void 纪念日ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_纪念日ActionPerformed
        读取商品(50300000, 50400000, 4, 6);
    }//GEN-LAST:event_纪念日ActionPerformed

    private void 个人商店ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_个人商店ActionPerformed
        读取商品(50200000, 50300000, 4, 3);
    }//GEN-LAST:event_个人商店ActionPerformed

    private void 表情ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_表情ActionPerformed
        读取商品(50100000, 50200000, 4, 2);
    }//GEN-LAST:event_表情ActionPerformed

    private void 会员卡ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_会员卡ActionPerformed
        读取商品(50000000, 50100000, 4, 1);
    }//GEN-LAST:event_会员卡ActionPerformed

    private void 卷轴ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_卷轴ActionPerformed
        读取商品(30200000, 30300000, 3, 3);
    }//GEN-LAST:event_卷轴ActionPerformed

    private void 通讯物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_通讯物品ActionPerformed
        读取商品(30100000, 30200000, 3, 2);
    }//GEN-LAST:event_通讯物品ActionPerformed

    private void 喜庆物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_喜庆物品ActionPerformed
        读取商品(30000000, 30100000, 3, 1);
    }//GEN-LAST:event_喜庆物品ActionPerformed

    private void 骑宠ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_骑宠ActionPerformed
        读取商品(21200000, 21300000, 2, 8);
    }//GEN-LAST:event_骑宠ActionPerformed

    private void 披风ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_披风ActionPerformed
        读取商品(21100000, 21200000, 2, 3);
    }//GEN-LAST:event_披风ActionPerformed

    private void 飞镖ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_飞镖ActionPerformed
        读取商品(21000000, 21100000, 2, 4);
    }//GEN-LAST:event_飞镖ActionPerformed

    private void 戒指ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_戒指ActionPerformed
        读取商品(20900000, 21000000, 2, 9);
    }//GEN-LAST:event_戒指ActionPerformed

    private void 武器ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_武器ActionPerformed
        读取商品(20800000, 20900000, 2, 12);
    }//GEN-LAST:event_武器ActionPerformed

    private void 手套ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_手套ActionPerformed
        读取商品(20700000, 20800000, 2, 11);
    }//GEN-LAST:event_手套ActionPerformed

    private void 鞋子ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_鞋子ActionPerformed
        读取商品(20600000, 20700000, 2, 7);
    }//GEN-LAST:event_鞋子ActionPerformed

    private void 裙裤ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_裙裤ActionPerformed
        读取商品(20500000, 20600000, 2, 2);
    }//GEN-LAST:event_裙裤ActionPerformed

    private void 上衣ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_上衣ActionPerformed
        读取商品(20400000, 20500000, 2, 13);
    }//GEN-LAST:event_上衣ActionPerformed

    private void 长袍ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_长袍ActionPerformed
        读取商品(20300000, 20400000, 2, 5);
    }//GEN-LAST:event_长袍ActionPerformed

    private void 眼饰ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_眼饰ActionPerformed
        读取商品(20200000, 20300000, 2, 10);
    }//GEN-LAST:event_眼饰ActionPerformed

    private void 脸饰ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_脸饰ActionPerformed
        读取商品(20100000, 20200000, 2, 6);
    }//GEN-LAST:event_脸饰ActionPerformed

    private void 帽子ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_帽子ActionPerformed
        读取商品(20000000, 20100000, 2, 1);
    }//GEN-LAST:event_帽子ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        JOptionPane.showMessageDialog(null, "[信息]:未启用。");
        //JOptionPane.showMessageDialog(this, "未启用");  // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void 活动ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_活动ActionPerformed
        读取商品(10200000, 10300000, 1, 3);
    }//GEN-LAST:event_活动ActionPerformed

    private void 读取热销产品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_读取热销产品ActionPerformed
        读取商品(10000000, 10100000, 1, 1);
    }//GEN-LAST:event_读取热销产品ActionPerformed

    private void 主题馆ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_主题馆ActionPerformed
        读取商品(10100000, 10200000, 1, 2);
    }//GEN-LAST:event_主题馆ActionPerformed

    private void 刷新家族信息ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新家族信息ActionPerformed
        刷新家族信息();
    }//GEN-LAST:event_刷新家族信息ActionPerformed

    private void 家族IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族IDActionPerformed

    private void 家族名称ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族名称ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族名称ActionPerformed

    private void 家族族长ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族族长ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族族长ActionPerformed

    private void 家族成员2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族成员2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族成员2ActionPerformed

    private void 家族成员3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族成员3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族成员3ActionPerformed

    private void 家族成员4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族成员4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族成员4ActionPerformed

    private void 家族成员5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族成员5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族成员5ActionPerformed

    private void 家族GPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_家族GPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_家族GPActionPerformed
    public static int 在线玩家() {
        int p = 0;
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr != null) {
                    p++;
                }
            }
        }
        return p;
    }

    private void 刷新角色信息ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新角色信息ActionPerformed
        JOptionPane.showMessageDialog(null, "[信息]:显示游戏所有玩家角色信息。");
        刷新角色信息();
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
    }//GEN-LAST:event_刷新角色信息ActionPerformed

    private void 显示管理角色ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_显示管理角色ActionPerformed
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
        for (int i = ((DefaultTableModel) (this.角色信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters  WHERE gm >0 ");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 在线 = "";
                if (World.Find.findChannel(rs.getString("name")) > 0) {
                    在线 = "在线";
                } else {
                    在线 = "离线";
                }
                ((DefaultTableModel) 角色信息.getModel()).insertRow(角色信息.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    rs.getInt("accountid"),
                    rs.getString("name"),
                    getJobNameById(rs.getInt("job")),
                    rs.getInt("level"),
                    rs.getInt("str"),
                    rs.getInt("dex"),
                    rs.getInt("int"),
                    rs.getInt("luk"),
                    rs.getInt("maxhp"),
                    rs.getInt("maxmp"),
                    rs.getInt("meso"),
                    rs.getInt("map"),
                    在线,
                    rs.getInt("gm"),
                    rs.getInt("hair"),
                    rs.getInt("face"
                    )});
            }
            JOptionPane.showMessageDialog(null, "[信息]:显示游戏所有管理员角色信息。");
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_显示管理角色ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean A = this.等级.getText().matches("[0-9]+");
        boolean B = this.GM.getText().matches("[0-9]+");
        boolean C = this.地图.getText().matches("[0-9]+");
        boolean D = this.金币1.getText().matches("[0-9]+");
        boolean E = this.MP.getText().matches("[0-9]+");
        boolean F = this.HP.getText().matches("[0-9]+");
        boolean G = this.运气.getText().matches("[0-9]+");
        boolean H = this.智力.getText().matches("[0-9]+");
        boolean Y = this.敏捷.getText().matches("[0-9]+");
        boolean J = this.力量.getText().matches("[0-9]+");
        if (角色昵称.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "角色昵称不能留空");
            return;
        }
        if (World.Find.findChannel(角色昵称.getText()) > 0) {
            JOptionPane.showMessageDialog(null, "请先将角色离线后再修改。");
            return;
        }
        int n = JOptionPane.showConfirmDialog(this, "你确定要修改这个角色吗？", "信息", JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("UPDATE characters SET (name = ?,level = ?, str = ?, dex = ?, luk = ?,int = ?,  maxhp = ?, maxmp = ?, meso = ?, map = ?, gm = ?, hair = ?, face = ? )WHERE id = ?");
            ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM characters WHERE id = ?");
            ps1.setInt(1, Integer.parseInt(this.角色ID.getText()));
            rs = ps1.executeQuery();
            if (rs.next()) {
                String sqlString1 = null;
                String sqlString2 = null;
                String sqlString3 = null;
                String sqlString4 = null;
                String sqlString5 = null;
                String sqlString6 = null;
                String sqlString7 = null;
                String sqlString8 = null;
                String sqlString9 = null;
                String sqlString10 = null;
                String sqlString11 = null;
                String sqlString12 = null;
                String sqlString13 = null;
                sqlString1 = "update characters set name='" + this.角色昵称.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement name = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                name.executeUpdate(sqlString1);
                sqlString2 = "update characters set level='" + this.等级.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement level = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                level.executeUpdate(sqlString2);

                sqlString3 = "update characters set str='" + this.力量.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement str = DatabaseConnection.getConnection().prepareStatement(sqlString3);
                str.executeUpdate(sqlString3);

                sqlString4 = "update characters set dex='" + this.敏捷.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement dex = DatabaseConnection.getConnection().prepareStatement(sqlString4);
                dex.executeUpdate(sqlString4);

                sqlString5 = "update characters set luk='" + this.智力.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement luk = DatabaseConnection.getConnection().prepareStatement(sqlString5);
                luk.executeUpdate(sqlString5);

                sqlString6 = "update characters set `int`='" + this.运气.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement executeUpdate = DatabaseConnection.getConnection().prepareStatement(sqlString6);
                executeUpdate.executeUpdate(sqlString6);

                sqlString7 = "update characters set maxhp='" + this.HP.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement maxhp = DatabaseConnection.getConnection().prepareStatement(sqlString7);
                maxhp.executeUpdate(sqlString7);

                sqlString8 = "update characters set maxmp='" + this.MP.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement maxmp = DatabaseConnection.getConnection().prepareStatement(sqlString8);
                maxmp.executeUpdate(sqlString8);

                sqlString9 = "update characters set meso='" + this.金币1.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement meso = DatabaseConnection.getConnection().prepareStatement(sqlString9);
                meso.executeUpdate(sqlString9);

                sqlString10 = "update characters set map='" + this.地图.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement map = DatabaseConnection.getConnection().prepareStatement(sqlString10);
                map.executeUpdate(sqlString10);

                sqlString11 = "update characters set gm='" + this.GM.getText() + "' where id=" + this.角色ID.getText() + ";";
                PreparedStatement gm = DatabaseConnection.getConnection().prepareStatement(sqlString11);
                gm.executeUpdate(sqlString11);

                sqlString12 = "update characters set hair='" + this.发型.getText() + "' where id=" + this.发型.getText() + ";";
                PreparedStatement hair = DatabaseConnection.getConnection().prepareStatement(sqlString12);
                hair.executeUpdate(sqlString12);

                sqlString13 = "update characters set face='" + this.脸型.getText() + "' where id=" + this.脸型.getText() + ";";
                PreparedStatement face = DatabaseConnection.getConnection().prepareStatement(sqlString13);
                face.executeUpdate(sqlString13);
                JOptionPane.showMessageDialog(null, "[信息]:角色信息修改成功。");
                刷新角色信息();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton38ActionPerformed

    private void 删除角色ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除角色ActionPerformed
        String 输出 = "";
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.角色ID.getText().matches("[0-9]+");

        if (result1) {
            int n = JOptionPane.showConfirmDialog(this, "你确定要删除这个角色吗？", "信息", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                try {
                    ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM characters WHERE id = ?");
                    ps1.setInt(1, Integer.parseInt(this.角色ID.getText()));
                    rs = ps1.executeQuery();
                    if (rs.next()) {
                        String sqlstr = " delete from characters where id =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr);
                        String sqlstr2 = " delete from inventoryitems where characterid =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr2);
                        String sqlstr3 = " delete from auctionitems where characterid =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr3);
                        String sqlstr31 = " delete from auctionitems1 where characterid =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr31);
                        String sqlstr4 = " delete from csitems where accountid =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr4);
                        String sqlstr5 = " delete from bank_item where cid =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr5);
                        String sqlstr6 = " delete from bossrank where cid =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr6);
                        String sqlstr7 = " delete from skills where characterid =" + Integer.parseInt(this.角色ID.getText()) + "";
                        ps1.executeUpdate(sqlstr7);
                        JOptionPane.showMessageDialog(null, "[信息]:成功删除角色 " + Integer.parseInt(this.角色ID.getText()) + " ，以及所有相关信息。");
                        刷新角色信息();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择删除的角色。");
        }
    }//GEN-LAST:event_删除角色ActionPerformed

    private void 角色昵称ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_角色昵称ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_角色昵称ActionPerformed

    private void 等级ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_等级ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_等级ActionPerformed

    private void 力量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_力量ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_力量ActionPerformed

    private void 敏捷ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_敏捷ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_敏捷ActionPerformed

    private void 智力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_智力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_智力ActionPerformed

    private void 运气ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_运气ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_运气ActionPerformed

    private void HPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HPActionPerformed

    private void MPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MPActionPerformed

    private void 金币1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_金币1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_金币1ActionPerformed

    private void 地图ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_地图ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_地图ActionPerformed

    private void GMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GMActionPerformed

    private void 角色IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_角色IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_角色IDActionPerformed

    private void 卡号自救1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_卡号自救1ActionPerformed
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.角色ID.getText().matches("[0-9]+");
        if (result1) {
            int n = JOptionPane.showConfirmDialog(this, "你确定要解卡发型脸型自救这个角色吗？", "信息", JOptionPane.YES_NO_OPTION);
            if (n != JOptionPane.YES_OPTION) {
                return;
            }
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE characters SET (hair = ?,face = ?)WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM characters WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.角色ID.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString1 = null;
                    String sqlString2 = null;
                    sqlString1 = "update characters set hair='30000' where id=" + this.角色ID.getText() + ";";
                    PreparedStatement hair = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                    hair.executeUpdate(sqlString1);
                    sqlString2 = "update characters set face='20000' where id=" + this.角色ID.getText() + ";";
                    PreparedStatement face = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    face.executeUpdate(sqlString2);
                    JOptionPane.showMessageDialog(null, "[信息]:解救成功，发型脸型初始化。");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择卡发型脸型的角色。");
        }
    }//GEN-LAST:event_卡号自救1ActionPerformed

    private void 卡号自救2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_卡号自救2ActionPerformed
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
        String 输出 = "";
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.角色ID.getText().matches("[0-9]+");
        if (result1) {
            int n = JOptionPane.showConfirmDialog(this, "你确定要解卡物品自救这个角色吗？", "信息", JOptionPane.YES_NO_OPTION);
            if (n != JOptionPane.YES_OPTION) {
                return;
            }
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE characterid = ?");
                ps1.setInt(1, Integer.parseInt(this.角色ID.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr2 = " delete from inventoryitems where characterid =" + Integer.parseInt(this.角色ID.getText()) + "";
                    ps1.executeUpdate(sqlstr2);
                    JOptionPane.showMessageDialog(null, "[信息]:角色已经进行38处理。");
                    刷新角色信息();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择要38处理的角色。");
        }
    }//GEN-LAST:event_卡号自救2ActionPerformed

    private void 查看技能ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查看技能ActionPerformed

        JOptionPane.showMessageDialog(null, "[信息]:查看玩家技能信息。");
        刷新技能信息();
    }//GEN-LAST:event_查看技能ActionPerformed

    private void 查看背包ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查看背包ActionPerformed
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
        boolean result1 = this.角色ID.getText().matches("[0-9]+");
        if (!result1) {
            JOptionPane.showMessageDialog(null, "[信息]:请选择角色。");
            return;
        }
        if (账号ID.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请先选择账号，再选择账号下的角色，接下来才可以查看游戏仓库。");
            return;
        }
        JOptionPane.showMessageDialog(null, "[信息]:查询速度跟角色信息量有关，请耐心等候。");
        刷新角色背包穿戴();
        刷新角色装备背包();
        刷新角色消耗背包();
        刷新角色设置背包();
        刷新角色其他背包();
        刷新角色特殊背包();
        刷新角色游戏仓库();
        刷新角色商城仓库();
        //        刷新角色金币拍卖行();
        //        刷新角色点券拍卖行();
        JOptionPane.showMessageDialog(null, "[信息]:请转到角色道具信息面板查看。");
    }//GEN-LAST:event_查看背包ActionPerformed

    private void 卡家族解救ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_卡家族解救ActionPerformed
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.角色ID.getText().matches("[0-9]+");
        if (result1) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE characters SET (guildid = ?,guildrank = ?,allianceRank = ?)WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM characters WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.角色ID.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString1 = null;
                    String sqlString2 = null;
                    String sqlString3 = null;
                    sqlString1 = "update characters set guildid='0' where id=" + this.角色ID.getText() + ";";
                    PreparedStatement hair = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                    hair.executeUpdate(sqlString1);
                    sqlString2 = "update characters set guildrank='5' where id=" + this.角色ID.getText() + ";";
                    PreparedStatement face = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    face.executeUpdate(sqlString2);
                    sqlString3 = "update characters set allianceRank='5' where id=" + this.角色ID.getText() + ";";
                    PreparedStatement allianceRank = DatabaseConnection.getConnection().prepareStatement(sqlString3);
                    allianceRank.executeUpdate(sqlString3);
                    JOptionPane.showMessageDialog(null, "[信息]:解卡家族角色成功。");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择卡家族的角色。");
        }
    }//GEN-LAST:event_卡家族解救ActionPerformed


    private void 脸型ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_脸型ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_脸型ActionPerformed

    private void 发型ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_发型ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_发型ActionPerformed

    private void 离线角色ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_离线角色ActionPerformed
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
        for (int i = ((DefaultTableModel) (this.角色信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters order by id desc");
            rs = ps.executeQuery();
            while (rs.next()) {
                if (World.Find.findChannel(rs.getString("name")) <= 0) {
                    ((DefaultTableModel) 角色信息.getModel()).insertRow(角色信息.getRowCount(), new Object[]{
                        rs.getInt("id"),
                        rs.getInt("accountid"),
                        rs.getString("name"),
                        getJobNameById(rs.getInt("job")),
                        rs.getInt("level"),
                        rs.getInt("str"),
                        rs.getInt("dex"),
                        rs.getInt("int"),
                        rs.getInt("luk"),
                        rs.getInt("maxhp"),
                        rs.getInt("maxmp"),
                        rs.getInt("meso"),
                        rs.getInt("map"),
                        "在线",
                        rs.getInt("gm"),
                        rs.getInt("hair"),
                        rs.getInt("face"
                        )});
                }
            }
            JOptionPane.showMessageDialog(null, "[信息]:显示游戏所有离线角色信息。");

        } catch (SQLException ex) {
            Logger.getLogger(Start.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_离线角色ActionPerformed

    private void 在线角色ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_在线角色ActionPerformed
        显示在线玩家.setText("在线玩家; " + 在线玩家() + "");
        for (int i = ((DefaultTableModel) (this.角色信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters order by id desc");
            rs = ps.executeQuery();
            while (rs.next()) {
                if (World.Find.findChannel(rs.getString("name")) > 0) {
                    ((DefaultTableModel) 角色信息.getModel()).insertRow(角色信息.getRowCount(), new Object[]{
                        rs.getInt("id"),
                        rs.getInt("accountid"),
                        rs.getString("name"),
                        getJobNameById(rs.getInt("job")),
                        rs.getInt("level"),
                        rs.getInt("str"),
                        rs.getInt("dex"),
                        rs.getInt("int"),
                        rs.getInt("luk"),
                        rs.getInt("maxhp"),
                        rs.getInt("maxmp"),
                        rs.getInt("meso"),
                        rs.getInt("map"),
                        "在线",
                        rs.getInt("gm"),
                        rs.getInt("hair"),
                        rs.getInt("face"
                        )});
                }
            }
            JOptionPane.showMessageDialog(null, "[信息]:显示游戏所有在线角色信息。");

        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_在线角色ActionPerformed

    private void 背包物品名字1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_背包物品名字1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_背包物品名字1ActionPerformed

    private void 身上穿戴序号1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_身上穿戴序号1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_身上穿戴序号1ActionPerformed

    private void 背包物品代码1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_背包物品代码1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_背包物品代码1ActionPerformed

    private void 删除穿戴装备ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除穿戴装备ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.身上穿戴序号1.getText().matches("[0-9]+");
        if (result == true) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE inventoryitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.身上穿戴序号1.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from inventoryitems where inventoryitemid =" + Integer.parseInt(this.身上穿戴序号1.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新角色背包穿戴();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品");
        }
    }//GEN-LAST:event_删除穿戴装备ActionPerformed

    private void 装备背包物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_装备背包物品名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_装备背包物品名字ActionPerformed

    private void 装备背包物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_装备背包物品序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_装备背包物品序号ActionPerformed

    private void 装备背包物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_装备背包物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_装备背包物品代码ActionPerformed

    private void 删除装备背包ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除装备背包ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.装备背包物品序号.getText().matches("[0-9]+");
        if (result == true) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE inventoryitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.装备背包物品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from inventoryitems where inventoryitemid =" + Integer.parseInt(this.装备背包物品序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新角色装备背包();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品");
        }
    }//GEN-LAST:event_删除装备背包ActionPerformed

    private void 消耗背包物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_消耗背包物品名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_消耗背包物品名字ActionPerformed

    private void 消耗背包物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_消耗背包物品序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_消耗背包物品序号ActionPerformed

    private void 消耗背包物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_消耗背包物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_消耗背包物品代码ActionPerformed

    private void 删除消耗背包ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除消耗背包ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.消耗背包物品序号.getText().matches("[0-9]+");
        if (result == true) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE inventoryitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.消耗背包物品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from inventoryitems where inventoryitemid =" + Integer.parseInt(this.消耗背包物品序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新角色消耗背包();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品");
        }
    }//GEN-LAST:event_删除消耗背包ActionPerformed

    private void 设置背包物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_设置背包物品名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_设置背包物品名字ActionPerformed

    private void 设置背包物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_设置背包物品序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_设置背包物品序号ActionPerformed

    private void 设置背包物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_设置背包物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_设置背包物品代码ActionPerformed

    private void 删除设置背包ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除设置背包ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.设置背包物品序号.getText().matches("[0-9]+");
        if (result == true) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE inventoryitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.设置背包物品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from inventoryitems where inventoryitemid =" + Integer.parseInt(this.设置背包物品序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新角色设置背包();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品");
        }
    }//GEN-LAST:event_删除设置背包ActionPerformed

    private void 其他背包物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_其他背包物品名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_其他背包物品名字ActionPerformed

    private void 其他背包物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_其他背包物品序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_其他背包物品序号ActionPerformed

    private void 其他背包物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_其他背包物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_其他背包物品代码ActionPerformed

    private void 删除其他背包ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除其他背包ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.其他背包物品序号.getText().matches("[0-9]+");
        if (result == true) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE inventoryitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.其他背包物品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from inventoryitems where inventoryitemid =" + Integer.parseInt(this.其他背包物品序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新角色其他背包();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品");
        }
    }//GEN-LAST:event_删除其他背包ActionPerformed

    private void 特殊背包物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_特殊背包物品名字ActionPerformed

    }//GEN-LAST:event_特殊背包物品名字ActionPerformed

    private void 特殊背包物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_特殊背包物品序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_特殊背包物品序号ActionPerformed

    private void 特殊背包物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_特殊背包物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_特殊背包物品代码ActionPerformed

    private void 删除特殊背包ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除特殊背包ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.特殊背包物品序号.getText().matches("[0-9]+");
        if (result == true) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE inventoryitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.特殊背包物品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from inventoryitems where inventoryitemid =" + Integer.parseInt(this.特殊背包物品序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新角色特殊背包();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品");
        }
    }//GEN-LAST:event_删除特殊背包ActionPerformed

    private void 游戏仓库物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_游戏仓库物品名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_游戏仓库物品名字ActionPerformed

    private void 游戏仓库物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_游戏仓库物品序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_游戏仓库物品序号ActionPerformed

    private void 游戏仓库物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_游戏仓库物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_游戏仓库物品代码ActionPerformed

    private void 删除游戏仓库ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除游戏仓库ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_删除游戏仓库ActionPerformed

    private void 商城仓库物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_商城仓库物品名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_商城仓库物品名字ActionPerformed

    private void 商城仓库物品序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_商城仓库物品序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_商城仓库物品序号ActionPerformed

    private void 商城仓库物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_商城仓库物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_商城仓库物品代码ActionPerformed

    private void 删除商城仓库ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除商城仓库ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.商城仓库物品序号.getText().matches("[0-9]+");
        if (result == true) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM csitems WHERE inventoryitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.商城仓库物品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from csitems where inventoryitemid =" + Integer.parseInt(this.商城仓库物品序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新角色商城仓库();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品");
        }
    }//GEN-LAST:event_删除商城仓库ActionPerformed

    private void 拍卖行物品名字1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_拍卖行物品名字1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_拍卖行物品名字1ActionPerformed

    private void 角色点券拍卖行序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_角色点券拍卖行序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_角色点券拍卖行序号ActionPerformed

    private void 拍卖行物品代码1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_拍卖行物品代码1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_拍卖行物品代码1ActionPerformed

    private void 删除拍卖行1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除拍卖行1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_删除拍卖行1ActionPerformed

    private void 拍卖行物品名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_拍卖行物品名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_拍卖行物品名字ActionPerformed

    private void 角色金币拍卖行序号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_角色金币拍卖行序号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_角色金币拍卖行序号ActionPerformed

    private void 拍卖行物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_拍卖行物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_拍卖行物品代码ActionPerformed

    private void 删除拍卖行ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除拍卖行ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_删除拍卖行ActionPerformed

    private void 技能名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_技能名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_技能名字ActionPerformed

    private void 修改技能ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改技能ActionPerformed
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.技能序号.getText().matches("[0-9]+");

        if (result1) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE skills SET skilllevel = ?,masterlevel = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM skills WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.技能序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString1 = null;
                    String sqlString2 = null;

                    sqlString1 = "update skills set skilllevel='" + this.技能目前等级.getText() + "' where id=" + this.技能序号.getText() + ";";
                    PreparedStatement skilllevel = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                    skilllevel.executeUpdate(sqlString1);

                    sqlString2 = "update skills set masterlevel='" + this.技能最高等级.getText() + "' where id=" + this.技能序号.getText() + ";";
                    PreparedStatement masterlevel = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    masterlevel.executeUpdate(sqlString2);

                    刷新技能信息();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要修改的技能");
        }// TODO add your handling code here:
    }//GEN-LAST:event_修改技能ActionPerformed

    private void 删除技能ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除技能ActionPerformed

        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.技能序号.getText().matches("[0-9]+");

        if (result1) {
            if (Integer.parseInt(this.技能序号.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "请填写正确的值");
            }
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM skills WHERE id = ?");
                ps1.setInt(1, Integer.parseInt(this.技能序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from skills where id =" + Integer.parseInt(this.技能序号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新技能信息();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的技能");
        }
    }//GEN-LAST:event_删除技能ActionPerformed

    private void 修改技能1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改技能1ActionPerformed
        JOptionPane.showMessageDialog(null, "[信息]:查看玩家技能信息。");
        刷新技能信息();
    }//GEN-LAST:event_修改技能1ActionPerformed

        public void runTool(final Tools tool) {
        if (tools.contains(tool)) {
            JOptionPane.showMessageDialog(null, "工具已在运行。");
        } else {
            tools.add(tool);
            Thread t = new Thread() {
                @Override
                public void run() {
                    switch (tool) {
                        case DumpCashShop:
                            DumpCashShop.main(new String[0]);
                            break;
                        case DumpItems:
                            DumpItems.main(new String[0]);
                            break;
                        case WzStringDumper装备数据:
                    {
                        try {
                            WzStringDumper装备数据.main(new String[0]);
                        } catch (IOException ex) {
                            Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                            break;
                        case DumpMobSkills:
                            DumpMobSkills.main(new String[0]);
                            break;
                        case DumpNpcNames:
                    {
                        try {
                            DumpNpcNames.main(new String[0]);
                        } catch (SQLException ex) {
                            Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                            break;
                        case DumpOxQuizData:
                    {
                        try {
                            DumpOxQuizData.main(new String[0]);
                        } catch (IOException ex) {
                            Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                            break;
                        case DumpQuests:
                            DumpQuests.main(new String[0]);
                            break;
                        case MonsterDropCreator:
                    {
                        try {
                            MonsterDropCreator.main(new String[0]);
                        } catch (IOException ex) {
                            Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NotBoundException ex) {
                            Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InstanceAlreadyExistsException ex) {
                            Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (MBeanRegistrationException ex) {
                            Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NotCompliantMBeanException ex) {
                            Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (MalformedObjectNameException ex) {
                            Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                            break;
                            
                    }
                    tools.remove(tool);
                }
            };
            t.start();
        }
    }
    
    
    public void openWindow(final Windows w) {
        if (!windows.containsKey(w)) {
            switch (w) {
                    case 基址计算工具:
                    windows.put(w, new 基址计算工具());
                    break;
                    case 卡密制作工具:
                    windows.put(w, new 卡密制作工具());
                    break;
                    case 包头转换工具:
                    windows.put(w, new 包头转换工具());
                    break;
                case 代码查询工具:
                    windows.put(w, new 代码查询工具());
                    break;
                    case 角色转移工具:
                    windows.put(w, new 角色转移工具());
                    break;
                    case 一键还原:
                    windows.put(w, new 一键还原());
                    break;
                    case 任务控制台:
                    windows.put(w, new 任务控制台());
                    break;
                    case 锻造控制台:
                    windows.put(w, new 锻造控制台());
                    break;
                    case 副本控制台:
                    windows.put(w, new 副本控制台());
                    break;
                    case 删除自添加NPC工具:
                    windows.put(w, new 删除自添加NPC工具());
                    break;
                    case 野外BOSS刷新时间:
                    windows.put(w, new 野外BOSS刷新时间());
                    break;
                    case 药水冷却时间控制台:
                    windows.put(w, new 药水冷却时间控制台());
                    break;
                    case 金锤子成功率控制台:
                    windows.put(w, new 金锤子成功率控制台());
                    break;
                    case 永恒重生装备控制台:
                    windows.put(w, new 永恒重生装备控制台());
                    break;
                    case 游戏抽奖工具:
                    windows.put(w, new 游戏抽奖工具());
                    break;
                default:
                    return;
            }
            windows.get(w).setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        }
        windows.get(w).setVisible(true);
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    class MyTableCellRenderer implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            ImageIcon ker = new ImageIcon("null");
            JLabel label = new JLabel(ker);
            label.setOpaque(false);
            return label;
        }
    }

    public enum Tools {

        DumpCashShop,
        DumpItems,
        WzStringDumper装备数据,
        DumpMobSkills,
        DumpNpcNames,
        DumpOxQuizData,
        DumpQuests,
        MonsterDropCreator;
    }

    public enum Windows {

        基址计算工具,
        角色转移工具,
        一键还原,
        任务控制台,
        锻造控制台,
        副本控制台,
        卡密制作工具,
        包头转换工具,
        代码查询工具,
        删除自添加NPC工具,
        游戏抽奖工具,
        药水冷却时间控制台,
        金锤子成功率控制台,
        永恒重生装备控制台,
        野外BOSS刷新时间,
        CashShopItemEditor,
        CashShopItemAdder,
        DropDataAdder,
        DropDataEditor,;

    }

    private javax.swing.ComboBoxModel getMapleTypeModel() {
        Vector mapleTypeModel = new Vector();
        mapleTypeModel.add("中国大陆");
//        for (ServerConstants.MapleType e : ServerConstants.MapleType.values()) {
//            if (e == ServerConstants.MapleType.UNKNOWN) {
//                continue;
//            }
//            mapleTypeModel.add(e.name());
//        }
        return new DefaultComboBoxModel(mapleTypeModel);
    }

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        openWindow(Windows.代码查询工具);
        if (!LoginServer.isShutdown() || searchServer) {
            return;

        }
    }//GEN-LAST:event_jButton31ActionPerformed

    private void 查询商店2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查询商店2ActionPerformed
        查询商店(1);
    }//GEN-LAST:event_查询商店2ActionPerformed

    private void 删除商品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除商品ActionPerformed

        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.商品序号.getText().matches("[0-9]+");
        if (result == true) {
            int 商城SN编码 = Integer.parseInt(this.商品序号.getText());
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM shopitems WHERE shopitemid = ?");
                ps1.setInt(1, 商城SN编码);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from shopitems where shopitemid =" + 商城SN编码 + "";
                    ps1.executeUpdate(sqlstr);
                    查询商店(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "[信息]:删除商店商品成功。");
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请选择你要删除的商品。");
        }
    }//GEN-LAST:event_删除商品ActionPerformed

    private void 新增商品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_新增商品ActionPerformed

        boolean result = this.商品物品代码.getText().matches("[0-9]+");
        boolean result1 = this.商店代码.getText().matches("[0-9]+");
        boolean result2 = this.商品售价金币.getText().matches("[0-9]+");

        if (result && result1 && result2) {
            if (Integer.parseInt(this.商店代码.getText()) < 0 && Integer.parseInt(this.商品物品代码.getText()) < 0 && Integer.parseInt(this.商品售价金币.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "[信息]:请填写正确的值。");
                return;
            }
            try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO shopitems (shopid ,itemid ,price ,pitch ,position ,reqitem ,reqitemq) VALUES ( ?, ?, ?, ?, ?, ?, ?)")) {
                ps.setInt(1, Integer.parseInt(this.商店代码.getText()));
                ps.setInt(2, Integer.parseInt(this.商品物品代码.getText()));
                ps.setInt(3, Integer.parseInt(this.商品售价金币.getText()));
                ps.setInt(4, 0);
                ps.setInt(5, 0);
                ps.setInt(6, 0);
                ps.setInt(7, 0);
                ps.executeUpdate();
                查询商店(1);
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "[信息]:新增商店商品成功。");
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:输入<商店ID><物品代码><售价>。");
        }
    }//GEN-LAST:event_新增商品ActionPerformed

    private void 商店代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_商店代码ActionPerformed

    }//GEN-LAST:event_商店代码ActionPerformed

    private void 修改商品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改商品ActionPerformed
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.商品物品代码.getText().matches("[0-9]+");
        boolean result1 = this.商店代码.getText().matches("[0-9]+");
        boolean result2 = this.商品售价金币.getText().matches("[0-9]+");

        if (result && result1 && result2) {
            if (Integer.parseInt(this.商店代码.getText()) < 0 && Integer.parseInt(this.商品物品代码.getText()) < 0 && Integer.parseInt(this.商品售价金币.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "请填写正确的值");
            }
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE shopitems SET itemid = ?,price = ?,shopid = ?WHERE shopitemid = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM shopitems WHERE shopitemid = ?");
                ps1.setInt(1, Integer.parseInt(this.商品序号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString1 = null;
                    String sqlString2 = null;
                    String sqlString3 = null;
                    sqlString1 = "update shopitems set itemid='" + this.商品物品代码.getText() + "' where shopitemid=" + this.商品序号.getText() + ";";
                    PreparedStatement itemid = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                    itemid.executeUpdate(sqlString1);

                    sqlString2 = "update shopitems set price='" + this.商品售价金币.getText() + "' where shopitemid=" + this.商品序号.getText() + ";";
                    PreparedStatement price = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    price.executeUpdate(sqlString2);

                    sqlString3 = "update shopitems set shopid='" + this.商店代码.getText() + "' where shopitemid=" + this.商品序号.getText() + ";";
                    PreparedStatement shopid = DatabaseConnection.getConnection().prepareStatement(sqlString3);
                    shopid.executeUpdate(sqlString3);

                    查询商店(1);
                }
                JOptionPane.showMessageDialog(null, "[信息]:商店商品修改成功。");
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:选择你要修改的商品,并填写<商店ID><物品代码><售价金币>。");
        }
    }//GEN-LAST:event_修改商品ActionPerformed

    private void 商品名称ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_商品名称ActionPerformed

    }//GEN-LAST:event_商品名称ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed

        查询商店(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton19ActionPerformed

    private void 全服发送物品数量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送物品数量ActionPerformed

    }//GEN-LAST:event_全服发送物品数量ActionPerformed

    private void 全服发送物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送物品代码ActionPerformed

    }//GEN-LAST:event_全服发送物品代码ActionPerformed

    private void 给予物品1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_给予物品1ActionPerformed
        刷物品2();    // TODO add your handling code here:
    }//GEN-LAST:event_给予物品1ActionPerformed
    private void 刷物品2() {
        try {
            int 数量;
            int 物品ID;
            物品ID = Integer.parseInt(全服发送物品代码.getText());
            数量 = Integer.parseInt(全服发送物品数量.getText());
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            MapleInventoryType type = GameConstants.getInventoryType(物品ID);
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (数量 >= 0) {
                        if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 数量, "")) {
                            return;
                        }
                        if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)
                                || type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100) {
                            final Equip item = (Equip) (ii.getEquipById(物品ID));
                            if (ii.isCash(物品ID)) {
                                item.setUniqueId(1);
                            }
                            final String name = ii.getName(物品ID);
                            if (物品ID / 10000 == 114 && name != null && name.length() > 0) { //medal
                                final String msg = "你已获得称号 <" + name + ">";
                                mch.getClient().getPlayer().dropMessage(5, msg);
                            }
                            MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                        } else {
                            MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 数量, "", null, (byte) 0);
                        }
                    } else {
                        MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -数量, true, false);
                    }
//                    mch.getClient().getSession.write(MaplePacketCreator.getShowItemGain(物品ID, (short) 数量, true));
                    mch.getClient().getSession().write(MaplePacketCreator.getShowItemGain(物品ID, (short) 数量, true));
                }
            }
            全服发送物品代码.setText("");
            全服发送物品数量.setText("");
            JOptionPane.showMessageDialog(null, "[信息]:发送成功。");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "[信息]:错误!" + e);
        }
    }
    private void 全服发送装备装备加卷ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备加卷ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备加卷ActionPerformed

    private void 全服发送装备装备制作人ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备制作人ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备制作人ActionPerformed

    private void 全服发送装备装备力量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备力量ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备力量ActionPerformed

    private void 全服发送装备装备MPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备MPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备MPActionPerformed

    private void 全服发送装备装备智力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备智力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备智力ActionPerformed

    private void 全服发送装备装备运气ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备运气ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备运气ActionPerformed

    private void 全服发送装备装备HPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备HPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备HPActionPerformed

    private void 全服发送装备装备攻击力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备攻击力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备攻击力ActionPerformed

    private void 全服发送装备装备给予时间ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备给予时间ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备给予时间ActionPerformed

    private void 全服发送装备装备可否交易ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备可否交易ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备可否交易ActionPerformed

    private void 全服发送装备装备敏捷ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备敏捷ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备敏捷ActionPerformed

    private void 全服发送装备物品IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备物品IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备物品IDActionPerformed

    private void 全服发送装备装备魔法力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备魔法力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备魔法力ActionPerformed

    private void 全服发送装备装备魔法防御ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备魔法防御ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备魔法防御ActionPerformed

    private void 全服发送装备装备物理防御ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备物理防御ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备物理防御ActionPerformed

    private void 给予装备1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_给予装备1ActionPerformed
        刷装备2(2);        // TODO add your handling code here:
    }//GEN-LAST:event_给予装备1ActionPerformed

    private void 发送装备玩家姓名ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_发送装备玩家姓名ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_发送装备玩家姓名ActionPerformed
    private void 刷装备2(int a) {
        try {
            int 物品ID;
            if ("物品ID".equals(全服发送装备物品ID.getText())) {
                物品ID = 0;
            } else {
                物品ID = Integer.parseInt(全服发送装备物品ID.getText());
            }
            int 力量;
            if ("力量".equals(全服发送装备装备力量.getText())) {
                力量 = 0;
            } else {
                力量 = Integer.parseInt(全服发送装备装备力量.getText());
            }
            int 敏捷;
            if ("敏捷".equals(全服发送装备装备敏捷.getText())) {
                敏捷 = 0;
            } else {
                敏捷 = Integer.parseInt(全服发送装备装备敏捷.getText());
            }
            int 智力;
            if ("智力".equals(全服发送装备装备智力.getText())) {
                智力 = 0;
            } else {
                智力 = Integer.parseInt(全服发送装备装备智力.getText());
            }
            int 运气;
            if ("运气".equals(全服发送装备装备运气.getText())) {
                运气 = 0;
            } else {
                运气 = Integer.parseInt(全服发送装备装备运气.getText());
            }
            int HP;
            if ("HP设置".equals(全服发送装备装备HP.getText())) {
                HP = 0;
            } else {
                HP = Integer.parseInt(全服发送装备装备HP.getText());
            }
            int MP;
            if ("MP设置".equals(全服发送装备装备MP.getText())) {
                MP = 0;
            } else {
                MP = Integer.parseInt(全服发送装备装备MP.getText());
            }
            int 可加卷次数;
            if ("加卷次数".equals(全服发送装备装备加卷.getText())) {
                可加卷次数 = 0;
            } else {
                可加卷次数 = Integer.parseInt(全服发送装备装备加卷.getText());
            }

            String 制作人名字;
            if ("制作人".equals(全服发送装备装备制作人.getText())) {
                制作人名字 = "";
            } else {
                制作人名字 = 全服发送装备装备制作人.getText();
            }
            int 给予时间;
            if ("给予物品时间".equals(全服发送装备装备给予时间.getText())) {
                给予时间 = 0;
            } else {
                给予时间 = Integer.parseInt(全服发送装备装备给予时间.getText());
            }
            String 是否可以交易 = 全服发送装备装备可否交易.getText();
            int 攻击力;
            if ("攻击力".equals(全服发送装备装备攻击力.getText())) {
                攻击力 = 0;
            } else {
                攻击力 = Integer.parseInt(全服发送装备装备攻击力.getText());
            }
            int 魔法力;
            if ("魔法力".equals(全服发送装备装备魔法力.getText())) {
                魔法力 = 0;
            } else {
                魔法力 = Integer.parseInt(全服发送装备装备魔法力.getText());
            }
            int 物理防御;
            if ("物理防御".equals(全服发送装备装备物理防御.getText())) {
                物理防御 = 0;
            } else {
                物理防御 = Integer.parseInt(全服发送装备装备物理防御.getText());
            }
            int 魔法防御;
            if ("魔法防御".equals(全服发送装备装备魔法防御.getText())) {
                魔法防御 = 0;
            } else {
                魔法防御 = Integer.parseInt(全服发送装备装备魔法防御.getText());
            }
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            MapleInventoryType type = GameConstants.getInventoryType(物品ID);
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (a == 1) {
                        if (1 >= 0) {
                            if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 1, "")) {
                                return;
                            }
                            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)
                                    || type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100) {
                                final Equip item = (Equip) (ii.getEquipById(物品ID));
                                if (ii.isCash(物品ID)) {
                                    item.setUniqueId(1);
                                }
                                if (力量 > 0 && 力量 <= 32767) {
                                    item.setStr((short) (力量));
                                }
                                if (敏捷 > 0 && 敏捷 <= 32767) {
                                    item.setDex((short) (敏捷));
                                }
                                if (智力 > 0 && 智力 <= 32767) {
                                    item.setInt((short) (智力));
                                }
                                if (运气 > 0 && 运气 <= 32767) {
                                    item.setLuk((short) (运气));
                                }
                                if (攻击力 > 0 && 攻击力 <= 32767) {
                                    item.setWatk((short) (攻击力));
                                }
                                if (魔法力 > 0 && 魔法力 <= 32767) {
                                    item.setMatk((short) (魔法力));
                                }
                                if (物理防御 > 0 && 物理防御 <= 32767) {
                                    item.setWdef((short) (物理防御));
                                }
                                if (魔法防御 > 0 && 魔法防御 <= 32767) {
                                    item.setMdef((short) (魔法防御));
                                }
                                if (HP > 0 && HP <= 30000) {
                                    item.setHp((short) (HP));
                                }
                                if (MP > 0 && MP <= 30000) {
                                    item.setMp((short) (MP));
                                }
                                if ("可以交易".equals(是否可以交易)) {
                                    short flag = item.getFlag();
                                    if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                        flag |= ItemFlag.KARMA_EQ.getValue();
                                    } else {
                                        flag |= ItemFlag.KARMA_USE.getValue();
                                    }
                                    item.setFlag((byte) flag);
                                }
                                if (给予时间 > 0) {
                                    item.setExpiration(System.currentTimeMillis() + (给予时间 * 24 * 60 * 60 * 1000));
                                }
                                if (可加卷次数 > 0) {
                                    item.setUpgradeSlots((byte) (可加卷次数));
                                }
                                if (制作人名字 != null) {
                                    item.setOwner(制作人名字);
                                }
                                final String name = ii.getName(物品ID);
                                if (物品ID / 10000 == 114 && name != null && name.length() > 0) { //medal
                                    final String msg = "你已获得称号 <" + name + ">";
                                    mch.getClient().getPlayer().dropMessage(5, msg);
                                }
                                MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                            } else {
                           //     MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 1, "", null, 给予时间, "");
                                MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 1, "", null, (byte) 0);

                            }
                        } else {
                            MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -1, true, false);
                        }
                        mch.getClient().getSession().write(MaplePacketCreator.getShowItemGain(物品ID, (short) 1, true));
                    } else if (mch.getName().equals(发送装备玩家姓名.getText())) {
                        if (1 >= 0) {
                            if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 1, "")) {
                                return;
                            }
                            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)
                                    || type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100) {
                                final Equip item = (Equip) (ii.getEquipById(物品ID));
                                if (ii.isCash(物品ID)) {
                                    item.setUniqueId(1);
                                }
                                if (力量 > 0 && 力量 <= 32767) {
                                    item.setStr((short) (力量));
                                }
                                if (敏捷 > 0 && 敏捷 <= 32767) {
                                    item.setDex((short) (敏捷));
                                }
                                if (智力 > 0 && 智力 <= 32767) {
                                    item.setInt((short) (智力));
                                }
                                if (运气 > 0 && 运气 <= 32767) {
                                    item.setLuk((short) (运气));
                                }
                                if (攻击力 > 0 && 攻击力 <= 32767) {
                                    item.setWatk((short) (攻击力));
                                }
                                if (魔法力 > 0 && 魔法力 <= 32767) {
                                    item.setMatk((short) (魔法力));
                                }
                                if (物理防御 > 0 && 物理防御 <= 32767) {
                                    item.setWdef((short) (物理防御));
                                }
                                if (魔法防御 > 0 && 魔法防御 <= 32767) {
                                    item.setMdef((short) (魔法防御));
                                }
                                if (HP > 0 && HP <= 30000) {
                                    item.setHp((short) (HP));
                                }
                                if (MP > 0 && MP <= 30000) {
                                    item.setMp((short) (MP));
                                }
                                if ("可以交易".equals(是否可以交易)) {
                                    short flag = item.getFlag();
                                    if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                        flag |= ItemFlag.KARMA_EQ.getValue();
                                    } else {
                                        flag |= ItemFlag.KARMA_USE.getValue();
                                    }
                                    item.setFlag((byte) flag);
                                }
                                if (给予时间 > 0) {
                                    item.setExpiration(System.currentTimeMillis() + (给予时间 * 24 * 60 * 60 * 1000));
                                }
                                if (可加卷次数 > 0) {
                                    item.setUpgradeSlots((byte) (可加卷次数));
                                }
                                if (制作人名字 != null) {
                                    item.setOwner(制作人名字);
                                }
                                final String name = ii.getName(物品ID);
                                if (物品ID / 10000 == 114 && name != null && name.length() > 0) { //medal
                                    final String msg = "你已获得称号 <" + name + ">";
                                    mch.getClient().getPlayer().dropMessage(5, msg);
                                }
                                MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                            } else {
                    //            MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 1, "", null, 给予时间, "");
                                MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 1, "", null, (byte) 0);
                            }
                        } else {
                            MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -1, true, false);
                        }
                        mch.getClient().getSession().write(MaplePacketCreator.getShowItemGain(物品ID, (short) 1, true));

                    }
                }
            }
            JOptionPane.showMessageDialog(null, "[信息]:发送成功。");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "[信息]:错误!" + e);
        }
    }
    private void 给予装备2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_给予装备2ActionPerformed
        刷装备2(1);
    }//GEN-LAST:event_给予装备2ActionPerformed
    private void 发送福利(int a) {
        boolean result1 = this.a1.getText().matches("[0-9]+");
        if (result1) {
            int 数量;
            if ("100000000".equals(a1.getText())) {
                数量 = 100;
            } else {
                数量 = Integer.parseInt(a1.getText());
            }
            if (数量 <= 0 || 数量 > 999999999) {
                return;
            }
            String 类型 = "";
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {

                    switch (a) {
                        case 1:
                            类型 = "点券";
                            mch.modifyCSPoints(1, 数量, true);
                            break;
                        case 2:
                            类型 = "抵用券";
                            mch.modifyCSPoints(2, 数量, true);
                            break;
                        case 3:
                            类型 = "金币";
                            mch.gainMeso(数量, true);
                            break;
                        case 4:
                            类型 = "经验";
                            mch.gainExp(数量, false, false, false);
                            break;
                        case 5:
                            类型 = "人气";
                            mch.addFame(数量);
                            break;
                        case 6:
                            类型 = "豆豆";
                            mch.gainBeans(数量);
                            break;
                        default:
                            break;
                    }
                    mch.startMapEffect("管理员发放 " + 数量 + " " + 类型 + "给在线的所有玩家！", 5121009);
                }
            }
            JOptionPane.showMessageDialog(null, "[信息]:发放 " + 数量 + " " + 类型 + "给在线的所有玩家。");
            a1.setText("");
            JOptionPane.showMessageDialog(null, "发送成功");
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入要发送数量。");
        }
    }
    private void z2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z2ActionPerformed
        发送福利(2);
    }//GEN-LAST:event_z2ActionPerformed

    private void z3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z3ActionPerformed
        发送福利(3);
    }//GEN-LAST:event_z3ActionPerformed

    private void z1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z1ActionPerformed
        发送福利(1);        // TODO add your handling code here:
    }//GEN-LAST:event_z1ActionPerformed

    private void z4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z4ActionPerformed
        发送福利(4);
    }//GEN-LAST:event_z4ActionPerformed

    private void z5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z5ActionPerformed
        发送福利(5);
    }//GEN-LAST:event_z5ActionPerformed

    private void z6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z6ActionPerformed
        发送福利(6);
    }//GEN-LAST:event_z6ActionPerformed

    private void a1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_a1ActionPerformed

    private void 个人发送物品数量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_个人发送物品数量ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_个人发送物品数量ActionPerformed

    private void 个人发送物品玩家名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_个人发送物品玩家名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_个人发送物品玩家名字ActionPerformed

    private void 个人发送物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_个人发送物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_个人发送物品代码ActionPerformed

    private void 给予物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_给予物品ActionPerformed
        刷物品();       // TODO add your handling code here:
    }//GEN-LAST:event_给予物品ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        try {

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE guilds SET GP =" + 家族GP.getText().toString() + " WHERE guildid = " + 家族ID.getText().toString() + "");
            ps.executeUpdate();
            ps.close();
            System.out.println("update guild gp !");
            刷新家族信息();
        } catch (SQLException ex) {
            ex.getStackTrace();
        }

    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed

        Runtime runtime = Runtime.getRuntime();
        int p = 0;
        for (handling.channel.ChannelServer cserv : handling.channel.ChannelServer.getAllInstances()) {
            p++;
            cserv.closeAllMerchants();
        }
        System.out.println("[保存雇佣商人系统] 雇佣商人保存" + p + "个频道成功。");
        JOptionPane.showMessageDialog(null, "雇佣商人保存" + p + "个频道成功。");
        int pp = 0;
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                pp++;
                chr.saveToDB(true, true);
            }
        }
        System.out.println("[保存数据系统] 保存" + p + "个成功。");
        JOptionPane.showMessageDialog(null, "保存数据成功。");
        try {

            runtime.exec("taskkill /im java.exe /f");
            runtime.exec("taskkill /im java.exe /f");
            runtime.exec("taskkill /im javax.exe /f");

        } catch (Exception e) {

            System.out.println("Error!");

        }

    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        openWindow(Windows.基址计算工具);
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        openWindow(Windows.包头转换工具);
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        openWindow(Windows.卡密制作工具);
    }//GEN-LAST:event_jButton25ActionPerformed
    public void 刷新反应堆() {
        for (int i = ((DefaultTableModel) (this.反应堆.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.反应堆.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM reactordrops ");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 反应堆.getModel()).insertRow(反应堆.getRowCount(), new Object[]{rs.getInt("reactordropid"), rs.getInt("reactorid"), rs.getInt("itemid"), rs.getInt("chance"), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))});
            }
        } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        反应堆.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 反应堆.getSelectedRow();
                String a = 反应堆.getValueAt(i, 0).toString();
                String a1 = 反应堆.getValueAt(i, 1).toString();
                String a2 = 反应堆.getValueAt(i, 2).toString();
                String a3 = 反应堆.getValueAt(i, 3).toString();
                反应堆序列号.setText(a);
                反应堆代码.setText(a1);
                反应堆物品.setText(a2);
                反应堆概率.setText(a3);
            }
        });
    }
    private void 删除反应堆物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除反应堆物品ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.删除反应堆物品代码.getText().matches("[0-9]+");

        if (result1) {
            if (Integer.parseInt(this.删除反应堆物品代码.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "请填写正确的值");
            }
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM reactordrops WHERE itemid = ?");
                ps1.setInt(1, Integer.parseInt(this.删除反应堆物品代码.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from reactordrops where itemid =" + Integer.parseInt(this.删除反应堆物品代码.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    JOptionPane.showMessageDialog(null, "成功删除 " + Integer.parseInt(this.删除反应堆物品代码.getText()) + " 物品，重载后生效。");
                    刷新反应堆();

                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你要删除的反应堆代码 ");
        }
    }//GEN-LAST:event_删除反应堆物品ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        刷新反应堆();
    }//GEN-LAST:event_jButton26ActionPerformed

    private void 反应堆概率ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_反应堆概率ActionPerformed

    }//GEN-LAST:event_反应堆概率ActionPerformed

    private void 新增反应堆物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_新增反应堆物品ActionPerformed

        boolean result2 = this.反应堆代码.getText().matches("[0-9]+");

        if (result2) {
            try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO reactordrops ( reactorid ,itemid ,chance ,questid ) VALUES ( ?, ?, ?, ?)")) {
                ps.setInt(1, Integer.parseInt(this.反应堆代码.getText()));
                ps.setInt(2, Integer.parseInt(this.反应堆物品.getText()));
                ps.setInt(3, Integer.parseInt(this.反应堆概率.getText()));
                ps.setInt(4, -1);
                ps.executeUpdate();
                刷新反应堆();
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入反应堆代码，物品代码，掉落概率 ");
        }
    }//GEN-LAST:event_新增反应堆物品ActionPerformed

    private void 删除反应堆物品1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除反应堆物品1ActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.反应堆序列号.getText().matches("[0-9]+");

        if (result1) {
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM reactordrops WHERE reactordropid = ?");
                ps1.setInt(1, Integer.parseInt(this.反应堆序列号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from reactordrops where reactordropid =" + Integer.parseInt(this.反应堆序列号.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新反应堆();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要删除的物品 ");
        }
    }//GEN-LAST:event_删除反应堆物品1ActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        boolean result2 = this.查找反应堆掉落.getText().matches("[0-9]+");
        if (result2) {
            for (int i = ((DefaultTableModel) (this.反应堆.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.反应堆.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;
                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM reactordrops WHERE reactorid = " + Integer.parseInt(查找反应堆掉落.getText()));
                rs = ps.executeQuery();
                while (rs.next()) {
                    ((DefaultTableModel) 反应堆.getModel()).insertRow(反应堆.getRowCount(), new Object[]{rs.getInt("reactordropid"), rs.getInt("reactorid"), rs.getInt("itemid"), rs.getInt("chance"), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))});
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你要查找的反应堆 ");
        }
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        boolean result2 = this.查找反应堆掉落.getText().matches("[0-9]+");
        if (result2) {
            for (int i = ((DefaultTableModel) (this.反应堆.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.反应堆.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;

                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM reactordrops WHERE itemid = " + Integer.parseInt(查找物品.getText()));
                rs = ps.executeQuery();
                while (rs.next()) {
                    ((DefaultTableModel) 反应堆.getModel()).insertRow(反应堆.getRowCount(), new Object[]{
                        rs.getInt("reactordropid"),
                        rs.getInt("reactorid"),
                        rs.getInt("itemid"),
                        rs.getInt("chance"),
                        MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                    });

                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你要查找的物品代码 ");
        }
    }//GEN-LAST:event_jButton37ActionPerformed

    private void 修改反应堆物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改反应堆物品ActionPerformed
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result = this.反应堆代码.getText().matches("[0-9]+");
        boolean result1 = this.反应堆物品.getText().matches("[0-9]+");
        boolean result2 = this.反应堆概率.getText().matches("[0-9]+");

        if (result && result1 && result2) {
            if (Integer.parseInt(this.反应堆代码.getText()) < 0 && Integer.parseInt(this.反应堆物品.getText()) < 0 && Integer.parseInt(this.反应堆概率.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "请填写正确的值");
            }
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE reactordrops SET reactorid = ?,itemid = ?,chance = ?WHERE reactordropid = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM reactordrops WHERE reactordropid = ?");
                ps1.setInt(1, Integer.parseInt(this.反应堆序列号.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString1 = null;
                    String sqlString2 = null;
                    String sqlString3 = null;
                    sqlString1 = "update reactordrops set reactorid='" + this.反应堆代码.getText() + "' where reactordropid=" + this.反应堆序列号.getText() + ";";
                    PreparedStatement itemid = DatabaseConnection.getConnection().prepareStatement(sqlString1);
                    itemid.executeUpdate(sqlString1);

                    sqlString2 = "update reactordrops set itemid='" + this.反应堆物品.getText() + "' where reactordropid=" + this.反应堆序列号.getText() + ";";
                    PreparedStatement price = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    price.executeUpdate(sqlString2);

                    sqlString3 = "update reactordrops set chance='" + this.反应堆概率.getText() + "' where reactordropid=" + this.反应堆序列号.getText() + ";";
                    PreparedStatement shopid = DatabaseConnection.getConnection().prepareStatement(sqlString3);
                    shopid.executeUpdate(sqlString3);

                    刷新反应堆();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请选择你要修改的数据");
        }
    }//GEN-LAST:event_修改反应堆物品ActionPerformed

    private void 查找道具ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查找道具ActionPerformed
        boolean result1 = this.游戏道具代码.getText().matches("[0-9]+");
        if (result1) {
            for (int i = ((DefaultTableModel) (this.游戏道具.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.游戏道具.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;

                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE itemid =" + Integer.parseInt(this.游戏道具代码.getText()) + "");
                rs = ps.executeQuery();
                while (rs.next()) {
                    ((DefaultTableModel) 游戏道具.getModel()).insertRow(游戏道具.getRowCount(), new Object[]{
                        rs.getInt("characterid"),
                        NPCConversationManager.角色ID取名字(rs.getInt("characterid")),
                        rs.getInt("itemid"),
                        MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                    });
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你要查找的<物品代码>");
        }
    }//GEN-LAST:event_查找道具ActionPerformed

    private void 删除道具ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除道具ActionPerformed
        String 输出 = "";
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.游戏道具代码.getText().matches("[0-9]+");

        if (result1) {
            try {
                for (int i = ((DefaultTableModel) (this.游戏道具.getModel())).getRowCount() - 1; i >= 0; i--) {
                    ((DefaultTableModel) (this.游戏道具.getModel())).removeRow(i);
                }
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM inventoryitems WHERE itemid = ?");
                ps1.setInt(1, Integer.parseInt(this.游戏道具代码.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from inventoryitems where itemid =" + Integer.parseInt(this.游戏道具代码.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    JOptionPane.showMessageDialog(null, "成功删除所有代码为 " + Integer.parseInt(this.游戏道具代码.getText()) + " 物品。");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你想要删除的<物品代码>");
        }
    }//GEN-LAST:event_删除道具ActionPerformed

    private void 查找道具1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查找道具1ActionPerformed
        boolean result1 = this.游戏道具代码.getText().matches("[0-9]+");
        if (result1) {
            for (int i = ((DefaultTableModel) (this.游戏道具.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.游戏道具.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;

                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM auctionitems WHERE itemid =" + Integer.parseInt(this.游戏道具代码.getText()) + "");
                rs = ps.executeQuery();
                while (rs.next()) {
                    ((DefaultTableModel) 游戏道具.getModel()).insertRow(游戏道具.getRowCount(), new Object[]{
                        rs.getInt("characterid"),
                        NPCConversationManager.角色ID取名字(rs.getInt("characterid")),
                        rs.getInt("itemid"),
                        MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                    });
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你要查找的<物品代码>");
        }
    }//GEN-LAST:event_查找道具1ActionPerformed

    private void 删除道具1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除道具1ActionPerformed
        String 输出 = "";
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.游戏道具代码.getText().matches("[0-9]+");
        if (result1) {
            try {
                for (int i = ((DefaultTableModel) (this.游戏道具.getModel())).getRowCount() - 1; i >= 0; i--) {
                    ((DefaultTableModel) (this.游戏道具.getModel())).removeRow(i);
                }
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM auctionitems WHERE itemid = ?");
                ps1.setInt(1, Integer.parseInt(this.游戏道具代码.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from auctionitems where itemid =" + Integer.parseInt(this.游戏道具代码.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    JOptionPane.showMessageDialog(null, "成功删除所有代码为 " + Integer.parseInt(this.游戏道具代码.getText()) + " 物品。");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你想要删除的<物品代码>");
        }
    }//GEN-LAST:event_删除道具1ActionPerformed

    private void 查找道具2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查找道具2ActionPerformed
        boolean result1 = this.游戏道具代码.getText().matches("[0-9]+");
        if (result1) {
            for (int i = ((DefaultTableModel) (this.游戏道具.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.游戏道具.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;
                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM auctionitems1 WHERE itemid =" + Integer.parseInt(this.游戏道具代码.getText()) + "");
                rs = ps.executeQuery();
                while (rs.next()) {
                    ((DefaultTableModel) 游戏道具.getModel()).insertRow(游戏道具.getRowCount(), new Object[]{
                        rs.getInt("characterid"),
                        NPCConversationManager.角色ID取名字(rs.getInt("characterid")),
                        rs.getInt("itemid"),
                        MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                    });
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你要查找的<物品代码>");
        }
    }//GEN-LAST:event_查找道具2ActionPerformed

    private void 删除道具2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除道具2ActionPerformed
        String 输出 = "";
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.游戏道具代码.getText().matches("[0-9]+");

        if (result1) {
            try {
                for (int i = ((DefaultTableModel) (this.游戏道具.getModel())).getRowCount() - 1; i >= 0; i--) {
                    ((DefaultTableModel) (this.游戏道具.getModel())).removeRow(i);
                }
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM auctionitems1 WHERE itemid = ?");
                ps1.setInt(1, Integer.parseInt(this.游戏道具代码.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from auctionitems1 where itemid =" + Integer.parseInt(this.游戏道具代码.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    JOptionPane.showMessageDialog(null, "成功删除所有代码为 " + Integer.parseInt(this.游戏道具代码.getText()) + " 物品。");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你想要删除的<物品代码>");
        }
    }//GEN-LAST:event_删除道具2ActionPerformed

    private void 查找道具3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查找道具3ActionPerformed
        boolean result1 = this.游戏道具代码.getText().matches("[0-9]+");
        if (result1) {
            for (int i = ((DefaultTableModel) (this.游戏道具.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.游戏道具.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;
                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM bank_item1 WHERE itemid =" + Integer.parseInt(this.游戏道具代码.getText()) + "");
                rs = ps.executeQuery();
                while (rs.next()) {
                    ((DefaultTableModel) 游戏道具.getModel()).insertRow(游戏道具.getRowCount(), new Object[]{
                        rs.getInt("cid"),
                        NPCConversationManager.角色ID取名字(rs.getInt("cid")),
                        rs.getInt("itemid"),
                        MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                    });
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你要查找的<物品代码>");
        }
    }//GEN-LAST:event_查找道具3ActionPerformed

    private void 删除道具3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除道具3ActionPerformed
        String 输出 = "";
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.游戏道具代码.getText().matches("[0-9]+");
        if (result1) {
            try {
                for (int i = ((DefaultTableModel) (this.游戏道具.getModel())).getRowCount() - 1; i >= 0; i--) {
                    ((DefaultTableModel) (this.游戏道具.getModel())).removeRow(i);
                }
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM bank_item1 WHERE itemid = ?");
                ps1.setInt(1, Integer.parseInt(this.游戏道具代码.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from bank_item1 where itemid =" + Integer.parseInt(this.游戏道具代码.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    JOptionPane.showMessageDialog(null, "成功删除所有代码为 " + Integer.parseInt(this.游戏道具代码.getText()) + " 物品。");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你想要删除的<物品代码>");
        }
    }//GEN-LAST:event_删除道具3ActionPerformed

    private void 查找道具4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查找道具4ActionPerformed
        boolean result1 = this.游戏道具代码.getText().matches("[0-9]+");
        if (result1) {
            for (int i = ((DefaultTableModel) (this.游戏道具.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.游戏道具.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;

                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM bank_item WHERE itemid =" + Integer.parseInt(this.游戏道具代码.getText()) + "");
                rs = ps.executeQuery();
                while (rs.next()) {
                    ((DefaultTableModel) 游戏道具.getModel()).insertRow(游戏道具.getRowCount(), new Object[]{
                        rs.getInt("cid"),
                        NPCConversationManager.角色ID取名字(rs.getInt("cid")),
                        rs.getInt("itemid"),
                        MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                    });
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你要查找的<物品代码>");
        }
    }//GEN-LAST:event_查找道具4ActionPerformed

    private void 删除道具4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除道具4ActionPerformed
        String 输出 = "";
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.游戏道具代码.getText().matches("[0-9]+");

        if (result1) {
            try {
                for (int i = ((DefaultTableModel) (this.游戏道具.getModel())).getRowCount() - 1; i >= 0; i--) {
                    ((DefaultTableModel) (this.游戏道具.getModel())).removeRow(i);
                }
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM bank_item WHERE itemid = ?");
                ps1.setInt(1, Integer.parseInt(this.游戏道具代码.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from bank_item where itemid =" + Integer.parseInt(this.游戏道具代码.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    JOptionPane.showMessageDialog(null, "成功删除所有代码为 " + Integer.parseInt(this.游戏道具代码.getText()) + " 物品。");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入你想要删除的<物品代码>");
        }
    }//GEN-LAST:event_删除道具4ActionPerformed

    private void 刷新封IPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新封IPActionPerformed
        刷新封IP();
    }//GEN-LAST:event_刷新封IPActionPerformed

    private void 刷新封MACActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新封MACActionPerformed
        刷新封MAC();
    }//GEN-LAST:event_刷新封MACActionPerformed
   public void 刷新封MAC() {
        for (int i = ((DefaultTableModel) (this.封MAC.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.封MAC.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM macbans");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 封MAC.getModel()).insertRow(封MAC.getRowCount(), new Object[]{
                    rs.getInt("macbanid"),
                    rs.getString("mac"),
                    NPCConversationManager.MAC取账号(rs.getString("mac"))
                });
            }
        } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void 刷新封IP() {
        for (int i = ((DefaultTableModel) (this.封IP.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.封IP.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM ipbans");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 封IP.getModel()).insertRow(封IP.getRowCount(), new Object[]{
                    rs.getInt("ipbanid"),
                    rs.getString("ip"),
                    NPCConversationManager.IP取账号(rs.getString("ip"))
                });
            }
        } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void 删除MACActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除MACActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.删MAC代码.getText().matches("[0-9]+");

        if (result1) {
            if (Integer.parseInt(this.删MAC代码.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "请填写正确的值");
            }
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM macbans WHERE macbanid = ?");
                ps1.setInt(1, Integer.parseInt(this.删MAC代码.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from macbans where macbanid =" + Integer.parseInt(this.删MAC代码.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新封MAC();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入数字 ");
        }
    }//GEN-LAST:event_删除MACActionPerformed

    private void 删除IPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除IPActionPerformed
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        boolean result1 = this.删除IP代码.getText().matches("[0-9]+");

        if (result1) {
            if (Integer.parseInt(this.删除IP代码.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "请填写正确的值");
            }
            try {

                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM ipbans WHERE ipbanid = ?");
                ps1.setInt(1, Integer.parseInt(this.删除IP代码.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from ipbans where ipbanid =" + Integer.parseInt(this.删除IP代码.getText()) + "";
                    ps1.executeUpdate(sqlstr);
                    刷新封IP();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请输入数字 ");
        }
    }//GEN-LAST:event_删除IPActionPerformed

    private void jButton69ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton69ActionPerformed
        runTool(Tools.DumpItems);  
    }//GEN-LAST:event_jButton69ActionPerformed

    private void jButton70ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton70ActionPerformed
        runTool(Tools.MonsterDropCreator);
    }//GEN-LAST:event_jButton70ActionPerformed

    private void jButton72ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton72ActionPerformed
        runTool(Tools.DumpNpcNames);  
    }//GEN-LAST:event_jButton72ActionPerformed

    private void jButton73ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton73ActionPerformed
          runTool(Tools.DumpMobSkills); 
    }//GEN-LAST:event_jButton73ActionPerformed

    private void jButton74ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton74ActionPerformed
        runTool(Tools.DumpOxQuizData);  
    }//GEN-LAST:event_jButton74ActionPerformed

    private void jButton75ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton75ActionPerformed
        runTool(Tools.DumpQuests);  
    }//GEN-LAST:event_jButton75ActionPerformed

    private void 开启双倍经验ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启双倍经验ActionPerformed
          boolean result1 = this.双倍经验持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (双倍经验持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始经验 = Integer.parseInt(ServerProperties.getProperty("ZeroMS.expRate"));
            int 双倍经验活动 = 原始经验 * 2;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.双倍经验持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "经验";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(双倍经验活动);
            }
            World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, 20, "[倍率活动] : 游戏开始 2 倍打怪经验活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启双倍经验活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启双倍经验ActionPerformed

    private void 开启双倍爆率ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启双倍爆率ActionPerformed
        boolean result1 = this.双倍爆率持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (双倍爆率持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始爆率 = Integer.parseInt(ServerProperties.getProperty("ZeroMS.dropRate"));
            int 双倍爆率活动 = 原始爆率 * 2;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.双倍经验持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "爆率";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(双倍爆率活动);
            }
            World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, 20, "[倍率活动] : 游戏开始 2 倍打怪爆率活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启双倍爆率活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启双倍爆率ActionPerformed

    private void 开启双倍金币ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启双倍金币ActionPerformed
        boolean result1 = this.双倍金币持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (双倍金币持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始金币 = Integer.parseInt(ServerProperties.getProperty("ZeroMS.mesoRate"));
            int 双倍金币活动 = 原始金币 * 2;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.双倍金币持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "金币";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(双倍金币活动);
            }
            World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, 20, "[倍率活动] : 游戏开始 2 倍打怪金币活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启双倍金币活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启双倍金币ActionPerformed

    private void 开启三倍经验ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启三倍经验ActionPerformed
        boolean result1 = this.三倍经验持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (三倍经验持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始经验 = Integer.parseInt(ServerProperties.getProperty("ZeroMS.expRate"));
            int 三倍经验活动 = 原始经验 * 3;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.三倍经验持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "经验";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(三倍经验活动);
            }
            World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, 20, "[倍率活动] : 游戏开始 3 倍打怪经验活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启三倍经验活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启三倍经验ActionPerformed

    private void 开启三倍爆率ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启三倍爆率ActionPerformed
         boolean result1 = this.三倍爆率持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (三倍爆率持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始爆率 = Integer.parseInt(ServerProperties.getProperty("ZeroMS.dropRate"));
            int 三倍爆率活动 = 原始爆率 * 3;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.三倍经验持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "爆率";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(三倍爆率活动);
            }
            World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, 20, "[倍率活动] : 游戏开始 3 倍打怪爆率活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启三倍爆率活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启三倍爆率ActionPerformed

    private void 开启三倍金币ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启三倍金币ActionPerformed
        boolean result1 = this.三倍金币持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (三倍金币持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始金币 = Integer.parseInt(ServerProperties.getProperty("ZeroMS.mesoRate"));
            int 三倍金币活动 = 原始金币 * 3;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.三倍金币持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "金币";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(三倍金币活动);
            }
            World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, 20, "[倍率活动] : 游戏开始 3 倍打怪金币活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启三倍金币活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启三倍金币ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        openWindow(Windows.角色转移工具); 
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        openWindow(Windows.一键还原); 
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        openWindow(Windows.任务控制台); 
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        openWindow(Windows.锻造控制台); 
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        openWindow(Windows.副本控制台); 
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        查找账号();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void 解卡ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_解卡ActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        if (账号操作.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入需要解卡的账号 ");
            return;
        }
        String account = 账号操作.getText();
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps;

            ps = con.prepareStatement("Update accounts set loggedin = ? Where name = ?");
            ps.setInt(1, 0);
            ps.setString(2, account);
            ps.execute();
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
        账号提示语言.setText("[信息]:解卡账号 " + this.账号操作.getText() + " 成功。");
        刷新账号信息();
    }//GEN-LAST:event_解卡ActionPerformed

    private void 封锁账号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_封锁账号ActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        if (账号操作.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入需要封锁的账号 ");
            return;
        }
        String account = 账号操作.getText();
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps;

            ps = con.prepareStatement("Update accounts set banned = ? Where name = ?");
            ps.setInt(1, 1);
            ps.setString(2, account);
            ps.execute();
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
        账号提示语言.setText("[信息]:封锁账号 " + this.账号操作.getText() + " 成功。");
        刷新账号信息();
    }//GEN-LAST:event_封锁账号ActionPerformed

    private void 删除账号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_删除账号ActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        try {
            int n = JOptionPane.showConfirmDialog(this, "你确定要删除这个账号吗？", "信息", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM accounts ");
                //ps1.setInt(1, Integer.parseInt(this.账号信息.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " Delete from accounts where name ='" + this.账号操作.getText() + "'";
                    账号提示语言.setText("[信息]:删除账号 " + this.账号操作.getText() + " 成功。");
                    ps1.executeUpdate(sqlstr);
                    刷新账号信息();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_删除账号ActionPerformed

    private void 在线账号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_在线账号ActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        账号提示语言.setText("[信息]:显示游戏所有在线玩家账号信息。");
        for (int i = ((DefaultTableModel) (this.账号信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.账号信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM accounts  WHERE loggedin > 0 ");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 封号 = "";
                if (rs.getInt("banned") == 0) {
                    封号 = "正常";
                } else {
                    封号 = "封禁";
                }
                String 在线 = "";
                if (rs.getInt("loggedin") == 0) {
                    在线 = "不在线";
                } else {
                    在线 = "在线";
                }
                String QQ = "";
                if (rs.getString("qq") != null) {
                    QQ = rs.getString("qq");
                } else {
                    QQ = "未绑定QQ";
                }
                ((DefaultTableModel) 账号信息.getModel()).insertRow(账号信息.getRowCount(), new Object[]{
                    rs.getInt("id"), //账号ID
                    rs.getString("name"), //账号
                    rs.getString("SessionIP"), //账号IP地址
                    rs.getString("macs"), ///账号MAC地址
                    QQ,//注册时间
                    rs.getInt("ACash"),//点券
                    rs.getInt("mPoints"),//抵用
                    rs.getString("lastlogin"),//最近上线
                    在线,
                    封号,
                    rs.getInt("gm")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        读取显示账号();
    }//GEN-LAST:event_在线账号ActionPerformed

    private void 已封账号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_已封账号ActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        账号提示语言.setText("[信息]:显示游戏所有已被封禁的玩家账号信息。");
        for (int i = ((DefaultTableModel) (this.账号信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.账号信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM accounts WHERE banned > 0 ");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 封号 = "";
                if (rs.getInt("banned") == 0) {
                    封号 = "正常";
                } else {
                    封号 = "封禁";
                }
                String 在线 = "";
                if (rs.getInt("loggedin") == 0) {
                    在线 = "不在线";
                } else {
                    在线 = "在线";
                }
                String QQ = "";
                if (rs.getString("qq") != null) {
                    QQ = rs.getString("qq");
                } else {
                    QQ = "未绑定QQ";
                }
                ((DefaultTableModel) 账号信息.getModel()).insertRow(账号信息.getRowCount(), new Object[]{
                    rs.getInt("id"), //账号ID
                    rs.getString("name"), //账号
                    rs.getString("SessionIP"), //账号IP地址
                    rs.getString("macs"), ///账号MAC地址
                    QQ,//注册时间
                    rs.getInt("ACash"),//点券
                    rs.getInt("mPoints"),//抵用
                    rs.getString("lastlogin"),//最近上线
                    在线,
                    封号,
                    rs.getInt("gm")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        读取显示账号();
    }//GEN-LAST:event_已封账号ActionPerformed

    private void 解封ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_解封ActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        if (账号操作.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入需要解封的账号 ");
            return;
        }
        String account = 账号操作.getText();
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps;

            ps = con.prepareStatement("Update accounts set banned = ? Where name = ?");
            ps.setInt(1, 0);
            ps.setString(2, account);
            ps.execute();
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
        账号提示语言.setText("[信息]:解封账号 " + account + " 成功。");
        //JOptionPane.showMessageDialog(null, "账号解封成功");
        刷新账号信息();
    }//GEN-LAST:event_解封ActionPerformed

    private void 离线账号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_离线账号ActionPerformed
        显示在线账号.setText("账号在线; " + 在线账号() + "");
        账号提示语言.setText("[信息]:显示游戏所有离线玩家账号信息。");
        for (int i = ((DefaultTableModel) (this.账号信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.账号信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM accounts  WHERE loggedin = 0 ");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 封号 = "";
                if (rs.getInt("banned") == 0) {
                    封号 = "正常";
                } else {
                    封号 = "封禁";
                }
                String 在线 = "";
                if (rs.getInt("loggedin") == 0) {
                    在线 = "不在线";
                } else {
                    在线 = "在线";
                }
                String QQ = "";
                if (rs.getString("qq") != null) {
                    QQ = rs.getString("qq");
                } else {
                    QQ = "未绑定QQ";
                }
                ((DefaultTableModel) 账号信息.getModel()).insertRow(账号信息.getRowCount(), new Object[]{
                    rs.getInt("id"), //账号ID
                    rs.getString("name"), //账号
                    rs.getString("SessionIP"), //账号IP地址
                    rs.getString("macs"), ///账号MAC地址
                    QQ,//注册时间
                    rs.getInt("ACash"),//点券
                    rs.getInt("mPoints"),//抵用
                    rs.getString("lastlogin"),//最近上线
                    在线,
                    封号,
                    rs.getInt("gm")
                });

            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        读取显示账号();
    }//GEN-LAST:event_离线账号ActionPerformed

    private void 刷新账号信息ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_刷新账号信息ActionPerformed
        账号提示语言.setText("[信息]:显示游戏所有玩家账号信息。");
        刷新账号信息();
        显示在线账号.setText("账号在线; " + 在线账号() + "");
    }//GEN-LAST:event_刷新账号信息ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        ChangePassWord();
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        注册新账号();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton35ActionPerformed

    private void 注册的密码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_注册的密码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_注册的密码ActionPerformed

    private void 注册的账号ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_注册的账号ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_注册的账号ActionPerformed

    private void 修改账号点券抵用ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改账号点券抵用ActionPerformed
        boolean result1 = this.点券.getText().matches("[0-9]+");
        boolean result2 = this.抵用.getText().matches("[0-9]+");
        boolean result3 = this.管理1.getText().matches("[0-9]+");
        boolean result4 = this.QQ.getText().matches("[0-9]+");
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        if (result1 && result2 && result3 && result4) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE accounts SET ACash = ?, mPoints = ?, gm = ?, qq = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM accounts  WHERE id = ? ");
                ps1.setInt(1, Integer.parseInt(this.账号ID.getText()));
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    String sqlString3 = null;
                    String sqlString4 = null;
                    String sqlString5 = null;
                    sqlString2 = "update accounts set ACash=" + Integer.parseInt(this.点券.getText()) + " where id ='" + Integer.parseInt(this.账号ID.getText()) + "';";
                    PreparedStatement priority = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    priority.executeUpdate(sqlString2);
                    sqlString3 = "update accounts set mPoints=" + Integer.parseInt(this.抵用.getText()) + " where id='" + Integer.parseInt(this.账号ID.getText()) + "';";
                    PreparedStatement period = DatabaseConnection.getConnection().prepareStatement(sqlString3);
                    period.executeUpdate(sqlString3);
                    sqlString4 = "update accounts set gm=" + Integer.parseInt(this.管理1.getText()) + " where id='" + Integer.parseInt(this.账号ID.getText()) + "';";
                    PreparedStatement gm = DatabaseConnection.getConnection().prepareStatement(sqlString4);
                    gm.executeUpdate(sqlString4);
                    sqlString5 = "update accounts set qq=" + Integer.parseInt(this.QQ.getText()) + " where id='" + Integer.parseInt(this.账号ID.getText()) + "';";
                    PreparedStatement qq = DatabaseConnection.getConnection().prepareStatement(sqlString5);
                    qq.executeUpdate(sqlString5);
                    刷新账号信息();
                    账号提示语言.setText("[信息]:修改账号 " + this.账号操作.getText() + " / 点券→" + Integer.parseInt(this.点券.getText()) + " / 抵用券→" + Integer.parseInt(this.抵用.getText()) + " 成功。");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            账号提示语言.setText("[信息]:请选择要修改的账号,数据不能为空，或者数值填写不对。");
        }
    }//GEN-LAST:event_修改账号点券抵用ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        openWindow(Windows.删除自添加NPC工具);
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        openWindow(Windows.野外BOSS刷新时间); 
    }//GEN-LAST:event_jButton40ActionPerformed

    private void 蓝蜗牛开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_蓝蜗牛开关ActionPerformed
        按键开关("蓝蜗牛开关", 2200);
        刷新蓝蜗牛开关();
        JOptionPane.showMessageDialog(null, "[信息]:修改成功!");
    }//GEN-LAST:event_蓝蜗牛开关ActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        openWindow(Windows.药水冷却时间控制台);
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        openWindow(Windows.金锤子成功率控制台);
    }//GEN-LAST:event_jButton42ActionPerformed

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        openWindow(Windows.永恒重生装备控制台);
    }//GEN-LAST:event_jButton43ActionPerformed

    private void 修改背包扩充价格ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_修改背包扩充价格ActionPerformed
        boolean result1 = this.商城扩充价格修改.getText().matches("[0-9]+");
        if (result1) {
            if (Integer.parseInt(this.商城扩充价格修改.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "[信息]:请输入正确的修改值。");
                return;
            }
            PreparedStatement ps1 = null;
            ResultSet rs = null;
            try {
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");
                ps1.setInt(1, 1);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlstr = " delete from configvalues where id =999";
                    ps1.executeUpdate(sqlstr);

                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeMS.class
                    .getName()).log(Level.SEVERE, null, ex);
            }
            try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO configvalues (id, name,Val) VALUES ( ?, ?, ?)")) {
                ps.setInt(1, 999);
                ps.setString(2, "商城扩充价格");
                ps.setInt(3, Integer.parseInt(this.商城扩充价格修改.getText()));
                ps.executeUpdate();
                刷新商城扩充价格();
                server.Start.GetConfigValues();
                JOptionPane.showMessageDialog(null, "[信息]:商城扩充背包价格修改成功，已经生效。");

            } catch (SQLException ex) {
                Logger.getLogger(JokeMS.class
                    .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_修改背包扩充价格ActionPerformed

    private void jButton44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton44ActionPerformed
        openWindow(Windows.游戏抽奖工具);
    }//GEN-LAST:event_jButton44ActionPerformed

    private void jButton45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton45ActionPerformed
        sendNotice(4);
        System.out.println("[公告系统] 发送公告成功！");
        JOptionPane.showMessageDialog(null, "发送公告成功！");
    }//GEN-LAST:event_jButton45ActionPerformed

    private void 公告发布喇叭代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_公告发布喇叭代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_公告发布喇叭代码ActionPerformed

    private void sendNpcTalkNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendNpcTalkNoticeActionPerformed
        sendNotice(3);
        System.out.println("[公告系统] 发送黄色滚动公告成功！");
        JOptionPane.showMessageDialog(null, "发送黄色滚动公告成功！");
    }//GEN-LAST:event_sendNpcTalkNoticeActionPerformed

    private void sendMsgNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMsgNoticeActionPerformed
        sendNotice(2);
        System.out.println("[公告系统] 发送红色提示公告成功！");
        JOptionPane.showMessageDialog(null, "发送红色提示公告成功！");
    }//GEN-LAST:event_sendMsgNoticeActionPerformed

    private void sendWinNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendWinNoticeActionPerformed
        sendNotice(1);
        System.out.println("[公告系统] 发送弹窗公告成功！");
        JOptionPane.showMessageDialog(null, "发送弹窗公告成功！");
    }//GEN-LAST:event_sendWinNoticeActionPerformed

    private void sendNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendNoticeActionPerformed
        sendNotice(0);
        System.out.println("[公告系统] 发送蓝色公告事项公告成功！");
        JOptionPane.showMessageDialog(null, "发送蓝色公告事项公告成功！");
    }//GEN-LAST:event_sendNoticeActionPerformed

    private void 测试刷新图片按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_测试刷新图片按钮ActionPerformed
        for (int i = ((DefaultTableModel) (this.测试显示图片.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.测试显示图片.getModel())).removeRow(i);
        }
        try {//方法连接到数据库并执行一个查询，从drop_data表中选择itemid在2380000和2390000之间的所有记录
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM drop_data WHERE itemid >=2380000&& itemid <2390000");
            rs = ps.executeQuery();
            //它遍历所有返回的记录，将它们插入到"怪物爆物"的表格中。每一行都包含五个字段："序列号""怪物代码""物品代码""爆率"和"物品名字"
            while (rs.next()) {
                ((DefaultTableModel) 测试显示图片.getModel()).insertRow(测试显示图片.getRowCount(), new Object[]{
                    //读取的是数据库的数据
                    rs.getInt("id"), //序列号
                    rs.getInt("dropperid"), //怪物代码
                    //物品名字是通过另一个方法获得的。读取WZ的数据
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))  //物品名字
                        //测试怪物图片
                        //测试怪物名字
                });
            }//如果在执行查询时发生了错误（SQLException），它将记录到日志中
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
           
            
    }//GEN-LAST:event_测试刷新图片按钮ActionPerformed
/*测试标记*/

    
    
    public void 刷新商城扩充价格() {
        for (int i = ((DefaultTableModel) (this.商城扩充价格.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.商城扩充价格.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM configvalues WHERE id = 999 ");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 商城扩充价格.getModel()).insertRow(商城扩充价格.getRowCount(), new Object[]{rs.getString("Val")});

            }
        } catch (SQLException ex) {
            Logger.getLogger(JokeMS.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
    public void 按键开关(String a, int b) {
        int 检测开关 = JokeMS.ConfigValuesMap.get(a);
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        if (检测开关 > 0) {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");
                ps1.setInt(1, b);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    String sqlString3 = null;
                    String sqlString4 = null;
                    sqlString2 = "update configvalues set Val= '0' where id= '" + b + "';";
                    PreparedStatement dropperid = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    dropperid.executeUpdate(sqlString2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                ps = DatabaseConnection.getConnection().prepareStatement("UPDATE configvalues SET Val = ? WHERE id = ?");
                ps1 = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM configvalues WHERE id = ?");
                ps1.setInt(1, b);
                rs = ps1.executeQuery();
                if (rs.next()) {
                    String sqlString2 = null;
                    String sqlString3 = null;
                    String sqlString4 = null;
                    sqlString2 = "update configvalues set Val= '1' where id='" + b + "';";
                    PreparedStatement dropperid = DatabaseConnection.getConnection().prepareStatement(sqlString2);
                    dropperid.executeUpdate(sqlString2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JokeMS.GetConfigValues();
    }
    
    private void 刷物品() {
        try {
            String 名字;
            if ("玩家名字".equals(个人发送物品玩家名字.getText())) {
                名字 = "";
            } else {
                名字 = 个人发送物品玩家名字.getText();
            }
            int 物品ID;
            if ("物品ID".equals(个人发送物品代码.getText())) {
                物品ID = 0;
            } else {
                物品ID = Integer.parseInt(个人发送物品代码.getText());
            }
            int 数量;
            if ("数量".equals(个人发送物品数量.getText())) {
                数量 = 0;
            } else {
                数量 = Integer.parseInt(个人发送物品数量.getText());
            }
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            MapleInventoryType type = GameConstants.getInventoryType(物品ID);
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (mch.getName().equals(名字)) {
                        if (数量 >= 0) {
                            if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 数量, "")) {
                                return;
                            }
                            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)
                                    || type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100) {
                                final Equip item = (Equip) (ii.getEquipById(物品ID));
                                if (ii.isCash(物品ID)) {
                                    item.setUniqueId(1);
                                }
                                final String name = ii.getName(物品ID);
                                if (物品ID / 10000 == 114 && name != null && name.length() > 0) { //medal
                                    final String msg = "你已获得称号 <" + name + ">";
                                    mch.getClient().getPlayer().dropMessage(5, msg);
                                }
                                MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                            } else {
                                MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 数量, "", null, (byte) 0);
                            }
                        } else {
                            MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -数量, true, false);
                        }
                        mch.getClient().getSession().write(MaplePacketCreator.getShowItemGain(物品ID, (short) 数量, true));

                    }
                }
            }
            个人发送物品玩家名字.setText("");
            个人发送物品代码.setText("");
            个人发送物品数量.setText("");
            JOptionPane.showMessageDialog(null, "[信息]:发送成功。");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "[信息]:错误!" + e);
        }
    }

    public void 读取显示账号() {
        账号信息.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 账号信息.getSelectedRow();
                String a = 账号信息.getValueAt(i, 0).toString();
                String a1 = 账号信息.getValueAt(i, 1).toString();
                String a2 = 账号信息.getValueAt(i, 5).toString();
                String a3 = 账号信息.getValueAt(i, 6).toString();
                //if (账号信息.getValueAt(i, 4).toString() != null) {
                String a4 = 账号信息.getValueAt(i, 4).toString();
                QQ.setText(a4);
                //}
                String a10 = 账号信息.getValueAt(i, 10).toString();
                账号ID.setText(a);
                账号操作.setText(a1);
                账号.setText(a1);

                点券.setText(a2);
                抵用.setText(a3);
                管理1.setText(a10);
                账号提示语言.setText("[信息]:显示账号 " + 账号.getText() + " 下角色信息。");
                刷新角色信息2();
            }
        });
    }

    public static int 在线账号() {
        int data = 0;
        int p = 0;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT loggedin as DATA FROM accounts WHERE loggedin > 0");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data = rs.getInt("DATA");
                    p += 1;
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("在线账号、出错");
        }
        return p;
    }

    private void 刷新地图刷新频率() {
        int 显示 = gui.JokeMS.ConfigValuesMap.get("地图刷新频率");
        地图刷新频率.setText("" + 显示 + "");
    }


    /**
     * @param args the command line arguments
     */
    private static long starttime = 0;

    public static void main(String args[]) throws InterruptedException {
    
        starttime = System.currentTimeMillis();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JokeMS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JokeMS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JokeMS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JokeMS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                instance = new JokeMS();
                instance.setVisible(true);
            }
        });
    }
    public class newOutputStream extends OutputStream {

        @Override
        public void write(int arg) throws IOException {
            // 忽略
        }

        @Override
        public void write(byte data[]) throws IOException {
            consoleInfo.append(new String(data));
        }

        @Override
        public void write(byte data[], int off, int len) throws IOException {
            if (buffline >= 5000) {
                consoleInfo.setText("");
                buffline = 0;
            }
            consoleInfo.append(new String(data, off, len));
            consoleInfo.setCaretPosition(consoleInfo.getText().length());
            buffline++;
        }
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ActiveThread;
    private javax.swing.JTextField GM;
    private javax.swing.JTextField HP;
    private javax.swing.JTextField MP;
    private javax.swing.JTextField QQ;
    private javax.swing.JLabel RunStats;
    private javax.swing.JLabel RunTime;
    private javax.swing.JTextField a1;
    private javax.swing.JTable charTable;
    private javax.swing.JTextArea consoleInfo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton69;
    private javax.swing.JButton jButton70;
    private javax.swing.JButton jButton72;
    private javax.swing.JButton jButton73;
    private javax.swing.JButton jButton74;
    private javax.swing.JButton jButton75;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel184;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel192;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
    private javax.swing.JLabel jLabel205;
    private javax.swing.JLabel jLabel206;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel210;
    private javax.swing.JLabel jLabel211;
    private javax.swing.JLabel jLabel212;
    private javax.swing.JLabel jLabel213;
    private javax.swing.JLabel jLabel214;
    private javax.swing.JLabel jLabel217;
    private javax.swing.JLabel jLabel219;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel221;
    private javax.swing.JLabel jLabel222;
    private javax.swing.JLabel jLabel223;
    private javax.swing.JLabel jLabel224;
    private javax.swing.JLabel jLabel225;
    private javax.swing.JLabel jLabel226;
    private javax.swing.JLabel jLabel227;
    private javax.swing.JLabel jLabel228;
    private javax.swing.JLabel jLabel229;
    private javax.swing.JLabel jLabel230;
    private javax.swing.JLabel jLabel231;
    private javax.swing.JLabel jLabel232;
    private javax.swing.JLabel jLabel233;
    private javax.swing.JLabel jLabel234;
    private javax.swing.JLabel jLabel235;
    private javax.swing.JLabel jLabel240;
    private javax.swing.JLabel jLabel241;
    private javax.swing.JLabel jLabel242;
    private javax.swing.JLabel jLabel244;
    private javax.swing.JLabel jLabel246;
    private javax.swing.JLabel jLabel259;
    private javax.swing.JLabel jLabel268;
    private javax.swing.JLabel jLabel269;
    private javax.swing.JLabel jLabel270;
    private javax.swing.JLabel jLabel271;
    private javax.swing.JLabel jLabel272;
    private javax.swing.JLabel jLabel273;
    private javax.swing.JLabel jLabel274;
    private javax.swing.JLabel jLabel275;
    private javax.swing.JLabel jLabel276;
    private javax.swing.JLabel jLabel277;
    private javax.swing.JLabel jLabel278;
    private javax.swing.JLabel jLabel279;
    private javax.swing.JLabel jLabel280;
    private javax.swing.JLabel jLabel281;
    private javax.swing.JLabel jLabel282;
    private javax.swing.JLabel jLabel283;
    private javax.swing.JLabel jLabel287;
    private javax.swing.JLabel jLabel288;
    private javax.swing.JLabel jLabel289;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel290;
    private javax.swing.JLabel jLabel291;
    private javax.swing.JLabel jLabel292;
    private javax.swing.JLabel jLabel293;
    private javax.swing.JLabel jLabel294;
    private javax.swing.JLabel jLabel295;
    private javax.swing.JLabel jLabel296;
    private javax.swing.JLabel jLabel297;
    private javax.swing.JLabel jLabel298;
    private javax.swing.JLabel jLabel299;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel300;
    private javax.swing.JLabel jLabel301;
    private javax.swing.JLabel jLabel302;
    private javax.swing.JLabel jLabel303;
    private javax.swing.JLabel jLabel304;
    private javax.swing.JLabel jLabel305;
    private javax.swing.JLabel jLabel306;
    private javax.swing.JLabel jLabel307;
    private javax.swing.JLabel jLabel308;
    private javax.swing.JLabel jLabel309;
    private javax.swing.JLabel jLabel310;
    private javax.swing.JLabel jLabel311;
    private javax.swing.JLabel jLabel312;
    private javax.swing.JLabel jLabel313;
    private javax.swing.JLabel jLabel314;
    private javax.swing.JLabel jLabel316;
    private javax.swing.JLabel jLabel319;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel320;
    private javax.swing.JLabel jLabel321;
    private javax.swing.JLabel jLabel323;
    private javax.swing.JLabel jLabel324;
    private javax.swing.JLabel jLabel325;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel337;
    private javax.swing.JLabel jLabel338;
    private javax.swing.JLabel jLabel339;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel340;
    private javax.swing.JLabel jLabel341;
    private javax.swing.JLabel jLabel342;
    private javax.swing.JLabel jLabel343;
    private javax.swing.JLabel jLabel344;
    private javax.swing.JLabel jLabel345;
    private javax.swing.JLabel jLabel346;
    private javax.swing.JLabel jLabel347;
    private javax.swing.JLabel jLabel348;
    private javax.swing.JLabel jLabel349;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel353;
    private javax.swing.JLabel jLabel354;
    private javax.swing.JLabel jLabel355;
    private javax.swing.JLabel jLabel356;
    private javax.swing.JLabel jLabel357;
    private javax.swing.JLabel jLabel359;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel360;
    private javax.swing.JLabel jLabel361;
    private javax.swing.JLabel jLabel362;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel76;
    private javax.swing.JPanel jPanel77;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane107;
    private javax.swing.JScrollPane jScrollPane108;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane132;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane8;
    private javax.swing.JTextField noticeText;
    public static javax.swing.JTextPane output_jTextPane1;
    private javax.swing.JButton sendMsgNotice;
    private javax.swing.JButton sendNotice;
    private javax.swing.JButton sendNpcTalkNotice;
    private javax.swing.JButton sendWinNotice;
    private javax.swing.JButton z1;
    private javax.swing.JButton z2;
    private javax.swing.JButton z3;
    private javax.swing.JButton z4;
    private javax.swing.JButton z5;
    private javax.swing.JButton z6;
    private javax.swing.JTextField 三倍爆率持续时间;
    private javax.swing.JTextField 三倍经验持续时间;
    private javax.swing.JTextField 三倍金币持续时间;
    private javax.swing.JButton 上衣;
    private javax.swing.JTable 世界爆物;
    private javax.swing.JTextField 世界爆物名称;
    private javax.swing.JTextField 世界爆物序列号;
    private javax.swing.JTextField 世界爆物爆率;
    private javax.swing.JTextField 世界爆物物品代码;
    private javax.swing.JTextField 个人发送物品代码;
    private javax.swing.JTextField 个人发送物品数量;
    private javax.swing.JTextField 个人发送物品玩家名字;
    private javax.swing.JButton 个人商店;
    private javax.swing.JButton 主题馆;
    private javax.swing.JButton 会员卡;
    private javax.swing.JButton 保存数据按钮;
    private javax.swing.JButton 保存雇佣按钮;
    private javax.swing.JButton 修改;
    private javax.swing.JButton 修改世界爆物;
    private javax.swing.JButton 修改反应堆物品;
    private javax.swing.JButton 修改商品;
    private javax.swing.JButton 修改怪物爆物;
    private javax.swing.JButton 修改技能;
    private javax.swing.JButton 修改技能1;
    private javax.swing.JButton 修改物品掉落持续时间;
    private javax.swing.JButton 修改物品掉落持续时间1;
    private javax.swing.JButton 修改物品掉落持续时间2;
    private javax.swing.JButton 修改背包扩充价格;
    private javax.swing.JButton 修改账号点券抵用;
    private javax.swing.JTextField 全服发送物品代码;
    private javax.swing.JTextField 全服发送物品数量;
    private javax.swing.JTextField 全服发送装备物品ID;
    private javax.swing.JTextField 全服发送装备装备HP;
    private javax.swing.JTextField 全服发送装备装备MP;
    private javax.swing.JTextField 全服发送装备装备制作人;
    private javax.swing.JTextField 全服发送装备装备力量;
    private javax.swing.JTextField 全服发送装备装备加卷;
    private javax.swing.JTextField 全服发送装备装备可否交易;
    private javax.swing.JTextField 全服发送装备装备攻击力;
    private javax.swing.JTextField 全服发送装备装备敏捷;
    private javax.swing.JTextField 全服发送装备装备智力;
    private javax.swing.JTextField 全服发送装备装备物理防御;
    private javax.swing.JTextField 全服发送装备装备给予时间;
    private javax.swing.JTextField 全服发送装备装备运气;
    private javax.swing.JTextField 全服发送装备装备魔法力;
    private javax.swing.JTextField 全服发送装备装备魔法防御;
    private javax.swing.JTextField 公告发布喇叭代码;
    private javax.swing.JButton 其他;
    public static javax.swing.JTextPane 其他文本大框;
    private javax.swing.JTextField 其他背包物品代码;
    private javax.swing.JTextField 其他背包物品名字;
    private javax.swing.JTextField 其他背包物品序号;
    private javax.swing.JTextField 删MAC代码;
    private javax.swing.JButton 删除IP;
    private javax.swing.JTextField 删除IP代码;
    private javax.swing.JButton 删除MAC;
    private javax.swing.JButton 删除世界爆物;
    private javax.swing.JButton 删除其他背包;
    private javax.swing.JButton 删除反应堆物品;
    private javax.swing.JButton 删除反应堆物品1;
    private javax.swing.JTextField 删除反应堆物品代码;
    private javax.swing.JButton 删除商品;
    private javax.swing.JButton 删除商城仓库;
    private javax.swing.JButton 删除怪物爆物;
    private javax.swing.JButton 删除技能;
    private javax.swing.JButton 删除拍卖行;
    private javax.swing.JButton 删除拍卖行1;
    private javax.swing.JTextField 删除指定的掉落;
    private javax.swing.JButton 删除指定的掉落按键;
    private javax.swing.JButton 删除消耗背包;
    private javax.swing.JButton 删除游戏仓库;
    private javax.swing.JButton 删除特殊背包;
    private javax.swing.JButton 删除穿戴装备;
    private javax.swing.JButton 删除装备背包;
    private javax.swing.JButton 删除角色;
    private javax.swing.JButton 删除设置背包;
    private javax.swing.JButton 删除账号;
    private javax.swing.JButton 删除道具;
    private javax.swing.JButton 删除道具1;
    private javax.swing.JButton 删除道具2;
    private javax.swing.JButton 删除道具3;
    private javax.swing.JButton 删除道具4;
    private javax.swing.JButton 刷新世界爆物;
    private javax.swing.JButton 刷新家族信息;
    private javax.swing.JButton 刷新封IP;
    private javax.swing.JButton 刷新封MAC;
    private javax.swing.JButton 刷新怪物卡片;
    private javax.swing.JButton 刷新怪物爆物;
    private javax.swing.JButton 刷新模式;
    private javax.swing.JButton 刷新角色信息;
    private javax.swing.JButton 刷新账号信息;
    private javax.swing.JTextField 力量;
    private javax.swing.JButton 卡号自救1;
    private javax.swing.JButton 卡号自救2;
    private javax.swing.JButton 卡家族解救;
    private javax.swing.JButton 卷轴;
    private javax.swing.JTextField 双倍爆率持续时间;
    private javax.swing.JTextField 双倍经验持续时间;
    private javax.swing.JTextField 双倍金币持续时间;
    private javax.swing.JTable 反应堆;
    private javax.swing.JTextField 反应堆代码;
    private javax.swing.JTextField 反应堆序列号;
    private javax.swing.JTextField 反应堆概率;
    private javax.swing.JTextField 反应堆物品;
    private javax.swing.JTextField 发型;
    private javax.swing.JTextField 发送装备玩家姓名;
    private javax.swing.JTextField 商品代码;
    private javax.swing.JTextField 商品价格;
    private javax.swing.JTextField 商品出售状态;
    private javax.swing.JTextField 商品名称;
    private javax.swing.JTextField 商品售价金币;
    private javax.swing.JTextField 商品序号;
    private javax.swing.JTextField 商品库存;
    private javax.swing.JTextField 商品折扣;
    private javax.swing.JTextField 商品数量;
    private javax.swing.JTextField 商品时间;
    private javax.swing.JTextField 商品物品代码;
    private javax.swing.JTextField 商品编码;
    private javax.swing.JTextField 商城仓库物品代码;
    private javax.swing.JTextField 商城仓库物品名字;
    private javax.swing.JTextField 商城仓库物品序号;
    private javax.swing.JTable 商城扩充价格;
    private javax.swing.JTextField 商城扩充价格修改;
    private javax.swing.JTextField 商店代码;
    private javax.swing.JButton 喜庆物品;
    private javax.swing.JButton 在线角色;
    private javax.swing.JButton 在线账号;
    private javax.swing.JTextField 地图;
    private javax.swing.JTextField 地图刷新频率;
    private javax.swing.JTextField 地图物品上限;
    private javax.swing.JButton 宠物;
    private javax.swing.JButton 宠物服饰;
    private javax.swing.JTextField 家族GP;
    private javax.swing.JTextField 家族ID;
    private javax.swing.JTable 家族信息;
    private javax.swing.JTextField 家族名称;
    private javax.swing.JTextField 家族成员2;
    private javax.swing.JTextField 家族成员3;
    private javax.swing.JTextField 家族成员4;
    private javax.swing.JTextField 家族成员5;
    private javax.swing.JTextField 家族族长;
    private javax.swing.JTable 封IP;
    private javax.swing.JTable 封MAC;
    private javax.swing.JButton 封锁账号;
    private javax.swing.JButton 已封账号;
    private javax.swing.JButton 帽子;
    private javax.swing.JButton 开启三倍爆率;
    private javax.swing.JButton 开启三倍经验;
    private javax.swing.JButton 开启三倍金币;
    private javax.swing.JButton 开启双倍爆率;
    private javax.swing.JButton 开启双倍经验;
    private javax.swing.JButton 开启双倍金币;
    private javax.swing.JTable 怪物爆物;
    private javax.swing.JTextField 怪物爆物序列号;
    private javax.swing.JTextField 怪物爆物怪物代码;
    private javax.swing.JTextField 怪物爆物爆率;
    private javax.swing.JTextField 怪物爆物物品代码;
    private javax.swing.JTextField 怪物爆物物品名称;
    private javax.swing.JButton 戒指;
    private javax.swing.JButton 手套;
    private javax.swing.JPanel 技能;
    private javax.swing.JTextField 技能代码;
    private javax.swing.JTable 技能信息;
    private javax.swing.JTextField 技能名字;
    private javax.swing.JTextField 技能序号;
    private javax.swing.JTextField 技能最高等级;
    private javax.swing.JTextField 技能目前等级;
    public static javax.swing.JTextPane 报错文本大框;
    private javax.swing.JButton 披风;
    private javax.swing.JTextField 抵用;
    private javax.swing.JTextField 拍卖行物品代码;
    private javax.swing.JTextField 拍卖行物品代码1;
    private javax.swing.JTextField 拍卖行物品名字;
    private javax.swing.JTextField 拍卖行物品名字1;
    public static javax.swing.JTextPane 提示文本大窗口;
    private javax.swing.JButton 效果;
    private javax.swing.JTextField 敏捷;
    public static javax.swing.JTextPane 数据包文本大框;
    private javax.swing.JButton 新增反应堆物品;
    private javax.swing.JButton 新增商品;
    private javax.swing.JLabel 显示在线玩家;
    private javax.swing.JLabel 显示在线账号;
    private javax.swing.JButton 显示管理角色;
    private javax.swing.JTextField 显示类型;
    private javax.swing.JTextField 智力;
    private javax.swing.JTextField 查找反应堆掉落;
    private javax.swing.JTextField 查找物品;
    private javax.swing.JButton 查找道具;
    private javax.swing.JButton 查找道具1;
    private javax.swing.JButton 查找道具2;
    private javax.swing.JButton 查找道具3;
    private javax.swing.JButton 查找道具4;
    private javax.swing.JButton 查看技能;
    private javax.swing.JButton 查看背包;
    private javax.swing.JTextField 查询商店;
    private javax.swing.JButton 查询商店2;
    private javax.swing.JButton 查询在线玩家人数按钮;
    private javax.swing.JButton 查询怪物掉落;
    private javax.swing.JTextField 查询怪物掉落代码;
    private javax.swing.JButton 查询物品掉落;
    private javax.swing.JTextField 查询物品掉落代码;
    private javax.swing.JButton 武器;
    private javax.swing.JTextField 每日限购;
    private javax.swing.JTextField 注册的密码;
    private javax.swing.JTextField 注册的账号;
    private javax.swing.JButton 活动;
    private javax.swing.JButton 测试刷新图片按钮;
    private javax.swing.JTable 测试显示图片;
    private javax.swing.JTextField 消耗背包物品代码;
    private javax.swing.JTextField 消耗背包物品名字;
    private javax.swing.JTextField 消耗背包物品序号;
    private javax.swing.JButton 添加;
    private javax.swing.JButton 添加世界爆物;
    private javax.swing.JButton 添加怪物爆物;
    private javax.swing.JButton 游戏;
    private javax.swing.JTextField 游戏仓库物品代码;
    private javax.swing.JTextField 游戏仓库物品名字;
    private javax.swing.JTextField 游戏仓库物品序号;
    private javax.swing.JTable 游戏商店2;
    private javax.swing.JTable 游戏道具;
    private javax.swing.JTextField 游戏道具代码;
    private javax.swing.JTextField 点券;
    private javax.swing.JTextField 物品掉落持续时间;
    private javax.swing.JTextField 特殊背包物品代码;
    private javax.swing.JTextField 特殊背包物品名字;
    private javax.swing.JTextField 特殊背包物品序号;
    public static javax.swing.JLabel 状态信息;
    private javax.swing.JButton 眼饰;
    private javax.swing.JButton 离线角色;
    private javax.swing.JButton 离线账号;
    private javax.swing.JTextField 等级;
    private javax.swing.JTextField 管理1;
    private javax.swing.JButton 纪念日;
    private javax.swing.JButton 给予物品;
    private javax.swing.JButton 给予物品1;
    private javax.swing.JButton 给予装备1;
    private javax.swing.JButton 给予装备2;
    private javax.swing.JTextField 背包物品代码1;
    private javax.swing.JTextField 背包物品名字1;
    private javax.swing.JTextField 脸型;
    private javax.swing.JButton 脸饰;
    private javax.swing.JButton 蓝蜗牛开关;
    private javax.swing.JButton 表情;
    private javax.swing.JTextField 装备背包物品代码;
    private javax.swing.JTextField 装备背包物品名字;
    private javax.swing.JTextField 装备背包物品序号;
    private javax.swing.JButton 裙裤;
    private javax.swing.JTextField 角色ID;
    private javax.swing.JTable 角色信息;
    private javax.swing.JPanel 角色信息1;
    private javax.swing.JTable 角色其他背包;
    private javax.swing.JTable 角色商城仓库;
    private javax.swing.JTextField 角色昵称;
    private javax.swing.JTable 角色消耗背包;
    private javax.swing.JTable 角色游戏仓库;
    private javax.swing.JTable 角色点券拍卖行;
    private javax.swing.JTextField 角色点券拍卖行序号;
    private javax.swing.JTable 角色特殊背包;
    private javax.swing.JPanel 角色背包;
    private javax.swing.JTable 角色背包穿戴;
    private javax.swing.JTable 角色装备背包;
    private javax.swing.JTable 角色设置背包;
    private javax.swing.JTable 角色金币拍卖行;
    private javax.swing.JTextField 角色金币拍卖行序号;
    private javax.swing.JButton 解卡;
    private javax.swing.JButton 解封;
    private javax.swing.JTextField 设置背包物品代码;
    private javax.swing.JTextField 设置背包物品名字;
    private javax.swing.JTextField 设置背包物品序号;
    private javax.swing.JButton 读取热销产品;
    private javax.swing.JTextField 账号;
    private javax.swing.JTextField 账号ID;
    private javax.swing.JTable 账号信息;
    private javax.swing.JLabel 账号提示语言;
    private javax.swing.JTextField 账号操作;
    private javax.swing.JTextField 货币类型;
    private javax.swing.JTextField 身上穿戴序号1;
    private javax.swing.JTextField 运气;
    public static javax.swing.JProgressBar 进度条;
    private javax.swing.JButton 通讯物品;
    private javax.swing.JButton 重载任务;
    private javax.swing.JButton 重载传送门按钮;
    private javax.swing.JButton 重载副本按钮;
    private javax.swing.JButton 重载包头按钮;
    private javax.swing.JButton 重载反应堆按钮;
    private javax.swing.JButton 重载商城按钮;
    private javax.swing.JButton 重载商店按钮;
    private javax.swing.JButton 重载爆率按钮;
    private javax.swing.JTextField 金币1;
    private javax.swing.JButton 长袍;
    private javax.swing.JButton 鞋子;
    private javax.swing.JButton 飞镖;
    private javax.swing.JButton 骑宠;
    // End of variables declaration//GEN-END:variables
    


  private void sendNotice(int a) {
        try {
            String str = noticeText.getText();
            String 输出 = "";
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    switch (a) {
                        case 0:
                            //顶端公告
                            World.Broadcast.broadcastMessage(MaplePacketCreator.getItemNotice(str.toString()));
                            break;
                        case 1:
                            //顶端公告
                            World.Broadcast.broadcastMessage(MaplePacketCreator.serverMessage(str.toString()));
                            break;
                        case 2:
                            //弹窗公告
                            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(1, str));
                            break;
                        case 3:
                            //聊天蓝色公告
                            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, str));
                            break;
                        case 4:
                            mch.startMapEffect(str, Integer.parseInt(公告发布喇叭代码.getText()));
                            break;
                        default:
                            break;
                    }
                }
                公告发布喇叭代码.setText("5120027");
            }
            //sendMsgToQQGroup("<" + MapleParty.开服名字 + ">公告:" + str + "。");
        } catch (Exception e) {
        }
    }
    

        public void updateThreadNum() {
        writeLock.lock();
        try {
            server.Timer.WorldTimer.GuiTimer.getInstance().register(new Runnable() {
                @Override
                public final void run() {
                    ActiveThread.setText("游戏线程：" + Thread.activeCount() + "");
                }
            }, 1 * 1000);
        } finally {
            writeLock.unlock();
        }
    }
 

    /*
    * cs
     */
    public void 上架() {

        try {
            int SN_ = Integer.parseInt(String.valueOf(this.charTable.getValueAt(this.charTable.getSelectedRow(), 0)));
            //清楚table数据
            for (int i = ((DefaultTableModel) (this.charTable.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.charTable.getModel())).removeRow(i);
            }
            int OnSale_ = 1;
            CashItemInfo merchandise = new CashItemInfo(SN_, OnSale_);
            int success = update上下架(merchandise);
            if (success == 0) {
                JOptionPane.showMessageDialog(null, "[信息]:上架失败。");
            } else {
                initCharacterPannel();
                JOptionPane.showMessageDialog(null, "[信息]:上架成功。");
            }
        } catch (NumberFormatException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "[信息]:上架失败，请选中你要上架的道具。");
        }
    }

    public void 下架() {
        try {
            int SN_ = Integer.parseInt(String.valueOf(this.charTable.getValueAt(this.charTable.getSelectedRow(), 0)));
            //清楚table数据
            for (int i = ((DefaultTableModel) (this.charTable.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.charTable.getModel())).removeRow(i);
            }
            int OnSale_ = 0;
            CashItemInfo merchandise = new CashItemInfo(SN_, OnSale_);
            int success = update上下架(merchandise);
            if (success == 0) {
                JOptionPane.showMessageDialog(null, "[信息]:下架失败。");
            } else {
                initCharacterPannel();
                JOptionPane.showMessageDialog(null, "[信息]:下架成功。");
            }
        } catch (NumberFormatException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "[信息]:下架失败，请选中你要上架的道具。");
        }
    }

    public static int update上下架(CashItemInfo merchandise) {//修改
        PreparedStatement ps = null;
        int resulet = 0;
        Connection conn = DatabaseConnection.getConnection();
        int i = 0;
        try {
            ps = conn.prepareStatement("update cashshop_modified_items set showup = ? where serial = ?");//itemid
            ps.setInt(++i, merchandise.getOnSale());
            ps.setInt(++i, merchandise.getSN());
            resulet = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Start.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return resulet;
    }

    public void 读取商品(final int a, int b, int c, int d) {
        for (int i = ((DefaultTableModel) (this.charTable.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.charTable.getModel())).removeRow(i);
        }
        商品编码.setText("" + a + "");
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            PreparedStatement pse;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM cashshop_modified_items WHERE serial >= " + a + " && serial < " + b + "");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 上架状态 = "";
                if (rs.getInt("showup") == 0) {
                    上架状态 = "已经下架↓";
                } else {
                    上架状态 = "已经上架↑";
                }
                String 出售状态2 = "";
                switch (rs.getInt("mark")) {
                    case -1:
                        出售状态2 = "无";
                        break;
                    case 0:
                        出售状态2 = "NEW";
                        break;
                    case 1:
                        出售状态2 = "Sale";
                        break;
                    case 2:
                        出售状态2 = "HOT";
                        break;
                    case 3:
                        出售状态2 = "Event";
                        break;
                    default:
                        break;
                }
                String 类型 = "";
                if ("".equals(NPCConversationManager.SN取类型(rs.getInt("serial")))) {
                    类型 = "点券";
                } else {
                    类型 = "点/抵用券";
                }
//                System.out.println( MapleItemInformationProvider.getInstance().getName(1302000));
                ((DefaultTableModel) charTable.getModel()).insertRow(charTable.getRowCount(), new Object[]{
                    rs.getInt("serial"),
                    rs.getInt("itemid"),
                    //itemName,
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("count"),
                    rs.getInt("discount_price"),
                    rs.getInt("period"),
                    出售状态2,
                    上架状态,
                    NPCConversationManager.SN取出售(rs.getInt("serial")),
                    NPCConversationManager.SN取库存(rs.getInt("serial")),
                    NPCConversationManager.SN取折扣(rs.getInt("serial")),
                    NPCConversationManager.SN取限购(rs.getInt("serial")),
                    类型
                });

            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT `serial` FROM cashshop_modified_items WHERE serial >= " + a + " && serial <" + b + " ORDER BY `serial` DESC LIMIT 1");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String SN = rs.getString("serial");
                    int sns = Integer.parseInt(SN);
                    sns++;
                    商品编码.setText("" + sns);
                    ps.close();
                }
            }
            ps.close();
        } catch (SQLException ex) {
            System.err.println("出错读取商品：" + ex.getMessage());
        }
        if (c == 1 && d == 1) {
            显示类型.setText("热销产品");
            JOptionPane.showMessageDialog(null, "[信息]:显示热销产品，双击后可在热销产品下添加商品。");
        } else if (c == 1 && d == 2) {
            显示类型.setText("主题馆");
            JOptionPane.showMessageDialog(null, "[信息]:显示主题馆，双击后可在主题馆下添加商品。");
        } else if (c == 1 && d == 3) {
            显示类型.setText("活动");
            JOptionPane.showMessageDialog(null, "[信息]:显示活动，双击后可在活动下添加商品。");
        } else if (c == 2 && d == 1) {
            显示类型.setText("帽子");
            JOptionPane.showMessageDialog(null, "[信息]:显示帽子，双击后可在帽子下添加商品。");
        } else if (c == 2 && d == 2) {
            显示类型.setText("裙裤");
            JOptionPane.showMessageDialog(null, "[信息]:显示裙裤，双击后可在裙裤下添加商品。");
        } else if (c == 2 && d == 3) {
            显示类型.setText("披风");
            JOptionPane.showMessageDialog(null, "[信息]:显示披风，双击后可在披风下添加商品。");
        } else if (c == 2 && d == 4) {
            显示类型.setText("飞镖");
            JOptionPane.showMessageDialog(null, "[信息]:显示飞镖，双击后可在飞镖下添加商品。");
        } else if (c == 2 && d == 5) {
            显示类型.setText("长袍");
            JOptionPane.showMessageDialog(null, "[信息]:显示长袍，双击后可在长袍下添加商品。");
        } else if (c == 2 && d == 6) {
            显示类型.setText("脸饰");
            JOptionPane.showMessageDialog(null, "[信息]:显示脸饰，双击后可在脸饰下添加商品。");
        } else if (c == 2 && d == 7) {
            显示类型.setText("鞋子");
            JOptionPane.showMessageDialog(null, "[信息]:显示鞋子，双击后可在鞋子下添加商品。");
        } else if (c == 2 && d == 8) {
            显示类型.setText("骑宠");
            JOptionPane.showMessageDialog(null, "[信息]:显示骑宠，双击后可在骑宠下添加商品。");
        } else if (c == 2 && d == 9) {
            显示类型.setText("戒指");
            JOptionPane.showMessageDialog(null, "[信息]:显示戒指，双击后可在戒指下添加商品。");
        } else if (c == 2 && d == 10) {
            显示类型.setText("眼饰");
            JOptionPane.showMessageDialog(null, "[信息]:显示眼饰，双击后可在眼饰下添加商品。");
        } else if (c == 2 && d == 11) {
            显示类型.setText("手套");
            JOptionPane.showMessageDialog(null, "[信息]:显示手套，双击后可在手套下添加商品。");
        } else if (c == 2 && d == 12) {
            显示类型.setText("武器");
            JOptionPane.showMessageDialog(null, "[信息]:显示武器，双击后可在武器下添加商品。");
        } else if (c == 2 && d == 13) {
            显示类型.setText("上衣");
            JOptionPane.showMessageDialog(null, "[信息]:显示上衣，双击后可在上衣下添加商品。");
        } else if (c == 3 && d == 1) {
            显示类型.setText("喜庆物品");
            JOptionPane.showMessageDialog(null, "[信息]:显示喜庆物品，双击后可在喜庆物品下添加商品。");
        } else if (c == 3 && d == 2) {
            显示类型.setText("通讯物品");
            JOptionPane.showMessageDialog(null, "[信息]:显示通讯物品，双击后可在通讯物品下添加商品。");
        } else if (c == 3 && d == 3) {
            显示类型.setText("卷轴");
            JOptionPane.showMessageDialog(null, "[信息]:显示卷轴，双击后可在卷轴下添加商品。");
        } else if (c == 4 && d == 1) {
            显示类型.setText("会员卡");
            JOptionPane.showMessageDialog(null, "[信息]:显示会员卡，双击后可在会员卡下添加商品。");
        } else if (c == 4 && d == 2) {
            显示类型.setText("表情");
            JOptionPane.showMessageDialog(null, "[信息]:显示表情，双击后可在表情下添加商品。");
        } else if (c == 4 && d == 3) {
            显示类型.setText("个人商店");
            JOptionPane.showMessageDialog(null, "[信息]:显示个人商店，双击后可在个人商店下添加商品。");
        } else if (c == 4 && d == 4) {
            显示类型.setText("效果");
            JOptionPane.showMessageDialog(null, "[信息]:显示效果，双击后可在效果下添加商品。");
        } else if (c == 4 && d == 5) {
            显示类型.setText("游戏");
            JOptionPane.showMessageDialog(null, "[信息]:显示游戏，双击后可在游戏下添加商品。");
        } else if (c == 4 && d == 6) {
            显示类型.setText("纪念日");
            JOptionPane.showMessageDialog(null, "[信息]:显示纪念日，双击后可在纪念日下添加商品。");
        } else if (c == 5 && d == 1) {
            显示类型.setText("宠物");
            JOptionPane.showMessageDialog(null, "[信息]:显示宠物，双击后可在宠物下添加商品。");
        } else if (c == 5 && d == 2) {
            显示类型.setText("宠物服饰");
            JOptionPane.showMessageDialog(null, "[信息]:显示宠物服饰，双击后可在宠物服饰下添加商品。");
        } else if (c == 5 && d == 3) {
            显示类型.setText("其他");
            JOptionPane.showMessageDialog(null, "[信息]:显示其他，双击后可在其他下添加商品。");
        } else {
            显示类型.setText("XXXX");
            JOptionPane.showMessageDialog(null, "[信息]:XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX。");
        }
    }

    public void 刷新() {
        if ("热销产品".equals(显示类型.getText())) {
            读取商品(10000000, 10100000, 1, 1);
        } else if ("主题馆".equals(显示类型.getText())) {
            读取商品(10100000, 10200000, 1, 2);
        } else if ("活动".equals(显示类型.getText())) {
            读取商品(10200000, 10300000, 1, 3);
        } else if ("帽子".equals(显示类型.getText())) {
            读取商品(20000000, 20100000, 2, 1);
        } else if ("裙裤".equals(显示类型.getText())) {
            读取商品(20500000, 20600000, 2, 2);
        } else if ("披风".equals(显示类型.getText())) {
            读取商品(21100000, 21200000, 2, 3);
        } else if ("飞镖".equals(显示类型.getText())) {
            读取商品(21000000, 21100000, 2, 4);
        } else if ("长袍".equals(显示类型.getText())) {
            读取商品(20300000, 20400000, 2, 5);
        } else if ("脸饰".equals(显示类型.getText())) {
            读取商品(20100000, 20200000, 2, 6);
        } else if ("鞋子".equals(显示类型.getText())) {
            读取商品(20600000, 20700000, 2, 7);
        } else if ("骑宠".equals(显示类型.getText())) {
            读取商品(21200000, 21300000, 2, 8);
        } else if ("戒指".equals(显示类型.getText())) {
            读取商品(20900000, 21000000, 2, 9);
        } else if ("眼饰".equals(显示类型.getText())) {
            读取商品(20200000, 20300000, 2, 10);
        } else if ("手套".equals(显示类型.getText())) {
            读取商品(20700000, 20800000, 2, 11);
        } else if ("武器".equals(显示类型.getText())) {
            读取商品(20800000, 20900000, 2, 12);
        } else if ("上衣".equals(显示类型.getText())) {
            读取商品(20400000, 20500000, 2, 13);
        } else if ("喜庆物品".equals(显示类型.getText())) {
            读取商品(30000000, 30100000, 3, 1);
        } else if ("通讯物品".equals(显示类型.getText())) {
            读取商品(30100000, 30200000, 3, 2);
        } else if ("卷轴".equals(显示类型.getText())) {
            读取商品(30200000, 30300000, 3, 3);
        } else if ("会员卡".equals(显示类型.getText())) {
            读取商品(50000000, 50100000, 4, 1);
        } else if ("表情".equals(显示类型.getText())) {
            读取商品(50100000, 50200000, 4, 2);
        } else if ("个人商店".equals(显示类型.getText())) {
            读取商品(50200000, 50300000, 4, 3);
        } else if ("效果".equals(显示类型.getText())) {
            读取商品(50500000, 50600000, 4, 4);
        } else if ("纪念日".equals(显示类型.getText())) {
            读取商品(50300000, 50400000, 4, 6);
        } else if ("游戏".equals(显示类型.getText())) {
            读取商品(50400000, 50500000, 4, 5);
        } else if ("宠物".equals(显示类型.getText())) {
            读取商品(60000000, 60100000, 5, 1);
        } else if ("宠物服饰".equals(显示类型.getText())) {
            读取商品(60100000, 60200000, 5, 2);
        } else if ("其他".equals(显示类型.getText())) {
            读取商品(60200000, 60300000, 5, 3);
        } else if ("".equals(显示类型.getText())) {
            initCharacterPannel();
        }
    }

    public void initCharacterPannel() {
        long start = System.currentTimeMillis();
        for (int i = ((DefaultTableModel) (this.charTable.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.charTable.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            PreparedStatement pse;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM cashshop_modified_items ");//WHERE serial > 10000000 && serial < 10100000

            rs = ps.executeQuery();
            while (rs.next()) {
                String itemName = "";
                itemName = MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"));
                String 上架状态 = "";
                if (rs.getInt("showup") == 0) {
                    上架状态 = "已经下架↓";
                } else {
                    上架状态 = "已经上架↑";
                }
                String 出售状态2 = "";
                switch (rs.getInt("mark")) {
                    case -1:
                        出售状态2 = "无";
                        break;
                    case 0:
                        出售状态2 = "NEW";
                        break;
                    case 1:
                        出售状态2 = "Sale";
                        break;
                    case 2:
                        出售状态2 = "HOT";
                        break;
                    case 3:
                        出售状态2 = "Event";
                        break;
                    default:
                        break;
                }
                ((DefaultTableModel) charTable.getModel()).insertRow(charTable.getRowCount(), new Object[]{
                    rs.getInt("serial"),
                    rs.getInt("itemid"),
                    "非详细分类不显示名称",
                    //itemName,
                    rs.getInt("count"),
                    rs.getInt("discount_price"),
                    rs.getInt("period"),
                    出售状态2,
                    上架状态,
                    NPCConversationManager.SN取出售(rs.getInt("serial")),
                    NPCConversationManager.SN取库存(rs.getInt("serial")),
                    NPCConversationManager.SN取折扣(rs.getInt("serial")),
                    NPCConversationManager.SN取限购(rs.getInt("serial"))
                });
            }
            long now = System.currentTimeMillis() - start;

        } catch (SQLException ex) {
            Logger.getLogger(Start.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        charTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = charTable.getSelectedRow();
                String a1 = charTable.getValueAt(i, 0).toString();
                String a2 = charTable.getValueAt(i, 1).toString();
                String a3 = charTable.getValueAt(i, 3).toString();
                String a4 = charTable.getValueAt(i, 4).toString();
                String a5 = charTable.getValueAt(i, 5).toString();
                String a6 = charTable.getValueAt(i, 6).toString();
                String a7 = charTable.getValueAt(i, 7).toString();
                String a8 = charTable.getValueAt(i, 8).toString();
                String a9 = charTable.getValueAt(i, 9).toString();
                String a10 = charTable.getValueAt(i, 10).toString();
                String a11 = charTable.getValueAt(i, 11).toString();

                商品编码.setText(a1);
                商品代码.setText(a2);
                商品数量.setText(a3);
                商品价格.setText(a4);
                商品时间.setText(a5);
                商品库存.setText(a9);
                商品折扣.setText(a10);
                每日限购.setText(a11);

                if (null != charTable.getValueAt(i, 6).toString()) {
                    switch (charTable.getValueAt(i, 6).toString()) {
                        case "无":
                            商品出售状态.setText("-1");
                            break;
                        case "NEW":
                            商品出售状态.setText("0");
                            break;
                        case "Sale":
                            商品出售状态.setText("1");
                            break;
                        case "HOT":
                            商品出售状态.setText("2");
                            break;
                        case "Event":
                            商品出售状态.setText("3");
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    /*
    * @drop 
     */
    public void 刷新世界爆物() {

        for (int i = ((DefaultTableModel) (this.世界爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.世界爆物.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM drop_data_global WHERE itemid !=0");
            rs = ps.executeQuery();
            while (rs.next()) {

                ((DefaultTableModel) 世界爆物.getModel()).insertRow(世界爆物.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    rs.getInt("itemid"),
                    rs.getString("chance"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                });
            }
            世界爆物.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int i = 世界爆物.getSelectedRow();
                    String a = 世界爆物.getValueAt(i, 0).toString();
                    String a1 = 世界爆物.getValueAt(i, 1).toString();
                    String a2 = 世界爆物.getValueAt(i, 2).toString();
                    世界爆物序列号.setText(a);
                    世界爆物物品代码.setText(a1);
                    世界爆物爆率.setText(a2);
                }
            });

        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void 刷新指定怪物爆物() {
        boolean result = this.查询怪物掉落代码.getText().matches("[0-9]+");
        if (result) {
            if (Integer.parseInt(this.查询怪物掉落代码.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "请填写正确的值");
            }
            for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;

                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM drop_data WHERE dropperid =  " + Integer.parseInt(this.怪物爆物怪物代码.getText()) + "");
                rs = ps.executeQuery();
                while (rs.next()) {
                    ((DefaultTableModel) 怪物爆物.getModel()).insertRow(怪物爆物.getRowCount(), new Object[]{rs.getInt("id"), rs.getInt("dropperid"), rs.getInt("itemid"), rs.getInt("chance"), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))});
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            怪物爆物.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int i = 怪物爆物.getSelectedRow();
                    String a = 怪物爆物.getValueAt(i, 0).toString();
                    String a1 = 怪物爆物.getValueAt(i, 1).toString();
                    String a2 = 怪物爆物.getValueAt(i, 2).toString();
                    String a3 = 怪物爆物.getValueAt(i, 3).toString();
                    String a4 = 怪物爆物.getValueAt(i, 4).toString();
                    怪物爆物序列号.setText(a);
                    怪物爆物怪物代码.setText(a1);
                    怪物爆物物品代码.setText(a2);
                    怪物爆物爆率.setText(a3);
                    怪物爆物物品名称.setText(a4);
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "请输入要查询的怪物代码 ");
        }
    }

    public void 刷新怪物爆物() {
        //
        for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM drop_data WHERE itemid !=0");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 怪物爆物.getModel()).insertRow(怪物爆物.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    rs.getInt("dropperid"),
                    //MapleLifeFactory.getMonster(rs.getInt("dropperid")),
                    rs.getInt("itemid"),
                    rs.getInt("chance"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        怪物爆物.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 怪物爆物.getSelectedRow();
                String a = 怪物爆物.getValueAt(i, 0).toString();
                String a1 = 怪物爆物.getValueAt(i, 1).toString();
                String a2 = 怪物爆物.getValueAt(i, 2).toString();
                String a3 = 怪物爆物.getValueAt(i, 3).toString();
                //String a4 = 怪物爆物.getValueAt(i, 4).toString();
                怪物爆物序列号.setText(a);
                怪物爆物怪物代码.setText(a1);
                怪物爆物物品代码.setText(a2);
                怪物爆物爆率.setText(a3);
                //怪物爆物物品名称.setText(a4);

            }
        });
    }

    public void 刷新怪物卡片() {
        //通过循环并删除表格中的每一行,清空当前的表格
        for (int i = ((DefaultTableModel) (this.怪物爆物.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.怪物爆物.getModel())).removeRow(i);
        }
        //方法连接到数据库并执行一个查询，从drop_data表中选择itemid在2380000和2390000之间的所有记录
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM drop_data WHERE itemid >=2380000&& itemid <2390000");
            rs = ps.executeQuery();
            //它遍历所有返回的记录，将它们插入到"怪物爆物"的表格中。每一行都包含五个字段："序列号""怪物代码""物品代码""爆率"和"物品名字"
            while (rs.next()) {
                ((DefaultTableModel) 怪物爆物.getModel()).insertRow(怪物爆物.getRowCount(), new Object[]{
                    //读取的是数据库的数据
                    rs.getInt("id"), //序列号
                    rs.getInt("dropperid"), //怪物代码
                    //MapleLifeFactory.getMonster(rs.getInt("dropperid")),
                    rs.getInt("itemid"), //物品代码
                    rs.getInt("chance"), //爆率
                    //物品名字是通过另一个方法获得的。读取WZ的数据
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))  //物品名字
                });
            }
            //如果在执行查询时发生了错误（SQLException），它将记录到日志中
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        //这个方法为"怪物爆物"表格添加一个鼠标点击事件。当用户点击行时 ，它将填充四个文本框
        怪物爆物.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 怪物爆物.getSelectedRow();
                String a = 怪物爆物.getValueAt(i, 0).toString();
                String a1 = 怪物爆物.getValueAt(i, 1).toString();
                String a2 = 怪物爆物.getValueAt(i, 2).toString();
                String a3 = 怪物爆物.getValueAt(i, 3).toString();
                //String a4 = 怪物爆物.getValueAt(i, 4).toString();
                怪物爆物序列号.setText(a);
                怪物爆物怪物代码.setText(a1);
                怪物爆物物品代码.setText(a2);
                怪物爆物爆率.setText(a3);
                //怪物爆物物品名称.setText(a4);
            }
        });
    }

    private void ChangePassWord() {
        /* boolean result1 = this.jTextField24.getText().matches("[0-9]+");
        boolean result2 = this.jTextField25.getText().matches("[0-9]+");
        if (result1 && result2) {*/
        String account = 注册的账号.getText();
        String password = 注册的密码.getText();

        if (password.length() > 12) {
            账号提示语言.setText("[信息]:修改密码失败，密码过长。");
            return;
        }
        if (!AutoRegister.getAccountExists(account)) {
            账号提示语言.setText("[信息]:修改密码失败，账号不存在。");
            return;
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps;
            ps = con.prepareStatement("Update accounts set password = ? Where name = ?");
            ps.setString(1, LoginCrypto.hexSha1(password));
            ps.setString(2, account);
            ps.execute();
            ps.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
        账号提示语言.setText("[信息]:修改密码成功。账号: " + account + " 密码: " + password + "");
        //JOptionPane.showMessageDialog(null, "账号: " + account + "\r\n密码: " + password);
        /* } else 
            JOptionPane.showMessageDialog(null, "请填写数据");
            return;
        }*/
    }

    public void 注册新账号() {
        boolean result1 = this.注册的账号.getText().matches("[0-9]+");
        boolean result2 = this.注册的密码.getText().matches("[0-9]+");
        if (注册的账号.getText().equals("") || 注册的密码.getText().equals("")) {
            账号提示语言.setText("[信息]:请填写注册的账号密码");
            return;
        } else {
            Connection con;
            String account = 注册的账号.getText();
            String password = 注册的密码.getText();

            if (password.length() > 10) {
                账号提示语言.setText("[信息]:注册失败，密码过长");
                return;
            }
            if (AutoRegister.getAccountExists(account)) {
                账号提示语言.setText("[信息]:注册失败，账号已存在");
                return;
            }
            try {
                con = DatabaseConnection.getConnection();
            } catch (Exception ex) {
                System.out.println(ex);
                return;
            }
            try {
                PreparedStatement ps = con.prepareStatement("INSERT INTO accounts (name, password) VALUES (?,?)");
                ps.setString(1, account);
                ps.setString(2, LoginCrypto.hexSha1(password));
                ps.executeUpdate();
                刷新账号信息();
                账号提示语言.setText("[信息]:注册成功。账号: " + account + " 密码: " + password + "");
            } catch (SQLException ex) {
                System.out.println(ex);
                return;
            }
        }
    }

    private void 刷新账号信息() {
        for (int i = ((DefaultTableModel) (this.账号信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.账号信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM accounts order by id desc");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 封号 = "";
                if (rs.getInt("banned") == 0) {
                    封号 = "正常";
                } else {
                    封号 = "封禁";
                }
                String 在线 = "";
                if (rs.getInt("loggedin") == 0) {
                    // Font fnA = new Font("细明本",Font.PLAIN,12);
                    在线 = "不在线";
                } else {
                    在线 = "在线";
                }
                String QQ = "";
                if (rs.getString("qq") != null) {
                    QQ = rs.getString("qq");
                } else {
                    QQ = "未绑定QQ";
                }
                ((DefaultTableModel) 账号信息.getModel()).insertRow(账号信息.getRowCount(), new Object[]{
                    rs.getInt("id"), //账号ID
                    rs.getString("name"), //账号
                    rs.getString("SessionIP"), //账号IP地址
                    rs.getString("macs"), ///账号MAC地址
                    QQ,
                    rs.getInt("ACash"),//点券
                    rs.getInt("mPoints"),//抵用
                    rs.getString("lastlogin"),//最近上线
                    //rs.getInt("loggedin"),//在线
                    //rs.getInt("banned")//封号
                    在线,
                    封号,
                    rs.getInt("gm")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        读取显示账号();

    }

    private void 查找QQ() {

        for (int i = ((DefaultTableModel) (this.账号信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.账号信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM accounts WHERE qq =  '" + 账号操作.getText() + " ' ");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 封号 = "";
                if (rs.getInt("banned") == 0) {
                    封号 = "正常";
                } else {
                    封号 = "封禁";
                }
                String 在线 = "";
                if (rs.getInt("loggedin") == 0) {
                    // Font fnA = new Font("细明本",Font.PLAIN,12);
                    在线 = "不在线";
                } else {
                    在线 = "在线";
                }
                String QQ = "";
                if (rs.getString("qq") != null) {
                    QQ = rs.getString("qq");
                } else {
                    QQ = "未绑定QQ";
                }
                ((DefaultTableModel) 账号信息.getModel()).insertRow(账号信息.getRowCount(), new Object[]{
                    rs.getInt("id"), //账号ID
                    rs.getString("name"), //账号
                    rs.getString("SessionIP"), //账号IP地址
                    rs.getString("macs"), ///账号MAC地址
                    QQ,//注册时间
                    rs.getInt("ACash"),//点券
                    rs.getInt("mPoints"),//抵用
                    rs.getString("lastlogin"),//最近上线
                    在线,
                    封号,
                    rs.getInt("gm")
                });
            }
            账号提示语言.setText("[信息]:查找账号 " + this.账号操作.getText() + " 成功。");
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        账号信息.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 账号信息.getSelectedRow();
                String a = 账号信息.getValueAt(i, 0).toString();
                String a1 = 账号信息.getValueAt(i, 1).toString();
                String a2 = 账号信息.getValueAt(i, 5).toString();
                String a3 = 账号信息.getValueAt(i, 6).toString();
                账号ID.setText(a);
                账号操作.setText(a1);
                账号.setText(a1);
                点券.setText(a2);
                抵用.setText(a3);
                刷新角色信息2();
            }
        });
    }

    private void 查找账号() {

        for (int i = ((DefaultTableModel) (this.账号信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.账号信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM accounts WHERE name =  '" + 账号操作.getText() + "  '");
            rs = ps.executeQuery();
            while (rs.next()) {
                String 封号 = "";
                if (rs.getInt("banned") == 0) {
                    封号 = "正常";
                } else {
                    封号 = "封禁";
                }
                String 在线 = "";
                if (rs.getInt("loggedin") == 0) {
                    // Font fnA = new Font("细明本",Font.PLAIN,12);
                    在线 = "不在线";
                } else {
                    在线 = "在线";
                }
                ((DefaultTableModel) 账号信息.getModel()).insertRow(账号信息.getRowCount(), new Object[]{
                    rs.getInt("id"), //账号ID
                    rs.getString("name"), //账号
                    rs.getString("SessionIP"), //账号IP地址
                    rs.getString("macs"), ///账号MAC地址
                    rs.getString("qq"),//注册时间
                    rs.getInt("ACash"),//点券
                    rs.getInt("mPoints"),//抵用
                    rs.getString("lastlogin"),//最近上线
                    在线,
                    封号,
                    rs.getInt("gm")
                });
            }
            账号提示语言.setText("[信息]:查找账号 " + this.账号操作.getText() + " 成功。");
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        账号信息.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 账号信息.getSelectedRow();
                String a = 账号信息.getValueAt(i, 0).toString();
                String a1 = 账号信息.getValueAt(i, 1).toString();
                String a2 = 账号信息.getValueAt(i, 5).toString();
                String a3 = 账号信息.getValueAt(i, 6).toString();
                账号ID.setText(a);
                账号操作.setText(a1);
                账号.setText(a1);
                点券.setText(a2);
                抵用.setText(a3);
                刷新角色信息2();
            }
        });
    }

    private void 刷新技能信息() {
        boolean result1 = this.角色ID.getText().matches("[0-9]+");
        if (result1) {
            for (int i = ((DefaultTableModel) (this.技能信息.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.技能信息.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;

                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM skills  WHERE characterid =" + this.角色ID.getText() + "");
                rs = ps.executeQuery();
                while (rs.next()) {
                    MapleDataProvider data = MapleDataProviderFactory.getDataProvider(new File(System.getProperty("net.sf.odinms.wzpath") + "/String.wz"));
                    MapleData itemsData;
                    int itemId;
                    String itemName = "";
                    itemsData = data.getData("Skill.img");
                    for (MapleData itemFolder : itemsData.getChildren()) {
                        itemId = Integer.parseInt(itemFolder.getName());
                        if (rs.getInt("skillid") == itemId) {
                            itemName = MapleDataTool.getString("name", itemFolder, "NO-NAME");
                        }
                    }
                    ((DefaultTableModel) 技能信息.getModel()).insertRow(技能信息.getRowCount(), new Object[]{
                        rs.getInt("id"),
                        itemName,
                        rs.getInt("skillid"),
                        rs.getInt("skilllevel"),
                        rs.getInt("masterlevel")
                    });
                }
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            技能信息.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int i = 技能信息.getSelectedRow();
                    String a = 技能信息.getValueAt(i, 0).toString();
                    // String a1 = 技能信息.getValueAt(i, 1).toString();
                    String a2 = 技能信息.getValueAt(i, 2).toString();
                    String a3 = 技能信息.getValueAt(i, 3).toString();
                    String a4 = 技能信息.getValueAt(i, 4).toString();
                    技能序号.setText(a);
                    // 技能名字.setText(a1);
                    技能代码.setText(a2);
                    技能目前等级.setText(a3);
                    技能最高等级.setText(a4);
                    //出售状态.setText(a8);
                    //jTextField9.setText(a9);
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请先点击你想查看的角色。");
        }
    }

    private void 刷新角色信息() {
        String 输出 = "";
        for (int i = ((DefaultTableModel) (this.角色信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters order by id desc");
            rs = ps.executeQuery();

            while (rs.next()) {
                String 在线 = "";
                if (World.Find.findChannel(rs.getString("name")) > 0) {
                    在线 = "在线";
                } else {
                    在线 = "离线";
                }
                ((DefaultTableModel) 角色信息.getModel()).insertRow(角色信息.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    rs.getInt("accountid"),
                    rs.getString("name"),
                    getJobNameById(rs.getInt("job")),
                    rs.getInt("level"),
                    rs.getInt("str"),
                    rs.getInt("dex"),
                    rs.getInt("luk"),
                    rs.getInt("int"),
                    rs.getInt("maxhp"),
                    rs.getInt("maxmp"),
                    rs.getInt("meso"),
                    rs.getInt("map"),
                    在线,
                    rs.getInt("gm"),
                    rs.getInt("hair"),
                    rs.getInt("face")
                });

            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色信息.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色信息.getSelectedRow();
                String a = 角色信息.getValueAt(i, 0).toString();
                String a1 = 角色信息.getValueAt(i, 2).toString();
                String a2 = 角色信息.getValueAt(i, 4).toString();
                String a3 = 角色信息.getValueAt(i, 5).toString();
                String a4 = 角色信息.getValueAt(i, 6).toString();
                String a5 = 角色信息.getValueAt(i, 7).toString();
                String a6 = 角色信息.getValueAt(i, 8).toString();
                String a7 = 角色信息.getValueAt(i, 9).toString();
                String a8 = 角色信息.getValueAt(i, 10).toString();
                String a9 = 角色信息.getValueAt(i, 11).toString();
                String a10 = 角色信息.getValueAt(i, 12).toString();
                String a11 = 角色信息.getValueAt(i, 14).toString();
                String a12 = 角色信息.getValueAt(i, 15).toString();
                String a13 = 角色信息.getValueAt(i, 16).toString();
                角色ID.setText(a);
                角色昵称.setText(a1);
                等级.setText(a2);
                力量.setText(a3);
                敏捷.setText(a4);
                智力.setText(a5);
                运气.setText(a6);
                HP.setText(a7);
                MP.setText(a8);
                金币1.setText(a9);
                地图.setText(a10);
                GM.setText(a11);
                发型.setText(a12);
                脸型.setText(a13);
                //  个人发送物品玩家名字.setText(a1);
                //  发送装备玩家姓名.setText(a1);
            }
        });
    }

    private void 刷新角色背包穿戴() {
        for (int i = ((DefaultTableModel) (this.角色背包穿戴.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色背包穿戴.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid =" + this.角色ID.getText() + " && inventorytype = -1");
            rs = ps.executeQuery();
            while (rs.next()) {

                ((DefaultTableModel) 角色背包穿戴.getModel()).insertRow(角色背包穿戴.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色背包穿戴.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色背包穿戴.getSelectedRow();
                String a = 角色背包穿戴.getValueAt(i, 0).toString();
                String a1 = 角色背包穿戴.getValueAt(i, 1).toString();
                String a2 = 角色背包穿戴.getValueAt(i, 2).toString();
                身上穿戴序号1.setText(a);
                背包物品代码1.setText(a1);
                背包物品名字1.setText(a2);
            }
        });
    }

    private void 刷新角色装备背包() {
        for (int i = ((DefaultTableModel) (this.角色装备背包.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色装备背包.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid =" + this.角色ID.getText() + " && inventorytype = 1");
            rs = ps.executeQuery();
            while (rs.next()) {

                ((DefaultTableModel) 角色装备背包.getModel()).insertRow(角色装备背包.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色装备背包.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色装备背包.getSelectedRow();
                String a = 角色装备背包.getValueAt(i, 0).toString();
                String a1 = 角色装备背包.getValueAt(i, 1).toString();
                String a2 = 角色装备背包.getValueAt(i, 2).toString();
                装备背包物品序号.setText(a);
                装备背包物品代码.setText(a1);
                装备背包物品名字.setText(a2);
            }
        });
    }

    private void 刷新角色消耗背包() {
        for (int i = ((DefaultTableModel) (this.角色消耗背包.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色消耗背包.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid =" + this.角色ID.getText() + " && inventorytype = 2");
            rs = ps.executeQuery();
            while (rs.next()) {

                ((DefaultTableModel) 角色消耗背包.getModel()).insertRow(角色消耗背包.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色消耗背包.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色消耗背包.getSelectedRow();
                String a = 角色消耗背包.getValueAt(i, 0).toString();
                String a1 = 角色消耗背包.getValueAt(i, 1).toString();
                //String a2 = 角色消耗背包.getValueAt(i, 2).toString();
                消耗背包物品序号.setText(a);
                消耗背包物品代码.setText(a1);
                //消耗背包物品名字.setText(a2);
            }
        });
    }

    private void 刷新角色特殊背包() {
        for (int i = ((DefaultTableModel) (this.角色特殊背包.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色特殊背包.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid =" + this.角色ID.getText() + " && inventorytype = 5");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 角色特殊背包.getModel()).insertRow(角色特殊背包.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色特殊背包.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色特殊背包.getSelectedRow();
                String a = 角色特殊背包.getValueAt(i, 0).toString();
                String a1 = 角色特殊背包.getValueAt(i, 1).toString();
                //String a2 = 角色特殊背包.getValueAt(i, 2).toString();
                特殊背包物品序号.setText(a);
                特殊背包物品代码.setText(a1);
                //特殊背包物品名字.setText(a2);
            }
        });
    }

    private void 刷新角色游戏仓库() {
        for (int i = ((DefaultTableModel) (this.角色游戏仓库.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色游戏仓库.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE accountid =" + this.账号ID.getText());
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 角色游戏仓库.getModel()).insertRow(角色游戏仓库.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色游戏仓库.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色游戏仓库.getSelectedRow();
                String a = 角色游戏仓库.getValueAt(i, 0).toString();
                String a1 = 角色游戏仓库.getValueAt(i, 1).toString();
                //String a2 = 角色游戏仓库.getValueAt(i, 2).toString();
                游戏仓库物品序号.setText(a);
                游戏仓库物品代码.setText(a1);
                //游戏仓库物品名字.setText(a2);
            }
        });
    }

    private void 刷新角色商城仓库() {
        for (int i = ((DefaultTableModel) (this.角色商城仓库.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色商城仓库.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM csitems WHERE accountid =" + this.账号ID.getText());
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 角色商城仓库.getModel()).insertRow(角色商城仓库.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色商城仓库.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色商城仓库.getSelectedRow();
                String a = 角色商城仓库.getValueAt(i, 0).toString();
                String a1 = 角色商城仓库.getValueAt(i, 1).toString();
                //String a2 = 角色商城仓库.getValueAt(i, 2).toString();
                商城仓库物品序号.setText(a);
                商城仓库物品代码.setText(a1);
                //商城仓库物品名字.setText(a2);
            }
        });
    }

    private void 刷新角色点券拍卖行() {
        for (int i = ((DefaultTableModel) (this.角色点券拍卖行.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色点券拍卖行.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM auctionitems WHERE characterid =" + this.角色ID.getText());
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 角色点券拍卖行.getModel()).insertRow(角色点券拍卖行.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("characterName")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色点券拍卖行.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色点券拍卖行.getSelectedRow();
                String a = 角色点券拍卖行.getValueAt(i, 0).toString();
                角色点券拍卖行序号.setText(a);
            }
        });
    }

    private void 刷新角色金币拍卖行() {
        for (int i = ((DefaultTableModel) (this.角色金币拍卖行.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色金币拍卖行.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM auctionitems1 WHERE characterid =" + this.角色ID.getText());
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 角色金币拍卖行.getModel()).insertRow(角色金币拍卖行.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("characterName")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色金币拍卖行.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色金币拍卖行.getSelectedRow();
                String a = 角色金币拍卖行.getValueAt(i, 0).toString();
                角色金币拍卖行序号.setText(a);
            }
        });
    }

    private void 刷新角色其他背包() {
        for (int i = ((DefaultTableModel) (this.角色其他背包.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色其他背包.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid =" + this.角色ID.getText() + " && inventorytype = 4");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 角色其他背包.getModel()).insertRow(角色其他背包.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色其他背包.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色其他背包.getSelectedRow();
                String a = 角色其他背包.getValueAt(i, 0).toString();
                String a1 = 角色其他背包.getValueAt(i, 1).toString();
                //String a2 = 角色其他背包.getValueAt(i, 2).toString();
                其他背包物品序号.setText(a);
                其他背包物品代码.setText(a1);
                //其他背包物品名字.setText(a2);
            }
        });
    }

    private void 刷新角色设置背包() {
        for (int i = ((DefaultTableModel) (this.角色设置背包.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色设置背包.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid =" + this.角色ID.getText() + " && inventorytype = 3");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 角色设置背包.getModel()).insertRow(角色设置背包.getRowCount(), new Object[]{
                    rs.getInt("inventoryitemid"),
                    rs.getInt("itemid"),
                    MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")),
                    rs.getInt("quantity")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色设置背包.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色设置背包.getSelectedRow();
                String a = 角色设置背包.getValueAt(i, 0).toString();
                String a1 = 角色设置背包.getValueAt(i, 1).toString();
                String a2 = 角色设置背包.getValueAt(i, 2).toString();
                设置背包物品序号.setText(a);
                设置背包物品代码.setText(a1);
                设置背包物品名字.setText(a2);
            }
        });
    }

    private void 刷新角色信息2() {
        for (int i = ((DefaultTableModel) (this.角色信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.角色信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters WHERE accountid =" + this.账号ID.getText() + "");
            rs = ps.executeQuery();

            while (rs.next()) {
                String 在线 = "";
                if (World.Find.findChannel(rs.getString("name")) > 0) {
                    在线 = "在线";
                } else {
                    在线 = "离线";
                }
                ((DefaultTableModel) 角色信息.getModel()).insertRow(角色信息.getRowCount(), new Object[]{
                    rs.getInt("id"),
                    rs.getInt("accountid"),
                    rs.getString("name"),
                    getJobNameById(rs.getInt("job")),
                    rs.getInt("level"),
                    rs.getInt("str"),
                    rs.getInt("dex"),
                    rs.getInt("luk"),
                    rs.getInt("int"),
                    rs.getInt("maxhp"),
                    rs.getInt("maxmp"),
                    rs.getInt("meso"),
                    rs.getInt("map"),
                    在线,
                    rs.getInt("gm"),
                    rs.getInt("hair"),
                    rs.getInt("face")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        角色信息.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 角色信息.getSelectedRow();
                String a = 角色信息.getValueAt(i, 0).toString();
                String a1 = 角色信息.getValueAt(i, 2).toString();
                String a2 = 角色信息.getValueAt(i, 4).toString();
                String a3 = 角色信息.getValueAt(i, 5).toString();
                String a4 = 角色信息.getValueAt(i, 6).toString();
                String a5 = 角色信息.getValueAt(i, 7).toString();
                String a6 = 角色信息.getValueAt(i, 8).toString();
                String a7 = 角色信息.getValueAt(i, 9).toString();
                String a8 = 角色信息.getValueAt(i, 10).toString();
                String a9 = 角色信息.getValueAt(i, 11).toString();
                String a10 = 角色信息.getValueAt(i, 12).toString();
                String a11 = 角色信息.getValueAt(i, 14).toString();
                String a12 = 角色信息.getValueAt(i, 15).toString();
                String a13 = 角色信息.getValueAt(i, 16).toString();
                角色ID.setText(a);
                角色昵称.setText(a1);
                等级.setText(a2);
                力量.setText(a3);
                敏捷.setText(a4);
                智力.setText(a5);
                运气.setText(a6);
                HP.setText(a7);
                MP.setText(a8);
                金币1.setText(a9);
                地图.setText(a10);
                GM.setText(a11);
                发型.setText(a12);
                脸型.setText(a13);
                //出售状态.setText(a8);
                //jTextField9.setText(a9);
            }
        });
    }

    public void 查询商店(int lx) {

        boolean result = this.查询商店.getText().matches("[0-9]+");
        if (lx == 0) {
            result = true;
        }
        if (result) {
            if (lx != 0) {
                if (Integer.parseInt(this.查询商店.getText()) < 0) {
                    JOptionPane.showMessageDialog(null, "[信息]:请填写正确的值。");
                    return;
                }
            }
            for (int i = ((DefaultTableModel) (this.游戏商店2.getModel())).getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) (this.游戏商店2.getModel())).removeRow(i);
            }
            try {
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = null;
                ResultSet rs = null;
                if (lx == 0) {
                    ps = con.prepareStatement("SELECT * FROM shopitems");
                } else {
                    ps = con.prepareStatement("SELECT * FROM shopitems WHERE shopid = " + Integer.parseInt(this.查询商店.getText()) + " ");
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    ((DefaultTableModel) 游戏商店2.getModel()).insertRow(游戏商店2.getRowCount(), new Object[]{
                        rs.getInt("shopitemid"),
                        rs.getInt("shopid"),
                        rs.getInt("itemid"),
                        rs.getInt("price"),
                        MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid"))
                    });
                }
                JOptionPane.showMessageDialog(null, "[信息]:商城物品查询成功。");
            } catch (SQLException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            游戏商店2.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int i = 游戏商店2.getSelectedRow();
                    String a = 游戏商店2.getValueAt(i, 0).toString();
                    String a1 = 游戏商店2.getValueAt(i, 1).toString();
                    String a2 = 游戏商店2.getValueAt(i, 2).toString();
                    String a3 = 游戏商店2.getValueAt(i, 3).toString();
                    //String a4 = 游戏商店2.getValueAt(i, 4).toString();
                    商品序号.setText(a);
                    商店代码.setText(a1);
                    商品物品代码.setText(a2);
                    商品售价金币.setText(a3);
                    // 商品名称.setText(a4);
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入你需要查询的商店ID。");
        }
    }

    private void 刷新家族信息() {
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;

            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters");
            rs = ps.executeQuery();

            /*   while (rs.next()) {
                ((DefaultTableModel) 角色信息.getModel()).insertRow(角色信息.getRowCount(), new Object[]{
                    rs.getString("name")
                });
            }*/
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = ((DefaultTableModel) (this.家族信息.getModel())).getRowCount() - 1; i >= 0; i--) {
            ((DefaultTableModel) (this.家族信息.getModel())).removeRow(i);
        }
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM guilds");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) 家族信息.getModel()).insertRow(家族信息.getRowCount(), new Object[]{
                    rs.getInt("guildid"),
                    rs.getString("name"),
                    //ooors.getInt("leader"),
                    //    NPCConversationManager.角色ID取名字(rs.getInt("leader")),
                    rs.getString("rank1title"),
                    rs.getString("rank2title"),
                    rs.getString("rank3title"),
                    rs.getString("rank4title"),
                    rs.getString("rank5title"),
                    rs.getString("GP")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        家族信息.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = 家族信息.getSelectedRow();
                String a1 = 家族信息.getValueAt(i, 0).toString();
                String a2 = 家族信息.getValueAt(i, 1).toString();
                String a3 = 家族信息.getValueAt(i, 2).toString();
                String a4 = 家族信息.getValueAt(i, 3).toString();
                String a5 = 家族信息.getValueAt(i, 4).toString();
                String a6 = 家族信息.getValueAt(i, 5).toString();
                String a7 = 家族信息.getValueAt(i, 6).toString();
                String a8 = 家族信息.getValueAt(i, 7).toString();

                家族ID.setText(a1);
                家族名称.setText(a2);
                家族族长.setText(a3);
                家族成员2.setText(a4);
                家族成员3.setText(a5);
                家族成员4.setText(a6);
                家族成员5.setText(a7);
                家族GP.setText(a8);
            }
        });
    }

}
