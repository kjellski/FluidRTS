package de.kjellski.games.fluidrts.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import de.kjellski.games.fluidrts.core.FluidRTS;

public class FluidRTSJava {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new FluidRTS());
  }
}
