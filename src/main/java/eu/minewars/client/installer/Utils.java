package eu.minewars.client.installer;

import java.util.*;
import java.util.List;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.lang.reflect.*;

public class Utils {
	public static final String MAC_OS_HOME_PREFIX = "Library/Application Support";
	private static final char[] hexTable;

	static {
		hexTable = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	}

	public static File getWorkingDirectory() {
		return getWorkingDirectory("minecraft");
	}

	public static File getWorkingDirectory(final String applicationName) {
		final String userHome = System.getProperty("user.home", ".");
		File workingDirectory = null;
		switch (getPlatform()) {
		case LINUX:
		case SOLARIS: {
			workingDirectory = new File(userHome, String.valueOf('.') + applicationName + '/');
			break;
		}
		case WINDOWS: {
			final String applicationData = System.getenv("APPDATA");
			if (applicationData != null) {
				workingDirectory = new File(applicationData, "." + applicationName + '/');
				break;
			}
			workingDirectory = new File(userHome, String.valueOf('.') + applicationName + '/');
			break;
		}
		case MACOS: {
			workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
			break;
		}
		default: {
			workingDirectory = new File(userHome, String.valueOf(applicationName) + '/');
			break;
		}
		}
		if (!workingDirectory.exists() && !workingDirectory.mkdirs()) {
			throw new RuntimeException("The working directory could not be created: " + workingDirectory);
		}
		return workingDirectory;
	}

	public static OS getPlatform() {
		final String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("win")) {
			return OS.WINDOWS;
		}
		if (osName.contains("mac")) {
			return OS.MACOS;
		}
		if (osName.contains("solaris")) {
			return OS.SOLARIS;
		}
		if (osName.contains("sunos")) {
			return OS.SOLARIS;
		}
		if (osName.contains("linux")) {
			return OS.LINUX;
		}
		if (osName.contains("unix")) {
			return OS.LINUX;
		}
		return OS.UNKNOWN;
	}

	public static int find(final byte[] buf, final byte[] pattern) {
		return find(buf, 0, pattern);
	}

	public static int find(final byte[] buf, final int index, final byte[] pattern) {
		for (int i = index; i < buf.length - pattern.length; ++i) {
			boolean found = true;
			for (int pos = 0; pos < pattern.length; ++pos) {
				if (pattern[pos] != buf[i + pos]) {
					found = false;
					break;
				}
			}
			if (found) {
				return i;
			}
		}
		return -1;
	}

	public static byte[] readAll(final InputStream is) throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final byte[] buf = new byte[1024];
		while (true) {
			final int len = is.read(buf);
			if (len < 0) {
				break;
			}
			baos.write(buf, 0, len);
		}
		is.close();
		final byte[] bytes = baos.toByteArray();
		return bytes;
	}

	public static void dbg(final String str) {
		System.out.println(str);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String[] tokenize(final String str, final String delim) {
		final List list = new ArrayList();
		final StringTokenizer tok = new StringTokenizer(str, delim);
		while (tok.hasMoreTokens()) {
			final String token = tok.nextToken();
			list.add(token);
		}
		final String[] tokens = (String[]) list.toArray(new String[list.size()]);
		return tokens;
	}

	public static String getExceptionStackTrace(final Throwable e) {
		final StringWriter swr = new StringWriter();
		final PrintWriter pwr = new PrintWriter(swr);
		e.printStackTrace(pwr);
		pwr.close();
		try {
			swr.close();
		} catch (IOException ex) {
		}
		return swr.getBuffer().toString();
	}

	public static void copyFile(final File fileSrc, final File fileDest) throws IOException {
		if (fileSrc.getCanonicalPath().equals(fileDest.getCanonicalPath())) {
			return;
		}
		final FileInputStream fin = new FileInputStream(fileSrc);
		final FileOutputStream fout = new FileOutputStream(fileDest);
		copyAll(fin, fout);
		fout.flush();
		fin.close();
		fout.close();
	}

	public static void copyAll(final InputStream is, final OutputStream os) throws IOException {
		final byte[] buf = new byte[1024];
		while (true) {
			final int len = is.read(buf);
			if (len < 0) {
				break;
			}
			os.write(buf, 0, len);
		}
	}

	public static void showMessage(final String msg) {
		JOptionPane.showMessageDialog(null, msg, "OptiFine", 1);
	}

	public static void showErrorMessage(final String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error", 0);
	}

	public static String readFile(final File file) throws IOException {
		return readFile(file, "ASCII");
	}

	public static String readFile(final File file, final String encoding) throws IOException {
		final FileInputStream fin = new FileInputStream(file);
		return readText(fin, encoding);
	}

	public static String readText(final InputStream in, final String encoding) throws IOException {
		final InputStreamReader inr = new InputStreamReader(in, encoding);
		final BufferedReader br = new BufferedReader(inr);
		final StringBuffer sb = new StringBuffer();
		while (true) {
			final String line = br.readLine();
			if (line == null) {
				break;
			}
			sb.append(line);
			sb.append("\n");
		}
		br.close();
		inr.close();
		in.close();
		return sb.toString();
	}

	public static String[] readLines(final InputStream in, final String encoding) throws IOException {
		final String str = readText(in, encoding);
		final String[] strs = tokenize(str, "\n\r");
		return strs;
	}

	public static void centerWindow(final Component c, final Component par) {
		if (c == null) {
			return;
		}
		final Rectangle rect = c.getBounds();
		Rectangle parRect;
		if (par != null && par.isVisible()) {
			parRect = par.getBounds();
		} else {
			final Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
			parRect = new Rectangle(0, 0, scrDim.width, scrDim.height);
		}
		int newX = parRect.x + (parRect.width - rect.width) / 2;
		int newY = parRect.y + (parRect.height - rect.height) / 2;
		if (newX < 0) {
			newX = 0;
		}
		if (newY < 0) {
			newY = 0;
		}
		c.setBounds(newX, newY, rect.width, rect.height);
	}

	public static String byteArrayToHexString(final byte[] bytes) {
		if (bytes == null) {
			return "";
		}
		final StringBuffer buf = new StringBuffer();
		for (int i = 0; i < bytes.length; ++i) {
			final byte b = bytes[i];
			buf.append(Utils.hexTable[b >> 4 & 0xF]);
			buf.append(Utils.hexTable[b & 0xF]);
		}
		return buf.toString();
	}

	public static String arrayToCommaSeparatedString(final Object[] arr) {
		if (arr == null) {
			return "";
		}
		final StringBuffer buf = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			final Object val = arr[i];
			if (i > 0) {
				buf.append(", ");
			}
			if (val == null) {
				buf.append("null");
			} else if (val.getClass().isArray()) {
				buf.append("[");
				if (val instanceof Object[]) {
					final Object[] valObjArr = (Object[]) val;
					buf.append(arrayToCommaSeparatedString(valObjArr));
				} else {
					for (int ai = 0; ai < Array.getLength(val); ++ai) {
						if (ai > 0) {
							buf.append(", ");
						}
						buf.append(Array.get(val, ai));
					}
				}
				buf.append("]");
			} else {
				buf.append(arr[i]);
			}
		}
		return buf.toString();
	}

	public static String removePrefix(String str, final String prefix) {
		if (str == null || prefix == null) {
			return str;
		}
		if (str.startsWith(prefix)) {
			str = str.substring(prefix.length());
		}
		return str;
	}

	public static String ensurePrefix(String str, final String prefix) {
		if (str == null || prefix == null) {
			return str;
		}
		if (!str.startsWith(prefix)) {
			str = String.valueOf(prefix) + str;
		}
		return str;
	}

	public static boolean equals(final Object o1, final Object o2) {
		return o1 == o2 || (o1 != null && o1.equals(o2));
	}

	public enum OS {
		LINUX("LINUX", 0), SOLARIS("SOLARIS", 1), WINDOWS("WINDOWS", 2), MACOS("MACOS", 3), UNKNOWN("UNKNOWN", 4);

		private OS(final String s, final int n) {
		}
	}
}
