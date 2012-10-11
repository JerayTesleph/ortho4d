package ortho4d.test;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import ortho4d.gui.AnglesPanel;

public class RotPanel extends JPanel {
	private static final long serialVersionUID = -7123335543703922721L;

	private final JPanel previewXY, previewWZ;
	private final AnglesPanel angles;

	public RotPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.5, 0.5 };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0 };
		setLayout(gridBagLayout);
		
		// ==== Angles
		
		angles = new AnglesPanel();
		GridBagConstraints gbc_angles = new GridBagConstraints();
		gbc_angles.fill = GridBagConstraints.BOTH;
		gbc_angles.insets = new Insets(5, 5, 5, 5);
		gbc_angles.gridwidth = 2;
		gbc_angles.gridx = 0;
		gbc_angles.gridy = 0;
		add(angles, gbc_angles);

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
		return angles.getAlpha();
	}

	public BoundedRangeModel getBeta() {
		return angles.getBeta();
	}

	public BoundedRangeModel getGamma() {
		return angles.getGamma();
	}
}
