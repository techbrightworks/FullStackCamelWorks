package org.srinivas.siteworks.dbrestserver.utilities;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class FireWorksService.
 */
public class FireWorksService {
	private static final Logger log = LoggerFactory.getLogger(FireWorksService.class);
	private final Map<String, FireWork> fireworksData = new TreeMap<String, FireWork>();

	/**
	 * Instantiates a new fireworksservice.
	 */
	public FireWorksService() {
		fireworksData.put("123", new FireWork(123, "Sparklers", "Sparkler200", "Long Sparklers"));
		fireworksData.put("456", new FireWork(456, "Rockets", "Rocket Absolute", "Goes up with sound"));
		fireworksData.put("789", new FireWork(789, "Fountains", "Small Fountain", "Lasts for two minutes"));
		fireworksData.put("265", new FireWork(265, "Swirls", "Ground Swirls", "Rotates with Coloured Sparkles"));
	}

	/**
	 * Gets the firework.
	 *
	 * @param id the id
	 * @return the fire work
	 */
	public FireWork getFireWork(String id) {
		return fireworksData.get(id);
	}

	/**
	 * List fireworks.
	 *
	 * @return the collection
	 */
	public Collection<FireWork> listFireWorks() {
		return fireworksData.values();
	}

	/**
	 * Update firework.
	 *
	 * @param firework the firework
	 * @return the fire work
	 */
	public FireWork updateFireWork(FireWork firework) {
		log.info("Updating firework at FireWorksService");
		return fireworksData.put("" + firework.getId(), firework);
	}

	/**
	 * Find firework by id.
	 *
	 * @param Id the id
	 * @return the fire work
	 */
	public FireWork findFireWorkById(String Id) {
		return fireworksData.get(Id);
	}
}