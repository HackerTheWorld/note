@Service
public class UserEventRegister {
    @Autowired
    private ApplicationEventPublisher publisher;
 
    public void register() throws Exception {
        UserDto user=new UserDto();
        user.setName("电脑");
        user.setSex("未知");
        publisher.publishEvent(user);
    }
}