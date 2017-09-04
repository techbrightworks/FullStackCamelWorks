package org.srinivas.siteworks.dbrestserver.utilities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class FireWorks.
 */
@XmlRootElement(name = "fireworks",namespace="urn:fireworks")
@XmlAccessorType(XmlAccessType.FIELD)
public class FireWorks {
	@XmlElement(name = "firework",namespace="urn:firework")
	private List<FireWork> fireworks = new ArrayList<FireWork>();

	/**
	 * Gets the fireworks.
	 *
	 * @return the fireworks
	 */
	public List<FireWork> getFireworks() {
		return fireworks;
	}

	/**
	 * Sets the fireworks.
	 *
	 * @param fireworks the new fireworks
	 */
	public void setFireworks(List<FireWork> fireworks) {
		this.fireworks = fireworks;
	}
}