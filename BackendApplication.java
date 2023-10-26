package com.g1t6.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.g1t6.backend.Loan.loanService;

@SpringBootApplication
public class BackendApplication {

	@Autowired
	private loanService loanService;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Scheduled(initialDelay = 1000L, fixedDelayString = "PT24H") 
	void sendReminderEmailMain() { 
	loanService.sendReminderEmail(); 
 }

}


@Configuration 
@EnableScheduling 
@ConditionalOnProperty(name = "scheduling.enable", matchIfMissing = true) 
class SchedulingConfiguration { 
 
}
