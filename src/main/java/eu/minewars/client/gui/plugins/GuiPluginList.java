package eu.minewars.client.gui.plugins;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.minewars.client.plugin.Plugin;
import eu.minewars.client.plugin.loader.PluginLoader;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;

public class GuiPluginList extends GuiScreen {
	private final GuiScreen parentScreen;
	/** List component that contains the plugins */
	private GuiSlotPluginList pluginList;

	public GuiPluginList(GuiScreen parentScreenIn) {
		this.parentScreen = parentScreenIn;
	}

	private PluginListEntry selected;

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when
	 * the GUI is displayed and when the window resizes, the buttonList is cleared
	 * beforehand.
	 */
	public void initGui() {
		this.buttonList.add(new GuiOptionButton(2, this.width / 2 - 154, this.height - 48, "Ordner öffnen"));
		this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 4, this.height - 48, I18n.format("gui.done")));
		/*
		 * this.availableResourcePacksList = new GuiResourcePackAvailable(this.mc, 200,
		 * this.height, this.availableResourcePacks);
		 * this.availableResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 - 4 -
		 * 200); this.availableResourcePacksList.registerScrollButtons(7, 8);
		 */
		List<PluginListEntry> plugins = new ArrayList<PluginListEntry>();
		for (Plugin p : PluginLoader.getPlugins()) {
			plugins.add(new PluginListEntry(this, p.getDescription()));
		}
		this.pluginList = new GuiSlotPluginList(this.mc, this.width/2 /* 200 */, this.height, plugins);
		this.pluginList.setSlotXBoundsFromLeft(this.width/4/* this.width / 2 - 4 -200 */);
		this.pluginList.registerScrollButtons(7, 8);
	}

	/**
	 * Handles mouse input.
	 */
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		this.pluginList.handleMouseInput();
		// this.availableResourcePacksList.handleMouseInput();
	}

	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed for
	 * buttons)
	 */
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 2) {
				File file1 = new File(this.mc.mcDataDir, "plugins");
				OpenGlHelper.openFile(file1);
			} else if (button.id == 1) {
				this.mc.displayGuiScreen(this.parentScreen);
			}
		}
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		// this.availableResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
		this.pluginList.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/**
	 * Called when a mouse button is released.
	 */
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawBackground(0);
		// this.availableResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
		this.pluginList.drawScreen(mouseX, mouseY, partialTicks);
		this.drawCenteredString(this.fontRendererObj, "Plugins", this.width / 2, 16, 16777215);
		this.drawCenteredString(this.fontRendererObj, "(Plugins hier einfügen)", this.width / 2 - 77, this.height - 26,
				8421504);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void setSelected(PluginListEntry pluginListEntry) {
		this.selected = pluginListEntry;
		Plugin p = PluginLoader.getPlugin(selected.plugin.getName());
		if (p.getGUI() != null) {
			this.mc.displayGuiScreen(p.getGUI());
		}
	}
}
