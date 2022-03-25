import edu.uptc.so.fms.FileManagerSystem;
import edu.uptc.so.fms.FileType;
import edu.uptc.so.views.FMSPanel;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class TestView extends JFrame{
	private static final long serialVersionUID = 1L;
	private static final int FORMAT = 0, SEED = 1, INIT = 2;

	public TestView(){
        setTitle("FMS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        
        setLayout(new BorderLayout());
        FMSPanel panel = new FMSPanel();
        add(panel, BorderLayout.CENTER);
        
        setVisible(true);
    }
	
	public static void main(String[] args) {
		int option = SEED;
		
		switch (option) {
		case FORMAT:
			FileManagerSystem.getInstance().format();
			break;
			
		case SEED:
			FileManagerSystem.getInstance().format();
			FileManagerSystem.getInstance().init();
			FileManagerSystem.getInstance().write("/test.txt", FileType.FILE);
			FileManagerSystem.getInstance().write("/homework", FileType.DIR);
			FileManagerSystem.getInstance().write("/homework/new.txt", FileType.FILE);
			FileManagerSystem.getInstance().write("/homework/so", FileType.DIR);
			FileManagerSystem.getInstance().write("/homework/so/final.doc", FileType.FILE);
			FileManagerSystem.getInstance().write("/tasks", FileType.DIR);
			FileManagerSystem.getInstance().write("/tasks/todo.doc", FileType.DIR);
			break;
		case INIT:
			FileManagerSystem.getInstance().init();
			break;

		default:
			System.out.println("NO");
			break;
		}
		
		new TestView();
	}
}
