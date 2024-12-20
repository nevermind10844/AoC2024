import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jksoft.utils.inputreader.InputReader;

public class Main {

	public static void main(String[] args) {
		List<String> lines = InputReader.readTestInput(3);
//		lines.forEach(System.out::println);

		Grid plot = Grid.parse(lines);
//		System.out.println(plot.highlightSymbol('L'));

		Map<Integer, List<Tile>> regionMap = new HashMap<>();
		List<Tile> assigned = new ArrayList<>(plot.getWidth() * plot.getHeight());

		int regionId = 0;

		for (int y = 0; y < plot.getHeight(); y++) {
			for (int x = 0; x < plot.getWidth(); x++) {
				Tile tile = plot.getTile(x, y);
				if (!assigned.contains(tile)) {
					List<Tile> tileList = new ArrayList<>();
					tileList.add(tile);
					regionMap.put(regionId, tileList);
					assigned.add(tile);
					explore(regionId, tile, assigned, plot, regionMap);
					regionId++;
				}
			}
		}

		int plotResult = 0;
		
		for (Map.Entry<Integer, List<Tile>> set : regionMap.entrySet()) {
			System.out.println(set.getKey());
			List<Tile> regionTiles = set.getValue();
			Grid region = Grid.fromList(regionTiles, 1);
			List<Tile> potentialBorders = region.findTiles(TileType.EMPTY);
			for (Tile tile : potentialBorders) {
				List<Tile> neighbors = region.getNeighbors(tile);
				if (neighbors.stream().filter(neighbor -> neighbor.getType().equals(TileType.OCCUPIED)).count() <= 0) {
					tile.setType(TileType.DISCARDED);
					tile.setSymbol(TileType.DISCARDED.getTileChar());
				}
			}
			
			System.out.println(region);

//			List<Tile> fields = region.findTiles(TileType.OCCUPIED);
//			for (Tile tile : fields) {
//				List<Tile> neighbors = region.getNeighbors(tile);
//				if (neighbors.stream().filter(neighbor -> neighbor.getType().equals(TileType.EMPTY)).count() <= 0) {
//					tile.setType(TileType.DISCARDED);
//					tile.setSymbol(TileType.DISCARDED.getTileChar());
//				}
//			}

			List<Tile> borderTiles = region.findTiles(TileType.EMPTY);
			for (Tile tile : borderTiles) {
				tile.setType(TileType.BORDER);
				tile.setSymbol(TileType.BORDER.getTileChar());
			}

			List<Border> borderList = new ArrayList<Border>();

			List<Tile> plotTiles = region.findTiles(TileType.OCCUPIED);
			for (Tile tile : plotTiles) {
				List<Tile> neighborTiles = region.getNeighbors(tile);
				for (Tile t : neighborTiles) {
					if (t.getType().equals(TileType.BORDER)) {
						Border b = new Border();
						b.setPlotTile(tile);
						b.setBorderTile(t);
						borderList.add(b);
					}
				}
			}

			
			List<List<Border>> borderChains = new ArrayList<>();
			
			while(borderList.size() > 0) {
//				borderList.forEach(System.out::println);
	
//				System.out.println(region);
	
				List<Border> chainedBorders = new ArrayList<>();
				Border startBorder = borderList.get(0);
				chainBorders(chainedBorders, startBorder, borderList, region);
				
				borderList.removeAll(chainedBorders);

				borderChains.add(chainedBorders);
				
				System.out.println(chainedBorders);
			}
			
			int regionResult = 0;
			
//			if(set.getKey() == 337)
//				System.out.println(region);
			
			for (List<Border> chain : borderChains) {
				int chainResult = 0;
				
				Border startBorder = chain.get(0);
				
				GridVector v0 = GridVector.fromTiles(startBorder.getPlotTile(), startBorder.getBorderTile());
				GridVector v1 = v0;
				
				Border border = null;
				GridVector v2 = null;
				
				for(int i=1; i<chain.size(); i++) {
					border = chain.get(i);
					v2 = GridVector.fromTiles(border.getPlotTile(), border.getBorderTile());
					if(!v1.equals(v2)) {
						chainResult++;
					}
					v1 = v2;
					v2 = null;
				}
				
				v2 = GridVector.fromTiles(border.getPlotTile(), border.getBorderTile());
				if(!v2.equals(v0))
					chainResult++;
				
				regionResult += chainResult;
			}
			
			regionResult *= plotTiles.size();
			
//			System.out.println(regionResult);
			plotResult += regionResult;

		}
		
		System.out.println(plotResult);

	}
	
	
	/**
	 * This method is recursively called to chain Border-Objects in order
	 * 
	 * @param chainedBorders
	 * @param nextBorder
	 * @param borderList
	 * @param region
	 */
	public static void chainBorders(List<Border> chainedBorders, Border nextBorder, List<Border> borderList, Grid region) {
		if(chainedBorders.contains(nextBorder))
			return;
		else {
			chainedBorders.add(nextBorder);
			System.out.println(nextBorder);
			System.out.println(region);
		}
		
		/**
		 * Handling outside corners first
		 * 
		 * get all borders that share the plot-tile with the nextBorder
		 * but only those, that aren't already in chainedBorders,
		 * and those where the border-tile is not aligned with the border-tile of nextBorder
		 * 
		 * 		*
		 * 		#*
		 * 		*
		 */
		List<Border> samePlotBorders = borderList.stream()
				.filter(b -> b.getPlotTile().equals(nextBorder.getPlotTile())
						&& !b.equals(nextBorder)
						&& !Grid.isAligned(b.getBorderTile(), nextBorder.getBorderTile())
						&& !chainedBorders.contains(b)
						&& !isInverted(GridVector.fromTiles(nextBorder.getPlotTile(), nextBorder.getBorderTile()), GridVector.fromTiles(b.getPlotTile(), b.getBorderTile())))
				.toList();

		if (samePlotBorders.size() > 0) {
			chainBorders(chainedBorders, samePlotBorders.get(0), borderList, region);
		} else {
			/**
			 * Handling inside-corners second
			 * 
			 * getting all the borders that share the border-tile with nextBorder
			 * but only those that aren't already in chainedBorders
			 * and those where the plot-tile is not aligned with the plot-tile of nextBorder
			 * 
			 * 		#*#
			 * 		 #
			 */
			List<Border> sameBorderBorders = borderList.stream()
					.filter(b -> b.getBorderTile().equals(nextBorder.getBorderTile())
							&& !b.equals(nextBorder)
							&& !Grid.isAligned(b.getPlotTile(), nextBorder.getPlotTile())
							&& !chainedBorders.contains(b)
							&& !isInverted(GridVector.fromTiles(nextBorder.getPlotTile(), nextBorder.getBorderTile()), GridVector.fromTiles(b.getPlotTile(), b.getBorderTile())))
					.toList();
			if(sameBorderBorders.size() > 0) {
				chainBorders(chainedBorders, sameBorderBorders.get(0), borderList, region);
			} else {
				/**
				 * Handling straights last
				 * 
				 * getting all the borders where the plot-tile is direct neighbor of nextBorder plot-tile
				 * and where their border-tile is direct neighbor of nextBorder border-tile
				 * but only those that aren't already in chained borders
				 * 
				 * 		***		#*
				 * 		###		#*
				 * 				#*
				 */
				List<Tile> plotNeighbors = region.getNeighbors(nextBorder.getPlotTile()).stream().filter(t -> t.getType().equals(TileType.OCCUPIED)).toList();
				List<Border> neighboringBorders = borderList.stream().filter(b -> plotNeighbors.contains(b.getPlotTile())).toList();
				List<Border> maybeBorders = neighboringBorders.stream()
						.filter(b -> region.getNeighbors(nextBorder.getBorderTile()).contains(b.getBorderTile())
								&& !chainedBorders.contains(b)
								&& !isInverted(GridVector.fromTiles(nextBorder.getPlotTile(), nextBorder.getBorderTile()), GridVector.fromTiles(b.getPlotTile(), b.getBorderTile())))
						.toList();
				if(maybeBorders.size() > 0) {
					chainBorders(chainedBorders, maybeBorders.get(0), borderList, region);
				}
			}
		}
	}
	
	public static boolean isInverted(GridVector v1, GridVector v2) {
		return GridVector.copyInverted(v1).equals(v2);
	}

	public static void explore(int regionId, Tile current, List<Tile> assigned, Grid plot,
			Map<Integer, List<Tile>> regionMap) {
		List<Tile> neighbors = plot.getNeighbors(current);
		for (Tile neighbor : neighbors) {
			if (!assigned.contains(neighbor)) {
				if (current.getSymbol() == neighbor.getSymbol()) {
					regionMap.get(regionId).add(neighbor);
					assigned.add(neighbor);
					explore(regionId, neighbor, assigned, plot, regionMap);
				}
			}
		}
	}

}
