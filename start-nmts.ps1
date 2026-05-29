# NMTS Startup Script (PowerShell)
# This script starts all microservices in the correct order in separate windows.

function Start-Service($name, $path) {
    Write-Host "Starting $name..." -ForegroundColor Cyan
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "title $name; ./mvnw spring-boot:run -pl $path"
}

# 1. Start Eureka Server (Wait 15s)
Start-Service "EUREKA-SERVER" "eureka-server"
Write-Host "Waiting for Eureka to initialize..." -ForegroundColor Yellow
Start-Sleep -Seconds 15

# 2. Start Config Server (Wait 15s)
Start-Service "CONFIG-SERVER" "config-server"
Write-Host "Waiting for Config Server to initialize..." -ForegroundColor Yellow
Start-Sleep -Seconds 15

# 3. Start Microservices
Start-Service "AUTH-SERVICE" "auth-service"
Start-Service "USER-SERVICE" "user-service"
Start-Service "MINING-AGENCY-SERVICE" "mining-agency-service"
Start-Service "LICENSE-SERVICE" "license-service"
Start-Service "SEARCH-CATALOG-SERVICE" "search-catalog-service"

Write-Host "Waiting for services to register with Eureka..." -ForegroundColor Yellow
Start-Sleep -Seconds 20

# 4. Start API Gateway (Last)
Start-Service "API-GATEWAY" "api-gateway"

Write-Host "All services have been triggered!" -ForegroundColor Green
Write-Host "Check the individual terminal windows for logs." -ForegroundColor White
