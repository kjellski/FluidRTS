package de.kjellski.games.fluidrts.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import de.kjellski.games.fluidrts.core.FluidRTS;

public class FluidRTSHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform.Config config = new HtmlPlatform.Config();
    // use config to customize the HTML platform, if needed
    HtmlPlatform platform = HtmlPlatform.register(config);
    platform.assets().setPathPrefix("FluidRTS/");
    PlayN.run(new FluidRTS());
  }
}
