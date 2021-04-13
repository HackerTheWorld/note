//指定本次请求，返回结果时的接收器
ResponseListener decoratedResponse = new ResponseListener(exchange.getResponse(), systemLog, systemLogRepository);
return chain.filter(exchange.mutate().response(decoratedResponse).build());

//指定接收的类。原本用于解码器
public class ResponseListener extends ServerHttpResponseDecorator {

    private final SystemLog systemLog;

    private final SystemLogRepository systemLogRepository;

    public ResponseListener(ServerHttpResponse delegate, SystemLog systemLog, SystemLogRepository systemLogRepository) {
        super(delegate);
        this.systemLog = systemLog;
        this.systemLogRepository = systemLogRepository;
    }

    @Override
    public void beforeCommit(@NonNull Supplier<? extends Mono<Void>> action) {
        ServerHttpResponse serverHttpResponse = getDelegate();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        systemLog.setHttpStatusCode(String.valueOf(Objects.requireNonNull(serverHttpResponse.getStatusCode()).value()));
        systemLog.setReturnTime(df.format(LocalDateTime.now()));
        systemLogRepository.save(systemLog);
        super.beforeCommit(action);
    }

}
