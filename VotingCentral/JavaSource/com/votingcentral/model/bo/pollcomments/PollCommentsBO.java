/*
 * Created on Jul 16, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.pollcomments;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.bo.content.VCContentBO;
import com.votingcentral.model.bo.vaco.VCVacoPointsBO;
import com.votingcentral.model.db.dao.IPollCommentsDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.to.PollCommentsTO;
import com.votingcentral.model.db.dao.to.VCContentTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.VCActivityTypeEnum;
import com.votingcentral.model.search.SearchTablesColumns;
import com.votingcentral.pres.web.to.PollCommentsWTO;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollCommentsBO {
	private static Log log = LogFactory.getLog(PollCommentsBO.class);

	private static PollCommentsBO commentsBO = null;

	private PollCommentsBO() {

	}

	public static PollCommentsBO getInstance() {
		if (commentsBO == null) {
			commentsBO = new PollCommentsBO();
		}
		return commentsBO;
	}

	public List getCommentsByPoll(String pollId) {
		VCDAOFactory factory = new VCDAOFactory();
		IPollCommentsDAO dao = factory.getPollCommentsDAO();
		List comments = null;
		try {
			comments = dao.getCommentsByPoll(pollId);
		} catch (SQLException e) {
			log.error("Exception retrieving comments", e);
		}
		return comments;
	}

	public List getCommentsByPollAndUserId(String pollId, long userId) {
		VCDAOFactory factory = new VCDAOFactory();
		IPollCommentsDAO dao = factory.getPollCommentsDAO();
		List comments = null;
		try {
			comments = dao.getCommentsByPollAndUserId(pollId, userId);
		} catch (SQLException e) {
			log.error("Exception retrieving comments", e);
		}
		return comments;
	}

	public List getCommentsByPollForDisplay(String domContext, String pollId) {
		List res = getCommentsByPoll(pollId);
		List display = new ArrayList();
		if (res == null || res.size() == 0) {
			return display;
		}
		for (Iterator itr = res.iterator(); itr.hasNext();) {
			PollCommentsTO pto = (PollCommentsTO) itr.next();
			PollCommentsWTO pwto = new PollCommentsWTO();
			try {
				BeanUtils.copyProperties(pwto, pto);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pwto.setUserLink(VCURLHelper.getVCUserPublicProfileUrl(domContext,
					pwto.getUserName()));
			display.add(pwto);
		}
		return display;
	}

	public void addCommentsForPoll(String pollId, PollCommentsTO pto)
			throws SQLException {
		VCDAOFactory factory = new VCDAOFactory();
		IPollCommentsDAO dao = factory.getPollCommentsDAO();
		try {
			dao.addCommentsForPoll(pollId, pto);
			VCContentTO vcto = new VCContentTO();
			fillVCContentTO(pollId, pto, vcto, SearchTablesColumns.COMMENT_TEXT);
			VCContentBO.getInstance().upsertContent(vcto);
			//increment vaco points
			VCVacoPointsBO.getInstance().incrementPoints(pto.getUserId(),
					VCActivityTypeEnum.VC_POINTS_COMMENTS);
		} catch (SQLException e) {
			log.error("Exception adding comments for poll", e);
		}
	}

	public int deleteCommentsForPoll(String pollId) throws SQLException {
		VCDAOFactory factory = new VCDAOFactory();
		IPollCommentsDAO dao = factory.getPollCommentsDAO();
		try {
			return dao.deleteCommentsForPoll(pollId);
		} catch (SQLException e) {
			log.error("Exception deleting comments for poll", e);
		}
		return 0;
	}

	public void fillPollCommentsTO(PollCommentsTO pto, VCUserTO vto,
			String pollId, String comment, String ip) {
		pto.setPollId(pollId);
		pto.setComment(comment);
		pto.setUserName(vto.getDisplayUserName());
		pto.setUserId(vto.getUserId());
		pto.setCreatorIPAddress(ip);
		pto.setFiltered(false);
	}

	private void fillVCContentTO(String pollId, PollCommentsTO pcto,
			VCContentTO vcto, String columnName) {
		vcto.setTableName(SearchTablesColumns.POLL_COMMENTS);
		vcto.setWhereId1(SearchTablesColumns.POLL_ID);
		vcto.setWhereValue1(pollId);
		if (columnName.equalsIgnoreCase(SearchTablesColumns.COMMENT_TEXT)) {
			vcto.setContent(pcto.getComment());
			vcto.setColumnName(SearchTablesColumns.COMMENT_TEXT);
		}
	}
}