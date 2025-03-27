package com.github.lil_minion.client.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;

public class CustomBookViewScreen extends BookViewScreen {

    private final Screen parent;

    public CustomBookViewScreen(BookAccess access, Screen parent) {
        super(access);
        this.parent = parent;
    }

    @Override
    public void onClose() {
        minecraft.setScreen(parent);
    }

}
