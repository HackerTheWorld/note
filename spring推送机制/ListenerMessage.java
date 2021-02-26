@Component
public class UserEventListener {
    @EventListener(condition = "#user.name!=null")
    public void handleEvent(UserDto user) throws Exception{
        System.out.println(user.getName());
        System.out.println(user.getSex());
    }
}