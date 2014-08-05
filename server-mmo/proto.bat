set SRC="proto"
set DST="src\main\java"

protoc --java_out=%DST% --proto_path=%SRC% %SRC%/*.proto
pause