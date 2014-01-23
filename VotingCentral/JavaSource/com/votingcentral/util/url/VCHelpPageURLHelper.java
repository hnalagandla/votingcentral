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
public class VCHelpPageURLHelper extends VCURLHelper {
	private static VCHelpPageURLHelper helper = null;

	private VCHelpPageURLHelper() {

	}

	public static VCHelpPageURLHelper getInstance() {
		if (helper == null) {
			helper = new VCHelpPageURLHelper();
		}
		return helper;
	}

	public String getAboutUsUrl(String domContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domContext).append("/aboutUs.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public String getContactUsUrl(String domContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domContext).append("/contactUs.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public String getFAQUrl(String domContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domContext).append("/faq.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public String getPrizesFAQUrl(String domContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domContext).append("/prizesFaq.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public String getEmailPromotionFAQUrl(String domContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domContext).append("/prizesFaq.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public String getPrizesTAndCUrl(String domContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domContext).append("/prizesTAndC.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public String getPrivacyPolicyUrl(String domContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domContext).append("/privacyPolicy.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public String getTermsAndConditionsUrl(String domContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domContext).append("/tAndC.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}
}