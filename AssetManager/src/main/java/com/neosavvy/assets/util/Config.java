package com.neosavvy.assets.util;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.yaml.snakeyaml.Yaml;

public class Config {

	private static MapWrapper properties;
	private static Config config;

	public Config(String filePath) {
		this(new File(filePath));
		Log.info("Properties were loaded.");
	}

	public Config(File file) {
		Log.info("Loading properties: %s", file);
		init(file);
		config = this;
	}

	public static Config get() {
		return config;
	}

	@SuppressWarnings("unchecked")
	protected void init(File file) {
		try {
			properties = new MapWrapper((Map<String, Object>) new Yaml().load(FileUtils.readFileToString(file)));
		} catch (Throwable ex) {
			Log.info("Error: %s", ex);
		}
	}

	public void validate() {
		if (StringUtils.isAnyEquivalent(Config.getDestinationDirectory(), ".", "..", "/", "./", "../")) {
			Log.info("The destination_dir property is set to: %s.  This will potentially overwrite all of your assets.  Please set it to something like 'publish'.", Config.getDestinationDirectory());
			System.exit(1000);
		}
	}

	public static String getSrcDirectory() {
		return properties.getString("source_dir", "src");
	}

	public static String getDestinationDirectory() {
		return properties.getString("destination_dir", "publish");
	}

	public static List<String> getWatchExtensions() {
		return properties.tokens("watch_extensions");
	}

	public static List<String> getExcludeFiles() {
		return properties.tokens("exclude_files");
	}

	public static int getMonitorInterval() {
		return properties.getInteger("monitor_interval", 1000);
	}

	public static boolean hasJavascriptConfig() {
		return getJavascriptConfig() != null;
	}

	public static FileConfig getJavascriptConfig() {
		return new FileConfig(properties.getMap("javascript_config"));
	}

	public static boolean hasCssConfig() {
		return getCssConfig() != null;
	}

	public static Object getCssConfig() {
		return properties.getMap("css_config");
	}

	public static boolean hasHtmlConfig() {
		return getHtmlConfig() != null;
	}

	public static Object getHtmlConfig() {
		return properties.getMap("html_config");
	}

	public static class FileConfig {

		private MapWrapper properties;

		public FileConfig(Map<String, Object> properties) {
			if (properties == null) {
				properties = new HashMap<String, Object>();
			}

			this.properties = new MapWrapper(properties);
		}

		public String getDirectory() {
			return properties.getString("directory");
		}

		public boolean isCompressed() {
			return properties.getBoolean("compressed");
		}

		public boolean hasOutput() {
			return getOutput() != null;
		}

		public String getOutput() {
			return properties.getString("output", null);
		}

		public List<String> getInput() {
			return properties.tokens("input");
		}

		public Collection<File> getInputFiles() {

			IOFileFilter filter = FileFilterUtils.fileFileFilter();

			for (String input : getInput()) {
				filter = FileFilterUtils.and(filter, FileFilterUtils.suffixFileFilter(input));
			}

			return FileUtils.listFiles(new File(Config.getSrcDirectory()), filter, TrueFileFilter.INSTANCE);
		}
	}

}
