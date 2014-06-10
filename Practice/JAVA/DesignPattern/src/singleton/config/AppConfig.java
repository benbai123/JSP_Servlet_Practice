package singleton.config;

import java.util.Locale;

/** Config for application, only one instance over whole application.
 * 
 * @author benbai123
 *
 */
public class AppConfig {
	private Locale _locale;
	private String _encoding;
	private static AppConfig _config = new AppConfig();
	private AppConfig () {
		// assume read config file here
		_locale = Locale.JAPAN;
		_encoding = "UTF-8";
	}
	public Locale getLocale () {
		return _locale;
	}
	public String getEncoding () {
		return _encoding;
	}
	public static AppConfig getConfig () {
		return _config;
	}
}
