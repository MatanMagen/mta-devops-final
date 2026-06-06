# MTA DevOps Final — JSP Web App

Simple JSP application with one link, one button, and one input text box.

**Team:** Matan Magen, Yael Moshkovich, Roy Kalfon, Ofri Kuperberg

## Structure

```
matan-yael-roy-ofri-app/   <- deployable webapp folder (name includes all team members)
├── index.jsp              <- the page (link + button + text input + JSP server-side code)
└── WEB-INF/
    └── web.xml            <- deployment descriptor
```

## Run locally

1. Install Tomcat (macOS):
   ```bash
   brew install tomcat
   ```

2. Deploy — copy the app folder into Tomcat's `webapps` directory:
   ```bash
   cp -r matan-yael-roy-ofri-app "$(brew --prefix)/opt/tomcat/libexec/webapps/"
   ```

3. Start Tomcat:
   ```bash
   brew services start tomcat        # background service
   # or run in foreground:
   # catalina run
   ```

4. Open in browser:

   http://localhost:8080/matan-yael-roy-ofri-app/

5. Stop Tomcat:
   ```bash
   brew services stop tomcat
   ```
