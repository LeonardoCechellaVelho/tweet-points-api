Write-Output "Starting Keycloak..."
Start-Process "docker" -ArgumentList "run", "-p", "8180:8080", "-e", "KEYCLOAK_ADMIN=admin", "-e", "KEYCLOAK_ADMIN_PASSWORD=admin", "-v", "./keycloak/realm-export", "quay.io/keycloak/keycloak:22.0.0", "start-dev", "--import-realm"
Start-Sleep -Seconds 10  # Espera um pouco para o serviço iniciar

Write-Output "Keycloak started."
Write-Output "Starting SurrealDB..."
Start-Process "surreal.exe" -ArgumentList "start", "memory", "-A", "--auth", "--user", "root", "--pass", "root"
Start-Sleep -Seconds 5

Write-Output "SurrealDB started."
Write-Output "Starting Quarkus..."
Start-Process "cmd" -ArgumentList "/c", "quarkus dev"
