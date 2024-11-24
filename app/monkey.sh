#!/bin/bash

PACKAGE_NAME="co.edu.uniandes.vinilos"

REPORT_DIR="./local_reports"
mkdir -p $REPORT_DIR

GLOBAL_REPORT_NAME="MonkeyExhaustiveReport.txt"
LOCAL_REPORT_PATH="$REPORT_DIR/$GLOBAL_REPORT_NAME"

echo "========================================"
echo "Iniciando pruebas Monkey desde MainActivity..."
echo "========================================"

echo ">>> Iniciando la aplicación en MainActivity..."
adb shell am start -n "$PACKAGE_NAME/.MainActivity"

sleep 5

echo ">>> Ejecutando Monkey en toda la aplicación..."
adb shell monkey -p $PACKAGE_NAME --pct-touch 60 --pct-motion 30 --pct-nav 10 --ignore-crashes --ignore-timeouts --throttle 300 -v 10000 > $LOCAL_REPORT_PATH

echo ">>> Informe global guardado en: $LOCAL_REPORT_PATH"

echo "========================================"
echo "Pruebas completadas. El informe está en: $LOCAL_REPORT_PATH"
echo "========================================"
