package eu.minewars.client.gui.plugins;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.text.TextFormatting;

public class GuiSlotPluginList extends GuiListExtended {
	
	protected final Minecraft mc;
	protected final List<PluginListEntry> resourcePackEntries;

	public GuiSlotPluginList(Minecraft mcIn, int p_i45055_2_, int p_i45055_3_,
			List<PluginListEntry> plugins) {
		super(mcIn, p_i45055_2_, p_i45055_3_, 32, p_i45055_3_ - 55 + 4, 36);
		this.mc = mcIn;
		this.resourcePackEntries = plugins;
		this.centerListVertically = false;
		this.setHasListHeader(true, (int) ((float) mcIn.fontRendererObj.FONT_HEIGHT * 1.5F));
	}

	/**
	 * Handles drawing a list's header row.
	 */
	protected void drawListHeader(int insideLeft, int insideTop, Tessellator tessellatorIn) {
		String s = TextFormatting.UNDERLINE + "" + TextFormatting.BOLD + this.getListHeader();
		this.mc.fontRendererObj.drawString(s,
				insideLeft + this.width / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2,
				Math.min(this.top + 3, insideTop), 16777215);
	}

	protected String getListHeader() {
		return "Plugins";
	}

	public List<PluginListEntry> getList() {
		return this.resourcePackEntries;
	}

	protected int getSize() {
		return this.getList().size();
	}

	/**
	 * Gets the IGuiListEntry object for the given index
	 */
	public PluginListEntry getListEntry(int index) {
		return this.getList().get(index);
	}

	/**
	 * Gets the width of the list
	 */
	public int getListWidth() {
		return this.width;
	}

	protected int getScrollBarX() {
		return this.right - 6;
	}
}
