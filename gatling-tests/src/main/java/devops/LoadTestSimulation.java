package devops;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

public class LoadTestSimulation extends Simulation {

    // 1. הגדרת פרוטוקול ה-HTTP לשרת המקומי
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .shareConnections();

    // 2. תרחיש: משתמש שולח בקשה, ואז "חושב" כמה שניות לפני הבקשה הבאה (מדמה התנהגות אמיתית)
    ScenarioBuilder scn = scenario("Tomcat Hello Form Scenario - Load Test")
            .exec(http("Send_Name_Request")
                    .get("/matan-yael-roy-ofri-app/index.jsp")
                    .queryParam("name", "OfriTest"))
            .pause(Duration.ofSeconds(1), Duration.ofSeconds(3));

    // 3. הגדרת סימולציית העומס - עלייה הדרגתית ל-1000 משתמשים מקבילים במשך דקה,
    // ושמירה על העומס למשך 4 דקות נוספות (5 דקות סה"כ)
    {
        setUp(
                scn.injectClosed(
                        rampConcurrentUsers(0).to(1000).during(Duration.ofMinutes(1)),
                        constantConcurrentUsers(1000).during(Duration.ofMinutes(4))
                )
        ).protocols(httpProtocol)
         .assertions(
                global().successfulRequests().percent().gt(99.0),
                global().responseTime().max().lt(1000),
                global().responseTime().mean().lt(200)
         );
    }
}
