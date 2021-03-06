package ortho4d;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

public final class Logger {
	// Change this to disable EVERY output except inevitable error messages.
	private static final boolean DISABLE_OUT = false;
	private static final PrintStream OUT = System.out;

	private Logger() {
		// Prevent instantiations
	}

	public static final void println(String... strings) {
		if (!DISABLE_OUT) {
			println(2, strings);
		}
	}

	public static final void println(int callLevel, Throwable t) {
		if (!DISABLE_OUT) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			pw.flush();
			println(callLevel + 1, sw.toString());
		}
	}

	public static final void println(int callLevel, String... strings) {
		if (!DISABLE_OUT) {
			final StackTraceElement e = new RuntimeException().getStackTrace()[callLevel];
			final StringBuilder sb = new StringBuilder();

			String className = e.getClassName();
			if (className.startsWith("ortho4d")) {
				sb.append(className.substring(7));
			} else {
				sb.append(className);
			}
			sb.append('.');
			sb.append(e.getMethodName());
			sb.append('@');
			sb.append(e.getLineNumber());
			sb.append(": ");
			sb.append(Arrays.toString(strings));
			sb.append(" -- ");
			sb.append(Thread.currentThread().getName());
			sb.append(", ID=");
			sb.append(Thread.currentThread().getId());
			OUT.println(sb.toString());
		}
	}
}
