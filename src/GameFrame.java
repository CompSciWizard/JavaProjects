import javax.swing.JFrame;

//An extended version of java.awt.Frame that adds support for the JFC/Swing component architecture (graphics)

public class GameFrame extends JFrame{

	//calls GameFrame
	GameFrame(){
		
		//adds GamePanel features
		this.add(new GamePanel());
		//Sets title at top left screen
		this.setTitle("Snake");
		//Sets the operation that will happen by default
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Frame is not resizable by the user
		this.setResizable(false);
		//Causes the dimesions of the screen to fit the screen if the preferred size is not assigned 
		this.pack();
		//Shows the visuals of the window
		this.setVisible(true);
		//Sets the location of the window relative to the specified components (center screen)
		this.setLocationRelativeTo(null);
		
		
	}
}