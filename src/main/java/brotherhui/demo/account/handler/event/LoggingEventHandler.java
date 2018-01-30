package brotherhui.demo.account.handler.event;

import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author xiaohui.c.liu
 * this is used to log what event is being generated
 *
 */
@Component
public class LoggingEventHandler {

	private final static Logger log = LoggerFactory.getLogger(LoggingEventHandler.class);
	@EventHandler
	public void on(Object event){
		log.info("event received :: {}", event);
	}
}
