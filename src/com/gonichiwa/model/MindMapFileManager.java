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
		Gson gson = new Gson();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode node = objectMapper.convertValue(model.tree.getRoot(), JsonNode.class);
		String result;
		try {
//			result = gson.toJson(model);
			result = objectMapper.writeValueAsString(node);
			File file = new File(path);
			if(!file.exists()) 
				file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(result);
			fileWriter.close();
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			System.out.println("error");
//			e1.printStackTrace();

		} catch (IOException e) {
			System.out.println("IO save error");

		}
//		try {
//			File file = new File(path+fileName);
//			if(!file.exists()) 
//				file.createNewFile();
//			FileWriter fileWriter = new FileWriter(file);
//			fileWriter.write(result);
//			fileWriter.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			System.out.println("save error");
//		}
	}
	
	MindMapNode loadRoot() {
		ObjectMapper objectMapper = new ObjectMapper();

		Gson gson = new Gson();
		JsonNode node = null;
		MindMapNode newNode = null;
		MindMapTree loadedModelData = new MindMapTree();
		try {
//			loadedModelData = objectMapper.readValue(new FileReader(path), MindMapModel.class);
			newNode = gson.fromJson(new FileReader(path), MindMapNode.class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("load error");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newNode;
	}
}
