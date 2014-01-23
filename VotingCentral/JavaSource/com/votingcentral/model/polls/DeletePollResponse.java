/*
 * Created on Apr 15, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.polls;

import com.votingcentral.util.UnSyncStringBuffer;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DeletePollResponse {
	private int imageFilesDeleted = 0;

	private int commentsDeleted = 0;

	private int votesDeleted = 0;

	private int searchRowsDeleted = 0;

	private int tafRecsDeleted = 0;

	private int contestEntriesDeleted = 0;

	private int pollRecsDeleted = 0;

	/**
	 * @return Returns the commentsDeleted.
	 */
	public int getCommentsDeleted() {
		return commentsDeleted;
	}

	/**
	 * @param commentsDeleted
	 *            The commentsDeleted to set.
	 */
	public void setCommentsDeleted(int commentsDeleted) {
		this.commentsDeleted = commentsDeleted;
	}

	/**
	 * @return Returns the contestEntriesDeleted.
	 */
	public int getContestEntriesDeleted() {
		return contestEntriesDeleted;
	}

	/**
	 * @param contestEntriesDeleted
	 *            The contestEntriesDeleted to set.
	 */
	public void setContestEntriesDeleted(int contestEntriesDeleted) {
		this.contestEntriesDeleted = contestEntriesDeleted;
	}

	/**
	 * @return Returns the imageFilesDeleted.
	 */
	public int getImageFilesDeleted() {
		return imageFilesDeleted;
	}

	/**
	 * @param imageFilesDeleted
	 *            The imageFilesDeleted to set.
	 */
	public void setImageFilesDeleted(int imageFilesDeleted) {
		this.imageFilesDeleted = imageFilesDeleted;
	}

	/**
	 * @return Returns the pollRecsDeleted.
	 */
	public int getPollRecsDeleted() {
		return pollRecsDeleted;
	}

	/**
	 * @param pollRecsDeleted
	 *            The pollRecsDeleted to set.
	 */
	public void setPollRecsDeleted(int pollRecsDeleted) {
		this.pollRecsDeleted = pollRecsDeleted;
	}

	/**
	 * @return Returns the searchRowsDeleted.
	 */
	public int getSearchRowsDeleted() {
		return searchRowsDeleted;
	}

	/**
	 * @param searchRowsDeleted
	 *            The searchRowsDeleted to set.
	 */
	public void setSearchRowsDeleted(int searchRowsDeleted) {
		this.searchRowsDeleted = searchRowsDeleted;
	}

	/**
	 * @return Returns the tafRecsDeleted.
	 */
	public int getTafRecsDeleted() {
		return tafRecsDeleted;
	}

	/**
	 * @param tafRecsDeleted
	 *            The tafRecsDeleted to set.
	 */
	public void setTafRecsDeleted(int tafRecsDeleted) {
		this.tafRecsDeleted = tafRecsDeleted;
	}

	/**
	 * @return Returns the votesDeleted.
	 */
	public int getVotesDeleted() {
		return votesDeleted;
	}

	/**
	 * @param votesDeleted
	 *            The votesDeleted to set.
	 */
	public void setVotesDeleted(int votesDeleted) {
		this.votesDeleted = votesDeleted;
	}

	public String toString() {
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append("Image Files Deleted:");
		buffer.append(imageFilesDeleted);
		buffer.append("<br>");
		buffer.append("Comments Deleted:");
		buffer.append(commentsDeleted);
		buffer.append("<br>");
		buffer.append("Votes Deleted:");
		buffer.append(votesDeleted);
		buffer.append("<br>");
		buffer.append("Searchable rows Deleted:");
		buffer.append(searchRowsDeleted);
		buffer.append("<br>");
		buffer.append("Taf records Deleted:");
		buffer.append(tafRecsDeleted);
		buffer.append("<br>");
		buffer.append("Contest entries Deleted:");
		buffer.append(contestEntriesDeleted);
		buffer.append("<br>");
		buffer.append("Poll records Deleted:");
		buffer.append(pollRecsDeleted);
		buffer.append("<br>");
		return buffer.toString();
	}
}