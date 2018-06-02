package com.gonichiwa.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
		String result = gson.toJson(model);
		try {
			File file = new File(path);
			if(!file.exists()) 
				file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(result);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("save error");
		}
	}
	
	MindMapModel load() {
		Gson gson = new Gson();
		MindMapModel loadedModelData = null;
		try {
			loadedModelData = gson.fromJson(new FileReader(path), MindMapModel.class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("load error");
		}
		return loadedModelData;
	}
}
