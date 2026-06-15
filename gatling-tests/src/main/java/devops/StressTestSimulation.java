package devops;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

public class StressTestSimulation extends Simulation {

    // 1. הגדרת פרוטוקול ה-HTTP לשרת המקומי
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .shareConnections();

    // 2. תרחיש: משתמש שולח בקשה, ואז "חושב" כמה שניות לפני הבקשה הבאה
    ScenarioBuilder scn = scenario("Tomcat Hello Form Scenario - Stress Test")
            .exec(http("Send_Name_Request")
                    .get("/matan-yael-roy-ofri-app/index.jsp")
                    .queryParam("name", "OfriTest"))
            .pause(Duration.ofSeconds(1), Duration.ofSeconds(3));

    // 3. הגדרת סימולציית העומס - עלייה הדרגתית עד 5,000 משתמשים מקבילים, שזוהה ב-MAX_LIMIT_REPORT.md
    // כנקודת ה"מקסימום המעשי" (מעבר אליה שיעור הכשלים בפרץ מתייצב על ~80%), במשך דקה, ושמירה על
    // העומס הגבוה למשך 4 דקות נוספות (5 דקות סה"כ) כדי לבחון אם עומס מתמשך (עם pacing) מתנהג
    // שונה מפרץ חד-פעמי באותו גודל.
    {
        setUp(
                scn.injectClosed(
                        rampConcurrentUsers(0).to(5000).during(Duration.ofMinutes(1)),
                        constantConcurrentUsers(5000).during(Duration.ofMinutes(4))
                )
        ).protocols(httpProtocol);
    }
}
