package eu.minewars.client.gui.plugins;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import eu.minewars.client.plugin.Plugin;
import eu.minewars.client.plugin.loader.PluginLoader;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.client.resources.ResourcePackRepository;

public class GuiPluginList extends GuiScreen {
	private final GuiScreen parentScreen;
	private List<ResourcePackListEntry> availableResourcePacks;
	private List<ResourcePackListEntry> selectedResourcePacks;

	/** List component that contains the available resource packs */
	//private GuiResourcePackAvailable availableResourcePacksList;

	/** List component that contains the selected resource packs */
	private GuiSlotPluginList pluginList;
	private boolean changed;

	public GuiPluginList(GuiScreen parentScreenIn) {
		this.parentScreen = parentScreenIn;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when
	 * the GUI is displayed and when the window resizes, the buttonList is cleared
	 * beforehand.
	 */
	public void initGui() {
		this.buttonList.add(
				new GuiOptionButton(2, this.width / 2 - 154, this.height - 48, "Ordner öffnen"));
		this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 4, this.height - 48, I18n.format("gui.done")));

		/*if (!this.changed) {
			this.availableResourcePacks = Lists.<ResourcePackListEntry>newArrayList();
			this.selectedResourcePacks = Lists.<ResourcePackListEntry>newArrayList();
			ResourcePackRepository resourcepackrepository = this.mc.getResourcePackRepository();
			resourcepackrepository.updateRepositoryEntriesAll();
			List<ResourcePackRepository.Entry> list = Lists
					.newArrayList(resourcepackrepository.getRepositoryEntriesAll());
			list.removeAll(resourcepackrepository.getRepositoryEntries());

			for (ResourcePackRepository.Entry resourcepackrepository$entry : list) {
				this.availableResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry));
			}

			ResourcePackRepository.Entry resourcepackrepository$entry2 = resourcepackrepository.getResourcePackEntry();

			if (resourcepackrepository$entry2 != null) {
				this.selectedResourcePacks
						.add(new ResourcePackListEntryServer(this, resourcepackrepository.getResourcePackInstance()));
			}

			for (ResourcePackRepository.Entry resourcepackrepository$entry1 : Lists
					.reverse(resourcepackrepository.getRepositoryEntries())) {
				this.selectedResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry1));
			}

			this.selectedResourcePacks.add(new ResourcePackListEntryDefault(this));
		}*/

		/*this.availableResourcePacksList = new GuiResourcePackAvailable(this.mc, 200, this.height,
				this.availableResourcePacks);
		this.availableResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 - 4 - 200);
		this.availableResourcePacksList.registerScrollButtons(7, 8);*/
		List<PluginListEntry> plugins = new ArrayList<PluginListEntry>();
		for(Plugin p : PluginLoader.getPlugins()) {
			plugins.add(new PluginListEntry(p.getDescription()));
		}
		this.pluginList = new GuiSlotPluginList(this.mc, 200, this.height,
				plugins);
		this.pluginList.setSlotXBoundsFromLeft(this.width / 2 - 4 -200);
		this.pluginList.registerScrollButtons(7, 8);
	}

	/**
	 * Handles mouse input.
	 */
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		this.pluginList.handleMouseInput();
		//this.availableResourcePacksList.handleMouseInput();
	}

	public boolean hasResourcePackEntry(ResourcePackListEntry p_146961_1_) {
		return this.selectedResourcePacks.contains(p_146961_1_);
	}

	public List<ResourcePackListEntry> getListContaining(ResourcePackListEntry p_146962_1_) {
		return this.hasResourcePackEntry(p_146962_1_) ? this.selectedResourcePacks : this.availableResourcePacks;
	}

	public List<ResourcePackListEntry> getAvailableResourcePacks() {
		return this.availableResourcePacks;
	}

	public List<ResourcePackListEntry> getSelectedResourcePacks() {
		return this.selectedResourcePacks;
	}

	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed for
	 * buttons)
	 */
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 2) {
				File file1 = this.mc.getResourcePackRepository().getDirResourcepacks();
				OpenGlHelper.openFile(file1);
			} else if (button.id == 1) {
				if (this.changed) {
					List<ResourcePackRepository.Entry> list = Lists.<ResourcePackRepository.Entry>newArrayList();

					for (ResourcePackListEntry resourcepacklistentry : this.selectedResourcePacks) {
						if (resourcepacklistentry instanceof ResourcePackListEntryFound) {
							list.add(((ResourcePackListEntryFound) resourcepacklistentry).getResourcePackEntry());
						}
					}

					Collections.reverse(list);
					this.mc.getResourcePackRepository().setRepositories(list);
					this.mc.gameSettings.resourcePacks.clear();
					this.mc.gameSettings.incompatibleResourcePacks.clear();

					for (ResourcePackRepository.Entry resourcepackrepository$entry : list) {
						this.mc.gameSettings.resourcePacks.add(resourcepackrepository$entry.getResourcePackName());

						if (resourcepackrepository$entry.getPackFormat() != 3) {
							this.mc.gameSettings.incompatibleResourcePacks
									.add(resourcepackrepository$entry.getResourcePackName());
						}
					}

					this.mc.gameSettings.saveOptions();
					this.mc.refreshResources();
				}

				this.mc.displayGuiScreen(this.parentScreen);
			}
		}
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		//this.availableResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
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
		//this.availableResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
		this.pluginList.drawScreen(mouseX, mouseY, partialTicks);
		this.drawCenteredString(this.fontRendererObj, "Plugins", this.width / 2, 16, 16777215);
		this.drawCenteredString(this.fontRendererObj, "(Plugins hier einfügen)", this.width / 2 - 77,
				this.height - 26, 8421504);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/**
	 * Marks the selected resource packs list as changed to trigger a resource
	 * reload when the screen is closed
	 */
	public void markChanged() {
		this.changed = true;
	}
}
