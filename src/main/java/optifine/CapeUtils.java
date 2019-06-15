package optifine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import eu.minewars.client.cape.MineWarsCapeUtils;
import eu.minewars.client.event.EventManager;
import eu.minewars.client.event.player.PlayerDownloadCapeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class CapeUtils {
	public static void downloadCape(AbstractClientPlayer player) {
		String name = player.getNameClear();

		if (name != null && !name.isEmpty()) {
			String url = "http://s.optifine.net/capes/" + name + ".png";
			ResourceLocation resourcelocation = new ResourceLocation("capemw/" + name);
			TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
			/*
			 * ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);
			 * 
			 * if (itextureobject != null && itextureobject instanceof
			 * ThreadDownloadImageData) { ThreadDownloadImageData threaddownloadimagedata =
			 * (ThreadDownloadImageData) itextureobject;
			 * 
			 * if (threaddownloadimagedata.imageFound != null) { if
			 * (threaddownloadimagedata.imageFound.booleanValue()) {
			 * player.setLocationOfCape(resourcelocation); }
			 * 
			 * return; } }
			 */

			if (MineWarsCapeUtils.capeExists(name)) {
				url = "http://cape.minewars.eu/" + name + ".png";
			}
			
			PlayerDownloadCapeEvent event = EventManager.callEvent(new PlayerDownloadCapeEvent(player, url));
			
			if(event.isCancelled()) {
				return;
			}

			CapeImageBuffer capeimagebuffer = new CapeImageBuffer(player, resourcelocation);
			ThreadDownloadImageData threaddownloadimagedata1 = new ThreadDownloadImageData((File) null, event.getURL(),
					(ResourceLocation) null, capeimagebuffer);
			threaddownloadimagedata1.pipeline = true;
			texturemanager.loadTexture(resourcelocation, threaddownloadimagedata1);
		}
	}

	public static BufferedImage parseCape(BufferedImage p_parseCape_0_) {
		int i = 64;
		int j = 32;
		int k = p_parseCape_0_.getWidth();

		for (int l = p_parseCape_0_.getHeight(); i < k || j < l; j *= 2) {
			i *= 2;
		}

		BufferedImage bufferedimage = new BufferedImage(i, j, 2);
		Graphics graphics = bufferedimage.getGraphics();
		graphics.drawImage(p_parseCape_0_, 0, 0, (ImageObserver) null);
		graphics.dispose();
		return bufferedimage;
	}
}
