package testingSub;

import java.awt.Component;
import java.util.HashMap;

//class to get the instance of the components so that have a reference to call from one to another
public class ComponentsLists {
	private static HashMap<String, Component> components = new HashMap<String, Component>();
	
	public Component getComponent(String key) {
		return components.get(key);
	}
	
	public void addComponent(String key, Component component) {
		components.put(key, component);
	}
}
