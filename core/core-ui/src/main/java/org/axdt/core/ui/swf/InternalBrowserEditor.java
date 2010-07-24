package org.axdt.core.ui.swf;

import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

/**
 * @author mb0
 */
public class InternalBrowserEditor extends BrowserSupportPlayer {

	public static final String ID = "org.axdt.core.ui.swf.internalEditor";

	public InternalBrowserEditor.Instance launch(URL target) throws Exception {
		return new InternalBrowserEditor.Instance(target);
	}

	public boolean isAvailable() {
		return getSupport().isInternalWebBrowserAvailable();
	}

	public static class Instance extends BrowserSupportPlayer.Instance {

		public Instance(URL target) throws CoreException {
			super(target);
		}

		@Override
		protected IWebBrowser createBrowser() throws CoreException {
			int style = IWorkbenchBrowserSupport.LOCATION_BAR
					| IWorkbenchBrowserSupport.NAVIGATION_BAR;
			return getSupport().createBrowser(style, target.getPath(), null, null);
		}
	}
}
