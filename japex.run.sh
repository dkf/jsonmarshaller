#!/bin/sh
function help() {
  echo "japex.run.sh releases|full"
  exit 1
}
if [ $# -eq 0 ]; then
  help
else
  JAPEX_CP=".:lib/japex/jcommon-1.0.0-rc1.jar:lib/japex/jfreechart-1.0.0-rc1.jar:lib/japex/japex-1.1.6.jar:lib/japex/jaxb-api.jar:lib/japex/jaxb-impl.jar:lib/japex/activation.jar:lib/japex/jsr173_api.jar:lib/japex/ant.jar"
  case $1 in
    full)
      ant benchmark
      java -cp $JAPEX_CP com.sun.japex.Japex config.japex.full.xml;;

    releases)
      ant dist
      ant benchmark
      java -cp $JAPEX_CP com.sun.japex.Japex config.japex.releases.xml;;

    *) help;;
  esac
fi
