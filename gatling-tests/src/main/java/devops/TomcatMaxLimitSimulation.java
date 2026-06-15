package devops;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class TomcatMaxLimitSimulation extends Simulation {

    // 1. הגדרת פרוטוקול ה-HTTP לשרת המקומי
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .shareConnections(); // שיתוף חיבורי TCP בין כל המשתמשים הוירטואליים - מונע אזילת פורטים מקומיים

    // 2. תרחיש: המשתמש מזין שם בטופס ושולח
    ScenarioBuilder scn = scenario("Tomcat Hello Form Scenario")
            .exec(http("Send_Name_Request")
                    .get("/matan-yael-roy-ofri-app/index.jsp")
                    .queryParam("name", "OfriTest")); // שליחת הפרמטר ב-GET

    // 3. הגדרת סימולציית העומס - פרץ בודד של בקשות סימולטניות, גודלו נקבע ע"י system property
    // "burst.size" (ברירת מחדל 1500). כל גודל פרץ רץ כריצת mvn נפרדת, כך שמתחילים
    // מ-state נקי (cold start) ואין השפעה הדדית בין הפרצים שנבדקים.
    {
        int burstSize = Integer.getInteger("burst.size", 1500);

        setUp(
                scn.injectOpen(
                        atOnceUsers(burstSize)
                )
        ).protocols(httpProtocol);
    }
}