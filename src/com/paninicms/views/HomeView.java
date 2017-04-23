package com.paninicms.views;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.paninicms.Panini;
import com.paninicms.utils.RenderContext;

public class HomeView {
	public static Object render(RenderContext context) {
		try {
			context.contextVars().put("posts", Panini.getAllPosts());
			PebbleTemplate template = Panini.getEngine().getTemplate("home.html");
			
			return template;
		} catch (PebbleException e) {
			e.printStackTrace();
			return null;
		}
	}
}
