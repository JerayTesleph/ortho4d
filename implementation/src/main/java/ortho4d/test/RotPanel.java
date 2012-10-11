package ortho4d.test;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

public class RotPanel extends JPanel {
	private static final long serialVersionUID = -7123335543703922721L;

	private final JPanel previewXY, previewWZ;
	private final BoundedRangeModel alpha, beta, gamma;

	public RotPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.5, 0.5 };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0 };
		setLayout(gridBagLayout);
		
		// ==== Angles
		
		JPanel angles = new JPanel();
		angles.setBorder(new TitledBorder(null, "Angles", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_angles = new GridBagConstraints();
		gbc_angles.fill = GridBagConstraints.BOTH;
		gbc_angles.insets = new Insets(5, 5, 5, 5);
		gbc_angles.gridwidth = 2;
		gbc_angles.gridx = 0;
		gbc_angles.gridy = 0;
		add(angles, gbc_angles);
		GridBagLayout gbl_angles = new GridBagLayout();
		gbl_angles.columnWidths = new int[] { 0, 0 };
		gbl_angles.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_angles.columnWeights = new double[] { 0.0, 1.0 };
		gbl_angles.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		angles.setLayout(gbl_angles);

		JLabel lblAlpha = new JLabel("Alpha");
		GridBagConstraints gbc_lblAlpha = new GridBagConstraints();
		gbc_lblAlpha.anchor = GridBagConstraints.WEST;
		gbc_lblAlpha.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlpha.gridx = 0;
		gbc_lblAlpha.gridy = 0;
		angles.add(lblAlpha, gbc_lblAlpha);

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
		angles.add(slAlpha, gbc_slAlpha);

		JLabel lblBeta = new JLabel("Beta");
		GridBagConstraints gbc_lblBeta = new GridBagConstraints();
		gbc_lblBeta.anchor = GridBagConstraints.WEST;
		gbc_lblBeta.insets = new Insets(0, 0, 5, 5);
		gbc_lblBeta.gridx = 0;
		gbc_lblBeta.gridy = 1;
		angles.add(lblBeta, gbc_lblBeta);

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
		angles.add(slBeta, gbc_slBeta);

		JLabel lblGamma = new JLabel("Gamma");
		GridBagConstraints gbc_lblGamma = new GridBagConstraints();
		gbc_lblGamma.insets = new Insets(0, 0, 0, 5);
		gbc_lblGamma.anchor = GridBagConstraints.WEST;
		gbc_lblGamma.gridx = 0;
		gbc_lblGamma.gridy = 2;
		angles.add(lblGamma, gbc_lblGamma);

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
		angles.add(slGamma, gbc_slGamma);

		// == Previews
		
		previewXY = new JPanel();
		previewXY.setBorder(new TitledBorder(null, "PreviewXY",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_previewXY = new GridBagConstraints();
		gbc_previewXY.fill = GridBagConstraints.BOTH;
		gbc_previewXY.insets = new Insets(0, 5, 5, 0);
		gbc_previewXY.gridx = 0;
		gbc_previewXY.gridy = 1;
		add(previewXY, gbc_previewXY);
		previewXY.setLayout(new BorderLayout(0, 0));

		previewWZ = new JPanel();
		previewWZ.setBorder(new TitledBorder(null, "PreviewWZ",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_previewWZ = new GridBagConstraints();
		gbc_previewWZ.fill = GridBagConstraints.BOTH;
		gbc_previewWZ.insets = new Insets(0, 0, 5, 5);
		gbc_previewWZ.gridx = 1;
		gbc_previewWZ.gridy = 1;
		add(previewWZ, gbc_previewWZ);
		previewWZ.setLayout(new BorderLayout(0, 0));
	}

	public void setPreviewXY(JComponent c) {
		previewXY.add(c, BorderLayout.CENTER);
	}

	public void setPreviewWZ(JComponent c) {
		previewWZ.add(c, BorderLayout.CENTER);
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
