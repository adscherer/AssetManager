package com.neosavvy.assets.util;

import org.junit.Test;

import static org.junit.Assert.*;

import com.neosavvy.assets.util.Config;
import com.neosavvy.assets.util.StringUtils;

public class ConfigTest {

	@Test
	public void testFindFiles() throws Exception {

		new Config(StringUtils.getClasspathFile("yaml.conf"));

		assertEquals(Config.getSrcDirectory(), "src_test");
		assertEquals(Config.getDestinationDirectory(), "dest_test");
		assertEquals(Config.getExcludeFiles().size(), 3);
		assertEquals(Config.getWatchExtensions().size(), 3);
	}

}
