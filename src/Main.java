import GUI.Images;
import GameScene.Collider;
import Main.*;
import Npc.*;
import Player.*;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Game.Init();
        Game.SetScale(50);
        Game.SetSize(new Dimension(100, 100));
        Game.GenerateWorld();

        Game.CenterPlayers(true);
        Game.CreatePlayer("Gustav", Color.blue, Game.defaultPlayerCollider, new Controller(KeyMap.A, KeyMap.D, KeyMap.W, KeyMap.S));
        Game.CreatePlayer("Gustav", Color.blue, Game.defaultPlayerCollider, new Controller(KeyMap.Left, KeyMap.Right, KeyMap.Up, KeyMap.Down));

        Game.CreateTile(Images.WhiteWool, true, new Collider(0, 0, 1,  1, true));
    }
}
