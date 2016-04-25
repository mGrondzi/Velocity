import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VeloConstructorBuilder extends VeloNoParaConstBuilder{
	private ArrayList parameter = new ArrayList();

	
	public VeloConstructorBuilder(String mod, String type, String... paras)
	{
		super(mod, type);
		for(String s : paras)
			parameter.add(s);
	}
	
	@Override
	public Map getTheConstructor(){
		Map constructor = new HashMap();
		constructor.put("mod", this.mod);
		constructor.put("type", this.type);
		constructor.put("paras", this.parameter);
		return constructor;
	}
}
