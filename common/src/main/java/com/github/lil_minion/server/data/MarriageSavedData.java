package com.github.lil_minion.server.data;

import com.github.lil_minion.Couples;
import com.github.lil_minion.model.marriage.Marriage;
import com.github.lil_minion.model.marriage.MarriageInteraction;
import com.github.lil_minion.model.marriage.MarriageInteractionType;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MarriageSavedData extends SavedData {

    public static final Map<UUID, Marriage> MARRIAGE_MAP = new HashMap<>();
    public static final Map<UUID, UUID> MARRIAGE_PROPOSAL_MAP = new HashMap<>();

    public static SavedData INSTANCE;

    public static void createServerState(MinecraftServer server) {
        INSTANCE = server.overworld().getDataStorage().computeIfAbsent(
                new Factory<>(MarriageSavedData::create, MarriageSavedData::load,
                        DataFixTypes.LEVEL), Couples.MOD_ID + ".marriage_data");
        INSTANCE.setDirty();
    }

    public static MarriageSavedData create() {
        return new MarriageSavedData();
    }

    public static MarriageSavedData load(CompoundTag tag, HolderLookup.Provider registryLookup) {
        MARRIAGE_MAP.clear();
        CompoundTag compoundTag = tag.getCompound("couples.marriage_data");

        for (String key : compoundTag.getAllKeys()) {
            CompoundTag marriageCompound = compoundTag.getCompound(key);
            UUID player1 = marriageCompound.getUUID("player1");
            UUID player2 = marriageCompound.getUUID("player2");
            long timeOfMarriage = marriageCompound.getLong("timeOfMarriage");
            int timesKissed = marriageCompound.getInt("timesKissed");
            int hearths = marriageCompound.getInt("hearths");
            int hearthsEarned = marriageCompound.getInt("hearthsEarned");
            int heartsLost = marriageCompound.getInt("heartsLost");
            int timesSleptTogether = marriageCompound.getInt("timesSleptTogether");
            int timesSleptApart = marriageCompound.getInt("timesSleptApart");
            int giftsGiven = marriageCompound.getInt("giftsGiven");
            int rumorCount = marriageCompound.getInt("rumorCount");

            List<MarriageInteraction> interactionList = new ArrayList<>();
            CompoundTag marriageInteractions = marriageCompound.getCompound("marriageInteractions");
            for (String interactionId : marriageInteractions.getAllKeys()) {
                CompoundTag interaction = marriageInteractions.getCompound(interactionId);
                MarriageInteractionType type = MarriageInteractionType.fromString(interaction.getString("type"));
                long timeOfInteraction = interaction.getLong("timeOfInteraction");
                int hearthsChanged = interaction.getInt("hearthsChanged");
                interactionList.add(new MarriageInteraction(type, timeOfInteraction, hearthsChanged));
            }

            Marriage marriage = new Marriage(player1, player2, timeOfMarriage, timesKissed);
            marriage.setHearths(hearths);
            marriage.setHearthsEarned(hearthsEarned);
            marriage.setHeartsLost(heartsLost);
            marriage.setTimesSleptTogether(timesSleptTogether);
            marriage.setTimesSleptApart(timesSleptApart);
            marriage.setGiftsGiven(giftsGiven);
            marriage.setRumorCount(rumorCount);
            marriage.getInteractionsList().addAll(interactionList);

            MARRIAGE_MAP.put(player1, marriage);
            MARRIAGE_MAP.put(player2, marriage);
        }

        return new MarriageSavedData();
    }

    @Override
    public @NotNull CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        CompoundTag compoundTag = new CompoundTag();

        for (Marriage marriage : MARRIAGE_MAP.values()) {
            CompoundTag marriageTag = new CompoundTag();
            marriageTag.putUUID("player1", marriage.getPlayer1());
            marriageTag.putUUID("player2", marriage.getPlayer2());
            marriageTag.putLong("timeOfMarriage", marriage.getTimeOfMarriage());
            marriageTag.putInt("timesKissed", marriage.getTimesKissed());
            marriageTag.putInt("hearths", marriage.getHearths());
            marriageTag.putInt("hearthsEarned", marriage.getHearthsEarned());
            marriageTag.putInt("heartsLost", marriage.getHeartsLost());
            marriageTag.putInt("timesSleptTogether", marriage.getTimesSleptTogether());
            marriageTag.putInt("timesSleptApart", marriage.getTimesSleptApart());
            marriageTag.putInt("giftsGiven", marriage.getGiftsGiven());
            marriageTag.putInt("rumorCount", marriage.getRumorCount());

            CompoundTag marriageInteractions = new CompoundTag();
            int i = 0;
            for (MarriageInteraction interaction : marriage.getInteractionsList()) {
                CompoundTag marriageInteraction = new CompoundTag();
                marriageInteraction.putString("type", interaction.getInteractionType().getType());
                marriageInteraction.putLong("timeOfInteraction", interaction.getTimeOfInteraction());
                marriageInteraction.putInt("hearthsChanged", interaction.getHearthsChanged());

                marriageInteractions.put("" + i, marriageInteraction);
                i++;
            }
            marriageTag.put("marriageInteractions", marriageInteractions);

            compoundTag.put(marriage.getPlayer1().toString(), marriageTag);
        }

        tag.put("couples.marriage_data", compoundTag);
        return tag;
    }

}
