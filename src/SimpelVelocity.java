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
        ArrayList<Map> methodlist = new ArrayList();//Liste aller Methoden

        
         //Neue Methode
        VeloMethodBuilder vM = new VeloMethodBuilder("public", "WeekScope", "every");
        vM.addBody("return new Weakscope(new GregorianCalendar(), new WeekAndDayMemorie());");
        methodlist.add(vM.getTheMethod());        
      //Neue Methode
        vM = new VeloMethodBuilder("public", "MinuteScope", "in");
        vM.addBody("return new MinuteScope();");
        methodlist.add(vM.getTheMethod());
        
        vM = new VeloMethodBuilder("public", "TimeWrapperScope", "on");
        vM.addPara("Day... days");
        vM.addBody("WeekAndDayMemorie wadm = new WeekAndDayMemorie();");
        vM.addBody("GregorianCalendar c = new GregorianCalendar();");
        vM.addBody("for (Day day : days) {wadm.addDay(day);}");
        vM.addBody("int oldVal = c.get(Calendar.DAY_OF_MONTH);");
        vM.addBody("c.set(Calendar.DAY_OF_WEEK, (days[0].ordinal() + 2 > 7) ? 1 : days[0].ordinal() + 2);");
        vM.addBody("if (oldVal > c.get(Calendar.DAY_OF_MONTH)) {c.add(Calendar.WEEK_OF_YEAR, 1);}");
        vM.addBody("return new TimeWrapperScope(c, wadm);");
        methodlist.add(vM.getTheMethod());
        
        vM = new VeloMethodBuilder("public", "YearScope", "on");
        vM.addBody("return new YearScope(new GregorianCalendar());");
        methodlist.add(vM.getTheMethod());
        
        vM = new VeloMethodBuilder("public", "TimeWrapperScope", "today");
        vM.addBody("return new TimeWrapperScope(new GregorianCalendar(), null);");
        methodlist.add(vM.getTheMethod());
        
        context.put("SimpleMethods", methodlist);
      
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
        nC = new VeloClassBuilder("DayScope");
        nC.addNewConstructor("private", "GregorianCalendar c");
        nC.addNewAttribute("private", "GregorianCalendar", "c");
        nC.addNewMethod("public", "TimeWrapperScope", "day");
        nC.getLastMethod().addPara("int day");
        nC.getLastMethod().addBody("int currMonth = c.get(Calendar.MONTH);");
        nC.getLastMethod().addBody("int highestDay;");
        nC.getLastMethod().addBody("if (currMonth == 1) {ighestDay = 28;}");
        nC.getLastMethod().addBody("else if ((currMonth % 2) == 0) {highestDay = 31;}");
        nC.getLastMethod().addBody("else {highestDay = 30;}");
        nC.getLastMethod().addBody("if (day < 1 || day > highestDay) {throw new IllegalArgumentException(ALARM_DATE_INVALID_MSG);}");
        nC.getLastMethod().addBody("c.set(Calendar.DAY_OF_MONTH, day);");
        nC.getLastMethod().addBody("return new TimeWrapperScope(c, null);");
        classlist.add(nC.getTheClass());
        
      //Neue Klasse erstellen
        nC = new VeloClassBuilder("TimeWrapperScope");
        nC.addNewConstructor("private", "GregorianCalendar c", "WeekAndDayMemorie wadm");
        nC.addNewAttribute("private", "GregorianCalendar", "c");
        nC.addNewAttribute("private", "WeekAndDayMemorie", "wadm");
        nC.addNewMethod("public", "HourScope", "at");
        nC.getLastMethod().addBody("return new HourScope(c, wadm);");
        classlist.add(nC.getTheClass());
        
      //Neue Klasse erstellen
        nC = new VeloClassBuilder("HourScope");
        nC.addNewConstructor("private", "GregorianCalendar c", "WeekAndDayMemorie wadm");
        nC.addNewAttribute("private", "GregorianCalendar", "c");
        nC.addNewAttribute("private", "WeekAndDayMemorie", "wadm");
        nC.addNewMethod("public", "TimeMinuteScope", "hour");
        nC.getLastMethod().addPara("int hour");
        nC.getLastMethod().addBody("if (hour < 0 || hour > 23) {throw new IllegalArgumentException(ALARM_DATE_INVALID_MSG);}");
        nC.getLastMethod().addBody("c.set(Calendar.HOUR_OF_DAY, hour);");
        nC.getLastMethod().addBody("return new TimeMinuteScope(c, wadm);");
        classlist.add(nC.getTheClass());
        
      //Neue Klasse erstellen
        nC = new VeloClassBuilder("TimeMinuteScope");
        nC.addNewConstructor("private", "GregorianCalendar c", "WeekAndDayMemorie wadm");
        nC.addNewAttribute("private", "GregorianCalendar", "c");
        nC.addNewAttribute("private", "WeekAndDayMemorie", "wadm");
        nC.addNewMethod("public", "AlarmProperties", "minute");
        nC.getLastMethod().addPara("int minute");
        nC.getLastMethod().addBody("if (minute < 0 || minute > 59) {throw new IllegalArgumentException(ALARM_DATE_INVALID_MSG);}");
        nC.getLastMethod().addBody("c.set(Calendar.MINUTE, minute);");
        nC.getLastMethod().addBody("return new TimeMinuteScope(c, wadm);");
        classlist.add(nC.getTheClass());
        
      //Neue Klasse erstellen
        nC = new VeloClassBuilder("WeekScope");
        nC.addNewConstructor("private", "GregorianCalendar c", "WeekAndDayMemorie wadm");
        nC.addNewAttribute("private", "GregorianCalendar", "c");
        nC.addNewAttribute("private", "WeekAndDayMemorie", "wadm");
        nC.addNewMethod("public", "DayWrapperScope", "weeks");
        nC.getLastMethod().addPara("int weeks");
        nC.getLastMethod().addBody("if (weeks < 1) {throw new IllegalArgumentException(ALARM_DATE_INVALID_MSG);}");
        nC.getLastMethod().addBody("wadm.setWeeks(weeks);");
        nC.getLastMethod().addBody("return new DayWrapperScope(c, wadm);");
        nC.addNewMethod("public", "DayWrapperScope", "week");
        nC.getLastMethod().addBody("wadm.setWeeks(1);");
        nC.getLastMethod().addBody("return new DayWrapperScope(c, wadm);");
        classlist.add(nC.getTheClass());

      //Neue Klasse erstellen
        nC = new VeloClassBuilder("DayWrapperScope");
        nC.addNewConstructor("private", "GregorianCalendar c", "WeekAndDayMemorie wadm");
        nC.addNewAttribute("private", "GregorianCalendar", "c");
        nC.addNewAttribute("private", "WeekAndDayMemorie", "wadm");
        nC.addNewMethod("public", "TimeWrapperScope", "on");
        nC.getLastMethod().addPara("Day... days");
        nC.getLastMethod().addBody("for (Day day : days) {wadm.addDay(day);}");
        nC.getLastMethod().addBody("int oldVal = c.get(Calendar.DAY_OF_MONTH);");
        nC.getLastMethod().addBody("c.set(Calendar.DAY_OF_WEEK, (days[0].ordinal() + 2 > 7) ? 1 : days[0].ordinal() + 2);");
        nC.getLastMethod().addBody("if (oldVal > c.get(Calendar.DAY_OF_MONTH)) {c.add(Calendar.WEEK_OF_YEAR, 1);}");
        nC.getLastMethod().addBody("return new TimeWrapperScope(c, wadm);");
        classlist.add(nC.getTheClass());
        
      //Neue Klasse erstellen
        nC = new VeloClassBuilder("WeekAndDayMemorie");
        nC.addNewAttribute("private", "int", "weeks");
        nC.addNewAttribute("private", "LinkedList<Day>", "days");
        nC.addNewNoParaConstructor("private", "0", "new LinkedList<Day>()");
        nC.addNewMethod("public", "void", "setWeeks");
        nC.getLastMethod().addPara("int weeks");
        nC.getLastMethod().addBody("if (weeks < 1) {throw new IllegalArgumentException(ALARM_DATE_INVALID_MSG);}");
        nC.getLastMethod().addBody("this.weeks = weeks;");
        nC.addNewMethod("public", "void", "addDay");
        nC.getLastMethod().addPara("Day day");
        nC.getLastMethod().addBody("if (!this.days.contains(day)) {days.add(day);}");
        nC.addNewMethod("public", "int", "getWeeks()");
        nC.getLastMethod().addBody("return weeks;");
        nC.addNewMethod("public", "LinkedList<Day>", "getDays");
        nC.getLastMethod().addBody("return days");
        classlist.add(nC.getTheClass());
        
        context.put("SimpleClasses", classlist);
    
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        
        System.out.println(writer);
    }
}