import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel{

    private boolean isActivated;


    Tile(boolean isActivated){
        super();
        this.isActivated=isActivated;

        //JFrame frame= new JFrame();
      //  frame.setLayout(new BorderLayout());




        if(isActivated){
            this.setBackground(Color.green);

        }
        else {
            this.setBackground(Color.DARK_GRAY);
        }
       // frame.add(colorPanel,BorderLayout.CENTER);









    }







}

