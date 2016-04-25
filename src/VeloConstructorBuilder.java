import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VeloConstructorBuilder {
	private ArrayList parameter = new ArrayList();
	private String type;
	private String mod;
	
	public VeloConstructorBuilder(String mod, String type, String... paras)
	{
		this.mod = mod;
		this.type = type;
		for(String s : paras)
			parameter.add(s);
	}
	
	public Map getTheConstructor(){
		Map constructor = new HashMap();
		constructor.put("mod", this.mod);
		constructor.put("type", this.type);
		constructor.put("paras", this.parameter);
		return constructor;
	}
}
