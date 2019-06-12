package eu.minewars.client.installer;

import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import eu.minewars.client.MineWarsClient;

public class Installer {
	public static void main(final String[] args) {
		try {
			final File dirMc = Utils.getWorkingDirectory();
			doInstall(dirMc);
		} catch (Exception e) {
			final String msg = e.getMessage();
			if (msg != null && msg.equals("QUIET")) {
				return;
			}
			e.printStackTrace();
			String str = Utils.getExceptionStackTrace(e);
			str = str.replace("\t", "  ");
			final JTextArea textArea = new JTextArea(str);
			textArea.setEditable(false);
			final Font f = textArea.getFont();
			final Font f2 = new Font("Monospaced", f.getStyle(), f.getSize());
			textArea.setFont(f2);
			final JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setPreferredSize(new Dimension(600, 400));
			JOptionPane.showMessageDialog(null, scrollPane, "Fehler", 0);
		}
	}

	public static void doInstall(final File dirMc) throws Exception {
		Utils.dbg("Minecraft Ordner: " + dirMc);
		final File dirMcLib = new File(dirMc, "libraries");
		Utils.dbg("Library Ordner: " + dirMcLib);
		final File dirMcVers = new File(dirMc, "versions");
		Utils.dbg("Versionen Ordner: " + dirMcVers);
		final String ofVer = MineWarsClient.VERSION;
		Utils.dbg(MineWarsClient.NAME + " Version: " + ofVer);
		final String mcVer = MineWarsClient.VERSION;
		Utils.dbg("Minecraft Version: " + mcVer);
		final String mcVerOf = String.valueOf(mcVer) + "-" + MineWarsClient.NAME;
		Utils.dbg("Minecraft_" + MineWarsClient.NAME + " Version: " + mcVerOf);
		File folder = new File(dirMcVers, mcVerOf);
		folder.mkdirs();
		JsonParser parser = new JsonParser();
		JsonObject Json = (JsonObject) parser.parse(getJson());
		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
		FileWriter fw = new FileWriter(new File(folder, mcVerOf + ".json"));
		gson.toJson(Json, fw);
		fw.flush();
		fw.close();
		installJar(getJar(), folder, mcVerOf + ".jar");
		//updateJson(dirMcVers, mcVerOf, dirMcLib, mcVer, "");
		updateLauncherJson(dirMc, mcVerOf);
	}
	
	public static void installJar(File jar, File folder, String name) throws IOException {
		Files.copy(jar, new File(folder, name));
	}
	
	public static File getJar() {
		return new File(Installer.class.getProtectionDomain().getCodeSource().getLocation().getFile());
	}
	
	public static String getJson() {
		BufferedReader r = new BufferedReader(new InputStreamReader(Installer.class.getResourceAsStream("/1.12.2.json")));
		String Json = "";
		String s;
		try {
			while((s = r.readLine()) != null) {
				Json = Json + s;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Json;
	}

	private static void updateLauncherJson(final File dirMc, final String mcVerOf) throws IOException, ParseException {
		final File fileJson = new File(dirMc, "launcher_profiles.json");
		if (!fileJson.exists() || !fileJson.isFile()) {
			Utils.showErrorMessage("File not found: " + fileJson);
			throw new RuntimeException("QUIET");
		}
		final String Json = Utils.readFile(fileJson, "UTF-8");
		final JsonParser jp = new JsonParser();
		final JsonObject root = (JsonObject) jp.parse(Json);
		final JsonObject profiles = (JsonObject) root.get("profiles");
		JsonObject prof = (JsonObject) profiles.get(MineWarsClient.NAME);
		if (prof == null) {
			prof = new JsonObject();
			prof.addProperty("name", MineWarsClient.NAME);
			prof.addProperty("created", formatDateMs(new Date()).toString());
			profiles.add(MineWarsClient.NAME, prof);
		}
		prof.addProperty("type", "custom");
		prof.addProperty("lastVersionId", mcVerOf);
		prof.addProperty("lastUsed", formatDateMs(new Date()).toString());
		root.addProperty("selectedProfile", MineWarsClient.NAME);
		final FileOutputStream fosJson = new FileOutputStream(fileJson);
		final OutputStreamWriter oswJson = new OutputStreamWriter(fosJson, "UTF-8");
		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
		gson.toJson(root, oswJson);
		oswJson.flush();
		oswJson.close();
	}

    @SuppressWarnings("unused")
	private static void updateJson(final File dirMcVers, final String mcVerOf, final File dirMcLib, final String mcVer, final String ofEd) throws IOException, ParseException {
        final File dirMcVersOf = new File(dirMcVers, mcVerOf);
        final File fileJson = new File(dirMcVersOf, String.valueOf(mcVerOf) + ".json");
        final String Json = Utils.readFile(fileJson, "UTF-8");
        final JsonParser jp = new JsonParser();
        final JsonObject root = (JsonObject)jp.parse(Json);
        final JsonObject rootNew = new JsonObject();
        rootNew.addProperty("id", mcVerOf);
        //rootNew.addProperty("inheritsFrom", mcVer);
        rootNew.addProperty("time", formatDate(new Date()).toString());
        rootNew.addProperty("releaseTime", formatDate(new Date()).toString());
        rootNew.addProperty("type", "release");
        final JsonArray libs = new JsonArray();
        rootNew.add("libraries", libs);
        String mainClass = root.get("mainClass").getAsString();
        if (!mainClass.startsWith("net.minecraft.client.main.")) {
            mainClass = "net.minecraft.client.main.Main";
            rootNew.addProperty("mainClass", mainClass);
            String mcArgs = root.get("minecraftArguments").getAsString();
            if (mcArgs != null) {
                mcArgs = String.valueOf(mcArgs);
                rootNew.addProperty("minecraftArguments", mcArgs);
            }
            else {
                rootNew.addProperty("minimumLauncherVersion", "21");
                final JsonObject args = new JsonObject();
                final JsonArray argsGame = new JsonArray();
                args.add("game", argsGame);
                rootNew.add("arguments", args);
            }
        }
        final FileOutputStream fosJson = new FileOutputStream(fileJson);
        final OutputStreamWriter oswJson = new OutputStreamWriter(fosJson, "UTF-8");
        new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(rootNew, oswJson);
        oswJson.flush();
        oswJson.close();
    }

	private static Object formatDate(final Date date) {
		try {
			final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
			final String str = dateFormat.format(date);
			return str;
		} catch (Exception e) {
			final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			final String str2 = dateFormat2.format(date);
			return str2;
		}
	}

	private static Object formatDateMs(final Date date) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'");
		final String str = dateFormat.format(date);
		return str;
	}

	public static File getMineWarsZipFile() throws Exception {
		final URL url = Installer.class.getProtectionDomain().getCodeSource().getLocation();
		Utils.dbg("URL: " + url);
		final URI uri = url.toURI();
		final File fileZip = new File(uri);
		return fileZip;
	}

}
