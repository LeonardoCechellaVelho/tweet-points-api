package org.ls.tweetpoints.config;

import com.surrealdb.connection.SurrealWebSocketConnection;
import com.surrealdb.driver.SyncSurrealDriver;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class SurrealConfig { 
    
    private static final String NAMESPACE_NAME = "ls";
    private static final String DATABASE_NAME = "tweet-points";
    
    @Produces
    public SyncSurrealDriver createConnection() {
        SurrealWebSocketConnection conn = new SurrealWebSocketConnection("localhost", 8000, false);
        conn.connect(5);
        SyncSurrealDriver driver = new SyncSurrealDriver(conn);
        driver.signIn("root", "root");
        driver.use(NAMESPACE_NAME, DATABASE_NAME);
        return driver;
    }
}
