package ortho4d.gui;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import ortho4d.Logger;
import ortho4d.logic.RotationalCamera;
import ortho4d.logic.RotationalConfig;
import ortho4d.math.Vector;

public final class CameraController implements MouseListener,
		MouseMotionListener, MouseWheelListener {
	private static final boolean DEBUG = true;

	/**
	 * Rotation per "wheel click"<br>
	 */
	// "Math.PI / 12" means "12 clicks per 180 degree"
	private static final double GAMMA_MULTIPLIER = Math.PI / 40;

	/**
	 * Rotation per pixel dragged
	 */
	// "Math.PI / 700" means "700 pixel per 180 degree"
	private static final double ALPHA_BETA_MULTIPLIER = Math.PI / 700;

	// Either <30 or >170
	private static final double DISTANCE = 250;

	private final RotationalCamera cam;
	private final Point dragStart = new Point();
	private final Vector tmp = new Vector();

	private boolean pressed = false;

	public CameraController() {
		this(new RotationalCamera());
	}

	public CameraController(RotationalCamera cam) {
		this.cam = cam;
		tmp.w = -DISTANCE;
		cam.moveTo(tmp);
		cam.commit();
	}

	public RotationalCamera getCamera() {
		return cam;
	}

	public void listenOn(Component c) {
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
		c.addMouseWheelListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Ignore
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			dragStart.setLocation(e.getPoint());
			pressed = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Ignore
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Ignore
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!pressed) {
			return;
		}

		final double alphaAmount, betaAmount;
		final Point dragDest = e.getPoint();

		betaAmount = (dragDest.x - dragStart.x) * ALPHA_BETA_MULTIPLIER;
		// Alpha behaves as "latitude", so let it (inversely) depend on y
		alphaAmount = (dragDest.y - dragStart.y) * -ALPHA_BETA_MULTIPLIER;

		cam.modifyAlpha(alphaAmount);
		cam.modifyBeta(betaAmount);
		updateLocation();
		cam.commit();

		dragStart.setLocation(dragDest);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// Ignore
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (DEBUG) {
			Logger.println("Rotated " + e.getPreciseWheelRotation());
		}

		final double amount = e.getPreciseWheelRotation() * GAMMA_MULTIPLIER;
		cam.modifyGamma(amount);
		updateLocation();
		cam.commit();
	}

	private void updateLocation() {
		tmp.x = tmp.y = tmp.z = 0;
		tmp.w = -DISTANCE;

		RotationalConfig conf = cam.getControlledConfig();
		conf.getInverseMatrix().times(tmp, tmp);
		cam.moveTo(tmp);
		if (DEBUG) {
			Logger.println("alpha=" + convert(conf.getAlpha()), "beta="
					+ convert(conf.getBeta()),
					"gamma=" + convert(conf.getGamma()));
			conf.getMatrix().times(tmp, tmp);
			Logger.println("Error amount: [0,0,0,-" + DISTANCE + "] -> " + tmp);
		}
	}

	private static final double convert(double radians) {
		return Math.toDegrees(radians);
	}
}
