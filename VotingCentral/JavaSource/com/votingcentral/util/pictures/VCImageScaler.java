/*
 * Created on Nov 15, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.pictures;

import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.ParameterBlockJAI;
import javax.media.jai.PlanarImage;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCImageScaler {

	private PlanarImage origImage;

	private BufferedImage scaledImage;

	public VCImageScaler(BufferedImage origImage, int width, int height) {
		this.origImage = getPlanarImage(origImage);
		scaleToFit(width, height);
	}

	/**
	 * @return Returns the scaledImage.
	 */
	public BufferedImage getScaledImage() {
		return scaledImage;
	}

	/**
	 * @param scaledImage
	 *            The scaledImage to set.
	 */
	public void setScaledImage(BufferedImage scaledImage) {
		this.scaledImage = scaledImage;
	}

	/**
	 * @param amount
	 *            The amount to scale by (i.e. 2.0F means "twice as big")
	 * 
	 * Scales the image by the given amount
	 */
	private void scale(float amount) {
		ParameterBlockJAI pb = new ParameterBlockJAI("scale");
		pb.addSource(origImage);
		pb.setParameter("xScale", amount); //x Scale Factor
		pb.setParameter("yScale", amount); //y Scale Factor
		pb.setParameter("xTrans", 0.0F); //x Translate amount
		pb.setParameter("yTrans", 0.0F); //y Translate amount
		pb.setParameter("interpolation", new InterpolationNearest());
		scaledImage = ((PlanarImage) JAI.create("scale", pb, null))
				.getAsBufferedImage();
	}

	/**
	 * @param width
	 *            The width this image should be scaled to as a max
	 * 
	 * Scales the image uniformly by calculating the scale value generated by
	 * using the width as a maximum width
	 */
	private void scaleX(float width) {
		float scaleFactor;
		scaleFactor = width / (float) origImage.getWidth();
		scale(scaleFactor);
	}

	/**
	 * @param height
	 *            The height this image should be scaled to as a max
	 * 
	 * Scales the image uniformly by calculating the scale value generated by
	 * using the height as a maximum height.
	 */
	private void scaleY(float height) {
		float scaleFactor;
		scaleFactor = height / (float) origImage.getHeight();
		scale(scaleFactor);
	}

	/**
	 * Resizes the image to fit inside the width and height given. Not
	 * guaranteed to be the exact dimensions given, just fit within them.
	 * 
	 * @param width
	 *            The maximum width to fit
	 * @param height
	 *            The maximum height to fit
	 */
	private void scaleToFit(int width, int height) {
		if (origImage.getWidth() > origImage.getHeight()) {
			scaleX((float) width);
		} else {
			scaleY((float) height);
		}
	}

	private PlanarImage getPlanarImage(BufferedImage image) {
		ParameterBlock pb = new ParameterBlock();
		pb.add(image);
		PlanarImage planarImage = JAI.create("awtImage", pb, null);
		return planarImage;
	}
}