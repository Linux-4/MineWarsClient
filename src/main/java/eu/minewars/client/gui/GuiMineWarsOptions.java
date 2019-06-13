package eu.minewars.client.gui;

import java.io.IOException;

import eu.minewars.client.MineWarsClient;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiMineWarsOptions extends GuiScreen {
	private final GuiScreen lastScreen;
	private String title = MineWarsClient.NAME;

	public GuiMineWarsOptions(GuiScreen lastScreen) {
		this.lastScreen = lastScreen;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when
	 * the GUI is displayed and when the window resizes, the buttonList is cleared
	 * beforehand.
	 */
	public void initGui() {
		this.buttonList
				.add(new GuiButton(110, this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20, "Nicht verfügbar!"));
		this.buttonList
				.add(new GuiButton(106, this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20, "Nicht verfügbar!"));
		this.buttonList
				.add(new GuiButton(101, this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20, "Nicht verfügbar!"));
		this.buttonList
				.add(new GuiButton(100, this.width / 2 + 5, this.height / 6 + 72 - 6, 150, 20, "Nicht verfügbar!"));
		this.buttonList
				.add(new GuiButton(102, this.width / 2 - 155, this.height / 6 + 96 - 6, 150, 20, "Nicht verfügbar!"));
		this.buttonList
				.add(new GuiButton(103, this.width / 2 + 5, this.height / 6 + 96 - 6, 150, 20, "Nicht verfügbar!"));
		this.buttonList
				.add(new GuiButton(105, this.width / 2 - 155, this.height / 6 + 120 - 6, 150, 20, "Nicht verfügbar!"));
		this.buttonList
				.add(new GuiButton(104, this.width / 2 + 5, this.height / 6 + 120 - 6, 150, 20, "Nicht verfügbar!"));
		this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done")));
	}

	/**
	 * Fired when a key is typed (except F11 which toggles full screen). This is the
	 * equivalent of KeyListener.keyTyped(KeyEvent e). Args : character (character
	 * on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1) {
			this.mc.gameSettings.saveOptions();
		}

		super.keyTyped(typedChar, keyCode);
	}

	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed for
	 * buttons)
	 */
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 200) {
				this.mc.displayGuiScreen(this.lastScreen);
			}
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
