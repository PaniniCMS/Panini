package com.paninicms;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class PaniniLauncher {
	public static void main(String[] args) {
		Options options = new Options();

		{
			Option input = new Option("f", "rootFolder", true, "Panini's root folder");
			input.setRequired(true);
			options.addOption(input);
		}
				
		{
			Option input = new Option("w", "websiteUrl", true, "Panini's website URL");
			input.setRequired(true);
			options.addOption(input);
		}
		
		{
			Option input = new Option("p", "port", true, "Panini's port");
			input.setRequired(true);
			options.addOption(input);
		}
		
		{
			Option input = new Option("m", "mongoDatabase", true, "Panini's MongoDB database name");
			input.setRequired(true);
			options.addOption(input);
		}
		
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("Panini", options);

			System.exit(1);
			return;
		}

		String websiteRootFolder = cmd.getOptionValue("rootFolder");
		String websiteUrl = cmd.getOptionValue("websiteUrl");
		String port = cmd.getOptionValue("port");
		String mongoDatabase = cmd.getOptionValue("mongoDatabase");
		
		Panini.init(websiteRootFolder, websiteUrl, port, mongoDatabase);
	}
}
