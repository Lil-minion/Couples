package com.github.lil_minion.utils;

import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;

import java.util.List;
import java.util.Random;

public class EffectUtil {

    public static final Random RANDOM = new Random();

    public static void spawnFireworksNearby(ServerPlayer player, FireworkExplosion.Shape shape, int color, int flightDuration) {
        // Get player location
        int x = player.getBlockX();
        int y = player.getBlockY();
        int z = player.getBlockZ();

        //Create rocket stack and add firework components
        IntList colors = IntLists.singleton(color);
        ItemStack rocketStack = new ItemStack(Items.FIREWORK_ROCKET);
        FireworkExplosion explosion = new FireworkExplosion(shape, colors, colors, true, true);
        Fireworks fireworks = new Fireworks(flightDuration, List.of(explosion));
        DataComponentPatch patch = DataComponentPatch.builder().set(DataComponents.FIREWORKS, fireworks).build();
        rocketStack.applyComponents(patch);
        FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(player.level(), player,
                player.getRandomX(10), y, player.getRandomZ(10), rocketStack);
        player.level().addFreshEntity(fireworkRocketEntity);
    }

    public static void spawnParticlesNearby(ServerPlayer player, ParticleOptions particleOptions, int particleCount) {
        int x = player.getBlockX();
        int y = player.getBlockY();
        int z = player.getBlockZ();
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(particleOptions, x, y, z, particleCount, 1, 1, 1, 1);
        }
    }
}


