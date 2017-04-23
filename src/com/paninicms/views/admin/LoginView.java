package com.paninicms.views.admin;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mongodb.client.model.Filters;
import com.paninicms.Panini;
import com.paninicms.utils.PaniniUtils;
import com.paninicms.utils.RenderContext;
import com.paninicms.utils.blog.Author;

import lombok.experimental.ExtensionMethod;

@ExtensionMethod(PaniniUtils.class)
public class LoginView {
	public static Object render(RenderContext context) {
		try {
			if (context.request().session().get("loggedAs").isSet()) {
				String mongoId = context.request().session().get("loggedAs").value();
				
				Document document = Panini.getAuthorsCollection().find(Filters.eq("_id", new ObjectId(mongoId))).first();

				if (document == null) {
					context.request().session().unset("loggedAs");
				} else {
					if (context.arguments().is(1, "panel")) {
						return AdminPanelView.render(context, Panini.getDatastore().get(Author.class, document.get("_id")));
					}
				}
			}

			context.contextVars().put("statusMessage", null);
			if (context.request().param("username").isSet() && context.request().param("password").isSet()) {
				// Processo de login!
				String username = context.request().param("username").value();
				String password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(context.request().param("password").value());
				
				Document document = Panini.getAuthorsCollection().find(Filters.and(Filters.eq("username", username), Filters.eq("password", password))).first();
			
				if (document != null) {
					context.contextVars().put("statusMessage", "Bem-Vindo(a)!");
					
					Author author = Panini.getDatastore().get(Author.class, document.get("_id"));
					
					context.request().session().set("loggedAs", author.getId().toString());
					try {
						context.response().redirect(Panini.getWebsiteUrl() + "panini/panel");
					} catch (Throwable e) {
						e.printStackTrace();
					}
				} else {
					context.contextVars().put("statusMessage", "Login ou senha inválida!");
				}
			}
			PebbleTemplate template = Panini.getEngine().getTemplate("admin/login.html");
			
			return template;
		} catch (PebbleException e) {
			e.printStackTrace();
			return null;
		}
	}
}
