/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package server;

import constants.GameConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tools.Randomizer;

public class RandomRewards {

    private static List<Integer> compiledGold = null, compiledSilver = null, compiledFishing = null, compiledPeanut = null,
	compiledEvent = null, compiledEventC = null, compiledEventB = null, compiledEventA = null, compiledDrops = null, 
            compiledDropsB = null, compiledDropsA = null, tenPercent = null;

    static {
        // Gold Box
        List<Integer> returnArray = new ArrayList<Integer>();
        processRewards(returnArray, GameConstants.goldrewards);
        compiledGold = returnArray;

        // Silver Box
        returnArray = new ArrayList<Integer>();
        processRewards(returnArray, GameConstants.silverrewards);
        compiledSilver = returnArray;

        // Fishing Rewards
        returnArray = new ArrayList<Integer>();
        processRewards(returnArray, GameConstants.fishingReward);
        compiledFishing = returnArray;

        // Event Rewards
        returnArray = new ArrayList<Integer>();
        processRewards(returnArray, GameConstants.eventCommonReward);
        compiledEventC = returnArray;

        returnArray = new ArrayList<Integer>();
        processRewards(returnArray, GameConstants.eventUncommonReward);
        compiledEventB = returnArray;

        returnArray = new ArrayList<Integer>();
        processRewards(returnArray, GameConstants.eventRareReward);
        processRewardsSimple(returnArray, GameConstants.tenPercent);
        processRewardsSimple(returnArray, GameConstants.tenPercent);//hack: chance = 2
        compiledEventA = returnArray;

        returnArray = new ArrayList<Integer>();
        processRewards(returnArray, GameConstants.eventSuperReward);
        compiledEvent = returnArray;
        
        returnArray = new ArrayList<Integer>();
        processRewards(returnArray, GameConstants.peanuts);
        compiledPeanut = returnArray;

        returnArray = new ArrayList<Integer>();
	processRewardsSimple(returnArray, GameConstants.normalDrops);
	compiledDrops = returnArray;
        
        returnArray = new ArrayList<Integer>();
	processRewardsSimple(returnArray, GameConstants.rareDrops);
	compiledDropsB = returnArray;

        returnArray = new ArrayList<Integer>();
	processRewardsSimple(returnArray, GameConstants.superDrops);
	compiledDropsA = returnArray;

        returnArray = new ArrayList<Integer>();
        processRewardsSimple(returnArray, GameConstants.tenPercent);
	tenPercent = returnArray;
    }

    private static void processRewards(final List<Integer> returnArray, final int[] list) {
        int lastitem = 0;
        for (int i = 0; i < list.length; i++) {
            if (i % 2 == 0) { // Even
                lastitem = list[i];
            } else { // Odd
                for (int j = 0; j < list[i]; j++) {
                    returnArray.add(lastitem);
                }
            }
        }
        Collections.shuffle(returnArray);
    }

    private static void processRewardsSimple(final List<Integer> returnArray, final int[] list) {
        for (int i = 0; i < list.length; i++) {
            returnArray.add(list[i]);
        }
        Collections.shuffle(returnArray);
    }

    public static int getGoldBoxReward() {
        return compiledGold.get(Randomizer.nextInt(compiledGold.size()));
    }

    public static int getSilverBoxReward() {
        return compiledSilver.get(Randomizer.nextInt(compiledSilver.size()));
    }

    public static int getFishingReward() {
        return compiledFishing.get(Randomizer.nextInt(compiledFishing.size()));
    }

    public static int getPeanutReward() {
        return compiledPeanut.get(Randomizer.nextInt(compiledPeanut.size()));
    }

    public static int getEventReward() {
        final int chance = Randomizer.nextInt(101);
        if (chance < 66) {
            return compiledEventC.get(Randomizer.nextInt(compiledEventC.size()));
        } else if (chance < 86) {
            return compiledEventB.get(Randomizer.nextInt(compiledEventB.size()));
        } else if (chance < 96) {
            return compiledEventA.get(Randomizer.nextInt(compiledEventA.size()));
        } else {
            return compiledEvent.get(Randomizer.nextInt(compiledEvent.size()));
        }
    }

    public static int getDropReward() {
        final int chance = Randomizer.nextInt(101);
        if (chance < 76) {
            return compiledDrops.get(Randomizer.nextInt(compiledDrops.size()));
        } else if (chance < 96) {
            return compiledDropsB.get(Randomizer.nextInt(compiledDropsB.size()));
        } else {
            return compiledDropsA.get(Randomizer.nextInt(compiledDropsA.size()));
        }
    }

    public static List<Integer> getTenPercent() {
	return tenPercent;
    }

    static void load() {
        //Empty method to initialize class.
    }
}
