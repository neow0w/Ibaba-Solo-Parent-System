import javax.swing.SwingUtilities;
import logInPage.*;

public class Main {
	    public static void main(String[] args) {
	        //SwingUtilities.invokeLater(mainPage::launch);
	        SwingUtilities.invokeLater(loginPage::launch);
	    }
}
