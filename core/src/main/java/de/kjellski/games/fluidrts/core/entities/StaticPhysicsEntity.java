
package de.kjellski.games.fluidrts.core.entities;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import de.kjellski.games.fluidrts.core.FluidLevel;

public abstract class StaticPhysicsEntity extends Entity implements PhysicsEntity {
  private Body body;

  public StaticPhysicsEntity(final FluidLevel fluidWorld, World world, float x, float y, float angle) {
    super(fluidWorld, x, y, angle);
    body = initPhysicsBody(world, x, y, angle);
  }

  abstract Body initPhysicsBody(World world, float x, float y, float angle);

  @Override
  public void paint(float alpha) {
  }

  @Override
  public void update(float delta) {
  }

  @Override
  public void initPreLoad(final FluidLevel fluidWorld) {
  }

  @Override
  public void initPostLoad(final FluidLevel fluidWorld) {
    fluidWorld.staticLayerBack.add(layer);
  }

  @Override
  public void setPos(float x, float y) {
    throw new RuntimeException("Error setting position on static entity");
  }

  @Override
  public void setAngle(float a) {
    throw new RuntimeException("Error setting angle on static entity");
  }

  @Override
  public Body getBody() {
    return body;
  }
}
