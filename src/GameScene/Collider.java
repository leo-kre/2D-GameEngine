package GameScene;

public class Collider {

    private float x;
    private float y;
    private int width;
    private int height;
    private boolean collidable = false;

    public Collider(int _x, int _y, int _width, int _height, boolean _collidable) {
        this.x = _x;
        this.y = _y;
        this.width = _width;
        this.height = _height;
        this.collidable = _collidable;
    }

    public void SetX(float _x) {
        this.x = _x;
    }

    public float GetX() {
        return this.x;
    }

    public void SetY(float _y) {
        this.y = _y;
    }

    public float GetY() {
        return this.y;
    }

    public void SetWidth(int _width) {
        this.width = _width;
    }

    public int GetWidth() {
        return this.width;
    }

    public void SetHeight(int _height) {
        this.height = _height;
    }

    public int GetHeight() {
        return this.height;
    }

    public void SetCollidable(boolean _state) {
        collidable = _state;
    }

    public boolean isCollidable() {
        return collidable;
    }
}
