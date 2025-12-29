#!/usr/bin/env bash

# ===========================================================
# EventosUY Complete Deployment Script
# Builds projects, starts tarea1, deploys WARs, and restarts Tomcat
# ===========================================================

set -e  # Stop on error

PROJECT_ROOT="$(cd "$(dirname "$0")" && pwd)"
TAREA1_DIR="$PROJECT_ROOT/tarea1"
TAREA2_DIR="$PROJECT_ROOT/tarea2-sjp"
TOMCAT_DIR="${TOMCAT_HOME:-$PROJECT_ROOT/apache-tomcat-11.0.13}"
SERVIDOR_PROPERTIES="$PROJECT_ROOT/servidor.properties"

echo ""
echo "=========================================="
echo "EventosUY - Complete Deployment"
echo "=========================================="
echo ""

# Step 1: Build both projects
echo "[1/4] Building projects..."
bash "$PROJECT_ROOT/desplegar.sh"
echo "✓ Build completed"
echo ""

# Step 2: Start tarea1 in background
echo "[2/4] Starting tarea1 (Servidor Central)..."
JAR_PATH="$TAREA1_DIR/target/servidor.jar"

if [[ ! -f "$JAR_PATH" ]]; then
    echo "ERROR: servidor.jar not found at $JAR_PATH"
    exit 1
fi

# Check if tarea1 is already running
if pgrep -f "servidor.jar" > /dev/null; then
    echo "⚠ tarea1 is already running, skipping..."
else
    cd "$PROJECT_ROOT"
    nohup java -jar "$JAR_PATH" > "$PROJECT_ROOT/tarea1.log" 2>&1 &
    TAREA1_PID=$!
    echo "✓ tarea1 started (PID: $TAREA1_PID)"
    echo "  Logs: $PROJECT_ROOT/tarea1.log"
    sleep 2  # Give it a moment to start
fi
echo ""

# Step 3: Stop Tomcat and clean old deployments
echo "[3/5] Stopping Tomcat..."
if [[ -f "$TOMCAT_DIR/bin/shutdown.sh" ]]; then
    "$TOMCAT_DIR/bin/shutdown.sh" >/dev/null 2>&1 || true
    sleep 3
fi

# Kill any remaining Tomcat processes
pkill -f "catalina.startup.Bootstrap" 2>/dev/null || true
sleep 2

# Remove old deployments to avoid conflicts
TOMCAT_WEBAPPS="$TOMCAT_DIR/webapps"
if [[ -d "$TOMCAT_WEBAPPS" ]]; then
    echo "  Cleaning old deployments..."
    rm -rf "$TOMCAT_WEBAPPS/web" "$TOMCAT_WEBAPPS/movil" \
           "$TOMCAT_WEBAPPS/web.war" "$TOMCAT_WEBAPPS/movil.war" 2>/dev/null || true
fi
echo "✓ Tomcat stopped"
echo ""

# Step 4: Copy WAR files to Tomcat
echo "[4/5] Copying WAR files to Tomcat..."
WEB_WAR="$TAREA2_DIR/target/web.war"
MOVIL_WAR="$TAREA2_DIR/target/movil.war"

if [[ ! -f "$WEB_WAR" ]]; then
    echo "ERROR: web.war not found at $WEB_WAR"
    exit 1
fi

if [[ ! -d "$TOMCAT_WEBAPPS" ]]; then
    echo "ERROR: Tomcat webapps directory not found at $TOMCAT_WEBAPPS"
    exit 1
fi

cp "$WEB_WAR" "$TOMCAT_WEBAPPS/"
echo "✓ web.war copied"

if [[ -f "$MOVIL_WAR" ]]; then
    cp "$MOVIL_WAR" "$TOMCAT_WEBAPPS/"
    echo "✓ movil.war copied"
fi
echo ""

# Step 5: Start Tomcat
echo "[5/5] Starting Tomcat..."
if [[ ! -f "$TOMCAT_DIR/bin/startup.sh" ]]; then
    echo "ERROR: Tomcat startup.sh not found at $TOMCAT_DIR/bin/startup.sh"
    exit 1
fi

# Ensure scripts are executable
chmod +x "$TOMCAT_DIR/bin"/*.sh 2>/dev/null || true

"$TOMCAT_DIR/bin/startup.sh"
echo "✓ Tomcat started"
echo ""

# Summary
echo "=========================================="
echo "Deployment Complete!"
echo "=========================================="
echo ""
echo "Services:"
echo "  - Tarea1 (Web Service): http://localhost:9128/webservices"
echo "  - Web App: http://localhost:8080/web"
echo "  - Mobile App: http://localhost:8080/movil"
echo ""
echo "Logs:"
echo "  - Tarea1: $PROJECT_ROOT/tarea1.log"
echo "  - Tomcat: $TOMCAT_DIR/logs/catalina.out"
echo ""

