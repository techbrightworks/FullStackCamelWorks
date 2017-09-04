package org.srinivas.siteworks.dbrestserver.utilities;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * The Class DbRestServerUtility.
 */
public class DbRestServerUtility {

	private static final Logger log = LoggerFactory.getLogger(DbRestServerUtility.class);

	/**
	 * Fireworks collection to xml.
	 *
	 * @param fireworks the fws
	 * @param stringWriter the string writer
	 */
	public static void fireWorksCollectionToXML(FireWorks fireworks, StringWriter stringWriter) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(FireWorks.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
					new NamespacePrefixMapper() {
						@Override
						public String getPreferredPrefix(String namespaceuri, String suggestion,
								boolean requireprefix) {
							String prefix = "";
							if ("urn:fireworks".equalsIgnoreCase(namespaceuri)) {
								prefix = "fws";
							}

							if ("urn:firework".equalsIgnoreCase(namespaceuri)) {
								prefix = "fw";
							}
							return prefix;
						}
					});

			jaxbMarshaller.marshal(fireworks, stringWriter);
		} catch (PropertyException e) {
			log.info("Error", e);
		} catch (JAXBException e) {
			log.info("Error", e);
		}
	}

	/**
	 * Fireworks to xml.
	 *
	 * @param fireworks the fireworks
	 * @param stringWriter the string writer
	 */
	public static void fireWorksToXML(FireWork fireworks, StringWriter stringWriter) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(FireWork.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
					new NamespacePrefixMapper() {
						@Override
						public String getPreferredPrefix(String namespaceuri, String suggestion,
								boolean requireprefix) {
							return "fw";
						}
					});

			jaxbMarshaller.marshal(fireworks, stringWriter);
		} catch (PropertyException e) {
			log.info("Error", e);
		} catch (JAXBException e) {
			log.info("Error", e);
		}
	}
}
