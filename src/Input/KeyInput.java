package Input;

import Main.*;
import Player.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyMap.F11) {
            Game.SetFullscreenMode(!Game.IsFullscreen());
        }

        if(e.getKeyCode() == KeyMap.R) {
            Game.GenerateWorld();
        }

        for(Player player : Game.GetPlayerList()) {
            if(e.getKeyCode() == player.GetController().left) {
                player.SetLeft(true);
            }

            if(e.getKeyCode() == player.GetController().right) {
                player.SetRight(true);
            }

            if(e.getKeyCode() == player.GetController().up) {
                player.SetUp(true);
            }

            if(e.getKeyCode() == player.GetController().down) {
                player.SetDown(true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for(Player player : Game.GetPlayerList()) {
            if(e.getKeyCode() == player.GetController().left) {
                player.SetLeft(false);
            }

            if(e.getKeyCode() == player.GetController().right) {
                player.SetRight(false);
            }

            if(e.getKeyCode() == player.GetController().up) {
                player.SetUp(false);
            }

            if(e.getKeyCode() == player.GetController().down) {
                player.SetDown(false);
            }
        }
    }
}
