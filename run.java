import java.awt.EventQueue; 


public class run {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override 
            public void run()  {
                UserFrame UFrame = new UserFrame(); 
                UFrame.show(); 
            }
        });
    }
}