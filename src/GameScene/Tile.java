package GameScene;

public class Tile {

    private Collider collider;
    private Object graphic;
    private String id;

    public void AddCollider(Collider _collider) {
        this.collider = _collider;
    }

    public void RemoveCollider() {
        this.collider = null;
    }

    public void SetCollider(Collider _collider) {
        RemoveCollider();
        AddCollider(_collider);
    }

    public void SetGraphic(Object _graphic) {
        this.graphic = _graphic;
    }

    public Object GetGraphic() {
        return this.graphic;
    }

    public Collider GetCollider() {
        return this.collider;
    }

    public void SetId(String _id) {
        this.id = _id;
    }

    public String GetId() {
        return this.id;
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
}
