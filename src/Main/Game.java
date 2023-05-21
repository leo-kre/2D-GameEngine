package Main;

import GUI.Frame;
import GUI.Images;
import GUI.Renderer;
import GameScene.*;
import Input.KeyInput;
import Player.*;
import Npc.*;
import Tools.ImageGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ICC_ProfileGray;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {

    private static boolean isInstantiated = false;

    private static final Frame frame = new Frame();
    private static final Renderer gamePanel = new Renderer();
    private static final KeyInput keyInput = new KeyInput();

    private static String frameTitle = "Game";
    private static Dimension gameSize = new Dimension(240, 240);
    private static boolean isFullscreen = true;
    private static boolean lastFullScreenState = false;

    private static float gameScale = 50f;
    private static Dimension totalGameSize = new Dimension((int) (gameSize.width * gameScale), (int) (gameSize.height * gameScale));

    private static ArrayList<Player> playerList = new ArrayList<Player>();

    private static Tile[][] tileList = new Tile[gameSize.width][gameSize.height];

    private static ArrayList<Npc> npcList = new ArrayList<Npc>();

    private static ArrayList<Enemy> enemyList = new ArrayList<Enemy>();

    private static boolean keepPlayerInBounds = true;
    private static boolean isPlayerColliderActive = true;
    private static boolean centerPlayers = false;

    private static boolean isLoading = true;

    private static float playerSpeed = 0.01f * 100;

    //User Interface Code

    public static final Collider defaultPlayerCollider = new Collider(0 , 0, 50, 75, false);
    public static final Collider defaultTileCollider = new Collider(0, 0, 1, 1,false);

    public static void Init() {
        if(isInstantiated) return;
        isInstantiated = true;
        frame.Create(gamePanel, keyInput);

        StartGameTimer();
    }

    public static void GenerateWorld() {
        //if(!isInstantiated) return;

        //0.08
        double scale = 0.038;

        int offsetX = (int) Math.floor(Math.random() * 9999);
        int offsetY = (int) Math.floor(Math.random() * 9999);

        PerlinNoise perlinNoise = new PerlinNoise(1);

        for(int x = 0; x < tileList.length; x++) {
            for(int y = 0; y < tileList[x].length; y++) {

                double noiseValue = perlinNoise.perlinNoise((x + offsetX) * scale, (y + offsetY) * scale);
                double value = (noiseValue * 100);

                int bounds = 25;

                if(value > bounds) {
                    value = bounds;
                } else if(value < -bounds) {
                    value = -bounds;
                }

                Image img = null;

                if(value >= -25) {
                    img = Images.BlackWool;
                }

                if(value >= -20) {
                    img = Images.BlueWool;
                }

                if(value >= -10) {
                    img = Images.LightBlueWool;
                }

                if(value >= -5) {
                    img = Images.Sand;
                }

                if(value >= 0) {
                    img = Images.Dirt;
                }

                if(value >= 5) {
                    img = Images.GreenWool;
                }

                if(value >= 10) {
                    img = Images.Cobblestone;
                }

                if(value >= 15) {
                    img = Images.Snow;
                }

                if(value >= 18) {
                    img = Images.Ice;
                }

                if(value >= 25) {
                    img = Images.PackedIce;
                }

                CreateTile(img, false, new Collider(x, y, 1, 1, false));

                noiseValue = perlinNoise.perlinNoise((x + offsetX) * scale, (y + offsetY) * scale);
                value = (noiseValue * 100);

                if(value > bounds) {
                    value = bounds;
                } else if(value < -bounds) {
                    value = -bounds;
                }

                if(value > 0 && tileList[x][y].GetGraphic() == Images.Sand) {
                    img = Images.RedTulip;
                    System.out.println("Tulip");
                    CreateTile(img, false, new Collider(x, y, 1, 1, false));
                }

            }
        }

    }

    public static void SetTitle(String _title) {
        frameTitle = _title;
    }

    public static void SetSize(Dimension _dimension) {
        gameSize = _dimension;
        tileList = new Tile[gameSize.width][gameSize.height];
    }

    public static Dimension GetSize() {
        return gameSize;
    }

    public static void SetScale(float _scale) {
        gameScale = _scale;
        Images.ScaleImages();
    }

    public static float GetScale() {
        return gameScale;
    }

    public static void CreatePlayer(String _name, Color _color, Collider _collider, Controller _controller) {
        Player player = new Player();
        player.Create(_name, _color, _collider, _controller);
        playerList.add(player);
    }

    public static void CreateTile(Object _graphic,boolean _rotateRandom, Collider _collider) {

        for(int w = 0; w < _collider.GetWidth(); w++) {
            for (int h = 0; h < _collider.GetHeight(); h++) {
                Tile t = new Tile();
                t.AddCollider(new Collider((int) (_collider.GetX() + w), (int) (_collider.GetY() + h), 1, 1, _collider.isCollidable()));
                Image image = (Image) _graphic;
                if(_rotateRandom && _graphic instanceof Image img) {
                    switch ((int) Math.floor(Math.random() * 4)) {
                        case 1 -> img = Renderer.RotateImage(img, 0);
                        case 2 -> img = Renderer.RotateImage(img, 90);
                        case 3 -> img = Renderer.RotateImage(img, 180);
                        case 4 -> img = Renderer.RotateImage(img, 270);
                    }
                    image = img;
                }
                t.SetGraphic(image);
                tileList[(int) (_collider.GetX() + w)][(int) (_collider.GetY() + h)] = t;
            }
        }
    }

    public static void DestroyTile(String _id) {
        for(int x = 0; x < tileList.length; x++) {
            for(int y = 0; y < tileList[x].length; y++) {
                if(Objects.equals(tileList[x][y].GetId(), _id)) {
                    tileList[x][y] = null;
                }
            }
        }
    }

    public static Player GetPlayerById(String _id) {
        Player p = null;
        for (Player player : playerList) {
            if (Objects.equals(player.GetName(), _id)) {
                p = player;
            }
        }
        return p;
    }

    public static void SetPlayerSpeed(float _speed) {
        playerSpeed = _speed / 100;
    }

    public float GetPlayerSpeed() {
        return playerSpeed * 100;
    }

    public static void SetPlayerList(ArrayList<Player> _playerList) {
        playerList = _playerList;
    }

    public static ArrayList<Player> GetPlayerList() {
        return playerList;
    }

    public static void SetTileList(Tile[][] _tileList) {
        tileList = _tileList;
    }

    public static Tile[][] GetTileList() {
        return tileList;
    }

    public static void CreateNpc(String _id, Collider _collider, Color _color) {
        Npc npc = new Npc(_id, _collider, _color);
        npcList.add(npc);
    }

    public static void DestroyNpc(Object _npc) {
        if(_npc.getClass() == String.class) {
            npcList.removeIf(npc -> npc.GetId() == _npc);
        } else if(_npc.getClass() == Npc.class) {
            for (Npc npc : npcList) {
                if (npc == _npc) {
                    npcList.remove(_npc);
                }
            }
        }
    }

    public static Npc GetNpcById(String _id) {
        Npc foundNpc = null;
        for (Npc npc : npcList) {
            if (Objects.equals(npc.GetId(), _id)) {
                foundNpc = npc;
            }
        }
        return foundNpc;
    }

    public static void CreateEnemy(String _id, Collider _collider, float _damage, float _health, Color _color) {
        Enemy enemy = new Enemy(_id, _collider, _color, _damage, _health);
        enemyList.add(enemy);
    }

    public static void DestroyEnemy(Enemy _enemy) {
        enemyList.removeIf(enemy -> enemy == _enemy);
    }

    public static Enemy GetEnemyById(String _id) {
        Enemy foundEnemy = null;
        for (Enemy enemy : enemyList) {
            if (Objects.equals(enemy.GetId(), _id)) {
                foundEnemy = enemy;
            }
        }
        return foundEnemy;
    }

    public static void SetNpcList(ArrayList<Npc> _npcList) {
        npcList = _npcList;
    }

    public static ArrayList<Npc> GetNpcList() {
        return npcList;
    }

    public static void SetEnemyList(ArrayList<Enemy> _enemyList) {
        enemyList = _enemyList;
    }

    public static ArrayList<Enemy> GetEnemyList() {
        return enemyList;
    }

    public static void KeepPlayersInBounds(boolean _state) {
        keepPlayerInBounds = _state;
    }

    public boolean GetKeepPlayersInBounds() {
        return keepPlayerInBounds;
    }

    public static void SetPlayerColliderState(boolean _state) {
        isPlayerColliderActive = _state;
    }

    public boolean GetPlayerColliderState() {
        return isPlayerColliderActive;
    }

    public static void CenterPlayers(boolean _state) {
        if(playerList.size() >= 3) return;
        centerPlayers = _state;
    }

    public static boolean KeepPlayersCentered() {
        return centerPlayers;
    }

    public static void SetFullscreenMode(boolean _state) {
        isFullscreen = _state;
    }

    public static boolean IsFullscreen() {
        return isFullscreen;
    }

    public static JPanel GetGraphicsPanel() {
        return gamePanel;
    }

    public static boolean IsLoading() {
        return isLoading;
    }

    public static void SetLoadingState(boolean _state) {
        isLoading = _state;
    }
    //Background Code

    private static void MovePlayers() {
        for (Player player : playerList) {

            if (player.IsLeft()) {
                player.SetX(player.GetX() - playerSpeed);
                if(isPlayerColliderActive) {
                    IsPlayerMovingInObject(player, "left");
                }
            }

            if (player.IsRight()) {
                player.SetX(player.GetX() + playerSpeed);

                if(isPlayerColliderActive) {
                    IsPlayerMovingInObject(player, "right");
                }
            }

            if (player.IsUp()) {
                player.SetY(player.GetY() - playerSpeed);

                if(isPlayerColliderActive) {
                    IsPlayerMovingInObject(player, "up");
                }
            }

            if (player.IsDown()) {
                player.SetY(player.GetY() + playerSpeed);

                if(isPlayerColliderActive) {
                    IsPlayerMovingInObject(player, "down");
                }
            }

            if (keepPlayerInBounds) {
                if (player.GetX() < 0) player.SetX(0);
                if (player.GetY() < 0) player.SetY(0);
                if (player.GetX() + player.GetWidth() > totalGameSize.width) player.SetX(totalGameSize.width - player.GetWidth());
                if (player.GetY() + player.GetHeight() > totalGameSize.height) player.SetY(totalGameSize.height - player.GetHeight());
            }
        }
    }

    private static void SetTile(Tile _tile, int _x, int _y) {
        tileList[_x][_y] = _tile;
    }

    private static Tile GetTile(int _x, int _y) {
        return tileList[_x][_y];
    }

    private static void IsPlayerMovingInObject(Player _player, String _direction) {
        for(int x = 0; x < tileList.length; x++) {
            for(int y = 0; y < tileList[x].length; y++) {
                Tile tile = tileList[x][y];

                if(tile != null && tile.GetCollider().isCollidable()) {
                    Rectangle pRect = new Rectangle((int) _player.GetX(), (int) _player.GetY(), _player.GetWidth(), _player.GetHeight());
                    Rectangle tRect = new Rectangle((int) (tile.GetX() * gameScale), (int) (tile.GetY() * gameScale), (int) (tile.GetWidth() * gameScale), (int) (tile.GetHeight() * gameScale));

                    if (pRect.intersects(tRect)) {
                        MovePlayerOutOfObject(tile.GetCollider(), _player, _direction);
                    }
                }
            }
        }
    }

    private static void MovePlayerOutOfObject(Collider _collider, Player _player, String _direction) {
        switch (_direction) {
            case "left" -> _player.SetX((_collider.GetX() + _collider.GetWidth()) * gameScale);
            case "right" -> _player.SetX((_collider.GetX() * gameScale - _player.GetWidth()));
            case "up" -> _player.SetY((_collider.GetY() + _collider.GetHeight()) * gameScale);
            case "down" -> _player.SetY((_collider.GetY() * gameScale - _player.GetHeight()));
        }
    }

    private static void GameLoop() {
        if(!Objects.equals(frame.getTitle(), frameTitle)) frame.setTitle(frameTitle);
        ChangeFullScreenMode(isFullscreen);
        MovePlayers();

        gamePanel.setSize(new Dimension(frame.getWidth(), frame.getHeight()));
        totalGameSize = new Dimension((int) (gameSize.width * gameScale), (int) (gameSize.height * gameScale));
        //gamePanel.repaint();
        gamePanel.paintImmediately(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
    }

    private static void StartGameTimer() {

        Runnable helloRunnable = new Runnable() {
            public void run() {
                GameLoop();
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 1000/60, TimeUnit.MICROSECONDS);
    }

    private static void ChangeFullScreenMode(boolean _state) {
        if(_state && !lastFullScreenState) {
            frame.dispose();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
            frame.setBackground(Color.black);
            frame.setVisible(true);
            lastFullScreenState = true;
        } else if(!_state && lastFullScreenState) {
            frame.dispose();
            frame.setUndecorated(false);
            frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
            frame.setExtendedState(JFrame.NORMAL);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            lastFullScreenState = false;
        }
    }

}
