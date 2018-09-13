package de.crusader.analytics;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicInteger;

import de.crusader.helpers.EnumDirection;
import de.crusader.helpers.picture.EnumPictureGrid;
import de.crusader.helpers.picture.Picture;
import de.crusader.painter.DrawString;
import de.crusader.painter.Painter;
import de.crusader.painter.util.EnumCenteredType;
import de.crusader.screens.Management;
import de.crusader.screens.api.ControlScreen;
import de.crusader.screens.interfaces.IScreenEvents;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class StatisticsScreen extends ControlScreen implements IScreenEvents {
	public final static int MAX_REPAINT_NS = 10_000_000;

	/*
	 * Colors of the GUI
	 */
	private final static Color LINE_COLOR = Color.RED;
	private final static Color BACKGROUND_COLOR = Color.WHITE;
	private final static Color TEXT_COLOR = Color.BLACK;
	private final static int BORDER_SIZE = 20;

	/*
	 * Cache of the statistic
	 */
	private Picture statsCache = Picture.createEmpty(512, 512, EnumPictureGrid.RGB_565);

	/*
	 * Current values of the statistic
	 */
	@Setter
	private float currentValue = 0f;
	private float position = 0f;

	/*
	 * Settings of this StatisticsScreen
	 */
	@Getter
	private AtomicInteger currentFrameUpdates = new AtomicInteger(0);
	private final String name;
	@Getter
	private final boolean fps;

	{
		// Called on initialize
		try (Painter cache = new Painter(statsCache)) {
			cache.createRectangle().rectangle(0, 0, cache.getWidth(), cache.getHeight()).color(BACKGROUND_COLOR)
					.filled(true).draw();
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onPaint(de.crusader.painter.Painter)
	 */
	@Override
	public void onPaint(Painter p) {
		p.createRectangle().color(BACKGROUND_COLOR).rectangle(0, 0, p.getWidth(), p.getHeight()).draw();

		p.createLine().position(BORDER_SIZE, BORDER_SIZE).position2(BORDER_SIZE, p.getHeight() - BORDER_SIZE)
				.color(Color.RED).draw();
		p.createLine().position(p.getWidth() - BORDER_SIZE, p.getHeight() - BORDER_SIZE)
				.position2(BORDER_SIZE, p.getHeight() - BORDER_SIZE).color(LINE_COLOR).draw();

		drawArrow(p, new Point(BORDER_SIZE, BORDER_SIZE), EnumDirection.UP);
		drawArrow(p, new Point(p.getWidth() - BORDER_SIZE, p.getHeight() - BORDER_SIZE), EnumDirection.RIGHT);

		p.createString().text(name).rectangle(0, 0, p.getWidth(), BORDER_SIZE).filled(true).centered(EnumCenteredType.Y)
				.color(TEXT_COLOR).draw();

		DrawString str = p.createString().text("Zeit")
				.rectangle(p.getWidth() - BORDER_SIZE, p.getHeight() - BORDER_SIZE, p.getWidth(), BORDER_SIZE)
				.filled(true).centered(EnumCenteredType.Y).color(TEXT_COLOR);
		str.position(p.getWidth() - str.getWidth(), str.getY()).draw();

		drawStats(p);

		if (fps) {
			str.text("- 100 Hz").position(BORDER_SIZE, BORDER_SIZE << 1).draw();
			str.text("- 50 Hz").position(BORDER_SIZE, p.getHeight() >> 1).draw();
			str.text("- 0 Hz").position(BORDER_SIZE, p.getHeight() - (BORDER_SIZE << 1)).draw();
		} else {
			str.text("- 10.000.000 ns").position(BORDER_SIZE, BORDER_SIZE << 1).draw();
			str.text("- 500.000 ns").position(BORDER_SIZE, p.getHeight() >> 1).draw();
			str.text("- 0 ns").position(BORDER_SIZE, p.getHeight() - (BORDER_SIZE << 1)).draw();
		}
	}

	/*
	 * Draws an arrow in the given target direction
	 */
	private void drawArrow(Painter p, Point point, EnumDirection target) {
		p.createLine().color(LINE_COLOR).position(point).setDirection(target.rotate(90 + 45), BORDER_SIZE >> 1).draw();
		p.createLine().color(LINE_COLOR).position(point).setDirection(target.rotate(-90 - 45), BORDER_SIZE >> 1).draw();
	}

	/*
	 * Draw statistic
	 */
	public void drawStats(Painter p) {
		int x;
		try (Painter cache = new Painter(statsCache)) {
			int fromX = (int) (cache.getWidth() * position);
			position += 0.001f; // +1 %
			int toX = x = (int) (cache.getWidth() * position);
			int toY = (int) (cache.getHeight() * currentValue);
			if (position > 1f) {
				position = 0f;
			}
			cache.createRectangle().rectangle(fromX, 0, toX - fromX, cache.getHeight() - toY).color(BACKGROUND_COLOR)
					.filled(true).draw();
			cache.createRectangle().rectangle(fromX, cache.getHeight() - toY, toX - fromX, cache.getHeight())
					.color(Color.CYAN).filled(true).draw();
		}

		if (p == null) {
			return;
		}

		Rectangle rect = new Rectangle(BORDER_SIZE + 1, BORDER_SIZE << 1, p.getWidth() - (BORDER_SIZE << 1),
				p.getHeight() - BORDER_SIZE - 1);
		rect.width -= rect.x;
		rect.height -= rect.y;

		int lengthA = x * rect.width / statsCache.getWidth();
		p.createImage().image(statsCache).rectangle(rect.x + rect.width - lengthA, rect.y, lengthA, rect.height)
				.imageRegion(0, 0, x, statsCache.getHeight()).draw();
		int lengthB = rect.width - lengthA;
		p.createImage().image(statsCache).rectangle(rect.x, rect.y, lengthB, rect.height)
				.imageRegion(x, 0, statsCache.getWidth() - x, statsCache.getHeight()).draw();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.crusader.screens.api.Screen#onResize()
	 */
	@Override
	public void onResize() {
		setRectangle(Management.getDocker().dock(getParent()));
		super.onResize();
	}
}
