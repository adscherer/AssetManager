package com.neosavvy.assets.util;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtils {

	public static boolean isEquivalent(String a, String b) {
		if (areBothNull(a, b))
			return true;

		if (areEitherNull(a, b)) {
			if ((a == null) && "".equals(b))
				return true;

			return "".equals(a);
		}

		return a.equals(b);
	}

	public static boolean isAnyEquivalent(String a, String... options) {
		for (String b : options) {
			if (isEquivalent(a, b)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isNotEquivalent(String a, String b) {
		return !isEquivalent(a, b);
	}

	public static boolean areEitherNull(Object a, Object b) {
		return (a == null) || (b == null);
	}

	public static boolean areBothNull(Object a, Object b) {
		return (a == null) && (b == null);
	}

	public static boolean startsWith(String s, String prefix) {
		if (s == null)
			return false;

		return s.startsWith(prefix);
	}

	public static boolean endsWith(String s, String suffix) {
		if (s == null)
			return false;

		return s.endsWith(suffix);
	}

	public static boolean hasValue(String s) {
		return s != null && s.length() > 0;
	}

	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	public static int parseInteger(String s) {
		return parseInteger(s, 0);
	}

	public static int parseInteger(String s, int def) {
		try {
			return Integer.parseInt(s);
		} catch (Throwable ex) {
			return def;
		}
	}

	public static long parseLong(String s) {
		return parseLong(s, 0);
	}

	public static long parseLong(String s, long def) {
		try {
			return Long.parseLong(s);
		} catch (Throwable ex) {
			return def;
		}
	}

	public static double parseDouble(String s) {
		return parseDouble(s, 0);
	}

	public static double parseDouble(String s, double def) {
		try {
			return Double.parseDouble(s);
		} catch (Throwable ex) {
			return def;
		}
	}

	public static boolean parseBoolean(String s) {
		return parseBoolean(s, false);
	}

	public static boolean parseBoolean(String s, boolean def) {
		if (isEmpty(s))
			return def;

		s = s.toLowerCase();

		if (s.equals("y"))
			return true;

		if (s.equals("yes"))
			return true;

		if (s.equals("t"))
			return true;

		if (s.equals("true"))
			return true;

		if (s.equals("on"))
			return true;

		if (s.equals("1"))
			return true;

		return false;
	}

	public static String replaceAll(String s, String oldValue, String newValue) {
		return replaceAll(s, oldValue, newValue, 0);
	}

	public static String replaceAll(String s, String oldValue, String newValue, int startingAt) {
		if (s == null)
			return s;

		int i = startingAt;

		while (true) {
			i = s.indexOf(oldValue, i);

			if (i < 0) {
				return s;
			}

			s = s.substring(0, i) + newValue + s.substring(i + oldValue.length());
			i += newValue.length();
		}
	}

	public static String getPathSeparator() {
		return "/";
	}

	public static String joinPath(String a, String b) {
		String x = getPathSeparator();
		a = (a == null) ? "" : a;
		b = (b == null) ? "" : b;

		int n = 0;
		if (a != null && a.endsWith(x))
			n++;
		if (b != null && b.startsWith(x))
			n++;

		StringBuffer sb = new StringBuffer();
		sb.append(a);
		if (n == 0)
			sb.append(x);
		if (n == 2)
			sb.append(b.substring(1));
		else
			sb.append(b);
		return sb.toString();
	}

	public static List<String> tokens(String source, String delim) {
		return tokens(source, delim, false);
	}

	public static List<String> tokens(String source, String delim, boolean removeQuotes) {
		ArrayList<String> results = new ArrayList<String>();
		if (source == null)
			return results;

		StringTokenizer st = new StringTokenizer(source, delim, false);
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			s = s.trim();
			if (removeQuotes)
				s = replaceAll(s, "\"", "");

			results.add(s);
		}

		return results;
	}

	public static String substringBefore(String str, String separator) {
		if (isEmpty(str) || separator == null)
			return str;
		if (separator.length() == 0)
			return "";
		int pos = str.indexOf(separator);
		if (pos == -1)
			return str;
		else
			return str.substring(0, pos);
	}

	public static String substringAfter(String str, String separator) {
		if (isEmpty(str))
			return str;
		if (separator == null)
			return "";
		int pos = str.indexOf(separator);
		if (pos == -1)
			return "";
		else
			return str.substring(pos + separator.length());
	}

	/**
	 * Courtesy of Spring's ResourceUtils
	 */
	public static File getClasspathFile(String path) {

		try {
			URL url = getDefaultClassLoader().getResource(path);

			return new File(new URI(url.toString()).getSchemeSpecificPart());
		} catch (Throwable ex) {
			Log.info("Error getting file: %s", ex);
			return null;
		}

	}

	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			// Cannot access thread context ClassLoader - falling back to system class loader...
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = StringUtils.class.getClassLoader();
		}
		return cl;
	}

}
