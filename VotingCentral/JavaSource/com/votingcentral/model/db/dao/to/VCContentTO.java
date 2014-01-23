/*
 * Created on Jun 5, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.sql.Timestamp;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCContentTO {

	private long id;

	private String tableName;

	private String columnName;

	private String whereId1;

	private String whereValue1;

	private String whereId2;

	private String whereValue2;

	private String whereId3;

	private String whereValue3;

	private String content;

	private Timestamp createTimestamp;

	private Timestamp modifyTimestamp;

	/**
	 * @return Returns the columnName.
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName
	 *            The columnName to set.
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return Returns the createTimestamp.
	 */
	public Timestamp getCreateTimestamp() {
		return createTimestamp;
	}

	/**
	 * @param createTimestamp
	 *            The createTimestamp to set.
	 */
	public void setCreateTimestamp(Timestamp createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return Returns the modifyTimestamp.
	 */
	public Timestamp getModifyTimestamp() {
		return modifyTimestamp;
	}

	/**
	 * @param modifyTimestamp
	 *            The modifyTimestamp to set.
	 */
	public void setModifyTimestamp(Timestamp modifyTimestamp) {
		this.modifyTimestamp = modifyTimestamp;
	}

	/**
	 * @return Returns the tableName.
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 *            The tableName to set.
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return Returns the whereId1.
	 */
	public String getWhereId1() {
		return whereId1;
	}

	/**
	 * @param whereId1
	 *            The whereId1 to set.
	 */
	public void setWhereId1(String whereId1) {
		this.whereId1 = whereId1;
	}

	/**
	 * @return Returns the whereId2.
	 */
	public String getWhereId2() {
		return whereId2;
	}

	/**
	 * @param whereId2
	 *            The whereId2 to set.
	 */
	public void setWhereId2(String whereId2) {
		this.whereId2 = whereId2;
	}

	/**
	 * @return Returns the whereId3.
	 */
	public String getWhereId3() {
		return whereId3;
	}

	/**
	 * @param whereId3
	 *            The whereId3 to set.
	 */
	public void setWhereId3(String whereId3) {
		this.whereId3 = whereId3;
	}

	/**
	 * @return Returns the whereValue1.
	 */
	public String getWhereValue1() {
		return whereValue1;
	}

	/**
	 * @param whereValue1
	 *            The whereValue1 to set.
	 */
	public void setWhereValue1(String whereValue1) {
		this.whereValue1 = whereValue1;
	}

	/**
	 * @return Returns the whereValue2.
	 */
	public String getWhereValue2() {
		return whereValue2;
	}

	/**
	 * @param whereValue2
	 *            The whereValue2 to set.
	 */
	public void setWhereValue2(String whereValue2) {
		this.whereValue2 = whereValue2;
	}

	/**
	 * @return Returns the whereValue3.
	 */
	public String getWhereValue3() {
		return whereValue3;
	}

	/**
	 * @param whereValue3
	 *            The whereValue3 to set.
	 */
	public void setWhereValue3(String whereValue3) {
		this.whereValue3 = whereValue3;
	}
}