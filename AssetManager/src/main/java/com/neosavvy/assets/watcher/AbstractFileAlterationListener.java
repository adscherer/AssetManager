package com.neosavvy.assets.watcher;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class AbstractFileAlterationListener implements FileAlterationListener {

		public void onStop(FileAlterationObserver arg0) {
			// TODO Auto-generated method stub
		}

		public void onStart(FileAlterationObserver arg0) {

		}

		public void onFileDelete(File arg0) {
			System.out.println("Deleted...");

		}

		public void onFileCreate(File arg0) {
			System.out.println("File Created...");

		}

		public void onFileChange(File arg0) {
			System.out.println("File Changed...");

		}

		public void onDirectoryDelete(File arg0) {
			// TODO Auto-generated method stub

		}

		public void onDirectoryCreate(File arg0) {
			// TODO Auto-generated method stub

		}

		public void onDirectoryChange(File arg0) {
			// TODO Auto-generated method stub
		}
}
