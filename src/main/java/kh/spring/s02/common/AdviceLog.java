package kh.spring.s02.common;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;

@Aspect
@Service
public class AdviceLog {
	
	
	// 로거 사용하기 위한 작업 slf4j + AdviceLog.class == 해당 class 에 뿌리기 위한 거
	private static final Logger logger = LoggerFactory.getLogger(AdviceLog.class);
	
	
	/*
	 * public * kh.spring.s02..*Controller.*(..)
	 * 
	 * kh.spring.s02 패키지의 ..*Controller 어떤 형태던 상관 없지만 Controller 로 끝나고 
	 * *(..) 어느 메소드 (*) + 파라메터(..)도 상관 없이 @Pointcut을 건다
	 * 
	 * * ==> 1개 이상이 존재한다.
	 * .. ==> 0개 이상이 존재한다.
	 * 
	 * * Controller ==>  Controller 앞에 어떤 문자가 1개 이상이 존재한다. ex) HomeController BoardController
	 * 
	 */
	
	@Pointcut("execution(public * kh.spring.s02..*Controller.*(..))")
	public void controllerPointCut() {
		// 해당 @Pointcut 의 이름은  controllerPointCut 이며 
		
		
	}
	
	@Pointcut("execution(public * kh.spring.s02..*Dao.*(..))")
	public void daoPointCut() {
		
	}
	
	@Pointcut("execution(public * kh.spring.s02..*ServiceImp.*(..))")
	public void serviceImpPointCut() {
		
	}
	
	@Around("controllerPointCut()")
	public Object aroundControllerPointCut(ProceedingJoinPoint jp)throws Throwable {

		Object returnObj = null;
		Object[] args= jp.getArgs();
		// args ==> Locale locale, Model model, HttpSession session
		
		for(int i=0; i< args.length; i++) {
			logger.info("args[" + i + "]: " + args[i]);
		}
		
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
				
		returnObj = jp.proceed();
		stopwatch.stop();
		
		logger.info("Ctrl Return : ["+ stopwatch.getTotalTimeMillis() + "] : " + returnObj);
		
		return returnObj;
	}
	
	@Around("daoPointCut()")
	public Object aroundDaoPointCut(ProceedingJoinPoint pjp) throws Throwable{
		// @Around 는 void 가 아닌 Object 사용 + JoinPoint 가 아닌 ProceedingJoinPoint 사용
		Object returnObj = null;
		
		Object[] args= pjp.getArgs();
		// args ==> Locale locale, Model model, HttpSession session
		
		for(int i=0; i< args.length; i++) {
			logger.info("args[" + i + "]: " + args[i]);
		}
		
		//걸리는 시간 측정
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		
		// 타겟 메소드 실행 + 해당 오류는 호출한 곳으로 던지기 ==> throws Throwable 이거 작성
		returnObj = pjp.proceed();
		stopwatch.stop();
		
		logger.info("Dao Return : ["+ stopwatch.getTotalTimeMillis() + "] : " + returnObj);
		
		
		return returnObj;
	}
	
	@Around("serviceImpPointCut()")
	public Object aroundServicePointCut(ProceedingJoinPoint pjp) throws Throwable{
		// @Around 는 void 가 아닌 Object 사용 + JoinPoint 가 아닌 ProceedingJoinPoint 사용
		Object returnObj = null;
		
		Object[] args= pjp.getArgs();
		// args ==> Locale locale, Model model, HttpSession session
		
		for(int i=0; i< args.length; i++) {
			logger.info("args[" + i + "]: " + args[i]);
		}
		
		//걸리는 시간 측정
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		
		// 타겟 메소드 실행 + 해당 오류는 호출한 곳으로 던지기 ==> throws Throwable 이거 작성
		returnObj = pjp.proceed();
		stopwatch.stop();
		
		logger.info("Srvc Return : ["+ stopwatch.getTotalTimeMillis() + "] : " + returnObj);
		
		
		return returnObj;
	}
	
	
}
