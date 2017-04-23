package com.paninicms.views;

import java.util.List;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mongodb.client.model.Filters;
import com.paninicms.Panini;
import com.paninicms.utils.RenderContext;
import com.paninicms.utils.blog.Post;

public class ReadPostView {
	public static Object render(RenderContext context) {
		try {
			List<Post> validPosts = Panini.getAllPosts(Filters.eq("slug", context.arguments()[1]));

			if (!validPosts.isEmpty()) {
				context.contextVars().put("post", validPosts.get(0));
				PebbleTemplate template = Panini.getEngine().getTemplate("post.html");
				
				return template;
			} else {
				return null;
			}
		} catch (PebbleException e) {
			e.printStackTrace();
			return null;
		}
	}
}
