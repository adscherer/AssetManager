package com.neosavvy.assets.watcher;

import java.io.File;

public class JavascriptFileAlterationListener extends AbstractFileAlterationListener {

	@Override
	public void onFileDelete(File arg0) {
		System.out.println("Javascript Deleted...");

	}

	public void onFileCreate(File arg0) {
		System.out.println("Javascript File Created...");

	}

	public void onFileChange(File arg0) {
		System.out.println("Javascript File Changed...");

	}

}
