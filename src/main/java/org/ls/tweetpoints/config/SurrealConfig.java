package org.ls.tweetpoints.config;

import com.surrealdb.connection.SurrealWebSocketConnection;
import com.surrealdb.driver.SyncSurrealDriver;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class SurrealConfig { 
    
    private static final String NAMESPACE_NAME = "ls";
    private static final String DATABASE_NAME = "tweet-points";
    
    private SyncSurrealDriver driver;
    
    void onStart(@Observes StartupEvent ev) {
        SurrealWebSocketConnection conn = new SurrealWebSocketConnection("localhost", 8000, false);
        conn.connect(5);
        driver = new SyncSurrealDriver(conn);
        driver.signIn("root", "root");
        driver.use(NAMESPACE_NAME, DATABASE_NAME);
    }

    public SyncSurrealDriver database() {
        return this.driver;
    }
}
