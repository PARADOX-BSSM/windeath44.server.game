package windeath44.game.global.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private static AtomicLong count = new AtomicLong(0);

    @Pointcut("execution(* windeath44.game.domain.rhythmGamePlayHistory.service.*.*(..))")
    public void playHisotryMethodLog() {}
//
//    @Pointcut("execution(* windeath44.game.domain.ranking.service.*.*(..))")
//    public void rankingMethodLog() {}


}
