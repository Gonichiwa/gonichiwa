package com.gonichiwa.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

class MindMapFileManager {
	private String path;
	private String fileName;

	MindMapFileManager() {
		path = "";
		fileName = "";
	}

	void setFileName(String name) {
		fileName = name;
	}

	String getFileName() {
		return fileName;
	}

	void setPath(String newPath) {
		//TODO: Joon
		path = newPath;
	}

	String getPath() {
		return path;
	}

	/**
	 * save model
	 * @param model
	 */
	void save(MindMapModel model) {

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(MindMapNode.class, new MindMapNodeSerializer());
		Gson gson = gsonBuilder.create();
		try {
			File file = new File(path);
			if(!file.exists())
				file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(gson.toJson(model.tree.getRoot()));
			fileWriter.close();
			fileName = file.getName();
		} catch (IOException e) {
			System.out.println("IO save error");
		}
	}

	MindMapNode loadRoot() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(MindMapNode.class, new MindMapNodeDesiralizer());
		Gson gson = gsonBuilder.create();
		MindMapNode newNode = null;
		try {
			FileReader reader = new FileReader(path);
			newNode = gson.fromJson(reader, MindMapNode.class);
			fileName = new File(path).getName();

		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			System.out.println("load error");
		}
		return newNode;
	}

	private class MindMapNodeSerializer implements JsonSerializer<MindMapNode> {

		@Override
		public JsonElement serialize(MindMapNode node, Type type, JsonSerializationContext arg2) {
			JsonObject jsonObject = new JsonObject();

			jsonObject.addProperty("name", node.getName());
			jsonObject.addProperty("x", node.getX());
			jsonObject.addProperty("y", node.getY());
			jsonObject.addProperty("width", node.getWidth());
			jsonObject.addProperty("height", node.getHeight());
			jsonObject.addProperty("id", node.getID());
			jsonObject.addProperty("alpha", node.getAlpha());
			jsonObject.addProperty("red", node.getRedColor());
			jsonObject.addProperty("green", node.getGreenColor());
			jsonObject.addProperty("blue", node.getBlueColor());
			jsonObject.addProperty("note", node.getNote());

			JsonArray childNodes = new JsonArray();

			for(MindMapNode child : node.getChildren()) {
				JsonObject childObject = (JsonObject) serialize(child, type, arg2);
				childNodes.add(childObject);
			}

			jsonObject.add("children", childNodes);

			return jsonObject;
		}
	}

	private class MindMapNodeDesiralizer implements JsonDeserializer<MindMapNode> {

		@Override
		public MindMapNode deserialize(JsonElement element, Type type, JsonDeserializationContext arg2)
				throws com.google.gson.JsonParseException {
			JsonObject jsonObject = element.getAsJsonObject();

			MindMapNode newRoot = new MindMapNode(jsonObject.get("name").getAsString(),
											      jsonObject.get("x").getAsDouble(),
											      jsonObject.get("y").getAsDouble(),
											      jsonObject.get("width").getAsDouble(),
											      jsonObject.get("height").getAsDouble(),
											      jsonObject.get("red").getAsInt(),
											      jsonObject.get("green").getAsInt(),
											      jsonObject.get("blue").getAsInt(),
											      jsonObject.get("note").getAsString());

			JsonArray childrenNodes = jsonObject.get("children").getAsJsonArray();

			for(JsonElement child : childrenNodes) {
				newRoot.addChild(deserialize(child, type, arg2));
			}

			return newRoot;
		}

	}
}
