#!/bin/ksh
START=`date +"%d.%m.%C%y %H:%M:%S"`
RAPPNAVN='Exworks fakturaliste'

~/xw_home/queries/kjor_sporring_xl.ksh db.properties.XW.prod sqlin_XW_fakturaliste.sql Exworks_fakturaliste J "Rapporteringsjobb for $RAPPNAVN" 'rokind@norgesgruppen.no' "$START"

