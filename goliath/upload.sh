sftp -v pi@10.0.0.5 <<EOF
lcd /c/Users/kscott-laptop-window/robotcode2/goliath/app/build/libs/
cd /home/pi/bot/
put shadow-all.jar
quit
EOF

SFTP_RETURN_CODE=${?}

# If the return code is non-zero then the upload was not successful

if [[ 0 != ${SFTP_RETURN_CODE} ]]
   then
   echo "bib upload for zzzzz failed"
   exit ${SFTP_RETURN_CODE}
else
   echo "bib upload for zzzzz was successful"
fi

exit 0

