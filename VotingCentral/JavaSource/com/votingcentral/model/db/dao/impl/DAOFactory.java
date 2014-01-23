package com.votingcentral.model.db.dao.impl;

import com.votingcentral.model.db.dao.ICategoryDAO;
import com.votingcentral.model.db.dao.IChartDAO;
import com.votingcentral.model.db.dao.IContestEntriesDAO;
import com.votingcentral.model.db.dao.ICountriesDAO;
import com.votingcentral.model.db.dao.IGeoLocationDAO;
import com.votingcentral.model.db.dao.IMailDAO;
import com.votingcentral.model.db.dao.IMessageBoardDAO;
import com.votingcentral.model.db.dao.IMessagesDAO;
import com.votingcentral.model.db.dao.IPersonalConfigDAO;
import com.votingcentral.model.db.dao.IPollCommentsDAO;
import com.votingcentral.model.db.dao.IPollUserHistoryDAO;
import com.votingcentral.model.db.dao.IPollsDAO;
import com.votingcentral.model.db.dao.IStatesDAO;
import com.votingcentral.model.db.dao.IStatesFIPS10And4DAO;
import com.votingcentral.model.db.dao.ISubjectBoardDAO;
import com.votingcentral.model.db.dao.ITafDAO;
import com.votingcentral.model.db.dao.IVCContentDAO;
import com.votingcentral.model.db.dao.IVCFilesDAO;
import com.votingcentral.model.db.dao.IVCParametersDAO;
import com.votingcentral.model.db.dao.IVCUserDAO;
import com.votingcentral.model.db.dao.IVCUserInfoDAO;
import com.votingcentral.model.db.dao.IVCUserLinksDAO;
import com.votingcentral.model.db.dao.IVCUserPrefsDAO;
import com.votingcentral.model.db.dao.IVCUserRolesDAO;
import com.votingcentral.model.db.dao.IVCVacoPointsDAO;
import com.votingcentral.model.db.dao.IVCWinnersDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;

public abstract class DAOFactory {

	public abstract ICategoryDAO getCategoryDAO();

	public abstract ICountriesDAO getCountriesDAO();

	public abstract IMessageBoardDAO getMessageBoardDAO();

	public abstract ISubjectBoardDAO getSubjectBoardDAO();

	public abstract IMessagesDAO getMessagesDAO();

	public abstract IPersonalConfigDAO getPersonalConfigDAO();

	public abstract IPollsDAO getPollsDAO();

	public abstract IPollUserHistoryDAO getPollUserHistoryDAO();

	public abstract IVCUserDAO getVCUserDAO();

	public abstract IChartDAO getChartDAO();

	public abstract IGeoLocationDAO getGeoLocationDAO();

	public abstract ITafDAO getTafDAO();

	public abstract IContestEntriesDAO getContestFilesDAO();

	public abstract IVCFilesDAO getVCFilesDAO();

	public abstract IVCContentDAO getVCContentDAO();

	public abstract IStatesDAO getStatesDAO();

	public abstract IPollCommentsDAO getPollCommentsDAO();

	public abstract IVCUserRolesDAO getVCUserRolesDAO();

	public abstract IMailDAO getMailDAO();

	public abstract IVCUserInfoDAO getVCUserInfoDAO();

	public abstract IVCUserLinksDAO getVCUserLinksDAO();

	public abstract IVCVacoPointsDAO getVCVacoPointsDAO();

	public abstract IVCWinnersDAO getVCWinnersDAO();

	public abstract IVCUserPrefsDAO getVCUserPrefsDAO();

	public abstract IStatesFIPS10And4DAO getStatesFIPS10And4DAO();

	public abstract IVCParametersDAO getVCParametersDAO();

	public static DAOFactory getDAOFactory() {
		//if we plan to implement multiple databases
		// add them here.
		return new VCDAOFactory();
	}

}