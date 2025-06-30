import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Falling Sand");
        FallingSand fallingSand = new FallingSand();

        frame.add(fallingSand);
        frame.setSize(500,438);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

        // Animation loop
    while (true) {
        fallingSand.update();
        fallingSand.repaint();
        try {
            Thread.sleep(15); // Control the speed of the animation
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    }
}
