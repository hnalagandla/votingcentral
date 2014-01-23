/*
 * Created on Aug 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.ISubjectDAO;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.SubjectTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SubjectDAO extends RdbmsDAO implements ISubjectDAO{

	private static Log log = LogFactory.getLog(SubjectDAO.class);

	/**
	 * 
	 * @param board
	 * @param msg
	 * @return
	 */
	public boolean createSubject(SubjectTO subject) {
		boolean updateFlag = false;

		try {
			String sql = SQLResources.getSQLResource("insert.new.subject");
			PreparedStatement ps = getConnection().prepareStatement(sql);
			
			log.info("insert.new.subject SQL:"+sql);
			ps.setString(1, subject.getSubjectId());
			ps.setString(2, subject.getPollId());			
			ps.setString(3, subject.getSubject());
			ps.setString(4, subject.getImageId());
			ps.setLong(5, subject.getUserId());
			ps.setString(6, "");
			ps.setString(7, "");

			ps.setQueryTimeout(60);
			updateFlag = ps.execute();
			ps.close();
		} catch (SQLException sqle) {
			log.error("", sqle);
		} finally {
			try {
				closeConnection();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return updateFlag;
	}

	/* (non-Javadoc)
	 * @see com.votingcentral.model.db.dao.ISubjectDAO#archiveSubject(java.lang.String)
	 */
	public boolean archiveSubject(String subjectId) {
		boolean updateFlag = false;

		try {
			String sql = SQLResources.getSQLResource("archive.subject");
			log.debug("Archive Subject:"+sql);
			PreparedStatement ps= getConnection().prepareStatement(sql);
			log.debug("subjectId:"+subjectId);
			ps.setString(1, subjectId);
			ps.setQueryTimeout(60);
			updateFlag = ps.execute();
			ps.close();
		} catch (SQLException sqle) {
			log.error("", sqle);
		} finally {
			try {
				closeConnection();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return updateFlag;
	}	

}