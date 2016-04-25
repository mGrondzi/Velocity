import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
        
        
        ArrayList<Map> classlist = new ArrayList();
        Map newclass = new HashMap();
        ArrayList<Map> attributlist = new ArrayList();
        Map attribute = new HashMap();
        ArrayList<Map> methodlist = new ArrayList();//Liste aller Methoden
        Map method = new HashMap();
        ArrayList methodpara = new ArrayList();
        ArrayList methodbody = new ArrayList();
        
         //Neue Methode
        method = new HashMap();
        method.put("type","WeekScope");
        method.put("name", "every");
        
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
        
//        //New Classes in
//        newclass = new HashMap();
//        newclass.put("type", "MinuteScope");
//        //Neue Methode der Klasse
//        methodlist = new ArrayList();
//        method = new HashMap();
//        method.put("mod", "public");
//        method.put("type", "AlarmProperties");
//        method.put("name", "minutes");
//        methodpara = new ArrayList();
//        methodpara.add("int minutes");
//        method.put("paras", methodpara);
//        methodbody.add("GregorianCalendar c = new GregorianCalendar();");
//        methodbody.add("c.add(Calendar.MINUTE, minutes);");
//        methodbody.add("return new AlarmProperties(c, null);");
//        method.put("lines", methodbody);
//        methodlist.add(method);
//        
//        newclass.put("methods", methodlist);
//        classlist.add(newclass);
        
//        //New Klasse in
//        newclass = new HashMap();
//        newclass.put("type", "YearScope");
//        //Neue Attribute
//        attributlist = new ArrayList();
//        //attributlist.add(vb.getNewAttribute("private", "GregorianCalendar", "c"));
//        
//        
//        //Neue Methode der Klasse
//        methodlist = new ArrayList(); //Reset Methodenliste
//        method = new HashMap();
//        method.put("mod", "public");
//        method.put("type", "MonthScope");
//        method.put("name", "year");
//        methodpara = new ArrayList();
//        methodpara.add("int year");
//        method.put("paras", methodpara);
//        methodbody = new ArrayList();
//        methodbody.add("c.set(Calendar.YEAR, year);");
//        methodbody.add("return new MonthScope(c);");
//        method.put("lines", methodbody);
//        methodlist.add(method);
//        
//        //Neue Methode der Klasse
//        method = new HashMap();
//        method.put("mod", "public");
//        method.put("type", "MonthScope");
//        method.put("name", "ThisYear");
//        methodbody = new ArrayList();
//        methodbody.add("return new MonthScope(c);");
//        method.put("lines", methodbody);
//        methodlist.add(method);
//        //Füge Attribute, Methoden der Klasse hinzu
//        newclass.put("attributes", attributlist);
//        newclass.put("methods", methodlist);
//        classlist.add(newclass);
        
      
      //Neue Klasse erstellen
        VeloClassBuilder nC = new VeloClassBuilder("MinuteScope");
        nC.addNewConstructor("private");
        nC.addNewMethod("public", "AlarmProperties", "minutes");
        nC.getLastMethod().addPara("int minutes");
        nC.getLastMethod().addBody("GregorianCalendar c = new GregorianCalendar();");
        nC.getLastMethod().addBody("c.add(Calendar.MINUTE, minutes);");
        nC.getLastMethod().addBody("return new AlarmProperties(c, null);");
        classlist.add(nC.getTheClass());
        
      //Neue Klasse erstellen
        nC = new VeloClassBuilder("YearScope");
        nC.addNewConstructor("private", "GregorianCalendar c");
        nC.addNewAttribute("private", "GregorianCalendar", "c");
        nC.addNewMethod("public", "MonthScope", "year");
        nC.getLastMethod().addPara("int year");
        nC.getLastMethod().addBody("c.set(Calendar.YEAR, year);");
        nC.getLastMethod().addBody("return new MonthScope(c);");
        nC.addNewMethod("public", "MonthScope", "thisYear");
        nC.getLastMethod().addBody("return new MonthScope(c);");
        classlist.add(nC.getTheClass());
        
        //Neue Klasse erstellen
        nC = new VeloClassBuilder("MonthScope");
        nC.addNewConstructor("private", "GregorianCalendar c");
        nC.addNewAttribute("private", "GregorianCalendar", "c");
        nC.addNewMethod("public", "DayScope", "month");
        nC.getLastMethod().addPara("int month");
        nC.getLastMethod().addBody("if (month < 1 || month > 12) {throw new IllegalArgumentException(ALARM_DATE_INVALID_MSG);}");
        nC.getLastMethod().addBody("c.set(Calendar.MONTH, month - 1);");
        nC.getLastMethod().addBody("return new DayScope(c);");
        nC.addNewMethod("public", "DayScope", "thisMonth");
        nC.getLastMethod().addBody("return new DayScope(c);");
        classlist.add(nC.getTheClass());
        
      //Neue Klasse erstellen
        nC = new VeloClassBuilder("TimeWrapperScope");
        nC.addNewConstructor("private", "GregorianCalendar c", "WeekAndDayMemorie wadm");
        nC.addNewAttribute("private", "GregorianCalendar", "c");
        nC.addNewAttribute("private", "WeekAndDayMemorie", "wadm");
        nC.addNewMethod("public", "HourScope", "at");
        nC.getLastMethod().addBody("return new HourScope(c, wadm);");
        classlist.add(nC.getTheClass());

        context.put("SimpleClasses", classlist);
    
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        
        System.out.println(writer);
    }
}