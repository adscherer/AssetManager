package com.neosavvy.assets.util;

import java.util.List;
import java.util.Map;

public class MapWrapper {

	private Map<String, Object> properties;

	public MapWrapper(Map<String, Object> properties) {
		this.properties = properties;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public boolean hasKey(String key) {
		return getProperties().get(key) != null;
	}

	public String getString(String key) {
		return getString(key, "");
	}

	public String getString(String key, String def) {
		if (!hasKey(key))
			return def;

		return (String) getProperties().get(key);
	}

	public int getInteger(String key, int def) {
		return StringUtils.parseInteger(getString(key), def);
	}

	public boolean getBoolean(String key) {
		return StringUtils.parseBoolean(getString(key));
	}

	public List<String> tokens(String key) {
		return tokens(key, ",");
	}
	
	public List<String> tokens(String key, String delimiter) {
		return StringUtils.tokens(getString(key, null), delimiter);
	}
	
	public List<String> getList(String key) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getMap(String key) {
		Object o = getProperties().get(key);
		if (o == null || !(o instanceof Map)) {
			return null;
		}

		return (Map<String, Object>) o;
	}

}
