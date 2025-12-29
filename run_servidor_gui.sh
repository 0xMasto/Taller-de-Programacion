#!/usr/bin/env bash
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
JAR_PATH="$PROJECT_ROOT/tarea1/target/servidor.jar"

if [ ! -f "$JAR_PATH" ]; then
    echo "Jar not found at $JAR_PATH. Build tarea1 first (e.g., mvn -pl tarea1 package)." >&2
    exit 1
fi

# Ensure DISPLAY is set even if the shell rc file has not run.
if [ -z "${DISPLAY:-}" ]; then
    if grep -qi microsoft /proc/version 2>/dev/null; then
        WSL_HOST_IP=$(awk '/nameserver/ {print $2; exit}' /etc/resolv.conf 2>/dev/null)
        if [ -n "$WSL_HOST_IP" ]; then
            export DISPLAY="$WSL_HOST_IP:0"
        else
            export DISPLAY=":0"
        fi
    else
        export DISPLAY=":0"
    fi
    echo "DISPLAY was unset. Using DISPLAY=$DISPLAY"
fi

echo "Launching GUI from $JAR_PATH ..."
# Ensure web services bind to an address available inside WSL unless overridden.
export SERVIDOR_PROPERTIES_PATH="${SERVIDOR_PROPERTIES_PATH:-$PROJECT_ROOT/servidor.properties}"
export WEBSERVICES_HOST="${WEBSERVICES_HOST:-0.0.0.0}"
exec java -jar "$JAR_PATH"

