/*
 * Created on Aug 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.votingcentral.model.db.dao.to.ChartDataSetTO;
import com.votingcentral.util.xml.XXMLException;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IChartDAO {
	public ChartDataSetTO getChartData(String pollid) throws SQLException,
			XXMLException;
	public Map getDistribution(String pollId, String distType);
	public List getDistinctDistTypes(String pollId, String distType);
}