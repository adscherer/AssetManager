package com.neosavvy.assets.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import com.neosavvy.assets.util.Config;
import com.neosavvy.assets.util.Log;
import com.neosavvy.assets.util.StopWatch;

public class BuildManager {

	private List<Builder> builders = new ArrayList<Builder>();

	private IOFileFilter copyFilter;

	public void addBuilder(Builder builder) {
		builders.add(builder);
	}

	public void compile() {
		StopWatch monitor = new StopWatch();
		monitor.start();
		try {
			clean();
			copy();

			for (Builder builder : builders) {
				builder.compile();
			}

		} catch (Throwable ex) {
			Log.info("Exception compiling: %s", ex);
		} finally {
			monitor.stop();
			Log.info("Build completed: %s ms", monitor.getTotalTimeMillis());
		}
	}

	/*
	 * Removes all asset types from the existing publish directory
	 */
	public void clean() throws Exception {
		Log.debug("Removing the existing publish directory...");
		FileUtils.deleteDirectory(new File(Config.getDestinationDirectory()));
	}

	/*
	 * Increase the build number by 1 and set a date
	 */
	public void revision() throws Exception {

	}

	/*
	 * This will copy everything from the source folder to the publish folder that
	 * haven't been excluded.
	 */
	public void copy() throws Exception {
		Log.debug("Copying over all non processed files...");
		FileUtils.copyDirectory(new File(Config.getSrcDirectory()), new File(Config.getDestinationDirectory()), getCopyFilter());
	}

	protected IOFileFilter getCopyFilter() {
		if (copyFilter != null) {
			return copyFilter;
		}

		copyFilter = FileFilterUtils.trueFileFilter();

		for (String excludeFile : Config.getExcludeFiles()) {
			copyFilter = FileFilterUtils.and(copyFilter, FileFilterUtils.notFileFilter(new WildcardFileFilter(excludeFile)));
		}

		if (Config.hasJavascriptConfig()) {
			copyFilter = FileFilterUtils.and(copyFilter, FileFilterUtils.notFileFilter(FileFilterUtils.suffixFileFilter(".js")));
		}
		if (Config.hasCssConfig()) {
			copyFilter = FileFilterUtils.and(copyFilter, FileFilterUtils.notFileFilter(FileFilterUtils.suffixFileFilter(".css")));
		}
		if (Config.hasHtmlConfig()) {
			copyFilter = FileFilterUtils.and(copyFilter, FileFilterUtils.notFileFilter(FileFilterUtils.suffixFileFilter(".html")));
		}

		copyFilter = FileFilterUtils.makeCVSAware(copyFilter);
		copyFilter = FileFilterUtils.makeSVNAware(copyFilter);

		return copyFilter;
	}

}
