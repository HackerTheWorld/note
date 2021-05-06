@Component
public class UserEventListener {

    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private PlatformTransactionManager dataSourceTransactionManager;
    @Autowired
    private TransactionDefinition transactionDefinition;


    @Override
    @Transactional
    //或者手动控制事务
    public void deleteAnalysis(Long dataId) throws Exception {

         //手动开启事务！
         //TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);


        OpticsMtfFaTrackEntity opticsMtfFaTrackWithDAO = new OpticsMtfFaTrackEntity();

        eventPublisher.publishEvent(opticsMtfFaTrackWithDAO);
        opticsMtfFaTrackWithDAO.setDataId(dataId);
        System.out.println("OpticsMtfFaTrackEntity.dataId ::"+opticsMtfFaTrackWithDAO.getDeletedStatus());
        opticsMtfFaTrackWithDAO.setStatus("Y");
        opticsMtfFaTrackWithDAO.setUpdatedBy("111");
        opticsMtfFaTrackWithDAO.setUpdatedDate(LocalDateTime.now());
        opticsMtfFaTrackRepository.save(opticsMtfFaTrackWithDAO);
        // if(dataId.equals(100L)){
        //     dataSourceTransactionManager.commit(transactionStatus);
        // }else {
        //     dataSourceTransactionManager.rollback(transactionStatus);
        // }
        //sqlUtil.updateMtfTrackById(opticsMtfFaTrackWithDAO);
    }

    @EventListener(condition = "#event.dataId == 100",classes = OpticsMtfFaTrackEntity.class)
    //condition属性是可以使用SpEL表达式的
    public void onUserRegisterEvent(OpticsMtfFaTrackEntity event) {
        System.out.println("event.dataId ::"+event.getStatus());
    }

    @TransactionalEventListener(condition = "#event.dataId == 100",phase = TransactionPhase.AFTER_COMMIT,fallbackExecution = false,classes = OpticsMtfFaTrackEntity.class)
    //phase = TransactionPhase.AFTER_COMMIT 控制触发的事务情况
    public void onUserTransactionEventListener(OpticsMtfFaTrackEntity event) {
        System.out.println("Transaction.dataId ::"+event.getStatus());
    }
}