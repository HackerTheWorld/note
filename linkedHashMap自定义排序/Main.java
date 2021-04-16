import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args){

        LinkedHashMap<String,Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("aa", 1);
        linkedHashMap.put("aaa", 2);
        linkedHashMap.put("aaaa", 3);

        LinkedHashMap<String,Integer> linkedHashMap2 = linkedHashMap.entrySet().stream()
            //非一般类型需要实现Comparator接口
            //按value倒叙
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            /**
             * 按value正序
             * Map.Entry.comparingByValue(Comparator.naturalOrder())
             * 按key正序,倒叙
             * Map.Entry.comparingByKey(Comparator.reverseOrder())
             */
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldValue, newValue) -> oldValue, LinkedHashMap::new));

        for(Map.Entry<String,Integer> entry:linkedHashMap2.entrySet()){
            System.out.println(entry.getKey()+"::"+entry.getValue());
        }

        System.out.println("-----------------------------------");

        LinkedHashMap<String,String> linkedHashMap3 = new LinkedHashMap<>();
        linkedHashMap3.put("aa", "11");
        linkedHashMap3.put("aaa", "02");
        linkedHashMap3.put("aaaa", "3");

        LinkedHashMap<String,String> linkedHashMap4 = linkedHashMap3.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(new MyCom()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldValue, newValue) -> oldValue, LinkedHashMap::new));
    
            for(Map.Entry<String,String> entry:linkedHashMap4.entrySet()){
                System.out.println(entry.getKey()+"::"+entry.getValue());
            }
        }
    
}

class MyCom implements Comparator<String>{
    /**
     * 如果指定的数与参数相等返回0。

       如果指定的数小于参数返回 -1。

       如果指定的数大于参数返回 1。
     */
    @Override
    public int compare(String o1, String o2) {
        Integer i1 = Integer.parseInt(o1);
        Integer i2 = Integer.parseInt(o2);
        return -(i1-i2);
    }

}