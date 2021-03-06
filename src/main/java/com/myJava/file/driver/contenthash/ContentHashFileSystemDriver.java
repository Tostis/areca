package com.myJava.file.driver.contenthash;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;

import com.myJava.file.OutputStreamListener;
import com.myJava.file.driver.FileSystemDriver;
import com.myJava.file.driver.AbstractLinkableFileSystemDriver;
import com.myJava.util.log.Logger;
import com.myJava.util.taskmonitor.TaskCancelledException;
import com.myJava.util.taskmonitor.TaskMonitor;

/**
 * This class behaves like a standard "FileSystemDriver", except that it doesn't store the content of the files 
 * it handles, but their hash code.
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 *
 */

 /*
 Copyright 2005-2015, Olivier PETRUCCI.

This file is part of Areca.

    Areca is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Areca is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Areca; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 */
public class ContentHashFileSystemDriver 
extends AbstractLinkableFileSystemDriver {

	public ContentHashFileSystemDriver(FileSystemDriver predecessor) {
		super();
		setPredecessor(predecessor);
	}

	public InputStream getCachedFileInputStream(File file) throws IOException {
		return predecessor.getCachedFileInputStream(file);
	}

	public InputStream getFileInputStream(File file) throws IOException {
		return predecessor.getFileInputStream(file);
	}

	public OutputStream getCachedFileOutputStream(File file) throws IOException {
		try {
			return new ContentHashOutputStream(predecessor.getCachedFileOutputStream(file));
		} catch (NoSuchAlgorithmException e) {
			Logger.defaultLogger().error(e);
			throw new IOException(e);
		}
	}

    public String getPhysicalPath(File file) {
    	return predecessor.getPhysicalPath(file);
	}
	
	public OutputStream getFileOutputStream(File file, boolean append, OutputStreamListener listener) throws IOException {
		try {
			return new ContentHashOutputStream(predecessor.getFileOutputStream(file, append, listener));
		} catch (NoSuchAlgorithmException e) {
			Logger.defaultLogger().error(e);
			throw new IOException(e);
		}
	}

	public OutputStream getFileOutputStream(File file, boolean append) throws IOException {
		try {
			return new ContentHashOutputStream(predecessor.getFileOutputStream(file, append));
		} catch (NoSuchAlgorithmException e) {
			Logger.defaultLogger().error(e);
			throw new IOException(e);
		}
	}

	public OutputStream getFileOutputStream(File file) throws IOException {
		try {
			return new ContentHashOutputStream(predecessor.getFileOutputStream(file));
		} catch (NoSuchAlgorithmException e) {
			Logger.defaultLogger().error(e);
			throw new IOException(e);
		}
	}
	
    public void forceDelete(File file, TaskMonitor monitor)
    throws IOException, TaskCancelledException {
    	predecessor.forceDelete(file, monitor);
	}
}

