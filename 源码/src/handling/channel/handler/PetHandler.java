/*
本源码由疯神论坛免费开源，仅供学习使用，请勿用于任何商业用途。

疯神论坛官网bbs.13mxd.com 交流群:6544394
*/
package handling.channel.handler;

import client.MapleCharacter;
import client.MapleClient;
import client.MapleDisease;
import client.inventory.Item;
import client.inventory.MapleInventoryType;
import client.inventory.MaplePet;
import client.inventory.PetCommand;
import client.inventory.PetDataFactory;
import constants.GameConstants;
import handling.world.MaplePartyCharacter;
import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.life.MapleMonster;
import server.maps.FieldLimitType;
import server.maps.MapleMapItem;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import server.movement.LifeMovementFragment;
import tools.packet.MaplePacketCreator;
import tools.Randomizer;
import tools.data.LittleEndianAccessor;
import tools.packet.PetPacket;

public class PetHandler {

    public static final void SpawnPet(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        //chr.updateTick(slea.readInt());
        slea.readInt();
        byte slot = slea.readByte();
        byte unk = slea.readByte();
        chr.spawnPet(slot, slea.readByte() > 0);

    }

    public static final void Pet_AutoPotion(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        slea.skip(9);
        chr.updateTick(slea.readInt());
        final byte slot = slea.readByte();
        slea.readByte();
        if (chr == null || !chr.isAlive() || chr.getMapId() == 749040100 || chr.getMap() == null || chr.hasDisease(MapleDisease.POTION)) {
            return;
        }
        final Item toUse = chr.getInventory(MapleInventoryType.USE).getItem(slot);

        if (toUse == null || toUse.getQuantity() < 1 || toUse.getItemId() != slea.readInt()) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        final long time = System.currentTimeMillis();
        if (chr.getNextConsume() > time) {
            chr.dropMessage(5, "暂时无法使用道具.");
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (!FieldLimitType.PotionUse.check(chr.getMap().getFieldLimit())) { //cwk quick hack
            if (MapleItemInformationProvider.getInstance().getItemEffect(toUse.getItemId()).applyTo(chr)) {
                MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, slot, (short) 1, false);
                if (chr.getMap().getConsumeItemCoolTime() > 0) {
                    chr.setNextConsume(time + (chr.getMap().getConsumeItemCoolTime() * 1000));
                }
            }
        } else {
            c.getSession().write(MaplePacketCreator.enableActions());
        }
    }

    public static final void PetChat(final int petid, final short command, final String text, MapleCharacter chr) {
        if (chr == null || chr.getMap() == null || chr.getPet(chr.getPetIndex(petid)) == null) {
            return;
        }
        chr.getMap().broadcastMessage(chr, PetPacket.petChat(chr.getId(), command, text, chr.getPetIndex(petid)), true);
    }

    public static final void PetCommand(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        final byte petIndex = chr.getPetIndex(slea.readInt());
        if (petIndex == -1) {
            return;
        }
        MaplePet pet = chr.getPet(petIndex);
        slea.skip(5);
        final byte command = slea.readByte();
        final PetCommand petCommand = PetDataFactory.getPetCommand(pet.getPetItemId(), (int) command);
        if (petCommand == null) {
            return;
        }
        boolean success = false;
        if (Randomizer.nextInt(99) <= petCommand.getProbability()) {
            success = true;
            if (pet.getCloseness() < 30000) {
                int newCloseness = pet.getCloseness() + (petCommand.getIncrease() * c.getChannelServer().getTraitRate());
                if (newCloseness > 30000) {
                    newCloseness = 30000;
                }
                pet.setCloseness(newCloseness);
                if (newCloseness >= GameConstants.getClosenessNeededForLevel(pet.getLevel() + 1)) {
                    pet.setLevel(pet.getLevel() + 1);
                    c.getSession().write(PetPacket.showOwnPetLevelUp(petIndex));
                    chr.getMap().broadcastMessage(PetPacket.showPetLevelUp(chr, petIndex));
                }
                c.getSession().write(PetPacket.updatePet(pet, chr.getInventory(MapleInventoryType.CASH).getItem((byte) pet.getInventoryPosition()), true));
            }
        }
        chr.getMap().broadcastMessage(PetPacket.commandResponse(chr.getId(), (byte) petCommand.getSkillId(), petIndex, success, false));
    }

    public static final void PetFood(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        int previousFullness = 100;
        MaplePet pet = null;
        if (chr == null) {
            return;
        }
        for (final MaplePet pets : chr.getPets()) {
            if (pets.getSummoned()) {
                if (pets.getFullness() < previousFullness) {
                    previousFullness = pets.getFullness();
                    pet = pets;
                }
            }
        }
        if (pet == null) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }

        c.getPlayer().updateTick(slea.readInt());
        short slot = slea.readShort();
        final int itemId = slea.readInt();
        Item petFood = c.getPlayer().getInventory(MapleInventoryType.USE).getItem(slot);
        if (petFood == null || petFood.getItemId() != itemId || petFood.getQuantity() <= 0 || itemId / 10000 != 212) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        boolean gainCloseness = false;

        if (Randomizer.nextInt(99) <= 50) {
            gainCloseness = true;
        }
        if (pet.getFullness() < 100) {
            int newFullness = pet.getFullness() + 30;
            if (newFullness > 100) {
                newFullness = 100;
            }
            pet.setFullness(newFullness);
            final byte index = chr.getPetIndex(pet);

            if (gainCloseness && pet.getCloseness() < 30000) {
                int newCloseness = pet.getCloseness() + 1;
                if (newCloseness > 30000) {
                    newCloseness = 30000;
                }
                pet.setCloseness(newCloseness);
                if (newCloseness >= GameConstants.getClosenessNeededForLevel(pet.getLevel() + 1)) {
                    pet.setLevel(pet.getLevel() + 1);

                    c.getSession().write(PetPacket.showOwnPetLevelUp(index));
                    chr.getMap().broadcastMessage(PetPacket.showPetLevelUp(chr, index));
                }
            }
            c.getSession().write(PetPacket.updatePet(pet, chr.getInventory(MapleInventoryType.CASH).getItem((byte) pet.getInventoryPosition()), true));
            chr.getMap().broadcastMessage(c.getPlayer(), PetPacket.commandResponse(chr.getId(), (byte) 1, index, true, true), true);
        } else {
            if (gainCloseness) {
                int newCloseness = pet.getCloseness() - 1;
                if (newCloseness < 0) {
                    newCloseness = 0;
                }
                pet.setCloseness(newCloseness);
                if (newCloseness < GameConstants.getClosenessNeededForLevel(pet.getLevel())) {
                    pet.setLevel(pet.getLevel() - 1);
                }
            }
            c.getSession().write(PetPacket.updatePet(pet, chr.getInventory(MapleInventoryType.CASH).getItem((byte) pet.getInventoryPosition()), true));
            chr.getMap().broadcastMessage(chr, PetPacket.commandResponse(chr.getId(), (byte) 1, chr.getPetIndex(pet), false, true), true);
        }
        MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, slot, (short) 1, true, false);
        c.getSession().write(MaplePacketCreator.enableActions());
    }

    public static final void MovePet(final LittleEndianAccessor slea, final MapleCharacter chr) {
        final int petId = slea.readInt();
        slea.readInt();
        final Point Pos = slea.readPos();
        final List<LifeMovementFragment> res = MovementParse.parseMovement(slea, 3, Pos);

        if (res != null && chr != null && !res.isEmpty() && chr.getMap() != null) { // map crash hack
            final byte slot = chr.getPetIndex(petId);
            final MaplePet pet = chr.getPet(slot);
            if (slot == -1) {
                return;
            }
            if (pet == null) {
                return;
            }
            pet.updatePosition(res);
            chr.getMap().broadcastMessage(chr, PetPacket.movePet(chr.getId(), Pos, slot, res), false);
            if (chr.hasBlockedInventory() || chr.getStat().pickupRange <= 0.0 || chr.inPVP()) {
                return;
            }
            chr.setScrolledPosition((short) 0);
            List<MapleMapObject> objects = chr.getMap().getMapObjectsInRange(chr.getTruePosition(), chr.getRange(), Arrays.asList(MapleMapObjectType.ITEM));
            for (LifeMovementFragment move : res) {
                final Point pp = move.getPosition();
                boolean foundItem = false;
                for (MapleMapObject mapitemz : objects) {
                    if (mapitemz instanceof MapleMapItem && (Math.abs(pp.x - mapitemz.getTruePosition().x) <= chr.getStat().pickupRange || Math.abs(mapitemz.getTruePosition().x - pp.x) <= chr.getStat().pickupRange) && (Math.abs(pp.y - mapitemz.getTruePosition().y) <= chr.getStat().pickupRange || Math.abs(mapitemz.getTruePosition().y - pp.y) <= chr.getStat().pickupRange)) {
                        final MapleMapItem mapitem = (MapleMapItem) mapitemz;
                        final Lock lock = mapitem.getLock();
                        lock.lock();
                        try {
                            if (mapitem.isPickedUp()) {
                                continue;
                            }
                            if (mapitem.getQuest() > 0 && chr.getQuestStatus(mapitem.getQuest()) != 1) {
                                continue;
                            }
                            if (mapitem.getOwner() != chr.getId() && mapitem.isPlayerDrop()) {
                                continue;
                            }
                            if (mapitem.getOwner() != chr.getId() && ((!mapitem.isPlayerDrop() && mapitem.getDropType() == 0) || (mapitem.isPlayerDrop() && chr.getMap().getEverlast()))) {
                                continue;
                            }
                            if (!mapitem.isPlayerDrop() && (mapitem.getDropType() == 1 || mapitem.getDropType() == 3) && mapitem.getOwner() != chr.getId()) {
                                continue;
                            }
                            if (mapitem.getDropType() == 2 && mapitem.getOwner() != chr.getId()) {
                                continue;
                            }
                            if (mapitem.getMeso() > 0) {
                                if (chr.getParty() != null && mapitem.getOwner() != chr.getId()) {
                                    final List<MapleCharacter> toGive = new LinkedList<MapleCharacter>();
                                    final int splitMeso = mapitem.getMeso() * 40 / 100;
                                    for (MaplePartyCharacter z : chr.getParty().getMembers()) {
                                        MapleCharacter m = chr.getMap().getCharacterById(z.getId());
                                        if (m != null && m.getId() != chr.getId()) {
                                            toGive.add(m);
                                        }
                                    }
                                    for (final MapleCharacter m : toGive) {
                                        m.gainMeso(splitMeso / toGive.size() + (m.getStat().hasPartyBonus ? (int) (mapitem.getMeso() / 20.0) : 0), true, true);
                                    }
                                    chr.gainMeso(mapitem.getMeso() - splitMeso, true, true);
                                } else {
                                    chr.gainMeso(mapitem.getMeso(), true, true);
                                }
                                InventoryHandler.removeItem_Pet(chr, mapitem, slot);
                                foundItem = true;
                            } else if (!MapleItemInformationProvider.getInstance().isPickupBlocked(mapitem.getItem().getItemId()) && mapitem.getItem().getItemId() / 10000 != 291) {
                                if (InventoryHandler.useItem(chr.getClient(), mapitem.getItemId())) {
                                    InventoryHandler.removeItem_Pet(chr, mapitem, slot);
                                } else if (MapleInventoryManipulator.checkSpace(chr.getClient(), mapitem.getItem().getItemId(), mapitem.getItem().getQuantity(), mapitem.getItem().getOwner())) {
                                    if (mapitem.getItem().getQuantity() >= 50 && mapitem.getItem().getItemId() == 2340000) {
                                        chr.getClient().setMonitored(true); //hack check
                                    }
                                    if (MapleInventoryManipulator.addFromDrop(chr.getClient(), mapitem.getItem(), true, mapitem.getDropper() instanceof MapleMonster, false)) {
                                        InventoryHandler.removeItem_Pet(chr, mapitem, slot);
                                        foundItem = true;
                                    }
                                }
                            }
                        } finally {
                            lock.unlock();
                        }
                    }
                }
                if (foundItem) {
                    return;
                }
            }
        }
    }

    public static void PetIgnoreTag(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) { // 170001
        int petSlot = slea.readInt();
        slea.readInt();
        final MaplePet pet = chr.getPet(chr.getPetIndex(petSlot));
        if ((pet == null) || (!MaplePet.PetFlag.PET_IGNORE_PICKUP.check(pet.getFlags()))) {
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        pet.clearExcluded();
        byte amount = slea.readByte();
        for (int i = 0; i < amount; i++) {
            pet.addExcluded(i, slea.readInt());
        }
    }
}
