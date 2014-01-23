/*
 * Created on Aug 13, 2005
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
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.cache.SimpleTimedRefreshCache;
import com.votingcentral.env.EnvProps;
import com.votingcentral.model.db.dao.ICategoryDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.CategoryTO;
import com.votingcentral.model.enums.VCCategoryTypeEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CategoryDAO extends RdbmsDAO implements ICategoryDAO {

	private static Log log = LogFactory.getLog(CategoryDAO.class);

	private static final int TOP_CATEGORIES_NUMBER = 10;

	private static ArrayList popularCats = null;

	private static SimpleTimedRefreshCache popularCatsCache = null;

	public List getMostPopularCategories() throws SQLException {
		long cacheRefreshIntervalMillis = new Long(
				EnvProps
						.getProperty("popular.categories.cache.refresh.interval.millis"))
				.longValue();
		if (popularCatsCache == null) {
			SimpleTimedRefreshCache popularCatsCache = new SimpleTimedRefreshCache(
					popularCats, cacheRefreshIntervalMillis, new TimerTask() {
						public void run() {
							try {
								getMostPopularCategoriesInternal(popularCats);
							} catch (SQLException e) {
								log.fatal("Problem retrieving Popular cats", e);
								throw new RuntimeException(e);
							}
						}
					});
		}
		return (List) popularCatsCache.get();
	}

	private void getMostPopularCategoriesInternal(ArrayList list)
			throws SQLException {
		//this query actually hits the Polls table
		String sql = SQLResources.getSQLResource("get.most.popular.categories");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement getPopCats = null;
		if (list == null) {
			list = new ArrayList();
		}
		try {
			conn = VCDAOFactory.getConnection();
			getPopCats = conn.prepareStatement(sql);
			getPopCats.setInt(1, TOP_CATEGORIES_NUMBER);
			rs = getPopCats.executeQuery();

			while (rs.next()) {
				CategoryTO cto = new CategoryTO();
				fillCatTO(rs, cto);
				list.add(cto);
			}
		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (getPopCats != null) {
					getPopCats.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
				throw e;
			}
		}
	}

	public List getDistinctSuperCategories() throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.distinct.super.categories");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		ArrayList list = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			rs = pps1.executeQuery();

			while (rs.next()) {
				String superCat = rs.getString("SUPER_CATEGORY");
				if (superCat != null) {
					CategoryTO cto = new CategoryTO();
					cto.setSuperCategory(superCat);
					list.add(cto);
				}
			}
		} catch (SQLException e) {
			log.fatal("getDistinctSuperCategories :", e);
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps1 != null) {
					pps1.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return list;
	}

	public List getDistinctCategories() throws SQLException {
		//this query actually hits the Polls table
		String sql = SQLResources.getSQLResource("get.distinct.categories");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement getDisCats = null;
		ArrayList list = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			getDisCats = conn.prepareStatement(sql);
			rs = getDisCats.executeQuery();

			while (rs.next()) {
				String cat = rs.getString("CATEGORY");
				if (cat != null) {
					CategoryTO cto = new CategoryTO();
					cto.setSuperCategory(cat);
					list.add(cto);
				}
			}
		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (getDisCats != null) {
					getDisCats.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return list;
	}

	public List getDistinctSubCategories() throws SQLException {
		//this query actually hits the Polls table
		String sql = SQLResources.getSQLResource("get.distinct.sub.categories");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement getDisSubCats = null;
		ArrayList list = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			getDisSubCats = conn.prepareStatement(sql);
			rs = getDisSubCats.executeQuery();

			while (rs.next()) {
				String subCat = rs.getString("SUB_CATEGORY");
				if (subCat != null) {
					CategoryTO cto = new CategoryTO();
					cto.setSubCategory(subCat);
					list.add(cto);
				}
			}
		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (getDisSubCats != null) {
					getDisSubCats.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return list;
	}

	private void fillCatTO(ResultSet rs, CategoryTO cto) throws SQLException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.ICategoryDAO#getDistinctSuperCategoriesByType()
	 */
	public List getDistinctSuperCategoriesByType(VCCategoryTypeEnum type)
			throws SQLException {
		String sql = SQLResources.getSQLResource("get.categories.by.type");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		ArrayList list = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, type.getName());
			rs = pps.executeQuery();

			while (rs.next()) {
				String cat = rs.getString("SUPER_CATEGORY");
				if (cat != null) {
					CategoryTO cto = new CategoryTO();
					cto.setSuperCategory(cat);
					list.add(cto);
				}
			}
		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return list;
	}
}