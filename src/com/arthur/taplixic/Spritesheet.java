package com.arthur.taplixic;

import static org.lwjgl.opengl.GL11.*;

import java.nio.*;

import org.lwjgl.*;
import org.lwjgl.stb.*;

public class Spritesheet {
	private int id, width, height, channels;

	public Spritesheet(String filepath) {
		try {
			IntBuffer wx = BufferUtils.createIntBuffer(1);
			IntBuffer hx = BufferUtils.createIntBuffer(1);
			IntBuffer cx = BufferUtils.createIntBuffer(1);
			
			ByteBuffer buffer = STBImage.stbi_load("./res/spritesheet.png", wx, hx, cx, 4);
			
			this.width = wx.get(0);
			this.height = hx.get(0);
			this.channels = cx.get(0);
			
//			buffer.flip();
			
			id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			
			STBImage.stbi_image_free(buffer);
			
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
