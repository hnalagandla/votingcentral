/*
 * Created on Jun 3, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.location;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import com.votingcentral.model.db.dao.ICountriesDAO;
import com.votingcentral.model.db.dao.IStatesDAO;
import com.votingcentral.model.db.dao.IStatesFIPS10And4DAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.CountriesTO;
import com.votingcentral.model.db.dao.to.StateTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class LocationBO {

	private static Log log = LogFactory.getLog(LocationBO.class);

	private static LocationBO lbo = null;

	private static Map stateIdToStateMap = null;

	private static Map countryIdToStateMap = null;

	private static Map countryIdToCountryMap = null;

	private static Map countryCodeToCountryMap = null;

	private LocationBO() {

	}

	public static LocationBO getInstance() {
		if (lbo == null) {
			lbo = new LocationBO();
		}
		return lbo;
	}

	/**
	 * @return Returns the listOfCountries.
	 * @throws SQLException
	 */
	public List getListOfCountries() throws SQLException {
		List listOfCountries = new ArrayList();
		DAOFactory dao = DAOFactory.getDAOFactory();
		ICountriesDAO cdo = dao.getCountriesDAO();
		Iterator itr = cdo.getCountries().iterator();
		listOfCountries.add(new LabelValueBean("Pick a Country",
				"Pick a Country"));
		while (itr.hasNext()) {
			CountriesTO cto = (CountriesTO) itr.next();
			if (!cto.getCountryCode().equalsIgnoreCase("XX")) {
				listOfCountries.add(new LabelValueBean(cto.getCountryName(),
						cto.getId()));
			}
		}
		return listOfCountries;
	}

	/**
	 * @return Returns the americanStates.
	 * @throws SQLException
	 */
	public List getStatesByCountry(int countryId) throws SQLException {
		List states = null;
		String countryCode = getCountryCodeByCountryId(countryId);
		DAOFactory dao = DAOFactory.getDAOFactory();
		IStatesFIPS10And4DAO sdo = dao.getStatesFIPS10And4DAO();
		states = sdo.getStatesByCountryCode(countryCode);
		List result = new ArrayList();
		if (states == null || states.size() == 0) {
			result.add(new LabelValueBean("No State Required",
					"No State Required."));
		} else {
			result.add(new LabelValueBean("Pick a State", "Pick a State"));
			for (Iterator itr = states.iterator(); itr.hasNext();) {
				StateTO sto = (StateTO) itr.next();
				result.add(new LabelValueBean(sto.getName(), Integer
						.toString(sto.getStateId())));
			}
		}
		return result;
	}

	public String getStateByStateIdCountryId(int stateId, int countryId)
			throws SQLException {
		if (stateIdToStateMap == null) {
			stateIdToStateMap = new HashMap();
			DAOFactory dao = DAOFactory.getDAOFactory();
			IStatesDAO sdo = dao.getStatesDAO();
			List states = sdo.getStatesForCountry(countryId);
			for (Iterator itr = states.iterator(); itr.hasNext();) {
				StateTO sto = (StateTO) itr.next();
				stateIdToStateMap.put(new Integer(sto.getStateId()), sto);
			}
		}
		Integer sid = new Integer(stateId);
		return ((StateTO) stateIdToStateMap.get(sid)).getName();
	}

	public String getStateCodeByStateIdCountryId(int stateId, int countryId)
			throws SQLException {
		if (stateIdToStateMap == null) {
			stateIdToStateMap = new HashMap();
			DAOFactory dao = DAOFactory.getDAOFactory();
			IStatesDAO sdo = dao.getStatesDAO();
			List states = sdo.getStatesForCountry(countryId);
			for (Iterator itr = states.iterator(); itr.hasNext();) {
				StateTO sto = (StateTO) itr.next();
				stateIdToStateMap.put(new Integer(sto.getStateId()), sto);
			}
		}
		Integer sid = new Integer(stateId);
		StateTO to = (StateTO) stateIdToStateMap.get(sid);
		if (to != null) {
			return to.getCode();
		} else {
			return "UNK";
		}

	}

	public int getFipsStateIdByCountryCodeStateCode(String countryCode,
			String stateCode) throws SQLException {
		log.error("Looking up state for countryCode: " + countryCode
				+ " stateCode:" + stateCode);
		StateTO to = null;
		DAOFactory dao = DAOFactory.getDAOFactory();
		IStatesFIPS10And4DAO sdao = dao.getStatesFIPS10And4DAO();
		if (countryCode.equalsIgnoreCase("US")
				|| countryCode.equalsIgnoreCase("CA")) {
			String stateName = getStateNameByStateCode(stateCode);
			to = sdao.getFIPS10And4StateByStateName(stateName);
		} else {
			to = sdao.getFIPS10And4StateByStateCodeAndCountryCode(countryCode,
					stateCode);
		}
		return to.getStateId();
	}

	public String getStateNameByStateCode(String stateCode) throws SQLException {
		log.error("looking up state by stateCode" + stateCode);
		DAOFactory dao = DAOFactory.getDAOFactory();
		IStatesDAO sdao = dao.getStatesDAO();
		StateTO sto = sdao.getStateByStateCode(stateCode);
		if (sto == null) {
			log.error("found no state");
			return "";
		}
		return sto.getName();
	}

	public String getCountryByCountryId(int countryId) throws SQLException {
		if (countryIdToCountryMap == null) {
			countryIdToCountryMap = new HashMap();
			DAOFactory dao = DAOFactory.getDAOFactory();
			ICountriesDAO cdao = dao.getCountriesDAO();
			List countries = cdao.getCountries();
			for (Iterator itr = countries.iterator(); itr.hasNext();) {
				CountriesTO cto = (CountriesTO) itr.next();
				countryIdToCountryMap.put(new Integer(cto.getId()), cto);
			}
		}
		Integer cid = new Integer(countryId);
		return ((CountriesTO) countryIdToCountryMap.get(cid)).getCountryName();
	}

	public String getCountryCodeByCountryId(int countryId) throws SQLException {
		if (countryIdToCountryMap == null) {
			countryIdToCountryMap = new HashMap();
			DAOFactory dao = DAOFactory.getDAOFactory();
			ICountriesDAO cdao = dao.getCountriesDAO();
			List countries = cdao.getCountries();
			for (Iterator itr = countries.iterator(); itr.hasNext();) {
				CountriesTO cto = (CountriesTO) itr.next();
				countryIdToCountryMap.put(new Integer(cto.getId()), cto);
			}
		}
		Integer cid = new Integer(countryId);
		return ((CountriesTO) countryIdToCountryMap.get(cid)).getCountryCode();
	}

	public int getCountryIdByCountryCode(String countryCode)
			throws SQLException {
		if (countryCodeToCountryMap == null) {
			countryCodeToCountryMap = new HashMap();
			DAOFactory dao = DAOFactory.getDAOFactory();
			ICountriesDAO cdao = dao.getCountriesDAO();
			List countries = cdao.getCountries();
			for (Iterator itr = countries.iterator(); itr.hasNext();) {
				CountriesTO cto = (CountriesTO) itr.next();
				countryCodeToCountryMap.put(cto.getCountryCode(), cto);
			}
		}
		return Integer.parseInt(((CountriesTO) countryCodeToCountryMap
				.get(countryCode)).getId());
	}
}