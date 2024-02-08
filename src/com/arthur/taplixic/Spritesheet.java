package com.arthur.taplixic;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.*;
import java.nio.*;

import javax.imageio.*;

import org.lwjgl.*;

public class Spritesheet {
	private int id, width, height, channels;

	public Spritesheet(String filepath) {
		BufferedImage bi;
		try {
			bi = ImageIO.read(getClass().getResourceAsStream(filepath));
			width = bi.getWidth();
			height = bi.getWidth();
			
			int[] pixels = new int[width * height * 4];
			bi.getRGB(0, 0, width, height, pixels, 0, width);
			
			ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
			for (int x = 0; x < width; ++x) {
				for (int y = 0; y < height; ++y) {
					int pixel = pixels[x * width + y];
					buffer.put((byte)((pixel >> 16) & 0xFF));
					buffer.put((byte)((pixel >> 8) & 0xFF));
					buffer.put((byte)(pixel & 0xFF));
					buffer.put((byte)((pixel >> 24) & 0xFF));
				}
			}
			
			buffer.flip();
			
			id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public int getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getChannels() {
		return channels;
	}

}
