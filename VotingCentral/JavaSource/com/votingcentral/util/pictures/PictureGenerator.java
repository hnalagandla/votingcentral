/*
 * Created on Sep 16, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.pictures;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * @author harishn
 * 
 * Orginally from JGossip code
 */
public class PictureGenerator {

	private static PictureGenerator instance = null;

	private BufferedImage image;

	private Graphics2D g;

	private GradientPaint gradient;

	private int height = 30;

	private int width = 200;

	private int grid = (int) (height / 3.1);

	private int maxH = 150;

	private int maxW = 150;

	private int thumbH = 150;

	private int thumbW = 150;

	private static Object lock = new Object();

	/**
	 * Method <code>getInstance</code> return generator instance
	 * 
	 * @return generator instance
	 * @throws <code>SteedException</code>
	 */
	public static PictureGenerator getInstance() {

		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new PictureGenerator();
				}
			}
		}

		return instance;
	}

	private PictureGenerator() {

		int newHeight = 30;
		int newWidth = 200;
		int newGrid = 9;

		int newMaxH = 150;
		int newMaxW = 150;

		int newThunbH = 100;
		int newThunbW = 100;

		maxH = newMaxH;
		maxW = newMaxW;

		thumbH = newThunbH;
		thumbW = newThunbW;

		height = newHeight;
		width = newWidth;
		grid = newGrid;

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("Dialog", Font.ITALIC, (int) (height * 0.9)));
		gradient = new GradientPaint(0, 0, Color.WHITE, width - 1, height - 1,
				Color.GRAY);
	}

	/**
	 * Method <code>getHeight</code> return picture height
	 * 
	 * @return height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Method <code>getWidth</code> return picture width
	 * 
	 * @return picture width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Method <code>generatePicture</code> write generated picture into given
	 * stream
	 * 
	 * @param <code>label</code> label to write on picture
	 * @param <code>os</code> stream to write picture
	 * @throws <code>IOException</code>
	 */
	public void generatePicture(String label, OutputStream out)
			throws IOException {

		/* background */
		g.setPaint(gradient);
		g.fillRect(0, 0, width - 1, height - 1);
		/* label */
		g.setColor(Color.BLUE);
		g.drawString(label,
				(width - g.getFontMetrics().stringWidth(label)) / 2,
				(int) (height * 0.85));

		/* grid */
		/* horizontal */
		g.setColor(Color.BLACK);
		for (int y = (int) (grid / 2); y < height; y += grid) {
			g.drawLine(0, y, width, y);
		}

		/* vertical */
		for (int x = (int) (grid / 2); x < width; x += grid) {
			g.drawLine(x, 0, x, height);
		}
		ImageIO.write((RenderedImage) image, "jpeg", out);
	}

	public static void main(String[] args) {//for tests only
		String label = "PictureGeneratorTest" + System.currentTimeMillis()
				+ ".jpg";
		File out = new File(label);
		try {
			getInstance().generatePicture(label, new FileOutputStream(out));		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}