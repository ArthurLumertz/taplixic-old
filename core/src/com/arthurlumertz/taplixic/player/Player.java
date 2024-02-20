package com.arthurlumertz.taplixic.player;

import box2dLight.*;
import com.arthurlumertz.taplixic.blocks.*;
import com.arthurlumertz.taplixic.entity.*;
import com.arthurlumertz.taplixic.inventory.*;
import com.arthurlumertz.taplixic.io.*;
import com.arthurlumertz.taplixic.items.*;
import com.arthurlumertz.taplixic.level.*;
import com.arthurlumertz.taplixic.particle.*;
import com.arthurlumertz.taplixic.phys.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

import java.util.*;

public class Player extends Entity {

    private final List<Particle> particles = new ArrayList<Particle>();
    private Camera camera;
    private RayHandler rayHandler;
    private float elapsedFoostepTime, elapsedStaminaTime;

    private Texture selectedBlock;

    public Player(Camera camera, RayHandler rayHandler) {
        this.camera = camera;
        this.rayHandler = rayHandler;

        show();
        load();
        save();
    }

    @Override
    public void show() {
        setSpeed(3);
        setPreviousSpeed(getSpeed());

        setPosition(new Vector2((Level.getSize() * 48) / 2, (Level.getSize() * 48) / 2));
        setSize(new Vector2(48, 48));

        setIdle1(new Texture(Gdx.files.internal("player/idle/1.png")));
        setIdle2(new Texture(Gdx.files.internal("player/idle/2.png")));

        setAnim1(new Texture(Gdx.files.internal("player/anim/1.png")));
        setAnim2(new Texture(Gdx.files.internal("player/anim/2.png")));

        setLiquid1(new Texture(Gdx.files.internal("player/liquid/1.png")));
        setLiquid2(new Texture(Gdx.files.internal("player/liquid/2.png")));

        selectedBlock = new Texture(Gdx.files.internal("io/select.png"));

        setImage(idle1);

        setBoundingBox(new Rectangle(position.x + 8, position.y + 12, size.x - 16, size.y - 24));
        setAttackingBox(new Rectangle(position.x, position.y, 4, 4));

        setAttackSound(Gdx.audio.newSound(Gdx.files.internal("player/sound/attack.ogg")));

        setHealth(12);
        setMaxHealth(health);

        setStamina(12);
        setMaxStamina(stamina);

        walkingParticle.setColor(Color.WHITE);
        walkingParticle.fill();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (State.STATE == State.PLAY) {
            elapsedFoostepTime += Gdx.graphics.getDeltaTime();

            elapsedStaminaTime += Gdx.graphics.getDeltaTime();
            if (elapsedStaminaTime >= 0.6F) {
                if (stamina <= maxStamina) {
                    stamina++;
                }
                elapsedStaminaTime = 0.0F;
            }

            handleInput();

            attack(batch);

            if (up || down || left || right) {
                Vector2 newPosition = new Vector2(position.x, position.y);

                if (up)
                    newPosition.y += speed;
                if (down)
                    newPosition.y -= speed;
                if (left)
                    newPosition.x -= speed;
                if (right)
                    newPosition.x += speed;

                setBoundingBox(new Rectangle(newPosition.x + 8, newPosition.y + 12, size.x - 16, size.y - 24));
                collision = Collision.checkEntity(camera, this);

                if (!collision) {
                    setPosition(newPosition);
                }

                if (spriteNumber == 1)
                    image = anim1;
                else if (spriteNumber == 2)
                    image = anim2;

            } else {
                if (spriteNumber == 1)
                    image = idle1;
                else if (spriteNumber == 2)
                    image = idle2;
            }

            for (Particle particle : particles) {
                particle.update(Gdx.graphics.getDeltaTime());
                particle.render(batch);
            }

            // get the current tile being walked on
            int tileX = (int) (getPosition().x / 48);
            int tileY = (int) (getPosition().y / 48);
            Block currentBlock = Level.getBlocks()[Level.getLevel()[tileX][tileY]];

            if (currentBlock.isLiquid()) {
                setSpeed(1);

                if (elapsedFoostepTime > 0.25F) {
                    if (currentBlock.getWalkingSound() != null) {
                        currentBlock.getWalkingSound().play(0.15F);
                    }

                    Particle particle = new Particle(new Vector2(position.x + (size.x / 2), position.y),
                            new Texture(walkingParticle));
                    particles.add(particle);
                    elapsedFoostepTime = 0.0F;
                }

                if (spriteNumber == 1) {
                    image = liquid1;
                } else if (spriteNumber == 2) {
                    image = liquid2;
                }
            } else {
                setSpeed(getPreviousSpeed());

                if (up || down || left || right) {
                    if (currentBlock.getWalkingSound() != null) {
                        if (elapsedFoostepTime >= 0.5F) {
                            currentBlock.getWalkingSound().play(0.5F);
                            elapsedFoostepTime = 0.0F;
                        }
                    }
                }
            }
        }

        batch.draw(image, position.x, position.y, size.x, size.y);

    }

    private void attack(SpriteBatch batch) {
        Vector3 unprojectedMouse = camera.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        float colX = unprojectedMouse.x / 48;
        float colY = unprojectedMouse.y / 48;

        int tileX = (int) colX;
        int tileY = (int) colY;

        float reach = 0.5F;

        switch (direction) {
            case "up":
                tileY -= 1;
                setAttackingBox(new Rectangle(tileX * 48, tileY * 48 + 48, 48, 48));
                break;
            case "down":
                tileY += 1;
                setAttackingBox(new Rectangle(tileX * 48, tileY * 48 - (48 - reach), 48, 48));
                break;
            case "left":
                tileX += 1;
                setAttackingBox(new Rectangle(tileX * 48 - (48 - reach), tileY * 48, 48, 48));
                break;
            case "right":
                tileX -= 1;
                setAttackingBox(new Rectangle(tileX * 48 + 48, tileY * 48, 48, 48));
                break;
        }

        // get the current tile being attacked
        int attackingTileX = (int) (getAttackingBox().x / 48);
        int attackingTileY = (int) (getAttackingBox().y / 48);
        Block attackingBlock = Level.getBlocks()[Level.getLevel()[attackingTileX][attackingTileY]];

        batch.setColor(new Color(1F, 1F, 1F, 0.25F));
        batch.draw(selectedBlock, attackingTileX * 48, attackingTileY * 48, 48, 48);
        batch.setColor(Color.WHITE);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (!attackingBlock.isSolid() && selectedItem != null) {
                if (selectedItem.getType() == Item.PLACEABLE) {
                    Inventory.remove(selectedItem);

                    Item item = selectedItem;
                    if (item != null) {
                        item.setPosition(new Vector2(attackingTileX * 48, attackingTileY * 48));
                        if (item.getBreakSound() != null) {
                            item.getBreakSound().play();
                        }
                    }

                    Level.addPlacedItem(item);

                    selectedItem = null;
                }
            }
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            attacking = true;
            if (attackingBlock.isDestructible() && !attackingBlock.isInvincible() && stamina > 0) {
                // play the attack sound
                attackingBlock.getBreakSound().play();
                stamina -= 1;

                int healthHit = 1;

                if (selectedItem != null) {

                    if (attackingBlock.getType() == selectedItem.getType()) {
                        healthHit = selectedItem.getDamage();
                    }

                }

                if (attackingBlock.damage(MathUtils.random(healthHit, healthHit + 1))) {
                    Level.getLevel()[attackingTileX][attackingTileY] = 1;
                }

                Particle particle = new Particle(
                        new Vector2((attackingTileX * 48) + (48 / 2), (attackingTileY * 48) + (48 / 2)),
                        attackingBlock.getImage());
                particles.add(particle);
                particles.add(particle);

                attackingBlock.setInvincible(true);

            }

            attackingCounter++;
            if (attackingCounter > 20) {
                attackingBlock.setInvincible(false);
                if (spriteNumber == 1)
                    spriteNumber = 2;
                else if (spriteNumber == 2)
                    spriteNumber = 1;
                attacking = false;
                attackingCounter = 0;
            }
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            up = true;
            direction = "up";
        } else {
            up = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            down = true;
            direction = "down";
        } else {
            down = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            left = true;
            direction = "left";
        } else {
            left = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            right = true;
            direction = "right";
        } else {
            right = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            setSpeed(2);
        } else {
            setSpeed(getPreviousSpeed());
        }

        spriteCounter++;
        if (spriteCounter > (speed >= 3 ? 20 : 30)) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }

    @Override
    public void dispose() {
        Inventory.clear();

        image.dispose();
        idle1.dispose();
        idle2.dispose();
        anim1.dispose();
        anim2.dispose();

        walkingParticle.dispose();
        particles.removeIf(Particle::isFinished);
    }

    public void save() {
        try {

            System.out.println("Saving player...");

            FileHandle file;

            StringBuilder playerData = new StringBuilder();
            playerData.append(health).append("\n");
            playerData.append(stamina).append("\n");
            playerData.append(speed).append("\n");
            playerData.append(position.x / 48).append(",").append(position.y / 48).append("\n");

            file = Gdx.files.local(Level.getWorld() + "/player.json");
            file.writeString(playerData.toString(), false);

            StringBuilder inventoryData = new StringBuilder();
            for (Item item : Inventory.getInventory()) {
                String name = item.getName();
                inventoryData.append(name).append("\n");
            }

            file = Gdx.files.local(Level.getWorld() + "/inventory.json");
            file.writeString(inventoryData.toString(), false);

        } catch (Exception e) {
            System.err.println("Something went wrong when Saving the player!");
            e.printStackTrace();
        }
    }

    public void load() {
        try {

            System.out.println("Loading player...");

            FileHandle file;

            file = Gdx.files.local(Level.getWorld() + "/player.json");
            if (file.exists()) {
                String[] lines = file.readString().split("\n");

                health = Integer.parseInt(lines[0]);
                stamina = Integer.parseInt(lines[1]);
                speed = Integer.parseInt(lines[2]);

                String[] positionValues = lines[3].split(",");
                position.x = Float.parseFloat(positionValues[0].trim()) * 48;
                position.y = Float.parseFloat(positionValues[1].trim()) * 48;
            }

            file = Gdx.files.local(Level.getWorld() + "/inventory.json");
            if (file.exists()) {
                String[] lines = file.readString().split("\n");

                for (String line : lines) {
                    Item item = Inventory.getItemByName(line);
                    Inventory.add(item);
                }
            }

        } catch (Exception e) {
            System.err.println("Something went wrong when loading the player!");
            e.printStackTrace();
        }
    }

}
