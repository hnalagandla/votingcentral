package com.votingcentral.servlets;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;

import com.votingcentral.initialize.VCInitializer;

/**
 * Servlet implementation class for Servlet: VotingCentralController
 *
 */
 public class VotingCentralController extends ActionServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public VotingCentralController() {
		super();
	}   	 	  	  	  
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		// perform our app related initialization.
		super.init();		
		VCInitializer.getInstance().init();
	}   
}