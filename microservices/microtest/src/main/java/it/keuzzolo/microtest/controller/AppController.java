package it.keuzzolo.microtest.controller;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.keuzzolo.microtest.dto.AppDto;

@RestController
@RequestMapping("/api")
public class AppController {

	Logger logger = LoggerFactory.getLogger(AppController.class);
	
	@Value("${app.version}")
	private String appVersion;

	@GetMapping("random")
	public AppDto getRandom() throws SocketException {
		logger.debug("START - getRandom()");

		InetAddress ip = null;
		String hostname = null;
		try {
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		AppDto dto = AppDto.builder().name("test name :-)").date(Calendar.getInstance()).ip(getHost4Address())
				.hostname(hostname).version(appVersion).build();
		logger.debug("END - getRandom({})", dto);
		
		return dto;
	}
	
	@GetMapping("sticky")
	public AppDto getSticky() throws SocketException {
		logger.debug("START - getSticky()");
		AppDto dto = getRandom();
		logger.debug("END - getSticky({})", dto);
		return dto;
	}

	private static List<Inet4Address> getInet4Addresses() throws SocketException {
		List<Inet4Address> ret = new ArrayList<Inet4Address>();

		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
		for (NetworkInterface netint : Collections.list(nets)) {
			Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
			for (InetAddress inetAddress : Collections.list(inetAddresses)) {
				if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
					ret.add((Inet4Address) inetAddress);
				}
			}
		}

		return ret;
	}

	private static String getHost4Address() throws SocketException {
		List<Inet4Address> inet4 = getInet4Addresses();
		return !inet4.isEmpty() ? inet4.get(0).getHostAddress() : null;
	}

}
