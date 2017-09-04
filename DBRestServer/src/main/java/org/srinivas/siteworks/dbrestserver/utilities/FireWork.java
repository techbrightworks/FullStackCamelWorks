package org.srinivas.siteworks.dbrestserver.utilities;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class FireWork.
 */
@XmlRootElement(name = "firework",namespace="urn:firework")
public class FireWork {

	private int id;
	private String name;
	private String category;
	private String description;

	/**
	 * Instantiates a new fire work.
	 */
	public FireWork() {
	}

	/**
	 * Instantiates a new fire work.
	 *
	 * @param id the id
	 * @param Category the category
	 * @param name the name
	 * @param description the description
	 */
	public FireWork(int id, String Category, String name, String description) {
		this.id = id;
		this.name = name;
		this.category = Category;
		this.description = description;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}