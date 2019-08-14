//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Test {
//    public static void main(String[] args) {
////        System.out.println(null);
//        System.out.println(getSP(0));
//    }
//    public  static double getSP(int offset){
//        Map<Date,Double> dateDoubleMap = ispm();
//        Date date = initSPM();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DAY_OF_YEAR, offset);
//        date = calendar.getTime();
//        Double res = dateDoubleMap.get(date);
//        return res;
//    }
//
//    public static Map<Date,Double> ispm(){
//        Map<Date,Double> map = new HashMap<>();
//        map.put(new Date(1573401600000L), 10.0);
//        map.put(new Date(1573488000000L), 20.0);
//        map.put(new Date(1573574400000L), 33.0);
//        return map;
//    }
//
//    public static Date initSPM(){
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.MONTH, 10);
//        calendar.set(Calendar.DATE, 11);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        return calendar.getTime();
//    }
//}


