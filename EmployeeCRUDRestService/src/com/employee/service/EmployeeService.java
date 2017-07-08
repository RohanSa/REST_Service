package com.employee.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;

public class EmployeeService {

	public void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation, int id, String fileName) {

		try {
			File opFile = new File(uploadedFileLocation + "\\" + id + "\\" + fileName);

			opFile.getParentFile().mkdirs();
			OutputStream out = new FileOutputStream(opFile,false);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = uploadedInputStream.read(bytes)) != -1) {
				System.out.println(bytes);
				out.write(bytes, 0, read);
				
				
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public File getEmployeeResume(String upLoadFileLocation, int id) {

		File downloadFile = null;

		try {

			File dir = new File(upLoadFileLocation + "\\" + id);
			// List only files
			File[] files = dir.listFiles(new FileFilter() {
				public boolean accept(File file) {
					return file.isFile();
				}
			});

			if (files == null) {
				return null;
			}

			// Get latest resume file from directory
			Arrays.sort(files, new Comparator<File>() {
				public int compare(File f1, File f2) {
					return Long.compare(f1.lastModified(), f2.lastModified());
				}
			});

			downloadFile = files[files.length - 1];

			System.out.println("downloadFile.getParentFile() " + downloadFile.getParentFile());
			System.out.println("downloadFile.getName() " + downloadFile.getName());

		} catch (Exception e) {
			return null;
		}

		return downloadFile;
	}

}
