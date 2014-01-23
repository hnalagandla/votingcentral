/*
 * Created on Nov 15, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.pictures;

import javax.media.jai.JAI;
import javax.media.jai.OperationRegistry;
import javax.media.jai.RegistryMode;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ListRegistry {
	public ListRegistry() {
		or = JAI.getDefaultInstance().getOperationRegistry();
		String[] modeNames = RegistryMode.getModeNames();
		String[] descriptorNames;

		for (int i = 0; i < modeNames.length; i++) {
			System.out.println("For registry mode: " + modeNames[i]);

			descriptorNames = or.getDescriptorNames(modeNames[i]);
			for (int j = 0; j < descriptorNames.length; j++) {
				System.out.print("\tRegistered Operator: ");
				System.out.println(descriptorNames[j]);
			}
		}
	}

	public static void main(String[] args) {
		new ListRegistry();
	}

	private OperationRegistry or;
}