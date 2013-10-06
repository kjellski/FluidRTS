package de.kjellski.games.fluidrts.core;

import de.kjellski.games.fluidrts.core.entities.Entity;
import de.kjellski.games.fluidrts.core.entities.PhysicsEntity;
import de.kjellski.games.fluidrts.core.tileutil.TiledMap;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import static playn.core.PlayN.graphics;

public class FluidLevel implements ContactListener {
    public final TiledMap map;

    public GroupLayer staticLayerBack;
    public GroupLayer dynamicLayer;
    public GroupLayer staticLayerFront;

    // box2d object containing physics world
    protected World world;

    private List<Entity> entities = new ArrayList<Entity>(0);
    private HashMap<Body, PhysicsEntity> bodyEntityLUT = new HashMap<Body, PhysicsEntity>();
    private Stack<Contact> contacts = new Stack<Contact>();

    private static boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;

    public FluidLevel(GroupLayer scaledLayer, Json.Object level) {
        map = new TiledMap(level);

        staticLayerBack = graphics().createGroupLayer();
        scaledLayer.add(staticLayerBack);
        dynamicLayer = graphics().createGroupLayer();
        scaledLayer.add(dynamicLayer);
        staticLayerFront = graphics().createGroupLayer();
        scaledLayer.add(staticLayerFront);

        // create the physics world
        Vec2 gravity = new Vec2(FluidRTS.GRAVITY_X , FluidRTS.GRAVITY_Y);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);
        world.setContactListener(this);

        // create the ground
        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0, map.height), new Vec2(map.width, map.height));
        ground.createFixture(groundShape, 0.0f);

        // create the walls
        Body wallLeft = world.createBody(new BodyDef());
        EdgeShape wallLeftShape = new EdgeShape();
        wallLeftShape.set(new Vec2(0, 0), new Vec2(0, map.height));
        wallLeft.createFixture(wallLeftShape, 0.0f);
        Body wallRight = world.createBody(new BodyDef());
        EdgeShape wallRightShape = new EdgeShape();
        wallRightShape.set(new Vec2(map.width, 0), new Vec2(map.width, map.height));
        wallRight.createFixture(wallRightShape, 0.0f);

        if (showDebugDraw) {
            CanvasImage image = graphics().createImage((int) (map.width / FluidRTS.physUnitPerScreenUnit),
                    (int) (map.height / FluidRTS.physUnitPerScreenUnit));
            graphics().rootLayer().add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit | DebugDraw.e_jointBit | DebugDraw.e_aabbBit);
            debugDraw.setCamera(0, 0, 1f );// / FluidRTS.physUnitPerScreenUnit);
            world.setDebugDraw(debugDraw);
        }
    }

    public void update(float delta) {
        for (Entity e : entities) {
            e.update(delta);
        }
        // the step delta is fixed so box2d isn't affected by framerate
        world.step(0.033f, 10, 10);
        processContacts();
    }

    public void paint(float delta) {
        if (showDebugDraw) {
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
        for (Entity e : entities) {
            e.paint(delta);
        }
    }

    public void add(Entity entity) {
        entities.add(entity);
        if (entity instanceof PhysicsEntity) {
            PhysicsEntity physicsEntity = (PhysicsEntity) entity;
            bodyEntityLUT.put(physicsEntity.getBody(), physicsEntity);
        }
    }

    // handle contacts out of physics loop
    public void processContacts() {
        while (!contacts.isEmpty()) {
            Contact contact = contacts.pop();

            // handle collision
            PhysicsEntity entityA = bodyEntityLUT.get(contact.m_fixtureA.m_body);
            PhysicsEntity entityB = bodyEntityLUT.get(contact.m_fixtureB.m_body);

            if (entityA != null && entityB != null) {
                if (entityA instanceof PhysicsEntity.HasContactListener) {
                    ((PhysicsEntity.HasContactListener) entityA).contact(entityB);
                }
                if (entityB instanceof PhysicsEntity.HasContactListener) {
                    ((PhysicsEntity.HasContactListener) entityB).contact(entityA);
                }
            }
        }
    }

    // Box2d's begin contact
    @Override
    public void beginContact(Contact contact) {
        contacts.push(contact);
    }

    // Box2d's end contact
    @Override
    public void endContact(Contact contact) {
    }

    // Box2d's pre solve
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    // Box2d's post solve
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    /**
     * load the json abstraction of the map onto layers in order to
     * @param assetWatcher
     */
    public void loadMap( AssetWatcher assetWatcher) {
        staticLayerBack.add(map.getBaseGroupLayer());
    }
}

