package org.jksoft.utils.grid;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class MappingConfiguration {
	private Map<Character, TileType> mappingTable;
	
	public MappingConfiguration() {
		this.mappingTable = new HashMap<>();
	}
	
	public void addMapping(char c, TileType type) {
		this.mappingTable.put(c, type);
	}
	
	public TileType getMapping(char c) {
		TileType t = null;
		if(!this.mappingTable.containsKey(c))
			throw new NoSuchElementException();
		else
			return this.mappingTable.get(c);
	}
}
