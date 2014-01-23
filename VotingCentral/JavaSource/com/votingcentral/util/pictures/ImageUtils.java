/*
 * Created on Apr 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.pictures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ImageUtils {

	public static int THUMB_H = 40;

	public static int THUMB_W = 60;

	private static int MAX_GREATER_BY = 2;

	public static final int MIN_H = 80;

	public static final int MIN_W = 112;

	public static final int MAX_H = MAX_GREATER_BY * MIN_H;

	public static final int MAX_W = MAX_GREATER_BY * MIN_W;

	private static String PROGRESSIVE_JPEG = "image/pjpeg";

	private static Set supportedMimes = uniqueMimes();

	private static String supportedFormats = getSuppFormats();

	private static ImageUtils imgUtils = null;

	public static ImageUtils getInstance() {
		if (imgUtils == null) {
			imgUtils = new ImageUtils();
		}
		return imgUtils;
	}

	/**
	 * @param oldImage
	 * @param os
	 * @throws IOException
	 */
	public void resizeImage(BufferedImage img, int maxH, int maxW,
			OutputStream out) throws IOException {

		int h = img.getHeight();
		int w = img.getWidth();

		if (h > maxH || w > maxW) {
			int fullH = h;
			int fullW = w;
			double d = (double) h / (double) w;
			if (h > maxH) {
				h = maxH;
				w = (int) Math.round(h / d);
			}
			if (w > maxW) {
				w = maxW;
				h = (int) Math.round(w * d);
			}

			AffineTransform xform = AffineTransform.getScaleInstance((double) h
					/ (double) fullH, (double) w / (double) fullW);
			AffineTransformOp op = new AffineTransformOp(xform,
					AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			BufferedImage newImage = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D gr = newImage.createGraphics();
			int top = 0;
			int left = 0;
			gr.drawImage(img, op, left, top);
			ImageIO.write((RenderedImage) newImage, "jpeg", out);
		} else {
			ImageIO.write((RenderedImage) img, "jpeg", out);
		}

	}

	/**
	 * @param oldImage
	 * @param os
	 * @throws IOException
	 */
	public BufferedImage getScaledImageWithQuality(BufferedImage img, int maxH,
			int maxW, int quality) throws IOException {
		int thumbWidth = maxW;
		int thumbHeight = maxH;
		double thumbRatio = (double) thumbWidth / (double) thumbHeight;
		int imageWidth = img.getWidth(null);
		int imageHeight = img.getHeight(null);
		double imageRatio = (double) imageWidth / (double) imageHeight;
		if (thumbRatio < imageRatio) {
			thumbHeight = (int) (thumbWidth / imageRatio);
		} else {
			thumbWidth = (int) (thumbHeight * imageRatio);
		}
		// draw original image to thumbnail image object and
		// scale it to the new size on-the-fly
		BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(img, 0, 0, thumbWidth, thumbHeight, null);
		return thumbImage;
	}

	public VCImage getScaledHeightAndWidthMin(int ch, int cw) {
		return getScaledHeightAndWidthInternal(ch, cw, MIN_H, MIN_W);
	}

	public VCImage getScaledHeightAndWidthMax(int ch, int cw) {
		return getScaledHeightAndWidthInternal(ch, cw, MAX_H, MAX_W);
	}

	//for a given height calculate the width and see if that fits our model.
	private VCImage getScaledHeightAndWidthInternal(int ch, int cw,
			int predefH, int predefW) {
		VCImage newImg = new VCImage();
		//set new height to min height
		int nh = predefH;
		//calculate the cooresponding width
		int nw = Math.round(predefH * cw / ch);
		if (nw <= predefW) {
			//we are, good return nw and nh
			newImg.setHeight(nh);
			newImg.setWidth(nw);
			return newImg;
		}
		nw = predefW;
		nh = Math.round(predefW * ch / cw);
		if (nh <= predefH) {
			//we are, good return nw and nh
			newImg.setHeight(nh);
			newImg.setWidth(nw);
			return newImg;
		}
		newImg.setHeight(predefH);
		newImg.setWidth(predefW);
		return newImg;
	}

	/**
	 * @param img
	 * @param out
	 * @throws IOException
	 */
	public void resizeImageThumbnail(BufferedImage img, OutputStream out)
			throws IOException {
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

		int h = img.getHeight();
		int w = img.getWidth();

		int fullH = h;
		int fullW = w;
		double d = (double) h / (double) w;
		if (h > THUMB_H) {
			h = THUMB_H;
			w = (int) Math.round(h / d);
		}
		if (w > THUMB_W) {
			w = THUMB_W;
			h = (int) Math.round(w * d);
		}

		AffineTransform xform = AffineTransform.getScaleInstance((double) h
				/ (double) fullH, (double) w / (double) fullW);
		AffineTransformOp op = new AffineTransformOp(xform,
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		BufferedImage newImage = new BufferedImage(THUMB_W, THUMB_H,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D gr = newImage.createGraphics();
		gr.setColor(Color.BLACK);
		gr.fillRect(0, 0, THUMB_H - 1, THUMB_W - 1);
		int top = (THUMB_H - h) / 2;
		int left = (THUMB_W - w) / 2;
		gr.drawImage(img, op, left, top);
		ImageIO.write((RenderedImage) newImage, "jpeg", out);

	}

	// Converts all strings in 'strings' to lowercase
	// and returns an array containing the unique values.
	// All returned values are lowercase.
	private static Set uniqueMimes() {
		String[] mimes = ImageIO.getReaderMIMETypes();
		Set set = new HashSet();
		for (int i = 0; i < mimes.length; i++) {
			String name = mimes[i].toLowerCase();
			set.add(name);
		}
		//jpeg, but browsers treat this format differently from the
		//regular jpeg. support this format too.
		set.add(PROGRESSIVE_JPEG);
		return set;
	}

	private static String getSuppFormats() {
		String[] formats = ImageIO.getReaderFormatNames();
		Set set = new HashSet();
		for (int i = 0; i < formats.length; i++) {
			String name = formats[i].toLowerCase();
			set.add(name);
		}
		return StringUtils.join((String[]) set.toArray(new String[0]), ", ");
	}

	public boolean isVCSupportedMimeType(String mimeType) {
		return supportedMimes.contains(mimeType);
	}

	public String getVCSupportedFileFormats() {
		return supportedFormats;
	}

	public int getImageWidth(byte[] fileContent) throws IOException {
		return getImage(fileContent).getWidth();
	}

	public int getImageHeight(byte[] fileContent) throws IOException {
		return getImage(fileContent).getHeight();
	}

	private BufferedImage getImage(byte[] fileContent) throws IOException {
		ByteArrayInputStream inStream = null;
		inStream = new ByteArrayInputStream(fileContent);
		return ImageIO.read(inStream);

	}
}