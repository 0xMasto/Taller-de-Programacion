#!/usr/bin/env bash
# ===========================================================
# EventosUY Build Script - Unix/Linux (bash) version
# ===========================================================
# Usage:
#   ./desplegar.sh           # Build both tarea1 and tarea2 (default)
#   ./desplegar.sh tarea1    # Build only tarea1
#   ./desplegar.sh tarea2    # Build only tarea2
# ===========================================================

set -e  # Stop on error


PROJECT_ROOT="$(cd "$(dirname "$0")" && pwd)"
TAREA1_DIR="$PROJECT_ROOT/tarea1"
TAREA2_DIR="$PROJECT_ROOT/tarea2-sjp"

function build_tarea1() {
  echo "==> Building Tarea 1..."
  cd "$TAREA1_DIR"
  mvn clean package -DskipTests
  echo "==> Tarea 1 build complete!"
}


function build_tarea2() {
  echo "==> Building Tarea 2..."
  cd "$TAREA2_DIR"
  mvn clean
  mvn package -DskipTests
  mvn package -Pmobile -DskipTests
  echo "==> Tarea 2 build complete!"
}

# Parse command line arguments
TARGET="${1:-both}"

case "$TARGET" in
  tarea1)
    build_tarea1
    ;;
  tarea2)
    build_tarea2
    ;;
  both|"")
    build_tarea1
    build_tarea2
    ;;
  *)
    echo "Error: Invalid target '$TARGET'"
    echo "Usage: $0 [tarea1|tarea2|both]"
    echo "  tarea1 - Build only Tarea 1"
    echo "  tarea2 - Build only Tarea 2"
    echo "  both   - Build both (default)"
    exit 1
    ;;
esac

echo ""
echo "==> All builds completed successfully!" 
