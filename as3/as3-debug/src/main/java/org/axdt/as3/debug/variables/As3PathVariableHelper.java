/*******************************************************************************
 * Copyright (c) 2010 Martin Schnabel <mb0@mb0.org>.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.axdt.as3.debug.variables;

import java.net.URI;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;

public class As3PathVariableHelper {

	public static String resolvePathString(String targetPath, String templateString) throws CoreException {
		IStringVariableManager manager = VariablesPlugin.getDefault().getStringVariableManager();
		templateString = addTarget(templateString,"resource_loc", targetPath);
		templateString = addTarget(templateString,"resource_name", targetPath);
		templateString = addTarget(templateString,"resource_name_noext", targetPath);
		templateString = addTarget(templateString,"resource_path", targetPath);
		templateString = addTarget(templateString,"deploy_loc", targetPath);
		templateString = addTarget(templateString,"deploy_name", targetPath);
		templateString = addTarget(templateString,"deploy_path", targetPath);
		return manager.performStringSubstitution(templateString, true);
	}
	public static URI resolveURL(String targetPath, String templateString) throws CoreException {
		return URI.create(resolvePathString(targetPath, templateString));
	}
	public static String addTarget(String pathStr, String string, String targetPath) {
		return pathStr.replace("${"+string+"}", "${"+string+":"+targetPath+"}");
	}
}
