/*
 * Created on Aug 1, 2006
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

public final class TextLinkDescTO {
	//any description to show to the user.
	//for eg. search result from Polls
	private String desc;

	//the actual text with the search string.
	private String text;

	//href to links, polls, message boards.
	private String href;

	public TextLinkDescTO() {

	}

	/**
	 * @param desc
	 * @param text
	 */
	public TextLinkDescTO(String desc, String text) {
		super();
		this.desc = desc;
		this.text = text;
	}

	/**
	 * @return Returns the desc.
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            The desc to set.
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return Returns the href.
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href
	 *            The href to set.
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @return Returns the text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            The text to set.
	 */
	public void setText(String text) {
		this.text = text;
	}
}