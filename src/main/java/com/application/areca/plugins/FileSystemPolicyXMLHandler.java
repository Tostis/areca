package com.application.areca.plugins;

import org.w3c.dom.Node;

import com.application.areca.AbstractTarget;
import com.application.areca.adapters.read.TargetXMLReader;
import com.application.areca.impl.policy.FileSystemPolicy;
import com.myJava.util.xml.AdapterException;

/**
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
public interface FileSystemPolicyXMLHandler {
    public FileSystemPolicy read(Node mediumNode, AbstractTarget target, TargetXMLReader reader) throws AdapterException;
    public void write(FileSystemPolicy policy, boolean removeSensitiveData, StringBuffer sb);
    public void setVersion(int version);
}
