import java.util.function.Function;

public class Main {
    
    public static void main(String[] args){

        Function<Object,Object> f1 = i -> i+"ss";
        Function<Object,Object> f2 = i -> i+"dd";
        //执行 i -> i+"ss" 方法
        String str = (String) f1.apply(10);
        System.out.println(str);
        //执行 i -> i+"ss" 方法 ,i -> i+"dd" 方法
        str = (String) f1.andThen(f2).apply(10);
        System.out.println(str);
        //执行 i -> i+"dd" 方法 ,i -> i+"ss" 方法
        str = (String) f1.compose(f2).apply(10);
        System.out.println(str);


    }
    
    
}
