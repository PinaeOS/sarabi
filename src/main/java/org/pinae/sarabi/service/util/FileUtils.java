package org.pinae.sarabi.service.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

	public static File getFile(String path, String filename) {
		File file = new File(filename);
		if (file.exists() && file.isFile()) {
			return file;
		}
		file = new File(path + File.separator + filename);
		if (file.exists() && file.isFile()) {
			return file;
		}
		file = new File(ClassLoaderUtils.getResourcePath("") + File.separator + filename);
		if (file.exists() && file.isFile()) {
			return file;
		}
		file = new File(ClassLoaderUtils.getResourcePath("") + File.separator + path + File.separator + filename);
		if (file.exists() && file.isFile()) {
			return file;
		}
		return null;
	}

	public static File getPath(String path) {
		File file = new File(path);
		if (file.exists()) {
			return file;
		}
		file = new File(ClassLoaderUtils.getResourcePath("") + File.separator + path);
		if (file.exists()) {
			return file;
		}
		return null;
	}

	public static StringBuffer read(String filename) throws IOException {
		File file = getFile(null, filename);
		return read(file);
	}

	public static StringBuffer read(File file) throws IOException {
		if (file != null && file.exists() && file.isFile()) {
			StringBuffer content = new StringBuffer();

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				content.append(line);
			}
			reader.close();
			return content;
		}
		return null;
	}
}
