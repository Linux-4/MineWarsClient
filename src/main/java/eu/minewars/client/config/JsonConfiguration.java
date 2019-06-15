package eu.minewars.client.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JsonConfiguration {

	private final File file;
	private JsonObject json;

	/**
	 * Class for reading/writing json to files
	 *
	 * @param file The file to open
	 */
	public JsonConfiguration(File file) {
		this.file = file;
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		try {
			final JsonElement tmp = new JsonParser().parse(new BufferedReader(new FileReader(file)));
			if (tmp instanceof JsonObject) {
				json = (JsonObject) tmp;
			} else {
				json = new JsonObject();
				save();
			}
		} catch (final JsonIOException e) {
			e.printStackTrace();
		} catch (final JsonSyntaxException e) {
			e.printStackTrace();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		if (json == null) {
			json = new JsonObject();
			save();
		}
	}

	/**
	 * Adds a member, which is a name-value pair, to self. The name must be a
	 * String, but the value can be an arbitrary JsonElement, thereby allowing you
	 * to build a full tree of JsonElements rooted at this node.
	 *
	 * @param property name of the member.
	 * @param value    member object.
	 */
	public void add(String property, JsonElement value) {
		json.add(property, value);
	}

	/**
	 * Convenience method to add a boolean member. The specified value is converted
	 * to a JsonPrimitive of Boolean.
	 *
	 * @param property name of the member.
	 * @param value    the boolean value associated with the member
	 */
	public void addProperty(String property, Boolean value) {
		json.addProperty(property, value);
	}

	/**
	 * Convenience method to add a boolean member. The specified value is converted
	 * to a JsonPrimitive of Character.
	 *
	 * @param property name of the member.
	 * @param value    the character value associated with the member
	 */
	public void addProperty(String property, Character value) {
		json.addProperty(property, value);
	}

	/**
	 * Convenience method to add a boolean member. The specified value is converted
	 * to a JsonPrimitive of Number.
	 *
	 * @param property name of the member.
	 * @param value    the number value associated with the member
	 */
	public void addProperty(String property, Number value) {
		json.addProperty(property, value);
	}

	/**
	 * Convenience method to add a boolean member. The specified value is converted
	 * to a JsonPrimitive of String.
	 *
	 * @param property name of the member.
	 * @param value    the string value associated with the member
	 */
	public void addProperty(String property, String value) {
		json.addProperty(property, value);
	}

	/**
	 * Returns the member with the specified name.
	 *
	 * @param memberName name of the member that is being requested.
	 * @return the member matching the name. Null if no such member exists.
	 */
	public JsonElement get(String memberName) {
		return json.get(memberName);
	}

	/**
	 * Get this JsonFile as JsonObject
	 *
	 * @return JsonObject representation of this JsonFile
	 */
	public JsonObject getAsJsonObject() {
		return json;
	}

	/**
	 * Get the file content as String
	 *
	 * @return File content as String
	 */
	public String getAsString() {
		return json.getAsString();
	}

	/**
	 * Removes the property from this JsonObject.
	 *
	 * @param memberName name of the member that should be removed.
	 */
	public void remove(String memberName) {
		json.remove(memberName);
	}

	/**
	 * Save this JsonFile
	 */
	public void save() {
		try {
			final FileWriter writer = new FileWriter(file);
			final Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
			gson.toJson(json, writer);
			writer.close();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return getAsString();
	}

}