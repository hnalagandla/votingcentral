package com.votingcentral.model.db.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.impl.CategoryDAO;
import com.votingcentral.model.db.dao.impl.ChartDAO;
import com.votingcentral.model.db.dao.impl.ContestEntriesDAO;
import com.votingcentral.model.db.dao.impl.CountriesDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.impl.GeoLocationDAO;
import com.votingcentral.model.db.dao.impl.MailDAO;
import com.votingcentral.model.db.dao.impl.MessageBoardDAO;
import com.votingcentral.model.db.dao.impl.MessagesDAO;
import com.votingcentral.model.db.dao.impl.PersonalConfigDAO;
import com.votingcentral.model.db.dao.impl.PollCommentsDAO;
import com.votingcentral.model.db.dao.impl.PollUserHistoryDAO;
import com.votingcentral.model.db.dao.impl.PollsDAO;
import com.votingcentral.model.db.dao.impl.StatesDAO;
import com.votingcentral.model.db.dao.impl.StatesFIPS10And4DAO;
import com.votingcentral.model.db.dao.impl.SubjectBoardDAO;
import com.votingcentral.model.db.dao.impl.SubjectDAO;
import com.votingcentral.model.db.dao.impl.TafDAO;
import com.votingcentral.model.db.dao.impl.VCContentDAO;
import com.votingcentral.model.db.dao.impl.VCFilesDAO;
import com.votingcentral.model.db.dao.impl.VCParametersDAO;
import com.votingcentral.model.db.dao.impl.VCUserDAO;
import com.votingcentral.model.db.dao.impl.VCUserInfoDAO;
import com.votingcentral.model.db.dao.impl.VCUserLinksDAO;
import com.votingcentral.model.db.dao.impl.VCUserPrefsDAO;
import com.votingcentral.model.db.dao.impl.VCUserRolesDAO;
import com.votingcentral.model.db.dao.impl.VCVacoPointsDAO;
import com.votingcentral.model.db.dao.impl.VCWinnersDAO;
import com.votingcentral.util.ResourceLocator;

public class VCDAOFactory extends DAOFactory {

	private static DataSource dataSource = null;

	private static Log log = LogFactory.getLog(VCDAOFactory.class);

	// method to create database connections
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	private void setDataSource() {
		dataSource = ResourceLocator.getDataSource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.db.dao.DAOFactory#getCategoryDAO()
	 */
	public ICategoryDAO getCategoryDAO() {
		setDataSource();
		return new CategoryDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.db.dao.DAOFactory#getMessageBoardDAO()
	 */
	public IMessageBoardDAO getMessageBoardDAO() {
		setDataSource();
		return new MessageBoardDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.db.dao.DAOFactory#getMessageBoardDAO()
	 */
	public ISubjectBoardDAO getSubjectBoardDAO() {
		setDataSource();
		return new SubjectBoardDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.db.dao.DAOFactory#getMessagesDAO( )
	 */
	public IMessagesDAO getMessagesDAO() {
		setDataSource();
		return new MessagesDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.db.dao.DAOFactory#getSubjectDAO( )
	 */
	public ISubjectDAO getSubjectDAO() {
		setDataSource();
		return new SubjectDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.db.dao.DAOFactory#getPersonalConfigDAO( )
	 */
	public IPersonalConfigDAO getPersonalConfigDAO() {
		setDataSource();
		return new PersonalConfigDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.db.dao.DAOFactory#getPollsDAO( )
	 */
	public IPollsDAO getPollsDAO() {
		setDataSource();
		return new PollsDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.db.dao.DAOFactory#getPollUserHistoryDAO( )
	 */
	public IPollUserHistoryDAO getPollUserHistoryDAO() {
		setDataSource();
		return new PollUserHistoryDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.db.dao.DAOFactory#getVCUserDAO( )
	 */
	public IVCUserDAO getVCUserDAO() {
		setDataSource();
		return new VCUserDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.db.dao.DAOFactory#getCountriesDAO()
	 */
	public ICountriesDAO getCountriesDAO() {
		setDataSource();
		return new CountriesDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.db.dao.DAOFactory#getChartDAO()
	 */
	public IChartDAO getChartDAO() {
		setDataSource();
		return new ChartDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.db.dao.DAOFactory#getGeoLocationDAO()
	 */
	public IGeoLocationDAO getGeoLocationDAO() {
		setDataSource();
		return new GeoLocationDAO();
	}

	public ITafDAO getTafDAO() {
		setDataSource();
		return new TafDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.impl.DAOFactory#getContestFilesDAO()
	 */
	public IContestEntriesDAO getContestFilesDAO() {
		setDataSource();
		return new ContestEntriesDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.impl.DAOFactory#getVCFilesDAO()
	 */
	public IVCFilesDAO getVCFilesDAO() {
		setDataSource();
		return new VCFilesDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.impl.DAOFactory#getVCContentDAO()
	 */
	public IVCContentDAO getVCContentDAO() {
		setDataSource();
		return new VCContentDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.impl.DAOFactory#getStatesDAO()
	 */
	public IStatesDAO getStatesDAO() {
		setDataSource();
		return new StatesDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.impl.DAOFactory#getPollCommentsDAO()
	 */
	public IPollCommentsDAO getPollCommentsDAO() {
		setDataSource();
		return new PollCommentsDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.impl.DAOFactory#getVCUserRolesDAO()
	 */
	public IVCUserRolesDAO getVCUserRolesDAO() {
		setDataSource();
		return new VCUserRolesDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.impl.DAOFactory#getMailDAO()
	 */
	public IMailDAO getMailDAO() {
		setDataSource();
		return new MailDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.impl.DAOFactory#getVCUserInfoDAO()
	 */
	public IVCUserInfoDAO getVCUserInfoDAO() {
		setDataSource();
		return new VCUserInfoDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.impl.DAOFactory#getVCUserLinksDAO()
	 */
	public IVCUserLinksDAO getVCUserLinksDAO() {
		setDataSource();
		return new VCUserLinksDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.impl.DAOFactory#getVCVacoPointsDAO()
	 */
	public IVCVacoPointsDAO getVCVacoPointsDAO() {
		setDataSource();
		return new VCVacoPointsDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.impl.DAOFactory#getVCWinnersDAO()
	 */
	public IVCWinnersDAO getVCWinnersDAO() {
		setDataSource();
		return new VCWinnersDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.impl.DAOFactory#getVCUserPrefsDAO()
	 */
	public IVCUserPrefsDAO getVCUserPrefsDAO() {
		setDataSource();
		return new VCUserPrefsDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.impl.DAOFactory#getIStatesFIPS10And4DAO()
	 */
	public IStatesFIPS10And4DAO getStatesFIPS10And4DAO() {
		setDataSource();
		return new StatesFIPS10And4DAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.impl.DAOFactory#getVCParametersDAO()
	 */
	public IVCParametersDAO getVCParametersDAO() {
		setDataSource();
		return new VCParametersDAO();
	}

}