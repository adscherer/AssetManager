package com.neosavvy.assets.builder;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.neosavvy.assets.util.Config;
import com.neosavvy.assets.util.StringUtils;

public abstract class AbstractBuilder implements Builder {

	public void compile() {
		for (File file : getFiles()) {
			compile(file);
		}
	}

	public Collection<File> getFiles() {
		IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(fileExtension()));

		return FileUtils.listFiles(new File(Config.getSrcDirectory()), files, TrueFileFilter.INSTANCE);
	}

	public String concat() throws Exception {
		return concat(getFiles());
	}

	public String concat(Collection<File> files) throws Exception {
		StringBuilder destination = new StringBuilder();
		for (File file : files) {
			destination.append(FileUtils.readFileToString(file));
		}

		return destination.toString();
	}

	public void write(String destinationFile, String compiled) throws Exception {
		FileUtils.writeStringToFile(new File(StringUtils.joinPath(Config.getDestinationDirectory(), destinationFile)), compiled, "UTF-8");
	}

	public abstract String fileExtension();

	public void compile(File file) {

	}

	public String getDestinationPath(File file) throws Exception {
		if (StringUtils.isEquivalent(".", Config.getSrcDirectory())) {
			return file.getPath();
		}

		return StringUtils.substringAfter(FilenameUtils.separatorsToUnix(file.getCanonicalPath()), FilenameUtils.separatorsToUnix(Config.getSrcDirectory()));
	}
}
