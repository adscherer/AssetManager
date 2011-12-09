package com.neosavvy.assets.util;

import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

@Ignore
public class FileCopyTest {

	private File projectDir;

	@Rule
	public TemporaryFolder recursivePath = new TemporaryFolder() {
		@Override
		public void create() throws IOException {
			super.create();

			projectDir = this.newFolder("codeStatistics/healthways-jar");
			new File(projectDir, "index.html").createNewFile();

			File srcDir = new File(projectDir, "src");
			srcDir.mkdir();
			File webDir = new File(projectDir, "web");
			webDir.mkdir();
			File dataDir = new File(projectDir, "data");
			dataDir.mkdir();

			new File(srcDir, "Test1.java").createNewFile();
			new File(srcDir, "Test2.java").createNewFile();
			new File(srcDir, "Test3.java").createNewFile();
			new File(srcDir, "Test4.java").createNewFile();

			new File(dataDir, "test1.sql").createNewFile();
			new File(dataDir, "test2.sql").createNewFile();
			new File(dataDir, "test3.sql").createNewFile();

			new File(webDir, "test1.vm").createNewFile();
			new File(webDir, "test2.vm").createNewFile();
			new File(webDir, "test3.vm").createNewFile();
			new File(webDir, "test4.vm").createNewFile();

			new File(webDir, "test1.css").createNewFile();
			new File(webDir, "test2.css").createNewFile();

			new File(webDir, "test1.js").createNewFile();
			new File(webDir, "test2.js").createNewFile();
		}
	};

	
	@Rule
	public TemporaryFolder recursivePath2 = new TemporaryFolder() {
		@Override
		public void create() throws IOException {
			super.create();

			projectDir = this.newFolder("codeStatistics/healthways-jar");
			new File(projectDir, "index.html").createNewFile();

			File srcDir = new File(projectDir, "src");
			srcDir.mkdir();
			File webDir = new File(projectDir, "web");
			webDir.mkdir();
			File dataDir = new File(projectDir, "data");
			dataDir.mkdir();

			new File(srcDir, "Test1.java").createNewFile();
			new File(srcDir, "Test2.java").createNewFile();
			new File(srcDir, "Test3.java").createNewFile();
			new File(srcDir, "Test4.java").createNewFile();

			new File(dataDir, "test1.sql").createNewFile();
			new File(dataDir, "test2.sql").createNewFile();
			new File(dataDir, "test3.sql").createNewFile();

			new File(webDir, "test1.vm").createNewFile();
			new File(webDir, "test2.vm").createNewFile();
			new File(webDir, "test3.vm").createNewFile();
			new File(webDir, "test4.vm").createNewFile();

			new File(webDir, "test1.css").createNewFile();
			new File(webDir, "test2.css").createNewFile();

			new File(webDir, "test1.js").createNewFile();
			new File(webDir, "test2.js").createNewFile();
		}
	};

	@Test
	public void testFindFiles() {

	}

}
