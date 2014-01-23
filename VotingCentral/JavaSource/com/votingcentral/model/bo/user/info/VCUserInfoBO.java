/*
 * Created on Apr 23, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.user.info;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.apache.struts.util.LabelValueBean;

import com.votingcentral.model.db.dao.IVCUserInfoDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.VCUserInfoTO;
import com.votingcentral.model.enums.userinfo.DrinkTypeEnum;
import com.votingcentral.model.enums.userinfo.EducationEnum;
import com.votingcentral.model.enums.userinfo.IdeologyEnum;
import com.votingcentral.model.enums.userinfo.MaritalStatusEnum;
import com.votingcentral.model.enums.userinfo.NumChildrenEnum;
import com.votingcentral.model.enums.userinfo.PartyEnum;
import com.votingcentral.model.enums.userinfo.RaceEnum;
import com.votingcentral.model.enums.userinfo.ReligionEnum;
import com.votingcentral.model.enums.userinfo.SalaryRangeEnum;
import com.votingcentral.model.enums.userinfo.SexualOrientationEnum;
import com.votingcentral.model.enums.userinfo.SmokeTypeEnum;
import com.votingcentral.model.enums.userinfo.TShirtSizeEnum;
import com.votingcentral.model.enums.userinfo.UnionMembershipEnum;
import com.votingcentral.model.enums.userinfo.ZodiacEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCUserInfoBO {
	private static VCUserInfoBO infoBo = null;

	private VCUserInfoBO() {
	}

	public static VCUserInfoBO getInstance() {
		if (infoBo == null) {
			infoBo = new VCUserInfoBO();
		}
		return infoBo;
	}

	public List getRacesForView() {
		List l = new ArrayList();
		ListIterator itr = RaceEnum.getIterator(RaceEnum.class);
		while (itr.hasNext()) {
			RaceEnum race = (RaceEnum) itr.next();
			l.add(new LabelValueBean(race.getName(), race.getName()));
		}
		Collections.sort(l);
		l.add(0, new LabelValueBean("No Answer", "No Answer"));
		return l;
	}

	public List getSalariesForView() {
		List l = new ArrayList();
		ListIterator itr = SalaryRangeEnum.getIterator(SalaryRangeEnum.class);
		while (itr.hasNext()) {
			SalaryRangeEnum salary = (SalaryRangeEnum) itr.next();
			l.add(new LabelValueBean(salary.getName(), salary.getName()));
		}
		l.add(0, new LabelValueBean("No Answer", "No Answer"));
		return l;
	}

	public List getUnionMemberShipForView() {
		List l = new ArrayList();
		ListIterator itr = UnionMembershipEnum
				.getIterator(UnionMembershipEnum.class);
		while (itr.hasNext()) {
			UnionMembershipEnum race = (UnionMembershipEnum) itr.next();
			l.add(new LabelValueBean(race.getName(), race.getName()));
		}
		Collections.sort(l);
		l.add(0, new LabelValueBean("No Answer", "No Answer"));
		return l;
	}

	public List getEducationForView() {
		List l = new ArrayList();
		ListIterator itr = EducationEnum.getIterator(EducationEnum.class);
		while (itr.hasNext()) {
			EducationEnum edu = (EducationEnum) itr.next();
			l.add(new LabelValueBean(edu.getName(), edu.getName()));
		}
		l.add(0, new LabelValueBean("No Answer", "No Answer"));
		return l;
	}

	public List getPartyForView() {
		List l = new ArrayList();
		ListIterator itr = PartyEnum.getIterator(PartyEnum.class);
		while (itr.hasNext()) {
			PartyEnum party = (PartyEnum) itr.next();
			l.add(new LabelValueBean(party.getName(), party.getName()));
		}
		Collections.sort(l);
		l.add(0, new LabelValueBean("No Answer", "No Answer"));
		return l;
	}

	public List getIdeologyForView() {
		List l = new ArrayList();
		ListIterator itr = IdeologyEnum.getIterator(IdeologyEnum.class);
		while (itr.hasNext()) {
			IdeologyEnum id = (IdeologyEnum) itr.next();
			l.add(new LabelValueBean(id.getName(), id.getName()));
		}
		Collections.sort(l);
		l.add(0, new LabelValueBean("No Answer", "No Answer"));
		return l;
	}

	public List getReligionForView() {
		List l = new ArrayList();
		ListIterator itr = ReligionEnum.getIterator(ReligionEnum.class);
		while (itr.hasNext()) {
			ReligionEnum re = (ReligionEnum) itr.next();
			l.add(new LabelValueBean(re.getName(), re.getName()));
		}
		Collections.sort(l);
		l.add(0, new LabelValueBean("No Answer", "No Answer"));
		return l;
	}

	public List getZodiacForView() {
		List l = new ArrayList();
		ListIterator itr = ZodiacEnum.getIterator(ZodiacEnum.class);
		while (itr.hasNext()) {
			ZodiacEnum zo = (ZodiacEnum) itr.next();
			l.add(new LabelValueBean(zo.getName(), zo.getName()));
		}
		l.add(0, new LabelValueBean("No Answer", "No Answer"));
		return l;
	}

	public List getMaritalStatusForView() {
		List l = new ArrayList();
		ListIterator itr = MaritalStatusEnum
				.getIterator(MaritalStatusEnum.class);
		while (itr.hasNext()) {
			MaritalStatusEnum ms = (MaritalStatusEnum) itr.next();
			l.add(new LabelValueBean(ms.getName(), ms.getName()));
		}
		Collections.sort(l);
		l.add(0, new LabelValueBean("No Answer", "No Answer"));
		return l;
	}

	public List getNumChildrenForView() {
		List l = new ArrayList();
		ListIterator itr = NumChildrenEnum.getIterator(NumChildrenEnum.class);
		while (itr.hasNext()) {
			NumChildrenEnum ms = (NumChildrenEnum) itr.next();
			l.add(new LabelValueBean(ms.getName(), ms.getName()));
		}
		l.add(0, new LabelValueBean("No Answer", "No Answer"));
		return l;
	}

	public List getSmokeTypeForView() {
		List l = new ArrayList();
		ListIterator itr = SmokeTypeEnum.getIterator(SmokeTypeEnum.class);
		while (itr.hasNext()) {
			SmokeTypeEnum ms = (SmokeTypeEnum) itr.next();
			l.add(new LabelValueBean(ms.getName(), ms.getName()));
		}
		l.add(0, new LabelValueBean("No Answer", "No Answer"));
		return l;
	}

	public List getDrinkTypeForView() {
		List l = new ArrayList();
		ListIterator itr = DrinkTypeEnum.getIterator(DrinkTypeEnum.class);
		while (itr.hasNext()) {
			DrinkTypeEnum ms = (DrinkTypeEnum) itr.next();
			l.add(new LabelValueBean(ms.getName(), ms.getName()));
		}
		l.add(0, new LabelValueBean("No Answer", "No Answer"));
		return l;
	}

	public List getSexualOrientationForView() {
		List l = new ArrayList();
		ListIterator itr = SexualOrientationEnum
				.getIterator(SexualOrientationEnum.class);
		while (itr.hasNext()) {
			SexualOrientationEnum ms = (SexualOrientationEnum) itr.next();
			l.add(new LabelValueBean(ms.getName(), ms.getName()));
		}
		l.add(0, new LabelValueBean("No Answer", "No Answer"));
		return l;
	}

	public List getTShirtSizesForView() {
		List l = new ArrayList();
		ListIterator itr = TShirtSizeEnum.getIterator(TShirtSizeEnum.class);
		while (itr.hasNext()) {
			TShirtSizeEnum ms = (TShirtSizeEnum) itr.next();
			l.add(new LabelValueBean(ms.getName(), ms.getName()));
		}
		l.add(0, new LabelValueBean("No Answer", "No Answer"));
		return l;
	}

	public VCUserInfoTO getVCUserInfoByUserId(long userId) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserInfoDAO vdao = dao.getVCUserInfoDAO();
		return vdao.getUserById(userId);
	}

	public void createVCUserInfo(VCUserInfoTO vto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserInfoDAO vdao = dao.getVCUserInfoDAO();
		vdao.createUser(vto);
	}

	public void updateVCUserInfo(VCUserInfoTO vto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserInfoDAO vdao = dao.getVCUserInfoDAO();
		vdao.updateUser(vto);
	}

	public void upsertVCUserInfo(VCUserInfoTO vto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserInfoDAO vdao = dao.getVCUserInfoDAO();
		boolean rowsUpdated = vdao.updateUser(vto);
		if (!rowsUpdated) {
			createVCUserInfo(vto);
		}
	}

	public void setImageId(long userId, String imageId, boolean isImagePublic)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserInfoDAO vdao = dao.getVCUserInfoDAO();
		vdao.setImageIdByUser(userId, imageId, isImagePublic);
	}

	public void incrementViewsCount(long userId) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserInfoDAO vdao = dao.getVCUserInfoDAO();
		vdao.incrementProfileViewsCount(userId);
	}
}