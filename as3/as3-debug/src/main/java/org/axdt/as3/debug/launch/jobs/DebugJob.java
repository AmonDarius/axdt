package org.axdt.as3.debug.launch.jobs;

import org.axdt.as3.debug.preferences.As3DebugPreferences;
import org.axdt.debugger.IAxdtDebugger;
import org.axdt.launch.AbstractLaunchJob;
import org.axdt.launch.AxdtLaunchContext;
import org.axdt.launch.JobMutexRule;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

public class DebugJob extends AbstractLaunchJob {

	public DebugJob(AxdtLaunchContext context) {
		super("Starting debugger", context);
		setRule(JobMutexRule.getMutex());
	}

	@Override
	protected IStatus doRun(IProgressMonitor monitor) throws Exception {
		String debuggerId = context.getDebuggerId();
		As3DebugPreferences pref = As3DebugPreferences.getInstance();
		IAxdtDebugger debugger = pref.getDebugger(debuggerId);
		if (debugger == null)
			throw new IllegalStateException("no debugger configured");
		debugger.debug(context, monitor);
		return null;
	}
}
