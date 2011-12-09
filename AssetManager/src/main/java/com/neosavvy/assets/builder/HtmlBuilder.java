package com.neosavvy.assets.builder;

import java.io.File;
import java.io.StringWriter;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.neosavvy.assets.util.Config;
import com.neosavvy.assets.util.Log;

public class HtmlBuilder extends AbstractBuilder {

	HtmlCompressor compressor = new HtmlCompressor();

	VelocityContext context;

	public HtmlBuilder() {
		ExtendedProperties properties = new ExtendedProperties();
		properties.addProperty(RuntimeConstants.RESOURCE_LOADER, "file");
		properties.addProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, Config.getSrcDirectory());

		Velocity.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, Config.getSrcDirectory());
		Velocity.init();

		context = new VelocityContext();
		context.put("config", Config.get());

		compressor.setPreserveLineBreaks(false);
	}

	@Override
	public void compile(File file) {
		try {
			if (!Config.hasHtmlConfig()) {
				return;
			}
			
			String compiled = FileUtils.readFileToString(file);
			compiled = preprocess(compiled);

			if (false) {
				compiled = compress(compiled);
			}

			write(getDestinationPath(file), compiled);
		} catch (Throwable ex) {
			Log.info("Unable to compile file: %s", ex);
		}
	}

	@Override
	public String fileExtension() {
		return ".html";
	}

	public String preprocess(String source) throws Exception {
		source = processVelocity(source);

		return source;
	}

	public String compress(String source) throws Exception {
		return compressor.compress(source);
	}

	public String processVelocity(String source) {
		StringWriter w = new StringWriter();
		Velocity.evaluate(context, w, "debug", source);
		return w.toString();
	}

}
