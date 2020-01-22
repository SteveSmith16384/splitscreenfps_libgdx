package com.scs.splitscreenfps.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.scs.splitscreenfps.BillBoardFPS_Main;
import com.scs.splitscreenfps.Settings;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                //return new GwtApplicationConfiguration(480, 320);
            return new GwtApplicationConfiguration(Settings.WINDOW_WIDTH_PIXELS, Settings.WINDOW_HEIGHT_PIXELS);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new BillBoardFPS_Main();
        }
}