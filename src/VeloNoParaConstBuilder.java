import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VeloNoParaConstBuilder {
	protected String type;
	protected String mod;
	private ArrayList<Map> attributes = new ArrayList();
	private ArrayList values = new ArrayList();
	
	public VeloNoParaConstBuilder(String mod, String type, ArrayList<Map> attributes, String... values)
	{
		this.mod = mod;
		this.type = type;
		this.attributes = attributes;
		for(String s : values)
			this.values.add(s);
	}
	
	public VeloNoParaConstBuilder(String mod, String type)
	{
		this.mod = mod;
		this.type = type;
	}
	
	public Map getTheConstructor(){
		Map constructor = new HashMap();
		constructor.put("mod", this.mod);
		constructor.put("type", this.type);
		constructor.put("attributes", this.attributes);
		constructor.put("values", this.values);
		return constructor;
	}
}
