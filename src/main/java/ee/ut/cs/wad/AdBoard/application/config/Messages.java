package ee.ut.cs.wad.AdBoard.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class Messages {
	
	private final MessageSource messageSource;
	private MessageSourceAccessor accessor;
	
	@Autowired
	public Messages(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@PostConstruct
	private void init() {
		accessor = new MessageSourceAccessor(messageSource);
	}
	
	public String get(String code) {
		Locale locale = LocaleContextHolder.getLocale();
		return accessor.getMessage(code, locale);
	}
}
