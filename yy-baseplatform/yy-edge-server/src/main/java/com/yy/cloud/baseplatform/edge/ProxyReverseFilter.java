package com.yy.cloud.baseplatform.edge;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyReverseFilter extends ZuulFilter {
	private final String LOCATION_HEADER_NAME = "Location";

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 7;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
		
		log.info("Edge server session id is {}.", context.getRequest().getSession().getId());
		String portalHost = context.getRequest().getServerName();
		String portalProtocol = context.getRequest().getScheme();
		int portalPort = context.getRequest().getServerPort();
		List<Pair<String, String>> responseHeaders = context.getZuulResponseHeaders();
		for (Pair<String, String> entry : responseHeaders) {
	        log.info("The pair item: First:{}. Seconde:{}.", entry.first(), entry.second());
			if (entry.first().equals(LOCATION_HEADER_NAME) && !entry.second().equals(portalHost)) {
				try {
					URL locationOriginal = new URL(entry.second());
					String path = locationOriginal.getFile();
					log.info("The URL is:" + path);
					if (path.startsWith("/ui/"))
						path = path.replace("/ui/", "/frontend/ui/");
					else if (path.startsWith("/uaa/"))
						path = path.replace("/uaa/", "/authserver/uaa/");
				//	else if (path.startsWith("/user/"))
				//		path = path.replace("/user/", "/authserver/uaa/user/");
					URL locationNew = new URL(portalProtocol, portalHost, portalPort, path);
					entry.setSecond(locationNew.toString());

					log.info("The New URL is:" + locationNew.toString());
				} catch (MalformedURLException e) {
					return new Object();
				}
			}

		}

		return new Object();
	}

}
