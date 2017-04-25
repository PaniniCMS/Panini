package com.paninicms;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paninicms.utils.PaniniConfig;

public class PaniniLauncher {
	public static void main(String[] args) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		File file = new File("./config.json");
		PaniniConfig config = null;
		
		if (file.exists()) {
			String json;
			try {
				json = FileUtils.readFileToString(file, "UTF-8");
				config = gson.fromJson(json, PaniniConfig.class);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1); // Sair caso der erro
				return;
			}
		} else {
			System.out.println("Welcome to PaniniCMS!");
			System.out.println("Because this is your first time using PaniniCMS, we are going to create a file named \"config.json\" on your PaniniCMS' root folder, where you will need to configure PaniniCMS before using it!");
			System.out.println("");
			System.out.println("After configuring PaniniCMS, start it again!");
			try {
				FileUtils.writeStringToFile(file, gson.toJson(new PaniniConfig() // Fill PaniniConfig with some placeholder variables, if we don't do this, Gson will return "{}"
						.setFrontendFolder("Frontend folder")
						.setWebsiteUrl("Website URL")
						.setMongoDBDatabase("MongoDB database name")
						.setPort(4569)), "UTF-8");
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(1);
			return;
		}
		
		Panini.init(config);
	}
}
