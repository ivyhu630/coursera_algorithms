DIR=$(pwd | tr "/" " " | awk '{ print $(NF) }')
OUT=~/Coding/Algorithms/packaged/$DIR
rm -rf $OUT
mkdir -p $OUT
cp *.java $OUT/
CWD=$(pwd)
cd $OUT
for f in $(ls); do
  cat $f | grep -v ^package >$f.tmp
  mv $f.tmp $f
done
cd ..
ZIPOUT=$DIR.zip
rm -f $ZIPOUT
zip -r $ZIPOUT $DIR
rm -rf $DIR
open .
