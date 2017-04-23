package com.paninicms.views.admin;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.paninicms.Panini;
import com.paninicms.utils.PaniniUtils;
import com.paninicms.utils.RenderContext;
import com.paninicms.utils.blog.Author;

import lombok.experimental.ExtensionMethod;

@ExtensionMethod(PaniniUtils.class)
public class AdminPanelView {
	public static Object render(RenderContext context, Author author) {
		try {
			if (context.arguments().is(2, "createpost")) {
				return CreatePostView.render(context, author);
			}
			if (context.arguments().is(2, "editpost")) {
				return EditPostView.render(context, author);
			}
			context.contextVars().put("posts", Panini.getAllPosts());
			context.contextVars().put("statusMessage", context.request().param("reason").value(null));
			
			PebbleTemplate template = Panini.getEngine().getTemplate("panel.html");

			return template;
		} catch (PebbleException e) {
			e.printStackTrace();
			return null;
		}
	}
}
