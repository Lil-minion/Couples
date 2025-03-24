package com.github.lil_minion.server.data;

import com.github.lil_minion.Couples;
import com.github.lil_minion.model.relationship.marriage.Marriage;
import com.github.lil_minion.model.relationship.marriage.MarriageInteraction;
import com.github.lil_minion.model.relationship.marriage.MarriageInteractionType;
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

    // Only used during I/O operations, should stay private
    private static final Set<Marriage> MARRIAGE_SET = new HashSet<>();

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
            Marriage marriage = loadMarriage(marriageCompound);
            MARRIAGE_SET.add(marriage);
        }

        for (Marriage marriage : MARRIAGE_SET) {
            MARRIAGE_MAP.put(marriage.getPlayer1(), marriage);
            MARRIAGE_MAP.put(marriage.getPlayer2(), marriage);
        }
        MARRIAGE_SET.clear();

        return new MarriageSavedData();
    }

    private static Marriage loadMarriage(CompoundTag marriageCompound) {
        UUID player1 = marriageCompound.getUUID("player1");
        UUID player2 = marriageCompound.getUUID("player2");
        long timeOfMarriage = marriageCompound.getLong("timeOfMarriage");
        Marriage marriage = new Marriage(player1, player2, timeOfMarriage);

        marriage.setHearths(marriageCompound.getInt("hearths"));
        marriage.setTimesKissed(marriageCompound.getInt("timesKissed"));
        marriage.setTimesFlirted(marriageCompound.getInt("timesFlirted"));
        marriage.setHearthsEarned(marriageCompound.getInt("hearthsEarned"));
        marriage.setHeartsLost(marriageCompound.getInt("heartsLost"));
        marriage.setTimesSleptTogether(marriageCompound.getInt("timesSleptTogether"));
        marriage.setTimesSleptApart(marriageCompound.getInt("timesSleptApart"));
        marriage.setGiftsGiven(marriageCompound.getInt("giftsGiven"));
        marriage.setRumorCount(marriageCompound.getInt("rumorCount"));

        List<MarriageInteraction> interactionList = loadInteractions(marriageCompound.getCompound("marriageInteractions"));
        marriage.getInteractionsList().addAll(interactionList);

        return marriage;
    }

    private static List<MarriageInteraction> loadInteractions(CompoundTag marriageInteractions) {
        List<MarriageInteraction> interactionList = new ArrayList<>();
        for (String interactionId : marriageInteractions.getAllKeys()) {
            CompoundTag interaction = marriageInteractions.getCompound(interactionId);
            MarriageInteractionType type = MarriageInteractionType.fromString(interaction.getString("type"));
            long timeOfInteraction = interaction.getLong("timeOfInteraction");
            int hearthsChanged = interaction.getInt("hearthsChanged");
            interactionList.add(new MarriageInteraction(type, timeOfInteraction, hearthsChanged));
        }
        return interactionList;
    }

    @Override
    public @NotNull CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        MARRIAGE_SET.addAll(MARRIAGE_MAP.values());
        CompoundTag compoundTag = new CompoundTag();

        for (Marriage marriage : MARRIAGE_SET) {
            CompoundTag marriageTag = saveMarriage(marriage);
            compoundTag.put(marriage.getPlayer1().toString(), marriageTag);
        }

        MARRIAGE_SET.clear();
        tag.put("couples.marriage_data", compoundTag);
        return tag;
    }

    private CompoundTag saveMarriage(Marriage marriage) {
        CompoundTag marriageTag = new CompoundTag();
        marriageTag.putUUID("player1", marriage.getPlayer1());
        marriageTag.putUUID("player2", marriage.getPlayer2());
        marriageTag.putLong("timeOfMarriage", marriage.getTimeOfMarriage());
        marriageTag.putInt("timesKissed", marriage.getTimesKissed());
        marriageTag.putInt("timesFlirted", marriage.getTimesFlirted());
        marriageTag.putInt("hearths", marriage.getHearths());
        marriageTag.putInt("hearthsEarned", marriage.getHearthsEarned());
        marriageTag.putInt("heartsLost", marriage.getHeartsLost());
        marriageTag.putInt("timesSleptTogether", marriage.getTimesSleptTogether());
        marriageTag.putInt("timesSleptApart", marriage.getTimesSleptApart());
        marriageTag.putInt("giftsGiven", marriage.getGiftsGiven());
        marriageTag.putInt("rumorCount", marriage.getRumorCount());

        CompoundTag marriageInteractions = saveInteractions(marriage.getInteractionsList());
        marriageTag.put("marriageInteractions", marriageInteractions);
        return marriageTag;
    }

    private CompoundTag saveInteractions(List<MarriageInteraction> interactions) {
        CompoundTag marriageInteractions = new CompoundTag();
        int i = 0;
        for (MarriageInteraction interaction : interactions) {
            CompoundTag marriageInteraction = new CompoundTag();
            marriageInteraction.putString("type", interaction.getInteractionType().getType());
            marriageInteraction.putLong("timeOfInteraction", interaction.getTimeOfInteraction());
            marriageInteraction.putInt("hearthsChanged", interaction.getHearthsChanged());

            marriageInteractions.put(String.valueOf(i), marriageInteraction);
            i++;
        }
        return marriageInteractions;
    }

}
