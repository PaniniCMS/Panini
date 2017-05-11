package com.paninicms.views;

import java.util.List;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mongodb.client.model.Filters;
import com.paninicms.Panini;
import com.paninicms.plugin.PaniniPlugin;
import com.paninicms.plugin.event.Listener;
import com.paninicms.plugin.event.blog.ReadBlogPageEvent;
import com.paninicms.utils.RenderContext;
import com.paninicms.utils.blog.Page;

public class ReadPageView {
	public static Object render(RenderContext context) {
		try {
			List<Page> validPages = Panini.getAllPages(Filters.eq("slug", context.arguments()[1]));

			if (!validPages.isEmpty()) {
				Page page = validPages.get(0);

				ReadBlogPageEvent readPostEvent = new ReadBlogPageEvent(page, context);
				for (PaniniPlugin plugin : Panini.getPlugins()) {
					for (Listener listener : plugin.getListeners()) {
						PaniniPlugin.executeEvent(listener, readPostEvent);
					}
				}
				page = readPostEvent.getPage();
				context = readPostEvent.getContext();
				
				context.contextVars().put("page", page);
				PebbleTemplate template = Panini.getEngine().getTemplate("page.html");
				
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
