Write-Host "==> Compiling TrackMint Sources & Tests..." -ForegroundColor Cyan
if (!(Test-Path out)) { New-Item -ItemType Directory -Path out | Out-Null }

$srcFiles = Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName }
$testFiles = Get-ChildItem -Recurse -Filter *.java test | ForEach-Object { $_.FullName }

javac -cp "lib/*" -d out $srcFiles $testFiles
if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed!" -ForegroundColor Red
    exit 1
}

Write-Host "==> Running JUnit 5 Test Suite..." -ForegroundColor Green
$jars = (Get-ChildItem lib/*.jar | ForEach-Object { $_.FullName }) -join ";"
$cp = "out;$jars"

java -jar lib/junit-platform-console-standalone-1.10.2.jar execute -cp $cp --scan-classpath --disable-banner
