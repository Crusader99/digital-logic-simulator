package de.crusader.analytics;

import java.io.Closeable;

import de.crusader.helpers.MathUtils;
import lombok.Getter;

@Getter
public class Analytics implements Closeable {
	private Class<?> type;
	private long started = System.nanoTime();

	/**
	 * Called when creating a new instance.
	 */
	public Analytics(Object obj) {
		this(obj.getClass());
	}

	/**
	 * Called when creating a new instance.
	 */
	public Analytics(Class<?> type) {
		super();
		this.type = type;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() {
		StatisticsScreen stats = PerformanceAnalytics.stats.get(type);
		if (stats != null) {
			if (stats.isFps()) {
				synchronized (PerformanceAnalytics.getTimer()) {
					stats.getCurrentFrameUpdates().incrementAndGet();
				}
			} else {
				long time = System.nanoTime() - started;
				stats.setCurrentValue(MathUtils.bounds((time / (float) StatisticsScreen.MAX_REPAINT_NS), 0f, 1f));
			}
		}
	}

}
