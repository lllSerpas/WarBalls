package wb.menu;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	JLabel height, width, tick, friendlyTeamCount, enemyTeamCount, sandBags, waveTimer, speed, winCount, aggression, friendlyColor, enemyColor, yourColor;
	JTextField fheight, fwidth, ftick, ffriendlyTeamCount, fenemyTeamCount, fsandBags, fwaveTimer, fspeed, fwinCount, faggression, ffriendlyColor, fenemyColor, fyourColor;
	
	JPanel diagnolMovementType, ffp, barrierp, dimension, start;
	JCheckBox type1, type2, type3, type4, ff, barrier;
	
	InputPanel()
	{
		height = new JLabel("Height");
		width = new JLabel("Height");
		tick = new JLabel("Tick Timer");
		friendlyTeamCount = new JLabel("Friendly Team Count");
		enemyTeamCount = new JLabel("Enemy Team Count");
		sandBags = new JLabel("Sand Bag Count");
		waveTimer = new JLabel("Wave Timer");
		speed = new JLabel("Movement Speed");
		winCount = new JLabel("Kills To Win");
		aggression = new JLabel("Level of Aggresion");
		
		
	}
}
