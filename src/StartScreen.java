import javax.swing.*;

public class StartScreen implements Runnable {
    @Override
    public void run() {
        JFrame startFrame = new JFrame("Chess");
        startFrame.setResizable(false);
        startFrame.setSize(300, 300);



        startFrame.setVisible(true);
    }
}
