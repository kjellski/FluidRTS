package de.kjellski.games.fluidrts.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import de.kjellski.games.fluidrts.core.FluidRTS;

public class FluidRTSActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new FluidRTS());
  }
}
