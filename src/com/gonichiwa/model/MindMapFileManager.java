package com.gonichiwa.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;

class MindMapFileManager {
	@Expose(serialize = true, deserialize = true)
	private String path;
	@Expose(serialize = true, deserialize = true)
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
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode node = objectMapper.convertValue(model.tree.getRoot(), JsonNode.class);
		String result;
		try {
			result = objectMapper.writeValueAsString(node);
			File file = new File(path);
			if(!file.exists()) 
				file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(result);
			fileWriter.close();
			fileName = file.getName();
		} catch (JsonProcessingException e1) {
			System.out.println("error");
		} catch (IOException e) {
			System.out.println("IO save error");
		}
	}
	
	MindMapNode loadRoot() {
		ObjectMapper objectMapper = new ObjectMapper();
		Gson gson = new Gson();
		MindMapNode newNode = null;
		try {

//			loadedModelData = objectMapper.readValue(new FileReader(path), MindMapModel.class);
//			newNode = objectMapper.readValue(new FileReader(path), MindMapNode.class);
			FileReader reader = new FileReader(path);
			newNode = gson.fromJson(reader, MindMapNode.class);
			fileName = new File(path).getName();

		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			System.out.println("load error");
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return newNode;
	}
}
