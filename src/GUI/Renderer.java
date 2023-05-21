package GUI;

import GameScene.Collider;
import GameScene.Tile;
import Main.Game;
import Npc.Npc;
import Player.Player;
import Npc.Enemy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Renderer extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        if(Game.GetPlayerList().size() == 1) {
            RenderNormal(g2d);
        } else {
            RenderSplitScreen(g2d);
        }
    }

    public void RenderNormal(Graphics2D g2d) {

        if(Game.GetPlayerList().size() > 1 || !Game.KeepPlayersCentered()) {
            for(int x = 0; x < Game.GetTileList().length; x++) {
                for(int y = 0; y < Game.GetTileList()[x].length; y++) {
                    drawTile(g2d, Game.GetTileList()[x][y], 0, 0);
                }
            }

            for (Npc npc : Game.GetNpcList()) {
                g2d.setColor(npc.GetColor());
                g2d.fillRect((int) npc.GetX(), (int) npc.GetY(), npc.GetWidth(), npc.GetHeight());
            }

            for(Enemy enemy : Game.GetEnemyList()) {
                g2d.setColor(enemy.GetColor());
                g2d.fillRect((int) enemy.GetX(), (int) enemy.GetY(), enemy.GetWidth(), enemy.GetHeight());
            }

            for (Player player : Game.GetPlayerList()) {
                g2d.setColor(player.GetColor());
                g2d.fillRect((int) player.GetX(), (int) player.GetY(), player.GetWidth(), player.GetHeight());
            }
        } else if (Game.GetPlayerList().size() == 1 && Game.KeepPlayersCentered()) {

            int offsetX = (int) (this.getWidth() / 2 - Game.GetPlayerList().get(0).GetX());
            int offsetY = (int) (this.getHeight() / 2 - Game.GetPlayerList().get(0).GetY());

            for(int x = 0; x < Game.GetTileList().length; x++) {
                for(int y = 0; y < Game.GetTileList()[x].length; y++) {
                    drawTile(g2d, Game.GetTileList()[x][y], offsetX, offsetY);
                }
            }

            Player player = Game.GetPlayerList().get(0);
            g2d.setColor(player.GetColor());
            g2d.fillRect((int) (player.GetX() + offsetX), (int) (player.GetY() + offsetY), player.GetWidth(), player.GetHeight());
        }
    }

    public void RenderSplitScreen(Graphics2D g2d) {

        for(Player player : Game.GetPlayerList()) {
            if(Game.GetPlayerList().indexOf(player) == 0) {
                g2d.clipRect(0, 0, this.getWidth() / 2, this.getHeight());

                int offsetX = (int) (this.getWidth() / 2 - player.GetX() - player.GetWidth() - this.getWidth() / 4);
                int offsetY = (int) (this.getHeight() / 2 - player.GetY() - player.GetHeight());

                g2d.setColor(Color.white);
                g2d.fillRect(offsetX, offsetY, (int) (Game.GetSize().width * Game.GetScale()), (int) (Game.GetSize().height * Game.GetScale()));

                for(int x = 0; x < Game.GetTileList().length; x++) {
                    for(int y = 0; y < Game.GetTileList()[x].length; y++) {
                        drawTile(g2d, Game.GetTileList()[x][y], offsetX, offsetY);
                    }
                }

                Player player2 = Game.GetPlayerList().get(1);
                g2d.setColor(player2.GetColor());
                g2d.fillRect((int) (player2.GetX() + offsetX), (int) (player2.GetY() + offsetY), (int) (player2.GetWidth()), (int) (player2.GetHeight()));

                g2d.setColor(player.GetColor());
                g2d.fillRect((int) (player.GetX() + offsetX), (int) (player.GetY() + offsetY), player.GetWidth(), player.GetHeight());

            } else if(Game.GetPlayerList().indexOf(player) == 1) {

                g2d.setClip(0, 0, this.getWidth(), this.getHeight());
                g2d.clipRect(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());

                int offsetX = (int) (this.getWidth() / 2 - player.GetX() - player.GetWidth() + this.getWidth() / 4);
                int offsetY = (int) (this.getHeight() / 2 - player.GetY() - player.GetHeight());

                g2d.setColor(Color.white);
                g2d.fillRect(offsetX, offsetY, (int) (Game.GetSize().width * Game.GetScale()), (int) (Game.GetSize().height * Game.GetScale()));

                for(int x = 0; x < Game.GetTileList().length; x++) {
                    for(int y = 0; y < Game.GetTileList()[x].length; y++) {
                        drawTile(g2d, Game.GetTileList()[x][y], offsetX, offsetY);
                    }
                }

                Player player2 = Game.GetPlayerList().get(0);
                g2d.setColor(player2.GetColor());
                g2d.fillRect((int) (player2.GetX() + offsetX), (int) (player2.GetY() + offsetY), (int) (player2.GetWidth()), (int) (player2.GetHeight()));

                g2d.setColor(player.GetColor());
                g2d.fillRect((int) (player.GetX() + offsetX), (int) (player.GetY() + offsetY), player.GetWidth(), player.GetHeight());
            }
        }

        g2d.setClip(0, 0, this.getWidth(), this.getHeight());
        g2d.setColor(Color.red);
        g2d.fillRect(this.getWidth() / 2 - 10, 0, 10, this.getHeight());
    }

    public static Image CreateImage(String _path) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(_path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image buffer = Game.GetGraphicsPanel().createImage((int) Game.GetScale(), (int) Game.GetScale());
        if (buffer != null) {
            Graphics bufferGraphics = buffer.getGraphics();
            bufferGraphics.drawImage(img, 0, 0, (int) Game.GetScale(), (int) Game.GetScale(), null);
            bufferGraphics.dispose();
        }

        return buffer;
    }

    private static void drawTile(Graphics2D g2d, Tile _tile, int offsetX, int offsetY) {
        if(_tile == null) return;
        if(_tile.GetGraphic().getClass() == Color.class) {
            g2d.setColor((Color) _tile.GetGraphic());
            g2d.fillRect((int) (_tile.GetX() * Game.GetScale() + offsetX), (int) (_tile.GetY() * Game.GetScale() + offsetY), (int) (_tile.GetWidth() * Game.GetScale()), (int) (_tile.GetHeight() * Game.GetScale()));
        } else {
            g2d.drawImage((Image) _tile.GetGraphic(), (int) (_tile.GetX() * Game.GetScale() + offsetX), (int) (_tile.GetY() * Game.GetScale() + offsetY), null);
        }
    }

    public static Image ScaleImage(Image _img, Dimension _scale) {
        return _img.getScaledInstance((int) (_scale.width * Game.GetScale()), (int) (_scale.height * Game.GetScale()), Image.SCALE_FAST);
    }

    public static Image RotateImage(Image image, double angle) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), width / 2, height / 2);
        g2d.setTransform(transform);

        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }
}
