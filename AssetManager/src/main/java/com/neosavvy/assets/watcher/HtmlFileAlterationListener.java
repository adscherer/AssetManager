package com.neosavvy.assets.watcher;

import java.io.File;

import com.neosavvy.assets.util.Log;

public class HtmlFileAlterationListener extends AbstractFileAlterationListener {

	@Override
	public void onFileDelete(File arg0) {
		Log.info("Html Deleted...");
	}

	public void onFileCreate(File arg0) {
		Log.info("Html File Created...");
	}

	public void onFileChange(File file) {
		Log.info("Html File Changed...");
	}

}
