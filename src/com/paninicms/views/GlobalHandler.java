package com.paninicms.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.jooby.Request;
import org.jooby.Response;

import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.paninicms.Panini;
import com.paninicms.plugin.PaniniPlugin;
import com.paninicms.plugin.event.PostRenderEvent;
import com.paninicms.utils.PaniniUtils;
import com.paninicms.utils.RenderContext;
import com.paninicms.views.admin.LoginView;

import lombok.experimental.ExtensionMethod;

@ExtensionMethod(PaniniUtils.class)
public class GlobalHandler {
	public static void render(Request req, Response res) {
		try { // The super try block(tm)
			String[] arguments = req.path().split("/");
			if (arguments.length > 0) {
				arguments = ArrayUtils.remove(arguments, 0); // O primeiro sempre será vazio, então vamos remover ele
			}

			Map<String, Object> contextVars = new HashMap<String, Object>(); // Variáveis usadas no Pebble

			contextVars.put("websiteUrl", Panini.getWebsiteUrl());

			RenderContext context = new RenderContext()
					.contextVars(contextVars)
					.request(req)
					.response(res)
					.arguments(arguments);

			for (PaniniPlugin plugin : Panini.getPlugins()) {
				plugin.onPreRender(context);
			}

			Object obj = null;
			if (arguments.is(0, "panini")) {
				obj = LoginView.render(context);
			} else if (arguments.length == 0) {
				obj = HomeView.render(context);
			}

			PostRenderEvent postRenderEvent = new PostRenderEvent(context, obj);
			for (PaniniPlugin plugin : Panini.getPlugins()) {
				plugin.onPostRender(postRenderEvent);
			}
			context = postRenderEvent.getRenderContext();
			obj = postRenderEvent.getTemplate();

			String output = null;

			if (obj instanceof PebbleTemplate) {
				PebbleTemplate compiledTemplate = (PebbleTemplate) obj;
				StringWriter writer = new StringWriter();
				compiledTemplate.evaluate(writer, context.contextVars());
				output = writer.toString();
			} else if (obj instanceof String) {
				output = (String) obj;
			}

			try {
				if (output == null) { output = "Missing output"; }
				res.send(output);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			StringWriter db1 = new StringWriter();
			PrintWriter coll1 = new PrintWriter(db1);
			e.printStackTrace(coll1);
			try {
				res.send(db1.toString());
			} catch (Throwable ex) {
				e.printStackTrace();
			}
		}
	}
}
