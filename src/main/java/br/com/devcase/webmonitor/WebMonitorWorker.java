package br.com.devcase.webmonitor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.devcase.webmonitor.service.CheckResourceService;
import dwf.slack.SlackService;

@Component
@Profile(value="!disable-worker")
public class WebMonitorWorker {
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private CheckResourceService checkResourceService;
	@Autowired(required=false)
	private SlackService slackService;
	@Value("${web-monitor.slackchannel:#general}")
	private String slackWakeUpChannel;
	
    public String getSlackWakeUpChannel() {
		return slackWakeUpChannel;
	}
	public void setSlackWakeUpChannel(String slackWakeUpChannel) {
		this.slackWakeUpChannel = slackWakeUpChannel;
	}

	final Thread checkResourcesThread = new Thread("Check resources thread") {
		@Override
		public void run() {
            try {
                while(true) { 
                	Thread.sleep( 60000 ); 
                	try {
                		logger.info("Resource checking starting" );
                        checkResourceService.checkPending();
                        logger.info("Resource checking finished");
                	} catch (Exception ex) {
                		logger.warn("Erro inesperado", ex);
                	}
                }
            } catch( InterruptedException ex ) {
            }
		}
    };

	@EventListener(classes=ContextRefreshedEvent.class) 
	public void startThread() {
        checkResourcesThread.setDaemon(false);
        checkResourcesThread.start();

        if(slackService != null) {
	        if(StringUtils.isNotBlank(slackWakeUpChannel))
	        	slackService.postMessage(slackWakeUpChannel, "WebMonitor is up!");
        }
	}
}
