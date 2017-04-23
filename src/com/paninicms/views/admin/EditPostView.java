package com.paninicms.views.admin;

import java.net.URLEncoder;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mongodb.client.model.Filters;
import com.paninicms.Panini;
import com.paninicms.utils.PaniniUtils;
import com.paninicms.utils.RenderContext;
import com.paninicms.utils.blog.Author;
import com.paninicms.utils.blog.Post;

import lombok.experimental.ExtensionMethod;

@ExtensionMethod(PaniniUtils.class)
public class EditPostView {
	public static Object render(RenderContext context, Author author) {
		try {
			context.contextVars().put("statusMessage", context.request().session().get("statusMessage").value(null));
			PebbleTemplate template = Panini.getEngine().getTemplate("editpost.html");
			
			Document document = Panini.getPostsCollection().find(Filters.eq("_id", new ObjectId(context.arguments()[3]))).first();
			
			Post post = Panini.getDatastore().get(Post.class, document.get("_id"));
			
			context.contextVars().put("editingPost", post);
			
			if (context.request().param("deleteMe").isSet()) {
				Panini.getDatastore().delete(post);
				
				try {
					context.response().redirect(Panini.getWebsiteUrl() + "panini/panel?reason=" + URLEncoder.encode("Post deletado com sucesso!", "UTF-8"));
					return null;
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
			if (context.request().param("title").isSet()) {

				post.author(author);
				post.markdownContent(context.request().param("markdown").value());
				post.postedIn(System.currentTimeMillis());
				post.title(context.request().param("title").value());
				
				String slug = Panini.getSlugify().slugify(context.request().param("title").value());
				
				int idx = 0;
				while (Panini.getPostsCollection().find(Filters.eq("slug", slug)).first() != null) {
					slug = Panini.getSlugify().slugify(context.request().param("title").value()) + idx;
					idx++;
				}
				
				post.slug(slug);
				
				Panini.getDatastore().save(post);
				context.contextVars().put("statusMessage", "Post alterado com sucesso!");
				
				try {
					context.response().redirect(Panini.getWebsiteUrl() + "panini/panel?reason=" + URLEncoder.encode("Post alterado com sucesso!", "UTF-8"));
					return null;
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
			return template;
		} catch (PebbleException e) {
			e.printStackTrace();
			return null;
		}
	}
}
