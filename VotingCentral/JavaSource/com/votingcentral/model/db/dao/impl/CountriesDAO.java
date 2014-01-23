/*
 * Created on Aug 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.ICountriesDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.CountriesTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CountriesDAO extends RdbmsDAO implements ICountriesDAO{

	private static Log log = LogFactory.getLog(CountriesDAO.class);
	private static List countriesList = null;
	
	public List getCountries() throws SQLException {
		if (countriesList != null) {
			return countriesList;
		}
		countriesList =  new ArrayList();
		String sql = SQLResources.getSQLResource("get.all.countries");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement getCountries = null;
		
		try {
			conn = VCDAOFactory.getConnection();
			getCountries = conn.prepareStatement(sql);			
			rs = getCountries.executeQuery();

			while (rs.next()) {
				CountriesTO cto = new CountriesTO();
				fillCountriesTO(rs, cto);
				countriesList.add(cto);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (getCountries != null) {
					getCountries.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return countriesList;
	}
	
	private void fillCountriesTO(ResultSet rs, CountriesTO cto) throws SQLException {

		String id = rs.getString("ID");
		if (id != null) {
			cto.setId(id);
		}

		String name = rs.getString("NAME");
		if (name != null) {
			cto.setCountryName(name);
		}

		String code = rs.getString("CODE");
		if (code != null) {
			cto.setCountryCode(code);
		}

	}
}