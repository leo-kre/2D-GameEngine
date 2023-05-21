package Player;

import GameScene.Collider;
import Npc.Enemy;

import java.awt.*;

public class Player {

    private float x, y;
    private int width = 50;
    private int height = 75;

    private Collider collider;

    private Controller controller;

    private String name;
    private Color color;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private int damage = 10;
    private int health = 50;

    public void Create(String _name, Color _color,Collider _collider, Controller _controller) {
        this.name = _name;
        this.color = _color;
        this.controller = _controller;
        this.collider = _collider;
    }

    public void SetCollider(Collider _collider) {
        this.collider = _collider;
    }

    public Collider GetCollider() {
        return this.collider;
    }

    public float GetX() {
        return this.x;
    }

    public float GetY() {
        return this.y;
    }

    public int GetWidth() {
        return this.width;
    }

    public int GetHeight() {
        return this.height;
    }

    public String GetName() {
        return this.name;
    }

    public Color GetColor() {
        return this.color;
    }

    public Controller GetController() {
        return this.controller;
    }

    public int GetDamage() {
        return this.damage;
    }

    public int GetHealth() {
        return this.health;
    }

    public boolean IsLeft() {
        return left;
    }

    public void SetLeft(boolean _left) {
        this.left = _left;
    }

    public boolean IsRight() {
        return right;
    }

    public void SetRight(boolean _right) {
        this.right = _right;
    }

    public boolean IsUp() {
        return up;
    }

    public void SetUp(boolean _up) {
        this.up = _up;
    }

    public boolean IsDown() {
        return down;
    }

    public void SetDown(boolean _down) {
        this.down = _down;
    }

    public void SetX(float _x) {
        this.x = _x;
    }

    public void SetY(float _y) {
        this.y = _y;
    }

    public boolean Collides(Object _obj) {
        boolean state = false;
        if(_obj.getClass() == Collider.class) {
            if(new Rectangle((int) ((Collider) _obj).GetX(), (int) ((Collider) _obj).GetY(), ((Collider) _obj).GetWidth(), ((Collider) _obj).GetHeight()).intersects(new Rectangle((int) GetX(), (int) GetY(), GetWidth(), GetHeight()))) {
                state = true;
            }
        } else if(_obj.getClass() == Player.class || _obj.getClass() == Enemy.class) {
            if(new Rectangle((int) ((Collider) _obj).GetX(), (int) ((Collider) _obj).GetY(), ((Collider) _obj).GetWidth(), ((Collider) _obj).GetHeight()).intersects(new Rectangle((int) GetX(), (int) GetY(), GetWidth(), GetHeight()))) {
                state = true;
            }
        }
        return state;
    }
}
