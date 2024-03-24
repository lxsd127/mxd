/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server;

import client.MapleCharacter;
import client.SkillFactory;
import client.inventory.MapleInventoryIdentifier;
import constants.BeansConstants;
import constants.ServerConfig;
import constants.ServerConstants;
import database.DBConPool;
import database.DatabaseConnection;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.channel.MapleGuildRanking;
import handling.login.LoginInformationProvider;
import handling.login.LoginServer;
import handling.world.World;
import handling.world.family.MapleFamily;
import handling.world.guild.MapleGuild;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Timer.BuffTimer;
import server.Timer.CheatTimer;
import server.Timer.CloneTimer;
import server.Timer.EtcTimer;
import server.Timer.EventTimer;
import server.Timer.MapTimer;
import server.Timer.PingTimer;
import server.Timer.WorldTimer;
import server.events.MapleOxQuizFactory;
import server.life.MapleLifeFactory;
import server.life.MapleMonsterInformationProvider;
import server.life.MobSkillFactory;
import server.life.PlayerNPC;
import server.maps.MapleMap;
import server.maps.MapleMapFactory;
import server.quest.MapleQuest;
import tools.FileoutputUtil;

public class Start {
    public static boolean  是否控制台启动 = false;
    public static Map<String, Integer> ConfigValuesMap = new HashMap<>();
    public static long startTime = System.currentTimeMillis();
    public static final Start instance = new Start();

  //  public void run() throws InterruptedException {
    public final static void start(final String args[]) {
        GetConfigValues();
        long startQuestTime = System.currentTimeMillis();
        long start = System.currentTimeMillis();
        try (PreparedStatement ps = DBConPool.getInstance().getDataSource().getConnection().prepareStatement("UPDATE accounts SET loggedin = 0")) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
            throw new RuntimeException("执行中出现异常 - 无法连线到数据库.");
        }
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        System.setProperty("file.encoding", "utf-8");
        System.setProperty("path", "");
        System.out.println("【当前操作系统】: " + System.getProperty("sun.desktop"));
        System.out.println("【游戏版本】: " + ServerConfig.MAPLE_VERSION + "." + ServerConfig.MAPLE_PATCH);
        System.out.println("【正在启动世界服务器】： " + ServerProperties.getProperty("ZeroMS.serverName"));
        World.init();
        System.out.println("主机位置: " + ServerConfig.interface_ + ":" + LoginServer.PORT);
        System.out.println("客户端版本: " + ServerConfig.MAPLE_VERSION + "." + ServerConfig.MAPLE_PATCH);
        System.out.println("正在加载线程...");
        WorldTimer.getInstance().start();
        EtcTimer.getInstance().start();
        MapTimer.getInstance().start();
        CloneTimer.getInstance().start();
        EventTimer.getInstance().start();
        BuffTimer.getInstance().start();
        PingTimer.getInstance().start();
        MapleGuildRanking.getInstance().load();
        MapleGuild.loadAll(); //(this);
        MapleFamily.loadAll(); //(this);
        MapleLifeFactory.loadQuestCounts();
        MapleQuest.initQuests();
        MapleItemInformationProvider.getInstance().runEtc();
        MapleMonsterInformationProvider.getInstance().load();
        MapleItemInformationProvider.getInstance().runItems();
        try {
            SkillFactory.load();
        } catch (Exception e) {
            System.out.println(e);
        }
        LoginInformationProvider.getInstance();
        //BeansConstants.getInstance();
        RandomRewards.load();
        MapleOxQuizFactory.getInstance();
        MapleCarnivalFactory.getInstance();
        MobSkillFactory.getInstance();
        SpeedRunner.loadSpeedRuns();
        //System.out.println("Loading MTSStorage :::");
        //MTSStorage.load();
        MapleInventoryIdentifier.getInstance();
        CashItemFactory.getInstance().initialize();
        System.out.println("【正在加载】 游戏商品数量: " + 服务器游戏商品() + " 个");
        System.out.println("【正在加载】 商城商品数量: " + 服务器商城商品() + " 个");
        System.out.println("【正在加载】 玩家账号数量: " + 服务器账号() + " 个");
        System.out.println("【正在加载】 玩家角色数量: " + 服务器角色() + " 个");
        System.out.println("【正在加载】 玩家道具数量: " + 服务器道具() + " 个");
        System.out.println("【正在加载】 玩家技能数量: " + 服务器技能() + " 个");
        System.out.println("【正在加载】 自动存档线程");
        System.out.println("【正在加载】 启动记录在线时长线程");
        System.out.println("【正在加载】 启动服务端内存回收线程");
        System.out.println("【正在加载】 启动服务端地图回收线程");
        System.out.println("【正在加载】 处理怪物重生、CD、宠物、坐骑");
        System.out.println("【正在加载】 玩家NPC");
        System.out.println("【正在加载】 检测游戏复制道具系统");
        LoginServer.run_startup_configurations();
        ChannelServer.startChannel_Main();
        CashShopServer.run_startup_configurations();
        CheatTimer.getInstance().register(AutobanManager.getInstance(), 60000);
        Runtime.getRuntime().addShutdownHook(new Thread(new Shutdown()));
        World.registerRespawn();
        ShutdownServer.registerMBean();
        PlayerNPC.loadAll();// touch - so we see database problems early...
        MapleMonsterInformationProvider.getInstance().addExtra();
        LoginServer.setOn(); //now or later
        MapleMapFactory.loadCustomLife();
        RankingWorker.run();
        if (Boolean.parseBoolean(ServerProperties.getProperty("world.admin")) || ServerConstants.Use_Localhost) {
            System.out.println("管理员模式已开启");
        }

        if (Boolean.parseBoolean(ServerProperties.getProperty("world.logpackets"))) {
            System.out.println("数据包记录已开启.");
        }
        if (ServerConfig.USE_FIXED_IV) {
            System.out.println("反抓包已开启.");
        }
        在线时间(1);
        long now = System.currentTimeMillis() - start;
        long seconds = now / 1000;
        long ms = now % 1000;
        System.out.println("总加载时间: " + seconds + "秒 " + ms + "毫秒");
    }

    public static class Shutdown implements Runnable {

        @Override
        public void run() {
            ShutdownServer.getInstance().run();
            ShutdownServer.getInstance().run();
        }
    }
      public static void 在线时间(int time) {
        Timer.WorldTimer.getInstance().register(new Runnable() {

            @Override
            public void run() {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                if (hour == 0 && minute == 0) {
                    try {
                        Connection con = DatabaseConnection.getConnection();
                        try (PreparedStatement ps = con.prepareStatement("UPDATE accounts_info SET gamePoints = ?, updateTime = CURRENT_TIMESTAMP()")) {
                            ps.setInt(1, 0);
                            ps.executeUpdate();
                            ps.close();
                        }
                    } catch (SQLException Ex) {
                        System.err.println("更新角色帐号的在线时间出现错误 - 数据库更新失败." + Ex);
                    }
                }
                try {
                    for (ChannelServer chan : ChannelServer.getAllInstances()) {
                        for (MapleCharacter chr : chan.getPlayerStorage().getAllCharacters()) {
                            if (chr == null) {
                                continue;
                            }
                            if (hour == 0 && minute == 0) {
                                chr.set在线时间(0);
                                continue;
                            }
                            chr.gainGamePoints(1);
                            if (chr.get在线时间() < 5) {//chr.getGamePoints() < 5
                                chr.resetGamePointsPS();
                                chr.resetGamePointsPD();
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("在线时间出错:" + e);
                }
            }
        }, 60000 * time);
     }
      
        public static int 服务器角色() {
        int p = 0;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT id as DATA FROM characters WHERE id >=0");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    p += 1;
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("服务器角色？");
        }
        return p;
    }

    public static int 服务器账号() {
        int p = 0;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT id as DATA FROM accounts WHERE id >=0");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    p += 1;
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("服务器账号？");
        }
        return p;
    }

    public static int 服务器技能() {
        int p = 0;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT id as DATA FROM skills ");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    p += 1;
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("服务器技能？");
        }
        return p;
    }

    public static int 服务器道具() {
        int p = 0;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT inventoryitemid as DATA FROM inventoryitems WHERE inventoryitemid >=0");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    p += 1;
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("服务器道具？");
        }
        return p;
    }

    public static int 服务器商城商品() {
        int p = 0;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT serial as DATA FROM cashshop_modified_items WHERE serial >=0");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    p += 1;
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("服务器商城商品？");
        }
        return p;
    }  
   
        public static int 服务器游戏商品() {
        int p = 0;
        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT shopitemid as DATA FROM shopitems WHERE shopitemid >=0");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    p += 1;
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("服务器道具游戏商品？");
        }
        return p;
    }
        
           /**
     * * <其他>
     */
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
       }

