package com.neosavvy.assets.builder;

import java.io.StringReader;
import java.io.StringWriter;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.neosavvy.assets.util.Config;
import com.neosavvy.assets.util.Config.FileConfig;
import com.neosavvy.assets.util.Log;
import com.neosavvy.assets.util.StringUtils;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class JavascriptBuilder extends AbstractBuilder {

	private ErrorReporter DEFAULT_ERROR_REPORTER;

	public JavascriptBuilder() {
		init();
	}

	public void init() {
		DEFAULT_ERROR_REPORTER = new ErrorReporter() {

			public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
				//Bury the message
			}

			public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
				if (line < 0)
					Log.info("\n[ERROR] " + message);
				else
					Log.info("\n[ERROR] " + line + ':' + lineOffset + ':' + message);
			}

			public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset) {
				error(message, sourceName, line, lineSource, lineOffset);
				return new EvaluatorException(message);
			}
		};
	}

	@Override
	public void compile() {
		try {
			if (!Config.hasJavascriptConfig()) {
				return;
			}
			
			FileConfig jsConfig = Config.getJavascriptConfig();
			String compiled = concat(jsConfig.getInputFiles());

			if (jsConfig.isCompressed()) {
				compiled = compress(compiled);
			}

			write(StringUtils.joinPath(jsConfig.getDirectory(), jsConfig.getOutput()), compiled);
		} catch (Throwable ex) {
			Log.info("Unable to compile file: %s", ex);
		}
	}

	@Override
	public String fileExtension() {
		return "todos.js";
	}

	public String compress(String source) throws Exception {
		StringWriter writer = new StringWriter();
		JavaScriptCompressor compressor = new JavaScriptCompressor(new StringReader(source), DEFAULT_ERROR_REPORTER);
		compressor.compress(writer, 4000, true, true, true, false);

		return writer.toString();
	}

	/*
	 * 
	 */
	public void jsScriptsConcat() throws Exception {

		/*
		Checksum checksum = new Checksum();
		checksum.setProject(DEFAULT_PROJECT);
		checksum.setAlgorithm("sha");
		checksum.setFile(file);
		checksum.setProperty("scripts.sha");
		checksum.execute();
		*/
	}

}
