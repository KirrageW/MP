import javax.swing.JOptionPane;

public class Launcher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int launcher = JOptionPane.showConfirmDialog(null, "Do you wish to begin a simulation?");
		
		
		if (launcher == JOptionPane.YES_OPTION) {

			String numContinents = JOptionPane.showInputDialog(null, "Number of continents?");

			// report entered data back to user 
			if ((numContinents != null) && (Integer.parseInt(numContinents) > 0 && Integer.parseInt(numContinents) < 5 )) {
				JOptionPane.showMessageDialog(null, "You entered \"" + numContinents + " continents\"", "",
						JOptionPane.INFORMATION_MESSAGE);
			}

			// close program if textfield is empty or is user closes window
			//else
				/*System.exit(0);
			
			// get balance as double, keep asking if format is wrong
						boolean acceptable = false;
						while (!acceptable) {
							String inputBalance = JOptionPane.showInputDialog(null, "Enter the customer's balance");
							if (inputBalance != null) {

								try {
									double bal = Double.parseDouble(inputBalance.trim());
									
									//will only reach this if user puts something in the field, and if a double can be parsed from it
									acceptable = true; 
									
									//converts balance input into an integer, because that's how we want it to be stored in the CustomerAccount object (pence not pounds)
									int balanceInPence = (int) Math.round(bal*100);
									
									//make CustomerAccount 
									CustomerAccount cac = new CustomerAccount(balanceInPence, writeName); 
									
									//make a GUI object using this CustomerAccount 
									new LWMGUI(cac);
									
								} catch (NumberFormatException x) {JOptionPane.showMessageDialog(null, "Incorrect format");}
							
							} else
								System.exit(0);
						}*/
						
					} else
						System.exit(0);

	}

}
