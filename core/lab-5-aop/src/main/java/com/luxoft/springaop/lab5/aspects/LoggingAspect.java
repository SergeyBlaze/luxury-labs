package com.luxoft.springaop.lab5.aspects;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

import com.luxoft.springaop.lab5.exceptions.ValidationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class LoggingAspect {
	private final static Logger LOG = Logger.getLogger(LoggingAspect.class.getName());

	@Pointcut("execution(* *.set*(..))")
	public void setterMethod() {
	}

	@Pointcut("execution(* com.luxoft.springaop.lab5.model.Person.setAge(..))")
	public void setterAgeMethod() {
	}

	@Around("setterMethod() ")
	public Object setAgeValidation(ProceedingJoinPoint thisJoinPoint) throws Throwable {
		String methodName = thisJoinPoint.getSignature().getName();
		Object[] methodArgs = thisJoinPoint.getArgs();
		StringBuilder args = new StringBuilder();
		for (Object arg: methodArgs) {
			if (args.length()>0) args.append(", ");
			args.append(arg.toString());
		}
		LOG.info("Call method " + methodName + " with args " + args);
		Object result = thisJoinPoint.proceed();
		LOG.info("Method " + methodName + " returns " + result);
		return result;
	}

	@Before("setterMethod()")
	public void setAgeValidation(JoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		Optional<Object> param = Stream.of(args).findFirst();
		Object age = param.orElseThrow(() -> new RuntimeException("No params in setter"));
		if (age instanceof Integer) {
			Integer value = (Integer) age;
			if (value > 100) {
				throw new ValidationException("Age should be less than or equals 100");
			}
		} else {
			throw new RuntimeException("Age parameter of setter should be Integer");
		}
	}

	@AfterThrowing(pointcut="setterMethod()", throwing="e")
	public void validationExceptionLogger(JoinPoint joinPoint, ValidationException e) {
		String methodName = joinPoint.getSignature().getName();
		Object[] methodArgs = joinPoint.getArgs();
		StringBuilder args = new StringBuilder();
		for (Object arg : methodArgs) {
			if (args.length() > 0)
				args.append(", ");
			args.append(arg.toString());
		}
		LOG.severe("ValidationException in method " + methodName + " with args " + args);
	}
}
