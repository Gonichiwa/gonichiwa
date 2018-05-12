package com.gonichiwa.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;

class MindMapFileManager {
	private String path;
	private String fileName;
	@Expose(serialize = false, deserialize = false)
	Gson gson;
	
	MindMapFileManager() {
		path = null;
		fileName = null;
		gson = new Gson();
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
	
	void save(MindMapModel model) {
		String result = gson.toJson(model);
		try {
			FileWriter fileWriter = new FileWriter(path+fileName);
			fileWriter.write(result);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("save error");
		}
	}
	
	MindMapModel load() {
		MindMapModel loadedModelData = null;
		try {
			loadedModelData = gson.fromJson(new FileReader(path+fileName), MindMapModel.class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("load error");
		}
		return loadedModelData;
	}
}
