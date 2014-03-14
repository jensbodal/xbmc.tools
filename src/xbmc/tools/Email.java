package xbmc.tools;

/**
 *
 * @author jensb
 */
public abstract class Email implements Emailable {
    
    

    public enum EMAIL {
        Template("", "", "", "");
        
        private final String to;
        private final String from;
        private final String subject;
        private final String body;
        
        private EMAIL(String to, String from, String subject, String body) {
            this.to = to;
            this.from = from;
            this.subject = subject;
            this.body = body;
        }
        
        public String[] getTemplate() {
            return new String[]{to, from, subject, body};
        }
    }
}
