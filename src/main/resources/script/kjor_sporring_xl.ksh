#!/bin/ksh

MOTTAKERE='Robert.Kindingstad@norgesgruppen.no'

export ENV_HOME=/u01/oracle/10.1.3/j2ee/xw
export JOBBNAVN=$5
export XW_HOME=/u01/oracle/10.1.3/j2ee/xw
export JARS=$XW_HOME/queries/java/jars
export JAVAIO=$ENV_HOME/queries/java/javaio
export SQLDIR=$ENV_HOME/queries/java/javaio/sql
export OC4J_JAR=/u01/oracle/10.1.3/j2ee/home/oc4jclient.jar
export OC4J_HOME=/u01/oracle/10.1.3
export JAVA_HOME=$OC4J_HOME/jdk
export JAVA=$JAVA_HOME/bin/java
export OUTPUT=$ENV_HOME/output/sql_resultat/
export OK_MLD=$JOBBNAVN' 'avsluttet' 'med' 'vellykket' 'resultat.
export IKKEOK_MLD="$JOBBNAVN har feilet!"
export MAILIND=N
export MOTTAKERE2=$6

echo JOBBNAVN=$JOBBNAVN

#START=`date +"%d.%m.%C%y %H:%M:%S"`
START=$7

cd $JAVAIO

cp $1 db.properties

cp $SQLDIR/$2 ./sqlin.txt 

$JAVA -Xmx1024M -cp $JARS/rkutils-1.0.jar:$JARS/classes12.jar:$JARS/poi-2.5.1-final-20040804.jar:$JARS/spring-2.5.jar:$JARS/commons-logging-1.1.jar:$JAVAIO/ rkutils.KjorSql 'J'

RETUR=$?

SLUTT=`date +"%d.%m.%C%y %H:%M:%S"`

if [ $RETUR -ne 0 ] ; then

echo '$2 har feilet!';

mailx -s "$IKKEOK_MLD" $MOTTAKERE << EOF
Scriptet $2 har feilet!

Skriptet startet $START og sluttet $SLUTT
EOF
exit 1
fi

#ftp -n ngoas1ap <<EOT
#user oracle oracle92
#asc
#put sqlut.txt /u01/oracle/10.1.3/j2ee/xw/output/sql_resultat/$3
#quit
#EOT

cp sqlut.txt $OUTPUT$3.sdv
cp sqlut.xls $OUTPUT$3.xls

SLUTT=`date +"%d.%m.%C%y %H:%M:%S"`

RETUR=$?

#echo verdien for input variabel nummer 4 er: $4

if [ $RETUR -ne 0 ] ; then
echo '$2 har feilet!'

mailx -s "$IKKEOK_MLD" $MOTTAKERE << EOF
Scriptet $2 har feilet i ftp-overføringen.!

Skriptet startet $START og sluttet $SLUTT
EOF
else
if [ "$4" = J ] ; then
echo sender ut mail ...

#mailx  -s "$OK_MLD" $MOTTAKERE << EOF
echo mottakerliste:$MOTTAKERE2

mailx -a $OUTPUT/$3.xls -s "$OK_MLD" $MOTTAKERE2 << EOF

Spørringen $2 har kjørt vellykket.

Skriptet startet $START og sluttet $SLUTT

EOF
exit 0
fi
exit 0
fi


