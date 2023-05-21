package Npc;

import GameScene.Collider;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Npc {

    private String id;
    private Collider collider;
    private Color color;
    private ArrayList<Quest> questsList = new ArrayList<Quest>();
    private int questIndex = 0;
    private float speed = 0.008f;

    public Npc(String _id, Collider _collider, Color _color) {
        this.id = _id;
        this.collider = _collider;
        this.color = _color;
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

    public void SetQuestList(ArrayList<Quest> _questList) {
        this.questsList = _questList;
    }

    public ArrayList<Quest> GetQuestList() {
        return this.questsList;
    }

    public void SetQuestIndex(int _index) {
        this.questIndex = _index;
    }

    public int GetQuestIndex() {
        return questIndex;
    }

    public void AddQuest(Quest _quest) {
        this.questsList.add(_quest);
    }

    public void RemoveQuest(Object _quest) {
        if(_quest.getClass() == Quest.class) {
            for(Quest quest : questsList) {
                if(quest == _quest) {
                    questsList.remove(_quest);
                }
            }
        }
    }

}
