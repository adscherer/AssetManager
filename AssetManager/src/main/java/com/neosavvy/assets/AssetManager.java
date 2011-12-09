package com.neosavvy.assets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.OrFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import com.neosavvy.assets.builder.BuildManager;
import com.neosavvy.assets.builder.CssBuilder;
import com.neosavvy.assets.builder.HtmlBuilder;
import com.neosavvy.assets.builder.JavascriptBuilder;
import com.neosavvy.assets.util.Config;
import com.neosavvy.assets.util.Log;
import com.neosavvy.assets.util.StringUtils;
import com.neosavvy.assets.watcher.AbstractFileAlterationListener;

public class AssetManager {

	BuildManager buildManager = new BuildManager();

	public static void main(String[] args) {
		try {
			if (StringUtils.isAnyEquivalent("--watch", args)) {
				new AssetManager().init().watch();
			} else {
				new AssetManager().init().compile();
			}
		} catch (Throwable ex) {
			Log.info("Error within Jammit: %s", ex);
		}
	}

	public AssetManager init() {
		new Config("./yaml.conf").validate();
		buildManager.addBuilder(new CssBuilder());
		buildManager.addBuilder(new JavascriptBuilder());
		buildManager.addBuilder(new HtmlBuilder());

		return this;
	}

	public AssetManager watch() throws Exception {
		Log.info("File watcher starting...");
		FileAlterationMonitor monitor = new FileAlterationMonitor(Config.getMonitorInterval());
		monitor.addObserver(createObserver());
		monitor.start();

		return this;
	}

	public AssetManager compile() throws Exception {
		buildManager.compile();

		return this;
	}

	protected FileAlterationObserver createObserver() {

		List<IOFileFilter> filters = new ArrayList<IOFileFilter>();
		for (String extension : Config.getWatchExtensions()) {
			filters.add(FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(extension)));
		}
		
		filters.add(FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE));

		IOFileFilter filter = FileFilterUtils.and(new OrFileFilter(filters), FileFilterUtils.notFileFilter(FileFilterUtils.nameFileFilter(Config.getDestinationDirectory())));

		// Create the File system observer and register File Listeners
		File f = new File(Config.getSrcDirectory());
		FileAlterationObserver observer = new FileAlterationObserver(f, filter);
		observer.addListener(new AbstractFileAlterationListener() {
			public void onFileCreate(File file) {
				buildManager.compile();
			}

			public void onFileChange(File file) {
				buildManager.compile();
			}

			public void onFileDelete(File file) {
				buildManager.compile();
			}
		});

		return observer;
	}

}
