import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Update {
	private List<Integer> pageList;
	
	public Update() {
		this.pageList = new ArrayList<>();
	}
	
	public void addPage(Integer page) {
		this.pageList.add(page);
	}
	
	public List<Integer> getPageList(){
		return this.pageList;
	}

	public void setPageList(List<Integer> pageList){
		this.pageList = pageList;
	}
	
	@Override
	public String toString() {
		return this.pageList.stream().map(String::valueOf).collect(Collectors.joining(","));
	}
}
