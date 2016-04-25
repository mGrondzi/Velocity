import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VeloMethodBuilder {
	
	private String mod;
	private String type;
	private String name;
	private ArrayList methodpara = new ArrayList();
    private ArrayList methodbody = new ArrayList();
    
    public VeloMethodBuilder(String mod, String type, String name)
    {this.mod=mod; this.type=type; this.name=name;}
    
    public void addPara(String newPara){
    	methodpara.add(newPara);
    }
    
    public void addBody(String newBody){
    	methodbody.add(newBody);
    }
    
	public Map getTheMethod(){
		Map method = new HashMap();
		method.put("mod", this.mod);
		method.put("type", this.type);
		method.put("name", this.name);
		method.put("paras", this.methodpara);
		method.put("lines", this.methodbody);
		return method;
	}
}
