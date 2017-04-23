package com.paninicms;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.bson.Document;
import org.jooby.Jooby;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.github.slugify.Slugify;
import com.google.gson.Gson;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.FileLoader;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.paninicms.plugin.PaniniPlugin;
import com.paninicms.plugin.PluginDescription;
import com.paninicms.utils.blog.Author;
import com.paninicms.utils.blog.Post;
import com.paninicms.views.GlobalHandler;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

import lombok.Getter;

public class Panini extends Jooby {
	@Getter
	private static File rootFolder;
	@Getter
	private static String websiteUrl;
	@Getter
	private static PebbleEngine engine;
	private static String rootFolderAsString;
	@Getter
	private static MongoClient mongoClient;
	@Getter
	private static MongoDatabase database;
	@Getter
	private static Morphia morphia;
	@Getter
	private static Datastore datastore;
	@Getter
	private static MongoCollection<Document> authorsCollection;
	@Getter
	private static MongoCollection<Document> postsCollection;
	@Getter
	private static Slugify slugify;
	@Getter
	private static Gson gson = new Gson();

	private static int port;

	public static List<Post> getAllPosts() {
		List<Post> posts = new ArrayList<Post>();

		FindIterable<Document> documents = postsCollection.find();

		documents.sort(Sorts.descending("postedIn"));
		for (Document document : documents) {
			Post post = (Post) getDatastore().get(Post.class, document.get("_id"));

			HtmlRenderer renderer = HtmlRenderer.builder().build();
			Parser parser = Parser.builder().build();
			Node node = parser.parse(post.markdownContent());
			String content = renderer.render(node);

			post.content(content);

			posts.add(post);
		}

		return posts;
	}

	{
		port(port); // Usar a porta que n�s passamos ao iniciar a Panini
		assets("/**", Paths.get(rootFolderAsString + "static/"));
		get("/**", (req, res) -> GlobalHandler.render(req, res));
		post("/**", (req, res) -> GlobalHandler.render(req, res));
	}

	// Registrar algumas vari�veis para usar no Jooby
	public static void init(String rootPath, String websiteUrl, String websitePort, String mongoDatabase) {
		mongoClient = new MongoClient();
		database = mongoClient.getDatabase(mongoDatabase);
		morphia = new Morphia();
		datastore = morphia.createDatastore(mongoClient, mongoDatabase);
		authorsCollection = database.getCollection("authors");
		postsCollection = database.getCollection("posts");
		rootFolder = new File(rootPath);
		rootFolderAsString = rootPath;
		slugify = new Slugify();
		Panini.websiteUrl = websiteUrl;

		FileLoader fl = new FileLoader();
		fl.setPrefix(rootFolder.toString());
		engine = new PebbleEngine.Builder().cacheActive(false).strictVariables(true).loader(fl).build();

		port = Integer.parseInt(websitePort);

		// Load external plugins
		loadPlugins();

		Thread t = new Thread() {
			public void run() {
				Scanner scanner = new Scanner(System.in);

				while (true) {
					String line = scanner.nextLine();
					String[] cmds = line.split(" ");

					if (cmds[0].equals("createauthor")) {
						if (cmds.length == 3) {
							String username = cmds[1];
							String password = cmds[2];

							Author author = new Author();
							author.setUsername(username);
							author.setPassword(org.apache.commons.codec.digest.DigestUtils.sha256Hex(password));

							datastore.save(author);

							System.out.println("Done!");
						} else {
							System.out.println("Author Creator");
							System.out.println("createauthor username pass");
						}
					}
				}
			}
		};
		t.start();

		run(Panini::new, new String[] {});
	}

	public static void loadPlugins() {
		final File file = new File("./plugins");
		for(final File child : file.listFiles()) {
			System.out.println("Loading " + child.getName() + "... May or may not work!");

			try {
				// Loading the JAR to the class loader
				URL url = child.toURI().toURL();

				URLClassLoader classLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
				Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
				method.setAccessible(true);
				method.invoke(classLoader, url);

				// Now we are going to read the plugin description from the JAR
				InputStream is = classLoader.getResourceAsStream("plugin.json");

				String result = new BufferedReader(new InputStreamReader(is))
						.lines().collect(Collectors.joining("\n"));

				PluginDescription description = getGson().fromJson(result, PluginDescription.class);

				Class<?> clazz = Class.forName(description.getClassPath());

				PaniniPlugin plugin = (PaniniPlugin) clazz.newInstance();
				plugin.setClassLoader(classLoader);
				plugins.add(plugin);
				plugin.onEnable();

				System.out.println(description.getPluginName() + " " + description.getVersion() + " loaded sucessfully!");
			} catch (Exception ex) {
				System.out.println("Error while loading " + child.getName() + "!");
				ex.printStackTrace();
			}
		}
	}

	@Getter
	private static List<PaniniPlugin> plugins = new ArrayList<PaniniPlugin>();
}
