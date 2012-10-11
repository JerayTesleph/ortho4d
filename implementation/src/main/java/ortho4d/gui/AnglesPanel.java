package ortho4d.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoundedRangeModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

public class AnglesPanel extends JPanel {
	private static final long serialVersionUID = 4596843991368577911L;

	private final BoundedRangeModel alpha, beta, gamma;

	public AnglesPanel() {
		setBorder(new TitledBorder(null, "Angles", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		GridBagLayout gbl_angles = new GridBagLayout();
		gbl_angles.columnWidths = new int[] { 0, 0 };
		gbl_angles.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_angles.columnWeights = new double[] { 0.0, 1.0 };
		gbl_angles.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gbl_angles);

		JLabel lblAlpha = new JLabel("Alpha");
		GridBagConstraints gbc_lblAlpha = new GridBagConstraints();
		gbc_lblAlpha.anchor = GridBagConstraints.WEST;
		gbc_lblAlpha.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlpha.gridx = 0;
		gbc_lblAlpha.gridy = 0;
		add(lblAlpha, gbc_lblAlpha);

		JSlider slAlpha = new JSlider();
		alpha = slAlpha.getModel();
		slAlpha.setMinimum(-90);
		slAlpha.setValue(0);
		slAlpha.setMaximum(90);
		GridBagConstraints gbc_slAlpha = new GridBagConstraints();
		gbc_slAlpha.fill = GridBagConstraints.BOTH;
		gbc_slAlpha.insets = new Insets(0, 0, 5, 0);
		gbc_slAlpha.gridx = 1;
		gbc_slAlpha.gridy = 0;
		add(slAlpha, gbc_slAlpha);

		JLabel lblBeta = new JLabel("Beta");
		GridBagConstraints gbc_lblBeta = new GridBagConstraints();
		gbc_lblBeta.anchor = GridBagConstraints.WEST;
		gbc_lblBeta.insets = new Insets(0, 0, 5, 5);
		gbc_lblBeta.gridx = 0;
		gbc_lblBeta.gridy = 1;
		add(lblBeta, gbc_lblBeta);

		JSlider slBeta = new JSlider();
		beta = slBeta.getModel();
		slBeta.setMinimum(-90);
		slBeta.setValue(0);
		slBeta.setMaximum(90);
		GridBagConstraints gbc_slBeta = new GridBagConstraints();
		gbc_slBeta.fill = GridBagConstraints.BOTH;
		gbc_slBeta.insets = new Insets(0, 0, 5, 0);
		gbc_slBeta.gridx = 1;
		gbc_slBeta.gridy = 1;
		add(slBeta, gbc_slBeta);

		JLabel lblGamma = new JLabel("Gamma");
		GridBagConstraints gbc_lblGamma = new GridBagConstraints();
		gbc_lblGamma.insets = new Insets(0, 0, 0, 5);
		gbc_lblGamma.anchor = GridBagConstraints.WEST;
		gbc_lblGamma.gridx = 0;
		gbc_lblGamma.gridy = 2;
		add(lblGamma, gbc_lblGamma);

		JSlider slGamma = new JSlider();
		gamma = slGamma.getModel();
		slGamma.setMinimum(-180);
		slGamma.setValue(0);
		slGamma.setMaximum(180);
		GridBagConstraints gbc_slGamma = new GridBagConstraints();
		gbc_slGamma.fill = GridBagConstraints.BOTH;
		gbc_slGamma.insets = new Insets(0, 0, 5, 0);
		gbc_slGamma.gridx = 1;
		gbc_slGamma.gridy = 2;
		add(slGamma, gbc_slGamma);
	}

	public BoundedRangeModel getAlpha() {
		return alpha;
	}

	public BoundedRangeModel getBeta() {
		return beta;
	}

	public BoundedRangeModel getGamma() {
		return gamma;
	}
}
