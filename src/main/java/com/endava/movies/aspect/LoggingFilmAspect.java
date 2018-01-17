
package com.endava.movies.aspect;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingFilmAspect {

	private static final Logger logger = Logger.getLogger(LoggingFilmAspect.class);

	@Around("execution(public * com.endava.movies.service.*.*(..))")
	public Object aroundFilm(ProceedingJoinPoint jp) throws Throwable {
		long startTime = System.nanoTime();
		StringBuffer logMessage = new StringBuffer();
		logMessage.append(jp.getTarget().getClass().getCanonicalName() + "  ");
		logMessage.append(jp.getSignature().getName() + "(");
		Object obj = jp.proceed();
		long elapsedTime = System.nanoTime() - startTime;
		Object[] args = jp.getArgs();
		for (int i = 0; i < args.length; i++) {
			logMessage.append(args[i]).append(",");
		}
		if (args.length > 0) {
			logMessage.deleteCharAt(logMessage.length() - 1);
		}
		logMessage.append(")");
		if (obj != null)
			logMessage.append(" Resulted in: " + obj.toString());
		logMessage.append(
				" execution time: " + new BigDecimal(elapsedTime).divide(new BigDecimal(1000000)) + " milliseconds");
		logger.info(logMessage.toString());
		return obj;
	}

};
