package Npc;

import GameScene.Collider;

import java.awt.*;

public class Enemy {
    private String id;
    private Collider collider;
    private Color color;
    private float speed = 0.008f;

    private float damage;
    private float health;

    public Enemy(String _id, Collider _collider, Color _color, float _damage, float _health) {
        this.id = _id;
        this.collider = _collider;
        this.color = _color;
        this.damage = _damage;
        this.health = _health;
    }

    public void SetId(String _id) {
        this.id = _id;
    }

    public String GetId() {
        return this.id;
    }

    public void SetSpeed(float _speed) {
        this.speed = _speed;
    }

    public float GetSpeed() {
        return this.speed;
    }

    public void SetCollider(Collider _collider) {
        this.collider = _collider;
    }

    public Collider GetCollider() {
        return this.collider;
    }

    public void SetX(float _x) {
        this.collider.SetX(_x);
    }

    public float GetX() {
        return this.collider.GetX();
    }

    public void SetY(float _y) {
        this.collider.SetY(_y);
    }

    public float GetY() {
        return this.collider.GetY();
    }

    public void SetWidth(int _width) {
        collider.SetWidth(_width);
    }

    public int GetWidth() {
        return collider.GetWidth();
    }

    public void SetHeight(int _height) {
        collider.SetHeight(_height);
    }

    public int GetHeight() {
        return collider.GetHeight();
    }

    public void SetColor(Color _color) {
        this.color = _color;
    }

    public Color GetColor() {
        return this.color;
    }

    public float GetDamage() {
        return damage;
    }

    public void SetDamage(float damage) {
        this.damage = damage;
    }

    public float GetHealth() {
        return health;
    }

    public void SetHealth(float health) {
        this.health = health;
    }
}
