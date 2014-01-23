/*
 * Created on Apr 14, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.votingcentral.model.db.dao.ICategoryDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.CategoryTO;
import com.votingcentral.model.enums.VCCategoryTypeEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CategoryBO {

	private static CategoryBO category = null;

	public static String SELECT_A_CATEGORY = "Select A Category";

	private static Map superCatsView = new LinkedHashMap();

	private static Map superCats = new LinkedHashMap();

	private CategoryBO() {

	}

	public static CategoryBO getInstance() {
		if (category == null) {
			category = new CategoryBO();
		}
		return category;
	}

	public List getListOfSuperCategoriesForView(VCCategoryTypeEnum type)
			throws SQLException {
		List superCatListForView = (List) superCatsView.get(type);

		if (superCatListForView == null) {
			superCatListForView = new ArrayList();
			DAOFactory dao = DAOFactory.getDAOFactory();
			ICategoryDAO catDao = dao.getCategoryDAO();
			Iterator itr = catDao.getDistinctSuperCategoriesByType(type)
					.iterator();
			superCatListForView.add(new LabelValueBean(SELECT_A_CATEGORY,
					SELECT_A_CATEGORY));
			while (itr.hasNext()) {
				CategoryTO cto = (CategoryTO) itr.next();
				superCatListForView.add(new LabelValueBean(cto
						.getSuperCategory(), cto.getSuperCategory()));
			}
			superCatsView.put(type, superCatListForView);
		}
		return superCatListForView;
	}

	public List getListOfSuperCategories(VCCategoryTypeEnum type)
			throws SQLException {
		List superCatsList = (List) superCats.get(type);

		if (superCatsList == null) {
			superCatsList = new ArrayList();
			DAOFactory dao = DAOFactory.getDAOFactory();
			ICategoryDAO catDao = dao.getCategoryDAO();
			superCatsList = catDao.getDistinctSuperCategoriesByType(type);
			superCats.put(type, superCatsList);
		}
		return superCatsList;
	}

	public String getNextSuperCategory(VCCategoryTypeEnum type, String superCat)
			throws SQLException {

		List superCatsList = getListOfSuperCategories(type);
		int i = 0;
		for (Iterator itr = superCatsList.iterator(); itr.hasNext(); i++) {
			CategoryTO cto = (CategoryTO) itr.next();
			if (cto.getSuperCategory().equalsIgnoreCase(superCat)) {
				break;
			}
		}
		if (i == superCatsList.size() - 1) {
			i = 0;
		} else {
			i++;
		}
		return ((CategoryTO) superCatsList.get(i)).getSuperCategory();
	}

	public boolean isValidCategory(VCCategoryTypeEnum type, String category)
			throws SQLException {
		List res = getListOfSuperCategories(type);
		for (Iterator itr = res.iterator(); itr.hasNext();) {
			CategoryTO cto = (CategoryTO) itr.next();
			if (cto.getSuperCategory().equalsIgnoreCase(category)) {
				return true;
			}
		}
		return false;
	}
}