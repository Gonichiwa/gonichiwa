package com.gonichiwa.model;

import java.io.File;
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

/**
 * MindMapFileManager default class
 * 
 * this is one of the Model class which is in charge of
 * saving and loading the root node of the MindMapTree.
 * 
 * this class is using Gson Library for serializing and
 * desiralizing data to Json format.
 * 
 * this class has two properties path and fileName.
 * fileName is also included in path.
 * 
 * @author YONG_JOON_KIM
 *
 */
class MindMapFileManager {
	private String path;
	private String fileName;

	/**
	 * constructor
	 * 
	 * initialize the path and filaName to empty String.
	 * 
	 */
	MindMapFileManager() {
		path = "";
		fileName = "";
	}

	/**
	 * Accessor method.
	 * 
	 * @return
	 * 		current fileName of the mindmap
	 */
	String getFileName() {
		return fileName;
	}

	/**
	 * Modifier method.
	 * 
	 * set New path this should be including fileName.
	 * 
	 * @param newPath
	 * 		newPath to be set.
	 */
	void setPath(String newPath) {
		path = newPath;
	}

	/**
	 * Accessor Method.
	 * 
	 * @return
	 * 		current path.
	 */
	String getPath() {
		return path;
	}

	/**
	 * Modifier Method.
	 * 
	 * save the root node in the MindMapTree.
	 * if file doesn't exists in given path, then make new file.
	 * 
	 * @param tree
	 */
	void save(MindMapTree tree) {

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(MindMapNode.class, new MindMapNodeSerializer());
		Gson gson = gsonBuilder.create();
		
		try {
			File file = new File(path);
			
			if(!file.exists())
				file.createNewFile();
			
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(gson.toJson(tree.getRoot()));
			fileWriter.close();
			fileName = file.getName();
		} catch (IOException e) {
			System.out.println("IO save error");
		}
	}

	/**
	 * Accessor Method.
	 * 
	 * load the Json file from the path. path should include the fileName.
	 * 
	 * @return
	 * 		loaded root node from the path.
	 */
	MindMapNode loadRoot() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(MindMapNode.class, new MindMapNodeDeserializer());
		Gson gson = gsonBuilder.create();
		MindMapNode newNode = null;
		try {
			FileReader reader = new FileReader(path);
			newNode = gson.fromJson(reader, MindMapNode.class);
			fileName = new File(path).getName();
			reader.close();
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			System.out.println("load error");
		}
		return newNode;
	}

	/**
	 * MindMapNodeSerializer inner class
	 * 
	 * this is concrete class implements JsonSerializer<MindMapNode>
	 * using serialize() method, we can serialize the objects in the way 
	 * we want.
	 * 
	 * this serializes all the property includes children List recursively.
	 * 
	 * @author YONG_JOON_KIM
	 *
	 */
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

	/**
	 * MindMapNodeDeserializer inner class
	 * 
	 * this is concrete class implements JsonDeserializer<MindMapNode>
	 * using deserialize() method, we can initialize the MindMapNode classes
	 * while deserializing from Json file.
	 * 	 * 
	 * @author YONG_JOON_KIM
	 *
	 */
	private class MindMapNodeDeserializer implements JsonDeserializer<MindMapNode> {

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
