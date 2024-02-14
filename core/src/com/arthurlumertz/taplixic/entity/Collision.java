package com.arthurlumertz.taplixic.entity;

import com.arthurlumertz.taplixic.engine.*;
import com.arthurlumertz.taplixic.level.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.*;

public class Collision {

	public static boolean checkEntity(Level level, Camera camera, Entity entity) {
		Frustum frustum = camera.getCamera().frustum;

		for (int x = 0; x < level.getSize(); ++x) {
			for (int y = 0; y < level.getSize(); ++y) {
				int tile = level.getLevel()[x][y];
				int xo = x * 48;
				int yo = y * 48;

				BoundingBox tileBoundingBox = new BoundingBox(new Vector3(xo, yo, 0), new Vector3(xo + 48, yo + 48, 0));
				if (frustum.boundsInFrustum(tileBoundingBox)) {

					if (level.getTiles()[tile].isCollision() && level.getTiles()[tile].getHealth() > 0) {
						Rectangle rectangle = new Rectangle();
						rectangle.set(xo, yo, 48, 48);

						if (rectangle.overlaps(entity.getBoundingBox())) {
							return true;
						}
					}

				}

			}
		}

		return false;
	}
	
	public static boolean checkObject(Level level, Camera camera, Rectangle boundingBox) {
		Frustum frustum = camera.getCamera().frustum;

		for (int x = 0; x < level.getSize(); ++x) {
			for (int y = 0; y < level.getSize(); ++y) {
				int tile = level.getLevel()[x][y];
				int xo = x * 48;
				int yo = y * 48;

				BoundingBox tileBoundingBox = new BoundingBox(new Vector3(xo, yo, 0), new Vector3(xo + 48, yo + 48, 0));
				if (frustum.boundsInFrustum(tileBoundingBox)) {

					if (level.getTiles()[tile].isCollision()) {
						Rectangle rectangle = new Rectangle();
						rectangle.set(xo, yo, 48, 48);

						if (rectangle.overlaps(boundingBox)) {
							return true;
						}
					}

				}

			}
		}

		return false;
	}

}
