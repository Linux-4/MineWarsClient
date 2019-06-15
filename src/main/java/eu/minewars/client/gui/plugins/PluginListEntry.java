package eu.minewars.client.gui.plugins;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;

import eu.minewars.client.plugin.PluginDescription;
import eu.minewars.client.plugin.loader.PluginLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class PluginListEntry implements GuiListExtended.IGuiListEntry {
	private static final ResourceLocation RESOURCE_PACKS_TEXTURE = new ResourceLocation(
			"textures/gui/resource_packs.png");
	protected final Minecraft mc;
	protected final PluginDescription plugin;

	public PluginListEntry(PluginDescription plugin) {
		this.mc = Minecraft.getMinecraft();
		this.plugin = plugin;
	}

	public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_,
			int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {

		this.bindPluginIcon();
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
		String name = plugin.getName();
		String description = "By " + plugin.getAuthor();

		if (this.showHoverOverlay() && (this.mc.gameSettings.touchscreen || p_192634_8_)) {
			this.mc.getTextureManager().bindTexture(RESOURCE_PACKS_TEXTURE);
			Gui.drawRect(p_192634_2_, p_192634_3_, p_192634_2_ + 32, p_192634_3_ + 32, -1601138544);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			/*
			 * if (i < 3) { name = INCOMPATIBLE.getFormattedText(); description =
			 * INCOMPATIBLE_OLD.getFormattedText(); } else if (i > 3) { name =
			 * INCOMPATIBLE.getFormattedText(); description =
			 * INCOMPATIBLE_NEW.getFormattedText(); }
			 */
		}

		int i1 = this.mc.fontRendererObj.getStringWidth(name);

		if (i1 > 157) {
			name = this.mc.fontRendererObj.trimStringToWidth(name, 157 - this.mc.fontRendererObj.getStringWidth("..."))
					+ "...";
		}

		this.mc.fontRendererObj.drawStringWithShadow(name, (float) (p_192634_2_ + 32 + 2), (float) (p_192634_3_ + 1),
				16777215);
		List<String> list = this.mc.fontRendererObj.listFormattedStringToWidth(description, 157);

		for (int l = 0; l < 2 && l < list.size(); ++l) {
			this.mc.fontRendererObj.drawStringWithShadow(list.get(l), (float) (p_192634_2_ + 32 + 2),
					(float) (p_192634_3_ + 12 + 10 * l), 8421504);
		}
	}

	private void bindPluginIcon() {
		this.mc.getTextureManager().bindTexture(getIcon());
	}

	private ResourceLocation getIcon() {
		return this.mc.getTextureManager().getDynamicTextureLocation("mwplugin/" + this.plugin.getName() + "/icon",
				new DynamicTexture(getImage()));
	}

	@SuppressWarnings("resource")
	private BufferedImage getImage() {
		try {
			JarFile jar = new JarFile(new File(PluginLoader.getPlugin(this.plugin.getName()).getClass()
					.getProtectionDomain().getCodeSource().getLocation().getFile()));
			return ImageIO.read(jar.getInputStream(jar.getEntry("plugin.png")));
		} catch (Exception ex) {
			try {
				return ImageIO.read(PluginListEntry.class.getResourceAsStream("/images/logo.png"));
			} catch (IOException e) {
				return null;
			}
		}
	}

	protected boolean showHoverOverlay() {
		return true;
	}

	/**
	 * Called when the mouse is clicked within this entry. Returning true means that
	 * something within this entry was clicked and the list should not be dragged.
	 */
	public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
		if (this.showHoverOverlay() && relativeX <= 32) {

		}

		return false;
	}

	public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {
	}

	/**
	 * Fired when the mouse button is released. Arguments: index, x, y, mouseEvent,
	 * relativeX, relativeY
	 */
	public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
	}

	public boolean isServerPack() {
		return false;
	}
}
