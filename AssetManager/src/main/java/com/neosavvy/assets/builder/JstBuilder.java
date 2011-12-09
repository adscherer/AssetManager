package com.neosavvy.assets.builder;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.neosavvy.assets.util.Log;

public class JstBuilder extends AbstractBuilder {

	@Override
	public void compile(File file) {
		try {
			String compiled = FileUtils.readFileToString(file);

			write("", compiled);
		} catch (Throwable ex) {
			Log.info("Unable to compile file: %s", ex);
		}
	}

	@Override
	public String fileExtension() {
		return ".jst";
	}
}
