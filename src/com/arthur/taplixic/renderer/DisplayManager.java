package com.arthur.taplixic.renderer;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

public class DisplayManager {
	private static long window;
	
	private static int WIDTH;
	private static int HEIGHT;
	
	private static DoubleBuffer mouseNX = BufferUtils.createDoubleBuffer(1);
	private static DoubleBuffer mouseNY = BufferUtils.createDoubleBuffer(1);
	public static double mouseX;
	public static double mouseY;

	public static void createDisplay(int WIDTHX, int HEIGHTX, String title) {
		WIDTH = WIDTHX;
		HEIGHT = HEIGHTX;
		
		// initializing window
		if (!glfwInit())
			throw new IllegalStateException("Failed to initialize GLFW!");

		// window settings
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

		// creating window
		window = glfwCreateWindow(WIDTH, HEIGHT, title, 0, 0);
		if (window == 0)
			throw new RuntimeException("Failed to create GLFW window!");

		// window position
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - WIDTH) / 2, (vidmode.height() - HEIGHT) / 2);

		// linking opengl context
		glfwMakeContextCurrent(window);

		// create gl context
		GL.createCapabilities();

		// show window
		glfwShowWindow(window);
	}
	
	
	public static void update() {
		glfwGetCursorPos(window, mouseNX, mouseNY);
		mouseX = mouseNX.get(0);
		mouseY = mouseNY.get(0);
		
		glfwSwapBuffers(window);
		glfwPollEvents();
	}
	
	public static void destroy() {
		glfwWindowShouldClose(window);
		glfwTerminate();
		System.exit(0);
	}

	public static long getWindow() {
		return window;
	}
	
	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}
	

}
