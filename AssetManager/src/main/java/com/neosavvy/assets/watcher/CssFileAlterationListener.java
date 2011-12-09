package com.neosavvy.assets.watcher;

import java.io.File;

public class CssFileAlterationListener extends AbstractFileAlterationListener {

	@Override
	public void onFileDelete(File arg0) {
		System.out.println("Css Deleted...");

	}

	public void onFileCreate(File arg0) {
		System.out.println("Css File Created...");

	}

	public void onFileChange(File arg0) {
		System.out.println("Css File Changed...");
	}

}
