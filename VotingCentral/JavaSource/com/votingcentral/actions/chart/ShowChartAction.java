/*
 * Created on Jul 9, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.chart;

import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.chart.ChartForm;
import com.votingcentral.model.bo.chart.VCChartBO;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.votes.Votes;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.VCChartPerspectiveEnum;
import com.votingcentral.model.enums.VCChartSizeEnum;
import com.votingcentral.model.enums.VCChartTypeEnum;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ShowChartAction extends VCDispatchAction {

	//TODO: move this to properties files

	private static int DEFAULT_CHART_HEIGHT = 500;

	private static int DEFAULT_CHART_WIDTH = 460;

	private static int BIG_CHART_HEIGHT = 700;

	private static int BIG_CHART_WIDTH = 700;

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ChartForm chartForm = (ChartForm) form;
		String pollId = chartForm.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		chartForm.setPollId(pollId);
		String questionId = chartForm.getQuestionId();
		questionId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.QUESTION_ID, questionId);
		chartForm.setQuestionId(questionId);

		if (pollId == null || pollId.length() == 0) {
			//without pollId can't do much.
			return mapping.findForward("home");
		}
		PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
		if (pto == null) {
			//invalid poll id, poll doesn't exist
			return mapping.findForward("home");
		}
		String userName = VCRequestHelper.getUser(request);
		VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
		//if the poll has not ended and if the user is trying to access the
		//results without voting redirect to display poll page.
		if (pto.getEndTimestamp().after(new Date())) {
			if (!Votes.getInstance()
					.hasUserVotedInPoll(vto.getUserId(), pollId)) {
				ActionForward forward = mapping.findForward("showPoll");
				UnSyncStringBuffer buffer = new UnSyncStringBuffer();
				buffer.append(forward.getPath()).append("&").append(
						RequestParameterObjects.POLL_ID).append("=").append(
						pollId);
				ActionForward newForward = new ActionForward();
				newForward.setPath(buffer.toString());
				newForward.setName(forward.getName());
				newForward.setRedirect(true);
				return newForward;
			}
		}
		VCChartTypeEnum chartType = VCChartTypeEnum.get(chartForm.getCct());
		if (chartType == VCChartTypeEnum.INVALID) {
			//set the type as the default.
			chartType = VCChartTypeEnum.DEFAULT;
		}
		VCChartPerspectiveEnum chartPersp = VCChartPerspectiveEnum
				.get(chartForm.getCcp());
		if (chartPersp == VCChartPerspectiveEnum.INVALID) {
			//set the type as the default.
			chartPersp = VCChartPerspectiveEnum.DEFAULT;
		}

		if (VCChartSizeEnum.get(chartForm.getChartSize()) == VCChartSizeEnum.INVALID) {
			//set a default height.
			chartForm.setHeight(DEFAULT_CHART_HEIGHT);
			//set a default width.
			chartForm.setWidth(DEFAULT_CHART_WIDTH);

		} else if (VCChartSizeEnum.get(chartForm.getChartSize()) == VCChartSizeEnum.CUSTOM) {
			if (chartForm.getHeight() == 0) {
				// set a default height.
				chartForm.setHeight(DEFAULT_CHART_HEIGHT);
			}
			if (chartForm.getWidth() == 0) {
				//set a default width.
				chartForm.setWidth(DEFAULT_CHART_WIDTH);
			}
		} else if (VCChartSizeEnum.get(chartForm.getChartSize()) == VCChartSizeEnum.SMALL) {
			//set a default height.
			chartForm.setHeight(DEFAULT_CHART_HEIGHT);
			//set a default width.
			chartForm.setWidth(DEFAULT_CHART_WIDTH);

		} else if (VCChartSizeEnum.get(chartForm.getChartSize()) == VCChartSizeEnum.BIG) {
			//set a big height.
			chartForm.setHeight(BIG_CHART_HEIGHT);
			//set a big width.
			chartForm.setWidth(BIG_CHART_WIDTH);
		}
		
		JFreeChart chart = null;
		if (chartType == VCChartTypeEnum.DEFAULT
				&& chartPersp == VCChartPerspectiveEnum.DEFAULT) {
			chart = VCChartBO.getInstance().getDefaultChart(pollId, questionId);
		} else if (chartType == VCChartTypeEnum.PIE) {
			chart = VCChartBO.getInstance().getPieChart(pollId, questionId);
		} else if (chartType == VCChartTypeEnum.BAR) {
			chart = VCChartBO.getInstance().getChartByPerspective(chartPersp,
					pollId, questionId);
		}
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out, chart, chartForm.getWidth(),
					chartForm.getHeight());
		} catch (Exception e) {
			throw e;
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return show(mapping, form, request, response);
	}

}