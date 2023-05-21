package Tools;

public class KeyInputGenerator {
    public static void main(String[] a) {
        for(int i = 0; i < 1000000; ++i) {
            String text = java.awt.event.KeyEvent.getKeyText(i);
            if(!text.contains("Unknown keyCode: ")) {
                System.out.println("public static final int " + text.replace(" ", "_").replace("-", "_") + " = " + i + ";");
            }
        }

    }
}