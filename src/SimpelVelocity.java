import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class SimpelVelocity
{
    public static void main( String[] args )
        throws Exception
    {
        /*  first, get and initialize an engine  */
        VelocityEngine ve = new VelocityEngine();
        ve.init();
        /*  next, get the Template  */
        Template t = ve.getTemplate( "/src/Weckermain.vm" );
        /*  create a context and add data */
        VelocityContext context = new VelocityContext();
        
        /* now render the template into a StringWriter */
        
        ArrayList methodlist = new ArrayList();//Liste aller Methoden
        
        Map method = new HashMap(); //Neue Methode
        method.put("type","WeekScope");
        method.put("name", "every");
        ArrayList methodpara = new ArrayList();
        methodpara.add("new GregorianCalendar()");
        methodpara.add("new WeekAndDayMemorie()");
        method.put("paras", methodpara);
        methodlist.add(method);
        
        method = new HashMap(); //Neue Methode
        method.put("type","MinuteScope");
        method.put("name", "in");
        methodlist.add(method);
        
        method = new HashMap(); //Neue Methode
        method.put("type","YearScope");
        method.put("name", "on");
        methodpara = new ArrayList();
        methodpara.add("new GregorianCalendar()");
        method.put("paras", methodpara);
        methodlist.add(method);
        
        method = new HashMap(); //Neue Methode
        method.put("type","TimeWrapperScope");
        method.put("name", "today");
        methodpara = new ArrayList();
        methodpara.add("new GregorianCalendar()");
        methodpara.add("null");
        method.put("paras", methodpara);
        methodlist.add(method);
        
        
        context.put("SimpleMethods", methodlist);
    
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        
        System.out.println(writer);
    }
}