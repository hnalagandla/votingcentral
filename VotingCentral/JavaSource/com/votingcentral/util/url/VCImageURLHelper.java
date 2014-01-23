/*
 * Created on Jan 9, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.url;

import com.votingcentral.util.UnSyncStringBuffer;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCImageURLHelper extends VCURLHelper {
	private static VCImageURLHelper helper = null;

	private VCImageURLHelper() {

	}

	public static VCImageURLHelper getInstance() {
		if (helper == null) {
			helper = new VCImageURLHelper();
		}
		return helper;
	}

	public String getEmailPromotionImageUrl(String domContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domContext).append("/images/vc-on-twitter.gif");
		return url.toString();
	}
}