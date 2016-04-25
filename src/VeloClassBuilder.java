import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VeloClassBuilder {

	private String type;
	private ArrayList<Map> attributlist = new ArrayList();
	private ArrayList<VeloNoParaConstBuilder> constructorArraylist = new ArrayList();
	private ArrayList<VeloMethodBuilder> methodArraylist = new ArrayList();
	
	public VeloClassBuilder(String type)
	{
		this.type=type;
	}
	
	public void addNewAttribute(String mod, String type, String name)
	{
		Map attribute = new HashMap();
		attribute.put("mod", mod);
		attribute.put("type", type);
		attribute.put("name", name);
		attributlist.add(attribute);
	}
	
	public void addNewConstructor(String mod,String... paras)
	{
		constructorArraylist.add(new VeloConstructorBuilder(mod, this.type, paras));
	}
	
	public void addNewNoParaConstructor(String mod,String... paras)
	{
		constructorArraylist.add(new VeloNoParaConstBuilder(mod, this.type, this.attributlist, paras));
	}
	
	public void addNewMethod(String mod, String type, String name)
	{
		methodArraylist.add(new VeloMethodBuilder(mod, type, name));
	}
	
	public VeloMethodBuilder getLastMethod(){
		return methodArraylist.get(methodArraylist.size()-1);
	}
	
	public Map getTheClass(){
		Map newclass = new HashMap();
		ArrayList<Map> methodlist = new ArrayList();
		ArrayList<Map> constlist = new ArrayList();
        
		newclass.put("type", type);
        newclass.put("attributes", attributlist);
        
        for(VeloNoParaConstBuilder c : constructorArraylist)
        	constlist.add(c.getTheConstructor());
        newclass.put("constructors", constlist);
        
        
        for(VeloMethodBuilder m : methodArraylist)
        	methodlist.add(m.getTheMethod());
        newclass.put("methods", methodlist);
		return newclass;
	}
		
}
