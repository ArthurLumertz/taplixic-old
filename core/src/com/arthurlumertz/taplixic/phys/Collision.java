package com.arthurlumertz.taplixic.phys;

import com.arthurlumertz.taplixic.entity.*;
import com.arthurlumertz.taplixic.level.*;
import com.arthurlumertz.taplixic.player.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.*;

public class Collision {

	public static boolean checkEntity(Camera camera, Entity entity) {
		Frustum frustum = camera.getCamera().frustum;

		for (int x = 0; x < Level.getSize(); ++x) {
			for (int y = 0; y < Level.getSize(); ++y) {
				int tile = Level.getLevel()[x][y];
				int xo = x * 48;
				int yo = y * 48;

				BoundingBox tileBoundingBox = new BoundingBox(new Vector3(xo, yo, 0), new Vector3(xo + 48, yo + 48, 0));
				if (frustum.boundsInFrustum(tileBoundingBox)) {

					if (Level.getBlocks()[tile].isSolid()) {
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
	
	public static boolean checkObject(Camera camera, Rectangle boundingBox) {
		Frustum frustum = camera.getCamera().frustum;

		for (int x = 0; x < Level.getSize(); ++x) {
			for (int y = 0; y < Level.getSize(); ++y) {
				int tile = Level.getLevel()[x][y];
				int xo = x * 48;
				int yo = y * 48;

				BoundingBox tileBoundingBox = new BoundingBox(new Vector3(xo, yo, 0), new Vector3(xo + 48, yo + 48, 0));
				if (frustum.boundsInFrustum(tileBoundingBox)) {

					if (Level.getBlocks()[tile].isSolid()) {
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
