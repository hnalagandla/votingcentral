/*
 * Created on Sep 10, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;

import com.votingcentral.model.db.dao.to.GeoLocationTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IGeoLocationDAO {

	public GeoLocationTO getLocation(String ip) throws SQLException;

	public GeoLocationTO getLongAndLat(String ip) throws SQLException;

	public String getCountry(String countryId) throws SQLException;
}