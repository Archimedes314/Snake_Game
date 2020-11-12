import javafx.scene.input.KeyCode;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Random;


public class GameView extends JFrame implements KeyListener {

    private int rows,columns;
    private Color activeColor=Color.green;
    private Color passiveColor=Color.darkGray;
    public Snake<Pair<Integer,Integer>> snake=new Snake(235);
    public Tile[][] GridAccessor;
    private Direction currentDirection= Direction.LEFT;


    GameView(final int rows, final int columns, int snakeNum) throws IllegalDirectionException, InterruptedException {
        this.rows=rows;



        this.columns=columns;


        this.addKeyListener(this);
       Random rng=new Random(5000);
       snake.add(new Pair<Integer, Integer>(rng.nextInt(rows),rng.nextInt(columns)),1);
       for(int i=1;i<snakeNum;i++){
           snake.add(new Pair<Integer, Integer>(snake.get(i-1).element.getKey(),(snake.get(i-1).element.getValue()+1)%columns),1);
       }






        JFrame Frame=new JFrame("Snake");
        Frame.setSize(500,500);
        Frame.setVisible(true);

        Frame.setLayout(new BorderLayout(100,10));


        GridLayout grid= new GridLayout(rows,columns);

        grid.setHgap(1);
        grid.setVgap(1);

        JPanel InnerPanel=new JPanel();
        InnerPanel.setLayout(grid);

        GridAccessor=new Tile[rows][columns];


        for(int r=0;r<rows;r++){
            for(int c=0;c<columns;c++){
                GridAccessor[r][c]=new Tile(false);
                InnerPanel.add(GridAccessor[r][c]);
            }
        }









        Frame.add(new JLabel("use the arrow keys to move the snake"),BorderLayout.NORTH);
        Frame.add(InnerPanel,BorderLayout.CENTER);

        Frame.setFocusable(true);
        Frame.addKeyListener(this);
        //Frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        Frame.setSize(500,500);
        Timer timeBuddy=new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               /* int newRow=(snake.head.element.getKey())%rows;
                int newCol=(((snake.head.element.getValue()-1)%columns)+columns)%columns;


                GridAccessor[newRow][newCol].setBackground(Color.DARK_GRAY);
                GridAccessor[snake.head.element.getKey()][snake.head.element.getValue()].setBackground(Color.green);





                snake.set(0,new Pair((snake.head.element.getKey())%rows,(snake.head.element.getValue()+1)%columns));*/





                try {
                    MoveSnakes();
                } catch (IllegalDirectionException ex) {
                    ex.printStackTrace();
                }

                RefreshSnakes();


            }
        });

        timeBuddy.start();

        //OuterPanel.setSize(Toolkit.getDefaultToolkit().getScreenSize());


    }
    public void MoveSnakes() throws IllegalDirectionException {
        int rowDisp=0;
        int colDisp=0;
        RefreshClearSnakes();

        //RefreshSnakes();
        switch (currentDirection){
            case BELOW:
                rowDisp++;
                break;
            case ABOVE:
                rowDisp--;
                break;
            case LEFT:
                colDisp--;
                break;

            case RIGHT:
                colDisp++;
                break;

        }

        //System.out.println(colDisp);
        Pair<Integer,Integer> temp=snake.head.element;
        //System.out.println(temp);
        snake.set(0,new Pair<Integer, Integer>((((temp.getKey()+rowDisp)%rows)+rows)%rows,(((temp.getValue()+colDisp)%columns)+columns)%columns));

       // System.out.println(snake.head.element.getValue());
        for(int i=1;i<snake.size;i++){
            Pair<Integer,Integer> temp2=snake.get(i).element;
            snake.set(i,temp);
            temp=temp2;



        }



    }

    public void RefreshClearSnakes(){

        SNIterator<Pair<Integer,Integer>> it=snake.SNIterator();
        while(it.hasNext()){
            Pair<Integer,Integer> temp=it.next();
            GridAccessor[temp.getKey()][temp.getValue()].setBackground(passiveColor);
        }

    }

    public void RefreshTile(int r,int c){
        Tile temp=GridAccessor[r][c];
        if(temp.getBackground()==activeColor){
            temp.setBackground(passiveColor);


        }
        else{
            temp.setBackground(activeColor);
        }

    }
    public void RefreshSnakes(){
        SNIterator<Pair<Integer,Integer>> it=snake.SNIterator();
        while(it.hasNext()){
            Pair<Integer,Integer> temp=it.next();
            RefreshTile(temp.getKey(),temp.getValue());
        }


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Direction newDirec=Direction.LEFT;

       switch (e.getKeyCode()){
           case 37:
               newDirec=Direction.LEFT;
               break;
           case 38:
               newDirec=Direction.ABOVE;
               break;
           case 39:
               newDirec=Direction.RIGHT;
               break;
           case 40:
               newDirec=Direction.BELOW;
               break;

       }
       if(newDirec!=Node.getOppositeDirectionOf(currentDirection)){
           currentDirection=newDirec;
       }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
