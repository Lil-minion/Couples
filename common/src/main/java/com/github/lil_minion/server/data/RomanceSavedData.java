package com.github.lil_minion.server.data;

import com.github.lil_minion.Couples;
import com.github.lil_minion.model.relationship.romance.Romance;
import com.github.lil_minion.utils.RomanceUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RomanceSavedData extends SavedData {

    public static final Map<UUID, List<Romance>> ROMANCE_MAP = new HashMap<>();

    // Only used during I/O operations, should stay private
    private static final Set<Romance> ROMANCE_SET = new HashSet<>();

    public static SavedData INSTANCE;

    public static void createServerState(MinecraftServer server) {
        INSTANCE = server.overworld().getDataStorage().computeIfAbsent(
                new Factory<>(RomanceSavedData::create, RomanceSavedData::load,
                        DataFixTypes.LEVEL), Couples.MOD_ID);
        INSTANCE.setDirty();
    }

    public static RomanceSavedData create() {
        return new RomanceSavedData();
    }

    public static RomanceSavedData load(CompoundTag tag, HolderLookup.Provider registryLookup) {
        ROMANCE_MAP.clear();
        CompoundTag compoundTag = tag.getCompound("couples.romance_data");

        for (String key : compoundTag.getAllKeys()) {
            CompoundTag romanceCompound = compoundTag.getCompound(key);
            UUID player1 = romanceCompound.getUUID("player1");
            UUID player2 = romanceCompound.getUUID("player2");
            int timesKissed = romanceCompound.getInt("timesKissed");
            int timesFlirt = romanceCompound.getInt("timesFlirt");
            int hearths = romanceCompound.getInt("hearths");


            Romance romance = new Romance(player1, player2);
            romance.setTimesKissed(timesKissed);
            romance.setTimesFlirted(timesFlirt);
            romance.setHearths(hearths);

            ROMANCE_SET.add(romance);
        }

        ROMANCE_SET.forEach(RomanceUtil::loadRomance);
        ROMANCE_SET.clear();

        return new RomanceSavedData();
    }

    @Override
    public @NotNull CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ROMANCE_MAP.values().forEach(ROMANCE_SET::addAll);
        CompoundTag romancesTag = new CompoundTag();

        int i = 0;
        for (Romance romance : ROMANCE_SET) {
            CompoundTag romanceTag = new CompoundTag();
            romanceTag.putUUID("player1", romance.getPlayer1());
            romanceTag.putUUID("player2", romance.getPlayer2());
            romanceTag.putInt("timesKissed", romance.getTimesKissed());
            romanceTag.putInt("timesFlirt", romance.getTimesFlirted());
            romanceTag.putInt("hearths", romance.getHearths());

            CompoundTag romanceInteractions = new CompoundTag();
            romancesTag.put("" + i, romanceTag);
            i++;
        }

        ROMANCE_SET.clear();
        tag.put("couples.romance_data", romancesTag);
        return tag;
    }

}
