package com.github.lil_minion.client.screen;

import com.github.lil_minion.model.relationship.interaction.InteractionRequest;
import com.github.lil_minion.utils.InteractionUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

/**
 * Represents a screen that displays an interaction request to the player.
 * This screen allows the player to accept or reject the interaction request.
 */
public class InteractionRequestScreen extends Screen {

    private final InteractionRequest request;
    private final LocalPlayer player;

    /**
     * Constructs a new InteractionRequestScreen.
     *
     * @param request The {@link InteractionRequest} to be displayed.
     * @param player  The local {@link LocalPlayer} who is receiving the request.
     */
    public InteractionRequestScreen(InteractionRequest request, LocalPlayer player) {
        super(switch (request.interactionRequestType()) {
            case KISS_REQUEST -> Component.literal("Kiss request");
            case FLIRT_REQUEST -> Component.literal("Flirt request");
            case MARRIAGE_PROPOSAL -> Component.literal("Marriage proposal");
        });
        this.request = request;
        this.player = player;
    }

    /**
     * Initializes the screen by adding the accept and reject buttons.
     */
    @Override
    protected void init() {
        // Accept button
        this.addRenderableWidget(Button.builder(Component.translatable("mco.invites.button.accept"), button -> {
            InteractionUtil.sendInteractionRequest(player, new InteractionRequest(
                    request.sender(), request.recipient(), request.timeStamp(),
                    request.interactionRequestType(), request.message(), true
            ));
            InteractionUtil.sendInteractionRequest(player, request);
            this.onClose();
        }).bounds(this.width / 2 - 100, this.height / 2 + 20, 100, 20).build());

        // Reject button
        this.addRenderableWidget(Button.builder(Component.translatable("mco.invites.button.reject"),
                button -> {this.onClose();}).bounds(this.width / 2, this.height / 2 + 20, 100, 20).build());
    }

    /**
     * Renders the screen, including the background, buttons, title, and custom message.
     *
     * @param graphics   The graphics context used for rendering.
     * @param mouseX     The X coordinate of the mouse cursor.
     * @param mouseY     The Y coordinate of the mouse cursor.
     * @param partialTicks The partial ticks for smooth rendering.
     */
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // Render the background
        renderTransparentBackground(graphics);

        // Render the buttons
        super.render(graphics, mouseX, mouseY, partialTicks);

        // Render the title (type of interaction)
        graphics.drawCenteredString(this.font, this.title.getString(), this.width / 2, this.height / 3, 0xFFFFFFFF);

        // Render the custom message
        graphics.drawCenteredString(this.font, request.message(), this.width / 2, this.height / 2 - 50, 0xFFFFFFFF);
    }

    /**
     * Closes the screen and returns to the previous screen.
     */
    @Override
    public void onClose() {
        this.minecraft.setScreen(null);
    }

    /**
     * Renders a blurred background for the screen.
     *
     * @param partialTick The partial ticks for smooth rendering.
     */
    @Override
    protected void renderBlurredBackground(float partialTick) {
        super.renderBlurredBackground(partialTick);
    }
}
