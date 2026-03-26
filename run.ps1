javac -cp "lib/*" -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java --enable-native-access=ALL-UNNAMED -cp "out;lib/*" com.trackmint.app.Main