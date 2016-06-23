package com.wangge.buzmgt.monthTask.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**调用app-server接口用其url
 * @author yangqc
 *
 */
@ConfigurationProperties(prefix = "app-server")
public class AppServer {
	public static String URL;

	public static String getURL() {
		return URL;
	}

	public static void setURL(String uRL) {
		URL = uRL;
	}

	public AppServer() {
		super();
	}

}
